/*
 * Utils.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.core;

import java.io.IOException;
import java.io.Writer;

/**
 * Class with utility functions.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Utils {

  /**
   * Pads a string with blanks on the right.
   *
   * @param s		the string to pad
   * @param width	the width to pad to
   */
  public static void padRight(Writer output, String s, int width) throws IOException {
    int		i;

    output.write(s);
    for (i = s.length(); i < width; i++)
      output.write(" ");
  }

  /**
   * Pads a string with blanks on the left.
   *
   * @param s		the string to pad
   * @param width	the width to pad to
   */
  public static void padLeft(Writer output, String s, int width) throws IOException {
    int		i;

    for (i = s.length(); i < width; i++)
      output.write(" ");
    output.write(s);
  }
}
