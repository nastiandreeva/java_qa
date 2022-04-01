package ru.stqa.auto.sandbox;

import java.util.Arrays;
import java.util.List;

public class Collections {

  public static void main(String [] args) { //объявляем массив(коллекцию) из значений что бы ее перебирать в цикле
    String[] langs = {"Java", "C#", "PHP", "Python"};

    List<String> languages = Arrays.asList("Java", "C++", "PHP", "Python"); //хождение по элементам списка как по массиву

    for (String l : languages){ //перебор элементов массива
      System.out.println("Я хочу выучить " + l);
    }
  }
}
