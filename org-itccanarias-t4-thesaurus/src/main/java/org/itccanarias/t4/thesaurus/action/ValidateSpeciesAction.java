// 
// Decompiled by Procyon v0.5.36
// 
package org.itccanarias.t4.thesaurus.action;

import javax.swing.Action;
import javax.swing.JMenuItem;
import org.openide.windows.TopComponent;
import org.itccanarias.t4.dialog.MessageDialog;
import org.itccanarias.t4.thesaurus.ThesaurusManager;
import org.itccanarias.t4.ui.editor.EditorUtil;
import java.awt.event.ActionEvent;
import org.openide.util.LookupEvent;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.Utilities;
import org.itccanarias.t4.model.MatrixData;
import org.openide.util.Lookup;
import org.openide.util.LookupListener;
import org.openide.util.actions.Presenter;
import org.openide.util.ContextAwareAction;
import javax.swing.AbstractAction;

public final class ValidateSpeciesAction extends AbstractAction implements ContextAwareAction, Presenter.Popup, LookupListener {

    private Lookup.Result<MatrixData> result;

    public ValidateSpeciesAction() {
        this(Utilities.actionsGlobalContext());
    }

    public ValidateSpeciesAction(final Lookup lookup) {
        super(NbBundle.getMessage(ValidateSpeciesAction.class, "CTL_ValidateSpeciesAction"), new ImageIcon(ImageUtilities.loadImage("org/itccanarias/t4/ui/icons/SpeciesThesaurosValidation.png")));
        this.result = (Lookup.Result<MatrixData>) lookup.lookupResult(MatrixData.class);
        this.result.addLookupListener(this);
        this.resultChanged(new LookupEvent(this.result));
    }

    public void actionPerformed(final ActionEvent e) {
        if (null != this.result && 0 < this.result.allInstances().size()) {
            final MatrixData data = this.result.allInstances().iterator().next();
            final TopComponent tc = EditorUtil.findEditor(data);

            if (tc == null) {
                ThesaurusManager.validateSpecies(data.getFilePath());
            } else {
                MessageDialog.showInfo("Thesaurus species validation could not be executed while " + data.getFileName() + " matrix is being edited");
            }
        }
    }

    public JMenuItem getPopupPresenter() {
        return new JMenuItem(this);
    }

    public Action createContextAwareInstance(final Lookup actionContext) {
        return new ValidateSpeciesAction(actionContext);
    }

    public void resultChanged(final LookupEvent ev) {
        if (this.result.allInstances().size() > 0) {
            this.setEnabled(true);
        } else {
            this.setEnabled(false);
        }
    }
}
