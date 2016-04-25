package teachingalgorithms.ui.components;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ToolTipManager;

/**
 * @author Moritz Floeter
 *         <p>
 *         The listener class for receiving longerTooltip events (so that the
 *         tooltip gets shown longer and does not disappear as quickly as by
 *         default). It also allows tooltips to be displayed on touch devices by
 *         touching the according GUI element that implements this listener.
 * <p>
 * --------------------------------------------------------------------
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or (at your
 * option) any later version.
 * <p>
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 * <p>
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 */
public class LongerTooltipListener implements MouseListener {

    private static List<TooltipSettingChangeListener> listeners = new ArrayList<TooltipSettingChangeListener>();

    private static boolean enabled = true;

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
    public void mouseClicked(MouseEvent e) {
        if (enabled) {
            // for touchscreens a click is interpreted as mousemovement so that
            // the
            // tooltip gets displayed
            ToolTipManager.sharedInstance().mouseMoved(e);
            ToolTipManager.sharedInstance().setDismissDelay(60000);
        }
    }

    /*
     * (non-Javadoc)
     *
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        // TODO
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    @Override
    public void mouseReleased(MouseEvent e) {

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
        if (enabled) {
            ToolTipManager.sharedInstance().setDismissDelay(ToolTipManager.sharedInstance().getDismissDelay());
        }
    }

}