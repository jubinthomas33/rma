package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.LoginPage;
import com.utility.Elements;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.*;

public class LoginPageStep {
	WebDriver driver;
	LoginPage lp;
	Elements e;

	public LoginPageStep() {
		// Driver will be initialized by @Before hook in Hooks class
		// Initialize page objects lazily when needed
	}

	private void initializeObjects() {
		if (this.driver == null) {
			this.driver = DriverManager.getDriver();
		}
		if (this.lp == null) {
			this.lp = new LoginPage();
		}
		if (this.e == null) {
			this.e = new Elements();
		}
	}

	@Given("user is on the login page")
	public void user_is_on_the_login_page() {
		initializeObjects();
		if (lp != null) {
			lp.goToUrl();
		}
	}

	@Then("user enters valid credentials and logs in")
	public void user_enters_valid_credentials_and_logs_in() {
		initializeObjects();
		if (lp != null) {
			lp.sendCredentials();
		}
	}

}
