/**
 * 
 */
package com.nacho.microservices.microservicestest.user;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nacho.microservices.microservicestest.user.exception.UserNotFoundException;

/**
 * @author nacho
 *
 */
@RestController
public class UserResource {

	@Autowired
	private UserDaoService userDao;
	
	@GetMapping("/users")
	public List<User> retrieveAllUsers() {
		return userDao.findAll();
	}
	
	@GetMapping(value = "/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		User user = userDao.findOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		
		Resource<User> resource = new Resource<User>(user);
		
		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());
		
		resource.add(linkTo.withRel("all-users"));
		
		return resource;
	}
	
	@DeleteMapping("/users/{id}")
	public void deleteUser(@PathVariable int id) {
		User user = userDao.deleteOne(id);
		
		if (user == null) {
			throw new UserNotFoundException("id-" + id);
		}
		
	}
	
	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User saved = userDao.save(user);
		
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest().path("/{id}")
				.buildAndExpand(saved.getId()).toUri();
		return ResponseEntity.created(location).build();
	}
}
