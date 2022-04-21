package ru.stqa.auto.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.auto.addressbook.model.ContactData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class ContactDataGenerator {

  @Parameter(names = "-c", description = "Contact count")  // значения этих переменных указывается в Configurations
  public int count;

  @Parameter(names = "-f", description = "Target file")  // значения этих переменных указывается в Configurations
  public String file;

  @Parameter(names = "-d", description = "Format data")  // значения этих переменных указывается в Configurations
  public String format;

  public static void main (String[] args) throws IOException {
    ContactDataGenerator generator = new ContactDataGenerator();
    JCommander jCommander = new JCommander(generator);
    try {
      jCommander.parse(args);
    } catch (ParameterException ex){
      jCommander.usage();
      return;
    }
    generator.run();
  }

  private void run() throws IOException {
    List<ContactData> contacts = generateContacts(count);
    if (format.equals("csv")){
      saveAsCsv(contacts, new File(file));
    } else if (format.equals("xml")){
      saveAsXml(contacts, new File(file));
    } else if (format.equals("json")){
      saveAsJson(contacts, new File(file));
    } else {
      System.out.println("Unrecognized format" + format);
    }
  }

  private void saveAsJson(List<ContactData> contacts, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();   // setPrettyPrinting для более читаемого вида, а не просто new Gson. excludeFieldsWithoutExposeAnnotation для того что бы указать какие теги пишем
    String json = gson.toJson(contacts);
    try (Writer writer = new FileWriter(file)) {                                                         // try исольуется для закрытия записи в файл и чтения из файла, что бы не расходовалась лишняя память
      writer.write(json);
    }
  }

  private void saveAsXml(List<ContactData> contacts, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(ContactData.class);                                                        // работа с тегами через аннотации в ContactData
    String xml = xstream.toXML(contacts);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  /* сохранение данных в файл */
  private static void saveAsCsv(List<ContactData> contacts, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (ContactData contact : contacts){
      writer.write(String.format("%s;%s;%s\n", contact.getName(), contact.getSurname(), contact.getAddress()
              , contact.getHomeTel(),contact.getHomeTel2(), contact.getEmail1(), contact.getEmail2()));
    }
    writer.close();
  }

  /* генерация данных */
  private static List<ContactData> generateContacts(int count) {
    List<ContactData> contacts = new ArrayList<ContactData>();
    for (int i = 0; i < count; i++) {
      contacts.add(new ContactData().withName(String.format("Имя %s", i)).
              withSurname(String.format("Фамилия %s", i)).withHomeTel(String.format("58-88-8%s", i))
              .withAddress(String.format("Город %s", i)).withHomeTel(String.format("58-88-8%s", i)).withHomeTel2(String.format("57-77-7%s", i))
              .withEmail1(String.format("testmail1-%s@bk.ru", i)).withEmail2(String.format("testmail2-%s@bk.ru", i)));
    }
    return contacts;
  }
}
