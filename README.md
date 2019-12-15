# java-class-version
Library for listing version of Java class files.

## Command-line

```
usage: com.github.fracpete.javaclassversion.ListClasses
       [-h] --input INPUT [--format {text,csv,summary}]
       [--output OUTPUT] [--verbose]

Listing Java class file versions.

named arguments:
  -h, --help             show this help message and exit
  --input INPUT          The files or directories to inspect
  --format {text,csv,summary}
                         The output format to use
  --output OUTPUT        The file to write the generated output to
  --verbose              Whether to be verbose during generation
```

## Maven

Add the following dependency to your `pom.xml`:
```xml
    <dependency>
      <groupId>com.github.fracpete</groupId>
      <artifactId>java-class-version</artifactId>
      <version>0.0.1</version>
    </dependency>
```
