package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;

public class CreaNuovoGruppo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtNomeGruppo;
	private JTextField txtInserisciEmail;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreaNuovoGruppo frame = new CreaNuovoGruppo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public CreaNuovoGruppo() {
		
		setTitle("Finestra: Crea Nuovo Gruppo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNomeGruppo = new JLabel("Nome Gruppo:");
		lblNomeGruppo.setBounds(30, 30, 90, 24);
		contentPane.add(lblNomeGruppo);
		
		txtNomeGruppo = new JTextField();
		txtNomeGruppo.setToolTipText("Es. Viaggio a Roma");
		txtNomeGruppo.setBounds(120, 30, 180, 24);
		contentPane.add(txtNomeGruppo);
		txtNomeGruppo.setColumns(10);
		
		JLabel lblInvita = new JLabel("Invita Partecipanti:");
		lblInvita.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblInvita.setBounds(30, 80, 150, 24);
		contentPane.add(lblInvita);
		
		txtInserisciEmail = new JTextField();
		txtInserisciEmail.setBounds(30, 110, 163, 24);
		contentPane.add(txtInserisciEmail);
		txtInserisciEmail.setColumns(10);
		
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setBackground(new Color(225, 213, 231)); 
		btnAggiungi.setBorder(new LineBorder(new Color(150, 115, 166), 1, true)); 
		btnAggiungi.setFocusPainted(false);
		btnAggiungi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAggiungi.setBounds(205, 110, 95, 24);
		contentPane.add(btnAggiungi);
		
		JLabel lblListaTemp = new JLabel("Lista invitati temporanea:");
		lblListaTemp.setBounds(30, 155, 180, 20);
		contentPane.add(lblListaTemp);
		
		JTextArea txtAreaLista = new JTextArea();
		txtAreaLista.setBackground(contentPane.getBackground());
		txtAreaLista.setEditable(false);
		txtAreaLista.setBounds(30, 175, 270, 60);
		contentPane.add(txtAreaLista);
		
		btnAggiungi.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String email = txtInserisciEmail.getText().trim();
		        
		        if (email.isEmpty() || email.equals("Inserisci un'email per aggiungere...")) {
		            javax.swing.JOptionPane.showMessageDialog(
		                CreaNuovoGruppo.this, 
		                "Il campo email è vuoto!", 
		                "Attenzione", 
		                javax.swing.JOptionPane.WARNING_MESSAGE
		            );
		            return; 
		        }
		        
		     String emailRegex = "^[A-Za-z0-9+_.-]+@(studenti\\.unina\\.it|[A-Za-z0-9.-]+\\.(com|it))$";		        
		        if (!email.matches(emailRegex)) {
		            javax.swing.JOptionPane.showMessageDialog(
		                CreaNuovoGruppo.this, 
		                "Formato email non valido. Assicurati di usare la @ e un dominio corretto.", 
		                "Errore di Formato", 
		                javax.swing.JOptionPane.ERROR_MESSAGE
		            );
		            return;
		        }
		        
		        txtAreaLista.append("- " + email + "\n");
		        txtInserisciEmail.setText(""); 
		        
		        contentPane.requestFocus();
		    }
		});
		
		JButton btnCreaGruppo = new JButton("CREA GRUPPO");
		btnCreaGruppo.setForeground(Color.BLACK);
		btnCreaGruppo.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreaGruppo.setFocusPainted(false);
		btnCreaGruppo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreaGruppo.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnCreaGruppo.setBackground(new Color(218, 232, 252));
		btnCreaGruppo.setBounds(160, 260, 140, 35);
		
		btnCreaGruppo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Gruppo Creato!");
			}
		});
		contentPane.add(btnCreaGruppo);
	}
}