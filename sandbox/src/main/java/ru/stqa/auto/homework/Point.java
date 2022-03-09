package ru.stqa.auto.homework;

public class Point {

    private double x;
    private double y;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    public Point(double x1, double y1){ //параметризация
      this.x = x1;
      this.y = y1;
    }

    public static double distance(Point p1, Point p2){ //функция
        return Math.sqrt((p1.x - p2.x) *(p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y));
    }

    public Point(double x1, double y1, double x2, double y2){ //параметризация
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public double distanceMethod(){ //метод
        return Math.sqrt((x1 - x2) *(x1 - x2) + (y1 - y2) * (y1 - y2));
  }
}
