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

import org.jdesktop.swingx.JXButton;
import org.jdesktop.swingx.JXPanel;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

/**
 * <p>
 *         The listener class for receiving longerTooltip events (so that the
 *         tooltip gets shown longer and does not disappear as quickly as by
 *         default). It also allows tooltips to be displayed on touch devices by
 *         touching the according GUI element that implements this listener.
 * </p>
 * @author Moritz Floeter
 */
public class LongerTooltipListener implements MouseListener {

    private static List<TooltipSettingChangeListener> listeners = new ArrayList<TooltipSettingChangeListener>();

    private static boolean enabled = true;

    private Popup popup;

    public static void addListener(TooltipSettingChangeListener toAdd) {
        listeners.add(toAdd);
    }

    private static void fireTooltipSettingChangeEvent() {

        for (TooltipSettingChangeListener listen : listeners)
            listen.tooltipSettingChanged(enabled);
    }

    public static boolean isActive() {
        return enabled;
    }

    public static void setActive(boolean active) {

        enabled = active;
        ToolTipManager.sharedInstance().setEnabled(enabled);
        fireTooltipSettingChangeEvent();

    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseClicked(MouseEvent me) {
        if (enabled) {
            // for touchscreens a click is interpreted as mousemovement so that
            // the
            // tooltip gets displayed
            if (me.getComponent() instanceof LaTeXPanel && Objects.isNull(popup)) {
                Component toolTip = ((LaTeXPanel) me.getComponent()).getToolTipComponent();
                JXPanel content = new JXPanel();
                content.setLayout(new BorderLayout());
                content.add(toolTip, BorderLayout.CENTER);

                JXButton close = new JXButton("\u2612");
                JXPanel southPanel = new JXPanel();
                southPanel.setLayout(new BorderLayout());
                southPanel.add(close, BorderLayout.EAST);
                content.add(southPanel, BorderLayout.NORTH);
                popup = PopupFactory.getSharedInstance().getPopup(me.getComponent(), content, me.getXOnScreen(), me.getYOnScreen());
                popup.show();

                close.addActionListener(actionEvent -> {
                    popup.hide();
                    popup = null;
                });
            }
            ToolTipManager.sharedInstance().mouseMoved(me);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent me) {
        // TODO
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent me) {

    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) {
        if (enabled) {
            ToolTipManager.sharedInstance().setDismissDelay(60000);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) {

    }

}