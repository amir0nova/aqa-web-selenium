package ru.netology.web;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class orderCardDebitTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        if (System.getProperty("os.name").contains("Linux")) {
            System.setProperty("webdriver.chrome.driver", "driver/linux/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "driver/win/chromedriver.exe");
        }
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

    @ParameterizedTest
    @DisplayName("1.Tasting Happy Path")
    @CsvFileSource(resources = "/OrderCardDebitData1.csv", numLinesToSkip = 2)
    void shouldHappyPath(String surnameName, String number) {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys(surnameName);
        elements.get(1).sendKeys(number);
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @ParameterizedTest
    @DisplayName("2.Testing invalid input in \"Surname and name\" or \"Mobile phone\" fields")
    @CsvFileSource(resources = "/OrderCardDebitData2.csv", numLinesToSkip = 3)
    void shouldForAnyInvalidInputInSurnameNameAndMobilePhoneFields(String surnameName, String number, int index, String msgExpected) {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys(surnameName);
        elements.get(1).sendKeys(number);
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(index).getText();   // for "Surname and name" -- 0, for "MobilePhone" -- 1.
        assertEquals(msgExpected, text.trim());
    }

    @Test
    @DisplayName("3.Should Checkbox Filled Invalid")
    void shouldCheckboxFilledInvalid() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Муранов Василий");
        elements.get(1).sendKeys("+");
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    @Test
    @DisplayName("4.1.Priority message. Should SurnameName Message Output Filled Invalid SurnameName And Mobile Phone")
    void shouldSurnameNameMessageOutputFilledInvalidSurnameNameAndMobilePhone() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Kochetov Andrew");
        elements.get(1).sendKeys("+7(967)6512486");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    @DisplayName("4.2.Priority message. Should SurnameName Message Output Filled Invalid SurnameName And Checkbox")
    void shouldSurnameNameMessageOutputFilledInvalidSurnameNameAndCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Плещеева Галина83");
        elements.get(1).sendKeys("+79159124687");
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    @DisplayName("4.3.Priority message. Should SurnameName Message Output Filled Invalid SurnameName And Mobile Phone And Checkbox")
    void shouldSurnameNameMessageOutputFilledInvalidSurnameNameAndMobilePhoneAndCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сычева Елена.");
        elements.get(1).sendKeys("79658412790");
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    @DisplayName("4.4.Priority message. Should Mobile Phone Message Output Filled Invalid Mobile Phone And Checkbox")
    void shouldMobilePhoneMessageOutputFilledInvalidMobilePhoneAndCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Михалкова Маргарита");
        elements.get(1).sendKeys("+7983514э520");
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}