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
import java.util.List;
import java.util.Objects;

/**
 * stepByStep commented depth first search.
 * @author Jonathan Lechner
 */
public class DepthFirstSearch implements GraphAlgorithm {

    public static final String NODE_HEAP = "node_heap";

    public static final String DEPTH = "depth";

    private StepByStepProtocol stepByStepProtocol;

    private AdjacencyMatrix adjacencyMatrix;

    @Override
    public StepByStepProtocol applyAlgorithm(AdjacencyMatrix matrix) {
        this.adjacencyMatrix = matrix.clone();
        this.stepByStepProtocol = new StepByStepProtocol();

        List<Node> nodeHeap = new ArrayList<>();
        nodeHeap.add(adjacencyMatrix.getNodes().get(0));
        nodeHeap.get(0).setVisited(1);
        depthFirstSearch(nodeHeap);

        return this.stepByStepProtocol;
    }

    /**
     * this method runs the depth first search for both directed and non-directed graphs
     * and sets the step by step.
     * @param nodeHeap list of visited nodes.
     */
    private void depthFirstSearch(List<Node> nodeHeap) {
        int depth = 0;
        Integer y = 0;
        Step newStep;
        while (nodeHeap.size() > 0){
            newStep = new Step(this.adjacencyMatrix.clone());
            newStep.addAdditionalInformation(NODE_HEAP, new ArrayList<>(nodeHeap));
            if (depth == 0) {
                newStep.addAdditionalInformation(DEPTH,
                        //following line should be split in depth and current node
                        "TSN(" + adjacencyMatrix.getEdges().get(0).get(y).getFrom().getName() + ") = " + (depth + 1));
            }

            this.stepByStepProtocol.add(newStep);

            for (int x = 0; x < adjacencyMatrix.getNodes().size(); x++) {
                boolean directed = adjacencyMatrix.getDirected();
                Edge currentEdge = adjacencyMatrix.getEdges().get(x).get(y);
                Edge wrongDirectedEdge = adjacencyMatrix.getEdges().get(y).get(x);
                if (Objects.nonNull(currentEdge.getWeight())
                        || (directed || Objects.nonNull(wrongDirectedEdge.getWeight()))) {
                    Node from = currentEdge.getFrom();
                    if (!nodeHeap.contains(from) && from.getVisited() == 0) {
                        nodeHeap.add(from);
                        from.setVisited(1);
                        if (Objects.nonNull(currentEdge.getWeight())) {
                            currentEdge.setVisited(1);
                        } else {
                            wrongDirectedEdge.setVisited(1);
                        }

                        y = x;
                        x = 0;
                        depth++;

                        newStep = new Step(this.adjacencyMatrix.clone());
                        newStep.addAdditionalInformation(NODE_HEAP, new ArrayList<>(nodeHeap));
                        newStep.addAdditionalInformation(DEPTH,
                                //following line should be split in depth and current node
                                "TSN(" + currentEdge.getFrom().getName() + ") = " + (depth + 1));
                        this.stepByStepProtocol.add(newStep);
                    }
                }
            }

            nodeHeap.remove(nodeHeap.size() - 1);
            y = nodeHeap.size() > 0 ? adjacencyMatrix.getNodes().indexOf(nodeHeap.get(nodeHeap.size() - 1)) : null;
        }
        newStep = new Step(this.adjacencyMatrix.clone());
        newStep.addAdditionalInformation(NODE_HEAP, null);
        this.stepByStepProtocol.add(newStep);
    }

    public static String getName() {
        return GraphAlgorithm.getName() + ".depthFirstSearch";
    }
}
