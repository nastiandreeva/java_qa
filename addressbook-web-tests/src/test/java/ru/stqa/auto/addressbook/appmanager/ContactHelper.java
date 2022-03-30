package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
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

//    if (creation) { //условие на то, что если это страницы создания контакта то поле выбора группы есть, иначе если страница модификации, то выпадающего списка выбора группы не должно быть
//      new Select(wd.findElement(By.name("new_group"))).selectByVisibleText(contactData.getGroup());
//    } else {
//      Assert.assertFalse(isElementPresent(By.name("new_group")));
//    }
  }

  public void selectContact() {
    click(By.name("selected[]"));
  }

  public void initContactModification() {
    click(By.xpath("//img[@alt='Edit']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

//  public void getLinkContactModification() {
//    getLink("http://localhost/addressbook/edit.php?id=46");
//  }

  public void goToPageContactModification() {
    click(By.xpath("//form[@action='edit.php']"));
  }

  public void submitContactDelete() {
    click(By.xpath("//input[@value='Delete']"));
  }

  public void createContact(ContactData contact) { //метод для создания контакта, нужен для предусловий при выполнении кейсов по удалению/изменению контакта
    fillNewContactForm(contact);
    submitNewContactCreation();
    returnToContactPage();
  }

  public boolean isThereAContact() { //проверка наличия элемента для дальнейшего изменения/удаления
    return isElementPresent(By.name("selected[]"));
  }
}
