package Steps;

import Helpers.PropertyHelper;
import Pages.PaymentPage;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Steps.CommonStepDefinitions.waitForNSeconds;
import static Utils.LoggingUtil.LOGGER;

public class AppiumStepDefinitions {

    public static AppiumDriver<MobileElement> appiumDriver = null;
    public static DesiredCapabilities desiredCapabilities;
    PropertyHelper propertiesReader = new PropertyHelper();
    private WebDriverWait wait;

    @Given("^Successful mobile payment$")
    public void successfulPayment() throws Exception {
        PaymentPage paymentPage = new PaymentPage(appiumDriver, wait);
        JavascriptExecutor js = (JavascriptExecutor) appiumDriver;
        String cardNumber = propertiesReader.getValue("cardNumber");


        if (paymentPage.isDisplayed()) {


            try {
                paymentPage.sendKeys(paymentPage.cardholderNameTextField, "Özgür Çetintaş");
                waitForNSeconds( 2 );
                js.executeScript("arguments[1].value = arguments[0];",cardNumber, paymentPage.cardNumberTextField);
                waitForNSeconds( 2 );

                if (paymentPage.ccLogo.isDisplayed()) {

                    paymentPage.sendKeys(paymentPage.cardExpirationMonth, "02");
                    waitForNSeconds( 2 );
                    paymentPage.sendKeys(paymentPage.cardExpirationYear, "2024");
                    waitForNSeconds( 2 );
                    paymentPage.sendKeys(paymentPage.cardCVV, "111");

                    if (paymentPage.startNowButton.isDisplayed()) {

                        paymentPage.startNowButton.click();
                        waitForNSeconds( 5 );

                    } else {

                        LOGGER.warn(String.format("\tFailed when start now button NOT displayed\t\n"));
                        Assert.fail("\tFailed when start now button NOT displayed\t\n");

                    }


                } else {

                    LOGGER.warn(String.format("\tFailed when card schema logo NOT displayed\t\n"));
                    Assert.fail("\tFailed when card schema logo NOT displayed\t\n");

                }

                if (paymentPage.openButton.isDisplayed()) {

                    LOGGER.info(String.format("\tSuccessfully completed\t\n"));

                } else {

                    LOGGER.warn(String.format("\tFailed when open button NOT displayed\t\n"));
                    Assert.fail("\tFailed when open button NOT displayed\t\n");

                }


            } catch (Exception e) {

                e.printStackTrace();

            }

        } else {

            LOGGER.warn(String.format("\tFailed when send keys to Element\t\n"));
            Assert.fail("\tFailed when send keys to Element\t\n");

        }


    }


}
