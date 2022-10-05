package ru.stqa.auto.mantis.appmanager;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.stqa.auto.mantis.model.UserData;
import ru.stqa.auto.mantis.model.Users;
import ru.stqa.auto.mantis.tests.TestBase;

import java.util.List;


public class DbHelper extends TestBase {

  private final SessionFactory sessionFactory;

  public DbHelper() {
    final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure()
            .build();
    sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
  }

  public Users getUsersWithoutAdmin() {
    Session session = sessionFactory.openSession();
    session.beginTransaction();
    List<UserData> result = session.createQuery("from UserData where id <> 1").list();
    session.getTransaction().commit();
    session.close();
    return new Users(result);
  }

}
