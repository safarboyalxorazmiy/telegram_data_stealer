package uz.predict.config;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SeleniumConfig {

    @Value("${webdriver.edge.driver}")
    private String edgeDriverPath;

    @Bean
    public WebDriver webDriver() {
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--remote-allow-origins=*");

        System.setProperty("webdriver.edge.driver", edgeDriverPath);
        WebDriver driver = new EdgeDriver(options);
        driver.manage().window().maximize();
        return driver;
    }
}