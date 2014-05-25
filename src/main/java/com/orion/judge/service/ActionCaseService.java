package com.orion.judge.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.persistence.ActionCaseMapper;

@Service
public class ActionCaseService {

	@Autowired
	private ActionCaseMapper mapper;
	
	public void insertActionCase(ActionCase actionCase){
		mapper.insertActionCase(actionCase);
	}
	public List<ActionCase> getActionCasesByCaseID(int caseID){
		return mapper.getActionCasesByCaseID(caseID);
	}
	public List<ActionCase> getDownloadedActionCasesByCaseID(int caseID){
		return mapper.getDownloadedActionCasesByCaseID(caseID);
	}
	
	public List<ActionCase> getActionCasesByDate(int year,int month){
		
		Calendar calendar = Calendar.getInstance();  
		
		calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);  
        calendar.set(Calendar.DAY_OF_MONTH, 1);  

        Date firstDay = calendar.getTime();
        
        calendar.add(Calendar.MONTH, 1);  
        calendar.add(Calendar.DATE, -1); 
        
        Date finalDay = calendar.getTime();
        System.out.println(firstDay+"  "+finalDay);
		return mapper.getActionCasesByDate(firstDay, finalDay);
		
	}
	
	public void updateStatus(int id,boolean downloaded){
		if(downloaded){
			mapper.setDocumentDownloaded(id);
		}else{
			mapper.setDocumentNotDownloaded(id);
		}
	}
}
