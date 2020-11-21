import threading
import argparse
import traceback

from validate_docbr import CNPJ, CPF


class Operator:
    def __init__(self, cpf_validator: CPF, cnpj_validator: CNPJ):
        self.cpf_validator = cpf_validator
        self.cnpj_validator = cnpj_validator

    def operate(self, dataset: list) -> list:
        return []

    def get_results() -> str:
        return 'here goes the thread status'


class Zookeeper:
    def __init__(self, operators: list):
        self.operators = operators
    
    def show_statuses(self):
        for operator in self.operators:
            print(operator.get_results())


def load_dataset(file_path: str) -> list:
    with open(file_path, 'r') as fp:
        return fp.read().splitlines()

def perform_operation(numbers: list) -> bool:
    pass

if __name__ == "__main__":
        
    parser = argparse.ArgumentParser()
    parser.add_argument('-jobs', '-j', type=int, help="Number of the jobs used in the dataset validation.", default=1)
    parser.add_argument('-path', '-p', type=str, help="Dataset file path.")
    args = parser.parse_args()
    
    dataset = load_dataset(args.path)

    print(args)

    print(dataset)

    dataset_offset = (0, 2)
    
    """
    threads = [threading.Thread(target=perform_operation, kwargs=(dataset_offset,)) for _ in range(0, args.jobs)]

    for thread in threads:
        thread.start()
    

    #  TODO

    for thread in threads:
        thread.join()
    """
