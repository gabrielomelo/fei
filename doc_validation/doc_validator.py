import threading
import argparse
import traceback
import math
from objects import Zookeeper, Operator, CNPJ, CPF


def load_dataset(file_path: str) -> list:
    with open(file_path, 'r') as fp:
        return [line.strip() for line in fp.read().splitlines()]


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('-jobs', '-j', type=int, help="Number of the jobs used in the dataset validation. Use values multiples by 2 or 1.", default=1)
    parser.add_argument('-path', '-p', type=str, help="Dataset file path.")
    args = parser.parse_args()
    
    cpf_validator = CPF()
    cnpj_validator = CNPJ()
    
    dataset = load_dataset(args.path)

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

    zookeeper.show_report()
