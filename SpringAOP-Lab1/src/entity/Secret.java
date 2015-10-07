package entity;

import java.util.UUID;

/**
 * This is generates a unique secret message.
 * 
 * @author Raina Shastri
 * 
 */
public class Secret {

	private UUID secretId;


	/**
	 * 
	 * @return secret message
	 */
	public UUID getSecretId() {
		return secretId;
	}

	/**
	 * Stores the secret messgage
	 * 
	 * @param secretId
	 *            secret message
	 */
	public void setSecretId(UUID secretId) {
		this.secretId = secretId;
	}

}
