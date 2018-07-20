# Ora Bolas!

Embora tenha um nome peculiar, o repositório contém códigos bem sérios.
O objetivo principal é calcular a rota de um robô qualquer até a bola, que possui movimento definido por um arquivo "dat" localizado na raíz do projeto.

Autor: Gabriel Melo -> Desenvolvedor da solução matemática e código fonte.

Licença:  GPL V3

## Dependências do projeto.

    * Python 3.6 -> Pode ser instalado ou executado com o WinPython.
    * Matplotlib -> Pode ser instalado usando o pip3 ou executado usando o WinPython.

## Orientações para execução do programa.
    
    1. Entrar no diretório do projeto.
    2. Executar com Python3 o arquivo ora-bolas.py obrigatóriamente pelo CMD.
    3. Fornecer os argumentos na ordem e quantidade indicada.
    4. Aguardar o carregamento da interface gráfica (Qt5).

## Responsabilidade por arquivo.

    * ora-bolas.py -> é o script de entrada, contém o processo principal.
    * field.py -> responsável por controlar a passagem de tempo.
    * robot.py -> responsável por realizar os cálculos e encontrar a bola <- A física acontece aqui.
    * graphics.py -> responsável por manipular a biblioteca de gráficos.
    * ball_trajectory.dat -> arquivo com as posições da bola.

### Observações.

    * Os comentários acima de cada função tem objetivo de guiar o professor durante minha avaliação.
    * O programa não receberá mais atualizações, e não será liberado antes do término do período de avaliação.

## Comentários desta versão.

    * A diferença vista no gráfico de velocidade da bola ("barriguinhas") não foi corrigida, verifiquei os cálculos e creio que estão certos,
    há a possibilidade de serem as mudanças de direção ou simplesmente decorrente do deslocamento para y positivo, já que a aceleração
    é calculada usando esta velocidade e não possui erros. Agradeceria se pudesse ter um feedback caso a professora ache algo que deixei passar.
    * O deslocamento do robô foi ajustado e pode ser usado QUALQUER ponto no plano. Utilizei a atan2 ao contrário da atan, entretanto,
    devo ressaltar que a função que usei durante a apresentação possui uma taxa de acerto boa, e era muito semelhante a atan2.
    * A função de zoom pode não funcionar dependendo do computador, como o do laboratório, por exemplo.
    * O raio de interceptação fica a sua escolha, mas recomendo usar algo entre 0.10 e 0.20, por ser mais próximo do real.
    * Comentários no código estão em portugues e sem acentos por questao de estilo e interoperabilidade entre editores.
    * Aumentei a grossura dos traços no gráfico para uma melhor visualização.
