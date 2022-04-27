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

import java.util.Arrays;
import java.util.List;

public class DbHelper {

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

  public ContactData contactsInGroup(int id) {                                             // получаем список контактов в группах через досутп к бд
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