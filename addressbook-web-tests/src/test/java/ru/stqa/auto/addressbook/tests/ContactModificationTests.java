package ru.stqa.auto.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import static org.junit.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().goToHomePage();
    if (! app.contact().isThereAContact()) {                                  //предусловия на то, если нет контакта что бы выделить для удаления, то создать
    app.goTo().goToNewContactPage();
    app.contact().create(new ContactData().withName("Анастасия").withSurname("Николаева")
            .withAddress("город Новый Уренгой")
            .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("naastya@bk.ru"));
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Contacts before = app.contact().all();                                    //предусловия "получить список контактов"
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withName("Ирина").withSurname("Миллер")
            .withAddress("город Хилок")
            .withWorkTel("55-22-44").withHomeTel("1-35-11").withEmail1("ir@bk.ru");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all(); //постусловия "получить список контактов" для сравнение со списком из предусловия
    MatcherAssert.assertThat(after, CoreMatchers.equalTo(before.without(modifiedContact).withAdded(contact)));

//    before.remove(before.size() - 1 );                                       // удаляем контакт
//    before.add(contact);
//    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));  //сортированное множество, все одинаковые имена схлопываются
  }

}
