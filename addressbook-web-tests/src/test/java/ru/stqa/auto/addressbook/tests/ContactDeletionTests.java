package ru.stqa.auto.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.AssertJUnit.assertEquals;

public class ContactDeletionTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().goToHomePage();
    if (! app.contact().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев").withPatronymic("Дмитриевич")
              .withNickname("Алекс").withCompany("СК Шустов").withAddress("город Омск")
              .withWorktel("55-55-33").withHometel("2-35-12").withEmail("ortho@bk.ru").withDatebirth("5")
              .withMonthbirth("May").withYearbirth("1995"));
    }
  }

  @Test
  public void testContactDeletion() {
    Contacts before = app.contact().all(); //предусловия "получить список контактов"
    ContactData deletedContact = before.iterator().next();
    app.contact().delete(deletedContact);
    Contacts after = app.contact().all(); //постусловия "получить список контактов" для сравнение со списком из предусловия
    assertEquals(after.size(), before.size() - 1);
    assertThat(after, equalTo(before.without(deletedContact)));
  }

}