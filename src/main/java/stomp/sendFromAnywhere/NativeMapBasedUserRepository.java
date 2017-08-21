package stomp.sendFromAnywhere;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import stomp.domain.Twittr;

@Repository
public class NativeMapBasedUserRepository implements UserRepository{
	private long nextId = 1L;
	private Map<Long, Twittr> users = new HashMap<Long, Twittr>();
	
	@Override
	public Twittr save(Twittr user) throws Exception{
		user.setId(nextId);
		users.put(nextId, user);
		nextId++;
		return user;
	}
	
	@Override
	public Twittr findOne(Long id){
		return users.get(id);
	}
}
