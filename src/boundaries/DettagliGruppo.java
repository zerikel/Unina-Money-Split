package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.util.List;

import javax.swing.*;
import javax.swing.border.*;

import control.MainController;
import Entities.Gruppo;
import Entities.Spesa;
import Entities.SpesaComune;
import Entities.Partecipazione;

public class DettagliGruppo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;    
    private JTextArea txtSpese;
    private JPanel panelSaldi;

    public DettagliGruppo() {
        
    	String nomeGruppo = MainController.getInstance().getNomeGruppoAttuale();
        setTitle("DETTAGLI GRUPPO: " + nomeGruppo.toUpperCase());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); 
        setBounds(100, 100, 600, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.setBounds(10, 10, 564, 390);
        tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 12));
        contentPane.add(tabbedPane);
        JPanel panelSpese = new JPanel();
        panelSpese.setLayout(null);
        tabbedPane.addTab("STORICO SPESE", null, panelSpese, null);

        txtSpese = new JTextArea();
        txtSpese.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtSpese.setEditable(false);
        
        JScrollPane scrollSpese = new JScrollPane(txtSpese);
        scrollSpese.setBounds(20, 20, 520, 200);
        panelSpese.add(scrollSpese);

        JButton btnInserisciSpesa = new JButton("+ INSERISCI SPESA");
        btnInserisciSpesa.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnInserisciSpesa.setBackground(new Color(218, 232, 252));
        btnInserisciSpesa.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
        btnInserisciSpesa.setFocusPainted(false);
        btnInserisciSpesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnInserisciSpesa.setBounds(20, 280, 160, 40);
        panelSpese.add(btnInserisciSpesa);
        
        btnInserisciSpesa.addActionListener(e -> {
            AggiungiSpesa dialog = new AggiungiSpesa(this);
            dialog.setVisible(true);
            aggiornaDati();
        });

        JButton btnVediReport = new JButton("VEDI REPORT");
        btnVediReport.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnVediReport.setBackground(new Color(225, 213, 231));
        btnVediReport.setBorder(new LineBorder(new Color(150, 115, 166), 1, true));
        btnVediReport.setFocusPainted(false);
        btnVediReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVediReport.setBounds(380, 280, 160, 40);
        
        btnVediReport.addActionListener(e -> {
            Report finestraReport = new Report();
            finestraReport.setVisible(true);
        });
        
        panelSpese.add(btnVediReport);

        panelSaldi = new JPanel();
        panelSaldi.setLayout(null);
        tabbedPane.addTab("SALDI E RIMBORSI", null, panelSaldi, null);
        aggiornaDati();
    }
   
    public void aggiornaDati() {
        List<Spesa> storico = MainController.getInstance().getSpeseGruppo();
        StringBuilder sb = new StringBuilder();
        sb.append("Data       | Descrizione    | Pagato Da | Importo  | Tipo Spesa\n");
        sb.append("-------------------------------------------------------------------\n");
        for (Spesa s : storico) {
            String tipo = (s instanceof SpesaComune) ? "COMUNE" : "PERSONALE";
            sb.append(String.format("%-10s | %-14s | %-9s | %6.2f € | %s\n", 
                s.getData().toString(), s.getDescrizione(), 
                s.getPagatore().getMyUtente().getNome(), s.getImportoTotale(), tipo));
        }
        txtSpese.setText(sb.toString());

        List<Partecipazione> saldi = MainController.getInstance().getSaldiGruppo();
        String emailUtenteLoggato = MainController.getInstance().getUtenteLoggato().getEmail();
        
        panelSaldi.removeAll(); 
        
        int yPos = 40;
        for(Partecipazione p : saldi) {
            String nomeUtente = p.getMyUtente().getNome() + " " + p.getMyUtente().getCognome();
            String labelNome = "• " + nomeUtente;
            labelNome += p.getMyUtente().getEmail().equals(emailUtenteLoggato) ? " (Tu):" : ":";
            
            JLabel lblNome = new JLabel(labelNome);
            lblNome.setFont(new Font("Tahoma", Font.PLAIN, 14));
            lblNome.setBounds(30, yPos, 200, 25);
            panelSaldi.add(lblNome);

            JLabel lblValore = new JLabel();
            lblValore.setFont(new Font("Tahoma", Font.BOLD, 14));
            lblValore.setBounds(250, yPos, 200, 25);
            
            float saldo = p.calcolaSaldoCorrente();
            if (saldo > 0) {
                lblValore.setText(String.format("+ %.2f € (Credito)", saldo));
                lblValore.setForeground(new Color(34, 139, 34));
            } else if (saldo < 0) {
                lblValore.setText(String.format("%.2f € (Debito)", saldo));
                lblValore.setForeground(Color.RED);
            } else {
                lblValore.setText("0.00 € (Pari)");
            }
            panelSaldi.add(lblValore);
            yPos += 50;
        }

        JButton btnRimborso = new JButton("EFFETTUA UN RIMBORSO");
        btnRimborso.setFont(new Font("Tahoma", Font.BOLD, 12));
        btnRimborso.setBackground(new Color(213, 232, 212));
        btnRimborso.setBorder(new LineBorder(new Color(130, 179, 102), 1, true));
        btnRimborso.setFocusPainted(false);
        btnRimborso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRimborso.setBounds(30, 280, 200, 40);
        
        btnRimborso.addActionListener(e -> {
            SaldaDebito dialog = new SaldaDebito(this);
            dialog.setVisible(true); 
            
            aggiornaDati();
        });

        panelSaldi.add(btnRimborso);
        panelSaldi.revalidate();
        panelSaldi.repaint();
    }
    }