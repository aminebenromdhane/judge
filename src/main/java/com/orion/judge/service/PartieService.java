package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.Attorney;
import com.orion.judge.domain.Partie;
import com.orion.judge.persistence.AttorneyMapper;
import com.orion.judge.persistence.PartieMapper;

@Service
public class PartieService {

	@Autowired
	private PartieMapper mapper;
	
	public void insertPartie(Partie partie){
		mapper.insertPartie(partie);
	}
}
