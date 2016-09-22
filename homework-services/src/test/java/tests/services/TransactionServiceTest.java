package tests.services;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import businessobjects.Account;
import dtos.TransactionDto;
import enums.Bank;
import enums.Country;
import services.TransactionThreadService;
@Ignore
public class TransactionServiceTest{
	
	@Mock
	private TransactionThreadService transactionThreadService;
	
	TransactionDto transactionDto;
	Account accountOrigin;
	Account accountDestiny;
	
	@Before
	public void setUp(){
		//seteo todos los parametros de cuenta origen
		transactionDto = new TransactionDto();
		accountOrigin = new Account();
		accountOrigin.setOriginCountry(Country.ARGENTINA);
		accountOrigin.setBank(Bank.MACRO);
		accountOrigin.setCredit(new BigDecimal(200));
		//seteo algunos parametros de cuenta destino
		accountDestiny = new Account();
		accountDestiny.setCredit(new BigDecimal(100));
	}

	@Test
	public void calculateTaxesInternationalSameBank(){
		
		prepareTransactionInternational();
		
		prepareTransactionSameBank();
		
		buildTransaction();
		
		transactionThreadService.receiveAndProcessTransactions(transactionDto);

		
		assertEquals(new Long(200), accountDestiny.getCredit());
		assertEquals(new Long(95), accountOrigin.getCredit());
	}
	
	@Test
	public void calculateTaxesInternationalDifferentBank(){
		
		prepareTransactionInternational();
		
		prepareTransactionDifferentBank();
		
		buildTransaction();
		
		transactionThreadService.receiveAndProcessTransactions(transactionDto);
	}
	@Test
	public void calculateTaxesNationalDifferentBank(){
		
		prepareTransactionNational();
		prepareTransactionDifferentBank();
		buildTransaction();
		transactionThreadService.receiveAndProcessTransactions(transactionDto);
	}
	
	@Test
	public void calculateTaxesNationalSameBank(){
		
		prepareTransactionNational();
		prepareTransactionSameBank();
		buildTransaction();
		
		transactionThreadService.receiveAndProcessTransactions(transactionDto);
	}
	private void buildTransaction(){
		transactionDto.setOriginAccount(accountOrigin);
		transactionDto.setDestinationAccount(accountDestiny);
	}
	private void prepareTransactionInternational(){
		accountDestiny.setOriginCountry(Country.INGLATERRA);
	}

	private void prepareTransactionNational(){
		accountDestiny.setOriginCountry(Country.ARGENTINA);
	}
	
	private void prepareTransactionSameBank(){
		accountDestiny.setBank(Bank.MACRO);
	}
	
	private void prepareTransactionDifferentBank(){
		accountDestiny.setBank(Bank.ICBC);
	}
}
