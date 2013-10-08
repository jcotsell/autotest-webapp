package seb.autotest.server;

import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import seb.autotest.server.models.SuiteDefinition;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class Generator {

    @RequestMapping("app/model/Suite.js")
    public void user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, SuiteDefinition.class, OutputFormat.EXTJS4);

    }
}