package com.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utility.ConfigReader;
import com.utility.Elements;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class FlowWorkDashboard {
	private static final Logger log = LoggerHelper.getLogger(FlowWorkDashboard.class);
	WebDriver driver;
	Elements e;

	public FlowWorkDashboard() {
		this.driver = DriverManager.getDriver();
		e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//header[contains(@id,'header_header')]//div[contains(@class,'header_container')]/button[3]")
	WebElement bentoMenu;
	@FindBy(xpath = "//a[@href='/return-management/dashboard']")
	WebElement RMATool;
	@FindBy(xpath = "//div[@class='header-text ']")
	WebElement headerText;
	@FindBy(xpath = "//div[@id='side_nav_container']")
	WebElement menuBar;

	public void validateFlowWorkDashboard() {
		try {
			e.getVisible(headerText);
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}

		try {
			if (!headerText.isDisplayed())
				driver.navigate().refresh();
		} catch (Exception ex) {
			log.info(ex.getMessage());
		}

		e.assertEqualsString(ConfigReader.getProperty("flowWorkDashboardUrl"), e.getCurrentUrl());
		log.info("FlowWork.ai Dashboard validated");
	}

	public void navigateToRMA() {
		e.click(bentoMenu);
		e.javaScrollAndClick(RMATool);
	}

	public void validateRmaDashboard() {
		e.getVisible(menuBar);
		e.assertEqualsString(ConfigReader.getProperty("RmaDashboardUrl"), e.getCurrentUrl());
		log.info("RMA Dashboard validated");
		e.assertEqualsString(ConfigReader.getProperty("RMADashboardTitle"), e.getTitle());
		log.info("Menu appearing successfully");
		log.info("RMA dashboard validation successful");

	}

}
