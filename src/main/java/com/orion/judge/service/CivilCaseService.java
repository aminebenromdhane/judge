package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.CivilCase;
import com.orion.judge.persistence.CivilCaseMapper;

@Service
public class CivilCaseService {

	@Autowired
	private CivilCaseMapper mapper;
	
	public void insertCivilCase(CivilCase civilCase){
		mapper.insertCivilCase(civilCase);
	}
	
	public CivilCase getCivilCase(String number){
		return mapper.getCivilCase(number);
	}
	
	public String getLatestCivilCase(String year){
		CivilCase civilCase = mapper.getLatestCivilCase(year);
		return civilCase.getNumber();
	}
	
	public List<CivilCase> getAllCivilCases(){
		return mapper.getAllCivilCases();
	}
}
