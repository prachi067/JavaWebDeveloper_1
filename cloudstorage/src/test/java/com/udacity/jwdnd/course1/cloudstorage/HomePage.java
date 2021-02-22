package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    @FindBy(id = "logout")
    private WebElement logoutbtn;

    @FindBy(id = "nav-notes-tab")
    private WebElement notesTab;

    @FindBy(id = "addNote")
    private WebElement addNote;

    @FindBy(id = "noteEdit")
    private WebElement noteEdit;

    @FindBy(id = "noteDelete")
    private WebElement noteDelete;

    @FindBy(id = "noteTitleText")
    private WebElement noteTitleText;

    @FindBy(id = "noteDescriptionText")
    private WebElement noteDescriptionText;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "note-save")
    private WebElement noteSave;

    @FindBy(id = "nav-credentials-tab")
    private WebElement credentialsTab;

    @FindBy(id = "addCredential")
    private WebElement addCredential;

    @FindBy(id = "credentialEdit")
    private WebElement credentialEdit;

    @FindBy(id = "credentialDelete")
    private WebElement credentialDelete;

    @FindBy(id = "credentialsUrlText")
    private WebElement credentialsUrlText;

    @FindBy(id = "credentialsUrlUsername")
    private WebElement credentialsUsernameText;

    @FindBy(id = "credentialsUrlPassword")
    private WebElement credentialsPasswordText;

    @FindBy(id = "credential-url")
    private WebElement credentialsUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialsUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialsPassword;

    @FindBy(id = "credential-save")
    private WebElement credentialSave;

    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void logoutUser() {
        logoutbtn.click();
    }

    public void getNotesTab() {
        notesTab.click();
    }

    public void clickAddaNewNote(){
        addNote.click();
    }

    public void addNoteDetails(String title, String description) {
        noteTitle.clear();
        noteDescription.clear();
        noteTitle.sendKeys(title);
        noteDescription.sendKeys(description);
        noteSave.click();
    }

    public String getNoteTitleText() {
        return noteTitleText.getAttribute("innerHTML");
    }

    public String getNoteDescriptionText() {
        return noteDescriptionText.getAttribute("innerHTML");
    }

    public void clickEditNote(){
        noteEdit.click();
    }

    public void clickDeleteNote(){
        noteDelete.click();
    }

    public void getCredentialsTab() {
        credentialsTab.click();
    }

    public void clickAddaNewCredential(){
        addCredential.click();
    }

    public void addCredentialDetails(String url, String userName, String password) {
        credentialsUrl.clear();
        credentialsUsername.clear();
        credentialsPassword.clear();
        credentialsUrl.sendKeys(url);
        credentialsUsername.sendKeys(userName);
        credentialsPassword.sendKeys(password);
        credentialSave.click();
    }

    public String getCredentialUrlText() {
        return credentialsUrlText.getAttribute("innerHTML");
    }

    public String getCredentialUsernameText() {
        return credentialsUsernameText.getAttribute("innerHTML");
    }

    public String getCredentialPasswordText() {
        return credentialsPasswordText.getAttribute("innerHTML");
    }

    public void clickEditCredential(){
        credentialEdit.click();
    }

    public void clickDeleteCredential(){
        credentialDelete.click();
    }

}
