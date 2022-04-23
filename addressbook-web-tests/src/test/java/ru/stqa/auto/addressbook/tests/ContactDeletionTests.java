package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase{

  /* предусловие перед выполнением кейса по созданию контактов, что бы можно было использовать это для всех тестов по модификации */
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {             // получение списка контактов с бд
      app.goTo().goToHomePage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.db().contacts();            //предусловия "получить список контактов"
    ContactData deletedContact = before.iterator().next();
    app.goTo().goToHomePage();
    app.contact().delete(deletedContact);
    Assert.assertEquals(app.contact().count(), before.size() - 1);
    Contacts after = app.db().contacts();             //постусловия "получить список контактов" для сравнение со списком из предусловия
    assertThat(after, equalTo(before.without(deletedContact)));

    verifyContactListInUi();
  }

}