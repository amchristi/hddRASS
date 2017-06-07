package Helper;

/**
 * Created by christia
 * Hacky class to detect that tests passed or not.
 * Need to change.
 *
 */
public class PythonTester {
    private PythonRunner pythonRunner;
    private String TEST_PASS_INDICATION = "ALL TESTS PASSED";
    private String TEST_FAIL_INDICATION = "ONE OR MORE TESTS FAILED";

    public PythonTester(PythonRunner pythonRunner){
        this.pythonRunner = pythonRunner;
    }

    public boolean test(){
        boolean result = false;
        result = pythonRunner.executeAndReturnResult();
        return result;


    }

}
