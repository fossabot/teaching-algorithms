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

/**
 * The listener interface for receiving tooltipSettingChange events.
 * The class that is interested in processing a tooltipSettingChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTooltipSettingChangeListener<code> method. When
 * the tooltipSettingChange event occurs, that object's appropriate
 * method is invoked.
 * @author Moritz Flöter
 */
public interface TooltipSettingChangeListener {

    /**
     * Tooltip setting changed.
     *
     * @param tooltipSettingValue the value of the setting
     */
    public void tooltipSettingChanged(boolean tooltipSettingValue);

}
