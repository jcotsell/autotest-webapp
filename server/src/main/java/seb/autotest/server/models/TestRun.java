package seb.autotest.server.models;

import com.avaje.ebean.Ebean;
import com.avaje.ebean.Query;
import com.avaje.ebean.RawSql;
import com.avaje.ebean.RawSqlBuilder;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * @author Stefan Sigurdsson, s44410
 */
@Entity
@Table(name="test_run")
public class TestRun {

    @Id
    private Integer id;
    
    @Version
    private Timestamp lastUpdate;

    @ManyToOne
    private seb.autotest.server.models.SuiteDefinition suite;

    private Timestamp startTime;

    @ManyToOne
    private seb.autotest.server.models.Environment environment;

    @OneToMany
    private List<TestResult> testResults;

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

    public SuiteDefinition getSuite() {
        return suite;
    }

    public void setSuite(SuiteDefinition suite) {
        this.suite = suite;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<TestResult> getTestResults() {
        if (null == testResults)
            testResults = new ArrayList<TestResult>();
        return testResults;
    }

    public List<TestResult> getTestResults(String dimension, String tag) {
        return getTestResults(dimension, tag, "");
    }

    public List<TestResult>
    getTestResults(String clause) {
        return getTestResults(RawSqlBuilder.parse(String.format(
            " select r.id "
          + "   from test_result r "
          + "   join test_run rr on r.test_run_id = rr.id "
          + "   join test_context c on r.context_id = c.id "
          + "  where rr.id = %d "
          + clause,
          getId()))
          .columnMapping("r.id", "id")
          .create());
    }

    public List<TestResult>
    getTestResults(String dimension, String tag, String clause) {
        return getTestResults(RawSqlBuilder.parse(String.format(
            " select r.id "
          + "   from test_result r "
          + "   join test_run rr on r.test_run_id = rr.id "
          + "   join test_context c on r.context_id = c.id "
          + "   join test_context_test_node cn on c.id = cn.test_context_id "
          + "   join test_node n on n.id = cn.test_node_id "
          + "   join test_dimension d on d.id = n.dimension_id "
          + "  where rr.id = %d "
          + "    and d.name = '%s' "
          + "    and n.name = '%s' "
          + clause,
          getId(),
          dimension.toUpperCase(),
          tag.toLowerCase()))
          .columnMapping("r.id", "id")
          .create());
    }

    public static List<TestResult> getTestResults(RawSql raw) {
      Query<TestResult> query = Ebean.find(TestResult.class);
      query.setRawSql(raw);
      return query.findList();
    }

    public List<Context>
    getContextsExecuted() {
        return Context.getContexts(RawSqlBuilder.parse(String.format(
                " select distinct(c.id), c.name "
                        + "   from test_context c "
                        + "   join test_result r "
                        + "     on r.context_id = c.id "
                        + "  where r.test_run_id = %d ",
                getId()))
                .columnMapping("distinct(c.id)", "id")
                .columnMapping("c.name", "name")
                .create());
    }

    public List<Context>
    getContextsExecuted(String dimension, String tag) {
        return Context.getContexts(RawSqlBuilder.parse(String.format(
                " select distinct(c.id), c.name "
                        + "   from test_context c "
                        + "   join test_context_test_node cn "
                        + "     on cn.test_context_id = c.id "
                        + "   join test_node n "
                        + "     on cn.test_node_id = n.id "
                        + "   join test_dimension d "
                        + "     on n.dimension_id = d.id "
                        + "   join test_result r "
                        + "     on r.context_id = c.id "
                        + "  where d.name = '%s' "
                        + "    and n.name = '%s' "
                        + "    and r.test_run_id = %d ",
                dimension, tag.toLowerCase(), getId()))
                .columnMapping("distinct(c.id)", "id")
                .columnMapping("c.name", "name")
                .create());
    }

    public List<Context>
    getContextsNotExecuted(String dimension, String tag) {
        return Context.getContexts(RawSqlBuilder.parse(String.format(
                " select distinct c.id, c.name "
                        + "   from test_context c "
                        + "   join test_context_test_node cn "
                        + "     on cn.test_context_id = c.id "
                        + "   join test_node n "
                        + "     on cn.test_node_id = n.id "
                        + "   join test_dimension d "
                        + "     on n.dimension_id = d.id "
                        + "  where d.name = '%s' "
                        + "    and n.name = '%s' "
                        + "    and c.id not in ( "
                        + "        select c2.id "
                        + "          from test_context c2 "
                        + "          join test_result r "
                        + "            on r.context_id = c2.id "
                        + "           and r.test_run_id = %d "
                        + "         ) ",
                dimension, tag.toLowerCase(), getId()))
                .columnMapping("c.id", "id")
                .columnMapping("c.name", "name")
                .create());
    }
    
    public void setTestResults(List<TestResult> testResults) {
        this.testResults = testResults;
    }
    
    public String toString() { 
        return String.format("%s [%s]", 
            getSuite().getName(), getStartTime().toString());
    }
}

