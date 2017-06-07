# hddRASS
How To Use:
Look at hddRASS/HDD/src/Driver/CommonsValidatorDriver class and use a command similar to this. 


java -cp "hdd.jar:commons-cli-1.3.1.jar:javaparser-1.0.8.jar:commons-collections4-4.1.jar:junit-4.12.jar:log4j-1.2.17.jar:hamcrest-core-1.3.jar:hamcrest-junit-2.0.0.0.jar:java-hamcrest-2.0.0.0.jar" Driver.CommonsValidatorDriver
-level
method
-rootFolder
"/home/ubuntu/research"
-relativePath
"/commons-text/src/main/java/org/apache/commons/text/StrBuilder.java"
-methodName
isValid
-className
StrBuilder
-jarFileName
"/home/ubuntu/research/cruisecontrol/out/artifacts/cruisecontrol/cruiseControl.jar"
-testJarFileName
"/home/ubuntu/research/cruisecontrol/out/artifacts/cruisecontrol/cruiseControl.jar"
-buildCommand
"ant -buildfile "/home/ubuntu/research/cruisecontrol/cruisecontrol.xml""
-packageName
"org.apache.commons.text"
-timeoutForInfiniteLoop
30
-testClassesFilePath
"/home/ubuntu/research/HDD/src/Driver/CommonsValidatorTests.data"
-testClassRelativePath
"/commons-text/src/test/java/org/apache/commons/text/StrBuilderTest.java"
-pristineFolder
"/home/ubuntu/pristine"
-resultsFolder
"/home/ubuntu/results/reduced/"
-testSuiteName
"org.apache.commons.text.StrBuilderTest"
