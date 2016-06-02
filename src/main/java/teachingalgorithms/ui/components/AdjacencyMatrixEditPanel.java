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

import org.jdesktop.swingx.*;
import teachingalgorithms.logic.graph.util.AdjacencyMatrix;
import teachingalgorithms.logic.graph.util.Edge;
import teachingalgorithms.logic.graph.util.Node;
import teachingalgorithms.ui.i18n.I18n;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;
import java.util.List;

/**
 * To create or edit adjacency matrix
 * @author Jonathan Lechner
 */
public class AdjacencyMatrixEditPanel extends JXPanel {

    private AdjacencyMatrix adjacencyMatrix;

    private JXLabel addNodeLabel;

    private JXTextField nodeField;

    private JXLabel directedLabel;

    private JCheckBox directed;

    private GraphPanel graphPanel;

    private DeleteColumnTableModel tableModel;

    /**
     * builds frame
     */
    public AdjacencyMatrixEditPanel() {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));

        adjacencyMatrix = new AdjacencyMatrix();

        addNodeLabel = new JXLabel();
        nodeField = new JXTextField();
        //directedLabel = new JXLabel();
        //directed = new JCheckBox();

        nodeField.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent focusEvent) {

            }

            @Override
            public void focusLost(FocusEvent focusEvent) {
                updateNodes();
            }
        });
        //directed.addActionListener(actionEvent -> updateDirected());

        graphPanel = new GraphPanel("graph");

        tableModel = new DeleteColumnTableModel();
        JXTable table = new JXTable(tableModel) {
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                return (jTable, o, b, b1, i, i1) -> {
                    Component component = new JXLabel();
                    if (o instanceof Node) {
                        JXLabel label = new JXLabel();
                        Node node = (Node) o;
                        label.setText(node.getName());
                        component = label;
                    } else if (o instanceof Edge) {
                        JXTextField textField = new JXTextField();
                        Edge edge = (Edge) o;
                        if (Objects.nonNull(edge.getWeight())) {
                            textField.setText(edge.getWeight().toString());
                        }
                        component = textField;
                    }
                    return component;
                };
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }

            public Class getColumnClass(int column) {
                return getValueAt(0, column).getClass();
            }
        };

        JXTableHeader tableHeader = new JXTableHeader(table.getColumnModel());
        tableHeader.setVisible(true);
        tableHeader.setDefaultRenderer((jTable, o, b, b1, i, i1) -> {
            Component component = null;
            if (o instanceof String) {
                JXLabel label = new JXLabel();
                label.setText((String) o);
                component = label;
            }
            return component;
        });
        table.setTableHeader(tableHeader);

        tableModel.addColumn("\u2193", new Node[]{});
        table.setSortable(false);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(250, 200));
        table.setDefaultEditor(Edge.class, new WeightCellEditor());

        this.add(addNodeLabel);
        this.add(nodeField);
        //this.add(directedLabel);
        //this.add(directed);
        this.add(scrollPane);
        this.add(graphPanel);
    }

    /**
     * called on any change on nodes.
     */
    private void updateNodes() {
        List<Node> nodes = new ArrayList<>(adjacencyMatrix.getNodes());
        // remove not used chars
        String textFieldText = nodeField.getText().replaceAll("(?!([A-Z]))(?!([0-9]))(?!,).", "");
        nodeField.setText(textFieldText);
        // get Array from String
        String[] textFieldList = textFieldText.split(",");
        List<String> newNodes = new ArrayList<>(Arrays.asList(textFieldList));
        // remove any empty elements
        newNodes.removeIf(predicate -> predicate.equals(""));

        for (Node node : nodes) {
            if (!(newNodes.contains(node.getName()))) {
                // needs current index to remove the correct node in ui
                removeNodeUI(adjacencyMatrix.getNodes().indexOf(node));
                adjacencyMatrix.removeNode(node);
            } else {
                newNodes.remove(newNodes.indexOf(node.getName()));
            }
        }

        for (String reallyNewNode : newNodes) {
            try {
                Node newNode = new Node(reallyNewNode);
                adjacencyMatrix.addNode(newNode);
                addNodeUI(newNode, adjacencyMatrix.getNodes().indexOf(newNode));
            } catch (IllegalArgumentException exc) {
                //TODO error message
            }
        }
        graphPanel.updateMatrix(adjacencyMatrix);
    }

    /**
     * updates table on nodeChange (add).
     * @param node
     * @param index
     */
    private void addNodeUI(Node node, int index) {
        tableModel.insertRow(index, new Node[]{node});
        tableModel.addColumn(node.getName(), adjacencyMatrix.getEdges().get(index).toArray());
        //TODO add values to row for bidirectional graphs
    }

    /**
     * updates table on nodeChange (remove).
     * @param index
     */
    private void removeNodeUI(int index) {
        tableModel.removeRow(index);
        tableModel.removeColumn(index + 1);
    }

    /**
     * updates directed
     */
    private void updateDirected() {
        boolean isDirected = directed.isSelected();
        adjacencyMatrix.setDirected(isDirected);
    }

    /**
     * like set text to window.
     * @param message
     */
    public void setTextToPanel(I18n message) {
        addNodeLabel.setText(message.getMessage("adjacencymatrixeditpanel.addNodeLabel"));
        //directedLabel.setText(message.getMessage("adjacencymatrixeditpanel.directedLabel"));
        //directed.setText(message.getMessage("adjacencymatrixeditpanel.directedCheckbox"));
    }

    public AdjacencyMatrix getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    private class DeleteColumnTableModel extends DefaultTableModel {

        /**
         *
         * http://stackoverflow.com/questions/5938436/removing-column-from-tablemodel-in-java
         * @param column
         */
        private void removeColumn(int column) {
            columnIdentifiers.remove(column);
            for (Object row: dataVector) {
                ((Vector) row).remove(column);
            }
            fireTableStructureChanged();
        }
    }

    class WeightCellEditor extends AbstractCellEditor implements TableCellEditor {

        Component component;

        Object object;

        @Override
        public Component getTableCellEditorComponent(JTable jtable, Object o, boolean isSelected, int row, int column) {
            object = o;
            component = new JXLabel();
            if (object instanceof Edge) {
                IntegerField field = new IntegerField(0, Integer.MAX_VALUE);
                Edge edge = (Edge) o;
                if (Objects.nonNull(edge.getWeight())) {
                    field.setText(edge.getWeight().toString());
                }
                field.addFocusListener(new FocusListener() {
                    @Override
                    public void focusGained(FocusEvent focusEvent) {

                    }

                    @Override
                    public void focusLost(FocusEvent focusEvent) {
                        edge.setWeight(field.getInt());
                        graphPanel.updateMatrix(adjacencyMatrix);
                    }
                });
                component = field;
            }
            return component;
        }

        @Override
        public Object getCellEditorValue() {
            return object;
        }
    }

}
