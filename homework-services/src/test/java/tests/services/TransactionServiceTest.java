//package tests.services;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import businessobjects.Account;
//import dtos.TransactionDto;
//import enums.Bank;
//import enums.Country;
//import services.TransactionService;
//import tests.AbstractTest;
//
//public class TransactionServiceTest extends AbstractTest{
//	
//	@Autowired
//	private TransactionService transactionService;
//	
//	TransactionDto transaction;
//	Account accountOrigin;
//	Account accountDestiny;
//	
//	@Before
//	public void setUp(){
//		accountOrigin = new Account();
//		accountDestiny = new Account();
//		accountOrigin.setOriginCountry(Country.ARGENTINA);
//		accountOrigin.setBank(Bank.MACRO);
//		accountOrigin.setCredit(new Long(200));
//		accountDestiny.setCredit(new Long(100));
//	}
//
//	@Test
//	public void calculateTaxesInternationalSameBank(){
//		
//		prepareTransactionInternational();
//		
//		prepareTransactionSameBank();
//		
//		buildTransaction();
//		
//		transactionService.putInQueue(transaction);
//		
//		transactionService.operate();
//		
//		assertEquals(new Long(200), accountDestiny.getCredit());
//		assertEquals(new Long(95), accountOrigin.getCredit());
//	}
//	
//	@Test
//	public void calculateTaxesInternationalDifferentBank(){
//		
//		prepareTransactionInternational();
//		prepareTransactionDifferentBank();
//		buildTransaction();
//		
//		transactionService.putInQueue(transaction);
//	}
//	@Test
//	public void calculateTaxesNationalDifferentBank(){
//		
//		prepareTransactionNational();
//		prepareTransactionDifferentBank();
//		buildTransaction();
//		transactionService.putInQueue(transaction);
//	}
//	
//	@Test
//	public void calculateTaxesNationalSameBank(){
//		
//		prepareTransactionNational();
//		prepareTransactionSameBank();
//		buildTransaction();
//		
//		transactionService.putInQueue(transaction);
//	}
//	private void buildTransaction(){
//		transaction.setOriginAccount(accountOrigin);
//		transaction.setDestinationAccount(accountDestiny);
//	}
//	private void prepareTransactionInternational(){
//		accountDestiny.setOriginCountry(Country.INGLATERRA);
//	}
//
//	private void prepareTransactionNational(){
//		accountDestiny.setOriginCountry(Country.ARGENTINA);
//	}
//	
//	private void prepareTransactionSameBank(){
//		accountDestiny.setBank(Bank.MACRO);
//	}
//	
//	private void prepareTransactionDifferentBank(){
//		accountDestiny.setBank(Bank.ICBC);
//	}
//}
