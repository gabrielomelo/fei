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

    @staticmethod
    def operate(dataset: list) -> object:
        cpf_digits = []
        cnpj_digits = []

        for i in range(0, len(dataset)):
            if len(dataset[i]) == 9:
                cpf_digits.append(CPF.validate(dataset[i]))
            elif len(dataset[i]) == 12:
                cnpj_digits.append(CNPJ.validate(dataset[i]))
        return len(cpf_digits), len(cnpj_digits)        


class Zookeeper:

    @staticmethod    
    def show_report(results):
        total_cpf_analyzed = 0
        total_cnpj_analyzed = 0
        for result in results:
            total_cnpj_analyzed += result[0]
            total_cpf_analyzed += result[1]
        print(f'On this execution a total of {total_cnpj_analyzed} CNPJs has been analyzed')
        print(f'On this execution a total of {total_cpf_analyzed} CPFs has been analyzed')
        print(f'A total of {total_cpf_analyzed + total_cnpj_analyzed} document numbers has been analyzed')
