package com.example.webservice.userandpost.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;

import com.example.webservice.userandpost.exceptions.ResourceNotFoundException;
import com.example.webservice.userandpost.service.UserAndPostService;
import com.example.webservice.userandpost.valueobject.Post;
import com.example.webservice.userandpost.valueobject.User;

@RestController
public class UsersAndPostsController {
	
	Logger logger = LoggerFactory.getLogger(UsersAndPostsController.class);
	
	@Autowired
	UserAndPostService service;

	@GetMapping("/users")
	public List<User> getAllUsers() throws Exception {
		logger.info("Invoking getAllUsers");
		List<User> user = null;
		try { 
			user = service.getAllUsers();
		}
		catch(RestClientException re) {
			throw new Exception(re); //Currently all the server exception is handled as internal server 500. Hence re-throwing same error for all cases. TODO: More cases
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		
		return user;
	}
	
	@GetMapping("/posts")
	public List<Post> getAllPosts() throws Exception {
		logger.info("Invoking getAllPosts");
		List<Post> posts = null;
		try {
			posts = service.getAllPosts();
		}
		catch(RestClientException re) {
			throw new Exception(re); //Currently all the server exception is handled as internal server 500. Hence re-throwing same error for all cases. TODO: More cases
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		
		return posts;
	}
	
	@GetMapping("/user/{id}")
	public User getUser(@PathVariable int id) throws Exception {
		logger.info("Invoking getUser for user id: {}", id);
		User user = null;
		try {
			user = service.getUser(id);
			if (user == null) {
				logger.info("User id not found for user id: {}", id); //TODO : Make a business exception.
				throw new ResourceNotFoundException("Resource : " + id + "not found" );
			}
		}
		catch(RestClientException re) {
			throw new Exception(re); //Currently all the server exception is handled as internal server 500. Hence re-throwing same error for all cases. TODO: More cases
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		return user;
	}
	
	@GetMapping("/user/{id}/posts")
	public List<Post> getAllPostsForUser(@PathVariable int id)  throws Exception {
		logger.info("Invoking getAllPostsForUser for user id: {}", id);
		List<Post> posts = null;
		try {
			posts = service.getPostsForUser(id);
			if (posts.isEmpty()) {
				logger.info("User id not found for user id: {}", id); //TODO : Make a business exception.
				throw new ResourceNotFoundException("No Post Found for User: " + id );
			}
		}
		catch(RestClientException re) {
			throw new Exception(re); //Currently all the server exception is handled as internal server 500. Hence re-throwing same error for all cases. TODO: More cases
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		return posts;
	}
	 
	@GetMapping("/usersandposts")
	public Map<User,List<Post>> getUsersAndPosts() throws Exception {
		logger.info("Invoking getAllUsersAndRelatedPosts");
		Map<User,List<Post>> userPostsMap = new HashMap<>();
		try {
			userPostsMap = service.getUsersPostsMapping();
		}
		catch(RestClientException re) {
			throw new Exception(re); //Currently all the server exception is handled as internal server 500. Hence re-throwing same error for all cases. TODO: More cases
		}
		catch(Exception e) {
			throw new Exception(e);
		}
		return userPostsMap;
	}

	/*
	 * @GetMapping("/user/{userId}/posts/{postId}") public Post
	 * getPostForUser(@PathVariable("userId") int userId, @PathVariable("postId")
	 * int postId) { Post post = service.getPostForUser(userId, postId); if (post ==
	 * null) { throw new ResourceNotFoundException("Post: " + postId + "not found"
	 * ); } return post; }
	 */

}
