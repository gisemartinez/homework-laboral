package tests.bussinessobjects;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import businessobjects.Account;
import businessobjects.InternationalTransaction;
import businessobjects.NationalTransaction;
import businessobjects.Transaction;
import enums.Bank;

public class CalcularImpuestoTest {

	Account accountOrigin;
	Account accountDestiny;
	BigDecimal transferAmount;
	BigDecimal internationalTax;
	BigDecimal nationalTax;

	@Before
	public void setUp() {
		accountOrigin = new Account();
		accountDestiny = new Account();
		transferAmount = BigDecimal.valueOf(10000);
		internationalTax = BigDecimal.valueOf(5).divide(BigDecimal.valueOf(100));
		nationalTax = BigDecimal.valueOf(1).divide(BigDecimal.valueOf(100));
		accountOrigin.setBank(Bank.MACRO);
	}

	@Test
	public void calculoInternacional() {

		Transaction transaccionInternacional = new InternationalTransaction();
		transaccionInternacional.setMonto(transferAmount);

		BigDecimal expected = transferAmount.multiply(internationalTax);

		assertEquals(expected, transaccionInternacional.calcularImpuesto());
	}

	@Test
	public void calculoNacionalMismoBanco() {
		Transaction transaccionNacional = new NationalTransaction();
		accountDestiny.setBank(accountOrigin.getBank());

		transaccionNacional.setMonto(transferAmount);
		transaccionNacional.setDestinationAccount(accountDestiny);
		transaccionNacional.setOriginAccount(accountOrigin);

		BigDecimal expected = BigDecimal.ZERO;
		assertEquals(expected, transaccionNacional.calcularImpuesto());
	}

	@Test
	public void calculoNacionalDiferenteBanco() {
		Transaction transaccionNacional = new NationalTransaction();
		
		accountDestiny.setBank(Bank.JPMORGAN);

		transaccionNacional.setMonto(transferAmount);
		transaccionNacional.setDestinationAccount(accountDestiny);
		transaccionNacional.setOriginAccount(accountOrigin);

		BigDecimal expected = transferAmount.multiply(nationalTax);
		assertEquals(expected, transaccionNacional.calcularImpuesto());
	}
}
