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

import java.util.ArrayList;

import teachingalgorithms.logic.sorting.step.BubbleStep;
import teachingalgorithms.logic.util.IntArrayUtils;

/**
 * <p>
 *         This class represents the implementation of the algorithm Bubblesort.
 * </p>
 * @author Moritz Floeter
 */
public class BubbleSort extends SortingAlgorithm {

    private static final String NAME = "Bubblesort";

    private static final int STEP_LIMIT = 20;

    /**
     * The protocol. This contains all the steps performed in the algorithm
     */
    private ArrayList<BubbleStep> protocol = new ArrayList<BubbleStep>();

    /**
     * Instantiates a new instance of bubblesort.
     *
     * @param input the inputarray
     */
    public BubbleSort(int[] input) {
        /*
		 * Create a sortingmarker for the first Step. The position is marked
		 * with the letter i and marks the 1st item of the array
		 */
        int sortingmarker = 0;
		/*
		 * The 1st entry in the protocol gets created. As no sorting has been
		 * performed at this point, the input remains the userinput, the 1
		 * symbolises that there were no values swapped and a small description
		 * gets set.
		 */
        protocol.add(new BubbleStep(input, sortingmarker, true,
                "i steht zu Beginn auf der ersten Position im Array. Da noch keine Zahlen vertauscht wurden, steht der Wert hierfür auch auf true."
                        + " Dieser Wert speichert die Antwort auf die Frage 'Wurde in diesem Durchlauf (sprich der inneren Schleife) noch nicht vertauscht?'"
                        + " Der Algorithmus stoppt, sobald er in einem Durchlauf nichts vertauschen musste."));
    }

    /**
     * performs all steps until the algorithm terminates.
     */
    public void doAllSteps() {
        while (doStep())
            ;

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
     * Reset.
     */
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }
    }

    /**
     * Do step.Does one single step of the algorithm.
     *
     * @return true, if successful
     */
    public boolean doStep() {
		/*
		 * because it will be needed several times in this method, a SortingStep
		 * instance referencing the previous step is created
		 */
        BubbleStep previous = protocol.get(protocol.size() - 1);
        boolean stepDone = false;

        if (!this.isTerminated()) {
            stepDone = true;

            int[] newArray = IntArrayUtils.copyArray(previous.getArray());
            int oldPos = previous.getSortingmarker();
            String explanation = "";
            boolean newMemory = previous.getMemory();

            if (newArray[oldPos] > newArray[oldPos + 1]) {
                int temp = newArray[oldPos];
                newArray[oldPos] = newArray[oldPos + 1];
                newArray[oldPos + 1] = temp;
                boolean oldMemory = previous.getMemory();
                newMemory = false;
                explanation = "Da das Array an der Stelle i+1 - bezogen auf den vorigen Schritt, hier also "
                        + newArray[oldPos] + " - einen geringern Wert hatte als an der Stelle i (hier: "
                        + newArray[oldPos + 1] + "), wurden beide Werte vertauscht. ";
                if (oldMemory) {
                    explanation += " Da somit in diesem  Durchlauf zum ersten Mal"
                            + " vertauscht wurde, wird der Speicher für nichtvertauscht? auf false ge&auml;ndert. ";
                }
            } else {
                explanation = "Da das Array an der Stelle i - bezogen auf den vorigen Schritt, hier also "
                        + newArray[oldPos]
                        + " - einen geringern (bzw. gleich hohen) Wert hatte als an der Stelle i+1 (hier: "
                        + newArray[oldPos + 1] + "), wurden die Werte nicht verauscht. ";

            }

            // Die neue Position wird gesetzt
            int newPos = oldPos + 1;

            if (newMemory && oldPos == newArray.length - 2 && arrayIsSorted(newArray)) {
                explanation += " Da in diesem Durchlauf keine Zahlen vertauscht wurden, ist das Sortierverfahren beendet.";
            } else if (oldPos == newArray.length - 2 && !(newMemory)) {
                newPos = 0;
                newMemory = true;
                explanation += "Da i die vorletzte Zahl des Arrays markiert hatte und wir"
                        + " in diesem Durchlauf Zahlen vertauscht haben, wird i "
                        + "nun wieder auf das erste Feld des Arrays gesetzt. Der Wert f&uuml;r nichtvertauscht? wurde wieder auf true gesetzt. ";
            } else {
                explanation += "i wurde im Vergleich zum vorigen Schritt um 1 erh&ouml;ht.";
            }

            protocol.add(new BubbleStep(newArray, newPos, newMemory, explanation));

        }

        return stepDone;
    }

    private boolean arrayIsSorted(int[] array) {

        boolean sorted = true;
        for (int i = 0; i < array.length - 2 && sorted; i++) {
            if (array[i] > array[i + 1]) {
                sorted = false;
            }
        }
        return sorted;
    }

    private boolean isTerminated() {
        boolean terminated = true;
        BubbleStep lastStep = protocol.get(protocol.size() - 1);
        int[] array = lastStep.getArray();
        if (!arrayIsSorted(array) || lastStep.getSortingmarker() != array.length - 1 || !lastStep.getMemory()) {
            terminated = false;
        }

        return terminated;
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
     * Gets the protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        return protocol.size();
    }


    /**
     * This produces a LaTeX-Expression representing the entire protocol.
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


    /**
     * Converts the chosen step to LaTeX code.
     *
     * @param chosenStepNumber the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        String retString = "$\n\\begin{smallmatrix}\n ";
        BubbleStep chosenStep = this.protocol.get(chosenStepNumber);
        int[] lastArray = chosenStep.getArray();

		/*
		 * write another line to the protocol when i is reset to the 1st
		 * position of the array. This is done so that it gets more obvious to
		 * the user that i is reset to the 1st position.
		 */
        if (!this.wasLastStepOfAlgorithm(chosenStep) & (this.protocol.get(chosenStepNumber).getSortingmarker() == 0)
                & chosenStepNumber >= 1) {
            for (int i = 0; i < lastArray.length; i++) {
                if (lastArray[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]) {
                    retString += " \\textcolor{red}{" + lastArray[i] + "} &";
                } else {
                    retString += lastArray[i] + " & ";
                }

            }
            retString += "false \\\\ \n";
        }

        // adds the actual step to the String
        for (int i = 0; i < lastArray.length; i++) {
            if (chosenStepNumber >= 1 && lastArray[i] != this.protocol.get(chosenStepNumber - 1).getArray()[i]
                    && !(this.protocol.get(chosenStepNumber).getSortingmarker() == 0)) {
                retString += " \\textcolor{red}{" + lastArray[i] + "} & ";
            } else {
                retString += lastArray[i] + " & ";
            }
        }
        if (!chosenStep.getMemory()) {
            retString += " false ";
        } else {
            retString += " true ";
        }

        retString += " \\\\ \n";

        // writes the marker (i)
        for (int i = 0; i < lastArray.length; i++) {
            if ((chosenStep.getSortingmarker() == i) && !wasLastStepOfAlgorithm(chosenStep)) {
                retString += "i &";
            } else {
                retString += " &";
            }

        }
        retString += " \n\\end{smallmatrix} \n$ ";

        return retString;
    }

    private boolean wasLastStepOfAlgorithm(BubbleStep chosenStep) {

        int chosenStepNumber = protocol.indexOf(chosenStep);
        return (chosenStepNumber == this.protocol.size() - 1 && this.isTerminated());
    }


    /**
     * Gets chosenStep by index.
     *
     * @param chosenStep the chosen step @return the step
     */
    public BubbleStep getStep(int chosenStep) {
        return protocol.get(chosenStep);
    }


    @Override
    public String getName() {
        // TODO
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }

}
