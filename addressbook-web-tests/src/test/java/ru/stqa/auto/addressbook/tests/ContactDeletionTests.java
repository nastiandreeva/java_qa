package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.Test;

public class ContactDeletionTests extends TestBase{

  @Test
  public void testContactDeletion() throws Exception {
    app.getNavigationHelper().goToHomePage();
    app.getContactHelper().selectContact();
    app.getContactHelper().submitContactDelete();
    app.getContactHelper().acceptDelete();
    app.getNavigationHelper().goToHomePage();
  }
}
