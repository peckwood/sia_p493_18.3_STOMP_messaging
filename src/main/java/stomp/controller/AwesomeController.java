package stomp.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

import stomp.domain.CalcInput;
import stomp.domain.Result;
import stomp.domain.Shout;

@Controller
public class AwesomeController {
	private static final Logger logger = LoggerFactory.getLogger(AwesomeController.class);
	
	//An @SendTo annotation is not strictly required — 
	//by default the message will be sent to the same destination as 
	//the incoming message but with an additional prefix ("/topic" by default). 
	//It is also possible to use the 
/*	@MessageMapping("/macro")
	public void handleShout(Shout incoming){
		System.out.println("Received message: " + incoming.getMessage());
		logger.info("Received message: " + incoming.getMessage());
	}*/
	
	/*The return value also follows the same rules as for @MessageMapping, 
	 * except if the method is not annotated with SendTo or SendToUser, 
	 * the message is sent directly back to the connected user and does not pass 
	 * through the message broker. This is useful for implementing a request-reply pattern. */
	@SubscribeMapping("/macro")
	@SendTo("/topic/macro")
	public Shout handleSubscription() throws InterruptedException{
		System.out.println("handleSubscription Received message"  );
		Thread.sleep(1000);
		Shout outgoing = new Shout();
		outgoing.setMessage("Polo!");
		return outgoing;
	}
	
	/*An @SendTo annotation is not strictly required — 
	by default the message will be sent to the same destination as 
	the incoming message but with an additional prefix 
	("/topic" by default). */
	@MessageMapping("/add")
	public Result addNumber(CalcInput input) throws InterruptedException{
		System.out.println("addNumber Received message"  );
		Thread.sleep(1000);
		Result result = new Result(input.getNum1()+"+"+input.getNum2()+"="+(input.getNum1()+input.getNum2()));
		return result;
	}
	
}