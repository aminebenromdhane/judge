package com.orion.judge.persistence;

import java.util.List;

import com.orion.judge.domain.ActionCase;
import com.orion.judge.domain.CivilCase;
import com.orion.judge.domain.Payment;


public interface PaymentMapper {
	
	public void insertPayment(Payment payment);
		
}
