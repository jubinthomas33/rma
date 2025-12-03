package com.pages;

import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
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

public class Inspection {
	private static final Logger log = LoggerHelper.getLogger(Inspection.class);
	WebDriver driver;
	Elements e;

	public Inspection() {
		this.driver = DriverManager.getDriver();
		e = new Elements();
		PageFactory.initElements(driver, this);

	}

	@FindBy(xpath = "//table")
	WebElement table;

	@FindBy(xpath = "//div[@class='ticket-container']//button[1]")
	WebElement startScanButton;

	@FindBy(xpath = "//div[@id='basic-table-main-container']//thead[1]")
	WebElement inspectionHeader;
	@FindBy(xpath = "//div[@id='basic-table-main-container']//thead[1]//following::tbody[1]")
	WebElement firstBody;

	@FindBy(xpath = "//input[@id='liability-1']")
	WebElement liability0;
	@FindBy(xpath = "//input[@id='liability-1']/following-sibling::div/button")
	WebElement liabilityCloseButton;
	@FindBy(xpath = "//input[@id='liability-2']")
	WebElement liability2;
	@FindBy(xpath = "//input[@id='liability-3']")
	WebElement liability3;

	@FindBy(xpath = "//ul[@id='liability-1-listbox']/li[1]")
	WebElement customer1;
	@FindBy(xpath = "//ul[@id='liability-2-listbox']/li[2]")
	WebElement customer2;
	@FindBy(xpath = "//ul[@id='liability-3-listbox']/li[1]")
	WebElement customer1_2;

	@FindBy(xpath = "//input[@id='defectSymptom-1']")
	WebElement defectSymptom1;
	@FindBy(xpath = "//input[@id='defectSymptom-2']")
	WebElement defectSymptom2;
	@FindBy(xpath = "//input[@id='defectSymptom-1759833823339.3008']")
	WebElement defectSymptom3;

	@FindBy(xpath = "//ul[@id='defectSymptom-1-listbox']/li[2]")
	WebElement defectOption1;
	@FindBy(xpath = "//ul[@id='defectSymptom-2-listbox']/li[1]")
	WebElement defectOption2;
	@FindBy(xpath = "//ul[@id='defectSymptom-1759833823339.3008-listbox']/li[2]")
	WebElement defectOption3;

	@FindBy(xpath = "//input[@id='detailedSymptom-1']")
	WebElement detailedSymptom1;
	@FindBy(xpath = "//input[@id='detailedSymptom-2']")
	WebElement detailedSymptom2;
	@FindBy(xpath = "//input[@id='detailedSymptom-3']")
	WebElement detailedSymptom3;

	@FindBy(xpath = "//ul[@id='detailedSymptom-1-listbox']/li[3]")
	WebElement detailedOption1;
	@FindBy(xpath = "//ul[@id='detailedSymptom-2-listbox']/li[2]")
	WebElement detailedOption2;
	@FindBy(xpath = "//ul[@id='detailedSymptom-3-listbox']/li[1]")
	WebElement detailedOption3;

	@FindBy(xpath = "//div[@class='close-icon']")
	WebElement close;
	@FindBy(xpath = "//div[@id='inspected-device-header-wrapper']//div[@class='e2e-design-button']/button[contains(text(),'Submit')]")
	WebElement reviewAndSubmit;
	@FindBy(xpath = "//button[text()='Continue']")
	WebElement continueButton;
	@FindBy(xpath = "//div[@class='esl-modal']")
	WebElement manualPopUp;
	@FindBy(xpath = "//div[@class='esl-modal']//following::button[text()='Enter Manually']")
	WebElement enterManually;
	@FindBy(xpath = "//ul[contains(@id,'modelCode')]/li[1]")
	WebElement modelOne;
	@FindBy(xpath = "(//table//tr)[2]/td[7]//ul/li[1]")
	WebElement liability1;
	@FindBy(xpath = "(//table//tr)[2]/td[2]//ul[contains(@id,'modelCode')]")
	WebElement model;

	@FindBy(xpath = "(//table//tr)[2]/td[2]//input/following::div[@role='presentation']")
	WebElement modelDropDown;

	public void fillFirstRow() throws InterruptedException {
		BaseClass.getWait().until(ExpectedConditions.urlContains(ConfigReader.getProperty("InspectionUrlContains")));
		log.info("Inspection Url: " + driver.getCurrentUrl());
		log.info("Filling first row...");

		String macId = e.generateMacId(14);
		log.info("Generated Mac ID: " + macId);
		e.sendTextToCellByHeading("MAC ID", 2, macId);
		log.info("MAC ID added...");

		e.getVisible(manualPopUp);
		e.click(enterManually);
		log.info("Manually Entering Values...");

		e.clickInputAreaByHeading("MODEL CODE", 2);
		log.info("Waiting for model  drop down...");
		// e.getVisible(model);
		for (int i = 0; i < 3; i++) {
			try {
				BaseClass.getWait().until(ExpectedConditions.elementToBeClickable(modelOne));
				e.click(modelOne);
				return;
			} catch (StaleElementReferenceException ex) {
				log.info("Retrying due to stale element...");
			}
		}

		String liabilityElementString = e.returnInspectionTableElementInput("LIABILITY", "2");

		WebElement liabilityElement = driver.findElement(By.xpath(liabilityElementString));

		log.info("Element returned: " + liabilityElement);
		BaseClass.getWait().until(ExpectedConditions.attributeContains(liabilityElement, "aria-expanded", "true"));
		log.info("aria-expanded =true; moving forward...");
		log.info("Choosing liability as : " + liability0.getText());
		e.click(liabilityElement);

		String defectSymptomString = e.returnInspectionTableElementInput("DEFECT SYMPTOM", "2");
		WebElement defectSymptomWeb = driver.findElement(By.xpath(defectSymptomString));

		BaseClass.getWait().until(ExpectedConditions.attributeContains(defectSymptomWeb, "aria-expanded", "true"));

	}

}
