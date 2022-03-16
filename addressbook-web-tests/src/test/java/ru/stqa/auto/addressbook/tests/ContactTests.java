package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactTests extends TestBase {

  @Test
  public void testAddNew() throws Exception {
    app.goToNewContactPage();
    app.fillNewContactForm(new ContactData("Анастасия", "Николаева", "Олеговна", "НастяКуа", "ООО \"Рога и копыта\"", "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992"));
    app.submitNewContactCreation();
    app.returnToContactPage();
    app.logout();
  }

}
