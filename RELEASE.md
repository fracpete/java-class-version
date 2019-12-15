# How to make a release

## Release to Maven Central

* Run the following command to deploy the artifact:

  ```
  mvn release:clean release:prepare release:perform
  ```

* Push all changes

## Github

* add `-spring-boot.jar` to release
 