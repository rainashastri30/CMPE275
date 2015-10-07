package aspects;

import java.util.Set;
import java.util.UUID;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import entity.Store;
import exception.UnauthorizedException;

/**
 * This is an Aspect responsible for enabling access control and logging for
 * exchanges of messages between different users
 * 
 * @author Raina Shastri
 * 
 */
@Aspect
public class AccessAndLoggingControl {
	/**
	 * Stores secret messages created by each user. Also stores the list of
	 * secret messages shared with a particular user
	 */
	Store store;

	public Store getStore() {
		return store;
	}

	public void setStore(Store store) {
		this.store = store;
	}

	/**
	 * This advice logs the details of the secret message created by the owner.
	 * 
	 * @param jp
	 *            - JoinPoint
	 * @param secretId
	 *            - secret Message being shared
	 */
	@AfterReturning(pointcut = "execution(* service.SecretService.storeSecret(..))", returning = "secretId")
	public void storeMessage(JoinPoint jp, Object secretId) {
		String userId = (String) jp.getArgs()[0];
		System.out.println(userId + " creates a secret with ID " + secretId);
	}

	/**
	 * This advice checks if the user initiating the sharing of message is
	 * authorized to do so.
	 * 
	 * @param jp
	 *            - JoinPoint
	 * @throws Throwable
	 */
	@Around("execution(* service.SecretService.shareSecret(..))")
	private void shareMessage(ProceedingJoinPoint jp) throws Throwable {
		Set<UUID> sharedSecretList = null;
		String userId = (String) jp.getArgs()[0];
		String targerUserId = (String) jp.getArgs()[2];
		UUID secretId = (UUID) jp.getArgs()[1];
		sharedSecretList = store.getSharedSecrets().get(userId);
		try {
			System.out.println(userId + " shares the secret of ID " + secretId
					+ " with " + targerUserId);
			if (sharedSecretList != null && sharedSecretList.contains(secretId)) {

				jp.proceed();

			} else {
				throw new UnauthorizedException();
			}
		} catch (UnauthorizedException e) {
			System.out.println(userId
					+ " does not have privileges to share the message");
			throw new UnauthorizedException();

		}
	}

	/**
	 * This advice checks if the user is authorized to read the secret message.
	 * 
	 * @param jp
	 *            - JoinPoint
	 * @throws Throwable
	 */
	@Around("execution(* service.SecretService.readSecret(..))")
	private void readMessage(ProceedingJoinPoint jp) throws Throwable {
		Set<UUID> idList = null;
		String userId = (String) jp.getArgs()[0];
		UUID secretId = (UUID) jp.getArgs()[1];
		idList = store.getSharedSecrets().get(userId);
		try {
			System.out.println(userId + " reads the secret of ID " + secretId);
			if (idList != null && idList.contains(secretId)) {
				jp.proceed();
			} else {
				throw new UnauthorizedException();
			}
		} catch (UnauthorizedException e) {
			System.out.println(userId
					+ " does not have privileges to read the message");
			throw new UnauthorizedException();
		}
	}

	/**
	 * This advice checks if the user is authorized to unshare the secret
	 * message.
	 * 
	 * @param jp
	 *            - JoinPoint
	 * @throws Throwable
	 */
	@Around("execution(void service.SecretService.unshareSecret(..))")
	private void unshareMessage(ProceedingJoinPoint jp) throws Throwable {
		Set<UUID> userSecretsList = null;
		Set<UUID> sharedSecretList = null;
		String userId = (String) jp.getArgs()[0];
		String targetUserId = (String) jp.getArgs()[2];
		UUID secretId = (UUID) jp.getArgs()[1];
		sharedSecretList = store.getSharedSecrets().get(userId);

		try {
			System.out.println(userId + " unshares the secret of ID "
					+ secretId + " with " + targetUserId);
			if (sharedSecretList != null
					&& (sharedSecretList.contains(secretId))) {
				userSecretsList = store.getUserSecret().get(userId);
				if (userSecretsList != null
						&& userSecretsList.contains(secretId)) {
					jp.proceed();
				}
			} else {
				throw new UnauthorizedException();
			}
		} catch (UnauthorizedException e) {

			System.out.println(userId
					+ " does not have privileges to unshare the message");
			throw new UnauthorizedException();
		}
	}
}
