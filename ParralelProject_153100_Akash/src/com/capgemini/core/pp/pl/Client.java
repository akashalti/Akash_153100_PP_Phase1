package com.capgemini.core.pp.pl;

import java.math.BigDecimal;
import java.util.Scanner;

import com.capgemini.core.pp.beans.Customer;
import com.capgemini.core.pp.exception.InsufficientBalanceException;
import com.capgemini.core.pp.exception.InvalidInputException;
import com.capgemini.core.pp.service.WalletService;
import com.capgemini.core.pp.service.WalletServiceImpl;

public class Client {

	public static void main(String[] args) throws InvalidInputException,InsufficientBalanceException {
		WalletService service = new WalletServiceImpl();
		Customer customer;
		int choice;
		String selection;
		Scanner str = new Scanner(System.in);
		do {
			System.out.println("\nChoose among the following menu: ");
			System.out.println("1. Create an Account");
			System.out.println("2. Show the Balance");
			System.out.println("3. Fund Transfer");
			System.out.println("4. Deposit Fund");
			System.out.println("5. Withdraw Fund");
			System.out.println("6. Exit");
			System.out.print("Enter your choice\t\t:\t");

			choice = str.nextInt();
			switch (choice) {
			case 1:

				System.out.print("\nEnter name\t\t\t:\t");
				String name = str.next();
				System.out.print("\nEnter phone number\t\t:\t");
				String mobileno = str.next();
				System.out.print("\nEnter amount\t\t\t:\t");
				BigDecimal amount=str.nextBigDecimal();
				customer = service.createAccount(name, mobileno, amount);
				System.out.println(customer);
				break;

			case 2:

				System.out.print("\nEnter mobile number\t\t:\t");
				customer = service.showBalance(str.next());
				System.out.println(customer.getWallet());
				break;

			case 3:
				System.out.print("\nEnter sender mobile number\t:\t");
				String sourceMobileNo = str.next();
				System.out.print("\nEnter reciever mobile number\t:\t");
				String targetMobileNo = str.next();
				System.out.print("\nEnter amount\t\t\t:\t");
				amount = str.nextBigDecimal();
				customer = service.fundTransfer(sourceMobileNo, targetMobileNo, amount);
				System.out.println(customer);
				break;

			case 4:

				System.out.print("\nEnter mobile phone\t\t:\t");
				String mobileNo = str.next();
				System.out.print("\nEnter the amount to be deposited:\t");
				amount = new BigDecimal(str.next());
				customer = service.depositAmount(mobileNo, amount);
				System.out.println(customer);
				break;

			case 5:

				System.out.print("\nEnter mobile phone\t\t:\t");
				mobileNo = str.next();
				System.out.print("\nEnter the amount to be withdrawn\t:\t");
				amount = new BigDecimal(str.next());
				customer = service.withdrawAmount(mobileNo, amount);
				System.out.print(customer);
				break;

			case 6:
				System.exit(0);

			default:
				System.out.print("\nEnter correct choice");

			}
			System.out.print("\nDo you wish to continue\t\t:\t");
			selection = str.next();
		} while (selection.equalsIgnoreCase("y")||selection.equalsIgnoreCase("yes"));
		str.close();
	}
	

}
