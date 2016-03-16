import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
/**
 * This class is thread safe.
 */
public class Parser {
  private File file;
  public void setFile(File f) {
    synchronized (file) {
      file = f;
    }
  }
  public File getFile() {
    return file;
  }
  public String getContent() throws IOException {
    StringBuffer output = new StringBuffer();
    int data;
    try (FileInputStream i = new FileInputStream(file)) {
      while ((data = i.read()) > 0) {
        output.append(data);
      }
    }
    return output.toString();
  }
  public String getContentWithoutUnicode() throws IOException {
    StringBuffer output = new StringBuffer();
    int data;
    try (FileInputStream i = new FileInputStream(file)) {
      while ((data = i.read()) > 0) {
        if (data < 0x80) {
          output.append(data);
        }
      }
    }
    return output.toString();
  }
  public void saveContent(String content) throws IOException {
    synchronized (file) {
      try (FileOutputStream o = new FileOutputStream(file)) {
        for (int i = 0; i < content.length(); i++) {
          o.write(content.charAt(i));
        }
      }
    }
  }


}
