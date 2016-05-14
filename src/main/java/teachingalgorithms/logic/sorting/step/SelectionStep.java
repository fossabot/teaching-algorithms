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

/**
 * @author Moritz Floeter
 */
public class SelectionStep extends SortingStep {

    /**
     * The array.
     */
    int[] array;
    /**
     * The sorted until position marker.
     */
    int sortedUntilPositionMarker;
    /**
     * The searching position marker.
     */
    int searchingPositionMarker;
    /**
     * The min position.
     */
    int minPosition;
    /**
     * The explanation.
     */
    private String explanation = "";

    /**
     * Instantiates a new selection step.
     *
     * @param array                     the array
     * @param sortedUntilPositionMarker the sorted until position marker
     * @param searchingPositionMarker   the searching position marker
     * @param minPosition               the min position
     */
    public SelectionStep(int[] array, int sortedUntilPositionMarker, int searchingPositionMarker, int minPosition) {
        super();
        this.array = array;
        this.sortedUntilPositionMarker = sortedUntilPositionMarker;
        this.searchingPositionMarker = searchingPositionMarker;
        this.minPosition = minPosition;
    }

    /**
     * Instantiates a new selection step.
     */
    public SelectionStep() {
        // TODO
    }

    /**
     * Gets the array.
     *
     * @return the array
     */
    public int[] getArray() {
        return array;
    }

    /**
     * Sets the array.
     *
     * @param array the new array
     */
    public void setArray(int[] array) {
        this.array = array;
    }

    /**
     * Gets the sorted until position marker.
     *
     * @return the sorted until position marker
     */
    public int getSortedUntilPositionMarker() {
        return sortedUntilPositionMarker;
    }

    /**
     * Sets the sorted until position marker.
     *
     * @param sortedUntilPositionMarker the new sorted until position marker
     */
    public void setSortedUntilPositionMarker(int sortedUntilPositionMarker) {
        this.sortedUntilPositionMarker = sortedUntilPositionMarker;
    }

    /**
     * Gets the searching position marker.
     *
     * @return the searching position marker
     */
    public int getSearchingPositionMarker() {
        return searchingPositionMarker;
    }

    /**
     * Sets the searching position marker.
     *
     * @param searchingPositionMarker the new searching position marker
     */
    public void setSearchingPositionMarker(int searchingPositionMarker) {
        this.searchingPositionMarker = searchingPositionMarker;
    }

    /**
     * Gets the min position.
     *
     * @return the min position
     */
    public int getMinPosition() {
        return minPosition;
    }

    /**
     * Sets the min position.
     *
     * @param minPosition the new min position
     */
    public void setMinPosition(int minPosition) {
        this.minPosition = minPosition;
    }

    /**
     * Gets the explanation.
     *
     * @return the explanation
     */
    public String getExplanation() {
        return this.explanation;
    }

    /**
     * Add2 explanation.
     *
     * @param text the text
     */
    public void add2Explanation(String text) {
        this.explanation += text;
    }


}
