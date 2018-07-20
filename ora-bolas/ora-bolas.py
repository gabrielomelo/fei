"""
    Author: Gabriel Melo
    License: GPL3.0
    For more instructions check: https://github.com/gabrielomelo
"""

import sys
from robot import *
from graphics import *
from field import *

#processa o arquivo de texto e transforma em uma matriz n-dimensional
def fetching_trajectory(path_to_file):
    ball_data = []
    values = open(path_to_file, "r")
    positions = values.readlines()
    positions.pop(0)
    for i in range(len(positions)):
        ball_data.append(positions[i].split())
    for i in range(len(ball_data)):
        for j in range(len(ball_data[i])):
            ball_data[i][j] = ball_data[i][j].replace(",", ".")
    return ball_data


#converte os elementos da matriz de char para float
def matrix_to_float(matrix):
    for i in range(len(matrix)):
        for j in range(len(matrix[i])):
            matrix[i][j] = float(matrix[i][j])
    return matrix 


#converte os elementos do vetor de char para float
def vector_to_float(vector):
    temp = []
    for i in range(len(vector)):
        temp.append(float(vector[i]))
    return temp

#converte uma coluna em lista
def col_to_list(matrix, index):
    temp = []
    for j in range(len(matrix)):
        temp.append(matrix[j][index])
    return temp


#processa os argumentos da linha de comando
def get_args():
    args = []
    for arg in sys.argv:
        args.append(arg)
    args.pop(0)
    return vector_to_float(args)


#define a ordem que os graficos sao compostos
def graphics_plotting(graphics, time, robot_tel, ball_tel, distance):
    #compondo gráficos do deslocamento
    graphics.trajectory(ball_tel[0][0], ball_tel[0][1], robot_tel[0][0], robot_tel[0][1], "Deslocamento no plano")
    graphics.time_trajectory(time, ball_tel[0][0], ball_tel[0][1], robot_tel[0][0], robot_tel[0][1], "Posição da bola em função do tempo")
    #compondo gráficos da velocidade
    graphics.velocity_components(time, ball_tel[1][0], ball_tel[1][1], robot_tel[1][0], robot_tel[1][1], "Componentes da Velocidade Média")
    graphics.module(time, [robot_tel[2][0], ball_tel[2][0]], "Velocidade", "Velocidade Escalar")
    #compondo gráficos da aceleracao
    graphics.aceleration_components(time, ball_tel[1][2], ball_tel[1][3], robot_tel[1][2], robot_tel[1][3], "Componentes da Aceleração Média")
    graphics.module(time, [robot_tel[2][1], ball_tel[2][1]], "Aceleração", "Aceleração Escalar")
    #compondo graficos da distancia relativa
    graphics.distance(time, distance, "Distância entre Robô e Bola")
    graphics.show()

def intro():
    print(
    """
     _____ _____ _____    _____ _____ __    _____ _____
    |     | __  |  _  |  | __  |     |  |  |  _  |   __|
    |  |  |    -|     |  | __ -|  |  |  |__|     |__   |
    |_____|__|__|__|__|  |_____|_____|_____|__|__|_____|
                                                    
    USAGE: ora-bolas.py [x] [y] [velocity] [interception_radius]

    Author: Gabriel Melo (@gabrielomelo) - V1 (FINAL)
    """)

#------------------ MAIN --------------------------------------------------

def main(): 
    args = get_args()

    if len(args) <= 3: #verifica se os argumentos necessários foram passados
        intro()
        return
    
    #processando o arquivo de texto e transformando em matriz
    ball_data = matrix_to_float(
            fetching_trajectory("ball_trajectory.dat")
            )

    #iniciando os objetos da simulacao
    field = Field(
        Robot(args[0], args[1], args[2], args[3],
        [col_to_list(ball_data, 1), col_to_list(ball_data, 2)],
        col_to_list(ball_data, 0))
        )
    #disparando a simulacao (retorna o robo)
    robot = field.simulate()

    #plot dos graficos <- passo os dados recolhidos da simulação para a funcao de plot
    graphics_plotting(Graphics(), robot.time_perception, 
        [
            robot.known_positions,
            robot.va_components,
            robot.displacement
        ],
        [
            robot.ball_known_positions,
            robot.ball_va_components,
            robot.ball_displacement
        ],
        robot.distance
    )

#processo principal
main()