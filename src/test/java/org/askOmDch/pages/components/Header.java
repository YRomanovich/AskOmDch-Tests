package org.askOmDch.pages.components;

import io.qameta.allure.Step;
import org.askOmDch.base.BasePage;
import org.askOmDch.pages.StorePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;

public class Header extends BasePage {
    @FindBy(css = "")
    @CacheLookup
    private WebElement storeMenuLink;

    public Header(WebDriver driver) {
        super(driver);
    }

    @Step("Click Store Link")
    public StorePage clickStoreLink(){
        clickElement(storeMenuLink);

        return new StorePage(driver);
    }
}
