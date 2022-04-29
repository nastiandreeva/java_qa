package ru.stqa.auto.mantis.appmanager;

import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.openqa.selenium.remote.http.HttpClient;

import javax.swing.text.html.parser.Entity;
import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HttpSession {
  private CloseableHttpClient httpclient;
  private ApplicationManager app;

  public HttpSession (ApplicationManager app){
    this.app = app;
    /* создается новый клиент/новая сессия для работы по протоколу
    setRedirectStrategy - перенаправление, если не указать, то убдет ошибка 302 */
    httpclient = HttpClients.custom().setRedirectStrategy(new LaxRedirectStrategy()).build();
  }

  public boolean login(String username, String password) throws IOException {           // метод для выполнения логина
    HttpPost post = new HttpPost(app.getProperty("web.BaseUrl") + "/login.php");    // адрес
    List<NameValuePair> params = new ArrayList<>();
    params.add( new BasicNameValuePair("username", username));                    // формируются параметры для тела
    params.add( new BasicNameValuePair("password", password));
    params.add( new BasicNameValuePair("secure_session", "on"));
    params.add( new BasicNameValuePair("return", "index.php"));
    post.setEntity( new UrlEncodedFormEntity(params));                                  // параметры упаковываются и помещаются в запрос
    CloseableHttpResponse response = httpclient.execute(post);                          // отправка, запрос выполняется
    String body = geTextForm(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));  // проверка что пользователь вошел и написано его имя
  }

  private String geTextForm(CloseableHttpResponse response) throws IOException  {
    try {
      return EntityUtils.toString(response.getEntity());
    } finally {
      response.close();
    }
  }

  public boolean isLoggedInAs(String username) throws IOException {                   // метод для определения какой пользователь сейчас вошел в систему
    HttpGet get = new HttpGet(app.getProperty("web.BaseUrl") + "/index.php");
    CloseableHttpResponse response = httpclient.execute(get);
    String body = geTextForm(response);
    return body.contains(String.format("<span class=\"italic\">%s</span>", username));
  }

}
