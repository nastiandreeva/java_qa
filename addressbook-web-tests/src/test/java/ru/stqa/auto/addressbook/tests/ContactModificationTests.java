package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ContactModificationTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().goToHomePage();
    if (! app.contact().isThereAContact()) { //предусловия на то, если нет контакта что бы выделить для удаления, то создать
    app.goTo().goToNewContactPage();
    app.contact().create(new ContactData().withName("Анастасия").withSurname("Николаева").withPatronymic("Олеговна")
            .withNickname("НастяКуа").withCompany("ООО \"Рога и копыта\"").withAddress("город Новый Уренгой")
            .withWorkTel("55-55-33").withHometel("2-35-12").withEmail1("naastya@bk.ru").withDatebirth("19")
            .withMonthbirth("February").withYearbirth("1992"));
    }
  }

  @Test
  public void testContactModification() throws Exception {
    Contacts before = app.contact().all(); //предусловия "получить список контактов"
    ContactData modifiedContact = before.iterator().next();
    ContactData contact = new ContactData().withId(modifiedContact.getId()).withName("Ирина").withSurname("Миллер")
            .withPatronymic("Павловна").withNickname("НастяКуа").withCompany("ООО Жэмс")
            .withAddress("город Хилок").withWorkTel("55-22-44").withHometel("1-35-11").withEmail1("ir@bk.ru")
            .withDatebirth("1").withMonthbirth("June").withYearbirth("1996");
    app.contact().modify(contact);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all(); //постусловия "получить список контактов" для сравнение со списком из предусловия
    assertThat(after, equalTo(before.without(modifiedContact).withAdded(contact)));

//    before.remove(before.size() - 1 );                                        // удаляем контакт
//    before.add(contact);
//    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    assertEquals(new HashSet<Object>(before), new HashSet<Object>(after));  //сортированное множество, все одинаковые имена схлопываются
  }

}
