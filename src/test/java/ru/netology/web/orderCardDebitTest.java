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

    //* Раздел 1. Проверка текста выводимых сообщений

    // Тест 01. Описание: сообщение о корректном вводе в поле «Фамилия и имя» (исправление ошибки).
    // БАГ!!!!!         ++++
    @Test
    void shouldMessageFilledCorrectSurnameAndName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Болдин Максим");
        elements.get(1).sendKeys("+79045430296");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с Вами в ближайшее время.", text.trim());
    }

    // Тест 02. Описание: сообщение о некорректном вводе в поле «Фамилия и имя» (исправление 3-х ошибок).
    //          БАГ!!!!! ++++
    @Test
    void shouldMessageFilledInvalidInSurnameAndName1() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Semyonova Darya");
        elements.get(1).sendKeys("+79112314930");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Фамилия и имя указаны неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 03. Описание: сообщение о незаполненном поле "Фамилия и имя" (ошибка: отсутствует точка).
    //   БАГ!!!!! ++++
    @Test
    void shouldMassageFilledFullBlankFieldSurnameAndName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        // elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79836598451");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Поле обязательно для заполнения.", text.trim());
    }

    // Тест 04. Сообщение о незаполненном поле "Мобильный телефон" (ошибка: отсутствует точка).
    //  БАГ!!! Красный    ++++
    @Test
    void shouldMassageFilledFullBlankFieldMobileNumber() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Гранин Давид");
        //elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Поле обязательно для заполнения.", text.trim());
    }

    // Тест 05. Описание: сообщение об отсутствии согласия на обработку персональных данных и запрос кредитной истории (ошибка: отсутствует точка).
    //БАГ!!! красный  ++++
    @Test
    void shouldMessageCheckedDoNotCheckbox() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Муранов Василий");
        elements.get(1).sendKeys("+");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй.", text.trim());
    }

    // Тест 06. Описание: НЕСОГЛАСОВАННЫЙ ТЕКСТ сообщения о введении в поле "Фамилия и имя" фамилии ИЛИ имени
    //               БАГ!!!! Красный
    @Test
    void shouldMessageFilledDoNotAgreementV1() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Смирнофф");
        elements.get(1).sendKeys("+79295148365");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Фамилия и имя указаны неверно. Проверьте введенные данные. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)", text.trim());
    }

    // Тест 07. Описание: НЕСОГЛАСОВАННЫЙ ТЕКСТ сообщения о введении в поле "Мобильный телефон" номера в формате "+8ХХХХХХХХХХ" (11 цифр).
    // Тип ожидаемого значения: БАГ, красный!!!!!!!!! ++++
    @Test
    void shouldMessageFilledDoNotAgreementV2() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Полонский Альберт");
        elements.get(1).sendKeys("+89135214892");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, первая цифра 7, например, +79012345678. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)", text.trim());
    }

    // Тест 08. Описание: НЕСОГЛАСОВАННЫЙ ТЕКСТ сообщения о введении в поле "Мобильный телефон" городского номера в междуноардном формате.
    // Тип ожидаемого значения: success БАГ, красный!!!!!! ++++
    @Test
    void shouldMessageFilledDoNotAgreementV3() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Подмосковный Олег");
        elements.get(1).sendKeys("+74953254369");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("«Введен номер городского телефона. Введите номер мобильного телефона. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)»", text.trim());
    }

    //=============||
            //     >>>>
    //=============||

    //* Раздел 1. Happy Path
    // Тест 09. Описание: заполнение всех полей без особенностей.
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова Татьяна");
        elements.get(1).sendKeys("+79205552233");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 10. Описание: введение в поле "Фамилия и имя" двойной фамилии (дефис). (исправлено)
    // Тип ожидаемого значения: success
    @Test
    void shouldFormFilledDoubleSurnameWithHypheName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Иванова-Ипполитова Анна");
        elements.get(1).sendKeys("+79242486148");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    // Тест 11. Описание: введение в поле "Фамилия и имя" фамилии и/или имени, содержащих "ё". (добавлено)
    // Тип ожидаемого значения: БАГ!!!! Красный
    @Test
    void shouldFormFilledLetterYoInSurnameOrName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Ерёмкина Полина");
        elements.get(1).sendKeys("+79065610823");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("paragraph_theme_alfa-on-white")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }



    //** Раздел 2. Пустое поле
    //** Подраздел 2.1. Для поля "Фамилия и имя"

    // Тест 12. Описание: поле "Фамилия и имя" не заполнено.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullBlankField() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        //elements.get(0).sendKeys("");
        elements.get(1).sendKeys("+79836598451");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 13. Описание: введение в поле "Фамилия и имя" пробела " ".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledBlank() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys(" ");
        elements.get(1).sendKeys("+79041248695");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 14. Описание: введение в поле "Фамилия и имя" 2-х пробелов "  ". (добавлено)
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledTreeBlanks() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("  ");
        elements.get(1).sendKeys("+79041248695");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    //** Подраздел 2.1. Для поля "Мобильный телефон"

    // Тест 15. Описание: поле "Мобильный телефон" не заполнено.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledFullBlankField() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Гранин Давид");
        //elements.get(1).sendKeys("");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    // Тест 16. Описание: введение в поле "Мобильный телефон" пробела " ".
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBlank() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Моргунов Николай");
        elements.get(1).sendKeys(" ");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }


    //*** Раздел 3. Некорректный ввод в поле "Фамилия и имя"

    // Тест 17. Описание: введение в поле "Фамилия и имя" фамилии или имени.// НЕСОГЛАСОВАННОЕ СООБЩЕНИЕ.
    //               БАГ!!!! Красный
    @Test
    void shouldFormFilledDoubleSurnameOrName() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кирилл");
        elements.get(1).sendKeys("+79135432890");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Фамилия и имя указаны неверно. Проверьте введенные данные. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)", text.trim());
    }

    // Тест 18. Описание: заполнение поля "Фамилия и имя" английскими буквами.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledEnglishRussian() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Fedorova Maria");
        elements.get(1).sendKeys("+79182548652");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 19. Описание: введение в поле "Фамилия и имя" спецсимволов $%. (исправлено)
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldSurnameAndNameFilledFullSpecialCharacters() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Бояринова Мария$%");
        elements.get(1).sendKeys("+78695124563");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }


    //*** Раздел 3. Некорректный ввод в поле "Мобильный телефон"
    //*** Подраздел 3.1. "Типичные" случаи"

    // Тест 20. Описание: введение в поле "Мобильный телефон" номера в формате " 7ХХХХХХХХХХ" (11 цифр).
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledBlankInsteadOfPlus() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Чимарев Павел");
        elements.get(1).sendKeys(" 79655418953");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 21. Описание: введение в поле "Мобильный телефон" номера в формате "7ХХХХХХХХХХ" (11 цифр).
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledWihtoutPlus() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Алехин Михаил");
        elements.get(1).sendKeys("79085612487");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 22. Описание: введение в поле "Мобильный телефон" номера в 10-значном формате "ХХХХХХХХХХ" (10 цифр).
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilled10SinglesFormat() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Кирюхин Димон");
        elements.get(1).sendKeys("9086542685");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 23. Описание: введение в поле "Мобильный телефон" номера в формате "+7ХХХХХХХХХХХ" (12 цифр).
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledPlusAnd12Single() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Жилова Ольга");
        elements.get(1).sendKeys("+796458654190");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 24. Описание: введение в поле "Мобильный телефон" знаков "=+ _-" вместо цифр. (исправлено)
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledSpecialCharacter() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Танин Александр");
        elements.get(1).sendKeys("=+ _-8945569");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    // Тест 25. Описание: введение в поле "Мобильный телефон" буквы вместо цифры.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldMobileNumberFilledLetterInsteadOfFigure() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Покровский Валентин");
        elements.get(1).sendKeys("+798125а3329");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    //*** Подраздел 3.2. Введение в поле "Мобильный телефон": БАГИ

    // Тест 26. Описание: введение в поле "Мобильный телефон" номера в формате "+8ХХХХХХХХХХ" (11 цифр) // НЕСОГЛАСОВАННОЕ СООБЩЕНИЕ.
    // Тип ожидаемого значения: БАГ, красный!!!!!!!!! ++++
    @Test
    void shouldMobileNumberFilledFigures8InsteadOf7() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Мухин Вячеслав");
        elements.get(1).sendKeys("+89298045637");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, первая цифра 7, например, +79012345678. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)", text.trim());
    }

    // Тест 27. Описание: ведение в поле "Мобильный телефон" городского номера (Москва) в междуноардном формате // НЕСОГЛАСОВАННОЕ СООБЩЕНИЕ.
    // Тип ожидаемого значения: success БАГ, красный!!!!!! ++++
    @Test
    void shouldMobileNumberFilledNotMobilInInternationalFormatV1() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Замоскворецкая Диана");
        elements.get(1).sendKeys("+74952254433");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("«Введен номер городского телефона. Введите номер мобильного телефона. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)»", text.trim());
    }

    // Тест 28. Описание: ведение в поле "Мобильный телефон" городского номера (Тверь) в междуноардном формате // НЕСОГЛАСОВАННОЕ СООБЩЕНИЕ.
    // Тип ожидаемого значения: successю. БАГ, красный!!!!!!!
    @Test
    void shouldMobileNumberFilledNotMobailInInternationalFormatV2() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Тверская Дарья");
        elements.get(1).sendKeys("+74822325103");
        driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("«Введен номер городского телефона. Введите номер мобильного телефона. (СООБЩЕНИЕ НЕ СОГЛАСОВАНО)»", text.trim());
    }


    //**** Раздел 4. Тестирование чекбокса (согласие на обработку данных и запрос кредитной истории).

    // Тест 29. Описание: отсутствие клика по чекбоксу.
    // Тип ожидаемого значения: invalid input
    @Test
    void shouldCheckboxDoNotCheck() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Муранов Василий");
        elements.get(1).sendKeys("+");
        //driver.findElement(By.className("checkbox__box")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }

    //***** Раздел 5. Порядок обработки некорректного ввода (выводится информаци по первому некорректно заполненному полю).

    // Тест 30. Описание: некорректно заполнены поля "Фамилия и имя", "Мобильный телефон".
    // Тип ожидаемого значения: invalid input для поля "Фамилия и имя"
    @Test
    void shouldSurnameAndNameAndMobailePhoneFilledNotCorrectly() {
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

    // Тест 31. Описание: некорректно заполнены поля "Фамилия и имя", "Мобильный телефон", не отмечен чекбокс.
    // Тип ожидаемого значения: invalid input для поля "Фамилия и имя"
    @Test
    void shouldSurnameAndNameAndMobilePhoneAndCheckboxFilledNotCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Сычева Елена.");
        elements.get(1).sendKeys("79658412790");
        driver.findElement(By.className("checkbox__box")); //.click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(0).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    // Тест 32. Описание: некорректно заполнено поле "Мобильный телефон", не отмечен чекбокс.
    // Тип ожидаемого значения: invalid input для поля "Мобильный телефон"
    @Test
    void shouldMobilePhoneAndCheckboxFilledNotCorrectly() {
        driver.get("http://localhost:9999");
        List<WebElement> elements = driver.findElements(By.className("input__control"));
        elements.get(0).sendKeys("Михалкова Маргарита");
        elements.get(1).sendKeys("+7983514э520");
        driver.findElement(By.className("checkbox__box")); //.click();
        driver.findElement(By.className("button")).click();
        List<WebElement> elements2 = driver.findElements(By.className("input__sub"));
        String text = elements2.get(1).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
}