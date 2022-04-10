package ru.stqa.auto.addressbook.tests;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.util.Set;
import java.util.regex.Matcher;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all(); //объявление переменной типа лист
    GroupData group =  new GroupData().withName("test2");
    app.group().create(group);
    Groups after = app.group().all();
    assertThat(after.size(), equalTo(before.size() + 1 ));
// 4 Set способ сравнения. Поток идентификации что бы сравнение сразу шло по айди преобразованному в число. в качестве параметра применяется группа а выдается как идентификатор.
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt()))));
  }


//    1 List способ сравнения с помощью лямбда выражения их способа 2, превращаем список в поток и ищем максимальный
//    group.setId(after.stream().max((o1, o2) -> Integer.compare(o1.getId(), o2.getId())).get().getId());
//    before.add(group);
//    Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
//    before.sort(byId);
//    after.sort(byId);
//    Assert.assertEquals(before, after);  //сортированное множество, все одинаковые имена схлопываются
//  }

//    2 List способ сравнения с помощью вспомогательного объекта компоратора
//    Comparator<? super GroupData> byId = new Comparator<GroupData>() { //локальная переменная "сравниватель"
//      @Override
//      public int compare(GroupData o1, GroupData o2) {
//        return Integer.compare(o1.getId(), o2.getId());
//      }
//    };

//    3 List способ сравнения с использованием цикла
//    int max = 0;
//    for (GroupData g: after){
//      if (g.getId() > max){
//        max = g.getId();
//      }
//    }
}
