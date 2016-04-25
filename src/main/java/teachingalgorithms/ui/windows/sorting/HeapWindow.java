package teachingalgorithms.ui.windows.sorting;

import teachingalgorithms.io.SaveFile;
import teachingalgorithms.logic.sorting.HeapSort;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Moritz Floeter
 *
 * The Class HeapWindow. A gui class for displaying heapsort and perfoming steps
 * with that algorithm.
 *
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

public class HeapWindow extends SortingWindowSubstructure implements ActionListener {

    private static final long serialVersionUID = -7777097415364226306L;

    /** The HeapSort. */
    private HeapSort hsort;

    /**
     * Instantiates a new bubble window.
     *
     * @param parent the parent window @param hsort the hsort
     */
    public HeapWindow(JFrame parent, HeapSort hsort) {
        super(parent);
        this.setTitle("HeapSort");
        this.hsort = hsort;
        nextStep.addActionListener(this);
        undoStep.addActionListener(this);
        allSteps.addActionListener(this);
        reset.addActionListener(this);
        exportBtn.addActionListener(this);
        infoBtn.addActionListener(this);

        this.add2protocol(new HeapSortPanelExtended(hsort.getStep(hsort.getProtocolSize()-1)));

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(nextStep)) {
            // if a step gets successfully performed, its graphical
            // representation
            // is added to the gui
            if (hsort.doStep()) {
                this.add2protocol(new HeapSortPanelExtended(hsort.getStep(hsort.getProtocolSize()-1)));
                this.validate();
                this.repaint();
                this.scroll2Bottom();
                // a message gets displayed if no step was performed.
            } else {
                JOptionPane.showMessageDialog(this, "<html>Fertig sortiert. Kein weiterer Schritt m&ouml;glich.</html>",
                        "Hinweis", JOptionPane.INFORMATION_MESSAGE);
            }
            /*
             * If allSteps is clicked, the sorting algorithm runs until it
             * terminates. The resulting steps then get added to the gui
             */
        } else if (e.getSource().equals(allSteps)) {
            hsort.doAllSteps();
            for (int i = this.protocolListPnl.getComponentCount(); i < hsort.getProtocolSize(); i++) {
                this.add2protocol(new HeapSortPanelExtended(hsort.getStep(i)));
            }
            this.validate();
            this.repaint();
            /*
             * If undoStep is clicked, the last steps gets removed from both gui
             * and the protocol. The 1st step cant be removed
             */
        } else if (e.getSource().equals(undoStep)) {
            if (hsort.undoStep()) {
                this.remFromProtocol();
                this.validate();
                this.repaint();
            } else {
                JOptionPane.showMessageDialog(this,
                        "<html>Es kann kein weiterer Schritt zur&uuml;ck gegangen werden...</html>", "Fehler",
                        JOptionPane.ERROR_MESSAGE);
            }
            // if reset is clicked, all steps but the 1st one get removed from
            // gui and protocol
        } else if (e.getSource().equals(reset)) {
            this.hsort.reset();
            while (this.protocolListPnl.getComponentCount() > 1) {
                this.remFromProtocol();
            }
            this.validate();
            this.repaint();
            // the export button triggers the export
        } else if (e.getSource().equals(exportBtn)) {
            if (this.hsort.getInputSize() > 20) {
                int reply = JOptionPane.showConfirmDialog(this,
                        "<html> Sie versuchen das Sortierprotokoll eines grossen Arrays (>20 Elemente) zu exportieren. <br>"
                                + " Zwar ist es immer m&ouml;glich eine .tex-Datei zu erstellen, jedoch ist LaTeX ein Format,<br>"
                                + " das f&uuml;r die Ausgabe von Formaten gedacht ist, die sich gut auf Papiergr&ouml;sse bringen lassen. <br>"
                                + " Dadurch kann die .tex-Datei gegebenenfalls durch ihren LaTeX-Setzer nicht oder nur fehlerhaft gesetzt werden."
                                + "<br> <br> Wollen Sie die Datei dennoch erstellen? </html>",
                        "Arraygroesse problematisch", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    SaveFile.saveLaTeX(this, this.hsort.protocol2LaTeX());
                }
            } else {
                SaveFile.saveLaTeX(this, this.hsort.protocol2LaTeX());
            }
            // the info button opens the according pdf with information about
            // the algorithm
        } else if (e.getSource().equals(infoBtn)) {
            try {
                Desktop.getDesktop().open(new File("info/heapsort.pdf"));
            } catch (Exception exc) {
                JOptionPane.showMessageDialog(this,
                        "<html> Das &Ouml;ffnen der Zusatzinformation 'heapsort.pdf' ist fehlgeschlagen. M&ouml;glicherweise <br>"
                                + "wurde die Datei durch den Nutzer gel&ouml;scht oder es ist kein geeignetes Anzeigeprogramm vorhanden.",
                        "Fehler", JOptionPane.ERROR_MESSAGE);
            }
        }

    }

}