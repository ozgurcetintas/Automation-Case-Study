package Steps;

import Helpers.PropertyHelper;
import Pages.PaymentPage;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import static Steps.CommonStepDefinitions.waitForNSeconds;
import static Utils.LoggingUtil.LOGGER;


public class PaymentStepDefinitions {

    private WebDriver driver = CommonStepDefinitions.driver;
    PropertyHelper propertiesReader = new PropertyHelper();
    private WebDriverWait wait;

    public PaymentStepDefinitions() throws Exception {

        this.wait = new WebDriverWait(driver, propertiesReader.getTimeout());

    }

    @Given("^Check page title as (\\w+(?: \\w+)*)$")
    public void verifyPageTitle(String expected){
        String actualTitle = driver.getTitle();

        if (actualTitle.equalsIgnoreCase(expected)){

            LOGGER.info(String.format("\tPage title is [%s] as expected\t\n",actualTitle ) );

        } else {

            LOGGER.warn(String.format("\tPage title is NOT [%s] as expected, Actual title is [%s]\t\n",expected, actualTitle ) );

        }
    }

    @Given("^Successful payment$")
    public void successfulPayment() throws Exception {
        PaymentPage paymentPage = new PaymentPage(driver, wait);
        JavascriptExecutor js = (JavascriptExecutor) driver;
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

    @Given("^Failed payment$")
    public void failedPayment() throws Exception {
        PaymentPage paymentPage = new PaymentPage(driver, wait);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        String cardNumber = propertiesReader.getValue("failedCardNumber");


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

                if (paymentPage.paymentFailed.isDisplayed()) {

                    LOGGER.info(String.format("\tFailed payment\t\n"));

                } else {

                    LOGGER.warn(String.format("\tFailed when failed text NOT displayed\t\n"));
                    Assert.fail("\tFailed when failed text NOT displayed\t\n");

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
