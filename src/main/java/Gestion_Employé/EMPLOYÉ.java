/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Gestion_Employé;

import java.awt.HeadlessException;
import java.awt.print.PrinterException;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import com.itextpdf.text.*;
import com.itextpdf.text.Document;

import com.itextpdf.text.pdf.*;
import javax.swing.*;
import java.io.FileOutputStream;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;


/**
 *
 * @author camaramohamed
 */
public class EMPLOYÉ extends javax.swing.JFrame {

    /**
     * Creates new form Index
     */
    
    public EMPLOYÉ() {
        initComponents();
        // initialiser la fonction de affichertout
         afficherTout(); // → affiche directement à l'ouverture
         
         // les bouttons modifier et supprimer
         table.getColumn("Actions").setCellRenderer(new Boutton());
         table.getColumnModel().getColumn(13).setCellEditor(new BouttonEditor(table));
         // la taille de la colonne Actions de la table 
         TableColumn actionColumn = table.getColumn("Actions");
         actionColumn.setMinWidth(200);
         actionColumn.setMaxWidth(200);
         actionColumn.setPreferredWidth(200);

         
         // la date actuel
         
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // format de date
        String dateActuelle = sd.format(new Date()); // date actuelle
        date.setText("le  "+dateActuelle +" à paris"); // on met à jour le JLabel

    }
    // la création de la connection 
    public Connection getConnection()
{
    Connection con = null;
    
    try{
        con = DriverManager.getConnection("jdbc:MySQL://localhost:3307/Maure","root","");
    }catch(SQLException ex){
        System.out.println(ex.getMessage());
    }
    
    return con;
}

    // la fonction pour afficher les données dans le tableau
    
    public void afficherTout()
{
    DefaultTableModel model = new DefaultTableModel();
    model.setColumnIdentifiers(new Object[]{"id", "Nom", "Prénom", "Statut", "Adresse", "Temps_travail", "Type_contrat", "Poste_occupé", "Telephone","Date d'embauche","numero carte vitale","numero carte d'identité", "Salaire","Actions"});
    
    try {
        try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "")) {
            String query = "SELECT * FROM Employé"; // PAS de WHERE, donc on prend tout
            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
        
            
            while (rs.next()) {
                Object[] row = new Object[14];
                
                row[0] = rs.getObject("id");
                row[1] = rs.getObject("nom");
                row[2] = rs.getObject("prénom");
                
                row[3] = rs.getObject("Statut");
                row[4] = rs.getObject("Adresse");
                row[5] = rs.getObject("Temps_travail");
                
                row[6] = rs.getObject("Type_contrat");
                row[7] = rs.getObject("Poste_occupé");
                row[8] = rs.getObject("Telephone");
                
                 row[9] = rs.getObject("Date1");
                row[10] = rs.getObject("vital");
                row[11] = rs.getObject("Numero_identité");
                
                row[12] = rs.getObject("Salaire");
                row[13] = ""; // On mettra les vrais boutons après
                model.addRow(row);
                           
            }
        } // PAS de WHERE, donc on prend tout
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(null, "Erreur lors de l'affichage !");
    }
    
    table.setModel(model);
}
    
   // la fonction pour imprimer les données de table 
   public void imprimerTable(JTable table) {
    Document document = new Document();

    try {
        // Choisir l'emplacement de sauvegarde
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le PDF");
        int userSelection = fileChooser.showSaveDialog(null);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            String filePath = fileChooser.getSelectedFile().getAbsolutePath();

            // Ajouter extension si absente
            if (!filePath.endsWith(".pdf")) {
                filePath += ".pdf";
            }

            PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            // Titre
            document.add(new Paragraph("Liste des Employés", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(new Paragraph(" ")); // Espace

            // Table PDF avec colonnes de JTable
            PdfPTable pdfTable = new PdfPTable(table.getColumnCount());
            pdfTable.setWidthPercentage(100);

            // En-têtes
            for (int i = 0; i < table.getColumnCount(); i++) {
                pdfTable.addCell(new PdfPCell(new Phrase(table.getColumnName(i))));
            }

            // Données
            for (int rows = 0; rows < table.getRowCount(); rows++) {
                for (int cols = 0; cols < table.getColumnCount(); cols++) {
                    Object value = table.getValueAt(rows, cols);
                    pdfTable.addCell(value != null ? value.toString() : "");
                }
            }

            document.add(pdfTable);
            document.close();

            JOptionPane.showMessageDialog(null, "PDF généré avec succès !");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de l'impression en PDF !");
    }
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        date = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jButton7 = new javax.swing.JButton();
        combostatut = new javax.swing.JComboBox<>();
        combocontrat = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(60, 25));
        setMinimumSize(new java.awt.Dimension(1500, 1600));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1500, 1600));
        jPanel1.setMinimumSize(new java.awt.Dimension(1500, 1600));
        jPanel1.setPreferredSize(new java.awt.Dimension(1590, 1600));

        jPanel2.setBackground(new java.awt.Color(10, 45, 90));
        jPanel2.setPreferredSize(new java.awt.Dimension(1500, 270));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setPreferredSize(new java.awt.Dimension(1590, 110));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/M.jpeg")); // NOI18N
        jLabel1.setText("MAUCO");

        jButton3.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/DASHBOARD.png")); // NOI18N
        jButton3.setText("DASHBOARD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/EMPLOIYER.png")); // NOI18N
        jButton1.setText("EMPLOYÉS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/PAIEMENT.png")); // NOI18N
        jButton5.setText("PAYEMENT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/PARAMETRE.png")); // NOI18N
        jButton2.setText("PARAMETRE");
        jButton2.setMaximumSize(new java.awt.Dimension(129, 34));
        jButton2.setMinimumSize(new java.awt.Dimension(129, 34));
        jButton2.setPreferredSize(new java.awt.Dimension(129, 34));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jLabel1)
                .addGap(191, 191, 191)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 221, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(300, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton1)
                    .addComponent(jButton5)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 1560, 110));

        date.setForeground(new java.awt.Color(255, 255, 255));
        date.setText("12/11/2024 À PARIS");
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 170, -1, -1));

        jButton4.setText("Ajouter employé");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, 230, 40));

        jLabel3.setText("TABLEAU DE DONNÉE DES EMPLOYÉS");

        jLabel4.setFont(new java.awt.Font("Helvetica Neue", 1, 18)); // NOI18N
        jLabel4.setText("Filtrer par ");

        jButton7.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/PARAMETRE.png")); // NOI18N
        jButton7.setText("Imprimer");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        combostatut.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Statut A", "Statut B", "Statut C" }));
        combostatut.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combostatutActionPerformed(evt);
            }
        });

        combocontrat.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CDD", "CDI", "ALTERNANCE" }));
        combocontrat.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combocontratActionPerformed(evt);
            }
        });

        table.setBackground(new java.awt.Color(204, 204, 204));
        table.setForeground(new java.awt.Color(255, 255, 255));
        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nom", "Prénom", "Statut", "Adresse", "Temps_travail", "Type_contrat", "Poste_occupé", "Téléphone", "Date d'embauche", "numero carte vital", "numero identité", "Salaire", "Actions"
            }
        ));
        table.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        table.setRowHeight(40);
        table.setSelectionBackground(new java.awt.Color(10, 45, 90));
        jScrollPane1.setViewportView(table);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1580, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1557, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(combocontrat, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(combostatut, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton7)
                        .addComponent(combostatut, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(combocontrat, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(76, 76, 76)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 498, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1580, 1360));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        PARAMETRE S = new PARAMETRE();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        Index S = new Index();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        PAYEMENT S;
        S = new PAYEMENT();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        AJOUTER_EMPLOYÉ S = new AJOUTER_EMPLOYÉ();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();    
    }//GEN-LAST:event_jButton4ActionPerformed

    private void combostatutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combostatutActionPerformed
        // le comboxe de statut
         String selectedGroup = (String) combostatut.getSelectedItem();
        
        // Exécuter une requête SQL pour récupérer les données correspondant à ce groupe
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
            String query = "SELECT * FROM Employé WHERE Statut = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, selectedGroup);
            ResultSet rs = pstmt.executeQuery();

            // Créer un nouveau modèle de tableau pour stocker les données récupérées
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"id", "Nom", "Prénom", "Statut", "Adresse", "Temps_travail", "Type_contrat", "Poste_occupé", "Telephone", "Salaire","Actions"});
    
            // Parcourir les résultats de la requête et ajouter chaque ligne au modèle de tableau
            while (rs.next()) {
                Object[] row = new Object[11];
                
                row[0] = rs.getObject("id");
                row[1] = rs.getObject("nom");
                row[2] = rs.getObject("prénom");
                
                row[3] = rs.getObject("Statut");
                row[4] = rs.getObject("Adresse");
                row[5] = rs.getObject("Temps_travail");
                
                row[6] = rs.getObject("Type_contrat");
                row[7] = rs.getObject("Poste_occupé");
                row[8] = rs.getObject("Telephone");
                
                row[9] = rs.getObject("Salaire");
                row[10] = ""; // On mettra les vrais boutons après
                model.addRow(row);
            }
            
            // Mettre à jour le modèle de tableau avec les données récupérées
            table.setModel(model);
            
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }//GEN-LAST:event_combostatutActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // le bouton imprimer
         imprimerTable(table);
    }//GEN-LAST:event_jButton7ActionPerformed

    private void combocontratActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combocontratActionPerformed
        // le comboxe du contrat 
         String selectedGroup = (String) combocontrat.getSelectedItem();
        
        // Exécuter une requête SQL pour récupérer les données correspondant à ce groupe
        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
            String query = "SELECT * FROM Employé WHERE Type_contrat = ?";
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, selectedGroup);
            ResultSet rs = pstmt.executeQuery();

            // Créer un nouveau modèle de tableau pour stocker les données récupérées
            DefaultTableModel model = new DefaultTableModel();
            model.setColumnIdentifiers(new Object[]{"id", "Nom", "Prénom", "Statut", "Adresse", "Temps_travail", "Type_contrat", "Poste_occupé", "Telephone", "Salaire","Actions"});
    
            // Parcourir les résultats de la requête et ajouter chaque ligne au modèle de tableau
            while (rs.next()) {
                Object[] row = new Object[11];
                
                row[0] = rs.getObject("id");
                row[1] = rs.getObject("nom");
                row[2] = rs.getObject("prénom");
                
                row[3] = rs.getObject("Statut");
                row[4] = rs.getObject("Adresse");
                row[5] = rs.getObject("Temps_travail");
                
                row[6] = rs.getObject("Type_contrat");
                row[7] = rs.getObject("Poste_occupé");
                row[8] = rs.getObject("Telephone");
                
                row[9] = rs.getObject("Salaire");
                row[10] = ""; // On mettra les vrais boutons après
                model.addRow(row);
            }
            
            // Mettre à jour le modèle de tableau avec les données récupérées
            table.setModel(model);
            
            con.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_combocontratActionPerformed

    
    // La fonction  supprimer un employé dans la table
    
    private void btnSupprimer(java.awt.event.ActionEvent evt) {                                         
        // le boutton supprimer
        int selectedRow = table.getSelectedRow();
    
    // Vérifiez si une ligne est sélectionnée
    if (selectedRow != -1) {
        // Obtenez l'identifiant de l'élément à supprimer à partir du modèle de tableau
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int selectedId = (int) model.getValueAt(selectedRow, 0); // Supposons que la première colonne contient l'identifiant
        
        try {
            // Connexion à la base de données
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");

            // Requête SQL DELETE pour supprimer l'élément de la base de données par ID
            String query = "DELETE FROM Employé WHERE id = ?";
            
            // Préparer la requête SQL
            PreparedStatement pstmt = conn.prepareStatement(query);
            
            // Remplacer le paramètre "?" par l'ID de l'élément à supprimer
            pstmt.setInt(1, selectedId);
            
            // Exécuter la requête SQL DELETE
            pstmt.executeUpdate();
            
            // Fermer la connexion à la base de données
            conn.close();
            
            // Supprimez les données de la ligne sélectionnée dans le modèle de tableau
            model.removeRow(selectedRow);
            
            // Affichez un message de succès ou effectuez d'autres actions nécessaires
            JOptionPane.showMessageDialog(this, "L'élément a été supprimé avec succès de la base de données.");
        } catch (SQLException ex) {
            // Gérer les exceptions liées à la connexion ou à l'exécution de la requête
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Une erreur s'est produite lors de la suppression de l'élément de la base de données.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    } else {
        // Affichez un message indiquant à l'utilisateur de sélectionner une ligne avant de supprimer
        JOptionPane.showMessageDialog(this, "Veuillez sélectionner une ligne à supprimer.", "Avertissement", JOptionPane.WARNING_MESSAGE);
    }
    }               
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EMPLOYÉ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EMPLOYÉ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EMPLOYÉ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EMPLOYÉ.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EMPLOYÉ().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> combocontrat;
    private javax.swing.JComboBox<String> combostatut;
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable table;
    // End of variables declaration//GEN-END:variables
}
