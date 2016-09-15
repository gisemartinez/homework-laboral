package businessobjects;

public class InternationalTransaction extends Transaction {
	

	public static final Long fixedTax = Long.valueOf(5)/Long.valueOf(100);

	@Override
	public synchronized Long calcularImpuesto() {
		return  this.getMonto() * fixedTax;
	}


}
