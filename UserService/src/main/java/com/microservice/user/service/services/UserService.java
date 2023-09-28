package com.microservice.user.service.services;

import java.util.List;

import com.microservice.user.service.entities.User;

public interface UserService {
	
	//user operations
	
	//create
	User saveUser(User user);
	
	//get all user
	List<User> getAllUser();
	
	//get single user of given userId
	User getUser(String userId);
	
	//update user by the given userId
	User updateUser(String userId, User updatedUser);
	
	//delete user by the given userId
	void deleteUser(String userId);

}
