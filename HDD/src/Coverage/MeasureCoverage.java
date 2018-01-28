package Coverage;

import Helper.FileReaderUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by root on 12/17/17.
 */
public class MeasureCoverage {
    // input: 1. a java files with UUID writel line statements 2. Set of UUID that are actually covered.
    // output: set of statements that are covered.

    String modifiedJavaFile;
    String uuidFile;
    Set<UUID> uuidSet;

    public MeasureCoverage(String modifiedJavaFile, String uuidFile){

    }

    private void GetUUIDSet(){
        try {
            LinkedList<String> uuids = (LinkedList<String>) FileReaderUtil.ReadFileByLine(uuidFile);
            uuidSet = uuids.stream().map(x -> UUID.fromString(x)).distinct().collect(Collectors.toSet());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }








}
