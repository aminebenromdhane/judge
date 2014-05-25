package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.Court;
import com.orion.judge.persistence.ActionCaseMapper;
import com.orion.judge.persistence.CourtMapper;

@Service
public class CourtService {

	@Autowired
	private CourtMapper mapper;
	
	public void insertCourt(Court court){
		mapper.insertCourt(court);
	}
}
