package com.testRunner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
		features="src/test/Resources/solumRma.feature", 
		glue="com.stepDefinitions", 
		plugin={
			"pretty", 
			"html:target/htmlReports/htmlReport.html", 
			"json:target/josnReports/jonsReport.json", 
			"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:target/ExtentReports/ExtentReport.html"
		}
) 
public class TestRunner extends AbstractTestNGCucumberTests {
	
}