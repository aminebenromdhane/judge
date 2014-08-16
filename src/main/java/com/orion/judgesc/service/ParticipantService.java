package com.orion.judgesc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judgesc.domain.Participant;
import com.orion.judgesc.persistence.ParticipantMapper;

@Service
public class ParticipantService {

	@Autowired
	private ParticipantMapper mapper;
	
	public void addParticipant(Participant participant){
		mapper.addParticipant(participant);
	}
}
