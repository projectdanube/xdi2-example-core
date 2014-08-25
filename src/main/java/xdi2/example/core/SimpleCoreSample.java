package xdi2.example.core;

import xdi2.core.ContextNode;
import xdi2.core.Graph;
import xdi2.core.Literal;
import xdi2.core.Relation;
import xdi2.core.impl.memory.MemoryGraphFactory;
import xdi2.core.io.XDIWriterRegistry;
import xdi2.core.syntax.XDIAddress;
import xdi2.core.syntax.XDIArc;
import xdi2.core.syntax.XDIStatement;

public class SimpleCoreSample {

	public static void main(String[] args) throws Exception {

		// create a simple graph with context nodes, a relation, and a literal

		Graph graph = MemoryGraphFactory.getInstance().openGraph();

		ContextNode root = graph.getRootContextNode();
		ContextNode markus = root.setContextNode(XDIArc.create("=markus"));
		ContextNode animesh = root.setContextNode(XDIArc.create("=animesh"));
		ContextNode name = markus.setContextNode(XDIArc.create("<#name>"));
		ContextNode value = name.setContextNode(XDIArc.create("&"));
		Relation relation = markus.setRelation(XDIAddress.create("#friend"), animesh);
		Literal literal = value.setLiteral("Markus Sabadello");

		// write some statements from our graph

		System.out.println("Statement associated with a context node: " + markus.getStatement());
		System.out.println("Statement associated with a relation: " + relation.getStatement());
		System.out.println("Statement associated with a literal: " + literal.getStatement());
		System.out.println();

		// we can also add a whole new statement to the graph

		graph.setStatement(XDIStatement.create("=alice/#friend/=bob"));

		// write the whole graph in different serialization formats

		System.out.println("Serialization in XDI/JSON: \n");
		XDIWriterRegistry.forFormat("XDI/JSON", null).write(graph, System.out);
		System.out.println();
		System.out.println();

		System.out.println("Serialization in XDI statements:\n");
		XDIWriterRegistry.forFormat("XDI DISPLAY", null).write(graph, System.out);

		// close the graph

		graph.close();
	}
}
