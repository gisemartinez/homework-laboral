package dtos;

import java.util.Date;

import businessobjects.Transaction;

public class TransactionToText {
	
	private String line;
	
	public static final String TAB = "       ";
	public TransactionToText(Transaction transaction){
		 StringBuilder lineBuild = new StringBuilder();

		 lineBuild.append("\n");
		 lineBuild.append(new Date());
		 lineBuild.append(TAB);
		 lineBuild.append(transaction.toString());
		 lineBuild.append("\n");
		 this.line = lineBuild.toString();
	
	}
	public String getLine() {
		return line;
	}
}
