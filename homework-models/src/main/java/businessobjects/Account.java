package businessobjects;

import models.AbstractAccount;

public class Account extends AbstractAccount {
	
	public boolean isDebitLessThanActualCredit(Long montoDebito) {
	
		return this.getCredit().compareTo(montoDebito) >= 0;
	};
}


