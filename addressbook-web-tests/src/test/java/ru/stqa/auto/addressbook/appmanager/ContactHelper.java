package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home page"));
  }

  public void submitNewContactCreation() {
    click(By.name("submit"));
  }

  public void fillNewContactForm(ContactData contactData) {
    type("firstname", contactData.getName());
    type("middlename", contactData.getSurname());
    type("lastname", contactData.getPatronymic());
    type("nickname", contactData.getNickname());
    type("company", contactData.getCompany());
    type("address", contactData.getAddress());
    type("home", contactData.getHometel());
    type("work", contactData.getWorktel());
    type("email", contactData.getEmail());
    wd.findElement(By.name("bday")).click();
    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDatebirth());
    wd.findElement(By.name("bmonth")).click();
    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonthbirth());
    type("byear", contactData.getYearbirth());


  }
}
