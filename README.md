# Organização do projeto
O projeto esta organizado em pacotes, com possíveis formas de uso do RabbitMQ
- Sender/Receive
  
  Configura o envio direto, sem o uso de Exchange ou filas personalizadas.
- WorkQueue
  
  Configura o uso de filas nomeadas, 2 consumidores, simulando o roud-robin, o comportamento padrão do Rabbitmq quando existe 
mais de um consumidor pra fila.E diferentes configurações de ack.
- PubSub
  
  Criação de uma Exchange, com 2 filas, uma temporaria, criado com nome aleatório e uma permanente, com nome escolhido pelo desenvolvedor.


# Teoria
## Mensageria

O que é um sistema de mensageria?

- Computação distribuída
    - Espalhados, com coordenação de maneira que o usuário final enxerga como um sistema único.
- Comunicação orientada a mensagem;
- Comunicação **ASSÍNCRONA**
    - Trasientes
        - Envio uma mensagem e já encerro a comunicação, não espero concluir o processamento.
    - Persistentes
        - Uma vez que se inicia uma comunicação, envio a mensagem,  eu só encerro quando concluir.
- Clusterizado
- Tolerante a falhas
- Escalável

## Comunicação Síncrona

Quando 2 sistemas se comunicam, e um fica offline e um evento é perdido

## Middleware Service

Sistema de comunicação orientado a mensageria

## Comunicação  do sistema

- Conexões (Protocolos... AMQP 0.9.1)
- Roteamento e Armazenamento

# Protocolo AMQP

O Protocolo busca trazer uma **interoperabilidade** entre clientes e servidores. Busca permitir que os devs *~~(usuários em geral )~~*  adotem uma **padronização** e com isso  **integrar** serviços e sistemas  de maneira menos custosa.

- Binário
    - Produtor/Publisher
    - Consumidor/Subscriber
- Framework de semântica
    
    O framework vai definir os papéis
    
- Microserviço

# Modelo AMQ

Defini a semântico do servidor,  definindo os papéis e permissões. Uma vez que os sistemas estão seguindo um modelo, ele garante também a interoperabilidade entre sistemas.

Quando as regras são bem definidas tudo fica claro o que cada ator pode fazer e qual ação deverá ser realizada ( enviar para uma ou mais filas) .

## Camadas do protocolo

- Camada Funcional
    - Definição de conjunto de comandos
- Camada Transporte
    - Tratamento de erros, transporte, multiplexação de canais, codificação dos dados.

# RabbitMQ 🐰

Na comunicação tradicional, um *publisher* envia para o consumer,  e através do protocolo TCP, ele garante a entrega.Uma simples comunicação síncrona, se o servidor cair e a mensagem for perdida, se os dados forem sensíveis.... Temos alguns problemas:

- Tentativas consecutivas de estabelecer conexão com o servidor consumer offline.
- Conexões simultâneas, gera sistemas lentos, perdas de pacotes.

Para resolver esse tipo de problema, os sistemas de mensageria pode vir ser uma solução. O serviço  (mensenger broker) iria armazenar todas as solicitações, e em seguida o consumer iria processa-las de maneira ordenada.

**Vantagens** :

- Escrito em Erlang, leve.
- Cross-language
- Cloud friendly
- Segurança - SSL , TLS
- Autenticação
- Acks
- Gerenciamento


**Quem faz** :
- Mensagens
  - Cabeçalho
  - Payload
- Filas
  - Nome
  - Exclusividade
  - Durável
  - Auto-delete
- Routing Keys
- Bind
- Binding Keys
- Exchange


## Exchange
É o elemeto que separa a mensagem em filas seguindo critérios, faz a correspondencia e o roteamento,  seja o o critério distribuir em todas as filas, 
ou um critério mais específico onde apenas umas o outra fila recebe a mensagem.
- Fanout

  Broadcast, Não há restrição ou critério de decisão para escolha das filas, todas as mensagens que chegam, vão para todas as filas.
- Direct

  Comparação realizada entre a routing key e binding key, a combinação precisa ser **exata**.
- Topic
  
  Comparação realizada entre a routing key e binding key, a combinação é **parcial**.
   - \*   Substituição por uma única palavra;
   - \#   Substituição por zero ou mais palavras;
   - `veloz.branco.carro` => `*.*.coelho` ou  `veloz.#`
- Head

  Utiliza o cbaeçalho da mensagem como fator de decisão;
- Default
  
  Comparativo padrão: Routing key e o nome da fila;
## Quando?

- Meu cliente precisa de respostas Imediatas?
- Tempo de respostas para usuário
- Workload
- Tempo de resposta por período ocioso.
- E quando eu  crescer (refaz as perguntas) ?

# Preparando Ambiente

# Roteiro

- Instalar docker
- Instalar docker-compose
- Criar containar com imagem do RabbitMQ
- Instalar o [portainer.io](http://portainer.io) (opcional)

# Instalar docker

```bash
sudo apt-get update
sudo apt-get install docker.io

```

Testando se o docker foi instalado corretamente

```bash
sudo docker run hello-world
```

# Instalando do docker-compose

```bash
sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose
```

# Criando um container do RabbitMQ

```bash
docker pull rabbitmq
```

# [docker-compose.yml](./docker-compose.yml)
 

```docker
services:
  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq
    ports:
      - 25672:25672
      - 15672:15672
      - 5672:5672
    volumes:
      - .docker_conf/rabbitmq:/var/lib/rabbitmq
    environment:
      - RABBITMQ_DEFAULT_USER=admin
      - RABBITMQ_DEFAULT_PASS=admin
    restart: always
volumes:
  log-folder:
    name: ${log_rabbitmq_management}
    driver: local
```