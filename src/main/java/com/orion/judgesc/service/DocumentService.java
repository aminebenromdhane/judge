package com.orion.judgesc.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judgesc.domain.Document;
import com.orion.judgesc.persistence.DocumentMapper;

@Service
public class DocumentService {

	@Autowired
	private DocumentMapper mapper;
	
	public void addDocument(Document document){
		mapper.addDocument(document);
	}
}
