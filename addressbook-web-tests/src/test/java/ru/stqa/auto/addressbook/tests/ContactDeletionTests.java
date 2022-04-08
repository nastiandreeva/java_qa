package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(new ContactData("Александр", "Дмитриевич", "Николаев", "Стом", "ООО \"СК\"",
              "город Омск", "77-44-33", "2-35-12", "neil@bk.ru", "5", "May", "1995"));
    }
    List<ContactData> before = app.getContactHelper().getContactList(); //предусловия "получить список контактов"
    app.getContactHelper().selectContact();
    app.getContactHelper().submitContactDelete();
    app.getContactHelper().acceptDelete();
    app.getNavigationHelper().goToHomePage();
    List<ContactData> after = app.getContactHelper().getContactList(); //постусловия "получить список контактов" для сравнение со списком из предусловия
    Assert.assertEquals(after.size(), before.size() - 1 );

    before.remove(before.size() - 1);
    Assert.assertEquals(before, after);
  }
}