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

package teachingalgorithms.ui.windows.sorting;

import teachingalgorithms.logic.sorting.step.HeapStep;
import teachingalgorithms.ui.components.GeneralGuiFunctions;
import teachingalgorithms.ui.components.LongerTooltipListener;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * <p>
 *         The Class HeapSortPanelExtended. This provides a more detailed view of a
 *         HeapStep by adding the currently sorted array as well as other additional
 *         information
 * </p>
 * @author Moritz Floeter
 */
public class HeapSortPanelExtended extends JPanel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = 6747671448724713302L;

    /**
     * The array label. This gui element contains the elements that already have
     * been sorted into the list of sorted elements.
     */
    JLabel arrayLabel = new JLabel("");

    /**
     * The hpanel. This contains the binary tree.
     */
    JPanel hpanel = new JPanel();

    /**
     * Instantiates a new heap sort panel extended.
     *
     * @param hstep the hstep
     */
    public HeapSortPanelExtended(HeapStep hstep) {
        super();
        this.setLayout(new BorderLayout());

        arrayLabel = new JLabel(generateArrayString(hstep));

        arrayLabel.setBackground(Color.white);
        this.setBackground(Color.white);

        if (hstep.getStep() != null) {
            hpanel = new HeapSortPanel(hstep);
        }

        hpanel.setBackground(Color.white);

        LongerTooltipListener listener = new LongerTooltipListener();
        hpanel.addMouseListener(listener);
        arrayLabel.addMouseListener(listener);

        String formattedToolTip = GeneralGuiFunctions.formatTip(hstep.getExplanation());
        arrayLabel.setToolTipText(formattedToolTip);
        hpanel.setToolTipText(formattedToolTip);
        this.add(arrayLabel, BorderLayout.NORTH);
        this.add(hpanel, BorderLayout.WEST);
    }

    /**
     * Generate array string. Generates the String for the arrayLabel. The
     * String generated will contain a list of all elements that have already
     * been added to the sorted list as well as other information.
     *
     * @param hstep the hstep @return the string
     */
    private String generateArrayString(HeapStep hstep) {
        String array = "<html>..........................";
        if (hstep.getStep() == null) {
            array += "<br>&nbsp;<font face=\"verdana\" size=\"4\">Fertig sortiert! :) </font><br>";
        } else if ((hstep.getMemory() == (hstep.getStep().length / 2 - 1)) && hstep.getSortedList().isEmpty()) {
            array += "<br>&nbsp;<font face=\"verdana\" size=\"14\">Heap erstellen</font><br>.........................."
                    + "<br>&nbsp;<font face=\"verdana\" size=\"4\">Urspr&uuml;nglicher Bin&auml;rbaum</font>";
        } else if (hstep.getMemory() == (hstep.getStep().length / 2 - 2) && hstep.getSortedList().isEmpty()) {
            array += "<br>&nbsp;<font face=\"verdana\" size=\"4\"> &gt;__&lt; := zu versickerndes Element</font>";
        }

        if (!hstep.getSortedList().isEmpty()) {
            if (hstep.getSortedList().size() == 1) {
                array += "<br>&nbsp;<font face=\"verdana\" size=\"14\">Sortieren</font><br>..........................";
            }
            array += "<br>&nbsp;<font face=\"verdana\" size=\"4\">sortiertes Array: [";
            for (int i = 0; i < hstep.getSortedList().size(); i++) {
                array += hstep.getSortedList().get(i);
                if (i < hstep.getSortedList().size() - 1) {
                    array += ", ";
                }
            }
            array += "]</font>";
            arrayLabel = new JLabel(array);
        }
        array += "</html>";
        return array;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#addMouseListener(java.awt.event.MouseListener)
     */
    public void addMouseListener(MouseListener listen) {
        super.addMouseListener(listen);
        hpanel.addMouseListener(listen);
        arrayLabel.addMouseListener(listen);
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.awt.Component#addMouseMotionListener(java.awt.event.
     * MouseMotionListener )
     */
    public void addMouseMotionListener(MouseMotionListener listen) {
        super.addMouseMotionListener(listen);
        hpanel.addMouseMotionListener(listen);
        arrayLabel.addMouseMotionListener(listen);
    }

}
