package edu.kit.crate.preview;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;

/**
 * The default preview should use the rochtml tool (https://www.npmjs.com/package/ro-crate-html-js)
 * for creating a simple preview file.
 *
 * @author Nikola Tzotchev on 6.2.2022 г.
 * @version 1
 */
public class DefaultPreview implements IROCratePreview {

  private static final String command = "rochtml";

  public DefaultPreview() {
  }

  @Override
  public void saveALLToZip(ZipFile zipFile) {
    try {
      // extract the .json file so we can run the "rochtml" tool on it"
      zipFile.extractFile("ro-crate-metadata.json", "temp");
      PreviewGenerator.generatePreview("temp");
      zipFile.addFile("temp/ro-crate-preview.html");
      FileUtils.deleteDirectory(new File("temp"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void saveALLToFolder(File folder) {
    PreviewGenerator.generatePreview(folder.getAbsolutePath());
  }
}
