package builders;

import java.math.BigDecimal;

import javax.naming.ServiceUnavailableException;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import businessobjects.Account;
import businessobjects.InternationalTransaction;
import businessobjects.NationalTransaction;
import businessobjects.Transaction;
import enums.ErrorMessages;
import fixedresources.Resources;
import services.AccountService;
import throwable.InexistentAccount;

/*
 * Caso ideal : Que me anduviera el autowired. Como no es un controller, 
 * y lo instancio para usarlo, spring no puede hacer la inyeccion de dependencia. 
 */
public class TransactionBuilder {
	
	public final static Logger LOGGER = LogManager.getLogger(TransactionBuilder.class);
	private Account accountOrigin;
	private Account accountDestiny;
	private BigDecimal amount;
	private Transaction transaction;
	
	@Autowired
	private AccountService accountService;
	
	public TransactionBuilder buildWithAmount(BigDecimal amount) {
		this.amount = amount;
		this.accountOrigin = new Account();
		this.accountDestiny = new Account();
		return this;

	}
	public TransactionBuilder buildWithOriginAccount(int id) throws InexistentAccount {

		try {
			this.accountOrigin = accountService.getAccountById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		if (!accountExists(this.accountOrigin)) {
			throw new InexistentAccount(ErrorMessages.CUENTA_ORIGEN_INEXISTENTE);
		}

		return this;
	}

	public TransactionBuilder buildWithDestinyAccount(int id) throws ServiceUnavailableException, InexistentAccount {

		try {
			this.accountDestiny = accountService.getAccountById(id);
		} catch (Exception e) {
			LOGGER.error(e.getMessage());
			throw e;
		}

		if (!accountExists(this.accountDestiny)) {
			throw new InexistentAccount(ErrorMessages.CUENTA_DESTINO_INEXISTENTE);
		}

		return this;
	}

	public Transaction build() {

		if (accountExists(this.accountOrigin) && accountExists(this.accountDestiny)) {

			if (isNationalTransaction(this.accountOrigin) && isNationalTransaction(this.accountDestiny)) {

				this.transaction = setData(new NationalTransaction());

			} else {

				this.transaction = setData(new InternationalTransaction());
			}
		} else {
			return null;
		}

		return this.transaction;

	}

	private Transaction setData(Transaction transaction) {
		transaction.setDestinationAccount(this.accountDestiny);
		transaction.setOriginAccount(this.accountOrigin);
		transaction.setMonto(this.amount);
		return transaction;
	}

	private boolean accountExists(Account account) {
		return null != account.getId();
	}

	private boolean isNationalTransaction(Account account) {
		return Resources.MY_COUNTRY.equals(account.getOriginCountry());
	}

}
