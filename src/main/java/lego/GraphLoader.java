package lego;

import org.neo4j.graphdb.*;

/**
 * Interface for providing {@link Graph}.
 */
public class GraphLoader {
    private GraphDatabaseService db;
    private ResourceIterable<Relationship> relationshipResourceIterator;
    private String label = null;
    private Graph graph = new Graph();

    public GraphLoader(GraphDatabaseService db, String label) {
        this.label = label;

        try (Transaction tx = db.beginTx()) {
            for (Relationship relationship : db.getAllRelationships()) {
                final Node startNode = relationship.getStartNode();
                final Node endNode = relationship.getEndNode();
                if (hasLabel(startNode) && hasLabel(endNode)) {
                    this.graph.addEdge(new Edge(startNode.getId(), endNode.getId()));
                }
            }
            tx.close();
        } catch (Exception e) {
            throw e;
        }
    }

    public GraphLoader(ResourceIterable<Relationship> relationshipResourceIterator, String label) {
        this.label = label;
        for (Relationship relationship : relationshipResourceIterator) {
            final Node startNode = relationship.getStartNode();
            final Node endNode = relationship.getEndNode();
            if (hasLabel(startNode) && hasLabel(endNode)) {
                this.graph.addEdge(new Edge(startNode.getId(), endNode.getId()));
            }

        }
    }

    public GraphLoader withLabel(String label) {
        this.label = label;
        return this;
    }

    public String getLabel() {
        return label;
    }

    /**
     * Load graph from db. Based on testing all relationships in a graph.
     * Needs the relationships iterator for work.
     *
     * @return Graph
     */
    public Graph load() {
//        try (Transaction tx = api.beginTx()) {
//            for(Relationship relationship : api.getAllRelationships()) {
//                final Node startNode = relationship.getStartNode();
//                final Node endNode = relationship.getEndNode();
//                if (hasLabel(startNode) && hasLabel(endNode)) {
//                    this.graph.addEdge(new Edge(startNode.getId(), endNode.getId()));
//                }
//            }
//            tx.success();
//            return this.graph;
//        } catch (Exception e) {
//            throw e;
//        }
//        try (Transaction tx = db.beginTx()) {
//        for (Relationship relationship : relationshipResourceIterator) {
//            final Node startNode = relationship.getStartNode();
//            final Node endNode = relationship.getEndNode();
//            if (hasLabel(startNode) && hasLabel(endNode)) {
//                this.graph.addEdge(new Edge(startNode.getId(), endNode.getId()));
//            }
//
//        }
//            for (Relationship relationship : db.getAllRelationships()) {
//                final Node startNode = relationship.getStartNode();
//                final Node endNode = relationship.getEndNode();
//                if (hasLabel(startNode) && hasLabel(endNode)) {
//                    this.graph.addEdge(new Edge(startNode.getId(), endNode.getId()));
//                }
//            }
//            for (ResourceIterator<Node> it = db.findNodes(this::getLabel); it.hasNext(); ) {
//                Node node = it.next();
//                node.getRelationships().forEach(relationship -> {
//                    final Node otherNode = relationship.getOtherNode(node);
//                    if (hasLabel(otherNode)) {
//                        this.graph.addEdge(new Edge(node.getId(), otherNode.getId()));
//                    }
//                });
//            }
//            tx.success();
        return this.graph;
//        } catch (Exception e) {
//            throw e;
//        }

    }

    /**
     * Check that node has label
     *
     * @param node Node for check
     * @return Returns true if node has label
     */
    private boolean hasLabel(Node node) {
        if (label.isEmpty()) {
            return true;
        }
        for (Object o : node.getLabels()) {
            if (o.toString().equals(label)) {
                return true;
            }
        }
        return false;
    }
}
