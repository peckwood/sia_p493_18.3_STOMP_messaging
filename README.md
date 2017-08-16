2 examples:

 - macro: client sends "macro" to @SubscriptionMapping controller handler(service) and service returns a topic
 - calculator: client sends 2 numbers to calculator(@MessageMapping) and service returns a topic with the default destination(/topic/add)

2 brokers:

 	1. Simple broker: just one line in `stomp.config.WebSocketStompConfig`
 	2. StompBroker implemented by RabbitMQ, changes **from simple broker**
      	1. add spring-rabbit and reactor-tcp jar to classpath
      	2. start Rabbit service installed
      	3. enable rabbitmq_stomp plugin with command `D:\programs\RabbitMQ Server\rabbitmq_server-3.6.10\sbin>rabbitmq-plugins enable rabbitmq_stomp` [reference](https://www.rabbitmq.com/stomp.html)
      	4. Change the simple broker's one line config to multiple lines in method `configureMessageBroker()`
      	5. ​





A lot of SIA4's configuration, like JS code about subscribe to a url may be wrong, so if different, follow the online demos'

The guide I followed as a guide for calculator

http://www.concretepage.com/spring-4/spring-4-websocket-sockjs-stomp-tomcat-example

> Official reference: https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html