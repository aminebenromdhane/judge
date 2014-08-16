package com.orion.judgesc.persistence;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.orion.judgesc.domain.Action;

public interface ActionMapper {
	
	public List<Action> getActionByCaseID(int caseID);
	
	public void addAction(Action action);
	
	public Action getActionID(@Param("entry") String entry,@Param("filedDate") Date filedDate,@Param("caseID") int caseID,@Param("filedBy") String filedBy);
}
