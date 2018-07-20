"""
    Author: Gabriel Melo
    License: GPL3.0
    For more instructions check: https://github.com/gabrielomelo
"""

class Field(object):
    #defino o tempo e o intervalo de tempo para sincronizar os calculos.
    #para fazer funcionar a ideia de qualquer trajetória, estes parametros
    #tem de estar dentro do arquivo, ou adicionar uma função que retorna os valores.
    TIME_DEFAULT_TICK = 0.02 #seconds
    SIMULATION_LENGHT = 20.00 #seconds

    def __init__(self, robot):
        self.current_time = 0
        self.robot = robot
    
    #dispara a simulação e caso o robo "diga" que tem a bola, a simulação é parad que tem a bola, a simulação é paradaa
    def simulate(self):
        while self.current_time < self.SIMULATION_LENGHT:
            if self.robot.get_ball():
                break
            self.current_time += self.TIME_DEFAULT_TICK
        return self.robot
