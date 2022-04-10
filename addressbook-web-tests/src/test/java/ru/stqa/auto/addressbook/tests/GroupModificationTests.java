package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.testng.Assert.assertEquals;

public class GroupModificationTests extends TestBase{

  /* предусловие перед выполнением кейса по созданию группы, что бы можно было использовать это для всех тестов по модификации */
  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() == 0)  {
      app.group().create(new GroupData().withName("test1"));
    }
  }

  /* кейсы по модификации групп */
  @Test
  public void testGroupModification() {
    Groups before = app.group().all();
    GroupData modifiedGroup = before.iterator().next(); // последовательно перебираем итератор который вернет пкрвый попавшийся элемент
    GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("test1modif").withHeader("test2mod").withFooter("test3mod"); //получение айди модифицированной группы
    app.group().modify(group);
    Groups after = app.group().all();
    assertEquals(after.size(), before.size());
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

  }

}
