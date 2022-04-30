package ru.stqa.auto.mantis.tests;

public class RegistrationTests extends TestBase {

  public void testRegistration() {
    app.registration().start("nastiandreeva", "na19@localhost.localdomain");
  }

}
