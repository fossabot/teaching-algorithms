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

package teachingalgorithms.export.latex.graph;

import teachingalgorithms.export.latex.LatexExporter;
import teachingalgorithms.logic.graph.algorithm.DepthFirstSearch;
import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.logic.graph.protocol.step.Step;
import teachingalgorithms.logic.graph.util.Node;
import teachingalgorithms.ui.internationalisation.Messages;

import java.util.List;
import java.util.Objects;

public class LatexDepthFirstSearch extends LatexExporter {
    /**
     * base structure for Table.
     * <ul>
     *     <li>0,1 are TableHeader</li>
     *     <li>2 are the actual rows</li>
     * </ul>
     */
    private static final String laTex = "\\begin{tabular}{|l|c|}\n" +
            "\\hline" +
            " [0] & [1] \\\\\n" +
            "\\hline" +
            "[2]"+
            "\\end{tabular}";

    /**
     * Table row.
     * <ul>
     *     <li>0 Node list</li>
     *     <li>1 depth</li>
     * </ul>
     */
    private static final String laTexRow = " [0] & [1] \\\\\n" +
            "\\hline";

    @Override
    public String toLatex(StepByStepProtocol protocol, Messages messages) {
        String toReturn = laTex;

        String rows = "";
        for (Step step : protocol) {
            rows = rows.concat(stepAsRow(step));
        }

        toReturn = setTextToHeader(toReturn, messages);
        toReturn = toReturn.replace("[2]", rows);

        return toReturn;
    }

    protected String setTextToHeader(String laTex, Messages messages) {
        laTex = laTex.replace("[0]", messages.getMessage("exporter.latex.nodeList"));
        laTex = laTex.replace("[1]", messages.getMessage("exporter.latex.nodeCount"));
        return laTex;
    }

    private String stepAsRow(Step step) {
        String toReturn = laTexRow;
        Object vn = step.getAdditionalInformation(DepthFirstSearch.NODE_HEAP);
        String nodeList = "";
        if (Objects.nonNull(vn)) {
            List<Node> visitedNodes = (List<Node>) vn;
            for (Node node : visitedNodes) {
                nodeList = nodeList.concat(node.getName() + ", ");
            }
            nodeList = nodeList.substring(0, nodeList.length() - 2);
        } else {
            nodeList = "\\emptyset";
        }


        toReturn = toReturn.replace("[0]", nodeList);

        String depth = "";
        Object dObject = step.getAdditionalInformation(DepthFirstSearch.DEPTH);
        if (Objects.nonNull(dObject)) {
            depth = dObject.toString();
        }

        toReturn = toReturn.replace("[1]", depth);

        return toReturn;
    }
}
