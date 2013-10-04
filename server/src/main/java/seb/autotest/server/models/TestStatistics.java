package seb.autotest.server.models;

import java.text.DecimalFormat;
import java.util.List;


public class TestStatistics {

    private final TestRun testRun;
    
    private List<Context> contexts;
    
    private int total;
    private int successes;
    private int failures;
    private int unrecognized;
    
    public TestStatistics(TestRun testRun) {
        this.testRun = testRun;
        calculate();
    }
    
    public TestRun getTestRun() {
        return testRun;
    }
    
    public List<Context> getContexts() {
        if (null == contexts)
            contexts = getTestRun().getSuite().getContexts();
        return contexts;
    }
    
    public List<Context> getContexts(String dimension, String tag) {
        return getTestRun().getSuite().getContexts(dimension, tag);
    }
    
    public List<Context> getContextsExecuted() {
        return getTestRun().getContextsExecuted();
    }
    
    public List<Context> getContextsExecuted(String dimension, String tag) {
        return getTestRun().getContextsExecuted(dimension, tag);
    }
    
    public double getContextRatio(String dimension, String tag) {
        return new Double(getContexts(dimension, tag).size()) 
            / new Double(getContexts().size());
    }
    
    public double getContextExecutionRatio() {
        return new Double(getContextsExecuted().size()) 
            / new Double(getContexts().size());
    }
    
    public double getContextExecutionRatio(String dimension, String tag) {
        return new Double(getContextsExecuted(dimension, tag).size()) 
            / new Double(getContexts(dimension, tag).size());
    }
    
    private static final DecimalFormat PERCENT_FORMATTER = new DecimalFormat("##0.00");
    
    public String formatAsPercent(double value) {
        return String.format("%s%%", PERCENT_FORMATTER.format(100.0 * value));
    }
    
    public List<TestResult> getTestResults() {
        return getTestRun().getTestResults();
    }
    
    public List<TestResult> getTestResults(String dimension, String tag) {
        return getTestRun().getTestResults(dimension, tag);
    }
    
    public double getTestResultsRatio(String dimension, String tag) {
        return new Double(getTestRun().getTestResults(dimension, tag).size())
            / new Double(getTestRun().getTestResults().size());
    }
    
    public List<TestResult> getTestResults(String clause) {
        return getTestRun().getTestResults(clause);
    }
    
    public double getTestResultsRatio(String clause) {
        return new Double(getTestRun().getTestResults(clause).size())
            / new Double(getTestRun().getTestResults().size());
    }
    
    public int getTotal() {
        return total;
    }
    
    public int getSuccesses() {
        return successes;
    }
    
    public int getSuccesses(String clause) {
        return getTestRun().getTestResults(clause + " and r.result_code = 1 ").size();
    }
    
    public int getSuccesses(String dimension, String tag) {
        return getTestRun().getTestResults(
            dimension, tag, " and r.result_code = 1 ")
            .size();
    }
    
    public int getFailures() {
        return failures;
    }
    
    public int getFailures(String clause) {
        return getTestRun().getTestResults(clause + " and r.result_code = -1 ").size();
    }
    
    public int getFailures(String dimension, String tag) {
        return getTestRun().getTestResults(
            dimension, tag, " and r.result_code = -1 ")
            .size();
    }
    
    public int getUnrecognized() {
        return unrecognized;
    }
    
    public double getSuccessRatio() {
        return new Double(getSuccesses()) / new Double(getTotal());
    }
    
    public double getSuccessRatio(String dimension, String tag) {
        return new Double(getSuccesses(dimension, tag))
            / new Double(getTestResults(dimension, tag).size());
    }
    
    public double getSuccessRatio(String clause) {
        return new Double(getSuccesses(clause))
            / new Double(getTestResults(clause).size());
    }
    
    public double getFailureRatio() {
        return new Double(getFailures()) / new Double(getTotal());
    }
    
    public double getFailureRatio(String dimension, String tag) {
        return new Double(getFailures(dimension, tag))
            / new Double(getTestResults(dimension, tag).size());
    }
    
    public double getFailureRatio(String clause) {
        return new Double(getFailures(clause))
            / new Double(getTestResults(clause).size());
    }
    
    private void calculate() {
        List<TestResult> results = getTestRun().getTestResults();
        int total = results.size();
        int successes = 0;
        int failures = 0;
        int unrecognized = 0;
        for (TestResult result: results) {
            Integer resultCode = result.getResultCode();
            switch (resultCode) {
            case -1:
                ++failures;
                break;
            case 1:
                ++successes;
                break;
            default:
                ++unrecognized;
                break;
            }
        }
        this.total = total;
        this.successes = successes;
        this.failures = failures;
        this.unrecognized = unrecognized;
    }
}
