package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SessionHelper extends HelperBase{

  public SessionHelper(WebDriver wd) {
    super(wd);
  }

  public void login(String username, String password) {
    type(String.valueOf(By.name("user")), username);
    type(String.valueOf(By.name("pass")), password);
    click(By.xpath("//input[@value='Login']"));
  }

  public void logout() {
    wd.findElement(By.linkText("Logout")).click();
  }

}
