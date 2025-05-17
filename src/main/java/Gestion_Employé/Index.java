/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Gestion_Employé;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.awt.Image;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import login.Login;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.renderer.category.AreaRenderer;
import org.jfree.data.general.DefaultPieDataset;

/**
 *
 * @author camaramohamed
 */
public class Index extends javax.swing.JFrame {

    /**
     * Creates new form Index
     */
    public Index() {
        initComponents();
        calculerSommeTotal();
        ccalculerNombreTotalRevendeurs();
        calculerSoldeMIN();
        calculerSoldeMax();
        displayAreaChart();
        displayPieChart();
        displayStatutPieCharc();
  
        
        
        
         // la date actuel
         
        SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss"); // format de date
        String dateActuelle = sd.format(new Date()); // date actuelle
        date.setText("le  "+dateActuelle +" à paris"); // on met à jour le JLabel
    }
    // statut 
    private ChartPanel createStatutPieChartPanel() {
    String SUrl = "jdbc:mysql://localhost:3307/Maure";
    String SUser = "root";
    String SPass = "";

    DefaultPieDataset dataset = new DefaultPieDataset();

    String query = "SELECT statut, COUNT(*) AS nombre FROM Employé GROUP BY statut";

    try (Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query)) {

        while (rs.next()) {
            String statut = rs.getString("statut");
            int nombre = rs.getInt("nombre");
            dataset.setValue(statut, nombre);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur SQL : " + e.getMessage());
    }

    // Création du graphique en secteurs (PieChart)
    JFreeChart chart = ChartFactory.createPieChart(
            "Répartition par Statut d'Employé",
            dataset,
            true,
            true,
            false
    );

    // Personnalisation
    chart.setBackgroundPaint(Color.white);
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setBackgroundPaint(Color.lightGray);

    // Liste de couleurs personnalisées (tu peux en ajouter d'autres)
    Color[] colors = {
        new Color(10, 45, 90),   // Bleu foncé
        new Color(200, 50, 50),  // Rouge
        new Color(50, 150, 50),  // Vert
        new Color(255, 165, 0),  // Orange
        new Color(100, 0, 150),  // Violet foncé
        new Color(0, 180, 180)   // Cyan
    };

    int colorIndex = 0;
    for (Object key : dataset.getKeys()) {
        plot.setSectionPaint((Comparable) key, colors[colorIndex % colors.length]);
        colorIndex++;
    
    }

    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(475, 430));
    return chartPanel;
}
    // statut 
    private void displayStatutPieCharc() {
    ChartPanel pieChartPanel = createStatutPieChartPanel();
    pie2.removeAll();
    pie2.setLayout(new BorderLayout());
    pie2.add(pieChartPanel, BorderLayout.CENTER);
    pie2.revalidate();
    pie2.repaint();
}


     // Fonction pour créer et afficher l'area chart dans un JPanel spécifique
 private ChartPanel createAreaChartPanel() {
    String SUrl = "jdbc:mysql://localhost:3307/Maure";
    String SUser = "root";
    String SPass = "";

    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    String query = "SELECT type_contrat, SUM(Salaire) as totalSalaire " +
                   "FROM Employé GROUP BY type_contrat";

    try (Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query)) {

        while (rs.next()) {
            String typeContrat = rs.getString("type_contrat");
            double totalSalaire = rs.getDouble("totalSalaire");
            dataset.addValue(totalSalaire, "Salaire Total", typeContrat);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur SQL : " + e.getMessage());
    }

    // Création du graphique
    JFreeChart chart = ChartFactory.createAreaChart(
            "Salaire Total par Type de Contrat",
            "Type de Contrat",
            "Salaire Total",
            dataset
    );

    // Personnalisation du graphique
    chart.setBackgroundPaint(Color.white);
    CategoryPlot plot = chart.getCategoryPlot();
    plot.setBackgroundPaint(Color.lightGray);
    plot.setRangeGridlinePaint(Color.GRAY);

    // Appliquer la couleur personnalisée [10, 45, 90]
    AreaRenderer renderer = new AreaRenderer();
    renderer.setSeriesPaint(0, new Color(10, 45, 90));
    plot.setRenderer(renderer);

    // Création du ChartPanel avec dimensions personnalisées
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(954, 399));

    return chartPanel;
}



// Créer une méthode pour l’afficher dans chart1
   private void displayAreaChart() {
    ChartPanel chartPanel = createAreaChartPanel();

    chart1.removeAll(); // Nettoyage
    chart1.setLayout(new BorderLayout());
    chart1.add(chartPanel, BorderLayout.CENTER); // Ajout au centre
    chart1.revalidate();
    chart1.repaint();
}

   // le pie chart
   
   private ChartPanel createPieChartPanel() {
    String SUrl = "jdbc:mysql://localhost:3307/Maure";
    String SUser = "root";
    String SPass = "";

    DefaultPieDataset dataset = new DefaultPieDataset();

    String query = "SELECT type_contrat, COUNT(*) AS nombre FROM Employé GROUP BY type_contrat";

    try (Connection con = DriverManager.getConnection(SUrl, SUser, SPass);
         Statement st = con.createStatement();
         ResultSet rs = st.executeQuery(query)) {

        while (rs.next()) {
            String typeContrat = rs.getString("type_contrat");
            int nombre = rs.getInt("nombre");
            dataset.setValue(typeContrat, nombre);
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur SQL : " + e.getMessage());
    }

    // Création du pie chart
    JFreeChart chart = ChartFactory.createPieChart(
            "Répartition des types de contrat",
            dataset,
            true,  // légende
            true,  // tooltips
            false  // URLs
    );

    // Personnalisation du graphique
    chart.setBackgroundPaint(Color.WHITE);
    PiePlot plot = (PiePlot) chart.getPlot();
    plot.setBackgroundPaint(Color.LIGHT_GRAY);
    plot.setSectionPaint(dataset.getKey(0), new Color(10, 45, 90)); // Appliquer la couleur au premier segment

    // Taille du ChartPanel
    ChartPanel chartPanel = new ChartPanel(chart);
    chartPanel.setPreferredSize(new Dimension(483, 399));

    return chartPanel;
}
// pie
private void displayPieChart() {
    ChartPanel chartPanel = createPieChartPanel();
    pie1.removeAll();
    pie1.setLayout(new BorderLayout());
    pie1.add(chartPanel, BorderLayout.CENTER);
    pie1.revalidate();
    pie1.repaint();
}

  
    
    
      // les fonctions 
    private void calculerSommeTotal() {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/maure", "root", "");
        String query = "SELECT SUM(Salaire) AS total FROM Employé";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        double sommeTotal = 0.0;
        if (rs.next()) {
            sommeTotal = rs.getDouble("total");
        }

        con.close();

        // Afficher la somme totale dans le label sommelabel
        somme.setText( ""+sommeTotal);
    } catch (Exception e) {
        e.printStackTrace();
    }
}

private void calculerSoldeMax() {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
        String query = "SELECT MAX(salaire) AS max_solde FROM Employé";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        double soldeMax = 0.0;
        if (rs.next()) {
            soldeMax = rs.getDouble("max_solde");
        }

        con.close();

        // Afficher le solde maximum dans le label soldemax
        soldemax.setText(soldeMax+"");
    } catch (Exception e) {
        e.printStackTrace();
    }
}
private void calculerSoldeMIN() {
    try {
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
        String query = "SELECT MIN(salaire) AS min_solde FROM Employé";
        Statement stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        double soldemin = 0.0;
        if (rs.next()) {
            soldemin = rs.getDouble("min_solde");
        }

        con.close();

        // Afficher le solde maximum dans le label soldemax
        soldemi.setText(soldemin+"");
    } catch (Exception e) {
        e.printStackTrace();
    }
}

// la fonction pour calculer le nombre total des employé de l'entreprise

public int ccalculerNombreTotalRevendeurs() {
    int nombreTotalRevendeurs = 0;
    try {
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3307/Maure", "root", "");
        Statement stmt = conn.createStatement();
        String query = "SELECT COUNT(id) AS TotalRevendeurs FROM Employé";
        ResultSet rs = stmt.executeQuery(query);

        if (rs.next()) {
            nombreTotalRevendeurs = rs.getInt("TotalRevendeurs");
        }

        conn.close();
         nbrer.setText(nombreTotalRevendeurs+"");
    } catch (Exception e) {
        e.printStackTrace();
    }
    return nombreTotalRevendeurs;
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
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        nbrere = new javax.swing.JLabel();
        nbrer = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        sommelabel = new javax.swing.JLabel();
        somme = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        soldemax = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        soldeminimal = new javax.swing.JLabel();
        soldemi = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        date = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel7 = new javax.swing.JPanel();
        chart1 = new javax.swing.JPanel();
        pie1 = new javax.swing.JPanel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jCheckBox2 = new javax.swing.JCheckBox();
        jCheckBox3 = new javax.swing.JCheckBox();
        jLabel20 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        pie2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setLocation(new java.awt.Point(70, 32));
        setMaximumSize(new java.awt.Dimension(1500, 1600));
        setName("PAGE INDEX"); // NOI18N
        setPreferredSize(new java.awt.Dimension(1500, 2000));
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setMaximumSize(new java.awt.Dimension(1500, 1300));
        jPanel1.setPreferredSize(new java.awt.Dimension(1500, 1300));

        jPanel2.setBackground(new java.awt.Color(10, 45, 90));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel4.setText("TOTAL DE SALARIÉ");

        jLabel10.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/icone personnes.jpg")); // NOI18N

        nbrere.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(nbrer, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4)
                    .addComponent(jLabel10))
                .addContainerGap(141, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(nbrere)
                    .addContainerGap(255, Short.MAX_VALUE)))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel10)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(nbrer, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(12, 12, 12))
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addContainerGap(101, Short.MAX_VALUE)
                    .addComponent(nbrere, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(9, 9, 9)))
        );

        jPanel2.add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 230, -1, 130));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel7.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel7.setText("TOTAL DE SALAIRE STATUS A , B ET C");

        jLabel12.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/salaire-total.png")); // NOI18N

        sommelabel.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(somme, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel7)
                    .addComponent(jLabel12))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(22, 22, 22)
                    .addComponent(sommelabel)
                    .addContainerGap(258, Short.MAX_VALUE)))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(somme, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
                .addGap(15, 15, 15))
            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                    .addContainerGap(98, Short.MAX_VALUE)
                    .addComponent(sommelabel, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12)))
        );

        jPanel2.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 230, 280, 130));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel8.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel8.setText("SALAIRE PLUS ÉLEVÉE");

        jLabel11.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/salaremax.jpg")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(soldemax, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(110, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(soldemax, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addGap(11, 11, 11))
        );

        jPanel2.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 230, 270, 130));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel9.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel9.setText("SALAIRE MINIMUM");

        jLabel13.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/salaire-mini.jpg")); // NOI18N

        soldeminimal.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel13))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel9))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(soldeminimal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(soldemi, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(162, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(soldeminimal, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 18, Short.MAX_VALUE))
                    .addComponent(soldemi, javax.swing.GroupLayout.DEFAULT_SIZE, 38, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel2.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(1160, 230, -1, 130));

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 255, 255));
        jLabel1.setFont(new java.awt.Font("Lao Sangam MN", 1, 24)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/M.jpeg")); // NOI18N
        jLabel1.setText("MAUCO");

        jButton3.setFont(new java.awt.Font("Lao Sangam MN", 1, 18)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/DASHBOARD.png")); // NOI18N
        jButton3.setText("DASHBOARD");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Lao Sangam MN", 1, 18)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/EMPLOIYER.png")); // NOI18N
        jButton1.setText("EMPLOYÉS");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setFont(new java.awt.Font("Lao Sangam MN", 1, 18)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/PAIEMENT.png")); // NOI18N
        jButton5.setText("PAYEMENT");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Lao Sangam MN", 1, 18)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon("/Volumes/MOUHAMED CAMARA/image/PARAMETRE.png")); // NOI18N
        jButton2.setText("PARAMETRE");
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
                .addGap(34, 34, 34)
                .addComponent(jLabel1)
                .addGap(159, 159, 159)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(39, 39, 39)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(279, Short.MAX_VALUE))
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
                    .addComponent(jButton2))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        jPanel2.add(jPanel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 1470, -1));

        date.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(date, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 170, 230, -1));

        jLabel6.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("TOTAL DE SALAIRE DU PRÉCEDENT");
        jPanel2.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 150, 290, -1));

        jLabel15.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("$13344,23344");
        jPanel2.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 180, -1, 20));

        jScrollPane1.setMaximumSize(null);

        jPanel7.setBackground(new java.awt.Color(219, 223, 228));
        jPanel7.setPreferredSize(new java.awt.Dimension(1492, 2000));

        chart1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout chart1Layout = new javax.swing.GroupLayout(chart1);
        chart1.setLayout(chart1Layout);
        chart1Layout.setHorizontalGroup(
            chart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 954, Short.MAX_VALUE)
        );
        chart1Layout.setVerticalGroup(
            chart1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 399, Short.MAX_VALUE)
        );

        pie1.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pie1Layout = new javax.swing.GroupLayout(pie1);
        pie1.setLayout(pie1Layout);
        pie1Layout.setHorizontalGroup(
            pie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 482, Short.MAX_VALUE)
        );
        pie1Layout.setVerticalGroup(
            pie1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jCheckBox1.setText("Catégorie A");
        jCheckBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox1ActionPerformed(evt);
            }
        });

        jCheckBox2.setText("Catégorie C");
        jCheckBox2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox2ActionPerformed(evt);
            }
        });

        jCheckBox3.setText("Catégorie B");
        jCheckBox3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jCheckBox3ActionPerformed(evt);
            }
        });

        jLabel20.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel20.setText("Statisque de salaire");

        jLabel22.setFont(new java.awt.Font("Lao Sangam MN", 1, 14)); // NOI18N
        jLabel22.setText("Tableau de fiche de paye");

        pie2.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout pie2Layout = new javax.swing.GroupLayout(pie2);
        pie2.setLayout(pie2Layout);
        pie2Layout.setHorizontalGroup(
            pie2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 474, Short.MAX_VALUE)
        );
        pie2Layout.setVerticalGroup(
            pie2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel7Layout.createSequentialGroup()
                                .addComponent(jLabel20)
                                .addGap(119, 119, 119)
                                .addComponent(jCheckBox1)
                                .addGap(58, 58, 58)
                                .addComponent(jCheckBox3)
                                .addGap(40, 40, 40)
                                .addComponent(jCheckBox2))
                            .addComponent(jLabel22))
                        .addGap(372, 372, 372)
                        .addComponent(pie2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(chart1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                        .addComponent(pie1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jCheckBox1)
                    .addComponent(jCheckBox2)
                    .addComponent(jCheckBox3)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(chart1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pie1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(55, 55, 55)
                        .addComponent(jLabel22))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(pie2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(1048, Short.MAX_VALUE))
        );

        jScrollPane1.setViewportView(jPanel7);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 384, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1250, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1500, 1600));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        EMPLOYÉ S = new EMPLOYÉ();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
      // lien vers la page de creation de login
        PARAMETRE S = new PARAMETRE();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();    
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
     // lien vers la page de creation de login
        PAYEMENT S = new PAYEMENT();
        S.setVisible(true);
        S.pack();
       // SignUpFrame.setLocationRelativeTo(null); 
        this.dispose();    
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jCheckBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox1ActionPerformed

    private void jCheckBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox2ActionPerformed

    private void jCheckBox3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jCheckBox3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jCheckBox3ActionPerformed

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
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Index.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Index().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chart1;
    private javax.swing.JLabel date;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JCheckBox jCheckBox3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel nbrer;
    private javax.swing.JLabel nbrere;
    private javax.swing.JPanel pie1;
    private javax.swing.JPanel pie2;
    private javax.swing.JLabel soldemax;
    private javax.swing.JLabel soldemi;
    private javax.swing.JLabel soldeminimal;
    private javax.swing.JLabel somme;
    private javax.swing.JLabel sommelabel;
    // End of variables declaration//GEN-END:variables
}
