package ru.stqa.auto.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class ContactInGroup extends TestBase {

  /* предусловие перед выполнением кейса по созданию контактов, что бы можно было использовать это для всех тестов по модификации */
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {                                                                          // проверяем что есть контакт что бы добавить его в группу
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
    }
    if (app.db().groups().size() == 0) {                                                                            // проверяем что есть группа для дальнейшего добавления контакта в нее
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }

  }

  @Test
  public void testAddContactToGroup() {
    Contacts before = app.db().contacts();                                                                          // предусловия "получить список контактов"
    ContactData addContactToGroup = before.iterator().next();
    app.goTo().goToHomePage();
    app.contact().selectGroupForAdd(addContactToGroup);
    assertEquals(app.contact().count(), before.size());
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.without(addContactToGroup).withAdded(addContactToGroup)));

    verifyContactListInUi();
  }

  @Test
  public void testDeleteContactFromToGroup() {
    ContactData contact = app.db().contacts().iterator().next();
    int idContact = contact.getId();
    if (contact.getGroups().size() == 0) {                                                                          // если контакта нет в группе, то добавляем ему группу
      ContactData addContactToGroup = app.db().contacts().iterator().next();
      app.goTo().goToHomePage();                                                                                    // сначала переходим на главную
      app.contact().selectGroupForAdd(addContactToGroup);                                                           // добавляем контакт в группу
    }
    ContactData contactInGroup = app.db().contactsInGroup(idContact);
    Groups groupDelete = contactInGroup.getGroups();
    app.contact().viewContactInGroup(contactInGroup);
//    assertThat(app.db().contactsInGroup(contact.getId()).getGroups().contains(groupDelete), equalTo(false)); // проверяем что у контакта нет группы

    verifyContactListInUi();
  }
}
