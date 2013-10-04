package seb.autotest.server.models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;


/**
 * Defines a database-backed JUnit suite.
 * 
 * @author Stefan Sigurdsson, s44410.
 */
@Entity
@Table(name="test_suite")
public class SuiteDefinition {
	
    public static SuiteDefinition
    get(String name) {
        return Ebean.find(SuiteDefinition.class).where().eq("name", name).findUnique();
    }
    
    @Id
    private Integer id;
    
    @Version
    private Timestamp lastUpdate;

    @Column(unique = true, nullable = false)
    private String name;
	
    @ManyToMany
    private List<Context> contexts;
    
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
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setContexts(List<Context> contexts) {
	    this.contexts = contexts;
	    for (Context context: this.contexts)
	        context.setActiveSuite(this);
	}
	
	public void add(Context context) {
	    this.contexts.add(context);
	}
	
	public void remove(Context context) {
	    this.contexts.remove(context);
	}
    
	public boolean contains(String name) {
        return null != getContext(name);
	}
	
	public boolean contains(Context context) {
	    return contains(context.getName());
	}
	
	public Context getContext(String name) {
	      RawSql raw = RawSqlBuilder.parse(String.format(
	          " select c.id, c.name "
	        + "   from test_context c "
	        + "   join test_suite_test_context cs on c.id = cs.test_context_id "
	        + "  where cs.test_suite_id = %d "
	        + "    and c.name = '%s' ", 
	          getId(), name))
	          .columnMapping("c.id", "id")
	          .columnMapping("c.name", "name")
	          .create();
	      return Context.getContext(raw);
	}
	
    public List<Context> getContexts() {
        return contexts;
    }
    
    public List<Context> getContexts(String dimension, String tag) {
      RawSql raw = RawSqlBuilder.parse(String.format(
          " select c.id, c.name "
        + "   from test_context c "
        + "   join test_suite_test_context cs on c.id = cs.test_context_id "
        + "   join test_context_test_node cn on c.id = cn.test_context_id "
        + "   join test_node n on n.id = cn.test_node_id "
        + "   join test_dimension d on d.id = n.dimension_id "
        + "  where cs.test_suite_id = %d "
        + "    and d.name = '%s' "
        + "    and n.name = '%s' ",
          getId(), 
          dimension.toUpperCase(), 
          tag.toLowerCase()))
          .columnMapping("c.id", "id")
          .columnMapping("c.name", "name")
          .create();
      return Context.getContexts(raw);
    }

    public int size() {
        return getContexts().size();
    }
    
	public String toString() {
		return getName();
	}
}

