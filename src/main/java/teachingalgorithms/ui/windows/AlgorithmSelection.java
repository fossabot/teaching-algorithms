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

package teachingalgorithms.ui.windows;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import javax.swing.*;

import org.jdesktop.swingx.JXPanel;
import org.jdesktop.swingx.JXTextField;
import teachingalgorithms.logic.graph.algorithm.BreadthFirstSearch;
import teachingalgorithms.logic.graph.algorithm.DepthFirstSearch;
import teachingalgorithms.logic.graph.algorithm.GraphAlgorithm;
import teachingalgorithms.logic.graph.algorithm.Kruskal;
import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.logic.sorting.*;
import teachingalgorithms.logic.util.InputGeneration;
import teachingalgorithms.ui.components.AdjacencyMatrixEditPanel;
import teachingalgorithms.ui.components.RandomArrayGenerator;
import teachingalgorithms.ui.windows.graph.GraphWindow;
import teachingalgorithms.ui.windows.sorting.HeapWindow;
import teachingalgorithms.ui.windows.sorting.SortingWindow;

/**
 * <p>
 *         The Class AlgorithmSelection. This class is the one where it all
 *         starts. It provides the gui the user sees right after the
 *         software is launched. Here the user can select algorithms which he
 *         wants to run and configure them.
 * </p>
 * @author Moritz Floeter
 */
public class AlgorithmSelection extends WindowSubstructure {

    /**
     * The Constant maxCount.
     */
    public static final int maxCount = 100;
    /**
     * The Constant maxValue.
     */
    public static final int maxValue = 2147483646;
    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 4107930542363651070L;
    /**
     * The startbtn.
     */
    private JButton startbtn;
    private JTabbedPane tabbedPane;
    private JXPanel sortingPanel;
    private JXPanel graphPanel;
    private AdjacencyMatrixEditPanel matrixEditPanel;

    /**
     * The randombtn.
     */
    private JButton randombtn;

    /**
     * The integerListField.
     */
    private JXTextField integerListField;
    /**
     * The availableSortingAlgorithms.
     */
    private String[] availableSortingAlgorithms = {"Bubblesort", "Selectionsort", "Radixsort", "Quicksort", "Heapsort (Minheap)", "Heapsort (Maxheap)"};

    /**
     * The algorithm selection.
     */
    private JComboBox<String> algorithmSelection;

    private JComboBox<String> graphSelection;
    /**
     * The textfield label
     */
    private JLabel textfieldLabel;


    /**
     * Instantiates a new algorithm selection window.
     */
    public AlgorithmSelection() {
        super();

        startbtn = new JButton();
        randombtn = new JButton();
        tabbedPane = new JTabbedPane();
        sortingPanel = new JXPanel();

        graphPanel = new JXPanel();
        graphPanel.setLayout(new BorderLayout());
        matrixEditPanel = new AdjacencyMatrixEditPanel();
        graphPanel.add(matrixEditPanel, BorderLayout.CENTER);

        graphSelection = new JComboBox<>();

        graphPanel.add(graphSelection, BorderLayout.SOUTH);

        integerListField = new JXTextField();
        algorithmSelection = new JComboBox<>(availableSortingAlgorithms);
        textfieldLabel = new JLabel();

        Container mainpane = this.getContentPane();
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        mainpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        //defined in WindowSubstructure
        mainpane.add(menuBar, BorderLayout.NORTH);
        mainpane.add(tabbedPane, BorderLayout.CENTER);
        mainpane.add(startbtn, BorderLayout.SOUTH);

        // Actionlisteners get added to buttons
        this.startbtn.addActionListener(actionEvent -> startSelectedAlgorithm());
        this.randombtn.addActionListener(actionEvent -> new RandomArrayGenerator(this));

        integerListField.addKeyListener(new InputListener());

		/*
         * create the panel where the algorithm selection and the startbutton
		 * gets displayed
		 */
        sortingPanel.setLayout(new BoxLayout(sortingPanel, BoxLayout.Y_AXIS));
        sortingPanel.add(textfieldLabel);
        sortingPanel.add(integerListField);
        sortingPanel.add(randombtn);
        sortingPanel.add(algorithmSelection);

        tabbedPane.setMinimumSize(new Dimension(800, 600));
        tabbedPane.addTab("", sortingPanel);
        tabbedPane.addTab("", graphPanel);

        setTextToWindow();

        // Setting a few window attributes
        this.pack();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.validate();
        this.repaint();
        this.setVisible(true);
    }

    /**
     * Removes invalid chars from the input.
     *
     * @param text the text
     * @return the string
     */
    public static String removeInvalidChars(String text) {
        String returnText = text;
        for (int i = 0; i < returnText.length(); i++) {
            char c = returnText.charAt(i);
            if (!((c >= '0') && (c <= '9')) && (c != ',')) {
                returnText = returnText.replace(Character.toString(returnText.charAt(i)), "");
                i--;
            }
        }
        return returnText;
    }

    /**
     * Sets the input.
     *
     * @param input the new input
     */
    public void setInput(String input) {
        integerListField.setText(input);
        this.validate();
        this.repaint();
    }

    @Override
    protected void setTextToWindow() {
        super.setTextToWindow();

        this.setTitle(MESSAGES.getMessage("algorithmselection.title"));

        this.tabbedPane.setTitleAt(0, MESSAGES.getMessage("algorithmselection.sorting"));
        this.tabbedPane.setTitleAt(1, MESSAGES.getMessage("algorithmselection.graph"));

        textfieldLabel.setText(
                MESSAGES.getMessage("algorithmselection.numberFieldLabel") + ":"
        );
        integerListField.setPrompt(MESSAGES.getMessage("algorithmselection.numberFieldPrompt"));
        startbtn.setText(MESSAGES.getMessage("algorithmselection.start"));
        randombtn.setText(MESSAGES.getMessage("algorithmselection.random"));

        matrixEditPanel.setTextToPanel(MESSAGES);

        graphSelection.removeAllItems();
        graphSelection.addItem(MESSAGES.getMessage(Kruskal.getName()));
        graphSelection.addItem(MESSAGES.getMessage(BreadthFirstSearch.getName()));
        graphSelection.addItem(MESSAGES.getMessage(DepthFirstSearch.getName()));
    }

    private void startSelectedAlgorithm() {
        if (tabbedPane.getSelectedIndex() == 0) {
            startSortingAlgorithm();
        } else {
            startGraphAlgorithm();
        }
    }

    private void startSortingAlgorithm() {
        int[] input = null;

        if (!integerListField.getText().isEmpty()) {
            String inputString = AlgorithmSelection.removeInvalidChars(integerListField.getText());
            input = InputGeneration.arrayFromString(inputString);
        }

        if (input == null) {
            JOptionPane.showMessageDialog(
                    this,
                    MESSAGES.getMessage("algorithmselection.error.nonumbers"),
                    MESSAGES.getMessage("algorithmselection.error"),
                    JOptionPane.ERROR_MESSAGE);
        } else if (input.length < 2) {
            JOptionPane.showMessageDialog(
                    this,
                    MESSAGES.getMessage("algorithmselection.error.oneElement"),
                    MESSAGES.getMessage("algorithmselection.error") , JOptionPane.INFORMATION_MESSAGE);
        } else if (input.length > maxCount) {
            String[] preparedValues = {String.valueOf(maxCount)};
            JOptionPane.showMessageDialog(
                    this,
                    MESSAGES.getPreparedMessage("algorithmselection.error.toMany", preparedValues),
                    MESSAGES.getMessage("algorithmselection.error"), JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (this.algorithmSelection.getSelectedItem().equals("Bubblesort")) {
                new SortingWindow(this, new BubbleSort(input));
            } else if (this.algorithmSelection.getSelectedItem().equals("Heapsort (Maxheap)")) {
                new HeapWindow(this, new HeapSort(input, false));
            } else if (this.algorithmSelection.getSelectedItem().equals("Heapsort (Minheap)")) {
                new HeapWindow(this, new HeapSort(input, true));
            } else if (this.algorithmSelection.getSelectedItem().equals("Quicksort")) {
                new SortingWindow(this, new QuickSort(input));
            } else if (this.algorithmSelection.getSelectedItem().equals("Radixsort")) {
                new SortingWindow(this, new RadixSort(input));
            } else if (this.algorithmSelection.getSelectedItem().equals("Selectionsort")) {
                new SortingWindow(this, new SelectionSort(input));
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        this.algorithmSelection.getSelectedItem() + " " + MESSAGES.getMessage("algorithmselection.error.notImplemented"),
                        MESSAGES.getMessage("algorithmselection.error"),
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void startGraphAlgorithm() {
        AdjacencyMatrix matrix = matrixEditPanel.getAdjacencyMatrix();
        GraphAlgorithm algorithm = null;

        Object selectedItem = graphSelection.getSelectedItem();
        if (selectedItem.equals(MESSAGES.getMessage(Kruskal.getName()))) {
            algorithm = new Kruskal();
        } else if (selectedItem.equals(MESSAGES.getMessage(BreadthFirstSearch.getName()))) {
            algorithm = new BreadthFirstSearch();
        } else if (selectedItem.equals(MESSAGES.getMessage(DepthFirstSearch.getName()))) {
            algorithm = new DepthFirstSearch();
        }

        if (Objects.nonNull(algorithm)) {
            new GraphWindow(this, algorithm, matrix);
        }
    }

    /**
     * The class for receiving input events in this kind of window
     * (AlgorithmSelection). When an input event occurs, the appropriate method
     * is invoked.
     */
    private class InputListener implements KeyListener {

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
         */
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            // invalid input events get consumed
            if (!((c >= '0') && (c <= '9') || (c == KeyEvent.VK_DELETE) || (c == KeyEvent.VK_COMMA))) {
                getToolkit().beep();
                e.consume();
            }
            if (c == KeyEvent.VK_COMMA) {
				/*
				 * when the last input was a comma, we do not need another
				 * one... only one comma is used to seperate numbers after all.
				 */
                if (integerListField.getText().isEmpty() || integerListField.getText().endsWith(",")) {
                    e.consume();
                    getToolkit().beep();
                }
            }

        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
         */
        public void keyPressed(KeyEvent e) {
            // TODO Auto-generated method stub

        }

        /*
         * (non-Javadoc)
         *
         * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
         */
        public void keyReleased(KeyEvent e) {
            // TODO Auto-generated method stub

        }

    }

}