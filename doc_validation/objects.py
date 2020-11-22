import time


class CPF:
    @staticmethod
    def validate(cpf: str) -> str:
        v1 = 0
        v2 = 0
        for i in range(0, len(cpf)):
            v1 = v1 + int(cpf[i]) * (9 - (i % 10))
            v2 = v2 + int(cpf[i]) * (9 - ((i + 1) % 10))
        v1 = (v1 % 11) % 10
        v2 = v2 + v1 * 9
        v2 = (v2 % 11) % 10
        return f'{v1}{v2}'

class CNPJ:
    @staticmethod
    def validate(str_cnpj: str) -> str:
        cnpj = [int(d) for d in str_cnpj]
        v1 = 0
        v2 = 0
        v1 = 5*cnpj[0] + 4*cnpj[1]  + 3*cnpj[2]  + 2*cnpj[3]
        v1 += 9*cnpj[4] + 8*cnpj[5]  + 7*cnpj[6]  + 6*cnpj[7]
        v1 += 5*cnpj[8] + 4*cnpj[9] + 3*cnpj[10] + 2*cnpj[11]
        v1 = 11 - v1 % 11
        if v1 >= 10:
            v1 = 0

        v2 = 6*cnpj[0] + 5*cnpj[1]  + 4*cnpj[2]  + 3*cnpj[3]
        v2 += 2*cnpj[4] + 9*cnpj[5]  + 8*cnpj[6]  + 7*cnpj[7]
        v2 += 6*cnpj[8] + 5*cnpj[9] + 4*cnpj[10] + 3*cnpj[11]
        v2= 2*v1
        v2 = 11 - v2 % 11
        if v2 >= 10:
            v2 = 0

        return f'{v1}{v2}'


class Operator:
    def __init__(self, cpf_validator: CPF, cnpj_validator: CNPJ, dataset: list):
        self.cpf_validator = cpf_validator
        self.cnpj_validator = cnpj_validator
        self.cpf_digits = []
        self.cnpj_digits = []
        self.dataset = dataset
        self.completion = 0.0

    def operate(self):
        for i in range(0, len(self.dataset)):
            if len(self.dataset[i]) == 9:
                self.cpf_digits.append(self.cpf_validator.validate(self.dataset[i]))
            elif len(self.dataset[i]) == 12:
                self.cnpj_digits.append(self.cnpj_validator.validate(self.dataset[i]))
            self.completion = (i+1)/len(self.dataset)

    def get_results(self) -> str:
        return f'{str(self)} | Calculated CPF Digits: {len(self.cpf_digits)} | Calculated CNPJ Digits: {len(self.cnpj_digits)} | Total processed: {self.completion}'


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
    
    def show_report(self):
        total_cpf_analyzed = 0
        total_cnpj_analyzed = 0
        for operator in self.operators:
            total_cnpj_analyzed += len(operator.cnpj_digits)
            total_cpf_analyzed += len(operator.cpf_digits)
        print(f'On this execution a total of {total_cnpj_analyzed} CNPJs has been analyzed')
        print(f'On this execution a total of {total_cpf_analyzed} CPFs has been analyzed')
        print(f'A total of {total_cpf_analyzed + total_cnpj_analyzed} document numbers has been analyzed')