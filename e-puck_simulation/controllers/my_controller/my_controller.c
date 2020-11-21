#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <webots/device.h>
#include <webots/distance_sensor.h>
#include <webots/led.h>
#include <webots/motor.h>
#include <webots/nodes.h>
#include <webots/robot.h>
#include <webots/supervisor.h>

#include <math.h>

#define DISTANCE_SENSORS_NUMBER 8
#define TIME_STEP 256
static WbDeviceTag distance_sensors[DISTANCE_SENSORS_NUMBER];
static double distance_sensors_values[DISTANCE_SENSORS_NUMBER];
static const char *distance_sensors_names[DISTANCE_SENSORS_NUMBER] = {"ps0", "ps1", "ps2", "ps3", "ps4", "ps5", "ps6", "ps7"};

#define GROUND_SENSORS_NUMBER 3
static WbDeviceTag ground_sensors[GROUND_SENSORS_NUMBER];
static double ground_sensors_values[GROUND_SENSORS_NUMBER] = {0.0, 0.0, 0.0};
static const char *ground_sensors_names[GROUND_SENSORS_NUMBER] = {"gs0", "gs1", "gs2"};

#define LEDS_NUMBER 10
static WbDeviceTag leds[LEDS_NUMBER];
static bool leds_values[LEDS_NUMBER];
static const char *leds_names[LEDS_NUMBER] = {"led0", "led1", "led2", "led3", "led4", "led5", "led6", "led7", "led8", "led9"};

static WbDeviceTag left_motor, right_motor;

#define LEFT 0
#define RIGHT 1
#define MAX_SPEED 3.5
static double speeds[2];


static double weights[DISTANCE_SENSORS_NUMBER][2] = {{-1.3, -1.0}, {-1.3, -1.0}, {-0.5, 0.5}, {0.0, 0.0},
                                                     {0.0, 0.0},   {0.05, -0.5}, {-0.75, 0},  {-0.75, 0}};
static double offsets[2] = {0.5 * MAX_SPEED, 0.5 * MAX_SPEED};

int touched_mobile_objects = 0;

double distance_points = 0.0;

static void turn_on_led_qty(int led_qty) {
  int i;
  if (led_qty > 10) return;
  for (i = 0; i < led_qty; i++) {
    leds_values[i] = true;
  }
}

static void turn_off_led_qty(int led_qty) {
  int i;
  if (led_qty > 10) return;
  for (i = 0; i < led_qty; i++) {
    leds_values[i] = false;
  }
}

static int get_time_step() {
  static int time_step = -1;
  if (time_step == -1)
    time_step = (int) wb_robot_get_basic_time_step();
  return time_step;
}

static void step() {
  if (wb_robot_step(get_time_step()) == -1) {
    wb_robot_cleanup();
    exit(EXIT_SUCCESS);
  }
}

static void passive_wait(double sec) {
  double start_time = wb_robot_get_time();
  do {
    step();
  } while (start_time + sec > wb_robot_get_time());
}

static void init_devices() {
  int i;
  for (i = 0; i < DISTANCE_SENSORS_NUMBER; i++) {
    distance_sensors[i] = wb_robot_get_device(distance_sensors_names[i]);
    wb_distance_sensor_enable(distance_sensors[i], get_time_step());
  }
  for (i = 0; i < LEDS_NUMBER; i++)
    leds[i] = wb_robot_get_device(leds_names[i]);

  for (i = 0; i < GROUND_SENSORS_NUMBER; i++)
    ground_sensors[i] = (WbDeviceTag)0;
    
  int ndevices = wb_robot_get_number_of_devices();
  for (i = 0; i < ndevices; i++) {
    WbDeviceTag dtag = wb_robot_get_device_by_index(i);
    const char *dname = wb_device_get_name(dtag);
    WbNodeType dtype = wb_device_get_node_type(dtag);
    if (dtype == WB_NODE_DISTANCE_SENSOR && strlen(dname) == 3 && dname[0] == 'g' && dname[1] == 's') {
      int id = dname[2] - '0';
      if (id >= 0 && id < GROUND_SENSORS_NUMBER) {
        ground_sensors[id] = wb_robot_get_device(ground_sensors_names[id]);
        wb_distance_sensor_enable(ground_sensors[id], get_time_step());
      }
    }
  }

  left_motor = wb_robot_get_device("left wheel motor");
  right_motor = wb_robot_get_device("right wheel motor");
  wb_motor_set_position(left_motor, INFINITY);
  wb_motor_set_position(right_motor, INFINITY);
  wb_motor_set_velocity(left_motor, 0.0);
  wb_motor_set_velocity(right_motor, 0.0);

  step();
}

static void reset_actuator_values() {
  int i;
  for (i = 0; i < 2; i++)
    speeds[i] = 0.0;
}


static void get_sensor_input() {
  for (int i = 0; i < DISTANCE_SENSORS_NUMBER; i++) {
    distance_sensors_values[i] = wb_distance_sensor_get_value(distance_sensors[i]);

    distance_sensors_values[i] /= 4096;
  }
}

static void set_actuators() {
  int i;
  for (i = 0; i < LEDS_NUMBER; i++)
    wb_led_set(leds[i], leds_values[i]);
  wb_motor_set_velocity(left_motor, speeds[LEFT]);
  wb_motor_set_velocity(right_motor, speeds[RIGHT]);
}

static void run_braitenberg() {
  int i, j;
  for (i = 0; i < 2; i++) {
    speeds[i] = 0.0;
    for (j = 0; j < DISTANCE_SENSORS_NUMBER; j++)
      speeds[i] += distance_sensors_values[j] * weights[j][i];

    speeds[i] = offsets[i] + speeds[i] * MAX_SPEED;
    if (speeds[i] > MAX_SPEED)
      speeds[i] = MAX_SPEED;
    else if (speeds[i] < -MAX_SPEED)
      speeds[i] = -MAX_SPEED;
  }
}


double distance(float x1, float y1, float x2, float y2) {
  return sqrt(pow(x1-x2, 2) + pow(y1-y2, 2));
}

bool detect_mobile_collision(double *sensor_values, const double *actual_position, double *previous_position) {
  if (sensor_values[0] > 0.115 || sensor_values[7] > 0.115) {
    distance_points = distance(previous_position[0], previous_position[2], actual_position[0], actual_position[2]);
    
    if ((distance_points > 0.0065)) {
      return true;
    }
    return false;
  }
}


int main(int argc, char **argv) {
  wb_robot_init();
  init_devices();
  
  WbNodeRef robot_node = wb_supervisor_node_get_from_def("roboto"); // get robot supervisor
  WbFieldRef trans_field = wb_supervisor_node_get_field(robot_node, "translation"); //identifica o campo de posição
  const double *actual_position; // robot actual absolute position
  double previous_position[3];
  
  actual_position = wb_supervisor_field_get_sf_vec3f(trans_field);
  previous_position[0] = actual_position[0];
  previous_position[1] = actual_position[1];
  previous_position[2] = actual_position[2];
  
  int blink_counter = 0;
  bool still_blinking = false;
  
  while (wb_robot_step(TIME_STEP) != -1) {
    reset_actuator_values();
    
    actual_position = wb_supervisor_field_get_sf_vec3f(trans_field);
    printf("Posicao anterior do robo: x= %f z= %f\n", previous_position[0], previous_position[2]);
    printf("Posicao do robo: x= %f z= %f\n", actual_position[0], actual_position[2]);
    
    get_sensor_input();
    
    for (int i = 0; i < DISTANCE_SENSORS_NUMBER; i++)
      printf("valor do sensor %d: %f \n", i, distance_sensors_values[i]);
    
    if(detect_mobile_collision(distance_sensors_values, actual_position, previous_position)) {
      printf("COLIDIU COM ALGO MOVEL\n");
      
      touched_mobile_objects++;
      
      still_blinking = true;
    }
    
    if (still_blinking) {
      if (((blink_counter)%2) == 0)
        turn_on_led_qty(LEDS_NUMBER);
      else
        turn_off_led_qty(LEDS_NUMBER);
      
      blink_counter++;
      if (blink_counter == 6) {
        turn_off_led_qty(LEDS_NUMBER);
        blink_counter = 0;
        still_blinking = false;
      }
    }
    
    previous_position[0] = actual_position[0];
    previous_position[1] = actual_position[1];
    previous_position[2] = actual_position[2];
    
    turn_on_led_qty(touched_mobile_objects);
    printf("quantidade de colisoes com objetos moveis: %d \n", touched_mobile_objects);
    printf("distancia entre pontos %f \n", distance_points);
    
  
    run_braitenberg();
    set_actuators();
    step();
  };

  return EXIT_SUCCESS;
}