/*
 * Info.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion.core;

import java.io.Serializable;
import java.net.URL;

/**
 * Container for storing location, classname and version.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class Info
  implements Serializable {

  /** the location. */
  protected URL m_Location;

  /** the classname. */
  protected String m_Classname;

  /** the version. */
  protected short m_Version;

  /** the version string. */
  protected String m_VersionText;

  /**
   * Initializes the container
   *
   * @param location	the location
   * @param classname	the name of the class
   * @param version	the version
   */
  public Info(URL location, String classname, short version) {
    m_Location    = location;
    m_Classname   = classname;
    m_Version     = version;
    m_VersionText = null;
  }

  /**
   * Returns the location.
   *
   * @return		the location
   */
  public URL getLocation() {
    return m_Location;
  }

  /**
   * Returns the classname.
   *
   * @return		the name
   */
  public String getClassname() {
    return m_Classname;
  }

  /**
   * Returns the version.
   *
   * @return		the version
   */
  public short getVersion() {
    return m_Version;
  }

  /**
   * Returns the version as text.
   *
   * @return 		the version as text
   */
  public String getVersionText() {
    if (m_VersionText == null)
      m_VersionText = Version.toString(m_Version);
    return m_VersionText;
  }

  /**
   * Returns a string representation of the container.
   *
   * @return		the representation
   */
  public String toString() {
    return m_Location + "/" + m_Classname + ":" + m_Version;
  }
}
