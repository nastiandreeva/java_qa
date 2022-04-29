package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddInGroup extends TestBase {

  /* предусловие перед выполнением кейса
   * 1. проверяем есть ли контакт, если нет то создаем новый контакт
   * 2. если контакт есть, проверяем есть ли у него группа
   * 3. если у контакта группы нет, то просто берем его айди
   * 4. если у контакта группа есть, то создаем новую группу и берем айди контакта  */
  int idContact;

  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().contacts().size() > 0) {
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        System.out.println(contact.getGroups());
        if (contact.getGroups().size() == 0) {                      // берем список контактов и смотрим есть ли контакт у которого нет группы
          if (app.db().groups().size() == 0) {                      // смотрим есть ли группа в бд и если нет, то создаем
            app.goTo().groupPage();
            app.group().create(new GroupData().withName("test1"));
          }
          idContact = contact.getId();
        } else if (contact.getGroups().size() == app.db().groups().size()) {                                                    // если у контакта уже есть группа, то нужно создать новую
          app.goTo().groupPage();
          app.group().create(new GroupData().withName("test2"));
        }
        idContact = contact.getId();
      }
    } else {                                                        // иначе если не находится контакт без группы создаем новый контакт
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        idContact = contact.getId();
      }
      if (app.db().groups().size() == 0) {                          // смотрим есть ли группа в бд и если нет, то создаем
        app.goTo().groupPage();
        app.group().create(new GroupData().withName("test1"));
      }
    }

  }

//  public void ensurePreconditions() {
//    if (app.db().contacts().size() > 0) {
//      List<ContactData> contacts = app.db().contacts().stream().collect(Collectors.toList());
//      for (ContactData contact : contacts) {
//        System.out.println(contact.getGroups());
//        if (contact.getGroups().size() == 0 && app.db().groups().size() > 0) {
//          List<GroupData> groups = app.db().groups().stream().collect(Collectors.toList());
//          for (GroupData group : groups) {
//            idGroup = group.getId();
//            System.out.println("у контакта группы нет, но в бд есть группа с айди " + idGroup);
//          }
//        } else if (contact.getGroups().size() > 0) {
//          app.goTo().groupPage();
//          app.group().create(new GroupData().withName("test1"));
//          System.out.println("у контакта группа есть, поэтому создается новая группа с айди " );
//        }
//        idContact = contact.getId();
//      }
//    } else if (app.db().contacts().size() == 0) {                                                                          // проверяем что есть контакт что бы добавить его в группу
//      app.goTo().goToNewContactPage();
//      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
//              .withAddress("город Омск")
//              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
//      if (app.db().groups().size() == 0) {
//        app.goTo().groupPage();
//        app.group().create(new GroupData().withName("test1"));
//      }
//    }
//  }

  @Test
  public void testAddContactToGroup() {
    /* вариант 1. почему-то не получается добавлять в другую группу */
//    ContactData contact = app.db().contacts().iterator().next();
//    GroupData group = app.db().groups().iterator().next();
//    app.goTo().goToHomePage();
//    app.contact().addContactInGroup(contact, group);
//    assertThat(new ArrayList<>(app.db().contacts()).contains(group), equalTo(false));
//    verifyContactListInUi();

    /* вариант 2. работает но выглядит кейс не хорошо */
    String nameGroup = null;
    app.goTo().goToHomePage();
    app.contact().selectContactById(idContact);
    int idGroup = app.db().idGroupWithoutContact(idContact);                                                        // получаем айди группы в которой контакт не состоит
    List<GroupData> groups = new ArrayList<>(app.db().groups());
    for (GroupData group : groups) {
      nameGroup = group.getName();
    }
    app.contact().setGroupForContact(nameGroup);
    app.contact().addInGroup();
    assertThat(new ArrayList<>(app.db().contacts()).contains(idGroup), equalTo(false)); // при ыборке из бд групп, в которых не состоит наш контакт нет той, в которую он был добавлен
    verifyContactListInUi();
  }
}
