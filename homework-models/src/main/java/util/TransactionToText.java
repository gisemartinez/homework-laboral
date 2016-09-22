package util;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Date;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import businessobjects.Account;
import enums.TransactionStatus;

public class TransactionToText {
	private Date fecha;
	private String cuentaOrigen;
	private String cuentaDestino;
	private TransactionStatus transactionStatus;
	public static final Path PATH = Paths.get("src/main/resources/transacciones.txt");
	public static final Logger LOGGER = LogManager.getLogger(TransactionToText.class);

	public TransactionToText(Account cuentaOrigen, Account cuentaDestino, TransactionStatus transactionStatus) {
		this.fecha = new Date();
		this.cuentaOrigen = cuentaOrigen.toString();
		this.cuentaDestino = cuentaDestino.toString();
		this.transactionStatus = transactionStatus;
	}

	public void writeFile() {
		try {
			BufferedWriter writer = Files.newBufferedWriter(PATH, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			writer.write(this.fecha.toString() + "\t");
			writer.write(this.cuentaOrigen + "\t");
			writer.write(this.cuentaDestino + "\t");
			writer.write(this.transactionStatus + "\t");
			writer.write("\n");
			writer.flush();
			writer.close();
		} catch (Exception e) {
			LOGGER.error(e);
		}

	}

	public String getCuentaOrigen() {
		return cuentaOrigen;
	}

	public void setCuentaOrigen(String cuentaOrigen) {
		this.cuentaOrigen = cuentaOrigen;
	}

	public String getCuentaDestino() {
		return cuentaDestino;
	}

	public void setCuentaDestino(String cuentaDestino) {
		this.cuentaDestino = cuentaDestino;
	}

	public TransactionStatus getTransactionStatus() {
		return transactionStatus;
	}

	public TransactionToText setTransactionStatus(TransactionStatus transactionStatus) {
		this.transactionStatus = transactionStatus;
		return this;
	}
}
