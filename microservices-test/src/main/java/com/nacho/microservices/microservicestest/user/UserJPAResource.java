/**
 * 
 */
package com.nacho.microservices.microservicestest.user;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

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
public class UserJPAResource {

	@Autowired
	private UserReporitory userReporitory;
	
	@Autowired
	private PostReporitory PostReporitory;

	@GetMapping("/jpa/users")
	public List<User> retrieveAllUsers() {
		return userReporitory.findAll();
	}

	@GetMapping(value = "/jpa/users/{id}")
	public Resource<User> retrieveUser(@PathVariable int id) {
		Optional<User> user = userReporitory.findById(id);

		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}

		Resource<User> resource = new Resource<User>(user.get());

		ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).retrieveAllUsers());

		resource.add(linkTo.withRel("all-users"));

		return resource;
	}

	@DeleteMapping("/jpa/users/{id}")
	public void deleteUser(@PathVariable int id) {
		userReporitory.deleteById(id);
	}

	@PostMapping("/jpa/users")
	public ResponseEntity<Object> createUser(@Valid @RequestBody User user) {
		User saved = userReporitory.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(saved.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
	
	@GetMapping("/jpa/users/{id}/posts")
	public List<Post> retrieveAllPosts(@PathVariable Integer id) {
		Optional<User> user = userReporitory.findById(id);
		
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		return user.get().getPosts();
	}
	
	@PostMapping("/jpa/users/{id}/post")
	public ResponseEntity<Object> createUser(@PathVariable int id, @RequestBody Post post) {
		Optional<User> user = userReporitory.findById(id);
		
		if (!user.isPresent()) {
			throw new UserNotFoundException("id-" + id);
		}
		
		post.setUser(user.get());
		PostReporitory.save(post);

		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId())
				.toUri();
		return ResponseEntity.created(location).build();
	}
}
