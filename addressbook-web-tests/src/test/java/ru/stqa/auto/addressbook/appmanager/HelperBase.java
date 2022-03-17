package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.WebDriver;

// базовый класс для всех помощников
public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void type(String locator, String text) {
    click(By.name(locator));
    wd.findElement(By.name(locator)).clear();
    wd.findElement(By.name(locator)).sendKeys(text);
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
