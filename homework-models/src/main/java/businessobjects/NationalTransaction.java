package businessobjects;

import java.math.BigDecimal;

import enums.Bank;

public class NationalTransaction extends Transaction {
	public static final BigDecimal fixedTaxNational = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(100));

	@Override
	public synchronized BigDecimal calcularImpuesto() {
		Bank originBank = this.getOriginAccount().getBank();
		Bank destinationBank = this.getDestinationAccount().getBank();
		BigDecimal amount = this.getMonto();
		if ( originBank != null 
				&&  destinationBank != null
				&& originBank.equals(destinationBank)){
			amount = amount.multiply(fixedTaxNational);
		}
		return amount;
	}
}
