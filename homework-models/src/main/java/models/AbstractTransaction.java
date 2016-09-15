package models;

import java.math.BigDecimal;

import businessobjects.Account;

public abstract class AbstractTransaction {
	
	private Account originAccount;
	private Account destinationAccount;
	private BigDecimal monto;
	
	
	public Account getOriginAccount() {
		return originAccount;
	}
	public void setOriginAccount(Account originAccount) {
		this.originAccount = originAccount;
	}
	public Account getDestinationAccount() {
		return destinationAccount;
	}
	public void setDestinationAccount(Account destinationAccount) {
		this.destinationAccount = destinationAccount;
	}
	public BigDecimal getMonto() {
		return monto;
	}
	public void setMonto(BigDecimal monto) {
		this.monto = monto;
	}

}