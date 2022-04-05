package ru.stqa.auto.addressbook.model;

import java.util.Objects;

public class ContactData {
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
//  private final String group;

  public ContactData(String name, String surname, String patronymic, String nickname, String company, String address, String hometel, String worktel, String email, String datebirth, String monthbirth, String yearbirth) {
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
//    this.group = group;
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
    return Objects.equals(name, that.name) && Objects.equals(surname, that.surname)
            && Objects.equals(patronymic, that.patronymic) && Objects.equals(address, that.address)
            && Objects.equals(hometel, that.hometel) && Objects.equals(worktel, that.worktel)
            && Objects.equals(email, that.email) && Objects.equals(datebirth, that.datebirth)
            && Objects.equals(monthbirth, that.monthbirth) && Objects.equals(yearbirth, that.yearbirth);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, surname, patronymic, address, hometel, worktel, email, datebirth, monthbirth, yearbirth);
  }

  @Override
  public String toString() {
    return "ContactData{" +
            "name='" + name + '\'' +
            ", surname='" + surname + '\'' +
            ", patronymic='" + patronymic + '\'' +
            ", nickname='" + nickname + '\'' +
            ", company='" + company + '\'' +
            ", address='" + address + '\'' +
            ", hometel='" + hometel + '\'' +
            ", worktel='" + worktel + '\'' +
            ", email='" + email + '\'' +
            ", datebirth='" + datebirth + '\'' +
            ", monthbirth='" + monthbirth + '\'' +
            ", yearbirth='" + yearbirth + '\'' +
            '}';
  }
}
