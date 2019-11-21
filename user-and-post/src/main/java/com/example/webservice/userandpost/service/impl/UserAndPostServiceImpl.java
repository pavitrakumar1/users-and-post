package com.example.webservice.userandpost.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClientException;

import com.example.webservice.userandpost.helper.RestClientHelper;
import com.example.webservice.userandpost.service.UserAndPostService;
import com.example.webservice.userandpost.valueobject.Post;
import com.example.webservice.userandpost.valueobject.User;

@Service
public class UserAndPostServiceImpl implements UserAndPostService {

	Logger logger = LoggerFactory.getLogger(UserAndPostServiceImpl.class);
	
	@Autowired
	RestClientHelper helper;

	@Override
	public List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		logger.info("Invoking API to fetch complete user list. API Name: {}", RestClientHelper.GET_USER_ENDPOINT);
		try {
			ResponseEntity<User[]> usersResp = helper.exchange(HttpMethod.GET, RestClientHelper.GET_USER_ENDPOINT, null, User[].class);
			//TODO: Check HTTP status, before doing a getBody for all endpoints call.
			User[] list = usersResp.getBody();
			users = Arrays.asList(list);
			logger.debug("User list returned from API", Arrays.toString(list));
		} catch (RestClientException rce) { // Currently have only one exception
			logger.error("I/O Error occured in getAllUsers", this.getClass());
			throw rce;
		}
		catch (Exception e) {
			logger.error("Generic Error occured in getAllUsers", this.getClass());
			throw e;
		}
		logger.info("Finished call to fetch complete user list. API Name: {}", RestClientHelper.GET_USER_ENDPOINT);
		return users;
	}

	@Override
	public List<Post> getAllPosts() {
		logger.info("Invoking API to fetch complete posts list. API Name: {}", RestClientHelper.GET_POSTS_ENDPOINT);
		Post[] list = null;
		try {
			ResponseEntity<Post[]> postsResp = helper.exchange(HttpMethod.GET, RestClientHelper.GET_POSTS_ENDPOINT, null, Post[].class);
			list = postsResp.getBody();
			logger.debug("Post list returned from API", Arrays.toString(list));
		} catch (RestClientException rce) { // Currently have only one exception
			logger.error("I/O Error occured in getAllPosts", this.getClass());
			throw rce;
		}
		catch (Exception e) {
			logger.error("Generic Error occured in getAllPosts", this.getClass());
			throw e;
		}
		logger.info("Finised call to fetch complete posts list. API Name: {}", RestClientHelper.GET_POSTS_ENDPOINT);
		return Arrays.asList(list);
	}

	@Override
	public User getUser(int id) {
		logger.info("Invoking API to fetch user with id {}. API Name: {}", id, RestClientHelper.GET_POSTS_ENDPOINT);
		try {
			ResponseEntity<User[]> userResp = helper.exchange(HttpMethod.GET, RestClientHelper.GET_USER_ENDPOINT, null, User[].class);
			User[] list = userResp.getBody();
			logger.debug("User list returned from API", Arrays.toString(list));
			for (User user : list) {
				if (user.getId() == id) {
					logger.info("User found. Finised call to fetch user with id {}. API Name: {}", id, RestClientHelper.GET_POSTS_ENDPOINT);
					return user;
				}
			}
		}
		catch (RestClientException rce) { // Currently have only one exception
			logger.error("I/O Error occured in getAllPosts", this.getClass());
			throw rce;
		}
		catch (Exception e) {
			logger.error("Generic Error occured in getAllPosts", this.getClass());
			throw e;
		}
		return null;
	}

	@Override
	public List<Post> getPostsForUser(int userId) {
		List<Post> filteredPosts = new ArrayList<>();
		logger.info("Invoking API to fetch all posts for user with id {}. API Name: {}", userId, RestClientHelper.GET_POSTS_ENDPOINT);
		try {
			ResponseEntity<Post[]> postsResp = helper.exchange(HttpMethod.GET, RestClientHelper.GET_POSTS_ENDPOINT, null,Post[].class);
			Post[] list = postsResp.getBody();
			logger.debug("Post list returned from API", Arrays.toString(list));
			for (Post post : list) {
				if (post.getUserId() == userId) {
					filteredPosts.add(post);
				}
			}
		} catch (RestClientException rce) {
			logger.error("I/O Error occured in getPostsForUser for user with id {}. API Name: {}", userId, RestClientHelper.GET_POSTS_ENDPOINT);
			throw rce;
		}
		catch (Exception e) {
			logger.error("Generic Error occured in getPostsForUser for user with id {}. API Name: {}", userId, RestClientHelper.GET_POSTS_ENDPOINT);
			throw e;
		}
		logger.info("Finished call to fetch all posts for user with id {}. API Name: {}", userId, RestClientHelper.GET_POSTS_ENDPOINT);
		return filteredPosts;
	}
	
	/*
	 * @Override public Post getPostForUser(int userId, int postId) { List<Post>
	 * posts = getPostsForUser(userId); for (Post post : posts) { if(post.getId() ==
	 * postId) { return post; } } return null; }
	 */

	@Override
	public Map<User, List<Post>> getUsersPostsMapping() {
		logger.info("Invoking API to fetch all users and all posts");
		Map<User, List<Post>> userAndPosts = new HashMap<>();

		//Make in-memory data manipulation to avoid un-necessary API calls.
		List<User> users = getAllUsers();
		List<Post> posts = getAllPosts();

		//It's logical to loop over posts rather than user, as we are trying to segregate posts into user group.
		for(Post post : posts) {
			int userId = post.getUserId();
			User user = getUserMap(users).get(userId);
			if(userAndPosts.get(user) != null) {
				userAndPosts.get(user).add(post);
			}else {
				List<Post> newList = new ArrayList<Post>();
				newList.add(post);
				userAndPosts.put(user, newList);
			}
		}
			
		logger.info("Finised call to fetch all users and related posts");
		return userAndPosts;
	}
	
	private Map <Integer, User> getUserMap(List<User> userList) {
		Map <Integer, User> userMap = new HashMap<Integer, User>();
		for(User user : userList) {
			userMap.put(user.getId(), user);
		}
		return userMap;
	}

}
