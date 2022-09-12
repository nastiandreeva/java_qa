package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeleteFromGroup extends TestBase {

  int idContact;

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() == 0) {
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
    }

    if (app.db().groups().size() == 0) {                                                    // смотрим есть ли группа в бд и если нет, то создаем
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  @Test
  public void testDeleteContactFromToGroup() {
    String nameGroup = null;
    List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
    for (ContactData contact : contacts) {
      if (contact.getGroups().size() == 0) {
        app.goTo().goToHomePage();                                                          // сначала переходим на главную
        app.contact().selectContactById(contact.getId());
        List<GroupData> groups = new ArrayList<>(app.db().groups());
        for (GroupData group : groups) {
          nameGroup = group.getName();
        }
        app.contact().setGroupForContact(nameGroup);
        app.contact().addInGroup();
      }
      idContact = contact.getId();
    }
    ContactData contactInGroup = app.db().getContactInGroup(idContact);
    Groups groupDelete = contactInGroup.getGroups();
    app.contact().returnToContactPage();
    app.contact().selectContactForDeleted(contactInGroup);
    assertThat(app.db().getContactInGroup(contactInGroup.getId()).getGroups().contains(groupDelete), equalTo(false)); // проверяем что у контакта нет группы
    verifyContactListInUi();
  }
}

