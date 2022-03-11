package ru.stqa.auto.distancePoint;


public class Program {

  public static void main(String[] args) {

    Point p1 = new Point(0, 1);
    Point p2 = new Point(2,-2);
    System.out.println("Расстояние между двумя точками через функцию" + " = " + Point.distance(p1,p2));

    Point p3 = new Point(0,1);
    Point p4 = new Point(2, -2);
    System.out.println("Расстояние между двумя точками через метод " + " = " + p3.distanceMethod(p4));
    System.out.println("Расстояние между двумя точками через метод " + " = " + p4.distanceMethod(p3));
  }
}
