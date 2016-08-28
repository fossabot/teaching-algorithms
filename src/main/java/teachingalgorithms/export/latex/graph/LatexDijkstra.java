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
import teachingalgorithms.logic.graph.protocol.StepByStepProtocol;
import teachingalgorithms.logic.graph.protocol.step.Step;
import teachingalgorithms.logic.graph.util.Node;
import teachingalgorithms.ui.internationalisation.Messages;

import java.util.List;
import java.util.Map;

import static teachingalgorithms.logic.graph.algorithm.Dijkstra.*;

public class LatexDijkstra extends LatexExporter {
    
    /**
     * base structure for Table.
     * <ul>
     *     <li>0,1,2 are TableHeader</li>
     *     <li>3 are the actual rows</li>
     * </ul>
     */
    private static final String laTex = "\\begin{tabular}{|c|l|l|}\n" +
            "\\hline" +
            " [0] & [1] & [2]\\\\\n" +
            "\\hline" +
            "[3]"+
            "\\end{tabular} \\\\\\\\";

    /**
     * Table row.
     * <ul>
     *     <li>0 current node</li>
     *     <li>1 border nodes</li>
     *     <li>2 unvisited nodes</li>
     * </ul>
     */
    private static final String laTexRow = " [0] & [1] & [2] \\\\\n" +
            "\\hline";

    @Override
    public String toLatex(StepByStepProtocol protocol, Messages messages) {
        String toReturn = laTex;

        String rows = "";
        for (Step step : protocol) {
            rows = rows.concat(stepAsRow(step));
        }

        toReturn = setTextToHeader(toReturn, messages);
        toReturn = toReturn.replace("[3]", rows);

        return toReturn;
    }

    private String setTextToHeader(String laTex, Messages messages) {
        laTex = laTex.replace("[0]", messages.getMessage("exporter.latex.currentNode"));
        laTex = laTex.replace("[1]", messages.getMessage("exporter.latex.borderNodes"));
        laTex = laTex.replace("[2]", messages.getMessage("exporter.latex.unvisitedNodes"));
        return laTex;
    }

    private String stepAsRow(Step step) {
        String toReturn = laTexRow;

        Map<Node, Node> previous = (Map<Node, Node>) step.getAdditionalInformation(PREVIOUS_NODES);
        Map<Node, Long> distances =  (Map<Node, Long>) step.getAdditionalInformation(CURRENT_DISTANCES);

        Node currentNode = (Node) step.getAdditionalInformation(CURRENT_NODE);

        List<Node> borderNodes = (List<Node>) step.getAdditionalInformation(BORDER_NODES);
        List<Node> unvisitedNodes = (List<Node>) step.getAdditionalInformation(UNVISITED_NODES);

        String borderText = "";
        String unvisitedText = "";

        for (Node n : borderNodes) {
            borderText = borderText.concat(toDistanceString(n, previous, distances) + ", ");
        }

        for (Node n : unvisitedNodes) {
            unvisitedText = unvisitedText.concat(n.getName() + ", ");
        }

        if (borderText.length() > 2) {
            borderText = borderText.substring(0, borderText.length() - 2);
        }

        if (unvisitedText.length() > 2) {
            unvisitedText = unvisitedText.substring(0, unvisitedText.length() - 2);
        }

        toReturn = toReturn.replace("[0]", toDistanceString(currentNode, previous, distances));
        toReturn = toReturn.replace("[1]", borderText);
        toReturn = toReturn.replace("[2]", unvisitedText);

        return toReturn;
    }

    private String toDistanceString(Node node, Map<Node, Node> previous, Map<Node, Long> distances) {
        return "(" + node.getName() + ", " + distances.get(node) + ", " + previous.get(node).getName() + ")";
    }

}
