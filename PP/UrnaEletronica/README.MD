#Descrição
Uma urna eletrônica permite a realização de uma votação a nível nacional para presidente (neste caso).

O seu trabalho será desenvolver um aplicativo para uma urna eletrônica de uma determinada seção, seguindo as seguintes recomendações:

- Antes da votação, todos os eleitores daquela seção bem como os candidatos deverão ser cadastrados na urna.

- No início da votação, o seu aplicativo deverá entrar no modo de votação, em que uma tela aparecerá solicitando o número do título do eleitor atual.

- Após digitar o número, deverá ser mostrado o nome e as informações do eleitor atual bem como a lista de candidatos para votação.

Ao selecionar um candidato, deverá ser solicitada uma confirmação.


Após a confirmação, a tela de fim de votação aparecerá com um botão para iniciar a próxima votação.


Ao final da eleição, o sistema deverá mostrar os resultados da urna atual incluindo estatísticas com porcentagens.


Considere uma votação com apenas um turno.

Funcionalidades Mínimas

    [X]Gerenciamento de eleitores
        [X] cadastro,
        [X] consulta,
        [X] alteração,
        [X] remoção
        
    [X]Gerenciamento de candidatos
        [X] cadastro,
        [X] consulta,
        [X] alteração,
        [X] remoção

    [X]Inicialização da urna e o término da votação devem ser feitos utilizando uma senha.
        [X]Inicialização
        [X]Termino
    [X]Somente a pessoa credenciada pelo sistema tem a senha de início e término da eleição.
    [X]Cada eleitor pode votar uma única vez, utilizando seu titulo de eleitor.
    [X]O eleitor pode votar em branco ou nulo.

#Como rodar subir o Banco

## Subir o docker

```
docker-compose up -d

```

## Rodando as migrations com FlyWay

É necessário criar as migrações dentro de `app/src/main/java/urnaeletronica/BackEnd/Migration`. Acesse a [documentação](https://documentation.red-gate.com/fd/migrations-184127470.html) para verificar como criar a sua:



```
 ./gradlew flywayMigrate    
```

## Rodando a aplicacao

``` 
./gradlew run
```