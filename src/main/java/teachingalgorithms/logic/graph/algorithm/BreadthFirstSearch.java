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

import java.util.*;

/**
 * stepByStep commented breadth first search.
 * @author Jonathan Lechner
 */
public class BreadthFirstSearch implements GraphAlgorithm {

    private StepByStepProtocol stepByStepProtocol;

    private AdjacencyMatrix adjacencyMatrix;

    @Override
    public StepByStepProtocol applyAlgorithm(AdjacencyMatrix matrix) {
        this.adjacencyMatrix = matrix.clone();
        this.stepByStepProtocol = new StepByStepProtocol();

        List<Node> nodeHeap = new ArrayList<>();
        nodeHeap.add(adjacencyMatrix.getNodes().get(0));

        Map<Node, Integer> depths = new HashMap<>();
        depths.put(adjacencyMatrix.getNodes().get(0), 0);

        breadthFirstSearch(nodeHeap, depths);

        return this.stepByStepProtocol;
    }

    /**
     * this method runs the breadth first search for both directed and non-directed graphs
     * and sets the step by step.
     * @param nodeHeap list of current Nodes.
     * @param depths map of depths node first reached.
     */
    private void breadthFirstSearch(List<Node> nodeHeap, Map<Node, Integer> depths) {
        Step newStep;
        Integer y = 0;
        while (nodeHeap.size() > 0) {
            adjacencyMatrix.getNodes().get(y).setVisited(1);
            newStep = new Step(this.adjacencyMatrix.clone());
            newStep.addAdditionalInformation(DepthFirstSearch.NODE_HEAP, new ArrayList<>(nodeHeap));
            this.stepByStepProtocol.add(newStep);

            for (int x = 0; x < adjacencyMatrix.getNodes().size(); x++) {
                boolean directed = adjacencyMatrix.getDirected();
                Edge currentEdge = adjacencyMatrix.getEdges().get(x).get(y);
                Edge wrongDirectedEdge = adjacencyMatrix.getEdges().get(y).get(x);
                int currentDepth = depths.get(currentEdge.getTo());
                if (Objects.nonNull(currentEdge.getWeight())
                        || (directed || Objects.nonNull(wrongDirectedEdge.getWeight()))) {
                    Node from = currentEdge.getFrom();
                    if (!nodeHeap.contains(from) && from.getVisited() == 0) {
                        nodeHeap.add(from);
                        depths.put(from , currentDepth + 1);

                        newStep = new Step(this.adjacencyMatrix.clone());
                        newStep.addAdditionalInformation(DepthFirstSearch.NODE_HEAP, new ArrayList<>(nodeHeap));
                        newStep.addAdditionalInformation(DepthFirstSearch.DEPTH, currentDepth + 1);
                        this.stepByStepProtocol.add(newStep);
                    }
                }
            }
            nodeHeap.remove(0);
            y = nodeHeap.size() > 0 ? adjacencyMatrix.getNodes().indexOf(nodeHeap.get(0)) : null;
        }
        newStep = new Step(this.adjacencyMatrix.clone());
        newStep.addAdditionalInformation(DepthFirstSearch.NODE_HEAP, null);
        this.stepByStepProtocol.add(newStep);
    }

    public static String getName() {
        return GraphAlgorithm.getName() + ".breadthFirstSearch";
    }
}
