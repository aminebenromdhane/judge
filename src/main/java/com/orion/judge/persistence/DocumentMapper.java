package com.orion.judge.persistence;

import java.util.List;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Document;


public interface DocumentMapper {
	
	public void insertDocument(Document document);
	
	public Document getDocument(int actionCaseID);
}
