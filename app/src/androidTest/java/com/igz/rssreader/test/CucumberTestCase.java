package com.igz.rssreader.test;

import cucumber.api.CucumberOptions;

@CucumberOptions(features = "features",
	glue = { "com.igz.rssreader.steps", "com.igz.rssreader.support" },
	format = { "pretty", "html:/data/data/com.igz.rssreader/cucumber-reports/cucumber-html-report", "json:/data/data/com.igz.rssreader/cucumber-reports/cucumber.json", "junit:/data/data/com.igz.rssreader/cucumber-reports/cucumber.xml" },
	tags = { "~@manual", "@android" })
class CucumberTestCase {

}
