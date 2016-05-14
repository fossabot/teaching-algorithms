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

package teachingalgorithms.logic.sorting;

import teachingalgorithms.logic.sorting.step.SelectionStep;
import teachingalgorithms.logic.sorting.step.SortingStep;
import teachingalgorithms.logic.util.IntArrayUtils;

import java.util.ArrayList;

/**
 * The Class SelectionSort.
 */
public class SelectionSort extends SortingAlgorithm {

    private static final String NAME = "Selectionsort";

    private static final int STEP_LIMIT = 20;

    /**
     * The protocol.
     */
    private ArrayList<SelectionStep> protocol = new ArrayList<SelectionStep>();

    /**
     * Instantiates a new selection sort.
     *
     * @param input the input
     */
    public SelectionSort(int[] input) {
        SelectionStep step = new SelectionStep(input, 0, -1, 0);
        step.add2Explanation("Zu Beginn des Sortierverfahrens liegt das Minimum des aktuell betrachteten Bereichs"
                + " noch auf der ersten Position des Arrays (durch i markiert). In den folgenden Schritten wird nach dem"
                + " Minimum im restlichen Array gesucht um dieses dann an die Position von i zu schreiben. Am Ende des"
                + " Durchlaufs (wenn j das Ende des Arrays erreicht) wird i um 1 erh&ouml;ht und markiert somit den Beginn"
                + " des unsortierten Teils  des Arrays. ");
        this.protocol.add(step);
    }

    /**
     * Converts Step to LaTeX expression.
     *
     * @param chosenStepNumber the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        String retString = "$\n\\begin{smallmatrix}\n ";
        SelectionStep chosenStep = this.protocol.get(chosenStepNumber);
        int[] array = chosenStep.getArray();

        for (int i = 0; i < array.length; i++) {
            if (chosenStepNumber > 0 && array[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]) {
                retString += " \\textcolor{red}{" + array[i] + "} &";
            } else {
                retString += array[i] + " & ";
            }
        }

        // insert minimum if the step was not the last step
        if (chosenStepNumber != protocol.size() - 1) {
            retString += "min = " + (chosenStep.getMinPosition() + 1);
        }

        retString += " \\\\ \n";

        for (int i = 0; i < array.length; i++) {
            if ((chosenStep.getSortedUntilPositionMarker() == i)) {
                retString += "i &";
            } else if ((chosenStep.getSearchingPositionMarker() == i)) {
                retString += "j &";
            } else {
                retString += " &";
            }
        }
        retString += " \n\\end{smallmatrix} \n$ ";

        return retString;
    }

    /**
     * Do step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        boolean stepDone = false;
        if (!this.isTerminated()) {
            String explanation = "";
            stepDone = true;
            SelectionStep lastStep = this.getLastStep();
            SelectionStep newStep = new SelectionStep();
            int[] oldArray = lastStep.getArray();
            int[] newArray = IntArrayUtils.copyArray(oldArray);
            int newMinPosition = lastStep.getMinPosition();
            int newSearchingPositionMarker = -1;
            int newSortedUntilPositionMarker = lastStep.getSortedUntilPositionMarker();
            int oldSortedUntilPositionMarker = lastStep.getSortedUntilPositionMarker();
            int oldSearchingPositionMarker = lastStep.getSearchingPositionMarker();

            // swapping of values
            if (oldSearchingPositionMarker == oldArray.length - 1) {

                newArray[oldSortedUntilPositionMarker] = oldArray[lastStep.getMinPosition()];
                newArray[lastStep.getMinPosition()] = oldArray[oldSortedUntilPositionMarker];
                newSortedUntilPositionMarker = oldSortedUntilPositionMarker + 1;
                newMinPosition = newSortedUntilPositionMarker;

                if (lastStep.getMinPosition() != oldSortedUntilPositionMarker) {
                    explanation += "Da j im vorigen Schritt das Ende des Arrays erreicht hat, wurde in diesem Schritt nun der"
                            + " Wert des in diesem Durchlauf gefundenen Minimums an seine endg&uuml;tige Position im Array getauscht "
                            + "(also mit der Position vertauscht, auf der i im vorigen Schritt stand). Somit steht "
                            + oldArray[lastStep.getMinPosition()] + " nun an der richtigen Position.";
                } else {
                    explanation += "Da das Minimum in diesem Durchlauf auf der selben Position wie i und somit schon an der"
                            + " richtigen Position lag, mussten keine Werte vertauscht werden. Somit ist sich die Zahl "
                            + oldArray[lastStep.getMinPosition()] + " richtig einsortiert. ";
                }

                if ((lastStep.getSortedUntilPositionMarker() != oldArray.length - 2)) {
                    explanation += "<br>i wurde f&uu;r den n&auml;chsten Suchdurchlauf um 1 erh&ouml;ht. ";
                }

				/*
				 * newSearchingPositionMarker remains at -1. /* this marks the
				 * beginning of a new searching cycle for the next step
				 */

                // setting searchingPosition marker to position next to the
                // sorted part of the array
            } else if (oldSearchingPositionMarker < 0) {
                explanation += "j wird auf die Position rechts neben i gesetzt, um den n&auml;chsten Suchdurchlauf"
                        + " nach dem n&auml;chsten lokalen Minimum zu starten. ";
                newSearchingPositionMarker = newSortedUntilPositionMarker + 1;
                // if neither a swap has happened nor a new cycle was started,
                // the marker just gets moved to the nex position
            } else {
                newSearchingPositionMarker = oldSearchingPositionMarker + 1;
            }

			/*
			 * if both markers stood at the last 2 positions of the array in the
			 * previous step the sortedUntilPositionMarker gets set to -1 as
			 * this marks the termination of the sorting algorithm. There is no
			 * longer a sorted and an unsorted portion of the Array as the array
			 * is completely sorted now.
			 */
            if (oldSortedUntilPositionMarker == newArray.length - 2
                    && oldSearchingPositionMarker == newArray.length - 1) {
                explanation += "Nun sind alle Zahlen sortiert und der Algorithmus terminiert.";
                newSortedUntilPositionMarker = -1;

            }

            // if the sorting procedure is not completed and the step does not
            // mark the beginning
            // of a new searching cycle for the minimum, the Minimum gets
            // adjusted if
            // a new minimum has been found
            if (newSortedUntilPositionMarker != -1 && newSearchingPositionMarker != -1
                    && newArray[newSearchingPositionMarker] < newArray[newMinPosition]) {
                explanation += "Da an der aktuell betrachteten Position ein kleinerer Wert "
                        + "als an der zuvor f&uuml;r das Minimum gespeicherten Position (a[" + newMinPosition + "]="
                        + newArray[newMinPosition] + ") steht, "
                        + "wird der Wert f&uuml;r min auf den aktuellen Wert von j (da a[j]="
                        + newArray[newSearchingPositionMarker] + ") gesetzt.";
                newMinPosition = newSearchingPositionMarker;
            } else if (newSortedUntilPositionMarker != -1 && newSearchingPositionMarker != -1) {
                explanation += "Da der an aktuell durch j betrachteten Position befindliche  Wert nicht kleiner als"
                        + " der Wert der zuvor als Minimum gespeicherten Position ist, bleibt der Wert f&uuml;r das Minimum in diesem Schritt bestehen. ";
            }


            newStep.setArray(newArray);
            newStep.setMinPosition(newMinPosition);
            newStep.setSearchingPositionMarker(newSearchingPositionMarker);
            newStep.setSortedUntilPositionMarker(newSortedUntilPositionMarker);
            newStep.add2Explanation(explanation);
            this.protocol.add(newStep);
        }

        return stepDone;

    }

    /**
     * Gets the input size.
     *
     * @return the input size
     */
    public int getInputSize() {

        return protocol.get(0).getArray().length;
    }

    /**
     * Gets the last step.
     *
     * @return the last step
     */
    private SelectionStep getLastStep() {
        return protocol.get(protocol.size() - 1);
    }

    /**
     * Undo step.
     *
     * @return true, if successful
     */
    public boolean undoStep() {
        boolean checker;
        if (protocol.size() > 1) {
            checker = true;
            protocol.remove(protocol.size() - 1);
        } else {
            checker = false;
        }
        return checker;
    }

    /**
     * Reset.
     */
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }
    }

    /**
     * Gets the protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        // TODO Auto-generated method stub
        return this.protocol.size();
    }

    /**
     * Do all steps.
     */
    public void doAllSteps() {
        while (doStep()) {
            ;
        }
    }


    /**
     * Gets the step.
     *
     * @param i the i
     * @return the step
     */
    public SortingStep getStep(int i) {
        return protocol.get(i);
    }

    /**
     * Protocol2 la tex.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i) + "\n\\newline \n\\newline \n";
        }

        return retString;

    }

    private boolean isTerminated() {
        return this.getLastStep().getSortedUntilPositionMarker() == -1;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }

}
