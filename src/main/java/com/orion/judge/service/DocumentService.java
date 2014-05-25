package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.Document;
import com.orion.judge.persistence.ActionCaseMapper;
import com.orion.judge.persistence.DocumentMapper;

@Service
public class DocumentService {

	@Autowired
	private DocumentMapper mapper;
	
	public void insertDocument(Document document){
		mapper.insertDocument(document);
	}
	
	public Document getDocument(int actionCaseID){
		return mapper.getDocument(actionCaseID);
	}
}
