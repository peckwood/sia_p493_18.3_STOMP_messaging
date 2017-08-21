2 examples:

 - macro: client sends "macro" to @SubscriptionMapping controller handler(service) and service returns a topic
 - calculator: client sends 2 numbers to calculator(@MessageMapping) and service returns a topic with the default destination(/topic/add)

2 brokers:

  1.  Simple broker: just one line in `stomp.config.WebSocketStompConfig`
     2. StompBroker implemented by RabbitMQ, changes **from simple broker**
        1. add spring-rabbit and reactor-tcp jar to classpath
         2. start Rabbit service installed
         3. enable rabbitmq_stomp plugin with command `D:\programs\RabbitMQ Server\rabbitmq_server-3.6.10\sbin>rabbitmq-plugins enable rabbitmq_stomp` [reference](https://www.rabbitmq.com/stomp.html)
         4. Change the simple broker's one line config to multiple lines in method `configureMessageBroker()`

     ---

     ### Sending from anywhere | send to specific user

     #### Anywhere

     - Aside from @MessageMapping and @SubscribeMapping which only executes after they're trigged you can send a message from anywhere from the application with `SimpMessageSendingOperations` interface.
     - `SimpleMessageTemplate`, an implementing class, is already built in Spring application context thanks to Spring's STOMP support
     - `messaging.convertAndSend("/topic/tweetfeed", twittr);`
     - Notable file: `stomp.sendFromAnywhere.UserFeedServiceImpl.java`

     #### Specific User

     1. 3 ways to take advantages of an authenticated

        1. `@MessageMapping` and `@SubscribeMapping` methods can receive a `Principal` parameter
        2. values returned from `@MessageMapping`, `@SubscribeMapping` and `@MessageException` can be sent to a specific user with `@SendToUser`
        3. SimpMessagingTemplate` can send messges to a specific user

        #### Method 1

        ​	See method `stomp.sendFromAnywhere.UserMessageController.handleUser(Principal, SpittleForm)`

        #### Method 2

        ​	The JS code `stomp.subscribe("/user/queue/notifications", handleNotification);` in `/main/webapp/WEB-INF/views/chat.html` 

        ​	`/user` messages flow through `UserDestinationMessageHandler` , which reroute user messges to a destination that's unique to the user by removing `/user` prefix and suffixing a username like `/queue/notification/{username}`or maybe `/queue/notification-{username}`

        ​

        ​	`@SendToUser("/queue/notifications")`in controller method `stomp.sendFromAnywhere.UserMessageController.handleUser(Principal, SpittleForm)` ensures the return value is published at user-who-sent-the-message-destination

        ​	These 2 work together.

        #### Method 3

        `convertAndSendToUser()` of `SimpMessageSendingOperations ` sends the message to a specific user. You need to give its username. And it will send to `/queue/notification/{username}`or maybe `/queue/notification-{username}`.

        Find it in `stomp.sendFromAnywhere.UserFeedServiceImpl.broadcastUser(Twittr)`



### Security enabled 

 - `stomp.sendFromAnywhere.SecurityConfig` set usernames/password and loaded into `stomp.config.WebInitializer`
 - `stomp.sendFromAnywhere.SecurityWebInitializer` no idea what it does, but totally necessary
 - `Principle` is used in `stomp.sendFromAnywhere.UserMessageController`

### Handling message exception

Due to the asynchronous nature of STOMP messaging, the sender may never know that anything went
wrong. Aside from being logged by Spring, the exception could be lost with no
recourse or opportunity to recover. 

You can use `@MessageExceptionHandler` together with `@SendToUser` to catch the exceptions and show them to the user triggered it. See `stomp.sendFromAnywhere.UserMessageController.handleExceptions(Throwable)`

---

The code from book SIA4 about connecting to an endpoint doesn't work. So for these, follow the code in this project

> [The guide I followed as a guide for calculator](http://www.concretepage.com/spring-4/spring-4-websocket-sockjs-stomp-tomcat-example)
>
> [Official reference](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/websocket.html)