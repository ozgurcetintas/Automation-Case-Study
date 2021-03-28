package Runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/Resources/Features"},
        glue = {"src/test/java/Steps"},
        plugin = { "pretty", "html:target/cucumber-reports" },
        monochrome = true
        )


public class BaseTest {

}


