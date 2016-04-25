package teachingalgorithms.ui.windows;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdesktop.swingx.JXTextField;
import teachingalgorithms.logic.sorting.*;
import teachingalgorithms.logic.util.InputGeneration;
import teachingalgorithms.ui.components.GeneralGuiFunctions;
import teachingalgorithms.ui.components.RandomArrayGenerator;
import teachingalgorithms.ui.windows.sorting.HeapWindow;
import teachingalgorithms.ui.windows.sorting.SortingWindow;

/**
 * The Class AlgorithmSelection.
 *
 * @author Moritz Floeter
 *         <p>
 *         The Class AlgorithmSelection. This class is the one where it all
 *         starts. It provides the gui that the user sees right after the
 *         software is launched. Here the user can select the algorithm which he
 *         wants to use and input the array on which it should be used.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class AlgorithmSelection extends JFrame implements ActionListener {

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
    JButton startbtn = new JButton("<html>Start</html>");
    /**
     * The txtfield.
     */
    JXTextField txtfield = new JXTextField();
    /**
     * The selectitems.
     */
    String[] selectitems = {"Bubblesort", "Selectionsort", "Radixsort", "Quicksort", "Heapsort (Minheap)", "Heapsort (Maxheap)"};
    /**
     * The selectbx.
     */
    JComboBox<String> selectbx = new JComboBox<String>(selectitems);
    /**
     * The randombtn.
     */
    JButton randombtn = new JButton("<html>Zuf&auml;llige Zahlenfolge</html>");
    /**
     * The aboutbtn.
     */
    JButton aboutbtn = new JButton("About");


    /**
     * Instantiates a new algorithm selection window.
     */
    public AlgorithmSelection() {
        super("Sortieralgorithmen");

        Container mainpane = this.getContentPane();
        JPanel mainpanel = new JPanel();
        mainpanel.setLayout(new BorderLayout());
        JPanel mainelements = new JPanel();
        mainpanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainelements.setBorder(BorderFactory.createEtchedBorder(1));
        mainpanel.add(mainelements, BorderLayout.CENTER);
        mainpane.add(mainpanel);
        Box bottom = new Box(1);
        bottom.add(aboutbtn);
        JPanel bottomBorder = new JPanel();
        bottomBorder.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        bottomBorder.setLayout(new BorderLayout());
        bottomBorder.add(bottom, BorderLayout.EAST);
        mainpanel.add(bottomBorder, BorderLayout.SOUTH);
        mainelements.setLayout(new BorderLayout());

        // Actionlisteners get added to buttons
        this.startbtn.addActionListener(this);
        this.randombtn.addActionListener(this);
        this.aboutbtn.addActionListener(this);

        txtfield.addKeyListener(new InputListener());


		/*
         * create the panel where the algorithm selection and the startbutton
		 * gets displayed
		 */
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BorderLayout());
        buttonPanel.add(selectbx, BorderLayout.CENTER);
        buttonPanel.add(startbtn, BorderLayout.EAST);
        mainelements.add(buttonPanel, BorderLayout.SOUTH);

		/*
		 * create instructions for the user input and the button for generating
		 * a random input string
		 */
        JPanel northpanel = new JPanel(new BorderLayout());
        northpanel.add(randombtn, BorderLayout.EAST);
        northpanel.add(new JLabel("  zu sortierende Zahlenfolge eingeben:"), BorderLayout.WEST);
        mainelements.add(northpanel, BorderLayout.NORTH);

        // create form for textinput
        JPanel txtpnl = new JPanel();
        txtpnl.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        txtpnl.setLayout(new BorderLayout());
        txtpnl.add(txtfield, BorderLayout.CENTER);
        mainelements.add(txtpnl, BorderLayout.CENTER);
        txtfield.setPrompt("Bitte Zahlen durch Kommata getrennt eingeben (z.B.: 1,2,5,4...)");

        // Setting a few window attributes
        this.pack();
        int originalheight = (int) this.getSize().getHeight();
        this.setSize(new Dimension(400, originalheight));
        this.setMaximumSize(new Dimension(900000, originalheight));
        this.setMinimumSize(new Dimension(500, originalheight));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        GeneralGuiFunctions.centerWindow(this);

        this.setResizable(false);
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
        txtfield.setText(input);
        this.validate();
        this.repaint();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        // If start is clicked, the following action will get performed

        if (e.getSource().equals(startbtn)) {
            int[] input = null;

            if (!txtfield.getText().isEmpty()) {
                String inputString = AlgorithmSelection.removeInvalidChars(txtfield.getText());
                input = InputGeneration.arrayFromString(inputString);
            }

            if (input == null) {
                JOptionPane.showMessageDialog(this, "Zuerst sollten ein paar Zahlen eingegeben werden...", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            } else if (input.length < 2) {
                JOptionPane.showMessageDialog(this,
                        "<html>Fun Fact: <br>" + "alle einelementigen Arrays sind sowieso schon sortiert.</html>",
                        "Der Fehler sitzt vor dem Bildschirm", JOptionPane.INFORMATION_MESSAGE);
            } else if (input.length > AlgorithmSelection.maxCount) {
                JOptionPane.showMessageDialog(this,
                        "<html>Das eingegebene Array ist beinhaltet &uuml;ber " + maxCount
                                + " Elemente. <br> <br> Da jeder Schritt "
                                + "dargestellt werden muss und die Anzahl der Schritte mit der Gr&ouml;sse der Eingabe steigt, <br> sind "
                                + "nur Eingaben bis zu 100 Elementen zul&auml;ssig.<br> <br>"
                                + "Und mal im Ernst: du hattest doch auch nicht vor, dir das Ablaufprotokoll komplett anzuschauen, sondern"
                                + "<br>wolltest nur mal probieren, ob das Programm crasht ;)</html>",
                        "Fehler: Ist das dein Ernst?", JOptionPane.INFORMATION_MESSAGE);
            } else {
                if (this.selectbx.getSelectedItem().equals("Bubblesort")) {
                    new SortingWindow(this, new BubbleSort(input));
                } else if (this.selectbx.getSelectedItem().equals("Heapsort (Maxheap)")) {
                    new HeapWindow(this, new HeapSort(input, false));
                } else if (this.selectbx.getSelectedItem().equals("Heapsort (Minheap)")) {
                    new HeapWindow(this, new HeapSort(input, true));
                } else if (this.selectbx.getSelectedItem().equals("Quicksort")) {
                    new SortingWindow(this, new QuickSort(input));
                } else if (this.selectbx.getSelectedItem().equals("Radixsort")) {
                    new SortingWindow(this, new RadixSort(input));
                } else if (this.selectbx.getSelectedItem().equals("Selectionsort")) {
                    new SortingWindow(this, new SelectionSort(input));
                } else {
                    JOptionPane.showMessageDialog(this,
                            this.selectbx.getSelectedItem()
                                    + " sollte jetzt starten. Dieser Algorithmus ist allerdings noch nicht impementiert.",
                            "Start geklickt", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }

        // if the button for arraygeneration is clicked, the following action
        // will get performed
        if (e.getSource().equals(randombtn)) {
            // The calling instance gets passed to the new window so that the
            // arraygenerator can fill the according field with a random array
            new RandomArrayGenerator(this);
        }

        if (e.getSource().equals(aboutbtn)) {
            new AboutWindow(this);
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
                if (txtfield.getText().isEmpty() || txtfield.getText().endsWith(",")) {
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