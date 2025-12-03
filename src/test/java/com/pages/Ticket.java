package com.pages;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.utility.Elements;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class Ticket {
	private static final Logger log = LoggerHelper.getLogger(Ticket.class);
	WebDriver driver;
	Elements e;
	NewRequest newRequest;

	public Ticket() {
		this.driver = DriverManager.getDriver();
		e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@class='d-flex align-items-center']/div[@id='e2e_design_textfield']//input[contains(@placeholder,'RMA No')]")
	WebElement searchRefNo;
	@FindBy(xpath = "//thead[contains(@class,'MuiTableHead-root css-o2h25k')]/following-sibling::tbody")
	WebElement Tickets;
	@FindBy(xpath = "//div[@class='rma-table-wrapper mt-3']/child::div[1]//div[@id='All Customers']")
	WebElement filterAllUsers;
	@FindBy(xpath = "(//tr)[2]/td[1]")
	WebElement theTicket;
	@FindBy(xpath = "//ul/li[@data-value='Admin']")
	WebElement adminRole;
	@FindBy(xpath = "//ul/li[@data-value='SENT']")
	WebElement arrivalStatus;
	@FindBy(xpath = "//ul[@role='listbox']/li[@data-value='lastModifiedDate,desc']")
	WebElement newestTime;
	@FindBy(xpath = "//div[@class='rma-table-wrapper mt-3']/child::div[1]//div[@id='Newest First']")
	WebElement filterTime;
	@FindBy(xpath = "//div[@class='rma-table-wrapper mt-3']/child::div[1]//div[@id='Status']")
	WebElement filterAllStatus;
	@FindBy(xpath = "//thead[contains(@class,'MuiTableHead-root css-o2h25k')]/following-sibling::tbody[1]")
	WebElement selectTicket;
	@FindBy(xpath = "//div[contains(@class,'notification-box')]/div[@class='date-container']/div[contains(@id,'date')]//input[1]")
	WebElement arrivalDate;
	@FindBy(xpath = "//div[@class='notification-banner']//button[text()='Confirm Arrival']")
	WebElement confirmArrival;
	@FindBy(xpath = "//div[@id='status-modal-main-container']//button[1]")
	WebElement goToInspectionButton;
	@FindBy(xpath = "//div[@class='nav-wrapper ']//span[text()='Inspection']")
	WebElement inspectionSideMenu;
	@FindBy(xpath = "//div[@id='status-modal-main-container']")
	WebElement inspectionContainer;
	@FindBy(xpath = "//div[@class='notification-banner']")
	WebElement arrivalContainer;
	@FindBy(xpath = "//p[text()='Inspection']/following::button")
	WebElement startScan;
	@FindBy(xpath = "//tbody/tr")
	List<WebElement> rowEntire;
	@FindBy(xpath = "(//tr)[2]")
	WebElement rowOne;

	@FindBy(xpath = "(//tr)[2]")
	WebElement rowOne1;
	@FindBy(xpath = "//div[@class='nav-wrapper ']//div[contains(@class,'button-container')]//*[contains(.,'Inspection')]")
	WebElement sideInspectionMenu;

	public void searchRefNumber() {

		String refNum = NewRequest.refNo;
		log.info("Searching for reference number: " + refNum);

		e.sendText(searchRefNo, refNum);

		if (rowEntire.size() > 2) {
			e.click(rowOne);
			log.info("There are more than 1 ticket");
			log.warn("Search function failed");
			log.info("Finding alternative method to proceed");
			log.info("Total rows:  " + rowEntire.size());
		} else if (rowEntire.size() == 2) {
			e.click(rowOne1);
			log.info("Clicking the ticket...");
		} else {
			log.info("Couldn't click the ticket...");
		}
	}

	/*
	 * public void validateSingleTicket() { // Wait for table to be visible first
	 * e.getVisible(Tickets);
	 * 
	 * // Wait for search results to stabilize
	 * BaseClass.getWait().until(ExpectedConditions.numberOfElementsToBe(By.xpath(
	 * "//tr"), 2));
	 * 
	 * List<WebElement> rows = driver.findElements(By.xpath("//tr"));
	 * log.info("Total tickets found: " + (rows.size() - 1));
	 * 
	 * if (rows.size() != 2) {
	 * log.error("Expected 2 rows (1 header + 1 data), but found: " + rows.size());
	 * throw new AssertionError("Expected 2 rows but found " + rows.size() +
	 * " rows"); }
	 * 
	 * log.info("Single ticket validation successful"); }
	 * 
	 * public void navigateToTicket() { e.click(theTicket);
	 * 
	 * }
	 */

	public void enterArrivalDate() {

		e.click(arrivalDate);
		e.clearAll(arrivalDate);

		String date = NewRequest.date;
		log.info("Using pickup date from NewRequest: " + date);

		// Parse with formatter
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		LocalDate datePickup;

		try {
			datePickup = LocalDate.parse(date, formatter);
			log.info("Parsed pickup date: " + datePickup);

			// Validate the parsed date is reasonable
			LocalDate today = LocalDate.now();
			if (datePickup.isBefore(today.minusYears(1)) || datePickup.isAfter(today.plusYears(1))) {
				log.warn("Parsed date " + datePickup + " seems unusual (more than 1 year from today)");
			}

			// Send date + 2 days (method adds 2 days despite name)
			// LocalDate handles month/year boundaries automatically
			log.info("Calculating arrival date: " + datePickup + " + 2 days");
			e.sendDatePlusOneDay(arrivalDate, datePickup, "yyyy-MM-dd");

		} catch (Exception e) {
			log.error("Failed to parse or send date: " + date, e);
			throw new RuntimeException("Date parsing or entry failed for: " + date, e);
		}
	}

	public void navigateToInspection() {
		e.click(confirmArrival);
		log.info("Confirmig Arrival");
		log.info("Starting Inspection");
		if (inspectionContainer.isDisplayed()) {
			e.click(goToInspectionButton);
			log.info("Using the pop up inspection navigation");
		} else {
			e.click(sideInspectionMenu);
			log.info("Using side inspection menu");
		}

		e.click(startScan);
		log.info("Clicked Start Scan button");
	}

}
