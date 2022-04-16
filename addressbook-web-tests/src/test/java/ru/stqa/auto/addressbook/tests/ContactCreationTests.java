package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;
import ru.stqa.auto.addressbook.model.Contacts;

import java.io.File;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testAddNewContact() throws Exception {
    Contacts before = app.contact().all();
    app.goTo().goToNewContactPage();
    File photo = new File("src/test/java/resources/contacts/photo.jpeg");
    ContactData contact = new ContactData().withName("Анастасия").withSurname("Николаева")
            .withPhoto(photo).withAddress("город Новый Уренгой")
            .withWorkTel("55-55-33").withMobileTel("+7(908)3180707").withHomeTel("2-35-12").withHomeTel2("8-55-55")
            .withEmail1("nasti1@gmail.com").withEmail2("nasti2@gmail.com").withEmail3("nasti3@gmail.com");
    app.contact().create(contact);
    assertThat(app.contact().count(), equalTo(before.size() + 1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(before.withAdded(contact.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }

  @Test
  public void testCurrentDir() {
    File currentDir = new File(".");
    System.out.println(currentDir.getAbsolutePath());
    File photo = new File("src/test/resources/contacts/.photo.jpg");
    System.out.println(photo.getAbsolutePath());
    System.out.println(photo.exists());                               // проверка что файл существует
    }

}
