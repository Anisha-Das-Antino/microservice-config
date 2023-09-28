package com.microservice.user.service.services.impl;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice.user.service.entities.User;
import com.microservice.user.service.entities.Hotel;
import com.microservice.user.service.entities.Rating;
import com.microservice.user.service.exceptions.ResourceNotFoundException;
import com.microservice.user.service.external.services.HotelService;
import com.microservice.user.service.repositories.UserRepository;
import com.microservice.user.service.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private HotelService hotelService;

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

	@Override
	public User saveUser(User user) {
		// generate unique userId
		String randomUserId = UUID.randomUUID().toString();
		user.setUserId(randomUserId);
		return userRepository.save(user);
	}

	@Override
	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	@Override
	public User getUser(String userId) {
		// get user from database with the help of the user repository
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given ID Not Found on Server : " + userId));
		// fetch rating of the above user from RATING SERVICE
		// http://localhost:8083/ratings/users/33e164bb-ff15-4ba8-ba01-b6626dc1f117

			Rating[] ratingsOfUser = restTemplate.getForObject(
					"http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
			logger.info("{}",ratingsOfUser);
			
			List<Rating> ratings = Arrays.stream(ratingsOfUser).toList();
			
			List<Rating> ratingList = ratings.stream().map(rating -> {
				//api call to hotel service to get the hotel
//				ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/"+rating.getHotelId(), Hotel.class);
//				Hotel hotel = forEntity.getBody();
				Hotel hotel = hotelService.getHotel(rating.getHotelId());
//				logger.info("Response Status Code: {}",forEntity.getStatusCode());
				
				//set the hotel to rating
				rating.setHotel(hotel);
				
				//return the rating
				return rating;
			}).collect(Collectors.toList());
			
			user.setRatings(ratingList);
			
		return user;
	}

	@Override
	public User updateUser(String userId, User updatedUser) {
		User userToUpdate = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given ID Not Found on Server: " + userId));

		userToUpdate.setName(updatedUser.getName());
		userToUpdate.setEmail(updatedUser.getEmail());

		return userRepository.save(userToUpdate);
	}

	@Override
	public void deleteUser(String userId) {
		User userToDelete = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User with given ID Not Found on Server: " + userId));

		userRepository.delete(userToDelete);
	}

}
