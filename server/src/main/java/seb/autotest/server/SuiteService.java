package seb.autotest.server;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import com.avaje.ebean.Ebean;
import org.springframework.stereotype.Controller;
import seb.autotest.server.models.SuiteDefinition;

import javax.ws.rs.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: s45875
 * Date: 2013-10-04
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
public class SuiteService {

    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
    public List<SuiteDefinition> getSuites(ExtDirectStoreReadRequest request) {
        List<SuiteDefinition> suites = new ArrayList<SuiteDefinition>();
        for (SuiteDefinition s : Ebean.find(SuiteDefinition.class).findList())
        {
            SuiteDefinition s1 = new SuiteDefinition();
            s1.setId(s.getId());
            s1.setName(s.getName());
            s1.setLastUpdate(s.getLastUpdate());
            suites.add(s1);
        }

        return suites;

    }
}
