package seb.autotest.server.models;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="test_dimension")
public class Dimension {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private Integer ordinal;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<Node> nodes;
    
    @Version
    private Timestamp lastUpdate;
    
    public Dimension() {}
    
	public Integer getId() {
	    return id;
	}
	
	public void setId(Integer id) {
	    this.id = id;
	}
	
	public Integer getOrdinal() {
	    return ordinal;
	}
	
	public void setOrdinal(Integer ordinal) {
	    this.ordinal = ordinal;
	}
	
	public String getName() {
	    return name;
	}
	
	public void setName(String name) {
	    this.name = name;
	}
	
	public List<Node> getNodes() {
	    return nodes;
	}
	
	public void setNodes(List<Node> nodes) {
	    this.nodes = nodes;
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

