package seb.autotest.server.models;

import com.avaje.ebean.Ebean;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Parses test descriptions ("specs", or "contexts") into an object model. 
 * 
 * A URI-formatted description looks like this:
 * 
 *      "suite:/fixtures/trade/events/early-termination/FXOB/"
 *      + "?name=barrier-double-early-termination-cash-european" 
 *      + "&files=opt_882617.xml,betm_882617.xml"
 *      + "&source=MUREX"
 *      + "&classes=incomingtrade.parser.mxml.TestTradeImport"
 *              + ":incomingtrade.parser.TestTradeWorkflow"
 *              + ",incomingtrade.parser.mxml.TestEventImport"
 *              + ":incomingtrade.parser.TestTradeTerminated",
 * 
 * Alternatively, test descriptions can be loaded from a database using Ebean.
 * 
 * @author Stefan Sigurdsson, s44410.
 */
public class ContextFactory {
	
	public Context[]
	create(String[] specs) {
		URI[] uris = new URI[specs.length];
		for (int i = 0; i < specs.length; ++i)
			try {
				uris[i] = new URI(specs[i]);
			} catch (URISyntaxException x) {
				throw new RuntimeException(String.format(
					"Unable to parse test URI: %s", specs[i]));
			}
		Context[] contexts = new Context[uris.length];
		for (int i = 0; i < uris.length; ++i) 
			contexts[i] = create(uris[i]);
		return contexts;
	}
	
	public Context
	create(URI uri) {
	    Context context = new Context();
	    initialize(context, uri);
	    Ebean.save(context);
	    return context;
	}
	
	public void 
	initialize(Context context, String uri) throws URISyntaxException {
	    initialize(context, new URI(uri));
	}
	
	public void 
	initialize(Context context, URI uri) {
        context.setFixturePath(uri.getPath());
        for (String param: uri.getQuery().split("&")) {
            String[] pair = param.split("=");
            if (2 > pair.length)
                continue;
            if ("name".equals(pair[0]))
                context.setName(pair[1]);
            else if ("files".equals(pair[0]))
                context.setFiles(pair[1]);
            // TODO: Support for creating sequences from URI is disabled:
            // (Class list was removed from context, replaced with Sequence.)
            // else if ("classes".equals(pair[0]))
            //    context.setClasses(pair[1]);
            else if ("source".equals(pair[0])) {
                context.setSource(Ebean.find(System.class)
                    .where().eq("name", pair[1]).findUnique());
            } else
                throw new IllegalArgumentException(String.format(
                    "Unexpected: %s (%s)", pair[0], pair[1]));
        }
	}
	
	public void 
	initialize(Context context, Context original) {
	    try {
	        initialize(context, original.getUri());
	    } catch (URISyntaxException x) {
	        // This should not happen; the original should hold a valid URI:
	        throw new RuntimeException(x);
	    }
        context.getRewriters().addAll(original.getRewriters());
	}
	
    public static URI createUri(Context context) throws ClassNotFoundException {
        String path = context.getFixturePath();
        if (null == path || "".equals(path))
            path = "/";
        String name = context.getName();
        StringBuffer classes = new StringBuffer();
        StringBuffer files = new StringBuffer();
        Collection<Sequence> sequences = context.getSequences();
        for (Sequence sequence: sequences) {
            if (0 < files.length()) {
                files.append(",");
                classes.append(",");
            }
            files.append(sequence.getInputPath());
            for (Class<?> element: sequence.getClasses()) {
                if (0 < classes.length() &&
                    !",".equals(classes.substring(classes.length() - 1)))
                    classes.append(":");
                classes.append(
                    element.getCanonicalName().substring(
                        Sequence.PACKAGE_PREFIX.length()));
            }
        }
        StringBuffer uri = new StringBuffer(String.format(
            "suite:%s?name=%s%s%s", 
            path, name, 
            (0 < files.length()) ? "&files=" + files.toString() : "", 
            (0 < classes.length()) ? "&classes=" + classes.toString() : ""));
        if (null != context.getSource())
            uri.append(String.format("&source=%s", context.getSource()));
        return URI.create(uri.toString());
    }
    
    public static List<Node> findNodes(String name) {
        String[] nodeNames = name.split("-");
        List<Node> nodes = new ArrayList<Node>();
        for (String nodeName: nodeNames) {
            Node node = Ebean.find(Node.class).where().eq("name", nodeName).findUnique();
            if (null == node)
                throw new IllegalArgumentException(String.format(
                    "No such node: %s", nodeName));
            nodes.add(node);
        }
        return nodes;
    }
}

