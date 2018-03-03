import pygame, random, sys
from pygame.locals import *

# CLASSE DO PERSONAGEM


class Character(pygame.Rect):

    def __init__(self, x, y, img):
        pygame.Rect.__init__(self, x, y, 30, 30)
        self.xi = (x - 30)
        self.yi = (y - 30)
        self.x = x
        self.y = y
        self.img = img

    def render(self, ctx):
        ctx.blit(self.img, (self.xi, self.yi))

    def has_collide(self, obj):
        pass

    def left(self, velocity):
        self.x += -velocity
        self.xi += -velocity

    def right(self, velocity):
        self.x += velocity
        self.xi += velocity

    def up(self, velocity):
        self.y += -velocity
        self.yi += -velocity

    def down(self, velocity):
        self.y += velocity
        self.yi += velocity

# CLASSE DO UFO


class Ufo(Character):
    def __init__(self, x, y, img):
        Character.__init__(self, x, y, img)
        self.behavior = random.randint(1, 2)
        self.speed = random.randint(5, 14)

# CLASSE DA VACA


class Cow(Character):
    def __init__(self, x, y, img):
        Character.__init__(self, x, y, img)

    def shoot(self):
        return Cow(self.x, self.y, self.img)

# CLASSE DO JOGO


class Game(object):

    # ENV. VARIABLES
    pygame = pygame
    ufos = []
    cows = []
    UFOS_QTD = 6
    MIN_UFOS_QTD = 3
    # DISPLAY SETTINGS
    LABEL = "Lost on Earth 3 - The revenge of the Cows"
    HEIGHT = 480
    WIDTH = 640
    REFRESH_RATE = 60
    INSTRUCTIONS = """
        BEM VINDO AO LOST ON EARTH 3 - REVENGE OF THE COWS.
        PARA JOGAR USE OS SEGUINTES COMANDOS:
        SETA PARA DIREITA - MOVA A VAQUINHA PARA A DIREITA
        SETA PARA ESQUERDA - MOVA A VAQUINHA PARA A ESQUERDA
        BARRA DE ESPAÇO - ATIRE VACAS CONTRA OS DISCOS VOADORES
        
        O OBJETIVO É ACERTAR O MÁXIMO DE DISCOS EM 1 MINUTO
        
        Developed by Gabriel Melo - https://github.com/gabrielomelo
    """
    # SPRITES
    BACKGROUND_IMG_PATH = "imgs/background.png"
    UFO_IMG_PATH = "imgs/ufo_default.png"
    COW_IMG_PATH = "imgs/cow_default.png"

    # Construtor que carrega todas as dependencias do jogo

    def __init__(self):
        self.pygame.init()
        self.start_time = self.pygame.time.get_ticks()
        self.ctx = self.pygame.display.set_mode((self.WIDTH, self.HEIGHT))
        self.background = self.pygame.image.load(self.BACKGROUND_IMG_PATH).convert_alpha()
        self.ufo_sprite = self.pygame.image.load(self.UFO_IMG_PATH).convert_alpha()
        self.cow_sprite = self.pygame.image.load(self.COW_IMG_PATH).convert_alpha()
        self.pygame.display.set_caption(self.LABEL)
        self.clock = pygame.time.Clock()
        self.score = 000
        self.state = True
        print(self.INSTRUCTIONS)
        for i in range(self.UFOS_QTD):
            self.ufos.append(
                Ufo(random.randint(1, self.WIDTH), random.randint(1, ((self.HEIGHT/8)*5)), self.ufo_sprite)
                )
        self.master_of_cows = Cow((self.WIDTH/2), ((self.HEIGHT/8)*7), self.cow_sprite)
        self.loop()

    # Função que contém o loop com a logica de execução do jogo

    def loop(self):
        while self.state:
            self.update()
            self.clock.tick(self.REFRESH_RATE)
        self.pygame.quit()
        sys.exit()

    # Função que atualiza o estado do jogo

    def update(self):
        self.seconds = (self.pygame.time.get_ticks() - self.start_time)/1000
        self.event_handler()

        for i in range(len(self.ufos)):
            if self.ufos[i].x > self.WIDTH:
                self.ufos[i].x = 0
                self.ufos[i].xi = (self.ufos[i].x - 30)
            elif self.ufos[i].x < 0:
                self.ufos[i].x = self.WIDTH
                self.ufos[i].xi = (self.ufos[i].x - 30)
            if self.ufos[i].y > self.HEIGHT:
                self.ufos[i].y = 0
                self.ufos[i].yi = (self.ufos[i].y - 30)
            elif self.ufos[i].y < 0:
                self.ufos[i].y = self.HEIGHT
                self.ufos[i].yi = (self.ufos[i].y - 30)
            if self.ufos[i].behavior == 1:
                self.ufos[i].right(self.ufos[i].speed)
            elif self.ufos[i].behavior == 2:
                self.ufos[i].left(self.ufos[i].speed)

        for i in range(len(self.cows)):
            self.cows[i].up(8)

        for i in range(len(self.ufos)):
            for j in range(len(self.cows)):
                if self.cows[j].colliderect(self.ufos[i]):
                    self.ufos.pop(i)
                    self.cows.pop(j)
                    self.add_points()
                    return

        if len(self.ufos) < self.MIN_UFOS_QTD:
            self.ufos.append(Ufo(random.randint(1, self.WIDTH), random.randint(1, ((self.HEIGHT/8)*5)), self.ufo_sprite))

        if self.master_of_cows.x > self.WIDTH:
            self.master_of_cows.x = 0
            self.master_of_cows.xi = (self.master_of_cows.x - 30)
        elif self.master_of_cows.x < 0:
            self.master_of_cows.x = self.WIDTH
            self.master_of_cows.xi = (self.master_of_cows.x - 30)

        self.garbage_collector()
        self.ctx.blit(self.background, (0, 0))
        self.render_characters(self.ctx)
        self.pygame.display.update()

    # Função que renderiza os personagens

    def render_characters(self, ctx):
        for i in range(len(self.ufos)):
            self.ufos[i].render(ctx)
        for j in range(len(self.cows)):
            self.cows[j].render(ctx)
        self.master_of_cows.render(ctx)
        self.update_score(self.score, self.ctx)
        self.countdown(self.ctx)

    # Função que aumenta o placar

    def add_points(self):
        self.score += 100

    # Função que captura os eventos

    def event_handler(self):
        for event in self.pygame.event.get():
            if event.type == pygame.QUIT:
                self.state = False
            if event.type == KEYDOWN:
                if event.key == K_LEFT:
                    self.master_of_cows.left(25)
                if event.key == K_RIGHT:
                    self.master_of_cows.right(25)
                if event.key == K_SPACE:
                    self.cows.append(self.master_of_cows.shoot())

    # Função "Garbage Collector"

    def garbage_collector(self):
        for i in range(len(self.cows)):
            if (self.cows[i].y > self.HEIGHT or self.cows[i].y < 0)\
                    or (self.cows[i].x > self.WIDTH or self.cows[i].x < 0):
                self.cows.pop(i)
                return

    # Função que renderiza o placar "corrente" no contexto do jogo

    def update_score(self, score, ctx):
        self.msg = "SCORE: %d" % score
        self.font = pygame.font.Font(None, 36)
        self.text = self.font.render(self.msg, True, Color("white"))
        ctx.blit(self.text, ((self.WIDTH/14), (self.HEIGHT/14)))

    # Função que renderiza o tempo restante

    def countdown(self, ctx):
        if self.seconds >= 60:
            self.state = False
            print("SUA PONTUAÇÃO FOI DE %d" % self.score)
        self.font = pygame.font.Font(None, 36)
        self.text = self.font.render("TIME: %i" % (60 - self.seconds), True, Color("white"))
        ctx.blit(self.text,
                 ((self.WIDTH/8), (self.HEIGHT/8))
                 )

Game()
