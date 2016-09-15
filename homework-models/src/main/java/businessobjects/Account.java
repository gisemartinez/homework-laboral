package businessobjects;

import java.math.BigDecimal;

import models.AbstractAccount;

public class Account extends AbstractAccount {
	
	public boolean isDebitLessThanActualCredit(BigDecimal montoDebito) {
	
		return this.getCredit().compareTo(montoDebito) >= 0;
	};
}


