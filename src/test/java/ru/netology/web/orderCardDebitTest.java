package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class orderCardDebitTest {
    private WebDriver driver;
    @BeforeAll
    static void setUpAll() {
        System.setProperty("webdriver.chrom.driver", "./driver/win/chromdriver.exe");
    }
    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
    }
    @AfterEach
    void teatDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldTestSomething() {

    }
}
