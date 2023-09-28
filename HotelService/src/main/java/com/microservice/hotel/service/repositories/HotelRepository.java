package com.microservice.hotel.service.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.microservice.hotel.service.entities.Hotel;

public interface HotelRepository extends JpaRepository<Hotel, String>{

}
