package controllers;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import businessobjects.Account;
import dtos.AmountDto;
import services.AccountService;

@Controller
@RequestMapping("/api")
public class AccountController {
	

	public final static Logger LOGGER = LogManager.getLogger(TransactionController.class);
	
	@Autowired
	private AccountService accountService;
	
	@RequestMapping(value = "/accounts", method = RequestMethod.GET)
	public ResponseEntity<List<Account>> getAccounts(){

		List<Account> accounts = accountService.getAccounts();
        return new ResponseEntity<>(accounts,HttpStatus.ACCEPTED);
		
	}
	/*Endpoint solo para probar*/
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<Account> getAccount(@PathVariable("id") int id){
		
		ResponseEntity<Account> response; 
		try {
			Account account = accountService.getAccountById(id);
			response = new ResponseEntity<>(account,HttpStatus.ACCEPTED);
		} catch (Exception e) {
			LOGGER.error(e);
			response = new ResponseEntity<>(HttpStatus.CONFLICT);
		}
		
        return response;
		
	}
	/*Endpoint solo para probar*/
	@RequestMapping(value = "/accounts/{id}", method = RequestMethod.POST)
	public ResponseEntity<Void> updateAccount(@PathVariable("id") int id, @RequestBody AmountDto monto){
		
		ResponseEntity<Void> response;
		try{
			accountService.updateCredit(id, monto.getAmount());
			response = new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception e){
			LOGGER.error(e);
			response = new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
		}
        return response;
		
	}
}
