/*
 * Text.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.output;

import com.github.fracpete.javaclassversion.core.Info;
import com.github.fracpete.javaclassversion.core.Utils;

import java.io.IOException;
import java.io.Writer;
import java.util.List;

/**
 * Outputs the info in plain text format.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Text
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
    int		lenLoc;
    int		lenName;
    int		lenVer;
    int		i;

    // determine max lengths
    lenLoc  = 0;
    lenName = 0;
    lenVer  = 0;
    for (Info cls: classes) {
      lenLoc  = Math.max(lenLoc,  cls.getLocation().toString().length());
      lenName = Math.max(lenName, cls.getClassname().length());
      lenVer  = Math.max(lenVer,  cls.getVersionText().length());
    }
    lenLoc  = Math.max(lenLoc,  LOCATION.length());
    lenName = Math.max(lenName, CLASS.length());
    lenVer  = Math.max(lenVer,  VERSION.length());

    // output
    Utils.padRight(output, LOCATION, lenLoc);
    output.write(" ");
    Utils.padRight(output, CLASS, lenName);
    output.write(" ");
    output.write(VERSION);
    output.write("\n");
    for (i = 0; i < lenLoc + lenName + lenVer + 2; i++)
      output.write("-");
    output.write("\n");
    for (Info cls: classes) {
      Utils.padRight(output, cls.getLocation().toString(), lenLoc);
      output.write(" ");
      Utils.padRight(output, cls.getClassname(), lenName);
      output.write(" ");
      output.write(cls.getVersionText());
      output.write("\n");
    }
    output.flush();
  }
}
