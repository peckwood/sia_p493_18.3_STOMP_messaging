package stomp.sendFromAnywhere;

import stomp.domain.Twittr;

public interface UserFeedService {

	void broadcastUser(Twittr user);

}
