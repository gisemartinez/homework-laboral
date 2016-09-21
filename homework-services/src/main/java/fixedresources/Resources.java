package fixedresources;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

import asinchronysm.TransactionConsumerCallable;
import businessobjects.Account;
import enums.Bank;
import enums.Country;

public class Resources {
	
	public static List<Account> ACCOUNTS = loadInMemoryData();
	public static final Country MY_COUNTRY = Country.ARGENTINA;
	public static final LinkedBlockingQueue<TransactionConsumerCallable> TRANSACTION_QUEUE = new LinkedBlockingQueue<>();
	
	
	
	private static List<Account> loadInMemoryData() {
		Account accountA = new Account();		
		Account accountB = new Account();
		Account accountC = new Account();
		accountA.setId(0);
		accountB.setId(1);;
		accountC.setId(2);;
		accountA.setBank(Bank.ICBC);
		accountB.setBank(Bank.ICBC);
		accountC.setBank(Bank.JPMORGAN);
		accountA.setOriginCountry(Country.ARGENTINA);
		accountB.setOriginCountry(Country.ARGENTINA);
		accountC.setOriginCountry(Country.JAPON);
		accountA.setCredit(BigDecimal.valueOf(10000));
		accountB.setCredit(BigDecimal.valueOf(1000));
		accountC.setCredit(BigDecimal.valueOf(100));
		List<Account> list = new ArrayList<>();
		list.add(accountA.getId(), accountA);
		list.add(accountB.getId(), accountB);
		list.add(accountC.getId(), accountC);
		return list;
	}
	
}
