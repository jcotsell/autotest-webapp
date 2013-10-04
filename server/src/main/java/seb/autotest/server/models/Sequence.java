package seb.autotest.server.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import java.sql.Timestamp;


/**
 * @see seb.autotest.server.models.Context
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="test_sequence")
public class Sequence {

    @Id
    private Integer id;

	private String inputPath;
	
	private String classNames;
	
    @Version
    private Timestamp lastUpdate;
    
    public Sequence() {}
    
	public Sequence(String classNames, String inputPath) {
        setClassNames(classNames);
        setInputPath(inputPath);
	}
	
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
    
	public String getInputPath() {
	    return inputPath;
	}
	
	public void setInputPath(String inputPath) {
	    this.inputPath = inputPath;
	}
	
	public String getClassNames() {
	    return classNames;
	}
	
	public void setClassNames(String classNames) {
	    this.classNames = classNames;
	}
	
	public Class<?>[] getClasses() throws ClassNotFoundException {
	    return getClasses(getClassNames());
	}
	
	public boolean isFirst(Class<?> testClass) throws ClassNotFoundException {
		return getClasses()[0].equals(testClass);
	}
    
    
    private static Class<?>[] 
    getClasses(String classNames) 
    throws ClassNotFoundException {
        return getClasses(classNames.split(":"));       
    }

    protected static Class<?>[] 
    getClasses(String[] classNames) 
    throws ClassNotFoundException {
        Class<?>[] classes = new Class<?>[classNames.length];
        for (int i = 0; i < classNames.length; ++i) 
            classes[i] = getClass(classNames[i]);
        return classes;
    }
    
    public static final String PACKAGE_PREFIX = "se.seb.test.sequences.";
    
    protected static Class<?> 
    getClass(String className) 
    throws ClassNotFoundException {
        String fullyQualified = PACKAGE_PREFIX + className;
        return Class.forName(fullyQualified);
    }
}

