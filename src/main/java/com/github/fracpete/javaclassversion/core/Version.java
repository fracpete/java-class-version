/*
 * Version.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Helper class for versions.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Version {

  /**
   * Reads the version from the specified stream. Does not close the stream.
   *
   * @param location	the stream to read
   * @return		the version, -1 if failed to determine
   */
  public static short fromStream(InputStream location) {
    short		result;
    byte[]		buffer;
    int			read;

    result = -1;
    buffer = new byte[10];
    try {
      read = location.read(buffer, 0, 10);
      if (read > 8)
        result = toShort(new byte[]{buffer[6], buffer[7]});
    }
    catch (Exception e) {
      // ignored
    }

    return result;
  }

  /**
   * Reads the version from the specified file.
   *
   * @param location	the file to read
   * @return		the version, -1 if failed to determine
   */
  public static short fromFile(File location) {
    short		result;
    FileInputStream	fis;
    byte[]		buffer;
    int			read;

    result = -1;
    fis    = null;
    buffer = new byte[10];
    try {
      fis = new FileInputStream(location);
      read = fis.read(buffer, 0, 10);
      if (read > 8)
        result = toShort(new byte[]{buffer[6], buffer[7]});
    }
    catch (Exception e) {
      // ignored
    }
    finally {
      if (fis != null) {
        try {
          fis.close();
	}
	catch (Exception e) {
          // ignored
	}
      }
    }

    return result;
  }

  /**
   * Turns the bytes 6/7 (0-based) containing the version of a class into
   * a version String.
   *
   * @param values	the bytes 6 and 7
   * @return		the version as short
   */
  public static short toShort(byte[] values) {
    ByteBuffer buffer;

    buffer = ByteBuffer.allocate(2);
    buffer.order(ByteOrder.BIG_ENDIAN);
    buffer.put(values);
    return buffer.getShort(0);
  }

  /**
   * Turns the version of a class into  a version String.
   *
   * @param version	the version
   * @return		the version string
   */
  public static String toString(short version) {
    switch (version) {
      case 45:
      	return "JDK 1.1";
      case 46:
      	return "JDK 1.2";
      case 47:
      	return "JDK 1.3";
      case 48:
      	return "JDK 1.4";
      case 49:
      	return "Java SE 5.0";
      case 50:
      	return "Java SE 6.0";
      case 51:
      	return "Java SE 7";
      case 52:
      	return "Java SE 8";
      case 53:
      	return "Java SE 9";
      case 54:
      	return "Java SE 10";
      case 55:
      	return "Java SE 11";
      case 56:
      	return "Java SE 12";
      case 57:
      	return "Java SE 13";
      case 58:
      	return "Java SE 14";
      default:
        return "Unknown version: " + version;
    }
  }
}
