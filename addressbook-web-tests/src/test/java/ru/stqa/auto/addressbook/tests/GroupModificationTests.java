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
    if (app.db().groups().size() == 0) {               // получение списка групп с бд
      app.goTo().groupPage();
      app.group().create(new GroupData().withName("test1"));
    }

//    if (app.group().all().size() == 0)  {            // получение списка групп с интерфейса
//      app.goTo().groupPage();
//      app.group().create(new GroupData().withName("test1"));
//    }
  }

  /* кейсы по модификации групп */
  @Test
  public void testGroupModification() {
    Groups before = app.db().groups();                  // заменяется в зависимости от получения списков через бд или через интерфейс (app.group().all())
    GroupData modifiedGroup = before.iterator().next(); // последовательно перебираем итератор который вернет первый попавшийся элемент
    GroupData group = new GroupData().withId(modifiedGroup.getId()).withName("test1modif").withHeader("test2mod").withFooter("test3mod"); //получение айди модифицированной группы
    app.goTo().groupPage();
    app.group().modify(group);
    assertEquals(app.group().count(), before.size());   // проверка хэша списка групп с интерфейса, остается для доп. контроля, но при получении списков из бд не очень нужно
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));

    verifyGroupListInUi();                              // отключаемый в Configurations метод для сравнения списков групп из бд и с ui
  }

}
