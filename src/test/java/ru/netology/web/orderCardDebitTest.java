package ru.netology.web;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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

    // number 1
    @Test
    void shouldFormFilledCorrectlyWithClassName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Татьяна");
        elements.get(1).sendKeys("+79105552233");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // number 2
    @Test
    void shouldFormFilledSurnameNameHyphenNameWithClassName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Германова Анна-Мария");
        elements.get(1).sendKeys("+79105552233");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // number 3
    @Test
    void shouldFormFilledSurnameDoubleNameWithClassName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Муранов Петр Василий");
        elements.get(1).sendKeys("+78002000539");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // number 4
    @Test
    void shouldFormFilledSurnameForeHyphensAndNameWithClassName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Пабло Диего Хозе Франциско Паула Петр");
        elements.get(1).sendKeys("+74998652351");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Задача №2 - Проверка валидации (необязательная)

    @Test
    void shouldSurnameAndNameFilledRussianWithClassName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Fedorova Maria");
        elements.get(1).sendKeys("+79252548652");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
}
