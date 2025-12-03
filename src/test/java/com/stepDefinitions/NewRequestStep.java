package com.stepDefinitions;

import org.openqa.selenium.WebDriver;
import com.pages.NewRequest;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class NewRequestStep {
	WebDriver driver;
	NewRequest nR;

	public NewRequestStep() {
		this.driver = DriverManager.getDriver();
		this.nR = new NewRequest();

	}

	@Then("now navigate to the new Request")
	public void now_navigate_to_the_new_request() {
	   nR.navigateToNewRequest();
	}       
	

	@Then("Fill in the required fields")
	public void fill_in_the_required_fields() throws InterruptedException {
	    nR.fillGeneralInfo();
	    nR.enterReferenceNumber();
	    nR.enterDate();
	    nR.enterForwarder();
	    nR.enterTrackingNumber();
	    nR.cardBoardCheck();
	    nR.enterDisplays("3");
	    nR.enterBoxes("10");
	    nR.enterPallets("1");
	    nR.clickOnFailureReason();
	    nR.enterFailure();
	    nR.submitTicket();
	    nR.goToTicketList();
	    
	}

}