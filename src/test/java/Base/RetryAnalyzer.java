package Base;

import org.testng.IRetryAnalyzer;

import org.testng.ITestResult;


public class RetryAnalyzer implements IRetryAnalyzer  {

    private int count = 0;

    private int maxCount = 2;

    @Override

    public boolean retry(ITestResult result) {

        if(count < maxCount) {

            count++;

            return true;

        }

        return false;

    }
    //The test will be executed up to three times if it does not pass;
    // after the third attempt, the test will definitively fail.
}