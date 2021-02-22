package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SignupPage {
    @FindBy(id = "inputFirstName")
    private WebElement inputFirstName;

    @FindBy(id = "inputLastName")
    private WebElement inputLastName;

    @FindBy(id = "inputUserName")
    private WebElement inputUserName;

    @FindBy(id = "inputPassword")
    private WebElement inputPassword;

    @FindBy(tagName = "button")
    private WebElement signupbtn;

    public SignupPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void signupUser(String firstName, String lastName, String userName, String password) {
        inputFirstName.sendKeys(String.valueOf(firstName));
        inputLastName.sendKeys(String.valueOf(lastName));
        inputUserName.sendKeys(String.valueOf(userName));
        inputPassword.sendKeys(String.valueOf(password));
        signupbtn.click();
    }
}
