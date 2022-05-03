package ru.stqa.auto.mantis.tests;

import org.testng.annotations.Test;

public class RegistrationTests extends TestBase {

  @Test
  public void testRegistration() {
    app.registration().start("nastiandreeva", "na19@localhost.localdomain");
  }

}
