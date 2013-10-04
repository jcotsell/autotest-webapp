package seb.autotest.server.models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="test_node")
public class Node {

    public static Node get(String tag) {
        return Ebean.find(Node.class).where().eq("name", tag).findUnique();
    }
    
    public static Node get(String dimension, String tag) {
        RawSql raw = RawSqlBuilder.parse(String.format(
            " select n.id, n.name "
          + "   from test_node n "
          + "   join test_dimension d on d.id = n.dimension_id "
          + "  where d.name = '%s' "
          + "    and n.name = '%s' ",
            dimension.toUpperCase(), 
            tag.toLowerCase()))
            .columnMapping("n.id", "id")
            .columnMapping("n.name", "name")
            .create();
        Query<Node> query = Ebean.find(Node.class);
        query.setRawSql(raw);
        return query.findUnique();
    }
    
    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;
    
    @ManyToOne
    @Column(nullable = false)
    private seb.autotest.server.models.Dimension dimension;

    @Version
    private Timestamp lastUpdate;

    public Node() {}

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

	public Dimension getDimension() {
	    return dimension;
	}

	public void setDimension(Dimension dimension) {
	    this.dimension = dimension;
	}
	
	public int getOrdinal() {
	    return getDimension().getOrdinal();
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

