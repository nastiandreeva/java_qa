package ru.stqa.auto.addressbook;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class NewContactTests {
  private WebDriver driver;


  @BeforeClass(alwaysRun = true)
  public void setUp() throws Exception {
    System.setProperty("webdriver.chrome.driver", "D:\\chromedriver_win32\\chromedriver.exe");
    driver = new ChromeDriver();
    login("admin", "secret");
  }

  private void login(String username, String password) {
    driver.get("http://localhost/addressbook/");
    driver.findElement(By.name("user")).clear();
    driver.findElement(By.name("user")).sendKeys(username);
    driver.findElement(By.name("pass")).clear();
    driver.findElement(By.name("pass")).sendKeys(password);
    driver.findElement(By.id("LoginForm")).click();
    driver.findElement(By.xpath("//input[@value='Login']")).click();
  }

  @Test
  public void testAddNew() throws Exception {
    goToNewContactPage();
    fillNewContactForm(new NewContactData("Анастасия", "Николаева", "Олеговна", "НастяКуа", "ООО \"Рога и копыта\"", "город Новый Уренгой", "55-55-33", "2-35-12", "naastya@bk.ru", "19", "February", "1992"));
    submitNewContactCreation();
    returnToContactPage();
    logout();
  }

  private void logout() {
    driver.findElement(By.linkText("Logout")).click();
  }

  private void returnToContactPage() {
    driver.findElement(By.linkText("home page")).click();
  }

  private void submitNewContactCreation() {
    driver.findElement(By.name("submit")).click();
  }

  private void fillNewContactForm(NewContactData newContactData) {
    driver.findElement(By.name("firstname")).click();
    driver.findElement(By.name("firstname")).clear();
    driver.findElement(By.name("firstname")).sendKeys(newContactData.getName());
    driver.findElement(By.name("middlename")).clear();
    driver.findElement(By.name("middlename")).sendKeys(newContactData.getSurname());
    driver.findElement(By.name("lastname")).clear();
    driver.findElement(By.name("lastname")).sendKeys(newContactData.getPatronymic());
    driver.findElement(By.name("nickname")).clear();
    driver.findElement(By.name("nickname")).sendKeys(newContactData.getNickname());
    driver.findElement(By.name("company")).clear();
    driver.findElement(By.name("company")).sendKeys(newContactData.getCompany());
    driver.findElement(By.name("address")).clear();
    driver.findElement(By.name("address")).sendKeys(newContactData.getAddress());
    driver.findElement(By.name("home")).clear();
    driver.findElement(By.name("home")).sendKeys(newContactData.getHometel());
    driver.findElement(By.name("work")).clear();
    driver.findElement(By.name("work")).sendKeys(newContactData.getWorktel());
    driver.findElement(By.name("email")).clear();
    driver.findElement(By.name("email")).sendKeys(newContactData.getEmail());
    driver.findElement(By.name("bday")).click();
    new Select(driver.findElement(By.name("bday"))).selectByVisibleText(newContactData.getDatebirth());
    driver.findElement(By.name("bmonth")).click();
    new Select(driver.findElement(By.name("bmonth"))).selectByVisibleText(newContactData.getMonthbirth());
    driver.findElement(By.name("byear")).click();
    driver.findElement(By.name("byear")).clear();
    driver.findElement(By.name("byear")).sendKeys(newContactData.getYearbirth());
  }

  private void goToNewContactPage() {
    driver.findElement(By.linkText("add new")).click();
  }

  @AfterClass(alwaysRun = true)
  public void tearDown() throws Exception {
    driver.quit();
  }

  private boolean isElementPresent(By by) {
    try {
      driver.findElement(by);
      return true;
    } catch (NoSuchElementException e) {
      return false;
    }
  }

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

}
