package com.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.base.BaseClass;
import com.utility.Elements;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class NewRequest {
	private static final Logger log = LoggerHelper.getLogger(NewRequest.class);
	WebDriver driver;
	Elements e;
	public static String refNo;
	public static String date;

	public NewRequest() {
		this.driver = DriverManager.getDriver();
		e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//div[@id='side_nav_container']//div[contains(@class,'new-request')]")
	WebElement newRequestIcon;
	@FindBy(xpath = "//input[@placeholder='Select Customer']")
	WebElement customerField;
	@FindBy(xpath = "//input[@id='customer']/following::ul/li[@id='customer-option-0']")
	WebElement reweCustomer;
	@FindBy(id = "store")
	WebElement storeField;
	@FindBy(xpath = "//li[@id='store-option-0']")
	WebElement FirstOption;
	// @FindBy(xpath = "//li[text()='RMA']")
	/// WebElement rmaStore;
	@FindBy(xpath = "//div[text()='Reference Number']/following-sibling::div//input")
	WebElement refNumber;
	@FindBy(xpath = "//div[@class='e2e-custom-select']/following::ul")
	WebElement allForwarders;
	@FindBy(xpath = "//div[text()='Forwarder']/following::div[@class='design-system-select']//input//following::button[1]")
	WebElement forwarder;
	@FindBy(xpath = "//div[@class='ant-picker-input']/input")
	WebElement pickupDate;
	@FindBy(xpath = "//div[@class='ant-picker-panel']//a[text()='Today']")
	WebElement dateToday;

	@FindBy(xpath = "//ul[@role='listbox']/li[1]")
	WebElement firstForwarder;
	@FindBy(xpath = "//div[text()='Tracking Number']/following::input[1]")
	WebElement trackingNumber;
	@FindBy(xpath = "//p[contains(text(),'Card')]/parent::div//input")
	WebElement cardBoardBox;
	@FindBy(xpath = "//div[@class='mini-display-item'][1]")
	WebElement firstDisplay;
	@FindBy(xpath = "(//div[@class='mini-display-item'])[1]//input[1]")
	WebElement firstDisplayInput;
	@FindBy(xpath = "//span[text()='Pallets']//parent::div//input")
	WebElement pallets;
	@FindBy(xpath = "//span[text()='Boxes']//parent::div//input")
	WebElement Boxes;
	@FindBy(xpath = "//div[@class=\"failure-reason-select\"]//input[1]")
	WebElement failureReason;

	@FindBy(xpath = "//ul/li[1]")
	WebElement failure;

	@FindBy(xpath = "//button[text()='Submit']")
	WebElement Submit;

	@FindBy(xpath = "//div[@class='success-message-container']")
	WebElement sucessContainer;
	@FindBy(xpath = "//div[@class='success-message-container']//p[@class='secondary-link']")
	WebElement ticketList;

	public void navigateToNewRequest() {
		e.click(newRequestIcon);
		log.info("Clicked on New Request");
	}

	public void fillGeneralInfo() {
		log.info("Filling general information");

		// Click customer field and wait for dropdown
		e.click(customerField);
		log.debug("Customer field clicked, waiting for dropdown");
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}

		// Enter customer search text
		e.sendText(customerField, "rewe");
		// log.debug("Search text 'rewe' entered");

		// Wait for customer option to appear and be clickable
		try {
			BaseClass.getWait().until(ExpectedConditions.elementToBeClickable(reweCustomer));
			Thread.sleep(500); // Additional wait for dropdown to stabilize
		} catch (Exception e) {
			log.warn("Customer dropdown may not be ready, continuing...");
		}

		// Click customer option
		e.click(reweCustomer);
		String customer = e.getText(customerField);
		log.info("Customer" + customer + "selected");

		// Wait before clicking store field
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			Thread.currentThread().interrupt();
		}

		// Click store field and enter store number
		e.click(storeField);
		log.debug("Store field clicked");
		e.sendText(storeField, "123456");
		log.debug("Store number entered");

		// Wait for store option to appear
		try {
			BaseClass.getWait().until(ExpectedConditions.elementToBeClickable(FirstOption));
			Thread.sleep(500);
		} catch (Exception e) {
			log.warn("Store dropdown may not be ready, continuing...");
		}

		// Click first store option
		e.click(FirstOption);
		log.info("Store option selected");
	}

	public void enterReferenceNumber() {
		refNo = e.faker.number().digits(10);
		e.sendText(refNumber, refNo);
		log.info("Reference number entered: " + refNo);

	}

	public void enterDate() {
		log.info("Entering pickup date (10 days before today)");
		e.click(pickupDate);
		e.clearAll(pickupDate);
		String madeUpPickupDate = e.getFormattedDateMinusDays(10, "yyyy-MM-dd");
		NewRequest.date = madeUpPickupDate;
		e.sendText(pickupDate, madeUpPickupDate);
		e.keyboardEnter();
		log.info("Entered pickup date: " + pickupDate.getAttribute("value"));

	}

	public void enterForwarder() {
		e.click(forwarder);
		e.getVisible(allForwarders);
		e.click(firstForwarder);
		log.info("forwarder clicked: " + firstForwarder.getText());

	}

	public void enterTrackingNumber() {
		e.javaScrollToView(trackingNumber);
		String trackNumber = e.faker.number().digits(10);
		e.click(trackingNumber);
		e.sendText(trackingNumber, trackNumber);
		log.info("Tracking Number entered: " + trackNumber);

	}

	public void cardBoardCheck() {
		e.toggleOn(cardBoardBox);
	}

	public void enterDisplays(String no) {
		log.info("Entering display count: " + no);

		// Wait for display element to be visible
		try {
			e.getVisible(firstDisplay);
			String displayType = firstDisplay.getText();
			log.debug("Display type: " + displayType);
		} catch (Exception e) {
			log.warn("Display type text not available, continuing...");
		}

		// Scroll to view and click
		e.javaScrollToView(firstDisplayInput);
		e.click(firstDisplayInput);
		log.debug("Display input field clicked");

		// Clear and enter value
		e.clear(firstDisplayInput);
		e.sendText(firstDisplayInput, no);
		log.info("Display count entered: " + no);
	}

	public void enterPallets(String palletNo) {
		e.sendText(pallets, palletNo);
	}

	public void enterBoxes(String boxesNo) {
		e.sendText(Boxes, boxesNo);
	}

	public void clickOnFailureReason() {
		e.click(failureReason);
	}

	public void enterFailure() {
		e.click(failure);

	}

	public void submitTicket() {
		e.click(Submit);
	}

	public void goToTicketList() throws InterruptedException {
		e.getVisible(sucessContainer);
		e.click(ticketList);

	}

}
