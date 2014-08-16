package com.orion.judgesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judgesc.domain.Case;
import com.orion.judgesc.persistence.CaseMapper;

@Service
public class CaseService {

	@Autowired
	private CaseMapper mapper;
	
	public void addCase(Case newCase){
		mapper.addCase(newCase);
	}
	public Case getCase(int sourceKey){
		return mapper.getCase(sourceKey);
	}
	public Case getCaseByNumber(String number){
		return mapper.getCaseByNumber(number);
	}
	public List<Case> getAllCases(){
		return mapper.getAllCases();
	}
	public List<Case> getSpecialCases(){
		return mapper.getSpecialCases();
	}
}
