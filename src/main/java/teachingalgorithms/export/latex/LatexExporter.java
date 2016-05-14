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

package teachingalgorithms.export.latex;

import teachingalgorithms.export.Exporter;
import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.ui.i18n.I18n;

/**
 * base structure for Latex documents.
 * @author Jonathan Lechner.
 */
public class LatexExporter implements Exporter {

    private static final String document = "\\documentclass[10pt,a4paper,landscape]{article}\n" +
            "\\usepackage[utf8]{inputenc}\n" +
            "\\usepackage{amsmath}\n" +
            "\\usepackage{amsfonts}\n" +
            "\\usepackage{amssymb}\n" +
            "\\usepackage{lscape}\n" +
            "\\begin{document}\n" +
            "[protocol]\n" +
            "\\end{document}";

    /**
     * constructs latex element.
     * @param protocol stepByStep of algorithm
     * @param messages I18n selected
     * @return latex element
     */
    public String toLatex(StepByStepProtocol protocol, I18n messages) {
        return "";
    }

    @Override
    public String export(StepByStepProtocol protocol, I18n messages) {
        return document.replace("[protocol]", toLatex(protocol, messages));
    }
}
