package teachingalgorithms.ui.windows;

import teachingalgorithms.ui.components.LaTeXPanel;
import teachingalgorithms.ui.components.MonoTextArea;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

/**
 * @author Moritz Floeter
 *         <p>
 *         The Class AboutWindow. This class represents the about window of the
 *         program.
 *         <p>
 *         --------------------------------------------------------------------
 *         This program is free software: you can redistribute it and/or modify
 *         it under the terms of the GNU General Public License as published by
 *         the Free Software Foundation, either version 3 of the License, or (at
 *         your option) any later version.
 *         <p>
 *         This program is distributed in the hope that it will be useful, but
 *         WITHOUT ANY WARRANTY; without even the implied warranty of
 *         MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 *         General Public License for more details.
 *         <p>
 *         You should have received a copy of the GNU General Public License
 *         along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

public class AboutWindow extends JFrame {

    private static final long serialVersionUID = -8640373993891704839L;

    /**
     * The Constant copyrightInformation.
     */
    private static final String copyrightInformation = "\n  This program serves the purpose of teaching and learning different algorithms\n"
            + "  and was developed during my studies at the University of Hildesheim.\n\n"
            + "  If needed, you can contact me via the contact form on my website (moritzf.de)\n"
            + "  Feedback and small donations can also be given through that website." + "\n\n"
            + "  -----------------------------------------------------------------------------\n" + ""
            + "  This program is licensed under the terms of the GPL. \n\n" + ""
            + "    Copyright (C) 2015  Moritz Floeter\n\n" + ""
            + "    This program is free software: you can redistribute it and/or modify\n"
            + "    it under the terms of the GNU General Public License as published by\n"
            + "    the Free Software Foundation, either version 3 of the License, or\n"
            + "    (at your option) any later version.\n\n" + ""
            + "    This program is distributed in the hope that it will be useful,\n"
            + "    but WITHOUT ANY WARRANTY; without even the implied warranty of\n"
            + "    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the\n"
            + "    GNU General Public License for more details.\n\n" + ""
            + "    You should have received a copy of the GNU General Public License\n"
            + "    along with this program.  If not, see <http://www.gnu.org/licenses/>.\n\n"
            + "  -----------------------------------------------------------------------\n"
            + "  Furthermore, this program uses other open source software:\n\n" + ""
            + "    JLaTeXMath - a library to render and display LaTeX-Elements\n\n"
            + "        Copyright (C) 2009-2011 DENIZET Calixte\n" + "        Copyright (C) Kris Coolsaet\n"
            + "        Copyright (C) Nico Van Cleemput\n" + "        Copyright (C) Kurt Vermeulen\n"
            + "        Copyright 2004-2007 Universiteit Gent\n\n" + "    licensed under the terms of GPL v3\n\n\n" + ""
            + "    DragScrollListner - a class for enabling scrolling by touchinput\n\n"
            + "        Copyright (C) Greg Cope\n\n" + "    licensed under the terms of GPL v3 \n\n\n" + ""
            + "    SwingX - a library for various GUI-Elements\n\n"
            + "        Copyright (C) 2005-2006 Sun Microsystems\n\n"
            + "    licensed under the terms of GNU Lesser GPL v2.1\n\n";

    /**
     * The Constant nameAsLaTeX. This represents the header shown above the
     * license information
     */
    private static final String nameAsLaTeX = "Sortieralgorithmen \\quad V \\,1.1beta3"
            + "\\\\  \\scriptsize ©\\,2015-2016\\,by\\, Moritz\\, Fl\\ddot{o}ter";

    /**
     * Instantiates a new about window.
     *
     * @param parent the parent
     */
    public AboutWindow(JFrame parent) {
        super("About");
        this.setSize(600, 400);
        Container mainpane = this.getContentPane();
        mainpane.setLayout(new BorderLayout());
        mainpane.add(new LaTeXPanel(nameAsLaTeX, null), BorderLayout.NORTH);
        mainpane.add(new JScrollPane(new MonoTextArea(copyrightInformation)), BorderLayout.CENTER);
        this.setLocationRelativeTo(parent);
        this.setAlwaysOnTop(true);
        this.setResizable(false);
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
}
