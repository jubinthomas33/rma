package com.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utility.Elements;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class Attachments {
	WebDriver driver;
	Elements util;
	 private static final Logger log = LoggerHelper.getLogger(Attachments.class);

	public Attachments() {
		this.driver = DriverManager.getDriver();
		util = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath="//div[@class='upload-dropzone ']") WebElement upload;
	@FindBy(xpath="//button[text()='Submit Inspection']") WebElement SubmitInspectionButton;
	@FindBy(xpath="//button[text()='Okay']") WebElement OkayButton;
	
	
	


	
}
