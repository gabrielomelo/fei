"""
    Author: Gabriel Melo
    License: GPL3.0
    For more instructions check: https://github.com/gabrielomelo
"""

import math

class Robot(object):

    #construtor da classe
    def __init__(self, x, y, velocity, inter_radius, ball, time):

        #variaveis de interoperabilidade
        self.time_perception = time
        self.ball_known_positions = ball #x and y
        self.known_positions = [[], []] #x and y
        self.displacement = [[], []] #velocity and aceleration
        self.ball_displacement = [[], []] #velocity and aceleration
        self.va_components = [[], [], [], []] #velocity and aceleration components
        self.ball_va_components = [[], [], [], []] #velocity and aceleration components
        self.distance = []
        self.index = 0

        #componentes da bola
        self.ball_velocity = 0
        self.ball_aceleration = 0
        self.bvx = 0
        self.bvy = 0
        self.bax = 0
        self.bay = 0

        #componentes do robo
        self.MAX_VELOCITY = velocity / 50 #metros
        self.INTERCEPTION_RADIUS = inter_radius #metros
        self.aceleration = 0
        self.velocity = 0
        self.x = x
        self.y = y
        self.vx = 0
        self.vy = 0
        self.ax = 0
        self.ay = 0

    #escreve os resultados da simulacao no buffer do terminal
    def display(self):
        if self.index == 0:
            print("|  time  |   xb   |   yb   | vel_b  |  bvx  |  bvy  | acel_b |   bax  |   bay  |   xr   |   yr   |  vel_r  |  rvx  |  rvy  | acel_r |  rax  |  ray  |  dist  |")
        print("|  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f  |  %.2f |  %.2f  |" 
                %(
                self.time_perception[self.index], #time
                self.ball_known_positions[0][self.index], #x
                self.ball_known_positions[1][self.index], #y
                self.ball_displacement[0][self.index], #velocity
                self.ball_va_components[0][self.index], #velocity x
                self.ball_va_components[1][self.index], #velocity y
                self.ball_displacement[1][self.index], #aceleration
                self.ball_va_components[2][self.index], #aceleration x
                self.ball_va_components[3][self.index], #aceleration y
                self.known_positions[0][self.index],
                self.known_positions[1][self.index],
                self.displacement[0][self.index],
                self.va_components[0][self.index],
                self.va_components[1][self.index],
                self.displacement[1][self.index],
                self.va_components[2][self.index],
                self.va_components[3][self.index],
                self.get_distance())
                )


    #dispara a pilha de execucao para a acao padrao do robo no campo
    def get_ball(self):
        #calculos -> aproximacao -> telemetria
        self.calculate()
        self.boost()
        self.display()
        #caso a distancia entre os dois seja menor que o raio de interceptacao
        #retorno o true, senao somo um no indice e retorno false
        if self.get_distance() <= self.INTERCEPTION_RADIUS:
            return True
        #caso o indice passe do tamanho maximo das posicoes da bola.
        elif self.index == len(self.ball_known_positions[0]) - 2:
            return False
        else :
            self.index += 1
            return False


    #realiza os calculos necessarios para o desenvolvimento dos graficos e da aproximacao
    def calculate(self):
        #armazenando os dados da bola
        self.ball_displacement[0].append(self.ball_velocity)
        self.ball_displacement[1].append(self.ball_aceleration)
        self.ball_va_components[0].append(self.bvx)
        self.ball_va_components[1].append(self.bvy)
        self.ball_va_components[2].append(self.bax)
        self.ball_va_components[3].append(self.bay)
        self.distance.append(self.get_distance())
        
        #armazenando os dados do robo
        self.displacement[0].append(self.velocity)
        self.displacement[1].append(self.aceleration)
        self.va_components[0].append(self.vx)
        self.va_components[1].append(self.vy)
        self.va_components[2].append(self.ax)
        self.va_components[3].append(self.ay)
        self.known_positions[0].append(self.x)
        self.known_positions[1].append(self.y)

        #se o indice da lista de componentes for menor que 1, logo nao temos dados suficientes para 
        #conseguir realizar os calculos,
        if len(self.ball_va_components[0]) < 1:

            return
            
        #computando o intervalo de tempo
        delta_t = self.time_perception[self.index] - self.time_perception[self.index - 1]

        #atribuindo os valores das posicoes da bola
        bxi = self.ball_known_positions[0][self.index -1]
        bxf = self.ball_known_positions[0][self.index]
        byi = self.ball_known_positions[1][self.index - 1]
        byf = self.ball_known_positions[1][self.index]  
        bvix = self.ball_va_components[2][self.index - 1]
        bvfx = self.ball_va_components[2][self.index]
        bviy = self.ball_va_components[3][self.index - 1]
        bvfy = self.ball_va_components[3][self.index]

        #atribuindo os valores das posicoes do robo
        xi = self.known_positions[0][self.index - 1]
        xf = self.known_positions[0][self.index]
        yi = self.known_positions[1][self.index - 1]
        yf = self.known_positions[1][self.index]
        vix = self.va_components[2][self.index - 1]
        vfx = self.va_components[2][self.index]
        viy = self.va_components[3][self.index - 1]
        vfy = self.va_components[3][self.index]

        #computando os deltas da bola
        delta_bx = bxf - bxi
        delta_by = byf - byi
        delta_bvx = bvfx - bvix
        delta_bvy = bvfy - bviy
        
        #computando a velocidade e aceleracao em cada componente da bola
        self.bvx = delta_bx / delta_t
        self.bvy = delta_by / delta_t
        self.bax = delta_bvx / delta_t
        self.bay = delta_bvy / delta_t

        #computando os deltas do robo
        delta_x = xf - xi
        delta_y = yf - yi
        delta_vx = vfx - vix
        delta_vy = vfy - viy   
        
        #computando a velocidade e aceleracao em cada componente do robo
        self.vx = delta_x / delta_t
        self.vy = delta_y / delta_t
        self.ax = delta_vx / delta_t
        self.ay = delta_vy / delta_t

        #conseguindo a velocidade e aceleracao escalares da bola ou a resultante
        self.ball_velocity = math.hypot(self.bvx, self.bvy)
        self.ball_aceleration = math.hypot(self.bax, self.bay)   
        
        #conseguindo a velocidade e aceleracao escalares do robo ou a resultante
        self.velocity = math.hypot(self.vx, self.vy)
        self.aceleration = math.hypot(self.ax, self.ay)


    #aproximação por cordenadas polares, usei a tangente e isso foi um erro
    #usei a atan2 que faz a verificação por quadrantes
    #a versão anterior tinha resultados interessantes, porém, havia a indefinicao da tang
    def boost(self):
        #descobrindo a próxima posição da bola
        bx = self.ball_known_positions[0][self.index + 1]
        by = self.ball_known_positions[1][self.index + 1]
        #calculando as distancias em cada direcao
        delta_x = bx - self.x
        delta_y = by - self.y
        #descobrindo o arco formado entre a bola e o robo em rad
        angle = math.atan2(delta_y, delta_x)
        #somando as componentes <- movimento acomtece aqui
        #algo que deduzi estudando é que o no plano
        #o movimento é apenas resultante do deslocamento para cada direcao
        self.x += self.MAX_VELOCITY * math.cos(angle)
        self.y += self.MAX_VELOCITY * math.sin(angle)


    #retorna a distancia entre o robo e a bola
    def get_distance(self):
        delta_x = self.x - self.ball_known_positions[0][self.index]
        delta_y = self.y - self.ball_known_positions[1][self.index]
        #retorna a distancia euclidiana <- usei a funcao de hypot para o codigo ficar mais limpo
        return math.hypot(delta_x, delta_y)
