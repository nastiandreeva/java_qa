package ru.stqa.auto.addressbook.appmanager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import ru.stqa.auto.addressbook.model.GroupData;

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

  public void selectGroup() {
    click(By.name("selected[]"));
  }

  public void initGroupModification() {
    click(By.name("edit"));
  }

  public void submitGroupModification() {
    click(By.name("update"));
  }

  public void createGroup(GroupData group) { //метод для создания группы, нужен для предусловий при выполнении кейсов по удалению/изменению групп
    initGroupCreation();
    fillGroupForm(group);
    submitGroupCreation();
    returnToGroupPage();
  }

  public boolean isThereAGroup() { //проверка наличия элемента для дальнейшего изменения/удаления
    return isElementPresent(By.name("selected[]"));
  }

  public int getGroupCount() {
    return wd.findElements(By.name("selected[]")).size();
  }
}
