package com.stepDefinitions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import org.apache.logging.log4j.Logger;

import com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter;
import com.base.BaseClass;
import com.utility.LoggerHelper;
import com.webdrivermanager.DriverManager;

public class Hooks {

	private static final Logger log = LoggerHelper.getLogger(Hooks.class);

    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    @Before
    public void setUp(Scenario scenario) {        
        for(String tag: scenario.getSourceTagNames()) {
        	DriverManager.setDriver(tag.toString().split("=")[1]);
        }
        
     // Initialize driver before each scenario
       // DriverManager.setDriver("chrome");
        WebDriver driver = DriverManager.getDriver();

        if (driver != null) {
            log.info("Driver initialized for scenario: " + scenario.getName());
            driver.manage().window().maximize();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));

            // Initialize WebDriverWait in BaseClass
            BaseClass.setWait(new WebDriverWait(driver, Duration.ofSeconds(30)));
        }
    }

    @After
    public void tearDown(Scenario scenario) {
        WebDriver driver = DriverManager.getDriver();

        if (driver != null) {
            // Capture screenshot if scenario failed
            if (scenario.isFailed()) {
                byte[] screenshotBytes = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshotBytes, "image/png", "Failed Screenshot");
                saveScreenshotForExtent(driver, scenario);
                log.info("Screenshot captured for failed scenario: " + scenario.getName());
            }

            // Quit driver after scenario
            DriverManager.quitDriver();
        }
    }

    private void saveScreenshotForExtent(WebDriver driver, Scenario scenario) {
        try {
            File sourceFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String sanitizedName = scenario.getName().replaceAll("[^a-zA-Z0-9-]", "_");
            String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);

            Path screenshotsDir = Paths.get("target", "ExtentReports", "screenshots");
            Files.createDirectories(screenshotsDir);

            String fileName = sanitizedName + "_" + timestamp + ".png";
            Path destination = screenshotsDir.resolve(fileName);

            Files.copy(sourceFile.toPath(), destination, StandardCopyOption.REPLACE_EXISTING);

            ExtentCucumberAdapter.addTestStepScreenCaptureFromPath("../screenshots/" + fileName);
        } catch (IOException ioException) {
            log.error("Failed to save screenshot for Extent report: " + ioException.getMessage(), ioException);
        }
    }
}
