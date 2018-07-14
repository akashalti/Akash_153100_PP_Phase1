package com.capgemini.core.pp.service;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Scanner;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.beans.Wallet;
import com.capgemini.core.pp.exception.InsufficientBalanceException;
import com.capgemini.core.pp.exception.InvalidInputException;
import com.capgemini.core.pp.repo.WalletRepo;
import com.capgemini.core.pp.repo.WalletRepoImpl;

public class WalletServiceImpl implements WalletService {

	WalletRepo repo = new WalletRepoImpl();
	Customer customer;
	Wallet wallet;
	
	Scanner str = new Scanner(System.in);

	 public WalletServiceImpl(Map<String, Customer> data){
	 repo= new WalletRepoImpl(data);
	 }
	 public WalletServiceImpl(WalletRepo repo) {
	 this.repo = repo;
	 }
	
	 public WalletServiceImpl() {
	 }

	@Override
	public Customer createAccount(String name, String mobileNo, BigDecimal amount) throws InvalidInputException {
		// validate here
		
		wallet = new Wallet(amount);
		customer = new Customer(name, mobileNo, wallet);
		boolean b1;
		do {
			b1 = isValid(customer);
			
		}while(!b1);
		
		if(b1) {
			boolean b = repo.save(customer);
			System.out.println("\nYour account has been created, having the following details: ");
				return customer;
		}
		else {
			throw new InvalidInputException("Incorrect input exception");
		}
			
		}
		
		
		
	

	@Override
	public Customer showBalance(String mobileno) throws InvalidInputException {
		 Customer customer=repo.findOne(mobileno);
		 if(customer!=null) {
			 return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
	}
	@Override
	public Customer fundTransfer(String sourceMobileNo, String targetMobileNo, BigDecimal amount) throws InvalidInputException {
		
		Customer customerSource = repo.findOne(sourceMobileNo);
		Customer customerTarget = repo.findOne(targetMobileNo);
		
		int i=customerSource.getWallet().getBalance().compareTo(amount);
		
		if(i!=-1) {
			Wallet wallet0=customerSource.getWallet();
			wallet0.setBalance(wallet0.getBalance().subtract(amount));
			customerSource.setWallet(wallet0);
			boolean b = repo.save(customerSource);
			
			Wallet wallet1 = customerTarget.getWallet();
			wallet1.setBalance(wallet1.getBalance().add(amount));
			
			customerTarget.setWallet(wallet1);
			boolean b1 = repo.save(customerTarget);
			return customerSource;
			
		}
		else {
			throw new InvalidInputException("Invalid amount ");
		}

		
		
	}

	@Override
	public Customer depositAmount(String mobileNo, BigDecimal amount) throws InvalidInputException {
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null) {
			wallet = customer.getWallet();
			wallet.setBalance(wallet.getBalance().add(amount));
			
			customer.setWallet(wallet);
			boolean b = repo.save(customer);
			 return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	
	}

	@Override
	public Customer withdrawAmount(String mobileNo, BigDecimal amount) throws InvalidInputException, InsufficientBalanceException{
		Customer customer=repo.findOne(mobileNo);
		if(customer!=null) {
			
			wallet = customer.getWallet();
			// amount validation here
			int i = wallet.getBalance().compareTo(amount);
			if(i!=-1) {
				wallet.setBalance(wallet.getBalance().subtract(amount));
				
				customer.setWallet(wallet);
				boolean b = repo.save(customer);
				 
			}
			else {
				throw new InsufficientBalanceException("INSUFFICIENT BALANCE");
			}
			
			return customer;
		 }
		 else
			 throw new InvalidInputException("Invalid mobile no ");
		
	
	}
	@Override
	public boolean isValid(Customer customer) throws InvalidInputException {
		
			if(String.valueOf(customer.getMobileNo()).matches("[1-9][0-9]{9}") && customer.getMobileNo()!=null) {
				if(customer.getName().matches("[A-Z][a-z]*")&& customer.getName()!=null) {
					return true;
				}		
				else {
					System.err.println("\nName can contain only alphabets");
					System.out.println("\nEnter correct name: ");
					customer.setName(str.next());
					return false;
				}
					
			}
			else {
				
				System.err.println("\nPhone no is invalid");
				System.out.println("\nEnter correct number: ");
				customer.setMobileNo(str.next());
				return false;
			}

	}
	
	
}
