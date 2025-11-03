# Sistemas Distribuídos

## Reaproveitar o trabalho de rabbitmq

- Requisição via tarefas, então eu vou setar tarefas para executar em X horário do dia
- Começar a persistir as entidades de Luz, ar-condicionado, portão no banco de dados para conseguir definir luzes, ar-condicionado ou portões específicos para ligar/desligar
- Comunicação com API externa: depois de X números de luzes ou ar-condicionado ligados mandar uma notificação via e-mail alertando o cara que “consumo de energia esta alto”

### Algoritmo de eleição

- Da pra subir 3 containers para fazer a eleição entre eles, os 2 ficam em standby para caso o master cair então ele entra em execução, o algoritmo de eleição vai ser em base o tempo de atividade daquele container, então se eu tenho um cara que subiu antes vai ser ele o próximo coordenador.

## Como calcular o consumo de área

- Cada área vai ter um consumo total definido, então assim que qualquer ar ou luz for ligado naquela área específica vai gerar um incremento no consumo de energia, quando esse consumo chegar em X definido pela criação da área então vai estourar a notificação

## Como vamos fazer para as mensagens serem executadas conforme estipulado pelo usuário?

- Utilizaremos o delayed exchange no RabbitMQ

---
- persistir as mensagens do rabbit no banco para se caso o cara cair eu conseguir consumir daquela tabela
- Deixar a logica de prioridade nos listeners


### Definições técnicas

Linguagem: Java com Springboot

Banco de dados: postgres

API externa: Google
<img width="851" height="600" alt="image" src="https://github.com/user-attachments/assets/3a4696a2-212e-4e20-9d5b-f70fd3d04605" />

<img width="1609" height="692" alt="image" src="https://github.com/user-attachments/assets/ac0cc3f3-f4d5-44ee-b3ca-388aa5b7f2b1" />

<img width="1038" height="588" alt="image" src="https://github.com/user-attachments/assets/5cd61495-a274-4d23-a29d-7da3ca61a55b" />

