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

import teachingalgorithms.logic.sorting.step.RadixStep;

import java.util.ArrayList;

/**
 * <p>
 *         The Class RadixSort.
 *         This represents the implementation of the algorithm radixsort.
 * </p>
 * @author Moritz Floeter
 */
public class RadixSort extends SortingAlgorithm {

    private static final String NAME = "Radixsort";

    private static final int STEP_LIMIT = -1;

    /**
     * The protocol.
     */
    private ArrayList<RadixStep> protocol = new ArrayList<>();

    /**
     * The input array.
     */
    private int[] inputArray;

    /**
     * Instantiates a new instance of radixsort.
     *
     * @param input the inputarray
     */
    public RadixSort(int[] input) {
        RadixStep firstStep = new RadixStep();
        ArrayList<String> inputAsStrings = new ArrayList<>();
        int max = 0;
        this.inputArray = input;

        // find the maximum length of one input element (letters needed for the
        // according value)
        for (int currentInputElement : input) {
            if (max < ("" + currentInputElement).length()) {
                max = ("" + currentInputElement).length();
            }
        }

		/*
         * all elements shorter than the longest element get filled with zeroes
		 * so that they have the same length when represented as String
		 */
        for (int currentInputElement : input) {
            String input2add = "" + currentInputElement;
            while (input2add.length() < max) {
                input2add = "0" + input2add;
            }
            inputAsStrings.add(input2add);
        }
        sortIntoBoxes(inputAsStrings, firstStep);

        protocol.add(firstStep);
    }

    /**
     * Sort into boxes.
     *
     * @param collectedStrings the collected strings
     * @param step             the step
     */
    private static void sortIntoBoxes(ArrayList<String> collectedStrings, RadixStep step) {
        // as memory marks the position counting from the right end of the
        // string, the position gets set accordingly
        int position = collectedStrings.get(0).length() - 1 - step.getMemory();
        if (step.getMemory() != 0) {
            step.add2Explanation("In diesem Schritt werden die Zahlen nach der " + (step.getMemory() + 1)
                    + ". Ziffer von rechts " + " in zugeh&ouml;hrige F&auml;cher verteilt. <br> ");
        } else {
            step.add2Explanation(
                    "Bevor das Sortierverfahren beginnen kann, m&uuml;ssen eigentlich alle Zahlen durch Voranstellen von 0en auf die selbe L&auml;ge gebracht werden, "
                            + "falls sie unterschiedlich lang sind. <br> "
                            + "Dieser Vorgang ist hier nicht gesondert aufgef&uuml;hrt. <br> <br> "
                            + "Lediglich beim ersten Schritt werden die Zahlen der Eingabeliste nach dieser Modifikation direkt verwendet - demnach ist hier die Reihenfolge "
                            + "generell nicht wichtig. Um das System allerdings einheitlich und durchschaubar zu gestalten werden auch "
                            + "hier die Zahlen in der Reihenfolge der Eingabe einsortiert (von links beginnend nach rechts). <br> <br> WICHTIG: In den folgenden Schritten ist es essentiell, "
                            + "dass die Zahlen stets in der selben Reihenfolge einsortiert werden,"
                            + " in der sie im Sammelschritt des vorangegangenen Schrittes gesammelt wurden. <br> <br> "
                            + "W&auml;hrend die Zahlen im 1. Schritt nach der 1. Ziffer von rechts sortiert werden, werden sie im 2. Schritt nach der"
                            + "2. Ziffer von rechts sortiert, im 3. nach der 3. Ziffer usw. <br> "
                            + "Dies geschieht so lange, bis man mit allen Ziffern durch ist. <br>  <br>"
                            + "Nach dem Einsortieren/Verteilen werden die Zahlen gesammelt. <br>"
                            + "WICHTIG: der Reihe nach aus dem Fach F0 von oben nach unten, dann aus dem Fach F1 von oben nach unten, dann aus dem Fach F3 usw.");
        }

        if (step.getMemory() == collectedStrings.get(0).length() - 1) {
            step.add2Explanation(
                    "Da dies die letzte Ziffer war, ist das Sortierverfahren nach diesem Schritt beendet.");
        }

        for (String collectedString : collectedStrings) {
            /*
			 * in order to know which postBox to sort into, the character at the
			 * position gets pulled and converted to an integer (the char '0'
			 * has the value 48, the char '1' has the value 49 etc. -> that is
			 * why the reduction by 48 is necessary)
			 */

            int number = collectedString.charAt(position) - 48;
            step.getPostBox(number).add(collectedString);
        }

    }

    /**
     * Highlights a certain part of a LaTeX expression by underlining it and
     * setting its color to red.
     *
     * @param input    the input
     * @param position the position
     * @return the string
     */
    private static String highlight(String input, int position) {
        String retString = input.substring(0, position) + "\\textcolor{red}{\\underline{"
                + input.substring(position, position + 1) + "}}" + input.substring(position + 1);
        return retString;

    }

    /**
     * Gets the number length.
     *
     * @return the number length
     */
    private int getNumberLength() {
        return this.protocol.get(0).collectBoxes().get(0).length();
    }

    /**
     * Do step.
     *
     * @return true, if successful
     */
    public boolean doStep() {
        boolean stepDone = false;
        RadixStep prevStep = this.getStep(this.getProtocolSize() - 1);
        int nextMemory = prevStep.getMemory() + 1;
        if (nextMemory < this.getNumberLength()) {
            RadixStep nextStep = new RadixStep();
            nextStep.setMemory(nextMemory);
            sortIntoBoxes(prevStep.collectBoxes(), nextStep);
            protocol.add(nextStep);
            stepDone = true;
        }
        return stepDone;
    }

    /**
     * Collects all elements of a certain step and returns them as a LaTeX
     * expression that can be rendered to display all the according items as
     * comma seperated values.
     *
     * @param stepNumber the step number
     * @return the string
     */
    private String collect2LaTeX(int stepNumber) {
        String retString = "$\n \\underline{Sammeln} \\\\ \n ";
        ArrayList<String> valuesAsString = this.protocol.get(stepNumber).collectBoxes();
        for (int i = 0; i < valuesAsString.size(); i++) {
            retString += valuesAsString.get(i);
            if (i < valuesAsString.size() - 1) {
                retString += ", ";
            }
            //add linebreaks after 10 elements.
            if ((i + 1) % 10 == 0 && this.inputArray.length > i + 1) {
                retString += "\\\\ ";
            }
        }
        retString += " \\\\ \n$";
        return retString;
    }

    /**
     * Returns a LaTeX expression (String) that contains
     * the input as a list of comma separated values.
     *
     * @return the string
     */
    private String originalArray2LaTeX() {
        String originalArray = "";
        for (int i = 0; i < this.inputArray.length; i++) {
            originalArray = originalArray + this.inputArray[i];
            if (this.inputArray.length > i + 1) {
                originalArray += ", ";
            }
            //add linebreaks after 10 elements.
            if ((i + 1) % 10 == 0 && this.inputArray.length > i + 1) {
                originalArray += "\\\\ ";
            }
        }

        originalArray = "$\n \\underline{Eingabe} \\\\ \n " + originalArray + " \\\\ \n$\n";
        return originalArray;
    }

    /**
     * Returns a representation of the complete step as LaTeX expression.
     *
     * @param stepNumber the step number
     * @return the string
     */
    public String step2LaTeX(int stepNumber) {
        String retString = "";
        if (stepNumber == 0) {
            retString += originalArray2LaTeX();
        } else {
            retString += "$\n............................\\\\\n$\n \n ";
        }
        retString += sort2LaTeX(stepNumber);
        retString += collect2LaTeX(stepNumber);
        return retString;
    }

    /**
     * Returns the complete protocol as LaTeX expression
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i) + " \n\\newline \n";
        }

        return retString;
    }

    /**
     * Sort2 la te x.
     *
     * @param stepNumber the step number
     * @return the string
     */
    private String sort2LaTeX(int stepNumber) {
        String retString = "$\n\\underline{Verteilen}\\\\\n$\n\n" + "$\n\\begin{matrix}\n"
                + "F_0 & F_1 & F_2 & F_3 & F_4 & F_5 & F_6 & F_7 & F_8 & F_9 \\\\ \n";
        RadixStep step = protocol.get(stepNumber);

        boolean checker = true;
        // i represents lines
        for (int i = 0; checker; i++) {
            checker = false;
            String line = "";
            // j represents columns
            for (int j = 0; j <= 9; j++) {
                // TODO: generate table here
                if (step.getPostBox(j).size() > i) {
                    checker = true;
					/*
					 * puts the value into the right place of the matrix and
					 * highlights the number at the position that was looked at
					 * for sorting into the postBoxes
					 */
                    line += highlight(step.getPostBox(j).get(i), this.getNumberLength() - 1 - step.getMemory());
                }
                if (j < 9) {
                    line += " & ";
                } else {
                    line += " \\\\ \n ";
                }
            }
            if (checker) {
                retString += line;
            }
        }
        retString += "\\end{matrix}\n \\\\$\n\n";
        return retString;
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
     * Reset all steps (removes all step from protocol and go back to the
     * beginning).
     */
    public void reset() {
        while (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
        }

    }

    /**
     * Do all steps.
     */
    public void doAllSteps() {
        while (doStep())
            ;

    }

    /**
     * Gets the step at the according position in the protocol.
     *
     * @param i the position in the protocol
     * @return the step
     */
    public RadixStep getStep(int i) {
        return protocol.get(i);
    }

    /**
     * Gets the protocol size.
     *
     * @return the protocol size
     */
    public int getProtocolSize() {
        return this.protocol.size();
    }

    @Override
    public int getInputSize() {
        return protocol.get(0).collectBoxes().size();
    }

    @Override
    public String getName() {
        // TODO Auto-generated method stub
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }


}
