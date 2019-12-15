/*
 * AbstractOutputFormat.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.output;

import com.github.fracpete.javaclassversion.core.Info;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.util.List;

/**
 * Ancestor for output formats.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public abstract class AbstractOutputFormat
  implements Serializable {

  /**
   * Outputs the class information using the specified writer.
   *
   * @param classes	the info to output
   * @param output	where to write it to
   * @throws IOException	if writing fails
   */
  public abstract void generate(List<Info> classes, Writer output) throws IOException;
}
