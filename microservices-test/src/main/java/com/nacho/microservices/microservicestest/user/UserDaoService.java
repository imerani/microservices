package com.nacho.microservices.microservicestest.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {
	private static final List<User> users = new ArrayList<>();
	private static int userCount = 3;

	static {
		users.add(new User(1, "Juan", new Date()));
		users.add(new User(2, "Pepe", new Date()));
		users.add(new User(3, "Jose", new Date()));
	}

	public List<User> findAll() {
		return users;
	}

	public User save(User user) {
		users.add(user);
		if (user.getId() == null) {
			user.setId(++userCount);
		}
		return user;
	}
	
	public User findOne(int id) {
		for (User u: users) {
			if (u.getId() == id) {
				return u;
			}
		}
		return null;
	}
	
	public User deleteOne(int id) {
		Iterator<User> it = users.iterator();
		while (it.hasNext()) {
			User u = it.next();
			if (u.getId() == id) {
				it.remove();
				return u;
			}
		}
		return null;
	}
}
