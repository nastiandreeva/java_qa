package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import java.util.List;


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
    type("home", contactData.getHomeTel());
    type("mobile", contactData.getMobileTel());
    type("work", contactData.getWorkTel());
    type("email", contactData.getEmail1());
    type("email2", contactData.getEmail2());
    type("email3", contactData.getEmail3());
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
//    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
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
      String address = cells.get(3).getText();
      String allEmails = cells.get(4).getText();
      String allPhones = cells.get(5).getText();                                                             // получаем из ячейки телефонов значения через разделитель по регулярке
//      String[] allPhones = cells.get(5).getText().split("\n");                                             // получаем из ячейки телефонов значения через разделитель по регулярке
      contacts.add(new ContactData().withId(id).withName(nameContact).withSurname(surnameContact)
              .withAddress(address).withAllPhones(allPhones).withAllEmails(allEmails));
//      contacts.add(new ContactData().withId(id).withName(nameContact).withSurname(surnameContact)
//              .withHometel(phones[0]).withMobileTel(phones[1]).withWorkTel(phones[2]));                     //создаем объект типа ContactData
    }
    return contacts;
  }

  public ContactData infoFromEditForm(ContactData contact) {
    initContactModification(contact.getId());
    String firstname = wd.findElement(By.name("firstname")).getAttribute("value");
    String lastname = wd.findElement(By.name("lastname")).getAttribute("value");
    String address = wd.findElement(By.name("address")).getAttribute("value");
    String home = wd.findElement(By.name("home")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withName(firstname).withSurname(lastname).withHometel(home)
            .withAddress(address)
            .withMobileTel(mobile).withWorkTel(work)
            .withEmail1(email1).withEmail2(email2).withEmail3(email3);
  }
}
