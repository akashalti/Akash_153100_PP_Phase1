package com.capgemini.core.pp.repo;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.exception.InvalidInputException;



public class WalletRepoImpl implements WalletRepo{
	Customer customer;
	
	private Map<String, Customer> data=new HashMap<String, Customer>(); 
	public WalletRepoImpl(Map<String, Customer> data) {
		this.data = data;
	}

	
	public WalletRepoImpl() {
		
	}


	@Override
	public boolean save(Customer customer) throws InvalidInputException {
		
			data.put(customer.getMobileNo(), customer);
			return true;
		
	}

	@Override
	public Customer findOne(String mobileNo) throws InvalidInputException{
		
	customer = data.get(mobileNo);
	if(customer==null)
		throw new InvalidInputException();
	else
		return customer;
	}

}
