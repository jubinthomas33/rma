package com.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utility.ConfigReader;
import com.utility.Elements;
import com.utility.HttpRequest;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class LoginPage {
	private static final Logger log = LoggerHelper.getLogger(LoginPage.class);
	WebDriver driver;
	Elements e;

	public LoginPage() {
		this.driver = DriverManager.getDriver();
		this.e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(id = "username")
	WebElement username;
	@FindBy(id = "password")
	WebElement password;
	@FindBy(id = "kc-login")
	WebElement login;

	
	public void goToUrl() {
		int statusCode=HttpRequest.getStatusCode(ConfigReader.getProperty("url"));
		log.info("Status code: "+ statusCode);
		e.goToUrl(ConfigReader.getProperty("url"));
	}
	public void sendCredentials() {

		String user = ConfigReader.getProperty("AdminUser");
		String pass = ConfigReader.getProperty("AdminPassword");
		
		log.info("Entering credentials for user: "+ user );
		e.sendText(username, user);
		e.sendText(password, pass);
		e.click(login);
		
		log.info("Clicked login");
		
	}

}
