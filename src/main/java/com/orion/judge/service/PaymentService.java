package com.orion.judge.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.Payment;
import com.orion.judge.persistence.ActionCaseMapper;
import com.orion.judge.persistence.PaymentMapper;

@Service
public class PaymentService {

	@Autowired
	private PaymentMapper mapper;
	
	public void insertPayment(Payment payment){
		mapper.insertPayment(payment);
	}
}
