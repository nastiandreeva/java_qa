package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactAddInGroup extends TestBase {

  /* предусловие перед выполнением кейса
   * 1. проверяем есть ли контакт, если нет то создаем новый контакт
   * 2. если контакт есть, проверяем есть ли у него группа
   * 3. если у контакта группы нет, то просто берем его айди
   * 4. если у контакта группа есть, то создаем новый контакт
   * 5. прверяем есть ли группа, если есть и контакт не в ней, то берес айди группы и добавляем в нее
   * 6. если группы нет, то создаем группу и берем ее айди  */
  int idContact;
  int idGroup;
  int idContactWithoutGroup;
  String nameGroup;

  @BeforeMethod
  public void ensurePreconditions() {
    long now = System.currentTimeMillis();
    if (app.db().contacts().size() > 0) {
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        System.out.println(contact.getGroups());
        if (contact.getGroups().size() == 0) {                                  // берем список контактов и смотрим есть ли контакт у которого нет группы
          if (app.db().groups().size() == 0) {                                  // смотрим есть ли группа в бд и если нет, то создаем
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(String.format("test%s", now)));
          }
          idContact = contact.getId();
        }  else if (contact.getGroups().size() == app.db().groups().size()) {   // если у контакта уже есть группа, то нужно создать новую
//          List<GroupData> allGroupsInDb = new ArrayList<GroupData>((Collection<? extends GroupData>) app.db().allGroups());
//          int idContactWithoutGroup = app.db().contactsWithoutGroup();
//          Groups contactGroups = app.db().getGroupsFromContact(idContactWithoutGroup);
//          if (contactGroups.size() < allGroupsInDb.size()) { // проверяем есть ли в бд контакт без группы
//            idContact = contact.getId();
//          }
          app.goTo().groupPage();
          app.group().create(new GroupData().withName(String.format("test%s", now)));
        }
          idContact = contact.getId();
          break;
      }
    } else {                                                                    // иначе если не находится контакт без группы создаем новый контакт
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        idContact = contact.getId();
      }
      if (app.db().groups().size() == 0) {                                      // смотрим есть ли группа в бд и если нет, то создаем
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(String.format("test%s", now)));
      }
    }

  }

  @Test
  public void testAddContactToGroup() {
    ContactData contact = app.db().getContactWithoutGroup(idContact);           // берем именно того контакта которого получили в предусловии по idContact
    if (contact.getGroups().size() < app.db().groups().size()) {                // если у контакта количествво групп меньше чем есть в бд, то есть есть группа в которой нашего контакта нет
      Groups contactGroups = app.db().getGroupsFromContact(idContact);
      List<GroupData> allGroups = new ArrayList<GroupData>((Collection<? extends GroupData>) app.db().allGroups());
      allGroups.removeAll(contactGroups);
      System.out.println(allGroups.get(0).getId());
      nameGroup = allGroups.get(0).getName();
      idGroup = allGroups.get(0).getId();                                      //записываем в переменную айди группы которой нет у контакта из allGroups, подойдет и нулевой элемент по индексу
    }
    app.contact().returnToContactPage();
    GroupData newGroupFromContact = app.db().newGroupContact(idGroup);
    app.contact().contactInGroup(contact, newGroupFromContact);                 // передавать айди той группы в которой контакт не состоит
    assertThat(app.db().getContactInGroup(idContact).getGroups().contains(newGroupFromContact), equalTo(true));
    verifyContactListInUi();
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
//    } else if (app.db().contacts().size() == 0) {                               // проверяем что есть контакт что бы добавить его в группу
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


//    String nameGroup = null;
//    app.goTo().goToHomePage();
//    ContactData contact = app.db().contacts().iterator().next();
//    Groups contactGroups = contact.getGroups();                                               // берем грпуппы в которых есть контакт
//    List<GroupData> groups = new ArrayList<>(app.db().groups());                              // берем все группы
//    for (GroupData group : groups) {                                                          // идем по всем группам и запоминаем имя той группы которой нет у контакта
//      if (!contactGroups.contains(group)) {
//        nameGroup = group.getName();
//      }
//    }
//    app.contact().addContactInGroup(contact, nameGroup);                                      // основные шаги добавления в группу
//    Groups contactGroupsAfter = app.db().contactsInGroup(contact.getId()).getGroups();
//    assertThat(contactGroups.size() + 1, equalTo(contactGroupsAfter.size()));           // сравниваем что количесвто групп у контакта равно изначальному + 1
//    assertThat(app.db().contactsInGroup(contact.getId()).getGroups().contains(nameGroup), equalTo(true));   // сравниваем что список групп у контакта содержит ту группу в которую добавляли
//    verifyContactListInUi();
//  }
}
