/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Gestion_Employé;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.swing.AbstractCellEditor;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author camaramohamed
 */
public class BouttonEditor extends AbstractCellEditor implements TableCellEditor {
    private JPanel panel;
    private JButton btnModifier;
    private JButton btnSupprimer;
    private JTable table;

    public BouttonEditor(JTable table) {
        this.table = table;
        panel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnModifier = new JButton("Modifier");
        btnSupprimer = new JButton("Supprimer");

        panel.add(btnModifier);
        panel.add(btnSupprimer);

        // Action bouton Modifier
        btnModifier.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int id = (int) table.getValueAt(row, 0); // ID colonne 0
                //JOptionPane.showMessageDialog(null, "Modifier utilisateur ID : " + id);
                        MODIFIER_EMPLOYÉ1 S = new MODIFIER_EMPLOYÉ1();
                        S.setVisible(true);
                        S.pack();
                        //this.dispose();
                fireEditingStopped();
            }
        });

        // Action bouton Supprimer
        btnSupprimer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = table.getSelectedRow();
                int id = (int) table.getValueAt(row, 0);
                int confirm = JOptionPane.showConfirmDialog(null, "Supprimer ID : " + id + " ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (confirm == JOptionPane.YES_OPTION) {
                    supprimerUtilisateur(id);
                }
                fireEditingStopped();
            }
        });
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return panel;
    }

    @Override
    public Object getCellEditorValue() {
        return "";
    }

    private void supprimerUtilisateur(int id) {
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
            String sql = "DELETE FROM Employé WHERE ID = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
            JOptionPane.showMessageDialog(null, "Employé supprimé !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
