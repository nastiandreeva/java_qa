package ru.stqa.auto.rest;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import org.apache.http.client.fluent.Executor;
import org.apache.http.client.fluent.Request;
import org.apache.http.message.BasicNameValuePair;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Set;

import static com.sun.javafx.runtime.async.BackgroundExecutor.getExecutor;
import static org.testng.AssertJUnit.assertEquals;

public class RestTests {

  @Test
  public void testCreateIssue() throws IOException {
    Set<Issue> oldIssues = getIssues();
    Issue newIssue = new Issue().withSubject("Test issue").withDescription("New test issue");
    int issueId = createIssue(newIssue);
    Set<Issue> newIssues = getIssues();
    oldIssues.add(newIssue.withId(issueId));
    assertEquals(newIssues, oldIssues);
  }

  private Set<Issue> getIssues() throws IOException {                                             // получение всех баг репортов
    String json = getExecutor().execute(Request.Get("https://bugify.stqa.ru/api/issues.json"))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);                                            // парсим json в переменную
    JsonElement issues = parsed.getAsJsonObject().get("issues");                                  // получаем список только issues
    return new Gson().fromJson(issues, new TypeToken<Set<Issue>>() {}.getType());                 // преобразовываем в множество объектов типа Issue
  }

  private Executor getExecutor() {                                                                // авторизация под токеном
    return Executor.newInstance().auth("288f44776e7bec4bf44fdfeb1e646490", "");
  }

  private int createIssue(Issue newIssue) throws IOException {                                    // создание баг репорта с параметрами subject и description
    String json = getExecutor().execute(Request.Post("https://bugify.stqa.ru/api/issues.json")
            .bodyForm(new BasicNameValuePair("subject", newIssue.getSubject()),
                    new BasicNameValuePair("description", newIssue.getDescription())))
            .returnContent().asString();
    JsonElement parsed = JsonParser.parseString(json);                                             // анализируем строку
    return parsed.getAsJsonObject().get("issue_id").getAsInt();                                    // берем айди
  }

}
