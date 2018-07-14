package com.capgemini.core.pp.test;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.beans.Wallet;
import com.capgemini.core.pp.exception.InsufficientBalanceException;
import com.capgemini.core.pp.exception.InvalidInputException;
import com.capgemini.core.pp.repo.WalletRepoImpl;
import com.capgemini.core.pp.service.WalletService;
import com.capgemini.core.pp.service.WalletServiceImpl;

public class TestClient {

	
	
	WalletService service;
	WalletRepoImpl repo;
	Customer customer1,customer2,customer3;
	
	
	@Before
	public void initialiseData(){
		Map<String, Customer> data = new HashMap<String,Customer>();
		customer1 = new Customer("Tony", "9988774455", new Wallet(new BigDecimal(9000)));
		customer2 = new Customer("Steve","9977886655", new Wallet(new BigDecimal(5000)));
		customer3 = new Customer("Peter","9944558877", new Wallet(new BigDecimal(7500)));
		
		data.put("9988774455", customer1);
		data.put("9977886655", customer2);
		data.put("9944558877", customer3);
		service = new WalletServiceImpl(data);
		
	}
	

	@Test//1
	public void testPhoneInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Wanda", "7894561230", new BigDecimal(20000));
		assertEquals("7894561230", customer.getMobileNo());
	}
	
	
	@Test//2
	public void testNameInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Wanda", "7894561230", new BigDecimal(20000));
		assertEquals("Wanda", customer.getName());
	}
	
	@Test//3
	public void testAmountInput() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Wanda", "7894561230", new BigDecimal(20000));
		assertEquals( new BigDecimal(20000), customer.getWallet().getBalance());
	}
	
	@Test//4
	public void testShowBalance1() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("9988774455");
		assertEquals(new BigDecimal(9000), customer.getWallet().getBalance());
	}
	
	
	@Test//5
	public void testShowBalance2() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("9977886655");
		assertEquals(new BigDecimal(5000), customer.getWallet().getBalance());
	}
	
	
	@Test//6
	public void testShowBalance3() throws InvalidInputException
	{
		Customer customer = new Customer();
		customer = service.showBalance("9944558877");
		assertEquals(new BigDecimal(7500), customer.getWallet().getBalance());
	}
	
	
	
	@Test/*7*/(expected = InsufficientBalanceException.class)
	public void testWithdraw1() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("9944558877", new BigDecimal(100000));
		
	}
	
	@Test/*8*/(expected = InsufficientBalanceException.class)
	public void testWithdraw2() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("9977886655", new BigDecimal(66000));
		
	}
	
	@Test/*9*/(expected = InsufficientBalanceException.class)
	public void testWithdraw3() throws InvalidInputException, InsufficientBalanceException
	{
		Customer customer = new Customer();
		customer = service.withdrawAmount("9988774455", new BigDecimal(9001));
		
	}
	
	@Test//10
	public void testValidation1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Oliver", "9876543210", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test//11
	public void testValidation2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.createAccount("Oliver", "7894561230", new BigDecimal(1100));
		assertEquals(10,customer.getMobileNo().length());
	}
	
	@Test//12
	public void testDepositAmount1() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("9988774455", new BigDecimal(500));
		assertEquals(new BigDecimal(9500), customer.getWallet().getBalance());
	}
	
	@Test//13
	public void testDepositAmount2() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("9977886655", new BigDecimal(500));
		assertEquals(new BigDecimal(5500), customer.getWallet().getBalance());
	}
	
	@Test//14
	public void testDepositAmount3() throws InvalidInputException {
		Customer customer = new Customer();
		customer=service.depositAmount("9944558877", new BigDecimal(500));
		assertEquals(new BigDecimal(8000), customer.getWallet().getBalance());
	}
	
	
	@Test//15
	public void testfundTransfer1() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9988774455", "9977886655", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test//16
	public void testfundTransfer2() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9988774455", "9977886655", new BigDecimal(500));
		assertEquals(new BigDecimal(8500), customer.getWallet().getBalance());
	}
	
	
	@Test//17
	public void testfundTransfer3() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9944558877", "9977886655", new BigDecimal(500));
		assertEquals(new BigDecimal(7000), customer.getWallet().getBalance());
	}
	
	@Test/*18*/(expected = InvalidInputException.class )
	public void testfundTransfer4() throws InvalidInputException {
		Customer customer = new Customer();
		customer = service.fundTransfer("9944558877", "9977886655", new BigDecimal(8000));
		
	}
	
	@Test/*19*/(expected = NullPointerException.class)
	public void testfindOneRepo() throws InvalidInputException {
		Customer customer = new Customer("","",new Wallet(null));
		customer = repo.findOne(customer.getMobileNo());
		
	}
	
	@Test//20
	public void testIsValid() throws InvalidInputException {
		Customer customer = new Customer("Scarlett","7387678820",new Wallet(new BigDecimal(150000)));
		assertTrue(service.isValid(customer));
	}
	
	@Test//21
	public void testIsValid2() throws InvalidInputException {
		Customer customer = new Customer("Peter","9944558877",new Wallet(new BigDecimal(5000)));
		assertTrue(service.isValid(customer));
	}

}

























