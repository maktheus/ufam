
# Barbearia do Recruta Zero
A barbearia do Recruta Zero está confortavelmente instalada dentro de um forte americano nos
arredores de San Francisco, e é frequentada por oficiais, sargentos e cabos. O objetivo do trabalho é fazer com que o barbeiro atenda a todos os seus clientes seguindo os critérios que serão descritos abaixo. A implementação é uma variação do problema do “barbeiro dorminhoco”, constante dos livros texto, e deve ser feita utilizando threads e semáforos (pthreads). As regras de utilização da barbearia são as seguintes:

## Sargento Tainha
É um sargento gordo e dorminhoco que periodicamente, entre um cochilo e outro, tenta adicionar um (e somente um) novo cliente à fila de cadeiras da barbearia (buffer) caso haja lugar disponível. A periodicidade do cochilo é variável e determinada pelo usuário através da linha de comando (entre 1 e 5 segundos).

## A Barbearia
Os clientes compartilham um conjunto de 20 cadeiras com distribuição das categorias de oficiais, sargentos e cabos em três filas FIFO, sendo uma fila para cada categoria. Somente um cliente pode ser atendido de cada vez por cada um dos barbeiros (quando houver mais de um).

## Recruta Zero – O barbeiro
É o responsável pela barbearia. Cada corte de cabelo pode durar entre 4 e 6 segundos no caso de um oficial, de 2 a 4 segundos no caso de um sargento e de 1 a 3 segundos no caso de um cabo. A prioridade de atendimento será sempre: oficiais – sargentos – cabos.

## Tenente Escovinha

O General Dureza queria saber se o atendimento da barbearia está satisfatório e para tal pediu que o Tenente Escovinha fornecesse um relatório das atividades da barbearia. Para não desapontar seu superior a cada 3 segundos o tenente verifica o estado da barbearia e ao final de cada dia elabora um relatório com as seguintes informações:

    ● Estado de ocupação da(s) cadeira(s) (% por categoria e livre)
    ● Comprimento médio das filas
    ● Tempo médio de atendimento por categoria
    ● Tempo médio de espera por categoria
    ● Número de atendimentos por categoria
    ● Número total de clientes por categoria (oficiais, sargentos, cabos e pausa)

## Entrada de dados
A entrada de dados deverá ser gerada aleatoriamente seguindo o seguinte formato:
<categoria><tempo de serviço>

categoria:
 1🡺oficial 
 2🡺sargento 
 3🡺cabo
 0🡺pausa
Uma pausa pode ser inserida (categoria = 0) para indicar que não há ninguém na fila, ou seja, quando o sargento Tainha for admitir alguém para o interior da barbearia, encontrará a fila vazia. Para efeito de entrega e geração do relatório, gerar um total de 1000 registros para acesso à barbearia.

## Término da execução
Quando não houver mais ninguém esperando do lado de fora em três tentativas sequenciais do Sgt
Tainha. Neste caso o Sargento pode ir imediatamente para casa, porém o barbeiro deverá terminar o atendimento dos clientes que já estiverem no interior da barbearia.

## Resultados

Mostre o relatório gerado pelo Tenente Escovinha considerando os 3 casos abaixo:

Caso A: O recruta Zero atende as três filas, lembrando que oficiais devem ser atendidos sempre antes dos sargentos e estes antes dos cabos.

Caso B: O recruta Zero contratou Dentinho, outro barbeiro, para ajudá-lo. Os dois barbeiros atendem as três filas, lembrando que oficiais devem ser atendidos sempre antes dos sargentos e estes antes dos
cabos.

Caso C: Como a barbearia andava muito cheia, foram contratados outros 2 barbeiros, Dentinho e Otto.

Cada um dos 3 barbeiros passou a se dedicar exclusivamente a uma das filas, no entanto, caso a fila correspondente a um dos barbeiros esteja vazia, o barbeiro correspondente poderá atender clientes de outra fila, obedecendo à ordem de prioridade.

## A Entrega
    ● As implementações realizadas, com código comentado.
    ● Teste na minha sala com eventuais perguntas as duplas.