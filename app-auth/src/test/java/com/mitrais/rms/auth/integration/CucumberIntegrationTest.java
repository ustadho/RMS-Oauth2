package com.mitrais.rms.auth.integration;

import org.junit.runner.RunWith;
import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;

@RunWith(Cucumber.class)
//@CucumberOptions(features = "classpath:feature", glue = "com/mitrais/rms/auth/integration")
@CucumberOptions(features = "src/test/java/features", glue = "com/mitrais/rms/auth/integration")
public class CucumberIntegrationTest {
}
