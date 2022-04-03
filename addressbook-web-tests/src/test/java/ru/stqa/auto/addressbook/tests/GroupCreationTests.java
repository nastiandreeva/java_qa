package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList(); //объявление переменной типа лист
    app.getGroupHelper().createGroup(new GroupData("test1", null, null));
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1 );
  }


}
