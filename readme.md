## Protocolos API

Projeto teste para CVC que consiste em uma API de busca de hoteis retornando valores para estadia referente as datas de checkin e checkout


### Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Git](https://git-scm.com)
- [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Spring](https://spring.io/)


### Pré-requisitos

Antes de começar, você vai precisar ter instalado em sua máquina as seguintes ferramentas: [Git](https://git-scm.com), [Postgres](https://www.postgresql.org/download/) e [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
Além disto é bom ter um editor para trabalhar com o código como [IntelliJ](https://www.jetbrains.com/pt-br/id) ou [VSCode](https://code.visualstudio.com/).

### Setup

1. Instalar o [Java 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html).
2. Configurar a variavel de ambiente **JAVA_HOME** para a pasta de instalação da sua versão do JDK.

## Endpoint

### Para usar o endpoint de busca siga o exemplo:

* GET Hotel: /hotel <br>
Este endpoint retorna a lista de hoteis com orçamento para a estadia baseado na quantidade de dias entre checkin e checkout. Caso seja preenchido no formulário o atributo "hotelId" o endpoint retorna apenas o hotel cujo o id seja equivalente, com as informações de valores referente a ele.

* Exemplo de data para o Endpoint: 
    {
        "cityId": 1032,
        "checkInDate": "2022-06-25",
        "checkOutDate": "2022-06-30",
        "numberOfAdults": 1,
        "numberOfChilds": 0,
        "hotelId": ""
    }
}

## Testes unitários

Foram feitos alguns testes unitários, eles se encontram na pasta test.
