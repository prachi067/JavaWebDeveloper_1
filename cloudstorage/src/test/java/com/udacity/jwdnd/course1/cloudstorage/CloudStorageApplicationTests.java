package com.udacity.jwdnd.course1.cloudstorage;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.beans.factory.annotation.Autowired;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	@Autowired
	private CredentialsService credentialService;

	@Autowired
	private EncryptionService encryptionService;

	private String baseURL;
	private static WebDriver driver;
	private SignupPage signupPage;
	private LoginPage loginPage;
	private HomePage homePage;
	private ResultPage resultPage;
	private Credentials credentials;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}

	@AfterAll
	public static void afterAll() {
		driver.quit();
	}

	@BeforeEach
	public void beforeEach() {
		baseURL = "http://localhost:" + port;
	}

	@Test
	@Order(1)
	public void verifyUnauthorizedUserAccess() {
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	@Order(2)
	public void verifySignupLoginLogout() throws InterruptedException {
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signupUser("Prachi", "Jain", "prachjai", "pass");
		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginUser("prachjai", "pass");
		Assertions.assertEquals("Home", driver.getTitle());
		homePage = new HomePage(driver);
		Thread.sleep(1000);
		homePage.logoutUser();
		Assertions.assertEquals("Login", driver.getTitle());
		driver.get(baseURL + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	public HomePage signupAndLogin(){
		driver.get(baseURL + "/signup");
		signupPage = new SignupPage(driver);
		signupPage.signupUser("Prachi", "Jain", "prachjai", "pass");
		driver.get(baseURL + "/login");
		loginPage = new LoginPage(driver);
		loginPage.loginUser("prachjai", "pass");
		return new HomePage(driver);
	}

	@Test
	@Order(3)
	public void verifyNoteCreation() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);
		homePage.clickAddaNewNote();
		Thread.sleep(1000);
		homePage.addNoteDetails("Test Title","Test Description");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals("Test Title", homePage.getNoteTitleText());
		Assertions.assertEquals("Test Description", homePage.getNoteDescriptionText());
	}

	@Test
	@Order(4)
	public void verifyNoteEdit() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);
		homePage.clickEditNote();
		Thread.sleep(1000);
		homePage.addNoteDetails("Test Title Update","Test Description Update");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);
		Assertions.assertEquals("Test Title Update", homePage.getNoteTitleText());
		Assertions.assertEquals("Test Description Update", homePage.getNoteDescriptionText());
	}

	@Test
	@Order(5)
	public void verifyNoteDelete() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);
		homePage.clickDeleteNote();
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getNotesTab();
		Thread.sleep(1000);

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			homePage.getNoteTitleText();
		});

	}

	@Test
	@Order(6)
	public void verifyCredentialCreation() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);
		homePage.clickAddaNewCredential();
		Thread.sleep(1000);
		homePage.addCredentialDetails("TestUrl","TestUsername", "TestPassword");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);

	    credentials = this.credentialService.getCredentialById(1);
		Assertions.assertEquals("TestUrl", homePage.getCredentialUrlText());
		Assertions.assertEquals("TestUsername", homePage.getCredentialUsernameText());
		Assertions.assertEquals(this.encryptionService.encryptValue("TestPassword", credentials.getKey()), homePage.getCredentialPasswordText());
	}

	@Test
	@Order(7)
	public void verifyCredentialEdit() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);
		homePage.clickEditCredential();
		Thread.sleep(1000);
		homePage.addCredentialDetails("TestUrlUpdate","TestUsernameUpdate", "TestPasswordUpdate");
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);

		credentials = this.credentialService.getCredentialById(1);
		Assertions.assertEquals("TestUrlUpdate", homePage.getCredentialUrlText());
		Assertions.assertEquals("TestUsernameUpdate", homePage.getCredentialUsernameText());
		Assertions.assertEquals(this.encryptionService.encryptValue("TestPasswordUpdate", credentials.getKey()), homePage.getCredentialPasswordText());
	}

	@Test
	@Order(8)
	public void verifyCredentialDelete() throws InterruptedException {
		homePage = signupAndLogin();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);
		homePage.clickDeleteCredential();
		Thread.sleep(1000);

		resultPage = new ResultPage(driver);
		resultPage.goToHomePage();
		Thread.sleep(1000);

		homePage.getCredentialsTab();
		Thread.sleep(1000);

		Assertions.assertThrows(NoSuchElementException.class, () -> {
			homePage.getCredentialUrlText();
		});

	}
}
