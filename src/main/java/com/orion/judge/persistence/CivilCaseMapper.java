package com.orion.judge.persistence;

import java.util.List;

import com.orion.judge.domain.CivilCase;


public interface CivilCaseMapper {
	
	public void insertCivilCase(CivilCase civilCase);
	
	public CivilCase getCivilCase(String number);
	
	public CivilCase getLatestCivilCase(String year);
	
	public List<CivilCase> getAllCivilCases();
}
