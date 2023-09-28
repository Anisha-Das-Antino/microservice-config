package com.microservice.hotel.service.services;

import java.util.List;

import com.microservice.hotel.service.entities.Hotel;

public interface HotelService {
	
	//create
	
	Hotel create(Hotel hotel);
	
	//getAll
	
	List<Hotel> getAll();
	
	//get single
	
	Hotel get(String id);

}
