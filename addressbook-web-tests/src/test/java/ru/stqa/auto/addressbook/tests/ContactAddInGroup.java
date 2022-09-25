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
    if (app.db().contacts().size() > 0) {                                       // 1. проверяем есть ли контакты и цикл по ним
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        System.out.println(contact.getGroups());
        if (contact.getGroups().size() == 0) {                                  // 2. проверяем количество групп у контакта
          if (app.db().groups().size() == 0) {                                  // 3. проверяем группы у контакта и если их нет, то создаем
            app.goTo().groupPage();
            app.group().create(new GroupData().withName(String.format("test%s", now)));
          }
          idContact = contact.getId();                                          // 4. запоминаем айди контакта для которого создали группу
        }  else if (contact.getGroups().size() != 0) {                          // 2. проверяем что количество групп у контакта больше 0 и хотим проверить есль ли группа в которой нет контакта
          Groups contactGroups = app.db().getGroupsFromContact(contact.getId());
          List<GroupData> allGroups = new ArrayList<GroupData>((Collection<? extends GroupData>) app.db().allGroups());
          allGroups.removeAll(contactGroups);
          if (allGroups.size() == 0) {                                          // 3. проверяем есть ли группа в которой нет контакта
            continue;
          } else if (allGroups.size() > 0) {
            idContact = contact.getId();                                        // 4. запоминаем айди контакта который не состоит в какой-то группе
          }
        }
          idContact = contact.getId();
          break;
      }
      if (idContact == 0) {                                                     // 2. проверяем нашелся ли контакт и записалось ли его айди, иначе нужно создать новую группу
        idContact = app.db().randomContact();                                   // 3. так как нет контакта без группы мы берем один айди любого контакта
        app.goTo().groupPage();
        app.group().create(new GroupData().withName(String.format("test%s", now)));
      }
    } else {                                                                    // 1. иначе если не находится контакт создаем новый контакт
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Александр").withSurname("Николаев")
              .withAddress("город Омск")
              .withWorkTel("55-55-33").withHomeTel("2-35-12").withEmail1("ortho@bk.ru"));
      List<ContactData> contacts = new ArrayList<ContactData>(app.db().contacts());
      for (ContactData contact : contacts) {
        idContact = contact.getId();
      }
      if (app.db().groups().size() == 0) {                                      // 2. смотрим есть ли группа в бд и если нет, то создаем
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
}
