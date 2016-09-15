package businessobjects;

import java.math.BigDecimal;

public class InternationalTransaction extends Transaction {
	

	public static final BigDecimal fixedTax = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(100));

	@Override
	public synchronized BigDecimal calcularImpuesto() {
		return  this.getMonto().multiply(fixedTax);
	}


}
