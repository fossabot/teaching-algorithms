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

package teachingalgorithms.logic.graph.util;

import com.rits.cloning.Cloner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * represents graph.
 * @author Jonathan Lechner
 */
public class AdjacencyMatrix implements Serializable {

    private Boolean directed;

    private List<Node> nodes;

    private List<List<Edge>> edges;

    public AdjacencyMatrix() {
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
        directed = false;
    }

    /**
     * also adds edges related.
     * @param node
     */
    public void addNode(Node node) {
        nodes.add(node);

        edges.add(new ArrayList<>());
        for (int i = 0; i < nodes.size() - 1; i++) {
            Edge edge = new Edge(nodes.get(i), node);
            edges.get(i).add(edge);
        }

        for (int i = 0; i < nodes.size(); i++) {
            Edge edge = new Edge(node, nodes.get(i));
            edges.get(nodes.size() - 1).add(edge);
        }
    }

    /**
     * also remove edges related.
     * @param node
     */
    public void removeNode(Node node) {
        int index = nodes.indexOf(node);
        if (index >= 0) {
            nodes.remove(index);
            edges.remove(index);
            for (List<Edge> fromList: edges) {
                fromList.remove(index);
            }
        }
    }

    public Boolean getDirected() {
        return directed;
    }

    public void setDirected(Boolean directed) {
        this.directed = directed;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<List<Edge>> getEdges() {
        return edges;
    }

    @Override
    public AdjacencyMatrix clone() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(this);
    }
}
