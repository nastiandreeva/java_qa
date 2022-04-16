package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.ContactData;


import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPropertiesTests extends TestBase{

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().goToHomePage();
    if (! app.contact().isThereAContact()) {
      app.goTo().goToNewContactPage();
      app.contact().create(new ContactData().withName("Анастасия").withSurname("Николаева")
              .withAddress("город Омск, Космический проспект дом 83, кв. 20")
              .withWorkTel("55-55-33").withMobileTel("+7(908)3180707").withHomeTel("2-35-12").withHomeTel2("8-55-55")
              .withEmail1("nasti1@gmail.com").withEmail2("nasti2@gmail.com").withEmail3("nasti3@gmail.com"));;
    }
  }

  @Test
  public void testContactProperties() {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactInfoFromEditForm = app.contact().infoFromEditForm(contact);

    assertThat(contact.getAddress(), equalTo(contactInfoFromEditForm.getAddress()));    // проверка адреса
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactInfoFromEditForm)));  // проверка эл. почты
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactInfoFromEditForm)));  // проверка телефонов
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail1(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s) -> ! s.equals(""))
            .collect(Collectors.joining("\n"));                                 // собираем в коллекцию, через сбор значений в поток
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomeTel(), contact.getMobileTel(), contact.getWorkTel(), contact.getHomeTel2())
            .stream().filter((s) -> ! s.equals(""))
            .map(ContactPropertiesTests::cleanedDelimiter)
            .collect(Collectors.joining("\n"));
  }

  public static String cleanedDelimiter(String phone){
    return phone.replaceAll("\\s","").replaceAll("[-()]", "");
  }
}
