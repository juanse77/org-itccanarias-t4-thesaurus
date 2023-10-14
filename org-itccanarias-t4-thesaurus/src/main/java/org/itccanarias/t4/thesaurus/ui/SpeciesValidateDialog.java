// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.ui;

import javax.swing.JTable;
import java.awt.Dialog;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import org.openide.DialogDescriptor;
import org.itccanarias.t4.thesaurus.ThesaurusValidationItem;
import java.util.List;

public class SpeciesValidateDialog
{
    public static List<ThesaurusValidationItem> show(final List<ThesaurusValidationItem> valList) {
        final SpeciesValidatePanel panel = new SpeciesValidatePanel(valList);
        final DialogDescriptor dd = new DialogDescriptor((Object)panel, "Thesaurus validation");
        dd.setHelpCtx(new HelpCtx("thesaurus"));
        final Dialog dlg = DialogDisplayer.getDefault().createDialog(dd);
        dlg.setVisible(true);
        if (dd.getValue() == DialogDescriptor.OK_OPTION) {
            final JTable t = panel.validationTable;
            if (t != null && t.getCellEditor() != null) {
                t.getCellEditor().stopCellEditing();
            }
            return panel.getValidationModel();
        }
        return null;
    }
}
