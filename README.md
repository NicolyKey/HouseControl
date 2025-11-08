# Sistemas Distribuídos

## Fluxo
- usuário se loga através do google na aplicação
- aplicação valida aquele token através da api do google outh2
-  gera um token jwt com uma expiração de 10h e codifica o email nome do usuário que logou e etc
-  manda através de um header para o rabbit com a routing key da fila para ser postada
-  o meu coordenador consome o objeto enviado naquela fila, decodifica o token, e salva no banco a requisição e as credenciais do usuário que fez a requisição
-  o meu quartz fica monitorando o banco para saber quando que aquela atividade deve ser executada, se chegar o tempo então ele posta no rabbit, a routing key, as credenciais e a mensagem
-  o meu coordenador vai ler aquela mensagem e vai alterar entao a minha entidade de acordo com aquela mensagem, e se caso ele consumiu mais energia do que o especulado pelo usuário entao ele manda uma notificação por email mandado na mensagem
