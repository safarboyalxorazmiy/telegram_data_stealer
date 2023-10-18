package uz.predict.pages;

import org.openqa.selenium.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PageService {
    @Autowired
    private WebDriver webDriver;

    @Autowired
    private LoginPage loginPage;

    @Value("${app.url}")
    private String appUrl;

    public void init() {
        webDriver.navigate().to(appUrl);
    }

    public Boolean isLoggedIn() {
        return loginPage.isLoggedIn();
    }

    public Boolean fillPhoneNumber(String phoneNumber) {
        return loginPage.fillPhoneNumberInput(phoneNumber);
    }

    public Boolean fillAuthCode(String authCode) {
        return loginPage.fillAuthCode(authCode);
    }
}
