package throwable;

import enums.ErrorMessages;

public class InexistentAccount extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InexistentAccount(ErrorMessages message) {
		super(message.toString());
	}

}
