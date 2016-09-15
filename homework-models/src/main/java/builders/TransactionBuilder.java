package builders;

import businessobjects.Account;
import businessobjects.Transaction;
import dtos.TransactionDto;

public class TransactionBuilder {
	private Transaction transaction;
	private TransactionDto transactionDto;
	
	public TransactionBuilder(Transaction transaction, TransactionDto dto){
		this.transaction = transaction;
		this.transaction.setMonto(dto.getMonto());
		this.transactionDto = dto;
	}
	public TransactionBuilder buildWithAccounts(){
		
		Account destinationAcc = new Account();
		Account originAcc = new Account();
		
		destinationAcc.setId(transactionDto.getDestinationAccount().getId());
		originAcc.setId(transactionDto.getOriginAccount().getId());
		
		this.transaction.setDestinationAccount(destinationAcc);
		this.transaction.setOriginAccount(originAcc);
		return this;
	}
	public Transaction build(){
		return this.transaction;
	}

}
