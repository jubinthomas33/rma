package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.LoginPage;
import com.utility.Elements;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.*;

public class LoginPageStep {
	WebDriver driver;
	LoginPage loginpage;
	Elements elements;

	public LoginPageStep() {
	}

	private void initializeObjects() {
		if (this.driver == null) {
			this.driver = DriverManager.getDriver();
		}
		if (this.loginpage == null) {
			this.loginpage = new LoginPage();
		}
		if (this.elements == null) {
			this.elements = new Elements();
		}
	}

	@Given("user is on the login page")
	public void user_is_on_the_login_page() {
		initializeObjects();
		if (loginpage != null) {
			loginpage.goToUrl();
		}
	}

	@Then("user enters valid credentials and logs in")
	public void user_enters_valid_credentials_and_logs_in() {
		initializeObjects();
		if (loginpage != null) {
			loginpage.sendCredentials();
		}
	}

}
