package ru.stqa.auto.mantis.model;

import biz.futureware.mantis.rpc.soap.client.ObjectRef;

import java.math.BigInteger;

public class Issue {

  private int id;
  private String summary;
  private String description;
  private Project project;
  private BigInteger status;

  public String getSummary() {
    return summary;
  }

  public Issue withSummary(String summary) {
    this.summary = summary;
    return this;
  }

  public String getDescription() {
    return description;
  }

  public Issue withDescription(String description) {
    this.description = description;
    return this;
  }

  public Project getProject() {
    return project;
  }

  public Issue withProject(Project project) {
    this.project = project;
    return this;
  }

  public int getId() {
    return id;
  }

  public Issue withId(int id) {
    this.id = id;
    return this;
  }

  public BigInteger getStatus() {
    return status;
  }

  public Issue withStatus(ObjectRef status) {
    this.status = status.getId();
    return this;
  }

}
