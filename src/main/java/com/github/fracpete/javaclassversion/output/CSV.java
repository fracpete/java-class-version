/*
 * CSV.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.output;

import com.github.fracpete.javaclassversion.core.Info;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Outputs the info in CSV format.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class CSV
  extends AbstractOutputFormat {

  public static final String LOCATION = "Location";

  public static final String CLASS = "Class";

  public static final String VERSION = "Version";

  /**
   * Outputs the class information using the specified writer.
   * Does not close the writer.
   *
   * @param classes	the info to output
   * @param output	where to write it to
   * @throws IOException	if writing fails
   */
  @Override
  public void generate(List<Info> classes, Writer output) throws IOException {
    output.write(LOCATION);
    output.write(",");
    output.write(CLASS);
    output.write(",");
    output.write(VERSION);
    output.write("\n");
    for (Info cls: classes) {
      output.write(cls.getLocation().toString());
      output.write(",");
      output.write(cls.getClassname());
      output.write(",");
      output.write(cls.getVersionText());
      output.write("\n");
    }
    output.flush();
  }
}
