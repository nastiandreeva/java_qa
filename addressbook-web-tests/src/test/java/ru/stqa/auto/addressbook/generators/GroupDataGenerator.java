package ru.stqa.auto.addressbook.generators;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thoughtworks.xstream.XStream;
import ru.stqa.auto.addressbook.model.GroupData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

public class GroupDataGenerator {

  @Parameter(names = "-c", description = "Group count")  // значения этих переменных указывается в Configurations
  public int count;

  @Parameter(names = "-f", description = "Target file")  // значения этих переменных указывается в Configurations
  public String file;

  @Parameter(names = "-d", description = "Format data")  // значения этих переменных указывается в Configurations
  public String format;

  public static void main (String[] args) throws IOException {
    GroupDataGenerator generator = new GroupDataGenerator();
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
    List<GroupData> groups = generateGroups(count);
    if (format.equals("csv")){
      saveAsCsv(groups, new File(file));
    } else if (format.equals("xml")){
      saveAsXml(groups, new File(file));
    } else if (format.equals("json")){
      saveAsJson(groups, new File(file));
    } else {
      System.out.println("Unrecognized format" + format);
    }
  }

  private void saveAsJson(List<GroupData> groups, File file) throws IOException {
    Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().create();   // setPrettyPrinting для более читаемого вида, а не просто new Gson. excludeFieldsWithoutExposeAnnotation для того что бы указать какие теги пишем
    String json = gson.toJson(groups);
    try (Writer writer = new FileWriter(file)) {
      writer.write(json);
    }
  }

  private void saveAsXml(List<GroupData> groups, File file) throws IOException {
    XStream xstream = new XStream();
    xstream.processAnnotations(GroupData.class);                  // работа с тегами через аннотации в GroupData
//    xstream.alias("group", GroupData.class);                    // изменение значения тега
//    xstream.omitField(GroupData.class, "id");                   // игнорировать и не создавать тег id
    String xml = xstream.toXML(groups);
    try (Writer writer = new FileWriter(file)) {
      writer.write(xml);
    }
  }

  /* сохранение данных в файл */
  private static void saveAsCsv(List<GroupData> groups, File file) throws IOException {
    Writer writer = new FileWriter(file);
    for (GroupData group : groups){
      writer.write(String.format("%s;%s;%s\n", group.getName(), group.getHeader(), group.getFooter()));
    }
    writer.close();
  }

  /* генерация данных */
  private static List<GroupData> generateGroups(int count) {
    List<GroupData> groups = new ArrayList<GroupData>();
    for (int i = 0; i < count; i++) {
      groups.add(new GroupData().withName(String.format("test %s", i)).
              withHeader(String.format("header %s", i)).withFooter(String.format("footer %s", i)));
    }
    return groups;
  }
}
