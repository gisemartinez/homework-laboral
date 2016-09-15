package controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import businessobjects.Account;
import services.AccountService;

@Controller
@RequestMapping("/api")
public class AccountController {
	

	public final static Logger LOGGER = LogManager.getLogger(TransactionController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getTransactions(){
		LOGGER.debug("Entrando a metodo GET");
		List<Account> accounts = accountService.getAccounts();
        return new ResponseEntity<>(accounts,HttpStatus.ACCEPTED);
		
	}
	
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	public ResponseEntity<Account> getTransaction(int id){
		LOGGER.debug("Entrando a metodo GET");
		Account account = accountService.getAccountById(id);
        return new ResponseEntity<>(account,HttpStatus.ACCEPTED);
		
	}
}
