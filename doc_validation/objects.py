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
                print(f'Operator {i+1} - {self.operators[i].get_results()}')
    
    def complete(self):
        for operator in self.operators:
            if operator.completion < 1.0:
                return False
        return True

