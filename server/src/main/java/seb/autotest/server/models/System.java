package seb.autotest.server.models;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="system")
public class System {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
    
    @Version
    private Timestamp lastUpdate;
    
    public System() {}
    
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

