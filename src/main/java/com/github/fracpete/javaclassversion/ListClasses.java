/*
 * ListClasses.java
 * Copyright (C) 2019 FracPete
 */

package com.github.fracpete.javaclassversion;

import com.github.fracpete.javaclassversion.core.Info;
import com.github.fracpete.javaclassversion.core.Version;
import com.github.fracpete.javaclassversion.output.AbstractOutputFormat;
import com.github.fracpete.javaclassversion.output.CSV;
import com.github.fracpete.javaclassversion.output.Summary;
import com.github.fracpete.javaclassversion.output.Text;
import net.sourceforge.argparse4j.ArgumentParsers;
import net.sourceforge.argparse4j.impl.Arguments;
import net.sourceforge.argparse4j.inf.ArgumentParser;
import net.sourceforge.argparse4j.inf.ArgumentParserException;
import net.sourceforge.argparse4j.inf.Namespace;

import java.io.File;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Main class for listing class versions.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 */
public class ListClasses
  implements Serializable {

  /** whether to be verbose. */
  protected boolean m_Verbose;

  /**
   * Returns all the version info for the specified location.
   *
   * @param location	class file, directory or jar
   * @return		the versions
   */
  protected List<Info> list(String location) {
    List<Info>		result;

    result = new ArrayList<>();

    return result;
  }

  /**
   * Sets whether to be verbose during generation.
   *
   * @param value	true if verbose
   */
  public void setVerbose(boolean value) {
    m_Verbose = value;
  }

  /**
   * Returns whether to be verbose during generation.
   *
   * @return		true if verbose
   */
  public boolean isVerbose() {
    return m_Verbose;
  }

  /**
   * Collects all the version info.
   *
   * @param classes	the info to add to
   * @param location	the location to inspect
   * @throws Exception	if listing fails
   */
  protected void list(List<Info> classes, File location) throws Exception {
    File[]				files;
    short				version;
    ZipFile				zip;
    Enumeration<? extends ZipEntry> 	entries;
    ZipEntry				entry;
    InputStream				stream;

    if (location.isDirectory()) {
      if (isVerbose())
	System.err.println("Directory: " + location);
      files = location.listFiles();
      if (files == null) {
	System.err.println("Failed to list files/dirs in directory: " + location);
	return;
      }
      for (File file: files) {
        if (file.getName().equals(".") || file.getName().equals(".."))
          continue;
        list(classes, file);
      }
    }
    else if (location.isFile()) {
      if (location.getName().endsWith(".class")) {
	if (isVerbose())
	  System.err.println("Class file: " + location);
	version = Version.fromFile(location);
	if (version > -1)
	  classes.add(new Info(location.getParentFile().toURL(), location.getName(), version));
      }
      else if (location.getName().toLowerCase().endsWith(".jar")) {
	if (isVerbose())
	  System.err.println("Jar file: " + location);
	zip = null;
	try {
	  zip = new ZipFile(location);
	  entries = zip.entries();
	  while (entries.hasMoreElements()) {
	    entry = entries.nextElement();
	    if (!entry.isDirectory() && entry.getName().toLowerCase().endsWith(".class")) {
	      stream = zip.getInputStream(entry);
	      version = Version.fromStream(stream);
	      if (version > -1)
		classes.add(new Info(location.toURL(), entry.getName(), version));
	      stream.close();
	    }
	  }
	}
	catch (Exception e) {
	  System.err.println("Failed to read jar '" + location + "'!");
	  e.printStackTrace();
	}
	finally {
	  if (zip != null)
	    zip.close();
	}
      }
      else {
	if (isVerbose())
	  System.err.println("Skipping: " + location);
      }
    }
  }

  /**
   * Collects all the version info.
   *
   * @param locations	the locations to inspect
   * @return		the gathered information
   * @throws Exception	if listing fails
   */
  public List<Info> list(List<String> locations) throws Exception {
    List<Info> 		result;

    result = new ArrayList<>();
    for (String location : locations)
      list(result, new File(location));

    return result;
  }

  /**
   * Executes the listing.
   *
   * @param options	the command-line options to use
   * @return		true if successful
   * @throws Exception	if listing fails
   */
  public boolean execute(String[] options) throws Exception {
    ArgumentParser 		parser;
    Namespace			ns;
    List<Info>			classes;
    List<String>		inputs;
    AbstractOutputFormat 	format;
    Writer			writer;
    boolean 			close;

    parser = ArgumentParsers.newFor(getClass().getName()).build()
      .description("Listing Java class file versions.");
    parser.addArgument("--input")
      .dest("input")
      .required(true)
      .action(Arguments.append())
      .help("The files or directories to inspect");
    parser.addArgument("--format")
      .dest("format")
      .required(false)
      .setDefault("text")
      .choices("text", "csv", "summary")
      .help("The output format to use");
    parser.addArgument("--output")
      .dest("output")
      .required(false)
      .help("The file to write the generated output to");
    parser.addArgument("--verbose")
      .dest("verbose")
      .action(Arguments.storeTrue())
      .help("Whether to be verbose during generation");

    try {
      ns = parser.parseArgs(options);
    }
    catch (ArgumentParserException e) {
      parser.handleError(e);
      return false;
    }

    setVerbose(ns.getBoolean("verbose"));

    // format
    switch (ns.getString("format")) {
      case "text":
	format = new Text();
	break;
      case "csv":
	format = new CSV();
	break;
      case "summary":
	format = new Summary();
	break;
      default:
	throw new IllegalStateException("Unhandled output: " + ns.getString("output"));
    }

    // collect classes
    inputs  = ns.getList("input");
    classes = list(inputs);

    // generate output
    if (ns.getString("output") == null) {
      writer = new PrintWriter(System.out);
      close  = false;
    }
    else {
      writer = new FileWriter(ns.getString("output"));
      close  = true;
    }
    format.generate(classes, writer);
    if (close)
      writer.close();

    return true;
  }

  /**
   * Executes the listing with the specified parameters.
   *
   * @param args	the parameters to use
   * @throws Exception	if listing fails
   */
  public static void main(String[] args) throws Exception {
    ListClasses l = new ListClasses();
    l.execute(args);
  }
}
