/**
 * This package contains implementation of the services
 */
package serviceImpl;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import service.SecretService;
import entity.Secret;
import entity.Store;

public class SecretServiceImpl implements SecretService {

	Store store;
	Secret secret;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	public Secret getSecret() {
		return secret;
	}

	public void setSecret(Secret secret) {
		this.secret = secret;
	}

	/**
	 * Store a secrete in the service. A new “secret” record is already created,
	 * identified by randomly generated UUID, with the current user as the owner
	 * of the secret.
	 * 
	 * @param userId
	 *            the ID of the current user
	 * @param secret
	 *            the secret to be stored. No duplication or null check is
	 *            performed.
	 * @return always return a new UUID for the given secret
	 */

	public UUID storeSecret(String userId, Secret secret) {

		UUID secretId = UUID.randomUUID();
		secret.setSecretId(secretId);
		store.setUserSecret(userId, secretId);
		store.setSharedSecrets(userId, secretId);

		return secret.getSecretId();
	}

	/**
	 * Read a secret by ID
	 * 
	 * @param userId
	 *            the ID of the current user
	 * @param secretId
	 *            the ID of the secret being requested
	 * @return the requested secret
	 */
	public Secret readSecret(String userId, UUID secretId) {

		return null;
	}

	/**
	 * Share a secret with another user. The secret may not have been created by
	 * the current user.
	 * 
	 * @param userId
	 *            the ID of the current user
	 * @param secretId
	 *            the ID of the secret being shared
	 * @param targetUserId
	 *            the ID of the user to share the secret with
	 */
	public void shareSecret(String userId, UUID secretId, String targetUserId) {
		store.setSharedSecrets(targetUserId, secretId);
	}

	/**
	 * Unshare the current user's own secret with another user.
	 * 
	 * @param userId
	 *            the ID of the current user
	 * @param secretId
	 *            the ID of the secret being unshared
	 * @param targetUserId
	 *            the ID of the user to share the secret with
	 */
	public void unshareSecret(String userId, UUID secretId, String targetUserId) {

		Map<String, Set<UUID>> sharedSecrets = store.getSharedSecrets();
		Set<UUID> targetUserSharedSecretList = sharedSecrets.get(targetUserId);
		if (targetUserSharedSecretList != null) {
			Set<UUID> list = new LinkedHashSet<UUID>(targetUserSharedSecretList);
			list.remove(secretId);
			targetUserSharedSecretList = list;
			store.updateSharedSecrets(targetUserId, targetUserSharedSecretList);
		}
	}
}