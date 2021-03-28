package Pages;

import Utils.LoggingUtil;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.PageFactory;

import static Utils.LoggingUtil.LOGGER;


public class PaymentPage extends PageObject {

    public PaymentPage(WebDriver driver, WebDriverWait wait) {

            super(driver, wait);
            PageFactory.initElements(driver, this);

    }

    @FindBy(xpath = "//*[@id='formPayment']")
    @CacheLookup
    public WebElement formPayment;

    @FindBy(xpath = "//*[@id='cardHolderName']")
    @CacheLookup
    public WebElement cardholderNameTextField;

    @FindBy(xpath = "//*[@id='cardNumber']")
    @CacheLookup
    public WebElement cardNumberTextField;

    @FindBy(xpath = "//*[@id='formPayment']/div[2]/div/div")
    @CacheLookup
    public WebElement ccLogo;

    @FindBy(xpath = "//*[@id='cardExpirationMonth']")
    @CacheLookup
    public WebElement cardExpirationMonth;

    @FindBy(xpath = "//*[@id='cardExpirationYear']")
    @CacheLookup
    public WebElement cardExpirationYear;

    @FindBy(xpath = "//*[@id='cardCVV']")
    @CacheLookup
    public WebElement cardCVV;

    @FindBy(xpath = "//*[@id='formPayment']/button")
    @CacheLookup
    public WebElement startNowButton;

    @FindBy(xpath = "//*[@id='formPayment']/div[3]/div[3]/div")
    @CacheLookup
    public WebElement paymentFailed;

    @FindBy(xpath = "//*[@id='formPayment']/div[5]/div[2]/a[1]")
    @CacheLookup
    public WebElement termsAndConditions;

    @FindBy(xpath = "/html/body/div[1]/div/div[2]/div/div/div/div")
    @CacheLookup
    public WebElement openButton;

    public boolean isDisplayed() {

       try {
           WaitUntilElementVisible(formPayment);
           formPayment.isDisplayed();

           WaitUntilElementVisible(cardholderNameTextField);
           cardholderNameTextField.isDisplayed();

           WaitUntilElementVisible(cardNumberTextField);
           cardNumberTextField.isDisplayed();

           WaitUntilElementVisible(cardExpirationMonth);
           cardExpirationMonth.isDisplayed();

           WaitUntilElementVisible(cardExpirationYear);
           cardExpirationYear.isDisplayed();

           WaitUntilElementVisible(cardCVV);
           cardCVV.isDisplayed();

           WaitUntilElementVisible(startNowButton);
           startNowButton.isDisplayed();

           WaitUntilElementVisible(termsAndConditions);
           termsAndConditions.isDisplayed();

           LOGGER.info(String.format("\tAll elements displayed as expected\t\n"));

           return true;

       } catch (Exception e) {

           LOGGER.warn(String.format("\tFailed when send keys to Element\t\n"));
           Assert.fail(e.getMessage());

           return false;
       }


    }

    public void sendKeys(WebElement element, String value){

        try {

            element.sendKeys(value);
            LOGGER.info(String.format("\n\tFilling the element: [%s] \t with the value: [%s]\n\t", element, value));

        } catch (Exception e) {

            LOGGER.warn(String.format("\tFailed when send keys to Element\t\n"));
            Assert.fail("\tFailed when send keys to Element\t\n");

        }

    }



}
