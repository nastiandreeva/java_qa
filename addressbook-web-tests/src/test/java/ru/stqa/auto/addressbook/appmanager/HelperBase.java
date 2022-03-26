package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;

// базовый класс для всех помощников
public class HelperBase {
  protected WebDriver wd;

  public HelperBase(WebDriver wd) {
    this.wd = wd;
  }

  protected void type(String locator, String text) {
    click(By.name(locator));
    if (text != null){
      String existingText =  wd.findElement(By.name(locator)).getAttribute("value");
      if (! text.equals(existingText)) {
        wd.findElement(By.name(locator)).clear();
        wd.findElement(By.name(locator)).sendKeys(text);
      }
    }
  }

  protected void click(By locator) {
    wd.findElement(locator).click();
  }

  protected void getLink(String locator) {
    wd.get(String.valueOf(locator));
  }

  public void acceptDelete() {
    wd.switchTo().alert().accept();
  }

  public boolean isAlertPresent() {
    try {
      wd.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  protected boolean isElementPresent(By locator) { //поиск элемента на странице и выброс исключения если элемент не найден
    try {
      wd.findElement(locator);
      return true;
    } catch (NoSuchElementException ex){
      return false;
    }
  }
}
