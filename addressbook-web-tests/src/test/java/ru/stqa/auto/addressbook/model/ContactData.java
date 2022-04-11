package ru.stqa.auto.addressbook.model;

import java.util.Objects;

public class ContactData {
  private int id = Integer.MAX_VALUE;;
  private String name;
  private String surname;
  private String patronymic;
  private String nickname;
  private String company;
  private String address;
  private String hometel;
  private String worktel;
  private String email;
  private String datebirth;
  private String monthbirth;
  private String yearbirth;

  public int getId() {
    return id;
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

  public ContactData withId(int id) {
    this.id = id;
    return this;
  }

  public ContactData withName(String name) {
    this.name = name;
    return this;
  }

  public ContactData withSurname(String surname) {
    this.surname = surname;
    return this;
  }

  public ContactData withPatronymic(String patronymic) {
    this.patronymic = patronymic;
    return this;
  }

  public ContactData withNickname(String nickname) {
    this.nickname = nickname;
    return this;
  }

  public ContactData withCompany(String company) {
    this.company = company;
    return this;
  }

  public ContactData withAddress(String address) {
    this.address = address;
    return this;
  }

  public ContactData withHometel(String hometel) {
    this.hometel = hometel;
    return this;
  }

  public ContactData withWorktel(String worktel) {
    this.worktel = worktel;
    return this;
  }

  public ContactData withEmail(String email) {
    this.email = email;
    return this;
  }

  public ContactData withDatebirth(String datebirth) {
    this.datebirth = datebirth;
    return this;
  }

  public ContactData withMonthbirth(String monthbirth) {
    this.monthbirth = monthbirth;
    return this;
  }

  public ContactData withYearbirth(String yearbirth) {
    this.yearbirth = yearbirth;
    return this;
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
