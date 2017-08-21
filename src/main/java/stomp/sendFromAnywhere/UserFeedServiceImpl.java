package stomp.sendFromAnywhere;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import stomp.domain.Twittr;

@Service
public class UserFeedServiceImpl implements UserFeedService {
	@Autowired
	private SimpMessageSendingOperations messaging;
	private Pattern pattern = Pattern.compile("\\@(\\S+)");
	
	@Override
	public void broadcastUser(Twittr twittr){
		messaging.convertAndSend("/topic/tweetfeed", twittr);
		
		Matcher matcher = pattern.matcher(twittr.getMessage());
		if(matcher.find()){
			String username = matcher.group(1);
			messaging.convertAndSendToUser(username, "/queue/notifications", new Notification("You are mentioned!"));
		}
	}
}
