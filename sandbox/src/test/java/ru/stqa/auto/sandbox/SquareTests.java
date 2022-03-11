package ru.stqa.auto.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

  @Test //аннотация
  public void testAreaSquare() {
    Square s = new Square(5);
    Assert.assertEquals(s.area(), 25.0); //вспомогательная функция для подробного описания ошибки
  }

  @Test
  public void testAreaRectangle(){
    Rectangle r = new Rectangle(4, 6);
    Assert.assertEquals(r.area(), 24.0);
  }
}
