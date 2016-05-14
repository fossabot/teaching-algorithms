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

package teachingalgorithms.logic.sorting.step;

import java.util.ArrayList;

/**
 * @author Moritz Floeter
 */
public class RadixStep extends SortingStep {

    /**
     * The post boxes.
     */
    private ArrayList<ArrayList<String>> postBoxes = new ArrayList<ArrayList<String>>(10);

    /**
     * The explanation.
     */
    private String explanation = "";

    /**
     * The memory.
     */
    private int memory = 0;

    /**
     * Instantiates a new radix step.
     */
    public RadixStep() {
        for (int i = 0; i <= 9; i++) {
            postBoxes.add(new ArrayList<String>());
        }
    }

    /**
     * Gets one of the boxes that stands for one of the numbers 0-9.
     * The value passed to this function must be 0-9
     *
     * @param i the i
     * @return the post box
     */
    public ArrayList<String> getPostBox(int i) {
        return postBoxes.get(i);
    }

    /**
     * Collect boxes.
     *
     * @return the array list
     */
    public ArrayList<String> collectBoxes() {
        ArrayList<String> collectedBoxes = new ArrayList<String>();
        for (int i = 0; i < postBoxes.size(); i++) {
            for (int j = 0; j < postBoxes.get(i).size(); j++) {
                collectedBoxes.add(postBoxes.get(i).get(j));
            }
        }
        return collectedBoxes;
    }

    /**
     * Do step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        boolean stepDone = false;

        return stepDone;
    }

    /**
     * Gets the memory.
     *
     * @return the memory
     */
    public int getMemory() {
        return memory;
    }

    /**
     * Sets the memory.
     *
     * @param memory the new memory
     */
    public void setMemory(int memory) {
        this.memory = memory;
    }

    /**
     * Adds the String passed to this function to explanation.
     *
     * @param explanation2add the explanation2add
     */
    public void add2Explanation(String explanation2add) {
        this.explanation += explanation2add;
    }

    /**
     * Gets the explanation.
     *
     * @return the explanation
     */
    public String getExplanation() {
        // TODO Auto-generated method stub
        return this.explanation;
    }
}
