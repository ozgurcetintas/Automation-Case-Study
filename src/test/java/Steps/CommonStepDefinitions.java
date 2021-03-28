package Steps;

import Helpers.PropertyHelper;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;


import java.net.URL;
import java.util.concurrent.TimeUnit;

import static Steps.AppiumStepDefinitions.*;
import static Utils.LoggingUtil.*;


public class CommonStepDefinitions {

    private static int scenariosCounter = 0;
    private static int failedScenariosCounter = 0;

    public static WebDriver driver;

    @Before
    public void beforeScenario(Scenario scenario) throws Exception {

        openBrowser();
        openSimulator();

        LOGGER.info( String.format( "\t[%d] > Scenario [%s] started\t\n", ++scenariosCounter, scenario.getName() ) );

    }

    @After
    public static void afterScenario(Scenario scenario) {

        boolean isFailed = scenario.isFailed();

        if ( isFailed ) {

            try {

                byte[] screenshot = takeScreenshot();
                scenario.attach(screenshot, "image/pgn", scenario.getName());

                LOGGER.info(String.format("\tThe screenshot has embedded for scenario: [ %s ]\t\n", scenario.getName() ) );

            } catch (WebDriverException noSupportScreenshot) {

                LOGGER.warn(noSupportScreenshot.getMessage());
                Assert.fail(noSupportScreenshot.getMessage());
            }

            ++failedScenariosCounter;

            if (driver != null) {
                driver.quit();
            }
            if (appiumDriver != null) {
                appiumDriver.quit();
            }

        } else {

            if (driver != null) {
                driver.quit();
            }
            if (appiumDriver != null) {
                appiumDriver.quit();
            }

        }

        driver = null;

        String result = isFailed ? "with errors" : "succesfully";

        LOGGER.info(String.format( "\t[%d] > Scenario [%s] finished %s\n\t", scenariosCounter, scenario.getName(), result ) );
        LOGGER.info(String.format( "\t%d of %d scenarios failed so far\n\t", failedScenariosCounter, scenariosCounter ) );

    }

    public void openBrowser() throws Exception {

        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments( "--window-size=1440,768");
        driver = new ChromeDriver(options);
        PropertyHelper propertiesReader = new PropertyHelper();
        driver.manage().timeouts().implicitlyWait(propertiesReader.getTimeout(), TimeUnit.SECONDS);
        driver.manage().timeouts().setScriptTimeout(propertiesReader.getTimeout(), TimeUnit.SECONDS);
        driver.manage().deleteAllCookies();
        driver.manage().window().maximize();
        driver.get(PropertyHelper.getValue("url"));

    }

    public void openSimulator() throws Exception {
        desiredCapabilities = new DesiredCapabilities();
        //setup the web driver and launch the webview app.
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, "iOS");
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, "14.3");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUTest");
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, "iPhone 11");
        desiredCapabilities.setCapability(MobileCapabilityType.BROWSER_NAME, "Safari");
        desiredCapabilities.setCapability(MobileCapabilityType.AUTOMATION_NAME, "XCUITest");
        URL url = new URL("http://127.0.0.1:4723/wd/hub");
        AppiumDriver appiumDriver = new AppiumDriver(url, desiredCapabilities);

        appiumDriver.get(PropertyHelper.getValue("url"));

    }

    @Given("^I take screenshoot$")
    public static byte[] takeScreenshot(){

        try {

            byte[] screenshot = ((TakesScreenshot)driver).getScreenshotAs( OutputType.BYTES );
            LOGGER.info( "\tThe screenshot is taken.\t\n" );

            return screenshot;

        } catch (Exception e) {

            LOGGER.info(String.format("\tThe screenshot could NOT taken; because error: [ %s ]\t\n", e.getMessage() ) );
            Assert.fail(String.format("\tThe screenshot could NOT taken; because error: [ %s ]\t\n", e.getMessage() ) );
            return new byte[0];

        }

    }

    @Given("^I wait for (\\d+) seconds$")
    public static void waitForNSeconds(long seconds) {

        LOGGER.info( "\tWaiting for [" + seconds + "] seconds\t\n" );

        try {

            Thread.sleep( seconds * 1000L );
            LOGGER.info( "\tWaited for [" + seconds + "] seconds\t\n" );

        } catch (Exception e) {

            LOGGER.error( "\tError during waiting for [" + seconds + "] seconds.\t\n", e );
            Assert.fail( "\tError during waiting for [" + seconds + "] seconds.\t\n" );

        }

    }

}


