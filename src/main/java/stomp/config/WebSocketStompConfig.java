package stomp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer{

	//STOMP client will send the message via the URL /macroApp/macro
	
	@Override
	public void registerStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/macro").withSockJS();
		registry.addEndpoint("/add").withSockJS();
	}

	@Override
	public void configureMessageBroker(MessageBrokerRegistry registry) {
		//does not work when using StompBrokerRelay
		registry.setApplicationDestinationPrefixes("/awesomeApp");
		
//		registry.enableSimpleBroker("/topic", "/queue");
		
		registry.enableStompBrokerRelay("/topic", "/queue")
		.setRelayHost("localhost")
		//.setRelayPort(61613)
		.setClientLogin("guest")
		.setClientPasscode("guest");
	}
}
