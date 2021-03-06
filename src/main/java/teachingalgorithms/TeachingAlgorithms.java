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

package teachingalgorithms;

import teachingalgorithms.ui.windows.AlgorithmSelection;

import javax.swing.UIManager;

/**
 * <p>
 *         The Class Run. The Class run has the sole purpose of starting the program. It
 *         contains the main method that starts the main program as intended by the
 *         developer.
 * </p>
 * @author Moritz Floeter
 */
public class TeachingAlgorithms {

    /**
     * The main method. This method launches the program.
     *
     * @param args the arguments
     */
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        // Set System look and feel according to the current OS
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            //no "system like" style will be available
        }

        // opens a Algorithm Selection window.
        new AlgorithmSelection();
    }
}
