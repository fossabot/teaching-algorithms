/*
 *
 *     Copyright (C) 2015-2016  Moritz Flöter
 *     Copyright (C) 2016  Jonathan Lechner
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package teachingalgorithms.ui.components;

import org.graphstream.graph.Element;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.swingViewer.ViewPanel;
import org.graphstream.ui.view.Viewer;
import org.jdesktop.swingx.JXPanel;
import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.logic.graph.util.Edge;
import teachingalgorithms.logic.graph.util.Node;

import java.awt.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.util.*;
import java.util.List;

/**
 * Use to display graphs by graphStream api.
 * @author Jonathan Lechner
 */
public class GraphPanel extends JXPanel {

    /**
     * will be removed
     */
    private static final int X_DIMENSION = 250;
    /**
     * will be removed
     */
    private static final int Y_DIMENSION = 200;

    private Graph graph;
    private Layout layout;

    /**
     * configures graph and its display
     * @param title name of graph.
     */
    public GraphPanel(String title) {
        super();
        graph = new MultiGraph(title);
        graph.addAttribute("ui.stylesheet", getGraphStyleSheet());
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");

        layout = new SpringBox(false);
        graph.addSink(layout);
        layout.addAttributeSink(graph);

        Viewer viewer = new Viewer(graph, Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
        viewer.enableAutoLayout();
        ViewPanel view = viewer.addDefaultView(false);
        view.setPreferredSize(this.getPreferredSize());
        this.add(view);
    }

    /**
     * sets or updates graph by adjacency matrix
     * @param matrix new values.
     */
    public void updateMatrix(AdjacencyMatrix matrix) {
        List<org.graphstream.graph.Node> nodes = new ArrayList<>();
        graph.getNodeIterator().forEachRemaining(nodes::add);
        List<Node> newNodes = new ArrayList<>(matrix.getNodes());

        for (org.graphstream.graph.Node node : nodes) {
            Optional<Node> nodeOptional = newNodes.stream().filter(n -> n.getName().equals(node.getId())).findAny();
            if (!(nodeOptional.isPresent())) {
                graph.removeNode(node);
            } else {
                setValuesToNode(nodeOptional.get(), node);
                newNodes.remove(nodeOptional.get());
            }
        }

        for (Node reallyNewNode : newNodes) {
            org.graphstream.graph.Node addedNode = graph.addNode(reallyNewNode.getName());
            setValuesToNode(reallyNewNode, addedNode);
        }

        for (List<Edge> adjaEdgeList : matrix.getEdges()) {
            for (Edge adjaEdge : adjaEdgeList) {
                String edgeId = getEdgeId(adjaEdge);
                org.graphstream.graph.Edge edge = graph.getEdge(edgeId);

                if (Objects.nonNull(adjaEdge.getWeight()) && Objects.isNull(edge)) {
                    edge = graph.addEdge(edgeId, adjaEdge.getFrom().getName(), adjaEdge.getTo().getName(), matrix.getDirected());
                    setValuesToEdge(adjaEdge, edge);
                } else if (Objects.nonNull(adjaEdge.getWeight()) && Objects.nonNull(edge)) {
                    setValuesToEdge(adjaEdge, edge);
                } else if (Objects.nonNull(edge)){
                    graph.removeEdge(edge);
                }
            }
        }

        while(layout.getStabilization() < 0.9){
            layout.compute();
        }
    }

    /**
     * loads StyleSheet from resource.
     * @return stylesheet
     */
    private String getGraphStyleSheet() {
        String style = "";
        InputStream input = null;
        try {
            input = (InputStream) this.getClass().getResource("graph.css").getContent();

            byte[] symbols = new byte[1024];

            int count;
            while((count = input.read(symbols)) != -1){
                style = new String(symbols, 0, count);
            }

        } catch (IOException exc) {
            // nothing to do here
        } finally {
            try {
                input.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return style;
    }

    private void setValuesToNode(Node adjaNode, org.graphstream.graph.Node node) {
        node.addAttribute("ui.label", adjaNode.getName());
        calculateVisitedColor(node, adjaNode.getVisited());
    }

    /**
     * set attributes to edge
     * @param adjaEdge new values
     * @param edge object to operate on.
     */
    private void setValuesToEdge(Edge adjaEdge, org.graphstream.graph.Edge edge) {
        edge.setAttribute("weight", adjaEdge.getWeight());
        edge.setAttribute("ui.label", adjaEdge.getWeight());
        calculateVisitedColor(edge, adjaEdge.getVisited());
    }

    private void calculateVisitedColor(Element element, Integer visitedCount) {
        if (visitedCount > 0) {
            int red = 255 * (visitedCount / 100);
            int green = 255 - (255 * (visitedCount / 100));
            element.setAttribute("ui.style", "fill-color: rgb(" + red + ", " + green + ", 0);");
        } else {
            element.removeAttribute("ui.style");
        }
    }

    private String getEdgeId(Edge edge) {
        return edge.getFrom().getName() + "-" + edge.getTo().getName();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(X_DIMENSION, Y_DIMENSION);
    }
}
