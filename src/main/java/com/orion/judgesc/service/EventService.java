package com.orion.judgesc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judgesc.domain.Event;
import com.orion.judgesc.persistence.EventMapper;

@Service
public class EventService {

	@Autowired
	private EventMapper mapper;
	
	public void addEvent(Event event){
		mapper.addEvent(event);
	}
}
