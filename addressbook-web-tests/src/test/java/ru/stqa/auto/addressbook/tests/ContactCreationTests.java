package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    List<ContactData> before = app.getContactHelper().getContactList(); //объявление переменной типа лист
    app.getNavigationHelper().goToNewContactPage();
    ContactData contact = new ContactData("Анастасия",  "Николаева","Олеговна", "НастяКуа", "ООО \"Рога и копыта\"",
            "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992");
    app.getContactHelper().createContact(contact);
    List<ContactData> after = app.getContactHelper().getContactList();
    Assert.assertEquals(after.size(), before.size() + 1 );

    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }

}
