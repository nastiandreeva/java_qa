package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    app.goTo().goToNewContactPage();
    Contacts before = app.contact().all(); //объявление переменной типа лист
    ContactData contact = new ContactData().withName("Анастасия").withSurname("Николаева").withPatronymic("Олеговна")
            .withNickname("НастяКуа").withCompany("ООО \"Рога и копыта\"").withAddress("город Новый Уренгой")
            .withWorktel("55-55-33").withHometel("2-35-12").withEmail("naastya@bk.ru").withDatebirth("19")
            .withMonthbirth("February").withYearbirth("1992");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1 ));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));

//    before.add(contact);
//    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    assertEquals(before, after);
  }

}
