package ru.dexsys.steps;

import cucumber.api.java.ru.Дано;
import cucumber.api.java.ru.Когда;
import cucumber.api.java.ru.Тогда;
import org.junit.Assert;
import ru.dexsys.RestfulBookerMethods;

public class MyStepdefs {

    private final RestfulBookerMethods restfulBookerMethods = new RestfulBookerMethods();

    @Дано("^Пользователь авторизуется с логином '(.*)' и паролем '(.*)'$")
    public void пользовательАвторизуетсяСЛогиномUsernameИПаролемPassword(String userName, String password) {
        Assert.assertFalse("Ошибка авторизации. Токен не получен",
               restfulBookerMethods.createToken(userName,password).contains("" +
                       "{\"reason\":\"Bad credentials\"}"));
    }

    @Когда("^Пользователь создает новый заказ$")
    public void пользовательСоздаетНовыйЗаказ() {
       Assert.assertNotNull("Новый заказ не создан. Произошла ошибка", restfulBookerMethods.createBooking());
    }

    @Тогда("^Пользователь проверяет наличие заказа на сайте$")
    public void пользовательПроверяетНаличиеЗаказаНаСайте() {
       Assert.assertNotNull("Заказ на сайте отсутствует", restfulBookerMethods.getBooking());
    }
}
