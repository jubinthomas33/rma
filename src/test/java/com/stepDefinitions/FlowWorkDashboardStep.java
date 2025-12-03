package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.FlowWorkDashboard;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class FlowWorkDashboardStep {
	WebDriver driver;
	FlowWorkDashboard rma;

	public FlowWorkDashboardStep() {
		this.driver = DriverManager.getDriver();
		this.rma = new FlowWorkDashboard();

	}
	@Then("user validates the dashboard url")
	public void user_validates_the_dashboard_url() {
	   rma.validateFlowWorkDashboard();
	}

	@Then("navigate to the RMA tool")
	public void navigate_to_the_rma_tool() {
	    rma.navigateToRMA();
	}

	@Then("user validates the rma tool dashboard url")
	public void user_validates_the_rma_tool_dashboard_url() {
	  rma.validateRmaDashboard();
	}

	

}