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

package teachingalgorithms.ui.windows;

import teachingalgorithms.ui.components.GeneralGuiFunctions;
import teachingalgorithms.ui.internationalisation.AvailableLanguages;
import teachingalgorithms.ui.internationalisation.Messages;

import javax.swing.*;
import java.util.Locale;

/**
 * This class should be extended by all Windows. It provides needed features for internationalisation
 * and the main menu.
 * @author Jonathan Lechner
 */
public abstract class WindowSubstructure extends JFrame {

    /**
     * Internationalisation
     */
    public static Messages MESSAGES = new Messages(Locale.getDefault());

    protected JMenuBar menuBar;

    protected JMenu help;
    private JMenuItem about;

    protected JMenu preferences;
    private JMenu language;

    /**
     * builds basic window with menu.
     */
    public WindowSubstructure() {
        super();
        menuBar = new JMenuBar();

        help = new JMenu();
        about = new JMenuItem();

        preferences = new JMenu();
        language = new JMenu();

        //could be changed to method to user define the order of entries
        menuBar.add(preferences);
        menuBar.add(help);

        preferences.add(language);
        help.add(about);

        about.addActionListener(actionEvent -> new AboutWindow(this));

        ButtonGroup group = new ButtonGroup();
        for (AvailableLanguages cl : AvailableLanguages.values()) {
            JRadioButtonMenuItem select = new JRadioButtonMenuItem(cl.toString());
            group.add(select);
            language.add(select);

            select.addActionListener(actionEvent -> {
                if (select.isSelected()) {
                    changeLanguage(cl);
                }
            });
        }

        GeneralGuiFunctions.centerWindow(this);
        GeneralGuiFunctions.enableOSXFullscreen(this);
        GeneralGuiFunctions.enableOSXMenu(this);
    }

    /**
     * This method sets all Texts to window, depending on selected language.
     * (please overwrite this method and super call it)
     */
    protected void setTextToWindow() {
        help.setText(MESSAGES.getMessage("windowsubstructure.help"));
        about.setText(MESSAGES.getMessage("windowsubstructure.about"));

        preferences.setText(MESSAGES.getMessage("windowsubstructure.preferences"));
        language.setText(MESSAGES.getMessage("windowsubstructure.language"));

    }

    /**
     * called by menu actionListener to change language.
     * @param availableLanguages
     */
    private void changeLanguage(AvailableLanguages availableLanguages) {
        if (availableLanguages.equals(AvailableLanguages.DE)) {
            MESSAGES = new Messages(Locale.GERMANY);
        } else {
            MESSAGES = new Messages(Locale.US);
        }
        this.setTextToWindow();
    }

}
