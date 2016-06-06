package teachingalgorithms.ui.components;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.util.ArrayList;

/**
 * Created by moritz on 06.06.16.
 */
public class TooltipActivationCheckbox extends JCheckBox implements ChangeListener{

    private static ArrayList<TooltipActivationCheckbox> checkBoxList = new ArrayList<TooltipActivationCheckbox>();

    public TooltipActivationCheckbox() {
        TooltipActivationCheckbox.checkBoxList.add(this);
        this.setSelected(ToolTipManager.sharedInstance().isEnabled());
        this.addChangeListener(this);
    }


    @Override
    public void stateChanged(ChangeEvent e) {
        if (ToolTipManager.sharedInstance().isEnabled() != this.isSelected()){
            ToolTipManager.sharedInstance().setEnabled(this.isSelected());

            for (TooltipActivationCheckbox checkbox : checkBoxList){
                if (checkbox.isSelected() != ToolTipManager.sharedInstance().isEnabled()){
                    checkbox.setSelected(ToolTipManager.sharedInstance().isEnabled());
                }
            }

        }

    }
}
