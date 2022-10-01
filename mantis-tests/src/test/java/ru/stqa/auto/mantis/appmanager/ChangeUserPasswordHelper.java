package ru.stqa.auto.mantis.appmanager;

import org.openqa.selenium.By;

public class ChangeUserPasswordHelper extends HelperBase {

  public ChangeUserPasswordHelper (ApplicationManager app) {
    super(app);
  }

  public void login(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/login_page.php");
    type(By.name("username"), username);
    click(By.cssSelector("input[type='submit']"));
    type(By.name("password"), email);
    click(By.cssSelector("input[type='submit']"));

  }

  public void start(String username, String email) {
    wd.get(app.getProperty("web.baseUrl") + "/signup_page.php");
    type(By.name("username"), username);
    type(By.name("email"), email);
    click(By.cssSelector("input[type='submit']"));

  }

  public void goToManageUsers(String user) throws InterruptedException {
    click(By.id("menu-toggler"));
    click(By.xpath("//*[@id=\"sidebar\"]/ul/li[6]/a"));
    click(By.xpath("//*[@id=\"main-container\"]/div[2]/div[2]/div/ul/li[2]/a"));            // выбрали ранее созданного пользователя и провалились в него TODO лучше бы брать по регулярке по тексту в теге <a>
    click(By.linkText(user));
    Thread.sleep(1000);
    click(By.xpath("//*[@id=\"manage-user-reset-form\"]/fieldset/span/input"));             // сбросили ему пароль
    Thread.sleep(1000);
  }

  public void finish(String confirmationLink, String password) {
    wd.get(confirmationLink);
    type(By.name("password"), password);
    type(By.name("password_confirm"), password);
    click(By.xpath("//*[@id=\"account-update-form\"]/fieldset/span/button/span['Изменить пользователя']"));
  }

}
