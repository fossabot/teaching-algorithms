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

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.event.ChangeEvent;

import teachingalgorithms.ui.components.DragScrollListener;
import teachingalgorithms.ui.components.LongerTooltipListener;

/**
 * <p>
 *         The Class SortingWindow. This class serves as a template for the
 *         graphical representation of any algorithm that can be divided into
 *         steps in a gui frame. From here all important actions concerning the
 *         sorting algorithm can be performed and displayed.
 * </p>
 * @author Moritz Floeter
 */
public abstract class AlgorithmWindowSubstructure extends WindowSubstructure {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 1218755388476653348L;
    /**
     * The protocol list pnl.
     */
    protected JPanel protocolListPnl = new JPanel();
    /**
     * The drag scroll.
     */
    protected DragScrollListener dragScroll;
    /**
     * The next step.
     */
    protected JButton nextStep;
    /**
     * The undo step.
     */
    protected JButton undoStep;
    /**
     * The all steps.
     */
    protected JButton allSteps;
    /**
     * The export btn.
     */
    protected JButton exportBtn;
    /**
     * The info btn.
     */
    protected JButton infoBtn;
    /**
     * The reset.
     */
    protected JButton reset;
    /**
     * The protocol pnl.
     */
    private JPanel protocolPnl = new JPanel();
    /**
     * The scroll.
     */
    private JScrollPane scroll;
    /**
     * The tooltip checkbox.
     */
    private JCheckBox tooltipCheckbox;

    /**
     * Instantiates a new sorting window.
     *
     * @param parent the parent
     */
    public AlgorithmWindowSubstructure(JFrame parent) {
        super();

        nextStep = new JButton();
        undoStep = new JButton();
        allSteps = new JButton();
        exportBtn = new JButton();
        infoBtn = new JButton();
        reset = new JButton();
        tooltipCheckbox = new JCheckBox("", LongerTooltipListener.isActive());;

        JPanel mainpanel = new JPanel();
        this.getContentPane().add(mainpanel);
        mainpanel.setLayout(new BorderLayout());

        mainpanel.add(menuBar, BorderLayout.NORTH);

        LongerTooltipListener.addListener(e -> tooltipSettingChanged(tooltipCheckbox.isSelected()));
        tooltipCheckbox.addChangeListener(changeEvent -> activateTooltip(changeEvent));

        // Creating and inserting layouts for the right area of the window
        JPanel right = new JPanel();
        mainpanel.add(right, BorderLayout.EAST);
        right.setLayout(new BorderLayout());
        Box righttop = Box.createVerticalBox();
        JPanel righttopPanel = new JPanel();
        righttopPanel.setLayout(new GridLayout(0,1));

        // adding buttons
        righttopPanel.add(nextStep);
        righttopPanel.add(undoStep);
        righttopPanel.add(allSteps);
        righttopPanel.add(reset);
        righttopPanel.add(tooltipCheckbox);

        righttop.add(righttopPanel);

        right.add(righttop, BorderLayout.NORTH);
        Box rightbottom = Box.createVerticalBox();
        JPanel rightbottomPanel = new JPanel();
        rightbottomPanel.add(infoBtn);
        rightbottomPanel.add(exportBtn);
        rightbottom.add(rightbottomPanel);
        right.add(rightbottom, BorderLayout.SOUTH);

        // Creating the main area of the window where the protocol is shown
        scroll = new JScrollPane(protocolPnl);
        this.dragScroll = new DragScrollListener(protocolPnl);
        scroll.getVerticalScrollBar().setUnitIncrement(25);
        scroll.getHorizontalScrollBar().setUnitIncrement(25);

        protocolPnl.addMouseListener(dragScroll);
        protocolPnl.addMouseMotionListener(dragScroll);
        protocolPnl.setLayout(new BorderLayout());

        mainpanel.add(scroll, BorderLayout.CENTER);
        protocolPnl.setBackground(Color.white);

        // /Adding a list-style panel that will late contain the steps of the
        // protocol
        protocolListPnl.setBackground(Color.white);
        protocolListPnl.setLayout(new BoxLayout(protocolListPnl, BoxLayout.PAGE_AXIS));
        protocolListPnl.addMouseListener(dragScroll);
        protocolListPnl.addMouseMotionListener(dragScroll);

        // adding to the north because the protocol should start right at the
        // top of the area.
        protocolPnl.add(protocolListPnl, BorderLayout.NORTH);

        setTextToWindow();

        // setting default window parameters
        this.setMinimumSize(new Dimension(600, 450));
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
        setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    /**
     * adds panel to protocol panel.
     *
     * @param stepPanel the panel
     */
    protected void add2protocol(JPanel stepPanel) {
        // the panel pushleft serves the purpose of aligning the protocol to the
        // left side of the protocol area
        JPanel pushleft = new JPanel();
        pushleft.addMouseListener(dragScroll);
        pushleft.addMouseMotionListener(dragScroll);

        pushleft.setBackground(Color.WHITE);
        pushleft.setLayout(new BorderLayout());
        pushleft.add(stepPanel, BorderLayout.WEST);

        stepPanel.addMouseListener(dragScroll);
        stepPanel.addMouseMotionListener(dragScroll);

        protocolListPnl.add(pushleft);
    }

    /**
     * Remove last element from protocol.
     */
    protected void remFromProtocol() {
        protocolListPnl.remove(protocolListPnl.getComponentCount() - 1);
    }

    private void tooltipSettingChanged(boolean tooltipSettingValue) {
        tooltipCheckbox.setSelected(tooltipSettingValue);
    }

    /**
     * Scroll2 bottom.
     */
    protected void scroll2Bottom() {
        JScrollBar vertical = scroll.getVerticalScrollBar();
        vertical.setValue(vertical.getMaximum());
    }

    private void activateTooltip(ChangeEvent e) {
        if (e.getSource().equals(tooltipCheckbox)
                && tooltipCheckbox.isSelected() != LongerTooltipListener.isActive()) {
            LongerTooltipListener.setActive(tooltipCheckbox.isSelected());
        }
    }

    @Override
    protected void setTextToWindow() {
        super.setTextToWindow();
        nextStep.setText(MESSAGES.getMessage("algorithmwindowsubstructure.nextStep"));
        undoStep.setText(MESSAGES.getMessage("algorithmwindowsubstructure.undoStep"));
        allSteps.setText(MESSAGES.getMessage("algorithmwindowsubstructure.allSteps"));
        exportBtn.setText(MESSAGES.getMessage("algorithmwindowsubstructure.exportLatex"));
        infoBtn.setText(MESSAGES.getMessage("algorithmwindowsubstructure.additionalInfo"));
        reset.setText(MESSAGES.getMessage("algorithmwindowsubstructure.reset"));
        tooltipCheckbox.setText(MESSAGES.getMessage("algorithmwindowsubstructure.activateTooltip"));
    }

}
