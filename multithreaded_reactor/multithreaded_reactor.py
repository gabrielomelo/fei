import threading
import time
import random
import math


class Component:

    def run(self) -> str:
        """
        Component process description
        :return: string containing component execution information
        """
        pass


class Tank(Component):

    def __init__(self, fill_ratio, material_name):
        self.fill_ratio = fill_ratio
        self.material_qty = 0.0
        self.material_name = material_name

    def run(self):
        if self.fill_ratio is not None:
            self.fill()

    def load(self, fluid_qty: float):
        self.material_qty += fluid_qty

    def fill(self):
        if isinstance(self.fill_ratio, tuple):
            self.material_qty += random.uniform(self.fill_ratio[0], self.fill_ratio[1])
        elif isinstance(self.fill_ratio, float):
            self.material_qty += self.fill_ratio


class Dryer(Component):
    def __init__(self, diesel_tank: Tank):
        self.fluid_qty = 0
        self.diesel_tank = diesel_tank

    def load(self, qty):
        self.fluid_qty += qty

    def run(self):
        if self.fluid_qty >= 1.0:
            self.fluid_qty -= 1.0
            self.diesel_tank.load(0.985)
            time.sleep(3)  # sleep for 3 secs


class Decanter(Component):
    def __init__(self, dryer: Dryer, etoh_tank: Tank):
        self.fluid = 0
        self.dryer = dryer
        self.etoh_tank = etoh_tank
        self.glycerin_total = 0
        self.solution_ceil = 10  # 10 liters
    
    def run(self):
        self.glycerin_total += self.fluid * 0.05
        self.etoh_tank.load(self.fluid * 0.13)
        self.dryer.load(self.fluid * 0.82 * math.pow(0.95, 3))  # sim the whashing tanks behavior
        self.fluid = 0

    def set_fluid(self, fluid_qty):
        if (self.fluid + fluid_qty) <= self.solution_ceil:
            self.fluid += fluid_qty


class Reactor(Component):

    REACTOR_CONFIG = {
        'PROC_LIMIT': 5,
        'COOLDOWN': 5,
        'PROPORTION': {
            'NAOH': 0.25,
            'ETOH': 0.5,
            'OIL': 0.25
        }
    }

    def __init__(self, oil_tank: Tank, naoh_tank: Tank, etoh_tank: Tank, decanter: Decanter):
        self.oil_tank = oil_tank
        self.naoh_tank = naoh_tank
        self.etoh_tank = etoh_tank
        self.decanter = decanter
        self.oil_prop = Reactor.REACTOR_CONFIG['PROC_LIMIT'] * Reactor.REACTOR_CONFIG['PROPORTION']['OIL']
        self.naoh_prop = Reactor.REACTOR_CONFIG['PROC_LIMIT'] * Reactor.REACTOR_CONFIG['PROPORTION']['NAOH']
        self.etoh_prop = Reactor.REACTOR_CONFIG['PROC_LIMIT'] * Reactor.REACTOR_CONFIG['PROPORTION']['ETOH']

    def run(self) -> str:
        if self.verify_tanks(): # if all the materials pass the threshold test
            self.oil_tank.material_qty -= self.oil_prop
            self.naoh_tank.material_qty -= self.naoh_prop
            self.etoh_tank.material_qty -= self.etoh_prop
            self.decanter.set_fluid(Reactor.REACTOR_CONFIG['PROC_LIMIT'])
            time.sleep(Reactor.REACTOR_CONFIG['COOLDOWN'])
            
    def verify_tanks(self) -> bool:
        try:
            return self.oil_tank.material_qty >= self.oil_prop and\
                self.naoh_tank.material_qty >= self.naoh_prop and\
                self.etoh_tank.material_qty >= self.etoh_prop
        except ZeroDivisionError:
            return False


def control_exec_time_thread(component: Component, sleep_time: int):
    global total_time
    global max_time

    while total_time < max_time:
        time.sleep(sleep_time)
        component.run()


if __name__ == "__main__":
    NAOH_RATIO = 0.3
    ETOH_RATIO = 0.1
    OIL_RATIO = (1, 2)

    global total_time
    global max_time

    total_time = 0
    max_time = 3600
    
    oil_tank = Tank(OIL_RATIO, 'oil')
    naoh_tank = Tank(NAOH_RATIO, 'naoh')
    etoh_tank = Tank(ETOH_RATIO, 'etoh')
    diesel_tank = Tank(None, 'diesel')

    dryer = Dryer(diesel_tank)
    decanter = Decanter(dryer, etoh_tank)
    reactor = Reactor(oil_tank, naoh_tank, etoh_tank, decanter)
    
    threads = dict()
    threads['oil'] = threading.Thread(target=control_exec_time_thread, args=(oil_tank, 5))  # runs every 5 secs
    threads['naoh'] = threading.Thread(target=control_exec_time_thread, args=(naoh_tank, 1))  # runs every 1 secs
    threads['etoh'] = threading.Thread(target=control_exec_time_thread, args=(etoh_tank, 1))  # runs every 1 secs
    threads['diesel'] = threading.Thread(target=control_exec_time_thread, args=(diesel_tank, 1))  # runs every 1 secs
    threads['reactor'] = threading.Thread(target=control_exec_time_thread, args=(reactor, 1))  # runs every 1 secs
    threads['decanter'] = threading.Thread(target=control_exec_time_thread, args=(decanter, 1))  # runs every 1 secs
    threads['dryer'] = threading.Thread(target=control_exec_time_thread, args=(dryer, 1))  # runs every 1 secs

    for item, value in threads.items():
        value.start()

    while total_time <= max_time:
        time.sleep(1)
        print(f"Elapsed execution time: {total_time} seconds")
        total_time += 1

    for item, value in threads.items():
        value.join()

    print(f"Quantidade de Biodiesel produzida: {diesel_tank.material_qty}")
    print(f"Quantidade de Glicerina produzida: {decanter.glycerin_total}")
    print(f"Volume de Óleo restante: {oil_tank.material_qty}")
    print(f"Volume de NaOH restante: {naoh_tank.material_qty}")
    print(f"Volume de EtOh restante: {etoh_tank.material_qty}")
    print('done!')
