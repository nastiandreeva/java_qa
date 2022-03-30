package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

public class ContactModificationTests extends TestBase{

  @Test
  public void testContactModification() throws Exception {
    app.getNavigationHelper().goToHomePage();
    if (! app.getContactHelper().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
      app.getNavigationHelper().goToNewContactPage();
      app.getContactHelper().createContact(new ContactData("Александр", "Николаев", "Дмитриевич", "Стом", "ООО \"СК\"",
              "город Омск", "77-44-33", "2-35-12", "neil@bk.ru", "5", "May", "1995"));
    }
    app.getContactHelper().selectContact();
    app.getContactHelper().initContactModification();
    app.getContactHelper().goToPageContactModification();
    app.getContactHelper().fillNewContactForm(new ContactData("Ирина", "Миллер", "Павловна", "Ortho", "ООО \"SH\"",
            "город НУр", "77-66-33", "2-35-05", "ao@bk.ru", "19", "February", "1995"));
    app.getContactHelper().submitContactModification();
    app.getContactHelper().returnToContactPage();
  }
}
