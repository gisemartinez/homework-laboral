package fixedresources;

import java.math.BigDecimal;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

import asinchronysm.TransactionCallable;
import businessobjects.Account;
import enums.Bank;
import enums.Country;

public class Resources {
	
	public static List<Account> ACCOUNTS = loadInMemoryData();
	public static final Country MY_COUNTRY = Country.ARGENTINA;
	public static final Deque<TransactionCallable> TRANSACTION_QUEUE = new ArrayDeque<>();
	
	
	
	private static List<Account> loadInMemoryData() {
		Account accountA = new Account();		
		Account accountB = new Account();
		Account accountC = new Account();
		accountA.setId(1);
		accountB.setId(2);;
		accountC.setId(3);;
		accountA.setBank(Bank.ICBC);
		accountB.setBank(Bank.ICBC);
		accountC.setBank(Bank.JPMORGAN);
		accountA.setOriginCountry(Country.ARGENTINA);
		accountB.setOriginCountry(Country.ARGENTINA);
		accountC.setOriginCountry(Country.JAPON);
		accountA.setCredit(BigDecimal.valueOf(10000));
		accountB.setCredit(BigDecimal.valueOf(1000));
		accountC.setCredit(BigDecimal.valueOf(100));
		return Arrays.asList(accountA,accountB,accountC);
	}
	
}
