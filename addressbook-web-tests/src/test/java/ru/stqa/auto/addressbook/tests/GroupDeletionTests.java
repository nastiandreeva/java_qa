package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupDeletionTests extends TestBase {

  /* предусловие перед выполнением кейса по созданию группы, что бы можно было использовать это для всех тестов по модификации */
  @BeforeMethod
  public void ensurePreconditions() {
    if (app.db().groups().size() == 0) {               // получение списка групп с бд
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }
  }

  /* кейсы по удалению групп */
  @Test
  public void testGroupDeletion(){
    Groups before = app.db().groups();
    GroupData deletedGroup = before.iterator().next(); // последовательно перебираем итератор который вернет первый попавшийся элемент
    app.goTo().groupPage();
    app.group().delete(deletedGroup);
    assertEquals(app.group().count(), before.size() - 1);
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(deletedGroup)));

    verifyGroupListInUi();
  }

}
