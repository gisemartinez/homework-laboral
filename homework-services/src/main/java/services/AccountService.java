package services;


import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import businessobjects.Account;


@Service
public class AccountService {
	
	public List<Account> getAccounts(){
		return fixedresources.Resources.ACCOUNTS;
	}
	
	public Account getAccountById(int id){
		
		return fixedresources.Resources.ACCOUNTS
				.stream()
				.filter(acc -> acc.getId() == id)
				.findFirst()
				.get();
	}

	public void updateCredit(int id, BigDecimal amount) {
	
		Account account = this.getAccountById(id);
			
		BigDecimal updatedAmount = account.getCredit().add(amount);

		account.setCredit(updatedAmount);
		
		fixedresources.Resources.ACCOUNTS.set(id, account);
	}

}
