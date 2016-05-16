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
 * represents node of graph.
 * @author Jonathan Lechner
 */
public class Node implements Cloneable {

    private static final String ALLOWED_NAME_PATTERN = "([A-Z]+[0-9]*)";

    /**
     * name of node
     */
    private String name;

    /**
     * can store how many times the node was visited
     */
    private Integer visited;

    /**
     * constructs new node.
     * @param name name of node (can't be changed).
     * @throws IllegalArgumentException if name does not match pattern.
     */
    public Node(String name) {
        if (name.matches(ALLOWED_NAME_PATTERN)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException(name + " does not match " + ALLOWED_NAME_PATTERN);
        }
        this.visited = 0;
    }

    public String getName() {
        return name;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    @Override
    public Node clone() {
        Cloner cloner = new Cloner();
        return cloner.deepClone(this);
    }
}
