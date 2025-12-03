package com.stepDefinitions;

import org.openqa.selenium.WebDriver;

import com.pages.Attachments;
import com.webdrivermanager.DriverManager;

public class AttachmentsStep {
	WebDriver driver;
	Attachments attachments;

	public AttachmentsStep() {
		this.driver = DriverManager.getDriver();
		this.attachments = new Attachments();

	}
}