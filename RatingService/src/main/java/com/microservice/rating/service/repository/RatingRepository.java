package com.microservice.rating.service.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservice.rating.service.entities.Rating;

public interface RatingRepository extends MongoRepository<Rating, String> {
	
	//custom finder methods
	
	List<Rating> findByUserId(String userId);
	List<Rating> findByHotelId(String hotelId);

}
