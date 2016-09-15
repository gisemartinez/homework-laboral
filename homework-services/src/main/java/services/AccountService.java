package services;


import java.util.List;

import org.springframework.stereotype.Component;

import businessobjects.Account;


@Component
public class AccountService {
	
	
	
	public List<Account> getAccounts(){
		return fixedresources.Resources.ACCOUNTS;
	}
	
	public Account getAccountById(int id){
		return fixedresources.Resources.ACCOUNTS.get(id);
	}

	public void updateCredit(int id, long amount) {
		Account account = this.getAccountById(id);
		Long updatedAmount = account.getCredit() + amount;
		account.setCredit(updatedAmount);
		fixedresources.Resources.ACCOUNTS.set(id, account);
	}

}
