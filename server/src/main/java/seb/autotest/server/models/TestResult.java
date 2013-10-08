package seb.autotest.server.models;

import ch.ralscha.extdirectspring.generator.ModelAssociation;
import ch.ralscha.extdirectspring.generator.ModelAssociationType;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="test_result")
public class TestResult {

    @Id
    private Integer id;


    private Timestamp lastUpdate;

    @ManyToOne
    @ModelAssociation(value = ModelAssociationType.HAS_ONE)
    private seb.autotest.server.models.TestRun testRun;

    @ManyToOne
    @ModelAssociation(value = ModelAssociationType.HAS_ONE)
    private seb.autotest.server.models.Context context;

    private String className;

    private String methodName;

    private String fileName;

    private Integer resultCode;

    private String errorMessage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public TestRun getTestRun() {
        return testRun;
    }

    public void setTestRun(TestRun testRun) {
        this.testRun = testRun;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }
    
    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public Integer getResultCode() {
        return resultCode;
    }
    
    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errMessage) {
        this.errorMessage = errMessage;
    }
    
    public String toString() { 
        return String.format("%s [%s] [%s] (%s)=%d",
            getMethodName(), 
            getContext().getName(),
            getFileName(), 
            getClassName(),
            getResultCode());
    }
}

