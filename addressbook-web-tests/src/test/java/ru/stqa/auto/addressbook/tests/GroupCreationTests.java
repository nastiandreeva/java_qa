package ru.stqa.auto.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.getNavigationHelper().goToGroupPage();
    List<GroupData> before = app.getGroupHelper().getGroupList(); //объявление переменной типа лист
    GroupData group =  new GroupData("test2", null, null);
    app.getGroupHelper().createGroup(group);
    List<GroupData> after = app.getGroupHelper().getGroupList();
    Assert.assertEquals(after.size(), before.size() + 1 );

//    1 способ сравнения с помощью лясбда выражения их способа 2, превращаем список в поток и ищем максимальный
    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
    before.add(group);
    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);  //сортированное множество, все одинаковые имена схлопываются
  }

//    2 способ сравнения с помощью вспомогательного объекта компоратора
//    Comparator<? super GroupData> byId = new Comparator<GroupData>() { //локальная переменная "сравниватель"
//      @Override
//      public int compare(GroupData o1, GroupData o2) {
//        return Integer.compare(o1.getId(), o2.getId());
//      }
//    };

//    3 способ сравнения с использованием цикла
//    int max = 0;
//    for (GroupData g: after){
//      if (g.getId() > max){
//        max = g.getId();
//      }
//    }
}
