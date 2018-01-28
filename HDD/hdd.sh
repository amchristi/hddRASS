JAVA_PATH="/usr/lib/jvm/java-8-oracle/bin/java"
echo "*********** java path ******************"
echo $JAVA_PATH
IMMORTALS_ROOT_PATH="/root/immortals_repo"
echo "*********** immortals root path ******************"
echo $IMMORTALS_ROOT_PATH
OUTPUT_PATH="/home/ubuntu/research/HDD/out/production/JavaTestReducer"
echo "*********** hdd tool output path ******************"
echo $OUTPUT_PATH
SUPPORT_JAR_PATH="/home/ubuntu/research/HDD/out/production/JavaTestReducer/"
echo "*********** support jar path ******************"
echo $SUPPORT_JAR_PATH
ROOT_IMMORTALS_PATH="/root/immortals_repo"
junit_path=$SUPPORT_JAR_PATH"junit-4.12.jar"
echo "*********** junit path ******************"
echo $junit_path
hamcrest_path=$SUPPORT_JAR_PATH"hamcrest-core-1.3.jar"
echo "*********** hamcrest path ******************"
echo $hamcrest_path
javaparser_path=$SUPPORT_JAR_PATH"javaparser-1.0.8.jar"
echo "*********** java parser path ******************"
echo $javaparser_path
jsonsimple_path=$SUPPORT_JAR_PATH"json-simple-1.1.jar"
echo "*********** json simple path ******************"
echo $jsonsimple_path
cli_path=$SUPPORT_JAR_PATH"commons-cli-1.3.1.jar"
echo "*********** commons cli path ******************"
echo $cli_path

echo "Staring run..................................."

$JAVA_PATH -classpath $OUTPUT_PATH:$junit_path:$hamcrest_path:$javaparser_path:$jsonsimple_path:$cli_path Driver.ATAKServerDriver -isDebug true -level class -rootFolder $ROOT_IMMORTALS_PATH -relativePath /applications/server/Marti/src/com/bbn/filter/Images.java -methodName not_needed_in_your_case_kept_for_individual_method_reduction -className Images -jarFileName "not needed in your case" -testJarFileName "not needed in your case" -runCommand "add your run command here" -buildCommand "add your build command here" -packageName com.bbn.filter -timeoutForInfiniteLoop 30 -testClassesFilePath "not needed in bbn case keep some string here I may be doing bad job with empty string check" -testClassRelativePath /applications/server/Marti/test/com/bbn/marti/Tests.java -pristineFolder "not needed in your case" -resultsFolder "not needed in your case" -testSuiteName com.bbn.marti.Tests -testJsonFile /home/ubuntu/research/HDD/src/Driver/tests.json -compileOutputPath /home/ubuntu/results/temp/tempAntCompile.txt -buildSuccessString "BUILD SUCCESSFUL" -buildSuccessString2 "BUILD SUCCESSFUL" -testResultOutputPath /home/ubuntu/results/temp/




