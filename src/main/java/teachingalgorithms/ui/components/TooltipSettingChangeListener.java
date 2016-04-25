package teachingalgorithms.ui.components;

/**
 * The listener interface for receiving tooltipSettingChange events.
 * The class that is interested in processing a tooltipSettingChange
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addTooltipSettingChangeListener<code> method. When
 * the tooltipSettingChange event occurs, that object's appropriate
 * method is invoked.
 */
public interface TooltipSettingChangeListener {

    /**
     * Tooltip setting changed.
     *
     * @param tooltipSettingValue the value of the setting
     */
    public void tooltipSettingChanged(boolean tooltipSettingValue);

}
