package businessobjects;

import java.math.BigDecimal;

import org.springframework.core.style.ToStringCreator;

import models.AbstractTransaction;

public abstract class Transaction extends AbstractTransaction {

	public abstract BigDecimal calcularImpuesto();

	@Override
	public String toString() {
		ToStringCreator builder = new ToStringCreator(this);
		builder.append("accountOrigin", this.getOriginAccount());
		builder.append("accountDestination", this.getDestinationAccount());
		builder.append("ammount", this.getMonto());
		return builder.toString();
	}

}
