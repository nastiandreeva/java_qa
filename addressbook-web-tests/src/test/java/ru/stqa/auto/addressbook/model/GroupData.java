package ru.stqa.auto.addressbook.model;

import com.google.gson.annotations.Expose;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XStreamAlias("group")          // xml. изменение значения тега
@Entity                         // приязывает класс к бд
@Table(name = "group_list")
public class GroupData {
  @XStreamOmitField             // xml. игнорировать и не создавать тег id
  @Id
  @Column(name = "group_id")
  private int id = Integer.MAX_VALUE;

  @Expose                       // json. добавить тег в файл
  @Column(name = "group_name")
  private String name;

  @Expose
  @Column(name = "group_header")
  @Type(type = "text")
  private String header;

  @Expose
  @Column(name = "group_footer")
  @Type(type = "text")
  private String footer;

  /* работа со связью многие ко многим по ключу в отдельной таблице, объявляется и для контакта и для группы
  * второй раз описывать связь не нужно, пишется только элемент который есть в ContactData и связь ситается там */
  @ManyToMany (mappedBy = "groups")
  private Set<ContactData> groups = new HashSet<ContactData>();

  public int getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getHeader() {
    return header;
  }

  public String getFooter() {
    return footer;
  }

  public Contacts getGroups() {
    return new Contacts(groups);
  }

  public GroupData withId(int id) {
    this.id = id;
    return this;
  }

  public GroupData withName(String name) {
    this.name = name;
    return this;
  }

  public GroupData withHeader(String header) {
    this.header = header;
    return this;
  }

  public GroupData withFooter(String footer) {
    this.footer = footer;
    return this;
  }

  @Override
  public String toString() {
    return "GroupData{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    GroupData groupData = (GroupData) o;
    return id == groupData.id && Objects.equals(name, groupData.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name);
  }

}
