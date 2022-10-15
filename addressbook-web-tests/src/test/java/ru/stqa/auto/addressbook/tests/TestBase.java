package ru.stqa.auto.addressbook.tests;

import org.openqa.selenium.remote.BrowserType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import ru.stqa.auto.addressbook.appmanager.ApplicationManager;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

public class TestBase {

  Logger logger = LoggerFactory.getLogger(GroupCreationTests.class);

  protected static final ApplicationManager app                                                 //создали делегирование вместо наследования
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));      // создаем переменную для указания браузера в конфигурационных настройках, иначе если не указан запускаем в хроме

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws Exception {
    app.stop();
  }

  @BeforeMethod
  public void logTestStart(Method m, Object[] p){
    logger.info("Start test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  @AfterMethod (alwaysRun = true)
  public void logTestStop(Method m, Object[] p){
    logger.info("Stop test " + m.getName() + " with parameters " + Arrays.asList(p));
  }

  public void verifyGroupListInUi() {
    if (Boolean.getBoolean("verifyUi")) {                                                  // VM options : -DverifyUI true - включаем проверку в "Edit Configurations"
      Groups dbGroups = app.db().groups();
      Groups uiGroups = app.group().all();
      assertThat(uiGroups, equalTo(dbGroups.stream().map((g) -> new GroupData()
              .withId(g.getId()).withName(g.getName()))                                           // упростим полученные с бд данные и оставим для сравнения только те данные, которые есть на ui
              .collect(Collectors.toSet())));                                                     // соберем эти данные в коллекцию
    }
  }


  public void verifyContactListInUi() {
    if (Boolean.getBoolean("verifyUi")) {                                                  // VM options : -DverifyUI true - включаем проверку в "Edit Configurations"
      Contacts dbContacts = app.db().contacts();
      Contacts uiContacts = app.contact().all();
      assertThat(uiContacts, equalTo(dbContacts.stream().map((g) -> new ContactData()
              .withId(g.getId()).withName(g.getName()).withSurname(g.getSurname()))              // упростим полученные с бд данные и оставим для сравнения только те данные, которые есть на ui
              .collect(Collectors.toSet())));                                                    // соберем эти данные в коллекцию
    }
  }
}
