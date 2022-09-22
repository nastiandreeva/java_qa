package ru.stqa.auto.mantis.tests;

import org.openqa.selenium.remote.BrowserType;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import ru.stqa.auto.mantis.appmanager.ApplicationManager;

import java.io.File;
import java.io.IOException;

public class TestBase {

  protected static final ApplicationManager app                                                 //создали делегирование вместо наследования
          = new ApplicationManager(System.getProperty("browser", BrowserType.CHROME));      // создаем переменную для указания браузера в конфигурационных настройках, иначе если не указан запускаем в хроме

  @BeforeSuite
  public void setUp() throws Exception {
    app.init();
    app.ftp().upload(new File("src/test/resources/config_inc.php"), "config_inc.php", "config_inc.php.bak"); //ToDO не проходит отключение капчи
  }

  @AfterSuite(alwaysRun = true)
  public void tearDown() throws IOException {
    app.ftp().restore("config_inc.php.bak", "config_inc.php");
    app.stop();
  }
}
