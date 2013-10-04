package seb.autotest.server;

import ch.ralscha.extdirectspring.annotation.ExtDirectMethod;
import ch.ralscha.extdirectspring.annotation.ExtDirectMethodType;
import ch.ralscha.extdirectspring.bean.ExtDirectStoreReadRequest;
import com.avaje.ebean.Ebean;
import org.springframework.stereotype.Controller;
import seb.autotest.server.models.SuiteDefinition;

import javax.ws.rs.Path;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: s45875
 * Date: 2013-10-04
 * Time: 13:31
 * To change this template use File | Settings | File Templates.
 */
@Controller
@Path("/suite")
public class SuiteService {

    @ExtDirectMethod(ExtDirectMethodType.STORE_READ)
    public List<SuiteDefinition> getSource(ExtDirectStoreReadRequest request) {
        return Ebean.find(SuiteDefinition.class).findList();

    }

}
