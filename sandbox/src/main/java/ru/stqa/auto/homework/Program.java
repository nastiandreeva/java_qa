package ru.stqa.auto.homework;


public class Program {

  public static void main(String[] args) {

    Point p1 = new Point(-1,3);
    Point p2 = new Point(6,2);
    System.out.println("Расстояние между двумя точками " + " = " + p1.distance(p1,p2));

    Point p3 = new Point(0,1);
    Point p4 = new Point(2, -2);
    System.out.println("Расстояние между двумя точками через метод " + " = " + p3.distanceMethod(p4));
    System.out.println("Расстояние между двумя точками через метод " + " = " + p4.distanceMethod(p3));
  }
}
