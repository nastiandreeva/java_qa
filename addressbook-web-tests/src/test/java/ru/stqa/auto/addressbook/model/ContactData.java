package ru.stqa.auto.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id;
  private final String name;
  private final String surname;
  private final String patronymic;
  private final String nickname;
  private final String company;
  private final String address;
  private final String hometel;
  private final String worktel;
  private final String email;
  private final String datebirth;
  private final String monthbirth;
  private final String yearbirth;

  public ContactData(String name, String surname, String patronymic, String nickname, String company, String address, String hometel, String worktel, String email, String datebirth, String monthbirth, String yearbirth) {
    this.id = Integer.MAX_VALUE;
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.hometel = hometel;
    this.worktel = worktel;
    this.email = email;
    this.datebirth = datebirth;
    this.monthbirth = monthbirth;
    this.yearbirth = yearbirth;
  }

  public ContactData(int id, String name, String surname, String patronymic,  String nickname, String company, String address, String hometel, String worktel, String email, String datebirth, String monthbirth, String yearbirth) {
    this.id = id;
    this.name = name;
    this.surname = surname;
    this.patronymic = patronymic;
    this.nickname = nickname;
    this.company = company;
    this.address = address;
    this.hometel = hometel;
    this.worktel = worktel;
    this.email = email;
    this.datebirth = datebirth;
    this.monthbirth = monthbirth;
    this.yearbirth = yearbirth;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public String getSurname() {
    return surname;
  }

  public String getPatronymic() {
    return patronymic;
  }

  public String getNickname() {
    return nickname;
  }

  public String getCompany() {
    return company;
  }

  public String getAddress() {
    return address;
  }

  public String getHometel() {
    return hometel;
  }

  public String getWorktel() {
    return worktel;
  }

  public String getEmail() {
    return email;
  }

  public String getDatebirth() {
    return datebirth;
  }

  public String getMonthbirth() {
    return monthbirth;
  }

  public String getYearbirth() {
    return yearbirth;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ContactData that = (ContactData) o;
    return Objects.equals(name, that.name) && Objects.equals(surname, that.surname);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            '}';
  }

}
