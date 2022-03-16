package ru.stqa.auto.addressbook;

import org.testng.annotations.Test;

public class ContactTests extends TestBase{

  @Test
  public void testAddNew() throws Exception {
    goToNewContactPage();
    fillNewContactForm(new ContactData("Анастасия", "Николаева", "Олеговна", "НастяКуа", "ООО \"Рога и копыта\"", "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992"));
    submitNewContactCreation();
    returnToContactPage();
    logout();
  }

}
