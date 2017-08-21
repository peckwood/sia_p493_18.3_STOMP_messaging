package stomp.sendFromAnywhere;

import stomp.domain.Twittr;

public interface UserRepository {

	Twittr save(Twittr user) throws Exception;

	Twittr findOne(Long id);

}
