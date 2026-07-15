package boundaries;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.Cursor;
import java.util.*;

import control.MainController;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Dashboard extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;

    public Dashboard() {
        setTitle("Dashboard Principale");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 627, 398);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(37, 90, 186, 122);
        contentPane.add(scrollPane);
        
        JList<String> list = new JList<>();
        scrollPane.setViewportView(list);
        
        DefaultListModel<String> modelloGruppi = new DefaultListModel<>();
        List<String> nomiGruppi = MainController.getInstance().getNomiGruppiUtente();
        
        for(String nome : nomiGruppi) {
            modelloGruppi.addElement(nome); 
        }
        list.setModel(modelloGruppi);
        
        list.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
            	if (evt.getClickCount() == 1) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                    	MainController.getInstance().impostaGruppoDaIndice(index);                    	
                    }
            	}
            	if (evt.getClickCount() == 2) {
                    int index = list.locationToIndex(evt.getPoint());
                    if (index >= 0) {
                    	MainController.getInstance().impostaGruppoDaIndice(index);
                    	
                        DettagliGruppo dettagliFrame = new DettagliGruppo(Dashboard.this);
                        dettagliFrame.setVisible(true);
                    }
                }
            }
        });
        
        JLabel lblNewLabel = new JLabel("I TUOI GRUPPI ATTIVI");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel.setBounds(37, 56, 186, 24);
        contentPane.add(lblNewLabel);
        
        JLabel lblInvitiInSospeso = new JLabel("INVITI IN SOSPESO");
        lblInvitiInSospeso.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblInvitiInSospeso.setBounds(358, 56, 186, 24);
        contentPane.add(lblInvitiInSospeso);
        
        JPanel panel = new JPanel();
        panel.setBounds(324, 98, 227, 161);
        contentPane.add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        List<String[]> invitiFormattati = MainController.getInstance().getInvitiUtenteFormattati();

        panel.removeAll();
        for (String[] invito : invitiFormattati) {
            int idGruppo = Integer.parseInt(invito[0]);
            String nomeGruppoInvito = invito[1];
            String testoInvito = "Invito per: " + nomeGruppoInvito;
            
            JPanel pannelloSingoloInvito = new JPanel();
            pannelloSingoloInvito.setLayout(new BorderLayout(0, 5));
            pannelloSingoloInvito.setBorder(new EmptyBorder(0, 0, 15, 0));
            pannelloSingoloInvito.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
            
            JLabel lblTesto = new JLabel(testoInvito);
            lblTesto.setHorizontalAlignment(SwingConstants.CENTER);
            lblTesto.setFont(new Font("Tahoma", Font.PLAIN, 13));
            pannelloSingoloInvito.add(lblTesto, BorderLayout.NORTH);
            
            JPanel pannelloBottoni = new JPanel();
            pannelloBottoni.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
            
            JButton btnAccetta = new JButton("Accetta");
            btnAccetta.setBackground(new Color(200, 230, 201));
            btnAccetta.addActionListener(e -> {
                boolean successo = MainController.getInstance().gestisciRispostaInvito(idGruppo, true);
                if (successo) {
                    javax.swing.JOptionPane.showMessageDialog(Dashboard.this,"Sei entrato in "+ nomeGruppoInvito + "!");
                    dispose();
                    new Dashboard().setVisible(true);
                }
            });
            
            JButton btnRifiuta = new JButton("Rifiuta");
            btnRifiuta.setBackground(new Color(255, 205, 210));
            btnRifiuta.addActionListener(e -> {
                 boolean successo = MainController.getInstance().gestisciRispostaInvito(idGruppo, false);
                 if (successo) {
                     javax.swing.JOptionPane.showMessageDialog(Dashboard.this,"Hai rifiutato l'invito ad entrare al gruppo "+ nomeGruppoInvito);
                     dispose();
                     new Dashboard().setVisible(true);
                 }
            });
            
            pannelloBottoni.add(btnAccetta);
            pannelloBottoni.add(btnRifiuta);
            pannelloSingoloInvito.add(pannelloBottoni, BorderLayout.CENTER);
            panel.add(pannelloSingoloInvito);
        }
        panel.add(Box.createVerticalGlue());
        
        JButton btnCreaGruppo = new JButton("CREA NUOVO GRUPPO");
        btnCreaGruppo.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		CreaNuovoGruppo finestraCrea = new CreaNuovoGruppo(Dashboard.this);
        		finestraCrea.setVisible(true);
        	}
        });
        btnCreaGruppo.setForeground(Color.BLACK);
        btnCreaGruppo.setFont(new Font("Arial", Font.BOLD, 12));
        btnCreaGruppo.setFocusPainted(false);
        btnCreaGruppo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnCreaGruppo.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
        btnCreaGruppo.setBackground(new Color(218, 232, 252));
        btnCreaGruppo.setBounds(37, 288, 218, 49);
        contentPane.add(btnCreaGruppo);
        
        JButton btnAggiungiUtente = new JButton("AGGIUNGI UTENTE");
        btnAggiungiUtente.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		if (MainController.getInstance().getNomeGruppoAttuale().isEmpty()) {
                    javax.swing.JOptionPane.showMessageDialog(Dashboard.this, "Seleziona prima un gruppo dalla lista!");
                    return;
                }
        		if (!MainController.getInstance().isCreatoreDelGruppo()) {
        			javax.swing.JOptionPane.showMessageDialog(Dashboard.this, 
        					"Accesso Negato: Solo il creatore del gruppo puÃ² invitare nuovi partecipanti!", 
        					"Permessi Insufficienti", 
        					javax.swing.JOptionPane.ERROR_MESSAGE);
        			return;
        		}
        		AggiungiUtente finestraInvito = new AggiungiUtente(Dashboard.this);
                finestraInvito.setVisible(true);
        	}
        });
        btnAggiungiUtente.setForeground(Color.BLACK);
        btnAggiungiUtente.setFont(new Font("Arial", Font.BOLD, 12));
        btnAggiungiUtente.setFocusPainted(false);
        btnAggiungiUtente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAggiungiUtente.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
        btnAggiungiUtente.setBackground(new Color(218, 232, 252));
        btnAggiungiUtente.setBounds(326, 288, 218, 49);
        contentPane.add(btnAggiungiUtente);
    }
}