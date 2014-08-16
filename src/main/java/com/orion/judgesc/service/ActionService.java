package com.orion.judgesc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judgesc.domain.Action;
import com.orion.judgesc.persistence.ActionMapper;

@Service
public class ActionService {

	@Autowired
	private ActionMapper mapper;
	
	public void addAction(Action action){
		mapper.addAction(action);
	}
	
	public List<Action> getActionByCaseID(int caseID){
		return mapper.getActionByCaseID(caseID);
	}
	
	public int getActionID(Action action){
		return mapper.getActionID(action.getEntry(),action.getFiledDate(),action.getCaseId(),action.getFiledBy()).getId();
	}
}
