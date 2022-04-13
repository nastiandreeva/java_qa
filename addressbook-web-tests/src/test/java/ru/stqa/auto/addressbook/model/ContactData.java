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
  private String homeTel;
  private String mobileTel;
  private String workTel;
  private String allPhones;
  private String email1;
  private String email2;
  private String email3;
  private String allEmails;
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

  public String getHomeTel() {
    return homeTel;
  }

  public String getMobileTel() {
    return mobileTel;
  }

  public String getWorkTel() {
    return workTel;
  }

  public String getAllPhones() {
    return allPhones;
  }

  public String getAllEmails() {
    return allEmails;
  }

  public String getEmail1() {
    return email1;
  }

  public String getEmail2() {
    return email2;
  }

  public String getEmail3() {
    return email3;
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
    this.homeTel = hometel;
    return this;
  }

  public ContactData withMobileTel(String mobile) {
    this.mobileTel = mobile;
    return this;
  }

  public ContactData withWorkTel(String worktel) {
    this.workTel = worktel;
    return this;
  }

  public ContactData withAllEmails(String allEmails) {
    this.allEmails = allEmails;
    return this;
  }

  public ContactData withAllPhones(String allPhones) {
    this.allPhones = allPhones;
    return this;
  }

  public ContactData withEmail1(String email) {
    this.email1 = email;
    return this;
  }

  public ContactData withEmail2(String emai2) {
    this.email2 = emai2;
    return this;
  }
  public ContactData withEmail3(String email3) {
    this.email3 = email3;
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
    return Objects.equals(name, that.name) && Objects.equals(surname, that.surname) && Objects.equals(address, that.address) && Objects.equals(homeTel, that.homeTel) && Objects.equals(mobileTel, that.mobileTel) && Objects.equals(workTel, that.workTel) && Objects.equals(allPhones, that.allPhones) && Objects.equals(email1, that.email1) && Objects.equals(email2, that.email2) && Objects.equals(email3, that.email3);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, address, homeTel, mobileTel, workTel, allPhones, email1, email2, email3);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            '}';
  }

}
