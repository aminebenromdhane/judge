package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.Attorney;
import com.orion.judge.persistence.AttorneyMapper;

@Service
public class AttorneyService {

	@Autowired
	private AttorneyMapper mapper;
	
	public void insertAttorney(Attorney attorney){
		mapper.insertAttorney(attorney);
	}
}
