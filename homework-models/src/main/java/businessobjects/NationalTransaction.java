package businessobjects;

import java.math.BigDecimal;

import enums.Bank;

public class NationalTransaction extends Transaction {
	public static final BigDecimal fixedTaxNational = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(100));

	@Override
	public synchronized BigDecimal calcularImpuesto() {
		
		Bank originBank = this.getOriginAccount().getBank();
		
		Bank destinationBank = this.getDestinationAccount().getBank();
		
		BigDecimal amountToCalculate = this.getMonto();
		
		BigDecimal impuesto = amountToCalculate.multiply(fixedTaxNational);
		
		if ( originBank != null 
				&&  destinationBank != null
				&& originBank.equals(destinationBank))
			impuesto = BigDecimal.ZERO;
			
		return impuesto;
	}
}
