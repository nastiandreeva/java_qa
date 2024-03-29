package ru.stqa.auto.mantis.tests;

import biz.futureware.mantis.rpc.soap.client.IssueData;
import biz.futureware.mantis.rpc.soap.client.MantisConnectLocator;
import biz.futureware.mantis.rpc.soap.client.MantisConnectPortType;
import biz.futureware.mantis.rpc.soap.client.ProjectData;
import org.testng.annotations.Test;
import ru.stqa.auto.mantis.model.Issue;
import ru.stqa.auto.mantis.model.Project;

import javax.xml.rpc.ServiceException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Set;

import static org.testng.AssertJUnit.assertEquals;

public class SoapTests extends TestBase{

  @Test
  public void testGetProjects() throws MalformedURLException, ServiceException, RemoteException {
    Set<Project> projects = app.soap().getProjects();
    System.out.println(projects.size());
    for (Project project : projects) {
      System.out.println(project.getName());
    }
  }

  @Test
  public void testCreateIssue() throws RemoteException, ServiceException, MalformedURLException {
    Set<Project> projects = app.soap().getProjects();
    Issue issue = new Issue().withSummary("Test issue").withDescription("Test issue description")
            .withProject(projects.iterator().next());
    Issue created = app.soap().addIssue(issue);
    assertEquals(issue.getSummary(), created.getSummary());
  }

  @Test
  public void testSkipIfBug() throws RemoteException, ServiceException, MalformedURLException {
    if (isIssueOpen(3) == true) {                               // если статус бага != 90 то скипать, так как айди статус 90 значи что баг закрыт и починен
      skipIfNotFixed(3);
    } else {
      Issue newIssue = new Issue().withSummary("Bug closed").withDescription("Test not skip");
      Issue created = app.soap().addIssue(newIssue);
      assertEquals(newIssue.getSummary(), created.getSummary());
    }
  }
}
