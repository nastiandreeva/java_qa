package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() throws Exception {
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().getLinkContactModification();
    app.getContactHelper().goToPageContactModification();
    app.getContactHelper().fillNewContactForm(new ContactData("Nasti", "Andreeva", "Олеговна", "НастяКуа", "ООО \"Рога и копыта\"", "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToContactPage();
  }
}
