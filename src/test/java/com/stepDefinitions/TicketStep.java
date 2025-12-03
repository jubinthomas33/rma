package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.Ticket;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class TicketStep {
	WebDriver driver;
	Ticket ticket;

	public TicketStep() {
		this.driver = DriverManager.getDriver();
		this.ticket = new Ticket();

	}

	@Given("Find the the new ticket")
	public void find_the_the_new_ticket() {
		ticket.searchRefNumber();
		
	}

	@Then("navigate to Inspection phase")
	public void navigate_to_inspection_phase() {

		ticket.enterArrivalDate();
		ticket.navigateToInspection();
	}

}