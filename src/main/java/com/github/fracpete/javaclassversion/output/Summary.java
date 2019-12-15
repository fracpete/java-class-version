/*
 * CSV.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.output;

import com.github.fracpete.javaclassversion.core.Info;
import com.github.fracpete.javaclassversion.core.Utils;

import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Outputs a summary of the versions.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Summary
  extends AbstractOutputFormat {

  public final static String VERSION = "Version";

  public final static String COUNT = "Count";

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
    Map<String,Integer>   	counts;
    int				lenVersion;
    int				lenCount;
    int				i;

    // count the versions
    counts = new HashMap<>();
    for (Info cls: classes) {
      if (!counts.containsKey(cls.getVersionText()))
        counts.put(cls.getVersionText(), 0);
      counts.put(cls.getVersionText(), counts.get(cls.getVersionText()) + 1);
    }

    // determine lengths
    lenVersion = 0;
    lenCount   = 0;
    for (String version: counts.keySet()) {
      lenVersion = Math.max(lenVersion, version.length());
      lenCount   = Math.max(lenCount, ("" + counts.get(version)).length());
    }
    lenVersion = Math.max(lenVersion, VERSION.length());
    lenCount   = Math.max(lenCount, COUNT.length());

    Utils.padRight(output, VERSION, lenVersion);
    output.write(" ");
    Utils.padRight(output, COUNT, lenCount);
    output.write("\n");
    for (i = 0; i < lenVersion + lenCount + 1; i++)
      output.write("-");
    output.write("\n");
    for (String version: counts.keySet()) {
      Utils.padRight(output, version, lenVersion);
      output.write(" ");
      Utils.padLeft(output, "" + counts.get(version), lenCount);
      output.write("\n");
    }
    output.flush();
  }
}
