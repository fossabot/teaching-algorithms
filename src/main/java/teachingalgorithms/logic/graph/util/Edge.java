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

/**
 * represents edge of graph.
 * @author Jonathan Lechner
 */
public class Edge implements Comparable<Edge>, Cloneable {

    private Node from;

    private Node to;

    private Integer weight;

    private Integer visited;

    /**
     * constructs edge
     * @param from outgoing node.
     * @param to ingoing node
     */
    public Edge(Node from, Node to) {
        this.from = from;
        this.to = to;
        this.visited = 0;
        weight = null;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getWeight() {
        return weight;
    }

    public Node getFrom() {
        return from;
    }

    public Node getTo() {
        return to;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    @Override
    public int compareTo(Edge edge) {
        try {
            int comp = this.getWeight().compareTo(edge.getWeight());
            return comp == 0 ? -1 : comp;
        } catch (NullPointerException exc) {
            return -1;
        }
    }

    @Override
    public Edge clone() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(this);
    }
}
