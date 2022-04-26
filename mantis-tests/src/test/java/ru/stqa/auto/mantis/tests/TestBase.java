package ru.stqa.auto.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.auto.mantis.appmanager.ApplicationManager;

public class TestBase {

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
}
