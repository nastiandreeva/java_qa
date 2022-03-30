package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(new ContactData("Александр", "Николаев", "Дмитриевич", "Стом", "ООО \"СК\"",
              "город Омск", "77-44-33", "2-35-12", "neil@bk.ru", "5", "May", "1995"));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().submitContactDelete();
    app.getContactHelper().acceptDelete();
    app.getNavigationHelper().goToHomePage();
  }
}
