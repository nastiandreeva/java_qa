package ru.stqa.auto.homework;

public class Point {

    private double x;
    private double y;

    public Point(double x1, double y1){ //параметризация
      this.x = x1;
      this.y = y1;
    }

    public static double distance(Point p1, Point p2){ //функция
        return Math.sqrt((p1.x - p2.x) *(p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public double distanceMethod(Point p2){ //метод
        return Math.sqrt((p2.x - this.x) *(p2.x - this.x) + (p2.y - this.y) * (p2.y - this.y));
  }
}
