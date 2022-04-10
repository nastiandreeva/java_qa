package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GroupHelper extends HelperBase{

  public GroupHelper(WebDriver wd) {
    super(wd);
  }

  public void returnToGroupPage() {
    click(By.linkText("group page"));
  }

  public void submitGroupCreation() {
    click(By.name("submit"));
  }

  public void fillGroupForm(GroupData groupData) {
    type("group_name", groupData.getName());
    type("group_header", groupData.getHeader());
    type("group_footer", groupData.getFooter());
  }

  public void initGroupCreation() {
    click(By.name("new"));
  }

  public void deleteSelectedGroups() {
    click(By.name("delete"));
  }

  public void selectGroup(int index) {
    wd.findElements(By.name("selected[]")).get(index).click();
  }

  public void selectGroupById(int id) {
    wd.findElement(By.cssSelector("input[value='" + id + "'")).click();
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void create(GroupData group) { //метод для создания группы, нужен для предусловий при выполнении кейсов по удалению/изменению групп
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public void modify(GroupData group) {
    selectGroupById(group.getId());
    initGroupModification();
    fillGroupForm(group);
    submitGroupModification();
    returnToGroupPage();
  }

  public void delete(int index) {
    selectGroup(index);
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public void delete(GroupData group) {
    selectGroupById(group.getId());
    deleteSelectedGroups();
    returnToGroupPage();
  }

  public boolean isThereAGroup() { //проверка наличия элемента для дальнейшего изменения/удаления
    return isElementPresent(By.name("selected[]"));
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }

  /* метод который возвращает список */
  public List<GroupData> list() {
    List<GroupData> groups = new ArrayList<GroupData>();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));                          //получаем список элементов в лист elements
    for (WebElement element : elements) {                                                               //проходимся по списку elements
      String nameGroup = element.getText();                                                             //получаем текст элемента списка в переменную
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value")); //получаем значение из тегов на странице
      GroupData group = new GroupData().withId(id).withName(nameGroup);                         //создаем объект типа GroupData
      groups.add(group);                                                                               //добавляем созданный объект в список

    }
    return groups;
  }

  /* метод который возвращает готовое множество */
  public Groups all() {
    Groups groups = new Groups();
    List<WebElement> elements = wd.findElements(By.cssSelector("span.group"));                          //получаем список элементов в лист elements
    for (WebElement element : elements) {                                                               //проходимся по списку elements
      String nameGroup = element.getText();                                                             //получаем текст элемента списка в переменную
      int id = Integer.parseInt(element.findElement(By.tagName("input")).getAttribute("value"));  //получаем значение из тегов на странице
      groups.add(new GroupData().withId(id).withName(nameGroup));                                       //добавляем созданный объект в список

    }
    return groups;
  }
}
