package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.SearchContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import java.util.List;

import static java.lang.String.format;

public class ContactHelper extends HelperBase{

  public ContactHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToContactPage() {
    click(By.linkText("home"));
  }

  public void submitNewContactCreation() {
    click(By.name("submit"));
  }

  public void fillNewContactForm(ContactData contactData) {
    type("firstname", contactData.getName());
    type("middlename", contactData.getPatronymic());
    type("lastname", contactData.getSurname());
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

  public void selectContactById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "']")).click();
  }

  public void initContactModification(int id) {
//    click(By.xpath("//img[@alt='Edit']"));
    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
//    click(By.xpath("//*[@id='maintable']/tbody//a[@href='edit.php?id='" + id + "']"));
  }

  public void submitContactModification() {
    click(By.name("update"));
  }

  public void goToPageContactModification() {
    click(By.xpath("//form[@action='edit.php']"));
  }

  public void submitContactDelete() {
    click(By.xpath("//input[@value='Delete']"));
    acceptDelete();
  }

  public void create(ContactData contact) { //метод для создания контакта, нужен для предусловий при выполнении кейсов по удалению/изменению контакта
    fillNewContactForm(contact);
    submitNewContactCreation();
    returnToContactPage();
  }

  public void delete(ContactData contact) {
    selectContactById(contact.getId());
    submitContactDelete();
    returnToContactPage();
  }

  public void modify(ContactData contact) {
    selectContactById(contact.getId());
    initContactModification(contact.getId());
    goToPageContactModification();
    fillNewContactForm(contact);
    submitContactModification();
    returnToContactPage();
  }

  public boolean isThereAContact() { //проверка наличия элемента для дальнейшего изменения/удаления
    return isElementPresent(By.name("selected[]"));
  }

  public int count() {
    return wd.findElements(By.name("selected[]")).size();
  }

  public Contacts all() {
    Contacts contacts = new Contacts();
    List<WebElement> elements = wd.findElements(By.xpath("//*[@id='maintable']/tbody/tr[@name = 'entry']")); //получаем список элементов в лист elements через xpath
    for (WebElement element : elements) {                                                                    //проходимся по списку elements
      List<WebElement> cells = element.findElements(By.tagName("td"));
      int id = Integer.parseInt(cells.get(0).findElement(By.tagName("input")).getAttribute("value"));  //получаем значение из тегов на странице
      String surnameContact = cells.get(1).getText();                                                        //получаем текст элемента списка в переменную
      String nameContact = cells.get(2).getText();
      contacts.add(new ContactData().withId(id).withName(nameContact).withSurname(surnameContact));          //создаем объект типа ContactData
    }
    return contacts;
  }
}
