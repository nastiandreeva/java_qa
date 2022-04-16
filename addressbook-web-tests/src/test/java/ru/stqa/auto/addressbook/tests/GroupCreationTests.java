package ru.stqa.auto.addressbook.tests;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import ru.stqa.auto.addressbook.model.GroupData;
import ru.stqa.auto.addressbook.model.Groups;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupCreationTests extends TestBase {

  @DataProvider
  public Iterator<Object[]> validGroups() throws IOException {
    List<Object[]> list = new ArrayList<Object[]>();
    BufferedReader reader = new BufferedReader(new FileReader(new File("src/test/java/resources/groups.csv")));
    String line = reader.readLine();
    while (line != null){
      String[] split = line.split(";");
      list.add(new Object[] {new GroupData().withName(split[0]).withHeader(split[1]).withFooter(split[2])});  // лист заполняется тестовыми данными
      line = reader.readLine();
    }
    return list.iterator();
  }

  @Test(dataProvider = "validGroups")
  public void testGroupCreation(GroupData group) {
    app.goTo().groupPage();
    Groups before = app.group().all(); //объявление переменной типа лист
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size() + 1 ));
    Groups after = app.group().all();
    assertThat(after, equalTo(
            before.withAdded(group.withId(after.stream().mapToInt((g) -> g.getId()).max().getAsInt())))); // 4 Set способ сравнения. Поток идентификации что бы сравнение сразу шло по айди преобразованному в число. в качестве параметра применяется группа а выдается как идентификатор.
  }

  @Test
  public void testBadGroupCreation() {
    app.goTo().groupPage();
    Groups before = app.group().all();
    GroupData group =  new GroupData().withName("test2'");
    app.group().create(group);
    assertThat(app.group().count(), equalTo(before.size()));
    Groups after = app.group().all();
    assertThat(after, equalTo(before));
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
