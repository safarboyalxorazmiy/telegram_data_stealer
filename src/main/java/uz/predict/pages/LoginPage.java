package uz.predict.pages;

import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class LoginPage {
    @Autowired
    private WebDriver webDriver;

    public Boolean isLoggedIn() {
        try {
            WebElement element = webDriver.findElement(By.cssSelector("h4.i18n"));
            return !element.getText().equals("Log in to Telegram by QR Code");
        } catch(NoSuchElementException e) {
            return false;
        }
    }


    public void clickLoginButton() {
        boolean isButtonNotExists = true;
        while (isButtonNotExists) {
            try {
                WebElement button = webDriver.findElement(By.tagName("button"));
                button.click();

                isButtonNotExists = false;
            } catch (NoSuchElementException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }
        }
    }

    public Boolean fillPhoneNumberInput(String phoneNumber) {

        if (phoneNumber.length() != 13) {
            return false;
        }

        boolean isPhoneNumberInputNotExists = true;

        int trysCount = 0;

        while (isPhoneNumberInputNotExists) {

            if (trysCount == 60) {
                isPhoneNumberInputNotExists = false;
                return false;
            }

            try {
                WebElement input = webDriver.findElement(By.cssSelector("input#sign-in-phone-number"));

                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);

                input.sendKeys(phoneNumber);

                isPhoneNumberInputNotExists = false;
            } catch (NoSuchElementException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }

            trysCount++;
        }


        return true;
    }

    public Boolean fillAuthCode(String authCode) {
        if (authCode.length() != 13) {
            return false;
        }

        boolean isPhoneNumberInputNotExists = true;

        int trysCount = 0;

        while (isPhoneNumberInputNotExists) {

            if (trysCount == 60) {
                isPhoneNumberInputNotExists = false;
                return false;
            }

            try {
                WebElement input = webDriver.findElement(By.cssSelector("input#sign-in-phone-number"));

                input.sendKeys(Keys.CONTROL + "a");
                input.sendKeys(Keys.DELETE);

                input.sendKeys(authCode);

                isPhoneNumberInputNotExists = false;
            } catch (NoSuchElementException e) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ignored) {}
            }

            trysCount++;
        }


        return true;
    }
}
