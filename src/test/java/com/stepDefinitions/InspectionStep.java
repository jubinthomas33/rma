package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.Inspection;
import com.webdrivermanager.DriverManager;

import io.cucumber.java.en.Given;

public class InspectionStep {
	WebDriver driver;
	Inspection inspection;

	public InspectionStep() {
		this.driver = DriverManager.getDriver();
		this.inspection = new Inspection();

	}
	
	@Given("fill in the corresponding tags and respective defects")
	public void fill_in_the_corresponding_tags_and_respective_defects() throws InterruptedException {
	  
	   
	    inspection.fillFirstRow();
	   
	}
}