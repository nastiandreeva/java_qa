package ru.stqa.auto.addressbook.tests;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactEmailTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().goToHomePage();
    if (! app.contact().isThereAContact()) {
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Анастасия").withSurname("Николаева").withPatronymic("Олеговна")
              .withNickname("НастяКуа").withCompany("ООО \"Рога и копыта\"")
              .withAddress("город Омск, Космический проспект дом 83, кв. 20")
              .withWorkTel("55-55-33").withMobileTel("+7(908)3180707").withHometel("2-35-12")
              .withEmail1("nasti1@gmail.com").withEmail2("nasti2@gmail.com").withEmail3("nasti3@gmail.com")
              .withDatebirth("19").withMonthbirth("February").withYearbirth("1992"));
    }
  }

  @Test
  public void testContactEmail() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactEmailTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String email) {
    return email.replaceAll("\\s","").replaceAll("[-()]", "");
  }
}
