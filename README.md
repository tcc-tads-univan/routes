# Routes Microservice

[Swagger Documentation](http://localhost:8081/swagger-ui/index.html)

## RabbitMQ

Para salvar um usuário e o placeId, dá pra testar via RabbitAdmin

[Queue Link](http://localhost:15672/#/queues/%2F/routes.listener.queue)

Headers:
`__TypeId__` : `saveUserAddress`

```json
{
  "userId": 1,
  "placeId": "ChIJI2XDAKL83JQR9iOeKz6qxks",
  "relatedTo": 2
}
```

A operação de salvar remove o registro atual (caso exista) no banco de dados, e salva
a nova informação.