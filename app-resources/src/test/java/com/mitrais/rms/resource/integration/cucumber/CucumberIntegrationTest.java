package com.mitrais.rms.resource.integration.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(Cucumber.class)
//@CucumberOptions(features = "classpath:feature", glue = "com/mitrais/rms/auth/integration")
@CucumberOptions(
        plugin = "pretty",
        features = "src/test/java/features",
        glue = "com/mitrais/rms/resource/integration/cucumber/stepdefs")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class CucumberIntegrationTest {
}
