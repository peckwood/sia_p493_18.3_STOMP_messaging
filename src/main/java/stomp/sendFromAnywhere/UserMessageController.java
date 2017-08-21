package stomp.sendFromAnywhere;

import java.security.Principal;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import stomp.domain.Twittr;

@Controller
public class UserMessageController {
	static{
		System.out.println("UserMessageController initiated");
	}
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private UserFeedService feedService;
	
	private static final Logger logger = LoggerFactory.getLogger(UserMessageController.class);
	
	@MessageMapping("/tweet")
	@SendToUser("/queue/notifications")
	public Notification handleUser(Principal principal, SpittleForm form) throws Exception{
		if("exception".equals(form.getText())){
			throw new Exception("Exception test");
		}
		Twittr user = new Twittr(principal.getName(), form.getText(), new Date());
		userRepo.save(user);
		feedService.broadcastUser(user);
		
		return new Notification("You said: " + form.getText()+" ["+user.getTimestamp()+"]");
	}
	
	@MessageExceptionHandler(Exception.class)
	@SendToUser("/queue/errors")
	public Notification handleExceptions(Throwable t){
		logger.error("Error handling message: " + t.getMessage());
		System.out.println("Error handling message: " + t.getMessage());
		return new Notification("You have an error: " + t.getMessage());
	}
}
