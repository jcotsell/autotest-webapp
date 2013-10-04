package seb.autotest.server.models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.bean.EntityBean;

import javax.persistence.*;
import java.io.File;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.util.*;


/**
 * Defines a sequence of related tests.
 * 
 * @author Stefan Sigurdsson, s44410.
 */
@Entity
@Table(name="test_context")
public class Context {
	
    public static Context get(String name) {
        return Ebean.find(Context.class).where().eq("name", name).findUnique();
    }
    
    @Id
    private Integer id;
    
    @Version
    private Timestamp lastUpdate;

    @ManyToMany
    private List<Node> nodes;
    
    @Column(unique = true, nullable = false)
    private String name;
    
    private Integer basisId;
    
    @ManyToOne
    private Environment basisEnv;

    // private Boolean generated;

    /**
     * Relative path to context fixture files.
     */
    private String fixturePath;

    private String files;

    @ManyToOne
    private seb.autotest.server.models.System source;

    @OneToMany(cascade = {CascadeType.ALL})
    private List<Sequence> sequences;

    @Transient
	private Collection<String> rewriters;

    /**
     * This is used to keep track of what suite the context belongs to during
     * a particular test run. A given context can be run in any number of
     * suites, hence this association is not written to the database (except
     * as part of the <code>SuiteDefinition</code> model and join table).
     */
    @Transient
    private seb.autotest.server.models.SuiteDefinition activeSuite;

	public Context() {
	    this.rewriters = new ArrayList<String>();
	}

	public Context(Context original, String name) {
	    this();
	    new ContextFactory().initialize(this, original);
        setName(name);
        setActiveSuite(original.getActiveSuite());
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

	public String getName() {
	    if (null == name && null != nodes) {
	        // Combines the nodes to form the name...
	        List<Node> sorted = new ArrayList<Node>(nodes.size());
	        // This is delicate - the Ebean interceptor for the nodes property
	        // has already run, so we can sneak in and reset the property...:
	        Collections.copy(this.nodes, nodes);
	        Collections.sort(this.nodes, new Comparator<Node>() {
	            public int compare(Node left, Node right) {
	                return compare(left.getOrdinal(), right.getOrdinal());
	            }
	            public int compare(int left, int right) {
	                if (left == right)
	                    return 0;
	                else if (left < right)
	                    return -1;
	                else
	                    return 1;
	            }
	        });
	        StringBuilder buffer = new StringBuilder();
	        for (Node node: sorted) {
	            buffer.append(node.getName());
	            buffer.append("-");
	        }
	        buffer.setLength(buffer.length() - 1);
	        name = buffer.toString();
	    }
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

	public Node getNode(String dimensionName) {
	    Dimension dimension = Ebean.find(Dimension.class)
	        .where().eq("name", dimensionName).findUnique();
	    if (null == dimension)
	        return null;
	    for (Node node: getNodes())
	        if (node.getDimension().equals(dimension))
	            return node;
	    return null;
	}

	public List<Node>
	findNodes() {
	    return findNodes(getName());
	}

	public static List<Node>
	findNodes(Context context) {
	    return findNodes(context.getName());
	}

	public static List<Node>
	findNodes(String name) {
	    List<Node> matches = new ArrayList<Node>();
	    String[] nodeNames = name.split("-");
	    for (String nodeName: nodeNames) {
	        Node node = Ebean.find(Node.class)
	            .where().eq("name", nodeName).findUnique();
	        if (null != node)
	            matches.add(node);
	    }
	    return matches;
	}

	public Integer getBasisId() {
	    return basisId;
	}

	public void setBasisId(Integer basisId) {
	    this.basisId = basisId;
	}

	public Environment getBasisEnv() {
	    return basisEnv;
	}

	public void setBasisEnv(Environment basisEnv) {
	    this.basisEnv = basisEnv;
	}
/*
	public Boolean getGenerated() {
	    return generated;
	}
	*/
	public boolean isGenerated() {
	    return false;
//	    Boolean generated = getGenerated();
	//    return (null != generated) ? generated : true;
	}
	/*
	public void setGenerated(Boolean generated) {
	    this.generated = generated;
	}
	*/

	public String getFixturePath() {
        return fixturePath;
    }

    public void setFixturePath(String fixturePath) {
        this.fixturePath = fixturePath;
    }

	public String[] getFileNames() {
	    String glommed = getFiles();
	    return (null == glommed || "".equals(glommed))
	        ? new String[]{}
	        : glommed.split(",");
	}

	public String getFiles() {
	    return files;
	}

	public void setFiles(String glommed) {
	    files = glommed;
	}

	public System getSource() {
	    return source;
	}

	public void setSource(System system) {
	    this.source = system;
	}

	public SuiteDefinition getActiveSuite() {
	    return activeSuite;
	}

	public void setActiveSuite(SuiteDefinition suite) {
	    this.activeSuite = suite;
	}

//	public String getInputPath() {
//		return findInputPath();
//	}

//	public String[] findInputPaths(String encodedBasenames) {
//		String[] basenames = (null != encodedBasenames)
//		        ? encodedBasenames.split("\\+") : new String[] { "" };
//		String[] results = new String[basenames.length];
//		for (int i = 0; i < basenames.length; ++i)
//			results[i] = findInputPath(basenames[i]);
//		return results;
//	}
//
//	private String findInputPath() {
//	    return findInputPath("");
//	}

//	private String findInputPath(String basename) {
//		String dirname = getFixturePath();
//        String fixturePath = getConfiguration(FIXTURE_ROOT_PATH);
//        String combined = combine(combine(fixturePath, dirname), basename);
//        if (resourceExists(combined))
//            return combined;
//        throw new IllegalStateException(String.format(
//            "Unable to determine input path (%s, %s)",
//            dirname, basename));
//	}

	private static String combine(String dirname, String basename) {
        return (dirname.endsWith("/"))
            ? dirname + basename : dirname + "/" + basename;
    }

    public static boolean resourceExists(String path) {
        return new File(path).exists();
    }

	public List<Sequence>
	getSequences() {
	    return sequences;
	}

	public void setSequences(List<Sequence> sequences) {
	    this.sequences = sequences;
	}

	public void
	addSequence(Sequence sequence) {
		if (null == getSequences())
		    setSequences(new ArrayList<Sequence>());
		getSequences().add(sequence);
	}
	
	public void
	clearSequences() {
	    getSequences().clear();
	}
	
    public Object getParameterValue(String name) {
        String getterName = "get" + name.substring(0, 1).toUpperCase() + name.substring(1);
        try {
            Method getter = this.getClass().getMethod(getterName, new Class<?>[] {});
            Object value = getter.invoke(this, new Object[] {});
            // Hacked to fit existing code when moving to database back-end:
            if (value instanceof EntityBean)
                value = value.toString();
            return value;
        } catch (Exception x) {
            throw new IllegalArgumentException(String.format(
                "No such attribute: %s", name), x);
        }  
    }
    
	public Collection<String> getRewriters() {
	    return rewriters;
	}
	
	public void addRewriter(String specifier) {
	    rewriters.add(specifier);
	}
	
	public String getUri() {
        try {
            return ContextFactory.createUri(this).toString();
        } catch (ClassNotFoundException x) {
            throw new RuntimeException(x);
        }
	}
	
	public String toString() {
		return getName();
	}
	
    public static List<Context>
    getContexts(RawSql raw) {
        Query<Context> query = Ebean.find(Context.class);
        query.setRawSql(raw);
        return query.findList();
    }
    
    public static Context
    getContext(RawSql raw) {
        Query<Context> query = Ebean.find(Context.class);
        query.setRawSql(raw);
        return query.findUnique();
    }
}

