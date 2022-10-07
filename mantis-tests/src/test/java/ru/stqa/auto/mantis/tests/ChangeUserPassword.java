package ru.stqa.auto.mantis.tests;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.lanwen.verbalregex.VerbalExpression;
import ru.stqa.auto.mantis.model.MailMessage;
import ru.stqa.auto.mantis.model.UserData;
import ru.stqa.auto.mantis.model.Users;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class ChangeUserPassword extends TestBase {

  @BeforeMethod
  public void startMailServer() {
    app.mail().start();
  }

  @Test
  public void testChangePassword() throws IOException, MessagingException, InterruptedException {
    long now = System.currentTimeMillis();                                                              // для уникальности timestamp
    String userName= String.format("user%s", now);
    String password1 = "password";
    String password2 = "newpassword";
    String email = String.format("mail%s@localhost.localdomain", now);

//    List<MailMessage> mailMessages1 = app.mail().waitForMail(2, 1000);
//    String confirmationLink1 = findConfirmationLink(mailMessages1, email);
//    app.changePassword().finish(confirmationLink1, password1);

    app.changePassword().login(app.getProperty("web.adminLogin"), app.getProperty("web.adminPassword"));// администратор входит в систему под логином и паролем из конфига
    Thread.sleep(1000);

    Users users = app.db().getUsersWithoutAdmin();                                                      // получаем данные из бд и смотрим есть ли юзеры кроме админа
    if (users.size() > 0) {
      UserData user = users.iterator().next();
      userName = user.getName();
      email = user.getEmail();
    } else if (users.size() == 0) {
      app.changePassword().start(userName, email);                                                      // регистрируем пользователя у которого будем сбрасывать пароль
    }

    app.changePassword().goToManageUsers(userName);                                                     // переход на страницу управления пользователями и сброс пароля
    Thread.sleep(1000);
    List<MailMessage> mailMessages2 = app.mail().waitForMail(2, 1000);                     // переходим в почту пользователя
    String confirmationLink2 = findConfirmationLink2(mailMessages2, email);                             // переходим по ссылке из письма
    app.changePassword().finish(confirmationLink2, password2);
    assertTrue(app.newSession().login(userName, password2));
  }

  private String findConfirmationLink(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.stream().filter((m) -> m.to.equals(email)).findFirst().get();  // ищем среди всех писем письмо отправленное именно на нужный эмейл
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();     // ищем текст ссылки
    return regex.getText(mailMessage.text);
  }

  private String findConfirmationLink2(List<MailMessage> mailMessages, String email) {
    MailMessage mailMessage = mailMessages.get(2);                                                        // берем письмо по индексу на изменение пароля, TODO лучше бы надо брать по тексту "Your password has been reset. Please visit the following URL to set a new one:"
    VerbalExpression regex = VerbalExpression.regex().find("http://").nonSpace().oneOrMore().build();     // ищем текст ссылки
    return regex.getText(mailMessage.text);
  }

  @AfterMethod(alwaysRun = true)
  public void stopMailServer() {
    app.mail().stop();
  }
}
