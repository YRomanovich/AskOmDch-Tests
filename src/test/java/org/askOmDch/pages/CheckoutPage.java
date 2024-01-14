package org.askOmDch.pages;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.askOmDch.objects.BillingAddress;
import org.askOmDch.objects.User;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static org.askOmDch.constants.EndPoint.CHECKOUT;

public class CheckoutPage extends BasePage {
    @FindBy(id = "")
    @CacheLookup
    private WebElement firstNameFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement lastNameFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement addressLineOneFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement billingCityFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement billingPostCodeFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement billingEmailFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement placeOrderBtn;
    @FindBy(css = "")
    private WebElement successNotice;

    @FindBy(css = "")
    @CacheLookup
    private WebElement logInLink;
    @FindBy(id = "")
    @CacheLookup
    private WebElement userNameFld;
    @FindBy(id = "")
    @CacheLookup
    private WebElement passwordFld;
    @FindBy(name = "")
    @CacheLookup
    private WebElement logInBtn;
    @FindBy(css = ".blockUI.blockOverlay")
    private WebElement waitSpinner;

    @FindBy(id = "billing-country")
    @CacheLookup
    private WebElement countryDropdown;
    @FindBy(id = "billing-state")
    @CacheLookup
    private WebElement stateDropdown;
    @FindBy(id = "payment_method_bacs")
    @CacheLookup
    private WebElement directBankTransferRadioBtn;

    @FindBy(id = "select2-billing_country-container")
    private WebElement expandCountryDropdown;
    @FindBy(id = "select2-billing_state-container")
    private WebElement expandStateDropdown;

    @FindBy(css = "td[class='product-name']")
    private WebElement productName;
    @FindBy(id = "payment_method_cod")
    private WebElement cashOnDeliveryTransferRadioBtn;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public Boolean isLoaded(){
        return waitLong.until(ExpectedConditions.urlContains("/checkout"));
    }

    public CheckoutPage load(){
        load(CHECKOUT.url);

        return this;
    }

    private CheckoutPage isLoginBtnToDisappear(){
        waitLong.until(ExpectedConditions.invisibilityOf(logInBtn));
        return this;
    }

    @Step("Enter first name as {0}")
    public CheckoutPage enterFirstName(String firstName){
        WebElement element = getElement(firstNameFld);

        element.clear();
        element.sendKeys(firstName);

        return this;
    }

    @Step("Enter last name as {0}")
    public CheckoutPage enterLastName(String lastName){
        WebElement element = getElement(lastNameFld);

        element.clear();
        element.sendKeys(lastName);

        return this;
    }

    @Step("Enter address as {0}")
    public CheckoutPage enterAddressLineOne(String address){
        WebElement element = getElement(addressLineOneFld);

        element.clear();
        element.sendKeys(address);

        return this;
    }

    @Step("Enter billing city as {0}")
    public CheckoutPage enterBillingCity(String billingCity){
        WebElement element = getElement(billingCityFld);

        element.clear();
        element.sendKeys(billingCity);

        return this;
    }

    @Step("Enter billing post code as {0}")
    public CheckoutPage enterBillingPostCode(String billingPostCode){
        WebElement element = getElement(billingPostCodeFld);

        element.clear();
        element.sendKeys(billingPostCode);

        return this;
    }

    @Step("Enter billing email as {0}")
    public CheckoutPage enterBillingEmail(String billingEmail){
        WebElement element = getElement(billingEmailFld);

        element.clear();
        element.sendKeys(billingEmail);

        return this;
    }

    @Step("Enter billing address")
    public CheckoutPage setBillingAddress(BillingAddress billingAddress){
        enterFirstName(billingAddress.getFirstName()).
                enterLastName(billingAddress.getLastName()).
                enterAddressLineOne(billingAddress.getAddressLineOne()).
                selectCountry(billingAddress.getCountry()).
                enterBillingCity(billingAddress.getCity()).
                selectState(billingAddress.getState()).
                enterBillingPostCode(billingAddress.getPostalCode()).
                enterBillingEmail(billingAddress.getEmail());
        return this;
    }

    @Step("Click Place Order button on Checkout page")
    public CheckoutPage clickPlaceOrder(){
        waitForSpinnerToDisappear(By.cssSelector(""));

        clickElement(placeOrderBtn);
        return this;
    }

    @Step("Get notice that products are successfully checked out")
    public String getSuccessNotice(){
        return getText(successNotice);
    }

    @Step("Click Log In link on Checkout page")
    public CheckoutPage clickLogInLink(){
        clickElement(logInLink);
        return this;
    }

    @Step("Enter username on Login form as {0}")
    public CheckoutPage enterUserName(String username){
        enterValue(userNameFld, username);
        return this;
    }

    @Step("Enter password on Login form as {0}")
    public CheckoutPage enterPassword(String password){
        enterValue(passwordFld, password);
        return this;
    }

    @Step("Click Login button on Login form")
    public CheckoutPage clickLogInButton(){
        clickElement(logInBtn);
        return this;
    }

    @Step("Log in to the system")
    public CheckoutPage login(User user){
        return enterUserName(user.getUsername())
                .enterPassword(user.getPassword())
                .clickLogInButton()
                .isLoginBtnToDisappear();
    }

    @Step("Select county {0} from dropdown menu on Checkout page")
    public CheckoutPage selectCountry(String countryName){
        waitLong.until(ExpectedConditions.elementToBeClickable(expandCountryDropdown)).click();
        WebElement element =  waitLong.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()=" + countryName + "'}")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
        element.click();

        return this;
    }

    @Step("Select state {0} from dropdown menu on Checkout page")
    public CheckoutPage selectState(String stateName){
        waitLong.until(ExpectedConditions.elementToBeClickable(expandStateDropdown)).click();
        WebElement element =  waitLong.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//li[text()=" + stateName + "'}")));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true)", element);
        element.click();

        return this;
    }

    @Step("Select Direct Bank Transfer on Checkout page")
    public CheckoutPage selectDirectBankTransfer() {
        WebElement element = waitElementToBeClickable(directBankTransferRadioBtn);

        if(!element.isSelected()) {
            element.click();
        }
        return this;
    }

    @Step("Get product name from Checkout page")
    public String getProductName(){
        return waitLong.until(ExpectedConditions.visibilityOf(productName)).getText();
    }

    @Step("Select Cash On Delivery on Checkout page")
    public CheckoutPage selectCashOnDeliveryTransfer(){
        clickElement(cashOnDeliveryTransferRadioBtn);
        return this;
    }
}
