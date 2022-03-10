package ru.stqa.auto.sandbox;

public class Square {

  public double l;

  public Square(double l){  // Конструктор
    this.l = l;             // Атрибут и переменная
  };

  public double area(){
    return this.l * this.l;
  }

}
