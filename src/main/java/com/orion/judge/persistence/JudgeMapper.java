package com.orion.judge.persistence;

import java.util.List;

import com.orion.judge.domain.Judge;


public interface JudgeMapper {
	
	public void insertJudge(Judge judge);
	
	public Judge getJudge(String name);
	
}
