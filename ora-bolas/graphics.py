"""
    Author: Gabriel Melo
    License: GPL3.0
    For more instructions check: https://github.com/gabrielomelo
"""

import matplotlib.pyplot as plt

class Graphics(object):

    #configuracoes graficas
    DPI = 100
    HEIGHT = 8
    WIDTH = 10
    LINE_WIDTH = 4
    LINE_TYPE = "-"

    def time_trajectory(self, time, x_bola, y_bola, x_robo, y_robo, label):
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(self.fetch_size(time, x_bola), x_bola, color = "blue", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x bola")
        plt.plot(self.fetch_size(time, y_bola), y_bola, color = "red", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y bola")
        plt.plot(self.fetch_size(time, x_robo), x_robo, color = "green", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x robô")
        plt.plot(self.fetch_size(time, y_robo), y_robo, color = "yellow", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y robô")
        plt.ylabel("Displacement(m)")
        plt.xlabel("Time(s)")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)

    def trajectory(self, x_bola, y_bola, x_robo, y_robo, label): 
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(x_bola, y_bola, color = "blue", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "Bola")
        plt.plot(x_robo, y_robo, color = "red", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "Robô")
        plt.ylabel("Y")
        plt.xlabel("X")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)

    def velocity_components(self, time, x_bola, y_bola, x_robo, y_robo, label):
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(self.fetch_size(time, x_bola), x_bola, color = "blue", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x bola")
        plt.plot(self.fetch_size(time, y_bola), y_bola, color = "red", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y bola")
        plt.plot(self.fetch_size(time, x_robo), x_robo, color = "green", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x robô")
        plt.plot(self.fetch_size(time, y_robo), y_robo, color = "yellow", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y robô")
        plt.ylabel("Velocity(m)")
        plt.xlabel("Time(s)")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)
    
    def aceleration_components(self, time, x_bola, y_bola, x_robo, y_robo, label):    
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(self.fetch_size(time, x_bola), x_bola, color = "blue", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x bola")
        plt.plot(self.fetch_size(time, y_bola), y_bola, color = "red", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y bola")
        plt.plot(self.fetch_size(time, x_robo), x_robo, color = "green", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "x robô")
        plt.plot(self.fetch_size(time, y_robo), y_robo, color = "yellow", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "y robô")
        plt.ylabel("Aceleration(m)")
        plt.xlabel("Time(s)")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)
    
    def module(self, time, module, identifier, label):
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(self.fetch_size(time, module[0]), module[0], color = "red", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "robô")
        plt.plot(self.fetch_size(time, module[1]), module[1], color = "blue", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "bola")
        plt.ylabel(identifier)
        plt.xlabel("Time(s)")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)

    def distance(self, time, distance, label): 
        plt.figure(figsize = (self.WIDTH, self.HEIGHT), dpi = self.DPI)
        plt.plot(self.fetch_size(time, distance), distance, color = "orange", linewidth = self.LINE_WIDTH, linestyle = self.LINE_TYPE, label = "distance")
        plt.ylim(ymin=0)
        plt.xlim(xmin=0)
        plt.ylabel("Distance(m)")
        plt.xlabel("Time(s)")
        plt.legend(loc = "upper left", frameon = False)
        plt.title(label)
    
    #dimensiona a primeira lista a partir da segunda
    def fetch_size(self, vector, size):
        temp = []
        for i in range(len(size)):
            temp.append(vector[i])
        return temp
    
    #dispara a apresentacao
    def show(self):
        plt.show()

