// 
// Decompiled by Procyon v0.5.36
// 

package org.itccanarias.t4.thesaurus.ui;

import javax.swing.Icon;
import org.openide.util.ImageUtilities;
import javax.swing.event.ListSelectionEvent;
import java.util.ArrayList;
import javax.swing.LayoutStyle;
import java.awt.LayoutManager;
import java.awt.Container;
import javax.swing.GroupLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Component;
import javax.swing.table.TableModel;
import org.openide.util.NbBundle;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.util.Iterator;
import org.itccanarias.t4.util.Utils;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTable;
import org.itccanarias.t4.thesaurus.ThesaurusValidationItem;
import java.util.List;
import javax.swing.JPanel;

public class SpeciesValidatePanel extends JPanel
{
    private List<ThesaurusValidationItem> valSpeList;
    JTable alternativesTable;
    JLabel lblThesaurusURL;
    JButton markAsNewButton;
    JButton replaceButton;
    JTable validationTable;
    
    public SpeciesValidatePanel(final List<ThesaurusValidationItem> valList) {
        this.initComponents();
        this.valSpeList = valList;
        final DefaultTableModel namesModel = (DefaultTableModel)this.validationTable.getModel();
        for (final ThesaurusValidationItem specie : valList) {
            namesModel.addRow(new Object[] { specie.getStatus(), specie.getName(), "" });
        }
        for (int i = 0; i < this.validationTable.getColumnCount(); ++i) {
            this.validationTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }
        this.validationTable.getSelectionModel().addListSelectionListener(new SelectedSpecieListener());
        this.validationTable.getTableHeader().setReorderingAllowed(false);
        this.validationTable.setAutoResizeMode(3);
        this.validationTable.getColumnModel().getColumn(0).setMinWidth(55);
        this.validationTable.getColumnModel().getColumn(0).setMaxWidth(55);
        this.validationTable.setAutoCreateRowSorter(true);
        for (int i = 0; i < this.alternativesTable.getColumnCount(); ++i) {
            this.alternativesTable.getColumnModel().getColumn(i).setCellRenderer(new CustomTableCellRenderer());
        }
        this.alternativesTable.getSelectionModel().addListSelectionListener(new SelectedSimilarListener());
        this.alternativesTable.getTableHeader().setReorderingAllowed(false);
        this.alternativesTable.setAutoCreateRowSorter(true);
        this.lblThesaurusURL.setText(Utils.getThesaurusWebServiceURL());
    }
    
    private void initComponents() {
        final JLabel jLabel2 = new JLabel();
        final JScrollPane jScrollPane1 = new JScrollPane();
        this.validationTable = new JTable();
        final JScrollPane jScrollPane2 = new JScrollPane();
        this.alternativesTable = new JTable();
        this.replaceButton = new JButton();
        final JLabel jLabel3 = new JLabel();
        this.markAsNewButton = new JButton();
        final JLabel jLabel4 = new JLabel();
        this.lblThesaurusURL = new JLabel();
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setPreferredSize(new Dimension(800, 281));
        jLabel2.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.jLabel2.text"));
        jScrollPane1.setPreferredSize(new Dimension(300, 275));
        this.validationTable.setModel(new DefaultTableModel(new Object[0][], new String[] { "Status", "Current name", "New name" }) {
            Class[] types = { Object.class, String.class, String.class };
            boolean[] canEdit = { false, false, true };
            
            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(this.validationTable);
        this.alternativesTable.setModel(new DefaultTableModel(new Object[0][], new String[] { "Name", "Server", "URL" }) {
            Class[] types = { String.class, String.class, String.class };
            boolean[] canEdit = { false, false, false };
            
            @Override
            public Class getColumnClass(final int columnIndex) {
                return this.types[columnIndex];
            }
            
            @Override
            public boolean isCellEditable(final int rowIndex, final int columnIndex) {
                return this.canEdit[columnIndex];
            }
        });
        jScrollPane2.setViewportView(this.alternativesTable);
        this.replaceButton.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.replaceButton.text"));
        this.replaceButton.setEnabled(false);
        this.replaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SpeciesValidatePanel.this.replaceButtonActionPerformed(evt);
            }
        });
        jLabel3.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.jLabel1.text"));
        this.markAsNewButton.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.markAsNewButton.text"));
        this.markAsNewButton.setEnabled(false);
        this.markAsNewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent evt) {
                SpeciesValidatePanel.this.markAsNewButtonActionPerformed(evt);
            }
        });
        jLabel4.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.jLabel3.text"));
        this.lblThesaurusURL.setFont(new Font("Tahoma", 1, 11));
        this.lblThesaurusURL.setText(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.lblThesaurusURL.text"));
        final GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.markAsNewButton, GroupLayout.Alignment.TRAILING).addComponent(jScrollPane1, GroupLayout.Alignment.TRAILING, -1, 377, 32767).addComponent(jLabel2, -1, 377, 32767)).addGap(18, 18, 18).addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addComponent(this.replaceButton).addComponent(jScrollPane2, -1, 387, 32767).addComponent(jLabel3, -1, 387, 32767))).addGroup(layout.createSequentialGroup().addComponent(jLabel4).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addComponent(this.lblThesaurusURL, -1, 662, 32767))).addContainerGap()));
        layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup().addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel4).addComponent(this.lblThesaurusURL)).addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(jLabel2).addComponent(jLabel3, -2, 14, -2)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(jScrollPane2, -1, 217, 32767).addComponent(jScrollPane1, -1, 217, 32767)).addPreferredGap(LayoutStyle.ComponentPlacement.RELATED).addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE).addComponent(this.markAsNewButton).addComponent(this.replaceButton)).addContainerGap()));
        jLabel2.getAccessibleContext().setAccessibleName(NbBundle.getMessage((Class)SpeciesValidatePanel.class, "SpeciesValidatePanel.jLabel2.AccessibleContext.accessibleName"));
    }
    
    private void replaceButtonActionPerformed(final ActionEvent evt) {
        final int altIndex = this.alternativesTable.getSelectedRow();
        if (altIndex != -1) {
            final String name = (String)this.alternativesTable.getValueAt(altIndex, 0);
            final int speIndex = this.validationTable.getSelectedRow();
            if (speIndex != -1) {
                this.validationTable.setValueAt(ThesaurusValidationItem.Status.VALID, speIndex, 0);
                this.validationTable.setValueAt(name, speIndex, 2);
            }
        }
    }
    
    private void markAsNewButtonActionPerformed(final ActionEvent evt) {
        final int index = this.validationTable.getSelectedRow();
        if (index != -1) {
            this.validationTable.setValueAt(ThesaurusValidationItem.Status.NEW, index, 0);
        }
    }
    
    public List<ThesaurusValidationItem> getValidationModel() {
        final List<ThesaurusValidationItem> retList = new ArrayList<ThesaurusValidationItem>();
        final TableModel model = this.validationTable.getModel();
        for (int i = 0; i < model.getRowCount(); ++i) {
            final ThesaurusValidationItem valSpe = new ThesaurusValidationItem();
            final ThesaurusValidationItem.Status taxonNameStatus = (ThesaurusValidationItem.Status)model.getValueAt(i, 0);
            valSpe.setStatus(taxonNameStatus);
            valSpe.setName((String)model.getValueAt(i, 1));
            valSpe.setNewName((String)model.getValueAt(i, 2));
            if (valSpe.getStatus() == ThesaurusValidationItem.Status.NEW && (valSpe.getNewName() == null || valSpe.getNewName().trim().length() == 0)) {
                valSpe.setNewName(valSpe.getName());
            }
            retList.add(valSpe);
        }
        return retList;
    }
    
    private ThesaurusValidationItem findThesaurusResult(final String taxonName, final List<ThesaurusValidationItem> l) {
        for (final ThesaurusValidationItem item : l) {
            if (item.getName().equals(taxonName)) {
                return item;
            }
        }
        return null;
    }
    
    private class SelectedSpecieListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(final ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                boolean v = false;
                final int index = SpeciesValidatePanel.this.validationTable.getSelectedRow();
                if (index != -1) {
                    final String taxonName = (String)SpeciesValidatePanel.this.validationTable.getValueAt(index, 1);
                    final ThesaurusValidationItem tvItem = SpeciesValidatePanel.this.findThesaurusResult(taxonName, SpeciesValidatePanel.this.valSpeList);
                    final DefaultTableModel speciesModel = (DefaultTableModel)SpeciesValidatePanel.this.alternativesTable.getModel();
                    speciesModel.setRowCount(0);
                    if (tvItem != null) {
                        for (final ThesaurusValidationItem.AlternativeSpecie term : tvItem.getSimilarNames()) {
                            speciesModel.addRow(new Object[] { term.getName(), term.getServer(), term.getUrl() });
                        }
                    }
                    v = (ThesaurusValidationItem.Status.VALID != SpeciesValidatePanel.this.validationTable.getValueAt(index, 0));
                }
                SpeciesValidatePanel.this.markAsNewButton.setEnabled(v);
                SpeciesValidatePanel.this.replaceButton.setEnabled(SpeciesValidatePanel.this.alternativesTable.getRowCount() > 0);
            }
        }
    }
    
    private class SelectedSimilarListener implements ListSelectionListener
    {
        @Override
        public void valueChanged(final ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                final int index = SpeciesValidatePanel.this.alternativesTable.getSelectedRow();
                SpeciesValidatePanel.this.replaceButton.setEnabled(index != -1);
            }
        }
    }
    
    private static class CustomTableCellRenderer implements TableCellRenderer
    {
        @Override
        public Component getTableCellRendererComponent(final JTable table, final Object obj, final boolean isSelected, final boolean hasFocus, final int row, final int column) {
            final JLabel c = (JLabel)table.getDefaultRenderer(Object.class).getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
            if (column == 0 && obj instanceof ThesaurusValidationItem.Status) {
                c.setText("");
                c.setHorizontalAlignment(0);
                String icon = null;
                switch ((ThesaurusValidationItem.Status)obj) {
                    case INVALID: {
                        icon = "org/itccanarias/t4/ui/icons/cross.png";
                        break;
                    }
                    case VALID: {
                        icon = "org/itccanarias/t4/ui/icons/tick.png";
                        break;
                    }
                    case NEW: {
                        icon = "org/itccanarias/t4/ui/icons/newspecie.png";
                        break;
                    }
                }
                c.setIcon(ImageUtilities.loadImageIcon(icon, false));
            }
            else {
                c.setIcon(null);
                c.setHorizontalAlignment(2);
            }
            return c;
        }
    }
}
