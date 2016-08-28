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
import teachingalgorithms.logic.graph.util.Node;

import java.util.*;

public class Dijkstra implements GraphAlgorithm {

    public static final String CURRENT_NODE = "current_node";

    public static final String BORDER_NODES = "border_nodes";

    public static final String UNVISITED_NODES = "unvisited_nodes";

    public static final String CURRENT_DISTANCES = "current_distances";

    public static final String PREVIOUS_NODES = "previous_nodes";

    private StepByStepProtocol stepByStepProtocol;

    private AdjacencyMatrix adjacencyMatrix;

    //distances from source (first node) to specific node
    private Map<Node, Long> distances;

    //previous Node defines path through graph
    private Map<Node, Node> previous;

    private List<Node> border;

    private List<Node> unvisited;

    @Override
    public StepByStepProtocol applyAlgorithm(AdjacencyMatrix matrix) {
        this.adjacencyMatrix = matrix.clone();
        this.stepByStepProtocol = new StepByStepProtocol();

        Node source = this.adjacencyMatrix.getNodes().get(0);

        distances = new HashMap<>();
        previous = new HashMap<>();
        border = new ArrayList<>();

        unvisited = new ArrayList<>();
        this.adjacencyMatrix.getNodes().forEach(node -> unvisited.add(node));

        daijkstra(source);

        return this.stepByStepProtocol;
    }

    private void daijkstra(Node source) {
        Node currentNode = source;
        Long currentDistance = 0L;
        this.distances.put(currentNode, currentDistance);
        this.previous.put(currentNode, currentNode);

        do {
            final Node cNode = currentNode;
            this.adjacencyMatrix.getEdges().forEach(edges -> edges.stream()
                    .filter(possibleNeighbor -> (Objects.nonNull(possibleNeighbor.getWeight())) && (possibleNeighbor.getFrom().equals(cNode) || possibleNeighbor.getTo().equals(cNode))).forEach(
                        neighbor -> {
                            Long newDistance;
                            if (this.adjacencyMatrix.getDirected() || cNode.equals(neighbor.getFrom())) {
                                newDistance = this.distances.get(neighbor.getFrom());

                                newDistance = newDistance + neighbor.getWeight();
                                if (this.distances.containsKey(neighbor.getTo())) {
                                    if (this.distances.get(neighbor.getTo()) > newDistance) {
                                        this.distances.put(neighbor.getTo(), newDistance);
                                        this.previous.put(neighbor.getTo(), neighbor.getFrom());
                                    }
                                } else {
                                    border.add(neighbor.getTo());
                                    this.distances.put(neighbor.getTo(), newDistance);
                                    this.previous.put(neighbor.getTo(), neighbor.getFrom());
                                }
                            } else {
                                newDistance = this.distances.get(neighbor.getTo());

                                newDistance = newDistance + neighbor.getWeight();
                                if (this.distances.containsKey(neighbor.getFrom())) {
                                    if (this.distances.get(neighbor.getFrom()) > newDistance) {
                                        this.distances.put(neighbor.getFrom(), newDistance);
                                        this.previous.put(neighbor.getFrom(), neighbor.getTo());
                                    }
                                } else {
                                    border.add(neighbor.getFrom());
                                    this.distances.put(neighbor.getFrom(), newDistance);
                                    this.previous.put(neighbor.getFrom(), neighbor.getTo());
                                }
                            }
                        }));

            currentNode.setVisited(currentNode.getVisited() + 1);
            this.border.remove(currentNode);
            this.unvisited.remove(currentNode);

            stepByStepProtocol.add(getAsStep(currentNode));

            Long minDist = Long.MAX_VALUE;
            for (Node possibleMinDistanceNode : this.border) {
                Long distanceTillNow = this.distances.get(possibleMinDistanceNode);
                if (distanceTillNow < minDist) {
                    minDist = distanceTillNow;
                    currentNode = possibleMinDistanceNode;
                }
            }
        } while (border.size() > 0);
    }

    private Step getAsStep(Node currentNode) {
        Step step = new Step(adjacencyMatrix.clone());
        step.addAdditionalInformation(CURRENT_NODE, currentNode);
        step.addAdditionalInformation(BORDER_NODES, new ArrayList<>(this.border));
        step.addAdditionalInformation(UNVISITED_NODES, new ArrayList<>(this.unvisited));
        step.addAdditionalInformation(CURRENT_DISTANCES, new HashMap<>(this.distances));
        step.addAdditionalInformation(PREVIOUS_NODES, new HashMap<>(this.previous));
        return step;
    }

    public static String getName() {
        return GraphAlgorithm.getName() + ".dijkstra";
    }
}
