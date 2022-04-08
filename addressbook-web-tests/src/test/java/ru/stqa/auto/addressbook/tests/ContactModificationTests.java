package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() throws Exception {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(new ContactData("Александр", "Николаев", "Дмитриевич", "Стом", "ООО \"СК\"",
              "город Омск", "77-44-33", "2-35-12", "neil@bk.ru", "5", "May", "1995"));
    }
    List<ContactData> before = app.getContactHelper().getContactList(); //предусловия "получить список контактов"
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().goToPageContactModification();
    ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"Ирина", "Павловна", "Миллер", "Ortho", "ООО \"SH\"",
            "город НУр", "77-66-33", "2-35-05", "ao@bk.ru", "19", "February", "1995");
    app.getContactHelper().fillNewContactForm(contact);
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToContactPage();
    List<ContactData> after = app.getContactHelper().getContactList(); //постусловия "получить список контактов" для сравнение со списком из предусловия
    Assert.assertEquals(after.size(), before.size());

    before.remove(before.size() - 1 );                                        // удаляем контакт
    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));  //сортированное множество, все одинаковые имена схлопываются
  }
}
