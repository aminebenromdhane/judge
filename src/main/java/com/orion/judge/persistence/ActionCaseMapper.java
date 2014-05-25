package com.orion.judge.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.CivilCase;


public interface ActionCaseMapper {
	
	public void insertActionCase(ActionCase actionCase);
		
	public List<ActionCase> getActionCasesByDate(@Param("firstDay") Date firstDay,@Param("finalDay") Date finalDay);

	public List<ActionCase> getActionCasesByCaseID(@Param("caseID") int caseID);
	
	public List<ActionCase> getDownloadedActionCasesByCaseID(@Param("caseID") int caseID);
		
	public void setDocumentDownloaded(@Param("id") int id);
	
	public void setDocumentNotDownloaded(@Param("id") int id);
}
