package ru.netology.card;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class TestCardDelivery {

    private DataGenerator dataGenerator = new DataGenerator();
    String city = dataGenerator.getCity();
    String name = dataGenerator.getName();
    String phone = dataGenerator.getPhone();

    @BeforeEach
    void setupClass() {
        open("http://localhost:9999/");
    }

    @Test
    void shouldSendValidValueAndChangeDate() {
        $("[data-test-id=city] .input__control").sendKeys(city);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(Keys.DELETE);
        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(5));
        $("[data-test-id=name] .input__control").sendKeys(name);
        $("[data-test-id=phone] .input__control").sendKeys(phone);
        $("[data-test-id=agreement] .checkbox__box").click();
        $("[class=button__text]").click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]  .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=success-notification]  .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(5)));

        $("[data-test-id=date] .input__control").doubleClick().sendKeys(dataGenerator.getDate(7));
        $("[class=button__text]").click();
        $("[data-test-id=replan-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=replan-notification] .notification__title")
                .shouldHave(exactText("Необходимо подтверждение"));
        $(byText("Перепланировать")).click();
        $("[data-test-id=success-notification]").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id=success-notification]  .notification__title")
                .shouldHave(exactText("Успешно!"));
        $("[data-test-id=success-notification]  .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + dataGenerator.getDate(7)));
    }


}
