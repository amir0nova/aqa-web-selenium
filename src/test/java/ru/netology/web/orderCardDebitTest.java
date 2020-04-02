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

    // Нумерация тестов сплошная.
    // Задача № 1 "Заказ карты".
    //* Раздел 1. Happy Path

    // Тест 01. Описание: заполнение всех полей без особенностей.
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Татьяна");
        elements.get(1).sendKeys("+79205552233");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 02. Описание: введение в поле "Фамилия и имя" имени из 2 слов, разделенных дефисом.
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledSurnameNameHyphenName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Германова Анна-Мария");
        elements.get(1).sendKeys("+79242486148");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 03. Описание: введение в поле "Фамилия и имя" имени из 2 слов, разделенных знаком "минус" (вместо дефиса).
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledSurnameNameMinusName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Бобруева Катерина-Хелена");
        elements.get(1).sendKeys("+79362486148");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 04. Описание: введение в поле "Фамилия и имя" имени из 2-х слов, разделенных пробелом.
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledSurnameNameBlankName() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Муранов Петр Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79372000539");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText(); // search in driver
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 05. Описание: введение в поле "Фамилия и имя" фамилии из 5 слов, разделенных пробелами.
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledSurnameFourHyphensAndName() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Пабло Диего Хозе Франциско Паула Петр");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79108652351");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=order-success]")).getText(); // search in form
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 06. Описание: введение в поле "Мобильный телефон" цифры 8 вместо 7 (формат "+8ХХХХХХХХХХ).
    // Тип ожидаемого значения: success
    @Test
    void shouldMobileNumberFilledFigures8InsteadOf7() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Мухин Вячеслав");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+89298045637");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=order-success]")).getText(); // search in form
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Задача № 2 "Проверка валидации"
    //** Раздел 2. Тесты 07-**: тестирование поля "Фамилия и имя".

    // Тест 07. Описание: заполнение поля "Фамилия и имя" английскими буквами.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledEnglishRussian() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Fedorova Maria");
        elements.get(1).sendKeys("+79182548652");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 08. Описание: введение в поле "Фамилия и имя" китайских букв.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledChinese() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Калугина 娜斯佳");
        elements.get(1).sendKeys("+79864512361");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 09. Описание: введение в поле "Фамилия и имя" точки ".".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullStop() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Капитонова Анна.");
        elements.get(1).sendKeys("+79802456385");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 10. Описание: введение в поле "Фамилия и имя" точки с запятой ";".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullSmicolon() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мартынов Николай;");
        elements.get(1).sendKeys("+79035698521");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 11. Описание: введение в поле "Фамилия и имя" подчеркивания "_".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullUnderline() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Беляков Слава_");
        elements.get(1).sendKeys("+79064583624");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 12. Описание: введение в поле "Фамилия и имя" знака вопроса "?".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullQuestionMark() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Ере?кин Максим");
        elements.get(1).sendKeys("+79635218569");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 13. Описание: введение в поле "Фамилия и имя" двоеточия ":".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullColon() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Бобыш: Димитрий");
        elements.get(1).sendKeys("+79665214762");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 14. Описание: введение в поле "Фамилия и имя" цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullFigure() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Белова2 Татьяна");
        elements.get(1).sendKeys("+79769524563");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 15. Описание: введение в поле "Фамилия и имя" спецсимвола ^.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullSpecialCharacter1() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Бояринова^Мария");
        elements.get(1).sendKeys("+78695124563");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 16. Описание: введение в поле "Фамилия и имя" спецсимвола №.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullSpecialCharacter2() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Перлов№Глеб");
        elements.get(1).sendKeys("+79781254631");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 17. Описание: введение в поле "Фамилия и имя" спецсимвола %.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullSpecialCharacter3() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("%Федорова Ирина");
        elements.get(1).sendKeys("+79248542351");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 18. Описание: поле "Фамилия и имя" не заполнено.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullBlankField() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79836598451");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 19. Описание: введение в поле "Фамилия и имя" пробела " ".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledBlank() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys(" ");
        elements.get(1).sendKeys("+79041248695");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    //*** Раздел 3. Тесты 20-**: тестирование поля "Мобильный телефон".

    // Тест 20. Описание: поле "Мобильный телефон" не заполнено.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledFullBlankField() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Еремина Полина");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 21. Описание: введение в поле "Мобильный телефон" пробела " ".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledFilledBlank() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Винградова Ольга");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys(" ");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 22. Описание: введение в поле "Мобильный телефон" скобок "(" и ")".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBrackets() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Винградова Ольга");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7(916)6594328");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 23. Описание: введение в поле "Мобильный телефон" пробела " " между цифрами.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBlankInside() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Смирнофф Олег");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7 9655418953");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 24. Описание: введение в поле "Мобильный телефон" пробела " " вместо "+".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBlankInsteadOfPlus() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Чимарев Павел");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys(" 79655418953");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 25. Описание: введение в поле "Мобильный телефон" пробела " " вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBlankInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Малинин Александр");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79836589_22");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 26. Описание: введение в поле "Мобильный телефон" дефиса "-".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledHyphen() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Малинин Александр");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7-9069518564");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 27. Описание: введение в поле "Мобильный телефон" знака минус "-" (вместо дефиса).
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledMinus() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Курочкин Федор");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7983784-5623");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 28. Описание: введение в поле "Мобильный телефон" номера без знака плюс "+".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledWihtoutPlus() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Алехин Михаил");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("79085612487");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 29. Описание: введение в поле "Мобильный телефон" номера в 10-значном формате (без "+7").
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled10SinglesFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Кирюхин Димон");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("9086542685");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 30. Описание: ведение в поле "Мобильный телефон" городского номера (Москва) в 10-значном формате.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledNotMobilInInternational10SinglesFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Польский Лев");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("4958463981");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 31. Описание: введение в поле "Мобильный телефон" номера, состоящего из 9 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled9Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Морозова Кэт");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("926584751");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 32. Описание: введение в поле "Мобильный телефон" номера, состоящего из 8 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled8Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Морозова Кэт");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("91350140");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 33. Описание: введение в поле "Мобильный телефон" номера, состоящего из 7 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled7Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Крафт София");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("9875610");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 34. Описание: введение в поле "Мобильный телефон" номера, состоящего из 6 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled6Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Викинг Борис");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("905003");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 35. Описание: введение в поле "Мобильный телефон" номера, состоящего из 5 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled5Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Головин Виктор");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("23510");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 36. Описание: введение в поле "Мобильный телефон" номера, состоящего из 4 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled4Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Головин Виктор");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("0568");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 37. Описание: введение в поле "Мобильный телефон" номера, состоящего из 3 цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled3Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Пустовойтов Гриша");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("729");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 38. Описание: введение в поле "Мобильный телефон" 2-х цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled2Singles() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Григорьева Марина");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("62");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 39. Описание: введение в поле "Мобильный телефон" 1 цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled1Single() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Мельникова Тамара");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("0");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 40. Описание: введение в поле "Мобильный телефон" знака "+" и 12-ти цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledPlusAnd12Single() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Жирова Ольга");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+796458654190");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 41. Описание: введение в поле "Мобильный телефон" знака "+" и 25-ти цифр.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledPlusAnd25Single() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Подмухин Даниил");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7923508125412819365012834");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 42. Описание: введение в поле "Мобильный телефон" знака плюс "+".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledPlus() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Чуприянов Максим");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 43. Описание: введение в поле "Мобильный телефон" знака плюс "+" вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledPlusInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Куликов Семен");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7928692+308");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 44. Описание: введение в поле "Мобильный телефон" знака равно "=" вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledqualInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Танин Александр");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7914=065844");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 45. Описание: введение в поле "Мобильный телефон" цифры 8 вместо "+7".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledFigures8InsteadOfPlus7() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Безруков Марк");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("89112659410");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 46. Описание: введение в поле "Мобильный телефон" буквы вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledLetterInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Покровский Валентин");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+798125а3329");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 47. Описание: введение в поле "Мобильный телефон" точки "." вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledFullStopInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Дурова Инга");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+7904.561029");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 48. Описание: введение в поле "Мобильный телефон" запятой "," вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledCommaInsteadOfFigure() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Пуговкин Илья");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79065,21833");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 49. Описание: ведение в поле "Мобильный телефон" городского номера (Москва) в междуноардном формате.
    // Тип ожидаемого значения: success
    @Test
    void shouldMobileNumberFilledNotMobilInInternationalFormatV1() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Золотова Диана");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+74952254433");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 50. Описание: ведение в поле "Мобильный телефон" городского номера (Тверь) в междуноардном формате.
    // Тип ожидаемого значения: success
    @Test
    void shouldMobileNumberFilledNotMobailInInternationalFormatV2() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Тверская Дарья");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+74822325103");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 51. Описание: заполнение поля "Мобильный телефон" номером в американском формате.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledAmerFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Пупкин Макс");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+134781549035");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 52. Описание: заполнение поля "Мобильный телефон" номером в украинском формате.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledUkrainianFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Галушко Галина");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+380698542631");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 53. Описание: заполнение поля "Мобильный телефон" номером в белорусском формате.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledByelorussianFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Селиванов Иван");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+375514586325");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 54. Описание: заполнение поля "Мобильный телефон" номером в казахстанском формате.
    // Тип ожидаемого значения: success
    @Test
    void shouldMobileNumberFilledKazakhFormat() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Киреев Ахмат");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+76385268410");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 55. Описание: заполнение поля "Мобильный телефон" номером в формате +7200ХХХХХХХ.
    // Тип ожидаемого значения: success
    @Test
    void shouldMobileNumberFilledFormatWith200() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Аганесян Артур");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+78002003355");
        form.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    //**** Раздел 4. Тестирование чекбокса (согласие на обработку данных и запрос кредитной истории).

    // Тест 56. Описание: отсутствие клика по чекбоксу.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldCheckboxDoNotCheck() {
        driver.get("http://localhost:9999");
        WebElement form = driver.findElement(By.cssSelector("body"));
        form.findElement(By.cssSelector("[data-test-id=name] .input__control")).sendKeys("Муранов Петр Василий");
        form.findElement(By.cssSelector("[data-test-id=phone] .input__control")).sendKeys("+79372000539");
        form.findElement(By.cssSelector("[data-test-id=agreement]")); //.click();
        form.findElement(By.cssSelector("[type=button]")).click();
        String text = form.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    //***** Раздел 5. Порядок обработки некорректного ввода (выводится информаци по первому некорректно заполненному полю).

    // Тест 57. Описание: некорректно заполнены поля "Фамилия и имя", "Мобильный телефон".
    // Тип ожидаемого значения: invalid input для поля "Фамилия и имя"
    @Test
    void shouldSurnameAndNameAndMobailePhoneFilledNotCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Kochetov Andrew");
        elements.get(1).sendKeys("+7(967)6512486");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 58. Описание: некорректно заполнены поля "Фамилия и имя", "Мобильный телефон" не отмечен чекбокс.
    // Тип ожидаемого значения: invalid input для поля "Фамилия и имя"
    @Test
    void shouldSurnameAndNameAndMobailePhoneAndChexkboxFilledNotCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сычева Елена.");
        elements.get(1).sendKeys("79658412790");
        driver.findElement(By.className("checkbox__box")); //.click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.className("input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 59. Описание: некорректно заполнено поле "Мобильный телефон", не отмечен чекбокс.
    // Тип ожидаемого значения: invalid input для поля "Мобильный телефон"
    @Test
    void shouldMobailePhoneAndCheckboxFilledNotCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Михалкова Маргарита");
        elements.get(1).sendKeys("+7983514э520");
        driver.findElement(By.className("checkbox__box")); //.click();
        driver.findElement(By.className("button_theme_alfa-on-white")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}