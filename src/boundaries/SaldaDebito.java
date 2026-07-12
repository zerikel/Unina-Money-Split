package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import control.MainController;

public class SaldaDebito extends JDialog {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextField txtImporto;
    private JComboBox<String> comboQuote;

    public SaldaDebito(JDialog parentFrame) {
        super(parentFrame, "Finestra: Registra Rimborso", true); 
        
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 420, 260);
        setLocationRelativeTo(parentFrame);
        
        contentPane = new JPanel();
        contentPane.setBackground(new Color(240, 240, 240)); 
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        
        JLabel lblSeleziona = new JLabel("Seleziona quota debito aperta:");
        lblSeleziona.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblSeleziona.setForeground(Color.BLACK);
        lblSeleziona.setBounds(30, 20, 250, 25);
        contentPane.add(lblSeleziona);
        
        comboQuote = new JComboBox<String>();
        comboQuote.setBackground(Color.WHITE);
        comboQuote.setBounds(30, 50, 340, 25);
        contentPane.add(comboQuote);
        
        List<String> quoteTesto = MainController.getInstance().getQuoteAperteTesto();
        for (String testo : quoteTesto) {
            comboQuote.addItem(testo);
        }
        
        JLabel lblImporto = new JLabel("Importo (€):");
        lblImporto.setFont(new Font("Tahoma", Font.BOLD, 12));
        lblImporto.setForeground(Color.BLACK);
        lblImporto.setBounds(30, 100, 100, 25);
        contentPane.add(lblImporto);
        
        txtImporto = new JTextField();
        txtImporto.setBounds(110, 100, 260, 25);
        txtImporto.setEditable(false);
        contentPane.add(txtImporto);
        txtImporto.setColumns(10);
        
        comboQuote.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int index = comboQuote.getSelectedIndex();
                if (index >= 0) {
                    float imp = MainController.getInstance().getImportoQuota(index);
                    txtImporto.setText(String.format("%.2f", imp).replace(",", "."));
                }
            }
        });

        if (comboQuote.getItemCount() > 0) {
            comboQuote.setSelectedIndex(0);
        } else {
            txtImporto.setText("0.00");
            comboQuote.addItem("Nessun debito aperto!");
            comboQuote.setEnabled(false);
        }

        JButton btnConferma = new JButton("CONFERMA RIMBORSO");
        btnConferma.setBackground(Color.WHITE);
        btnConferma.setForeground(Color.BLACK);
        btnConferma.setFont(new Font("Arial", Font.BOLD, 12));
        btnConferma.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
        btnConferma.setFocusPainted(false);
        btnConferma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnConferma.setBounds(110, 160, 190, 35);
        contentPane.add(btnConferma);
        
        btnConferma.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = comboQuote.getSelectedIndex();
                
                if (selectedIndex < 0 || !comboQuote.isEnabled()) {
                    JOptionPane.showMessageDialog(SaldaDebito.this, 
                        "Nessun debito valido selezionato!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                

                String importoStr = txtImporto.getText().trim().replace(",", ".");
                float importoInserito = Float.parseFloat(importoStr);

                boolean successo = MainController.getInstance().registraRimborso(selectedIndex, importoInserito);
                
                if (successo) {
                    JOptionPane.showMessageDialog(SaldaDebito.this, "Rimborso registrato con successo!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(SaldaDebito.this, "Errore di connessione al database.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
}