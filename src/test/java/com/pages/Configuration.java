package com.pages;

import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.base.BaseClass;
import com.utility.ConfigReader;
import com.utility.Elements;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class Configuration {
	private static final Logger log = LoggerHelper.getLogger(Configuration.class);
	WebDriver driver;
	Elements e;

	public Configuration() {
		this.driver = DriverManager.getDriver();
		e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@id='side_nav_container']//div[text()='Customers']")
	WebElement customerMenu;
	@FindBy(xpath = "//input[@placeholder='Search']")
	WebElement searchCustomer;
	@FindBy(xpath = "//div[@id='customer-card-container']")
	WebElement customerCardContainer;
	@FindBy(xpath = "//div[@id='customer-card-container']//div[text()='REWE']")
	WebElement reweCustomer;
	@FindBy(xpath = "//div[contains(@class,'MuiTabs-flexContainer')]/button[text()='Configurations']")
	WebElement configurations;
	@FindBy(xpath = "(//div[contains(text(),'OoW')]/following::div[@class='switch-container']/button)[1]")
	WebElement OoWToggle;
	@FindBy(xpath = "//div[contains(text(),'Logistics')]/following::div[@class='switch-container']/button")
	WebElement logisticsToggle;
	@FindBy(xpath = "//div[@id='main_container_wrapper']/div[1]")
	WebElement customersTitle;

	public void selectCustomerMenu() {
		e.click(customerMenu);

	}

	public void validateCustomerUrl() {
		e.getVisible(customersTitle);
		e.assertEqualsString(ConfigReader.getProperty("customerUrl"), e.getCurrentUrl());
	}

	public void SearchCustomer(String text) {
		log.info("Searching for customer: " + text);
		// Clear any existing search first
		e.clear(searchCustomer);
		// Wait a moment for clear to take effect
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}
		
		// Send search text
		e.sendText(searchCustomer, text);
		log.debug("Search text entered, waiting for results");
		
		// Wait for search results to appear (at least one result or no results message)
		try {
			BaseClass.getWait().until(ExpectedConditions.or(
				ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@id='customer-card-container']"), 1),
				ExpectedConditions.presenceOfElementLocated(By.xpath("//div[@id='customer-card-container']"))
			));
			// Additional wait for search to complete
			Thread.sleep(1000);
		} catch (Exception e) {
			log.warn("Search may still be processing, continuing...");
		}
	}

	public void validateSingleUser() {
		log.info("Validating single user search result");
		
		// Wait for search container to be visible
		e.getVisible(customerCardContainer);
		
		// Wait for exactly one customer card
		try {
			BaseClass.getWait().until(ExpectedConditions.numberOfElementsToBe(
				By.xpath("//div[@id='customer-card-container']"), 1));
			
			// Additional wait to ensure results are stable
			Thread.sleep(500);
			
			List<WebElement> cards = driver.findElements(By.xpath("//div[@id='customer-card-container']"));
			
			if (cards.size() != 1) {
				log.error("Expected 1 customer card, but found: " + cards.size());
				throw new AssertionError("Expected 1 customer card but found " + cards.size());
			}
			
			log.info("Single user validation successful - found exactly 1 customer");
			e.assertEqualsInt(cards.size(), 1);
		} catch (Exception e) {
			List<WebElement> cards = driver.findElements(By.xpath("//div[@id='customer-card-container']"));
			log.error("Validation failed. Found " + cards.size() + " customer cards");
			throw new RuntimeException("Failed to validate single user - found " + cards.size() + " cards", e);
		}
	}

	public void selectCustomer() {
		e.click(reweCustomer);
	}

	public void changeConfig() throws InterruptedException {
		log.info("Starting configuration changes");
		e.click(configurations);
		
		// Wait for configuration tabs to load
		Thread.sleep(1000);

		// Wait for toggles to be clickable
		e.getClickable(OoWToggle);
		e.getClickable(logisticsToggle);
		
		// Get initial states
		String oowInitialState = OoWToggle.getAttribute("aria-checked");
		String logisticsInitialState = logisticsToggle.getAttribute("aria-checked");
		log.debug("OoW toggle initial state: " + oowInitialState);
		log.debug("Logistics toggle initial state: " + logisticsInitialState);

		// Toggle OoW off
		log.info("Toggling OoW configuration off");
		e.toggleOffConfig(OoWToggle);

		// Wait for OoW toggle to update
		try {
			BaseClass.getWait()
					.until(ExpectedConditions.attributeToBe(
							By.xpath("(//div[contains(text(),'OoW')]/following::div[@class='switch-container']/button)[1]"),
							"aria-checked", "false"));
			log.info("OoW toggle successfully set to false");
			
			// Additional wait to ensure UI has updated
			Thread.sleep(500);
		} catch (Exception e) {
			String currentState = OoWToggle.getAttribute("aria-checked");
			log.error("OoW toggle did not update to false. Current state: " + currentState);
			throw new RuntimeException("OoW toggle failed to update", e);
		}
		
		// Toggle Logistics off
		log.info("Toggling Logistics configuration off");
		e.toggleOffConfig(logisticsToggle);
		
		// Wait for Logistics toggle to update
		try {
			BaseClass.getWait()
					.until(ExpectedConditions.attributeToBe(
							By.xpath("//div[contains(text(),'Logistics')]/following::div[@class='switch-container']/button"),
							"aria-checked", "false"));
			log.info("Logistics toggle successfully set to false");
			
			// Verify final state
			String logisticsFinalState = logisticsToggle.getAttribute("aria-checked");
			if (!"false".equalsIgnoreCase(logisticsFinalState)) {
				log.error("Logistics toggle final state is not false: " + logisticsFinalState);
				throw new AssertionError("Logistics configuration change failed");
			}
			
			log.info("Logistics configuration changed successfully");
		} catch (Exception e) {
			String currentState = logisticsToggle.getAttribute("aria-checked");
			log.error("Logistics toggle did not update to false. Current state: " + currentState);
			throw new RuntimeException("Logistics toggle failed to update", e);
		}
	}

}
