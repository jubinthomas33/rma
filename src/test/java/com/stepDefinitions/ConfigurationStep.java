package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.Configuration;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;

public class ConfigurationStep {
	WebDriver driver;
	Configuration c;

	public ConfigurationStep() {
		this.driver = DriverManager.getDriver();
		this.c = new Configuration();

	}
	@Given("navigates to customer menu for configurational changes")
	public void navigates_to_customer_menu_for_configurational_changes() {
	    c.selectCustomerMenu();
	}

	@Then("validates the url")
	public void validates_the_url() {
		c.validateCustomerUrl();
	   
	}

	@Then("search for {string} customer")
	public void search_for_customer(String string) {
	   c.SearchCustomer(string);
	}

	@Then("now validates the single user")
	public void now_validates_the_single_user() {
	   c.validateSingleUser();
	}

	@Then("configure based on the requirement")
	public void configure_based_on_the_requirement() throws InterruptedException {
	   c.selectCustomer();
	   c.changeConfig();
	}



	
	
	    
	}