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

import org.jdesktop.swingx.JXLabel;
import org.jdesktop.swingx.JXPanel;
import org.scilab.forge.jlatexmath.TeXConstants;
import org.scilab.forge.jlatexmath.TeXFormula;
import org.scilab.forge.jlatexmath.TeXIcon;

import java.awt.*;

/**
 * <p>
 *         The Class LaTeXPanel. A JPanel that displays LaTeX expressions.
 * </p>
 * @author Moritz Floeter.
 */
public class LaTeXPanel extends JXPanel {

    /**
     * The Constant serialVersionUID.
     */
    private static final long serialVersionUID = -7654861252773688599L;

    private static final float FONT_SIZE_TEX = 21;

    private static final float SMALLMATRIX_FONT_SIZE_MULTIPLIER = 1.43f;

    private Component toolTipComponent;

    /**
     * The expression.
     */
    private String expression;

    /**
     * Instantiates a new LaTeX panel.
     *
     * @param expression the expression @param tooltip the tooltip
     */
    public LaTeXPanel(String expression) {
        super();
        this.expression = expression;
        this.setBackground(Color.white);
        this.render();
    }

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
     * Instantiates a new LaTeX panel.
     *
     * @param expression the expression @param tooltip the tooltip
     */
    public LaTeXPanel(String expression, Component tooltip) {
        super();
        this.expression = expression;
        this.addMouseListener(new LongerTooltipListener());
        this.setBackground(Color.white);
        this.toolTipComponent = tooltip;
        this.render();
    }

    /**
     * Renders the formula.
     */
    public void render() {
        try {
            // create a formula
            TeXFormula formula = new TeXFormula(this.expression);
            float multiplier = 1;
            if (expression.contains("\\begin{smallmatrix}")){
                multiplier = SMALLMATRIX_FONT_SIZE_MULTIPLIER;
            }
            TeXIcon ticon = formula.createTeXIcon(TeXConstants.STYLE_DISPLAY, FONT_SIZE_TEX * multiplier, TeXConstants.UNIT_PIXEL, 80,
                    TeXConstants.ALIGN_LEFT);
            this.add(new JXLabel(ticon));

        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public Component getToolTipComponent() {
        return toolTipComponent;
    }
}