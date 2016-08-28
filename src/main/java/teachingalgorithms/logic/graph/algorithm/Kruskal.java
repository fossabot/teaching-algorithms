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

package teachingalgorithms.logic.graph.algorithm;

import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.logic.graph.protocol.step.Step;
import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.logic.graph.util.Edge;
import teachingalgorithms.logic.graph.util.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * stepByStep commented kruskal.
 * @author Jonathan Lechner
 */
public class Kruskal implements GraphAlgorithm {

    public static final String NODE_LIST = "node_list";

    public static final String CURRENT_EDGE = "current_edge";

    public static final String IS_SELECTED = "is_selected";

    public static final String SORTED_EDGES = "sorted_edges";

    private StepByStepProtocol stepByStepProtocol;

    private AdjacencyMatrix adjacencyMatrix;

    @Override
    public StepByStepProtocol applyAlgorithm(AdjacencyMatrix matrix) {
        adjacencyMatrix = matrix.clone();

        List<Edge> sortedEdges = new ArrayList<>();
        adjacencyMatrix.getEdges().forEach(
                edges -> sortedEdges.addAll(edges.stream().filter(
                        edge -> Objects.nonNull(edge.getWeight())).collect(Collectors.toList())
                )
        );
        Collections.sort(sortedEdges);

        List<List<Node>> visitedNodes = new ArrayList<>();
        adjacencyMatrix.getNodes().forEach(node -> {
            List<Node> nList = new ArrayList<>();
            nList.add(node);
            visitedNodes.add(nList);
        });

        stepByStepProtocol = new StepByStepProtocol();

        Step sortedEdgesStep = new Step(adjacencyMatrix.clone());
        List<Edge> clonedEdgeList = new ArrayList<>();
        clonedEdgeList.addAll(sortedEdges);
        sortedEdgesStep.addAdditionalInformation(SORTED_EDGES, clonedEdgeList);
        stepByStepProtocol.add(sortedEdgesStep);

        kruskal(sortedEdges, visitedNodes);
        return stepByStepProtocol;
    }

    public static String getName() {
        return GraphAlgorithm.getName() + ".kruskal";
    }

    /**
     * runs kruskal and generates stepByStep
     * @param remainingSortedEdges sorted edges.
     * @param visitedNodes node heap.
     */
    private void kruskal(List<Edge> remainingSortedEdges, List<List<Node>> visitedNodes) {
        while (visitedNodes.size() > 1 && remainingSortedEdges.size() > 0) {
            Edge edge = remainingSortedEdges.get(0);
            edge.setVisited(100);
            remainingSortedEdges.remove(0);

            Node from = edge.getFrom();
            Node to = edge.getTo();

            if (Objects.nonNull(from) && Objects.nonNull(to)) {
                List<Integer> containing = new ArrayList<>();

                for (List<Node> connectedNodes : visitedNodes) {
                    if (connectedNodes.contains(from) || connectedNodes.contains(to)) {
                        containing.add(visitedNodes.indexOf(connectedNodes));
                    }
                }

                if (containing.size() == 2) {
                    edge.setVisited(1);
                    //TODO description if needed
                    stepByStepProtocol.add(getNewStepFor(adjacencyMatrix.clone(), visitedNodes, edge, true));

                    //copy cascading list
                    List<List<Node>> newVisitedNodes = new ArrayList<>();
                    for (List<Node> list : visitedNodes) {
                        newVisitedNodes.add(new ArrayList<>(list));
                    }
                    visitedNodes = newVisitedNodes;

                    List<Node> toConnectTo = visitedNodes.get(containing.get(0));
                    List<Node> other = visitedNodes.get(containing.get(1));
                    visitedNodes.remove((int) containing.get(1));

                    toConnectTo.addAll(other);
                } else {
                    //TODO description if needed
                    stepByStepProtocol.add(getNewStepFor(adjacencyMatrix.clone(), visitedNodes, edge, false));
                }
            }
        }

        if (remainingSortedEdges.size() > 0) {
            Edge edge = remainingSortedEdges.get(0);
            remainingSortedEdges.remove(0);

            //TODO description if needed
            stepByStepProtocol.add(getNewStepFor(adjacencyMatrix.clone(), visitedNodes, edge, "*"));
        } else {
            //TODO add last row if there are no left edges
        }
        while (remainingSortedEdges.size() > 0) {
            Edge edge = remainingSortedEdges.get(0);
            remainingSortedEdges.remove(0);

            //TODO description if needed
            stepByStepProtocol.add(getNewStepFor(adjacencyMatrix.clone(), null, edge, null));
        }
    }

    private Step getNewStepFor(AdjacencyMatrix matrix, List<List<Node>> nodeList, Edge edge, Object selected) {
        Step toReturn = new Step(matrix);
        toReturn.addAdditionalInformation(NODE_LIST, nodeList);
        toReturn.addAdditionalInformation(CURRENT_EDGE, edge);
        toReturn.addAdditionalInformation(IS_SELECTED, selected);
        return toReturn;
    }
}
