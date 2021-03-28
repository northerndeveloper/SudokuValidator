# SudokuValidator

## Technologies That Used

* Spring Boot 2.4.4
* Open CSV 5.3
* Log4j.API 2.14.1
* JUnit 5
* MavenSureFire-Report Plugin

## How to Execute the Project

1-) Files can be verified by executing jar in the project such like 

' java -jar sudokuvalidator-1.0.0.jar sudokuPuzzle.txt '

sudokuvalidator-1.0.0.jar -> Name of the Jar
sudokuPuzzle.txt  -> Validated file.

Important : File and jar should be in the same path 

2-) It is a Spring Boot Application. Therefore project can be executed by running the project.

A Program Argument which is the csv file to be verified  needs to be provided as well .

Such like sudokuPuzzle.txt.

3-) For the Linux version validate.sh should be executed.

4-) For the Windows version validate.bat should be executed.

## BATCH FILES 

validate.sh & validate.bat files are batch files to be verify csv files.

## TEST REPORT

maven-surefire-report-plugin is added for test reports.
Test reports can be found after mvn clean install jobs from /target/surefire-reports/


