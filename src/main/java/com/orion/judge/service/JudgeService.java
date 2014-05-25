package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.Attorney;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Judge;
import com.orion.judge.persistence.AttorneyMapper;
import com.orion.judge.persistence.JudgeMapper;

@Service
public class JudgeService {

	@Autowired
	private JudgeMapper mapper;
	
	public void insertJudge(Judge judge){
		mapper.insertJudge(judge);
	}
	
	public Judge getJudge(String name){
		return mapper.getJudge(name);
	}
	
}
