package seb.autotest.server.models;

import ch.ralscha.extdirectspring.generator.ModelGenerator;
import ch.ralscha.extdirectspring.generator.OutputFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class ModelController {

    @RequestMapping("/autotest/model/SuiteDefinition.js")
    public void user(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, SuiteDefinition.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/TestResult.js")
    public void testResult(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, TestResult.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/TestRun.js")
    public void testRun(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, TestRun.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/Environment.js")
    public void environment(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, Environment.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/Context.js")
    public void context(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, Context.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/system.js")
    public void system(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, System.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/teststatistics.js")
    public void teststatistics(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, TestStatistics.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/sequence.js")
    public void sequence(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, Sequence.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/node.js")
    public void node(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, Node.class, OutputFormat.EXTJS4);
    }

    @RequestMapping("/autotest/model/dimension.js")
    public void dimension(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ModelGenerator.writeModel(request, response, Dimension.class, OutputFormat.EXTJS4);
    }

}
