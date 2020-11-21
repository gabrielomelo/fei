import threading
import argparse
import traceback
import math
import time
from validate_docbr import CNPJ, CPF


class Operator:
    def __init__(self, cpf_validator: CPF, cnpj_validator: CNPJ, dataset: list):
        self.cpf_validator = cpf_validator
        self.cnpj_validator = cnpj_validator
        self.valid_cpfs = 0
        self.valid_cnpjs = 0
        self.dataset = dataset
        self.completion = 0.0

    def operate(self):
        for i in range(0, len(self.dataset)):
            if self.cpf_validator.validate(self.dataset[i]):
                self.valid_cpfs += 1
            elif self.cnpj_validator.validate(self.dataset[i]):
                self.valid_cnpjs += 1
            self.completion = (i+1)/len(self.dataset)

    def get_results(self) -> str:
        return f'{str(self)} | Valid CPFs: {self.valid_cpfs} | Valid CNPJs: {self.valid_cnpjs} | Total processed: {self.completion}'


class Zookeeper:
    def __init__(self, operators: list):
        self.operators = operators
    
    def show_statuses(self):
        while not self.complete():
            time.sleep(1)
            for i in range(0, len(self.operators)):
                print(f'Operator {i+1} - ' + self.operators[i].get_results())
    
    def complete(self):
        for operator in self.operators:
            if operator.completion < 1.0:
                return False
        return True



def load_dataset(file_path: str) -> list:
    with open(file_path, 'r') as fp:
        return [line.strip() for line in fp.read().splitlines()]


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('-jobs', '-j', type=int, help="Number of the jobs used in the dataset validation. Please use pair values multiples by 2.", default=1)
    parser.add_argument('-path', '-p', type=str, help="Dataset file path.")
    args = parser.parse_args()
    
    cpf_validator = CPF()
    cnpj_validator = CNPJ()
    
    dataset = load_dataset(args.path)

    print(dataset[0:100])

    partition_size = math.floor(len(dataset)/args.jobs)

    operators = [
        Operator(
            cpf_validator,
            cnpj_validator,
            dataset[((i-1)*partition_size):(i*partition_size)]
            ) for i in range(1, args.jobs + 1)
        ]

    zookeeper = Zookeeper(operators)
    
    operators_threads = [threading.Thread(target=operator.operate, daemon=True) for operator in operators]
    
    for thread in operators_threads:
        thread.start()
        
    zookeeper.show_statuses()

    for thread in operators_threads:
        thread.join()
