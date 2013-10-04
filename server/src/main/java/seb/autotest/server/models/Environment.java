package seb.autotest.server.models;

import com.avaje.ebean.Ebean;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="environment")
public class Environment {

    public static Environment get(String name) {
        return Ebean.find(Environment.class).where().eq("name", name).findUnique();
    }
    
    @Id
    private Integer id;
    
    @Version
    private Timestamp lastUpdate;

    @Column(unique = true, nullable = false)
    private String name;

    @Column(nullable = false)
    private seb.autotest.server.models.System system;

    public Environment() {}

	public Integer getId() {
	    return id;
	}

	public void setId(Integer id) {
	    this.id = id;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public System getSystem() {
	    return system;
	}
	
	public void setSystem(System system) {
	    this.system = system;
	}
	
    public Timestamp getLastUpdate() {
        return lastUpdate;
    }
    
    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
    
    public String toString() { 
        return getName();
    }
}

