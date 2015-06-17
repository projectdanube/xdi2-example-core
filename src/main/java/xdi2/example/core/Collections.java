package xdi2.example.core;

import java.io.StringReader;
import java.util.Iterator;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.features.nodetypes.XdiAbstractContext;
import xdi2.core.features.nodetypes.XdiAttributeCollection;
import xdi2.core.features.nodetypes.XdiAttributeInstanceUnordered;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.MimeType;
import xdi2.core.io.XDIReaderRegistry;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.syntax.XDIArc;

public class Collections {

	public static void main(String[] args) throws Exception {

		// create and print a graph with a collection

		Graph graph = MemoryGraphFactory.getInstance().openGraph();
		ContextNode contextNode = graph.getRootContextNode().setContextNode(XDIArc.create("=markus"));

		XdiAttributeCollection telAttributeCollection = XdiAbstractContext.fromContextNode(contextNode).getXdiAttributeCollection(XDIArc.create("[<#tel>]"), true);
		telAttributeCollection.setXdiInstanceUnordered(true, false).setLiteralDataString("+1.206.555.1111");
		telAttributeCollection.setXdiInstanceUnordered(true, false).setLiteralDataString("+1.206.555.2222");

		System.out.println(graph.toString(new MimeType("application/xdi+json;pretty=1")));

		// write and re-read the graph, then find and print the members of the attribute collection

		Graph graph2 = MemoryGraphFactory.getInstance().openGraph();
		XDIReaderRegistry.getAuto().read(graph2, new StringReader(graph.toString()));
		ContextNode contextNode2 = graph.getDeepContextNode(XDIAddress.create("=markus"));

		XdiAttributeCollection telCollection2 = XdiAbstractContext.fromContextNode(contextNode2).getXdiAttributeCollection(XDIArc.create("[<#tel>]"), false);

		for (Iterator<XdiAttributeInstanceUnordered> i = telCollection2.getXdiInstancesUnordered(); i.hasNext(); ) {

			System.out.println(i.next().getLiteralNode().getLiteralData());
		}
	}
}
