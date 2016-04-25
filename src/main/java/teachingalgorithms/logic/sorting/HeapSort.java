package teachingalgorithms.logic.sorting;

import teachingalgorithms.logic.sorting.step.HeapStep;
import teachingalgorithms.logic.util.IntArrayUtils;

import java.util.ArrayList;

/**
 *
 *
 * @author Moritz Floeter
 *
 *         The Class HeapSort. Represents the implementation of the algorithm
 *         heapSort
 *
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 *
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 *
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class HeapSort extends SortingAlgorithm {

    private static final String NAME = "Heapsort";

    private static final int STEP_LIMIT = -1;

    /** The protocol. */
    private ArrayList<HeapStep> protocol = new ArrayList<HeapStep>();

    /** The minheap. */
    boolean minheap = true;

    /**
     * Instantiates a new heap sort.
     *
     * @param input
     *            the input
     * @param minheap
     *            the minheap
     */
    public HeapSort(int[] input, boolean minheap) {
        int[][] step = new int[input.length][];
        this.minheap = minheap;
        for (int i = 0; i < input.length; i++) {
            step[i] = new int[] { input[i] };
        }
        String explanation = "Die Werte des zu sortierenden Arrays werden in Form eines Bin&auml;rbaums aufgeschrieben. "
                + "Dazu werden die Werte in Leserichtung in den Baum eingetragen, also von links nach rechts und von oben nach unten. "
                + "So steht der erste Wert des Eingabearrays ganz oben (rechts daneben kommt allerdings hier kein weiterer Wert, da der Bin&auml;rbaum "
                + "in der ersten Zeile nur ein Element hat), der zweite Wert folgt in der n&auml;chsten Zeile ganz links. <br> "
                + "Da in der zweiten Zeile zwei Werte eingetragen werden k&ouml;nnen, folgt rechts daneben der dritte Wert. In der darauffolgenden Zeile "
                + "folgen die n&auml;chsten 4 Werte usw. <br> <br> "
                + "Im ersten Teil \"Heap erstellen\" wird zun&auml;chst die HeapBedinung erf&uuml;llt. Daf&uuml;r ist es notwendig, die H&auml;fte aller Elemente zu versickern. Hierf&uuml;r"
                + " beginnen wir mit dem ersten Element von rechts unten, dass noch Tochternkoten hat. Es wird in folgender Reihenfolge versickert: <br> "
                + " 3.) &nbsp;&nbsp;&lt;-<br>2.) &nbsp;&lt;--<br>1.) &lt;----";
        // Memory wird auf das erste zu versickernde Element gesetzt
        int memory = (int) Math.floor(input.length / 2 - 1);
        protocol.add(new HeapStep(step, memory, new ArrayList<Integer>(), explanation));
    }

    /**
     * Do all steps.
     */
    public void doAllSteps() {
        while (doStep())
            ;
    }

    private void heapifyMax(HeapStep curStep) {
        int[][] curStepArray = curStep.getStep();
        int memory = curStep.getMemory();

        int i = memory;
        int iAtBeginning = -1;

		/*
		 * If the memory is -1 this means that we are in the sorting phase but
		 * regarless of that it is still the 1st element of the array that gets
		 * heapified. Therefore i gets set to 0.
		 */
        if (memory == -1) {
            i = 0;
        }

        curStep.add2Explanation("<i>MaxHeap -> es wird so vertauscht, dass"
                + " der Vaterknoten immer gr&ouml;sser als seine Tochterknoten ist </i> <br> ");

        while (curStepArray.length > 2 * (i + 1) && iAtBeginning != i) {
            // iAtBeginning stores the initial value for i. If i changes,
            // values have been swapped. i always marks the position of the
            // element that is looked at.
            iAtBeginning = i;
            int swapParentValue = curStepArray[i][curStepArray[i].length - 1];
            int swapLeftChildValue = curStepArray[2 * (i + 1) - 1][curStepArray[2 * (i + 1) - 1].length - 1];
            int swapRightChildValue = curStepArray[2 * (i + 1)][curStepArray[2 * (i + 1)].length - 1];

            if (swapParentValue < swapLeftChildValue && swapLeftChildValue >= swapRightChildValue) {
                curStep.add2Explanation("- Da " + swapParentValue + " kleiner als " + swapLeftChildValue
                        + " ist, werden die beiden Werte vertauscht. ");
                if (swapParentValue < swapRightChildValue) {
                    curStep.add2Explanation("Auch wenn der rechte Tochterknoten mit " + swapRightChildValue
                            + " ebenfalls gr&ouml;sser als der Vaterknoten ist, muss an dieser Stelle der linke Tochterknoten gew&auml;hlt"
                            + " werden. Wir w&auml;hlen immer den gr&ouml;sseren der beiden Kandidaten.");
                }
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapLeftChildValue);
                curStepArray[2 * (i + 1) - 1] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1) - 1],
                        swapParentValue);
                i = 2 * (i + 1) - 1;
            } else if (swapParentValue < swapRightChildValue) {
                curStep.add2Explanation("- Da " + swapParentValue + " kleiner als " + swapRightChildValue
                        + " ist, werden die beiden Werte vertauscht. ");
                if (swapParentValue < swapLeftChildValue) {
                    curStep.add2Explanation("Auch wenn der linke Tochterknoten mit " + swapLeftChildValue
                            + " ebenfalls gr&ouml;sser als der Vaterknoten ist, muss an dieser Stelle der recht Tochterknoten gew&auml;hlt"
                            + " werden. Wir w&auml;hlen immer den gr&ouml;sseren der beiden Kandidaten.");
                }
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapRightChildValue);
                curStepArray[2 * (i + 1)] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1)], swapParentValue);
                i = 2 * (i + 1);
            }
            curStep.add2Explanation(" <br> ");
        }

		/*
		 * If the element that should be heapified still has one child that
		 * should be swapped this action gets performed here
		 */

        if (curStepArray.length > 2 * (i + 1) - 1 && !(curStepArray.length > 2 * (i + 1))) {
            int swapParentValue = curStepArray[i][curStepArray[i].length - 1];
            int swapLeftChildValue = curStepArray[2 * (i + 1) - 1][curStepArray[2 * (i + 1) - 1].length - 1];
            if (swapParentValue < swapLeftChildValue) {
                curStep.add2Explanation("- Da " + swapParentValue + " kleiner als " + swapLeftChildValue
                        + " ist, werden die beiden Werte vertauscht. <br>");
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapLeftChildValue);
                curStepArray[2 * (i + 1) - 1] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1) - 1],
                        swapParentValue);
            }
        }

        if (iAtBeginning == i) {
            curStep.add2Explanation("- Der Wert " + curStepArray[i][curStepArray[i].length - 1]
                    + " ist bereits gr&ouml;sser als seine Tochterknoten. Daher muss an dieser Stelle nicht mehr getauscht werden. <br> ");
        }

    }

    private void heapifyMin(HeapStep curStep) {
        int[][] curStepArray = curStep.getStep();
        int memory = curStep.getMemory();

        int i = memory;
        int iAtBeginning = -1;

		/*
		 * If the memory is -1 this means that we are in the sorting phase but
		 * regarless of that it is still the 1st element of the array that gets
		 * heapified. Therefore i gets set to 0.
		 */
        if (memory == -1) {
            i = 0;
        }

        curStep.add2Explanation(
                "<i>MinHeap -> Es wird so vertauscht, dass der Vaterknoten immer kleiner als seine Tochterknoten ist </i> <br> ");

		/*
		 * heapifies the element at position i until it is heapified (= i did
		 * not change) or does not have 2 children
		 */
        while (curStepArray.length > 2 * (i + 1) && iAtBeginning != i) {
            iAtBeginning = i;
            int swapParentValue = curStepArray[i][curStepArray[i].length - 1];
            int swapLeftChildValue = curStepArray[2 * (i + 1) - 1][curStepArray[2 * (i + 1) - 1].length - 1];
            int swapRightChildValue = curStepArray[2 * (i + 1)][curStepArray[2 * (i + 1)].length - 1];
            if (swapParentValue > swapLeftChildValue && swapLeftChildValue <= swapRightChildValue) {
                curStep.add2Explanation("- Da " + swapParentValue + " gr&ouml;sser als " + swapLeftChildValue
                        + " ist, werden die beiden Werte vertauscht. ");
                if (swapParentValue > swapRightChildValue) {
                    curStep.add2Explanation("Auch wenn der rechte Tochterknoten mit " + swapRightChildValue
                            + " ebenfalls kleiner als der Vaterknoten ist, muss an dieser Stelle der linke Tochterknoten gew&auml;hlt werden. Wir w&auml;hlen immer den kleineren der beiden Kandidaten.");
                }
                curStep.add2Explanation(" <br> ");
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapLeftChildValue);
                curStepArray[2 * (i + 1) - 1] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1) - 1],
                        swapParentValue);
                i = 2 * (i + 1) - 1;
            } else if (swapParentValue > swapRightChildValue) {
                curStep.add2Explanation("- Da " + swapParentValue + " gr&ouml;sser als " + swapRightChildValue
                        + " ist, werden die beiden Werte vertauscht. ");
                if (swapParentValue > swapLeftChildValue) {
                    curStep.add2Explanation("Auch wenn der linke Tochterknoten mit " + swapLeftChildValue
                            + " ebenfalls kleiner als der Vaterknoten ist, muss an dieser Stelle der rechte Tochterknoten gew&auml;hlt werden. Wir w&auml;hlen immer den kleineren der beiden Kandidaten.");
                }
                curStep.add2Explanation(" <br> ");
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapRightChildValue);
                curStepArray[2 * (i + 1)] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1)], swapParentValue);
                i = 2 * (i + 1);
            }

        }

		/*
		 * If the element that should be heapified still has one child that
		 * should be swapped this action gets performed here
		 */
        if (curStepArray.length > 2 * (i + 1) - 1 && !(curStepArray.length > 2 * (i + 1))) {
            int swapParentValue = curStepArray[i][curStepArray[i].length - 1];
            int swapLeftChildValue = curStepArray[2 * (i + 1) - 1][curStepArray[2 * (i + 1) - 1].length - 1];
            if (swapParentValue > swapLeftChildValue) {
                curStep.add2Explanation("- Da der Wert " + swapParentValue + " gr&ouml;sser als " + swapLeftChildValue
                        + " ist, werden beide Werte vertauscht. ");
                curStepArray[i] = IntArrayUtils.copyAdd2IntArray(curStepArray[i], swapLeftChildValue);
                curStepArray[2 * (i + 1) - 1] = IntArrayUtils.copyAdd2IntArray(curStepArray[2 * (i + 1) - 1],
                        swapParentValue);
            } else {
                curStep.add2Explanation("- Der Wert " + swapParentValue
                        + " ist bereits kleiner als sein Tochterknoten. Daher muss an dieser Stelle nicht mehr getauscht werden.");
            }
        }

        if (iAtBeginning == i) {
            curStep.add2Explanation("- Der Wert " + curStepArray[i][curStepArray[i].length - 1]
                    + " ist bereits kleiner als seine Tochterknoten. Daher muss an dieser Stelle nicht mehr getauscht werden. ");
        }

        curStep.add2Explanation(" <br> ");
    }

    /**
     * Heapify. Performs heapify on the current step. Takes into consideration
     * the current value stored in memory and thereby only heapifies the value
     * passed to it.
     *
     * @param curStep
     *            the cur step
     */
    private void heapify(HeapStep curStep) {

        // For minheaps, the steps get performed according to the minheap
        // condition
        if (minheap) {
            heapifyMin(curStep);
        } else {
            heapifyMax(curStep);
        }

    }

    /**
     * Do step.
     *
     * @return true, if successful
     */
    @SuppressWarnings("unchecked")
    public boolean doStep() {
        boolean stepDone = false;

        // new Step is being created.
        HeapStep curStep = new HeapStep();
        HeapStep prevStep = protocol.get(protocol.size() - 1);

        //check if algorithm has already terminated
        if (prevStep.getStep() != null) {
            curStep.setMemory(prevStep.getMemory());
            curStep.setSortedList((ArrayList<Integer>) prevStep.getSortedList().clone());

            // If the memory is equal or above 0 it means that the heap
            // condition has not been achieved yet
            if (prevStep.getMemory() >= 0) {
                curStep.add2Explanation(
                        "Es wird nun das mit &gt; &lt; markierte Element versickert. Daf&uuml;r sind die folgenden Tauschschritte notwendig (diese werden nacheinander ausgef&uuml;hrt): <br> <br>");

                curStep.setStep(copyCompleteStep(prevStep.getStep()));
                heapify(curStep);
                stepDone = true;

                // If it is -1 or lower it means that the heap condition has
                // been achieved and that a number can be removed
                // from the top of the binary tree as long as there is a number
                // to remove.
            } else if (prevStep.getStep().length > 1) {
                curStep.add2Explanation(
                        "Da wir uns in der Sortierphase befinden, wurde nun das oberste Element des vorigen Bin&auml;rbaums im Heap entfernt. Gleichzeitig"
                                + " wurde das Element von rechts unten an dessen Stelle gesetzt. <br> ");
                curStep.setStep(copyStepAndRemoveElement(prevStep.getStep()));
                int newSortedValue = prevStep.getStep()[0][prevStep.getStep()[0].length - 1];
                curStep.getSortedList().add(newSortedValue);
                if (curStep.getStep().length > 1) {
                    curStep.add2Explanation("Anschliessend wird dieses Element versickert: <br> <br>");
                    heapify(curStep);
                } else {
                    curStep.add2Explanation(
                            "Es ist nun nur noch eine Zahl im Heap vorhanden. Hier kann nat&uuml;rlich nichts mehr"
                                    + " vertauscht werden. <br> Dies ist die letzte Zahl, die entfernt und zur Liste der sortierten"
                                    + " Zahlen hinzugef&uuml;gt wird. <br>");
                }
                stepDone = true;
            } else {
				/*
				 * if there is only one last number to remove, the number is
				 * added to the list of sorted numbers and the array that
				 * represents the binary tree gets set to null.
				 */
                curStep.setStep(null);
                curStep.add2Explanation(
                        "Fertig sortiert... dazu kann man ja gar keinen vern&uuml;nftigen Tooltip mehr machen.");
                curStep.getSortedList().add(prevStep.getStep()[0][0]);
                stepDone = true;
            }

			/*
			 * After each step the next element that needs to be heapified is
			 * marked by i. Because we want to set it to the next Element, the
			 * value gets adjusted. However: If the value was already -1, that
			 * means that we were already in the sorting stage and therefore do
			 * not need to change the value anymore.
			 */
            if (curStep.getMemory() >= 0) {
                curStep.setMemory(curStep.getMemory() - 1);
            }
            protocol.add(curStep);
        }

        return stepDone;
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
     * Gets the step at the chosen postion from the protocol.
     *
     * @param chosenStep
     *            the chosen step
     * @return the step
     */
    public HeapStep getStep(int chosenStep) {
        return protocol.get(chosenStep);
    }

    /**
     * Undo step.
     *
     * @return true, if successful
     */
    public boolean undoStep() {
        boolean removed = false;
        if (protocol.size() > 1) {
            protocol.remove(protocol.size() - 1);
            removed = true;
        }
        System.out.println("Step is being removed: " + removed);
        return removed;
    }

    /**
     * Copy complete step.
     *
     * @param array
     *            the array
     * @return the int[][]
     */
    private static int[][] copyCompleteStep(int[][] array) {
        int[][] newarray = new int[array.length][];
        for (int i = 0; i < array.length; i++) {
            // copies the last element in every secondary contained in the array
            // to the new array.
            newarray[i] = new int[] { array[i][array[i].length - 1] };
        }
        return newarray;

    }

    /**
     * Renders the protocol to LaTeX-code.
     *
     * @return the string
     */
    public String protocol2LaTeX() {
        String retString = "";
        for (int i = 0; i < protocol.size(); i++) {
            retString += " " + this.step2LaTeX(i) + "\n \n";
        }

        return retString;
    }

    /**
     * Generates a LaTeX-Expression representing one step. This is used for
     * export only as JLaTeX-Math does not allow the use of tikz. The GUI uses
     * HeapSortPanelExtended as replacement (which uses BinaryTreePanel to
     * render the binary tree)
     *
     * @param chosenStepNumber
     *            the chosen step number
     * @return the string
     */
    public String step2LaTeX(int chosenStepNumber) {
        HeapStep chosenStep = this.protocol.get(chosenStepNumber);
        String retString = "";
        if (chosenStepNumber == 0) {
            retString += "\\underline{\\textbf{Heap erstellen}}\n\n" + "Urspr\\\"unglicher Bin\\\"arbaum \n\n";
        } else if (chosenStepNumber == 1) {
            retString += "$\\boxed{Zahl}$ := zu versickerndes Element \n\n";
        }

		/*
		 * Generate elements for the part of the protocol where values get
		 * removed from the Heap and inserted into the Array
		 */
        if (chosenStep.getMemory() == -1 && !chosenStep.getSortedList().isEmpty()) {
            if (this.protocol.get(chosenStepNumber - 2).getMemory() > -1) {
                retString += "\\underline{\\textbf{Sortieren}}\n\n";
            }
            retString += "sortiertes Array : [";
            for (int i = 0; i < chosenStep.getSortedList().size(); i++) {
                retString += "" + chosenStep.getSortedList().get(i);
                if (i < chosenStep.getSortedList().size() - 1) {
                    retString += ", ";
                }
            }
            retString += "]\n\n";
        }

        if (chosenStep.getStep() != null) {
            retString += "\\begin{tikzpicture}[very thick,level/.style={sibling distance=60mm/#1}]\n \\"
                    + generateLaTeXHeapNode(0, chosenStep) + "; \n \\end{tikzpicture}";
        } else {
            retString += "$\\rightarrow fertig \\, sortiert$";
        }

        retString += "\n\n ............................ \n\n";
        return retString;
    }

    /**
     * Generate heap node. This generates a node of the binary tree as well as
     * all its children by recursively calling this function
     *
     * @param nodeNumber
     *            the node number
     * @param step
     *            the step
     * @return the string
     */
    private String generateLaTeXHeapNode(int nodeNumber, HeapStep step) {
        String retString = "node [vertex]";
        int[][] heap = step.getStep();
        String heapNodeNumberString = "" + heap[nodeNumber][0];
        if (nodeNumber == step.getMemory() + 1 && nodeNumber <= Math.floor(heap.length / 2 - 1)) {
            heapNodeNumberString = "\\boxed{" + heapNodeNumberString + "}";
        }
        if (heap[nodeNumber].length > 1) {
            retString += "[label={\\small ";
            for (int i = 1; i < heap[nodeNumber].length - 1; i++) {
                retString += "\\Ccancel[red]{$" + heap[nodeNumber][i] + "$}, ";
            }
            retString += "$" + heap[nodeNumber][heap[nodeNumber].length - 1] + "$}] ";
            retString += "{\\Ccancel[red]{$" + heapNodeNumberString + "$}}";
        } else {
            retString += "{$" + heapNodeNumberString + "$}";
        }

        int leftnode = 2 * (nodeNumber + 1) - 1;
        int rightnode = 2 * (nodeNumber + 1);

        if (heap.length > leftnode) {
            retString += "child{" + generateLaTeXHeapNode(leftnode, step) + "}";
            if (heap.length > rightnode) {
                retString += "\nchild{" + generateLaTeXHeapNode(rightnode, step) + "}";

            } else {
                retString += "child[missing]{}";
            }

        }

        return retString;
    }

    /**
     * Copy step and remove top element of the heap. This is used in the sorting
     * part of the algorithm.
     *
     * @param array
     *            the array
     * @return the int[][]
     */
    private static int[][] copyStepAndRemoveElement(int[][] array) {
        // length is shorter because an element gets removed.
        int[][] newarray = new int[array.length - 1][];
        // copies the last value from the last secondary array into the first
        // position of the new array.
        // In regard to the binary tree this means copying the last element in
        // the tree to the top of the new one
        newarray[0] = new int[] { array[array.length - 1][array[array.length - 1].length - 1] };
        for (int i = 1; i < array.length - 1; i++) {
            // copies the last element in every secondary contained in the array
            // to the new array.
            newarray[i] = new int[] { array[i][array[i].length - 1] };
        }
        return newarray;

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
     * Gets the input size.
     *
     * @return the input size
     */
    public int getInputSize() {
        return this.getStep(0).getStep().length;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public int getStepLimit() {
        return STEP_LIMIT;
    }

}