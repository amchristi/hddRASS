package JReduce;

import Helper.Debugger;
import Helper.PythonRunner;
import Helper.PythonTester;
import Helper.Globals;

/**
 * Created by christia
 * Update as you go. Current assumption is we will run ScenarioRunner.py and it will return us results
 * weak assumption, but added as a starting point.
 */
public class ImmortalsTester implements ITester {
    private String scenarioRunnerPath;
    private String args[];
    private long TIME_TO_WAIT_BEFORE_GIVING_UP;

    @Override
    public boolean run() {
        return false;
    }

    public ImmortalsTester(String runnerPath, String args[]) {
        this.scenarioRunnerPath = runnerPath;
        this.args = args;
        TIME_TO_WAIT_BEFORE_GIVING_UP = Globals.TEST_RUNNING_TIME_OUT * 1000;
        //60 * 1000 * 3; // seconds * ms * min.

    }

    @Override
    public boolean runAll() throws InterruptedException {
        PythonRunner pythonRunner = new PythonRunner("python", scenarioRunnerPath, args);
        PythonTester tester = new PythonTester(pythonRunner);


        ThreadedImmortalsTester threadedrunner = new ThreadedImmortalsTester(tester);
        threadedrunner.start();
        // if infinite loop is introduced
        // give up and return test suite run result as false;
        Debugger.log(TIME_TO_WAIT_BEFORE_GIVING_UP);
        threadedrunner.join(TIME_TO_WAIT_BEFORE_GIVING_UP);

        threadedrunner.interrupt();
        threadedrunner.stop();
        if (!threadedrunner.GetResult())
            return false;

        return true;
    }


}
