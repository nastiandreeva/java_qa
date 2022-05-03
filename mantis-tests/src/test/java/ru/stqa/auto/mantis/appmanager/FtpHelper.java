package ru.stqa.auto.mantis.appmanager;

import org.apache.commons.net.ftp.FTPClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FtpHelper {

  private final ApplicationManager app;
  private FTPClient ftp;

  public FtpHelper(ApplicationManager app) {                                          // создается ftp клиент
    this.app = app;
    ftp = new FTPClient();
  }

  public void upload(File file, String target, String backup) throws IOException {   // загружается новый файл, а старый временно переименовывается (локальный файл, имя файла, имя резервной копии)
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(backup);                                                          // удаляем файл
    ftp.rename(target, backup);                                                      // переименовываем
    ftp.enterLocalPassiveMode();
    ftp.storeFile(target, new FileInputStream(file));                                // передача файло, читаются/изменяются/сохраняются
    ftp.disconnect();
  }

  public void restore(String backup, String target) throws IOException {              // восстанавливается файл
    ftp.connect(app.getProperty("ftp.host"));
    ftp.login(app.getProperty("ftp.login"), app.getProperty("ftp.password"));
    ftp.deleteFile(target);
    ftp.rename(backup, target);
    ftp.disconnect();

  }
}
