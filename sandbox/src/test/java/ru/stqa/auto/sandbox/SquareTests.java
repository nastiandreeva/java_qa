package ru.stqa.auto.sandbox;

import org.testng.Assert;
import org.testng.annotations.Test;

public class SquareTests {

  @Test //аннотация
  public void testAreaSquare1() {
    Square s = new Square(5);
    Assert.assertEquals(s.area(), 20.0); //вспомогательная функция для подробного описания ошибки
  }

  @Test
  public void testAreaSquare2() {
    Square s = new Square(18);
    Assert.assertEquals(s.area(), 324.0); //вспомогательная функция для подробного описания ошибки
  }

  @Test
  public void testAreaRectangle1(){
    Rectangle r = new Rectangle(4, 6);
    Assert.assertEquals(r.area(), 24.0);
  }

  @Test
  public void testAreaRectangle2(){
    Rectangle r = new Rectangle(14, 3);
    Assert.assertEquals(r.area(), 42.0);
  }
}
