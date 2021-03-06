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

package teachingalgorithms.io;

import java.io.File;
import java.io.FileWriter;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * <p>
 *         The Class SaveFile.
 *         Provides functionality for saving files. It is used to save latex files but
 *         can easily be used to save other textbased files aswell if that is needed
 *         in the future. If other LaTeX-packages are used in exported files, they
 *         should be added to the constant HEADER_LATEX
 * </p>
 * @author Moritz Floeter
 */
public class SaveFile {

    /**
     * The Constant HEADER_LATEX.
     */
    public static final String HEADER_LATEX = "\\documentclass{article} \n"
            + "\\usepackage{tikz} \n"
            + "\\usetikzlibrary{arrows,positioning, calc}\n"
            + "\\usetikzlibrary{trees} \n"
            + "\\usepackage{amsmath} \n"
            + "\\usepackage{amssymb} \n"
            + "\\usepackage{cancel} \n"
            + "\\newcommand\\Ccancel[2][black]{\\renewcommand\\CancelColor{\\color{#1}}\\xcancel{#2}} \n"
            + "\\tikzstyle{vertex}=[draw,fill=white,circle,minimum size=20pt,inner sep=0pt] \n\n"
            + "%\\Bearbeitet Format, sodass keine Einrueckung bei Paragraphen vorgenommen wird \n"
            + "\\usepackage{parskip} \n" + "\\setlength\\parindent{0pt} \n\n"
            + "\\begin{document} \n\n"
            + "%\\Tips fuer die Anpassung von Elementen, die nicht auf die Seite passen oder zu klein dargestellt werden:\n"
            + "%\\a) an sinnvollen Stellen Zeilenumbrueche einfuegen\n"
            + "%\\b) Verwendung von \\tiny um die Schriftgroesse zu verringern\n"
            + "%\\c) Verwendung von \\begin{smallmatrix} anstatt \\begin{matrix} bei Radixsort\n\n"
            + "%\\d) falls die Darstellung unter Verwendung der standardmaessig bei allen Verfahren ausser Radix "
            + "verwendeten \"smallmatrix\" kleiner als gewuenscht ist, kann die Schriftgroesse  beispielsweise "
            + "ueber \\huge angepasst werden. Der Wechsel zu \"matrix\" ist hier nicht empfehlenswert, da in dieser "
            + "unabhaengig von der Schriftgroesse immer maximal 10 Spalten dargestellt werden koennen.\n\n";

    /**
     * The Constant FOOTER_LATEX.
     */
    public static final String FOOTER_LATEX = "\n\\end{document}";

    /**
     * Saves the String passed to it as LaTeX document by also adding the
     * predefined header and footer of this class to the document.
     *
     * @param parent the parent
     * @param body   the body
     */
    public static void saveLaTeX(JFrame parent, String body) {
        saveFile(parent, "tex", HEADER_LATEX + body + FOOTER_LATEX);
    }

    /**
     * Save file. Shows a window that lets you choose a path for saving a file
     * and saves the text contained in the body-parameter in that path.
     *
     * @param parent the parent
     * @param type   the type
     * @param body   the body
     */
    public static void saveFile(JFrame parent, String type, String body) {
        FileWriter writer = null;
        try {
            File fileToSave = showSaveFileDialog(parent, type);
            if (fileToSave != null) {
                fileToSave.createNewFile();
                writer = new FileWriter(fileToSave.getAbsolutePath(), false);
                writer.write(body);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(parent, "Schreiben fehlgeschlagen",
                    "Fehler", JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (Exception e) {
                //TODO
            }

        }
    }

    /**
     * Show save file dialog.
     *
     * @param parent the parent
     * @param type   the type
     * @return the file
     */
    private static File showSaveFileDialog(JFrame parent, String type) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(type + "-Datei speichern");

        File fileToSave = null;
        boolean overwrite = false;

        do {
            int userSelection = fileChooser.showSaveDialog(parent);

            //only store a path if the user wanted to save the path
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                fileToSave = fileChooser.getSelectedFile();
            } else {
                fileToSave = null;
            }

            //append file-extension if not included in path
            if (fileToSave != null
                    && !fileToSave.getAbsolutePath().endsWith("." + type)) {
                fileToSave = new File(fileToSave.getAbsolutePath() + "." + type);
            }

            //ask before overwriting existing files
            if (fileToSave != null && fileToSave.exists()) {
                int reply = JOptionPane
                        .showConfirmDialog(
                                parent,
                                "<html> Datei existiert bereits. Wollen sie die bestehende Datei wirklich &uuml;berschreiben?</html>",
                                "Datei bereits vorhanden",
                                JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    overwrite = true;
                } else {
                    overwrite = false;
                }
            }

        } while (fileToSave != null && (fileToSave.exists() && !overwrite));

        return fileToSave;
    }
}
