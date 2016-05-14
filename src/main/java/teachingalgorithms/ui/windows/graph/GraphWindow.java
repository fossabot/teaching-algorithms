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

package teachingalgorithms.ui.windows.graph;

import teachingalgorithms.export.latex.LatexExporter;
import teachingalgorithms.export.latex.graph.LatexKruskal;
import teachingalgorithms.io.SaveFile;
import teachingalgorithms.logic.graph.algorithm.GraphAlgorithm;
import teachingalgorithms.logic.graph.algorithm.Kruskal;
import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.ui.components.GraphPanel;
import teachingalgorithms.ui.components.LaTeXPanel;
import teachingalgorithms.ui.windows.AlgorithmWindowSubstructure;

import javax.swing.*;
import java.util.Objects;

/**
 * <p> The Class GraphWindow. This Class is meant for displaying all graph algorithms. </p>
 * @author Jonathan Lechner
 */
public class GraphWindow extends AlgorithmWindowSubstructure {

    private StepByStepProtocol protocol;

    private AdjacencyMatrix matrix;

    private GraphAlgorithm algorithm;

    private LatexExporter exporter;

    private GraphPanel graphPanel;

    private int stepCount = 0;

    /**
     * constructs the window and runs the algorithm on provided graph.
     * @param parent parent window.
     * @param graphAlgorithm the graph algorithm to apply.
     * @param matrix the grap represented as adjacency matrix.
     */
    public GraphWindow(JFrame parent, GraphAlgorithm graphAlgorithm, AdjacencyMatrix matrix) {
        super(parent);
        //this.setTitle(algorithm.getClass().getName());
        this.matrix = matrix;
        this.algorithm = graphAlgorithm;
        this.protocol = graphAlgorithm.applyAlgorithm(matrix);

        if (algorithm instanceof Kruskal) {
            exporter = new LatexKruskal();
        }
        nextStep.addActionListener(actionEvent -> showNextStep());
        undoStep.addActionListener(actionEvent -> hideLastStep());
        allSteps.addActionListener(actionEvent -> showAllSteps());
        reset.addActionListener(actionEvent -> hideAllSteps());
        infoBtn.addActionListener(actionEvent -> showInfo());
        exportBtn.addActionListener(actionEvent -> exportToLatex());

        graphPanel = new GraphPanel("");
    }

    /**
     * called by button to display next step out of protocol.
     */
    private void showNextStep() {
        this.protocolListPnl.removeAll();
        this.protocolListPnl.add(new LaTeXPanel(exporter.toLatex(protocol.subList(0, ++stepCount), MESSAGES), graphPanel));
        graphPanel.updateMatrix(protocol.get(stepCount - 1).getMatrix());
        if (stepCount >= protocol.size()) {
            nextStep.setEnabled(false);
        }

        this.validate();
        this.repaint();
    }

    /**
     * called by button to hide last displayed step of protocol again.
     */
    private void hideLastStep() {
        if (stepCount > 0) {
            this.protocolListPnl.removeAll();
            this.protocolListPnl.add(new LaTeXPanel(exporter.toLatex(protocol.subList(0, --stepCount), MESSAGES), graphPanel));
            graphPanel.updateMatrix(protocol.get(stepCount).getMatrix());
            if (stepCount < protocol.size()) {
                nextStep.setEnabled(true);
            }

            this.validate();
            this.repaint();
        }
    }

    /**
     * called by button to display all steps out of protocol
     */
    private void showAllSteps() {
        this.protocolListPnl.removeAll();
        this.protocolListPnl.add(new LaTeXPanel(exporter.toLatex(protocol, MESSAGES), graphPanel));
        stepCount = protocol.size();
        graphPanel.updateMatrix(protocol.get(stepCount - 1).getMatrix());

        if (stepCount >= protocol.size()) {
            nextStep.setEnabled(false);
        }

        this.validate();
        this.repaint();
    }

    /**
     * called by button to hide all step out of protocol again
     */
    private void hideAllSteps() {
        this.protocolListPnl.removeAll();
        stepCount = 0;

        if (stepCount < protocol.size()) {
            nextStep.setEnabled(true);
        }

        this.validate();
        this.repaint();
    }

    /**
     * called by button to open separately provided info file
     */
    private void showInfo() {

    }

    /**
     * called by button to save protocol as LaTeX
     */
    private void exportToLatex() {
        SaveFile.saveFile(this, "tex", exporter.export(protocol, MESSAGES));
    }

    @Override
    protected void setTextToWindow() {
        super.setTextToWindow();

        if (Objects.nonNull(exporter) && Objects.nonNull(exporter) && Objects.nonNull(protocolListPnl)) {
            this.protocolListPnl.removeAll();
            this.protocolListPnl.add(new LaTeXPanel(exporter.toLatex(protocol.subList(0, stepCount), MESSAGES), graphPanel));

            this.validate();
            this.repaint();
        }
    }
}
