package test;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import service.SecretService;
import entity.Secret;
import exception.UnauthorizedException;

public class SecretServiceTest {

	@Autowired
	SecretService secretService;

	@Before
	public void setUp() throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"config.xml");

		secretService = (SecretService) context.getBean("secretService");

	}

	@Test(expected = UnauthorizedException.class)
	public void testA() {
		System.out.println("*****Test A*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.readSecret("Bob", secret);

	}

	@Test
	public void testB() {
		System.out.println("*****Test B*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.readSecret("Bob", secret);

	}

	@Test
	public void testC() {
		System.out.println("*****Test C*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Bob", secret, "Carl");
		secretService.readSecret("Carl", secret);
	}

	@Test(expected = UnauthorizedException.class)
	public void testD() {
		System.out.println("*****Test D*****");
		UUID aliceSecret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", aliceSecret, "Bob");
		UUID carlSecret = secretService.storeSecret("Carl", new Secret());
		secretService.shareSecret("Bob", carlSecret, "Alice");
	}

	@Test(expected = UnauthorizedException.class)
	public void testE() {
		System.out.println("*****Test E*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Alice", secret, "Carl");
		secretService.unshareSecret("Alice", secret, "Carl");
		secretService.readSecret("Carl", secret);
	}

	@Test(expected = UnauthorizedException.class)
	public void testF() {
		System.out.println("*****Test F*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Alice", secret, "Carl");
		secretService.shareSecret("Carl", secret, "Bob");
		secretService.unshareSecret("Alice", secret, "Bob");
		secretService.readSecret("Bob", secret);
	}

	@Test
	public void testG() {
		System.out.println("*****Test G*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Bob", secret, "Carl");
		secretService.unshareSecret("Bob", secret, "Carl");
		secretService.readSecret("Carl", secret);
	}

	@Test(expected = UnauthorizedException.class)
	public void testH() {
		System.out.println("*****Test H*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.unshareSecret("Carl", secret, "Bob");
	}

	@Test(expected = UnauthorizedException.class)
	public void testI() {
		System.out.println("*****Test I*****");
		UUID secret = secretService.storeSecret("Alice", new Secret());
		secretService.shareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Bob", secret, "Carl");
		secretService.unshareSecret("Alice", secret, "Bob");
		secretService.shareSecret("Bob", secret, "Carl");
	}

	@Test
	public void testJ() {
		System.out.println("*****Test J*****");
		Secret secret = new Secret();
		UUID secretId_one = secretService.storeSecret("Alice", secret);
		UUID secretId_two = secretService.storeSecret("Alice", secret);
		System.out.println("Secret Message one is: " + secretId_one);
		System.out.println("Secret Message two is: " + secretId_two);
	}
}
