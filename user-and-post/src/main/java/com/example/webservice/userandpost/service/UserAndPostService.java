package com.example.webservice.userandpost.service;

import java.util.List;
import java.util.Map;

import com.example.webservice.userandpost.valueobject.Post;
import com.example.webservice.userandpost.valueobject.User;

public interface UserAndPostService {
	
	public List<User> getAllUsers();
	
	public List<Post> getAllPosts();
	
	public User getUser(int id);

	public List<Post> getPostsForUser(int userId);
	
	public Map<Integer,List<Post>> getUsersPostsMapping();

	//public Post getPostForUser(int userId, int postId); 

}
