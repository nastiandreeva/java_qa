package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    app.getNavigationHelper().goToNewContactPage();
    app.getContactHelper().createContact(new ContactData("Анастасия", "Николаева", "Олеговна", "НастяКуа", "ООО \"Рога и копыта\"",
            "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992"));
  }

}
