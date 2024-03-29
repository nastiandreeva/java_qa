package ru.stqa.auto.mantis.model;

import com.google.gson.annotations.Expose;

import javax.persistence.*;


  @Entity
  @Table(name = "mantis_user_table")

  public class UserData {

    @Id
    @Column(name = "id")
    private Integer id;

    @Expose
    @Column(name = "username")
    private String name;

    @Expose
    @Column(name = "email")
    private String email;

    public Integer getId() {
      return id;
    }

    public String getName() {
      return name;
    }

    public String getEmail() {
      return email;
    }

    public UserData withId(Integer id) {
      this.id = id;
      return this;
    }

    public UserData withName(String name) {
      this.name = name;
      return this;
    }

    public UserData withEmail(String email) {
      this.email = email;
      return this;
    }

    @Override
    public String toString() {
      return "UserData{" +
              "id=" + id +
              ", name='" + name + '\'' +
              ", email='" + email + '\'' +
              '}';
    }

}
