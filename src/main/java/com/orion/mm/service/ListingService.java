package com.orion.mm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.mm.domain.Listing;
import com.orion.mm.persistence.ListingMapper;

@Service
public class ListingService {

	@Autowired
	private ListingMapper mapper;
	
	public void addListing(Listing listing){
		mapper.addListing(listing);
	}
}
