import argparse
import math
import time
from multiprocessing import Pool
from objects import Zookeeper, Operator, CNPJ, CPF


def load_dataset(file_path: str) -> list:
    with open(file_path, 'r') as fp:
        return [line.strip() for line in fp.read().splitlines()]


if __name__ == "__main__":
    parser = argparse.ArgumentParser()
    parser.add_argument('--jobs', '-j', type=int, help="Number of the jobs used in the dataset validation. Use values multiples by 2 or 1.", default=1)
    parser.add_argument('--path', '-p', type=str, help="Dataset file path.")
    args = parser.parse_args()

    initial_time = time.time()
    
    dataset = load_dataset(args.path)

    pool = Pool(processes=args.jobs)

    partition_size = math.floor(len(dataset)/args.jobs)
    partition_offset = len(dataset) - (partition_size * args.jobs)
    
    partitions = []

    for i in range(1, args.jobs + 1):
        if i == args.jobs:
            partitions.append(dataset[((i-1)*partition_size):(i*partition_size) +  partition_offset])
        else:
            partitions.append(dataset[((i-1)*partition_size):(i*partition_size)])

    results = pool.map_async(Operator.operate, partitions).get()

    pool.close()

    Zookeeper.show_report(results)

    print(f'Total Execution Time: {((time.time() - initial_time) * 1000)} miliseconds')
