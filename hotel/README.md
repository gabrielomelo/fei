# Sistema de gerenciamento de reservas

O sistema neste repositório foi construído para a matéria CC4652, este conta
com o propósito de ser um sistema de gerenciamento de reservas para um hotel.

## Arquitetura

No inicio pensei em fazer um sistema que manipulasse dados na interface e
os mandava para um banco de dados, entretanto, como a proposta era usar estruturas
próprias, pensei em realizar um mock. Cada uma das estruturas pode ser vista como um
pequeno banco de dados, e seu papel era ser a mais adequada para o serviço.

* A hash foi usada pensando na quantidade de quartos, e no fato de que eles seriam
fixos. As reservas estariam crescendo lateralmente porém teriam um número limitado.
Idealizei uma tabela tridimensional, onde teriamos o número do quarto como 1D, mês como 2D
e reserva como 3D, sendo esta uma LES com 30 espaços. (A segunda dimensão seria
 substiuída anualmente).

* A LES é um componente de outra estrutura.

* A LDE foi usada em um contexto dinâmico. Os titulares podem ser infinitos e
 esses estão vinculados a uma reserva, que por sua vez está vinculada a um quarto.

* A Queue foi feita para ser uma fila, para ser exatamente uma fila, sem abstração.
Ela foi feita para gerenciar requisições em uma segunda thread.

A aplicação possui inumeras threads inicializadas pela JVM, porém, 2 que eu defini.
São essas:

* Principal: Lida com a interface e todas as threads que são instanciadas nela são
sincronizada. (interface, dados e eventos).

* Serviços: Ela processa objetos de serviço que possuem um trigger apenas. 
Ela é assincrona com a principal, já que caso ocorra um lançamento de exceção
 a aplicação principal continue.

Uma ideia que poderia ter sido implementada é a utilização de lotes na execução de serviços.
Esse objeto cuidaria para que não existisse duplicatas e possuiria a referencia de uma thread
 onde esta por sua vez, emitiria sinais. e armazenariam no máximo de 25% de 75% dos quartos.
Os 25% seriam um valor padrao de carga, já que cada serviço seria designado em uma dashboard
para um usuário do sistema (funcionario).

OBS: a arquitetura foi pensada em um sistema que muitas pessoas utilizariam ao mesmo tempo
via web e rodando em um servidor.

## Requisitos

Recomento rodar no terminal para visualizar o log da pilha de serviço.
Além disso é necessário possuir a jvm 1.8.0 para rodar sem problemas.

## Pacotes

Os pacotes são separados por tipos de classes e não por responsabilidade.

* DAO - Data Access Objects
* Service - Interface, runnable e scheduler.
* Util - Classes estáticas que realizam algo.
* entity - São os objetos manipulados.
* structures - Estruturas de dados
* view - Forms do swing e controllers (carregam os forms e capturam os eventos).

## Estruturas

Das estruturas ensinadas em sala, foram usadas 4, o que confere com a descrição
do projeto. Elas são:

* LDE - Lista Dinâmica Encadeada.
* LES - Lista Encadeada Sequêncial.
* Queue - Fila (implementada com LDE).
* Hash - Implementada com LES (Necessitava de algo rápido e sequencial).


## Usando o Sistema

O sistema é bem precário quando se trata de UX, mas creio que fiz algumas coisas
interessantes no backend. Gostaria de ressaltar que todo esse código foi feito para
fins de estudo e nenhum padrão de projeto foi seguido a risca além do MVC.

Quando entrar no sistema irá se deparar com a tela inicial:

<img src="https://raw.githubusercontent.com/gabrielomelo/hotel/master/docs/img/main_menu.PNG" height= "400" width="800">


Que contém os seguintes componentes:

* File
    ** Exit - Saí do programa e encerra todos os serviços.
    ** About - Mostra as informações do desenvolvedor.
* Buscar Reserva - Procura reservas relativas ao número do quarto digitado.
* Criar Reserva - Formulário de criação de reservas.
* Criar Titular - Formulário de criação de titular.
* Modificar Reserva - Formulário de alteração de reserva.
* Check-in & Check-out - Adicionam uma reserva na fila executar a alteração.
* Recarrega Tabela - Botão para recarregar a view.

### Criando Reservas

É a unica operação que necessita de um pipeline, caso este não seja seguido
não haverá o registro da reserva. Segue ele:

1. Caso não exista, criar o usuário desejado.
2. Vincular o usuário a reserva clicando na "?" do formulário de criação.
3. Terminar de preencher o formulário e clicar em "Criar".

## Autor

Gabriel Filipe da Silva Melo. RA: 22217015-1.

OBS: Veja commits para mais detalhes.

## Legal

A licença desde projeto é GPL v3 veja arquivo para mais detalhes.
