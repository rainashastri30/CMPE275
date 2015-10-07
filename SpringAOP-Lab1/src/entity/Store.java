package entity;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * This class stores the user generated secreted messages and the secret message
 * shared with him.
 * 
 * @author Raina Shastri
 * 
 */
public class Store {

	private Map<String, Set<UUID>> userSecret;
	private Map<String, Set<UUID>> sharedSecrets;

	public Store() {

		userSecret = new HashMap<String, Set<UUID>>();
		sharedSecrets = new HashMap<String, Set<UUID>>();
	}

	/**
	 * 
	 * 
	 * @return user Generated secrets
	 */
	public Map<String, Set<UUID>> getUserSecret() {
		return userSecret;
	}

	/**
	 * Stores the user generated secret message.
	 * 
	 * @param userId
	 *            Id of the user
	 * @param secret
	 *            secret message being shared
	 */
	public void setUserSecret(String userId, UUID secret) {
		Set<UUID> uuidList = userSecret.get(userId);
		if (uuidList != null) {
			if (!uuidList.contains(secret)) {
				uuidList.add(secret);
				userSecret.put(userId, uuidList);
			}
		} else {
			uuidList = new LinkedHashSet<UUID>();
			uuidList.add(secret);
			userSecret.put(userId, uuidList);
		}

	}

	/**
	 * 
	 * 
	 * @return sharedSecrets secret messages shared with a user
	 */
	public Map<String, Set<UUID>> getSharedSecrets() {
		return sharedSecrets;
	}

	/**
	 * Stores secret messages shared with a user
	 * 
	 * @param userId
	 *            Id of the user
	 * @param secret
	 *            secret message being shared
	 */
	public void setSharedSecrets(String userId, UUID secret) {
		Set<UUID> uuidList = sharedSecrets.get(userId);
		if (uuidList != null) {
			if (!uuidList.contains(secret)) {
				uuidList.add(secret);
				sharedSecrets.put(userId, uuidList);
			}
		} else {
			uuidList = new LinkedHashSet<UUID>();
			uuidList.add(secret);
			sharedSecrets.put(userId, uuidList);
		}
	}

	/**
	 * Updated the list of secret messages shared with user.
	 * 
	 * @param userId
	 *            Id of the user
	 * @param secretList
	 *            list of secret messages
	 */
	public void updateSharedSecrets(String userId, Set<UUID> secretList) {
		sharedSecrets.put(userId, secretList);

	}

}
