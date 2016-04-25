package teachingalgorithms.ui.components;

import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * @author Moritz Floeter.
 *         <p>
 *         The Class LaTeXPanel. A JPanel that displays LaTeX expressions.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or
 *         (at your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful,
 *         but WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *         GNU General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class LaTeXPanel extends JPanel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7654861252773688599L;

    private static final float FONT_SIZE_TEX = 21;

    /**
     * The expression.
     */
    private String expression;

    /**
     * Instantiates a new LaTeX panel.
     *
     * @param expression the expression @param tooltip the tooltip
     */
    public LaTeXPanel(String expression, String tooltip) {
        super();
        this.expression = expression;
        this.addMouseListener(new LongerTooltipListener());
        this.setBackground(Color.white);
        if (tooltip != null) {
            this.setToolTipText(GeneralGuiFunctions.formatTip(tooltip));
        }
        this.render();
    }

    /**
     * Renders the formula.
     */
    public void render() {
        try {
            // create a formula
            TeXFormula formula = new TeXFormula(this.expression);
            TeXIcon ticon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, FONT_SIZE_TEX, TeXConstants.UNIT_PIXEL, 80,
                    TeXConstants.ALIGN_LEFT);
            this.add(new JLabel(ticon));

        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Fehler beim Rendern eines LaTeX-Elements",
                    JOptionPane.INFORMATION_MESSAGE);
        }

    }

}