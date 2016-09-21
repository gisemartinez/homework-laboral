package models;

import java.math.BigDecimal;

import org.springframework.core.style.ToStringCreator;
import org.springframework.data.annotation.Id;

import enums.Bank;
import enums.Country;

public abstract class AbstractAccount {
	
	@Id
	private Integer id;
	
	private Bank bank;
	private Country originCountry;
	private BigDecimal credit;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	public Bank getBank() {
		return bank;
	}
	public void setBank(Bank bank) {
		this.bank = bank;
	}
	public Country getOriginCountry() {
		return originCountry;
	}
	public void setOriginCountry(Country originCountry) {
		this.originCountry = originCountry;
	}
	
	public  String toString(){
		ToStringCreator builder = new ToStringCreator( this );
		builder.append("id",this.getId());
	    builder.append( "credit", this.getCredit());
	    builder.append( "bank", this.getBank() );
	    builder.append( "country", this.getOriginCountry() );
	    return builder.toString();
	}
}
