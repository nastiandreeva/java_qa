package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;
import ru.stqa.auto.addressbook.model.GroupData;

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
    type(By.name("firstname"), contactData.getName());
    type(By.name("lastname"), contactData.getSurname());
    type(By.name("company"), contactData.getCompany());
    type(By.name("address"), contactData.getAddress());
    type(By.name("home"), contactData.getHomeTel());
    type(By.name("mobile"), contactData.getMobileTel());
    type(By.name("work"), contactData.getWorkTel());
    type(By.name("email"), contactData.getEmail1());
    type(By.name("email2"), contactData.getEmail2());
    type(By.name("email3"), contactData.getEmail3());
//    attach(By.name("photo"), contactData.getPhoto());
//    wd.findElement(By.name("bday")).click();
//    new Select(wd.findElement(By.name("bday"))).selectByVisibleText(contactData.getDatebirth());
//    wd.findElement(By.name("bmonth")).click();
//    new Select(wd.findElement(By.name("bmonth"))).selectByVisibleText(contactData.getMonthbirth());
//    type("byear", contactData.getYearbirth());
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
//    wd.findElement(By.xpath(String.format("//input[@value='%s']/../../td[8]/a", id))).click();
    wd.findElement(By.cssSelector(String.format("a[href='edit.php?id=%s']", id))).click();
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
    initContactModification(contact.getId());
    goToPageContactModification();
    fillNewContactForm(contact);
    submitContactModification();
    returnToContactPage();
  }

  public void addContactInGroup(ContactData contact, String groupName){
    selectContactById(contact.getId());
    setGroupForContact(groupName);
    addInGroup();
    returnToContactPage();
  }

//  public void selectGroupForAdd(String nameGroup) {
//    selectContactById(idContact);
//    int idGroup = idGroupWithoutContact(idContact);
//    System.out.println(idGroup);
//
//    selectContactById(contact.getId());
//    setGroupForContact(idGroup);
//  }

  public void setGroupForContact(String nameGroup) {
    click(By.name("to_group"));
    new Select(wd.findElement(By.name("to_group"))).selectByVisibleText(nameGroup);
  }

  public void addInGroup(){
    click(By.name("add"));
  }

  public void selectContactForDeleted(ContactData contact){
    selectGroupForViewContact(contact);
    selectContactById(contact.getId());                                                                                 // выбираем контакт
    deleteContactFromGroup();                                                                       // нажимаем удалить
    returnToContactPage();
  }

  public void selectGroupForViewContact(ContactData contact) {
    click(By.name("group"));
    new Select(wd.findElement(By.name("group"))).selectByVisibleText(contact.getGroups().iterator().next().getName());  // переходим в группу
  }

  public void deleteContactFromGroup(){
    click(By.name("remove"));
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
    String home2 = wd.findElement(By.name("phone2")).getAttribute("value");
    String mobile = wd.findElement(By.name("mobile")).getAttribute("value");
    String work = wd.findElement(By.name("work")).getAttribute("value");
    String email1 = wd.findElement(By.name("email")).getAttribute("value");
    String email2 = wd.findElement(By.name("email2")).getAttribute("value");
    String email3 = wd.findElement(By.name("email3")).getAttribute("value");
    wd.navigate().back();
    return new ContactData().withId(contact.getId()).withName(firstname).withSurname(lastname)
            .withAddress(address)
            .withHomeTel(home).withHomeTel2(home2).withMobileTel(mobile).withWorkTel(work)
            .withEmail1(email1).withEmail2(email2).withEmail3(email3);
  }

  public void contactInGroup(ContactData contact, GroupData group) {
    selectContactById(contact.getId());
    selectGroupForAddContact(group.getName());
    addInGroup();
  }

  private void selectGroupForAddContact(String groupName) {
    new Select(wd.findElement(By.name("to_group")))
            .selectByVisibleText(String.valueOf(groupName));
  }

  private void selectGroupForContact(ContactData contact,GroupData group) {
    if(contact.getGroups().size() > 0)
      Assert.assertTrue(contact.getGroups().size() == 1);
    new Select(wd.findElement(By.name("to_group")))
            .selectByVisibleText(group.getName());
  }
}
