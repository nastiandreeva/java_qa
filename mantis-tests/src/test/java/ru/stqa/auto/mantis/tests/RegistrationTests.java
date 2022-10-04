package ru.stqa.auto.mantis.tests;

import com.sun.jmx.snmp.Timestamp;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.auto.mantis.model.MailMessage;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class RegistrationTests extends TestBase {

  @BeforeMethod
  public void startMailServer() {
//    app.mail().start();
  }

  @Test
  public void testRegistration() throws IOException, MessagingException {
    long now = System.currentTimeMillis();                                                              // для уникальности timestamp
    String user = String.format("user%s", now);
    String password = "password";
    String email = String.format("user%s@localhost.localdomain", now);
    app.james().createUser(user, password);
    app.registration().start(user, email);
//    List<MailMessage> mailMessages = app.mail().waitForMail(2, 1000);
    List<MailMessage> mailMessages = app.james().waitForMail(user, password, 6000);               // нужно запустить run.bat для внешнего сервера
    String confirmationLink = findConfirmationLink(mailMessages, email);
    app.registration().finish(confirmationLink, password);
    assertTrue(app.newSession().login(user, password));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();  // ищем среди всех писем письмо отправленное именно на нужный эмейл
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();     // ищем текст ссылки
    return regex.getText(mailMessage.text);
  }

  @AfterMethod (alwaysRun = true)
  public void stopMailServer() {
//    app.mail().stop();
  }
}
