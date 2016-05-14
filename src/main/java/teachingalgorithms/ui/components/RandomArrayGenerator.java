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

package teachingalgorithms.ui.components;

import teachingalgorithms.logic.util.InputGeneration;
import teachingalgorithms.ui.windows.AlgorithmSelection;
import teachingalgorithms.ui.windows.WindowSubstructure;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * <p>
 *         This class provides the gui for creating a randomized array.
 * </p>
 * @author Moritz Floeter
 */
public class RandomArrayGenerator extends WindowSubstructure{

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -2004873389331204943L;

    private JPanel parameters;
    /**
     * The number count.
     */
    private IntegerField numberCount;

    /**
     * The max number value.
     */
    private IntegerField maxNumberValue;

    /**
     * The min number value.
     */
    private IntegerField minNumberValue;

    /**
     * The generate btn.
     */
    private JButton generateBtn;

    /**
     * The cancel btn.
     */
    private JButton cancelBtn;

    /**
     * The selection.
     */
    private AlgorithmSelection selection;

    private JLabel infoLabel;

    private JLabel elementCountLabel;

    private JLabel minValueLabel;

    private JLabel maxValueLabel;

    /**
     * Instantiates a new random array generator.
     *
     * @param selection the selection
     */
    public RandomArrayGenerator(AlgorithmSelection selection) {
        super();

        this.selection = selection;

        infoLabel = new JLabel();
        elementCountLabel = new JLabel();
        minValueLabel = new JLabel();
        maxValueLabel = new JLabel();
        generateBtn = new JButton();
        cancelBtn = new JButton();
        numberCount = new IntegerField(2, AlgorithmSelection.maxCount);
        maxNumberValue = new IntegerField(0, AlgorithmSelection.maxValue);
        minNumberValue = new IntegerField(0, AlgorithmSelection.maxValue);


        JPanel mainpanel = new JPanel(new BorderLayout());
        parameters = new JPanel();
        JPanel buttons = new JPanel();

        parameters.setLayout(new GridLayout(0, 1));
        parameters.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        buttons.setLayout(new GridLayout(1, 1));
        buttons.add(cancelBtn);
        buttons.add(generateBtn);
        mainpanel.add(buttons, BorderLayout.SOUTH);

        generateBtn.addActionListener(actionEvent -> generateAndClose(actionEvent));
        cancelBtn.addActionListener(actionEvent -> generateAndClose(actionEvent));
        this.getContentPane().add(mainpanel);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // default values
        minNumberValue.setText("0");
        maxNumberValue.setText("99");
        numberCount.setText("10");

        parameters.add(infoLabel);
        parameters.add(elementCountLabel);
        parameters.add(numberCount);
        parameters.add(minValueLabel);
        parameters.add(minNumberValue);
        parameters.add(maxValueLabel);
        parameters.add(maxNumberValue);
        mainpanel.add(parameters, BorderLayout.CENTER);

        setTextToWindow();
        /*
         * Sets location to the place where the calling selection window is and
         * disables + hides the selection window
         */
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(selection);
        selection.setVisible(false);
        selection.setEnabled(false);
        this.setVisible(true);
    }

    public void generateAndClose(ActionEvent e) {
        boolean close = true;

        if (e.getSource().equals(generateBtn)) {
            numberCount.validateValue();
            minNumberValue.validateValue();
            maxNumberValue.validateValue();
            Integer count = numberCount.getInt();
            Integer min = minNumberValue.getInt();
            Integer max = maxNumberValue.getInt();

            if(Objects.nonNull(count) && Objects.nonNull(min) && Objects.nonNull(max)) {
                /*
                 * If the input is valid, the String gets generated and set in the
                 * selection window from which this instance of the
                 * RandomArrayGenerator was called. Otherwise - if the input is not
                 * valid, an according message gets displayed and the
                 * RandomArrayGenerator does not close.
                 */

                if (!(max < min)) {
                    selection.setInput(InputGeneration.generate(max, min, count));
                } else {
                    JOptionPane.showMessageDialog(null, MESSAGES.getMessage("algorithmselection.randomarray.minOverMax"));
                    close = false;
                }
            } else {
                close = false;
                JOptionPane.showMessageDialog(null, MESSAGES.getMessage("algorithmselection.randomarray.notAllSet"));
            }


        }
        /*
         * when the RandomArrayGenerator closes, the selection-window from where
         * it was called gets reactivated
         */
        if (close) {
            selection.setEnabled(true);
            selection.setVisible(true);
            this.dispose();
        }

    }

    @Override
    protected void setTextToWindow() {
        super.setTextToWindow();

        this.setTitle(MESSAGES.getMessage("algorithmselection.randomarray.titel"));
        generateBtn.setText(MESSAGES.getMessage("algorithmselection.randomarray.generate"));
        cancelBtn.setText(MESSAGES.getMessage("algorithmselection.randomarray.cancle"));
        infoLabel.setText(MESSAGES.getMessage("algorithmselection.randomarray.infoLabel"));
        String[] prepCount = {String.valueOf(AlgorithmSelection.maxCount)};
        elementCountLabel.setText(MESSAGES.getPreparedMessage("algorithmselection.randomarray.elementCountLabel", prepCount));
        minValueLabel.setText(MESSAGES.getMessage("algorithmselection.randomarray.minValueLabel"));
        String[] prepVal = {String.valueOf(AlgorithmSelection.maxValue)};
        maxValueLabel.setText(MESSAGES.getPreparedMessage("algorithmselection.randomarray.maxValueLabel", prepVal));
    }
}
