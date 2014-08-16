package com.orion.judgesc.persistence;

import java.util.List;

import com.orion.judgesc.domain.Case;

public interface CaseMapper {
	
	public Case getCase(int sourceKey);
	
	public Case getCaseByNumber(String number);
		
	public List<Case> getAllCases();
	
	public void addCase(Case newCase);
	
	public List<Case> getSpecialCases();
}
