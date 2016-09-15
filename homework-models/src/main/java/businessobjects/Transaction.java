package businessobjects;

import org.springframework.core.style.ToStringCreator;

import enums.TransactionStatus;
import models.AbstractTransaction;

public abstract class Transaction extends AbstractTransaction{
	
	
	private TransactionStatus status;
	
	public abstract Long calcularImpuesto();

	public TransactionStatus getStatus() {
		return status;
	}

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}
	public  String toString(){
		ToStringCreator builder = new ToStringCreator( this );
	    builder.append( "accountOrigin", this.getOriginAccount() );
	    builder.append( "accountDestination", this.getDestinationAccount() );
	    builder.append( "status", this.getStatus());
	    builder.append( "ammount", this.getMonto());
	    return builder.toString();
	}

}
