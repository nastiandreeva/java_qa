package ru.stqa.auto.addressbook.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;
import ru.stqa.auto.addressbook.tests.TestBase;

import java.util.Collection;
import java.util.List;

public class DbHelper extends TestBase {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    // A SessionFactory is set up once for an application!
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure() // configures settings from hibernate.cfg.xml
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Groups groups() {                                                              // получаем список групп через досутп к бд, а не через чтение с интерфейса
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();    // запрос к объекту, вместо запроса sql
    session.getTransaction().commit();
    session.close();
    return new Groups(result);
  }

  public Contacts contacts() {                                                          // получаем список контактов через досутп к бд, а не через чтение с интерфейса
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'" ).list();   // запрос к объекту, вместо запроса sql
    session.getTransaction().commit();
    session.close();
    return new Contacts(result);
  }

  public ContactData getContactWithoutGroup(int id) {                                   // получаем один контакт по айди
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery( "from ContactData where id = :id" );
    result.setParameter("id", id);
    ContactData contact = (ContactData) result.getSingleResult();
    session.close();
    return contact;
  }

  public GroupData newGroupContact(int id)  {                                           // получаем список групп через досутп к бд, а не через чтение с интерфейса
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery( "from GroupData where id = :id" );
    result.setParameter("id", id);
    GroupData group = (GroupData ) result.getSingleResult();
    session.close();
    return group;
  }

  public Groups getGroupsFromContact(int id) {                                          // получаем список групп в которых есть контакт
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery( "from ContactData where id = :id" );
    result.setParameter("id", id);
    ContactData contact = (ContactData) result.getSingleResult();
    session.close();
    return contact.getGroups();
  }

  public int contactsWithoutGroup() {                                               // получаем список контактов у которых количество групп меньше чем есть групп в бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery( "from ContactData where deprecated = '0000-00-00'" );   // запрос к объекту, вместо запроса sql
    ContactData contact = (ContactData) result.getSingleResult();
    session.close();
    return contact.getId();
  }

//  public Collection<? extends Object> getGroupsFromContact(int idContact) {                          // получаем список групп в которых есть контакт
//    Session session = sessionFactory.openSession();
//    session.beginTransaction();
//    List<ContactData> result = session.createQuery( "from ContactData where deprecated = '0000-00-00'" ).list();
//    session.getTransaction().commit();
//    session.close();
//    for ( ContactData contact : result ) {
//      System.out.println(contact);
//      System.out.println(contact.getGroups());
//      return contact.getGroups();
//    }
//    return result;
//  }

  public Collection<? extends Object> allGroups() {                                     // получаем список групп через досутп к бд, а не через чтение с интерфейса
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<GroupData> result = session.createQuery("from GroupData").list();
    session.getTransaction().commit();
    session.close();
    return result;
  }

  public ContactData getContactInGroup(int id) {                                        // получаем контакт добавленный в группу, для того что бы проверить у него в списке групп вхождение новой
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    Query result = session.createQuery( "from ContactData where id = :id" );
    result.setParameter("id", id);
    ContactData contact = (ContactData) result.getSingleResult();
//    ContactData result = (ContactData) session.createQuery( "from ContactData where deprecated = '0000-00-00'" ).list().get(0);
//    session.getTransaction().commit();
    session.close();
    return contact;
  }






  public GroupData idGroupWithoutContact(int id) {                                      // получаем список групп в которых нет передаваемого айди контакта
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.getTransaction().commit();
    Query result = session.createQuery( "from GroupData where group_name = :group_name" );
    result.setParameter("id", id);
    GroupData group = (GroupData) result.getSingleResult();
    session.getTransaction().commit();
    session.close();
    return group;

//    result.setParameter("group_name", group_name);
//    List<GroupData> groupId = result.getResultList();
//    session.close();
//    return groupId.get(0).getId();
  }

  public ContactData contactsInGroup(int id) {                                         // получаем список контактов в группах через досутп к бд
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    session.getTransaction().commit();
    Query result = session.createQuery( "from ContactData where id = :id" );
    result.setParameter("id", id);
    ContactData contact = (ContactData) result.getSingleResult();
    session.close();
    return contact;
  }

}
