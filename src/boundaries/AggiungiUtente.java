package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class AggiungiUtente extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtInserisciEmail;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiUtente frame = new AggiungiUtente();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AggiungiUtente() {
		
		// 1. Impostazioni Finestra
		setTitle("Invita Utenti");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 300);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 2. Sezione "Invita Partecipanti"
		JLabel lblInvita = new JLabel("Invita Partecipanti:");
		lblInvita.setFont(new Font("Tahoma", Font.BOLD, 12)); 
		lblInvita.setForeground(Color.BLACK);
		lblInvita.setBounds(30, 20, 150, 24);
		contentPane.add(lblInvita);
		
		txtInserisciEmail = new JTextField();
		txtInserisciEmail.setBounds(30, 50, 163, 24);
		contentPane.add(txtInserisciEmail);
		txtInserisciEmail.setColumns(10);
		
		// 3. Bottone "Aggiungi" 
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setBackground(new Color(225, 213, 231)); 
		btnAggiungi.setBorder(new LineBorder(new Color(150, 115, 166), 1, true)); 
		btnAggiungi.setFocusPainted(false);
		btnAggiungi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAggiungi.setBounds(205, 50, 95, 24);
		contentPane.add(btnAggiungi);
		
		// 4. Sezione "Lista invitati temporanea"
		JLabel lblListaTemp = new JLabel("Lista invitati temporanea:");
		lblListaTemp.setForeground(Color.BLACK);
		lblListaTemp.setBounds(30, 95, 180, 20);
		contentPane.add(lblListaTemp);
		
		JTextArea txtAreaLista = new JTextArea();
		txtAreaLista.setBackground(Color.WHITE);
		txtAreaLista.setForeground(Color.BLACK);
		
		// --- MODIFICA 1: Bordo trasparente (EmptyBorder invece di LineBorder) ---
		txtAreaLista.setBorder(new EmptyBorder(5, 5, 5, 5)); 
		// ------------------------------------------------------------------------
		
		txtAreaLista.setEditable(false);
		txtAreaLista.setBounds(30, 115, 270, 70);
		contentPane.add(txtAreaLista);
		
		btnAggiungi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String email = txtInserisciEmail.getText().trim();
				
				if (email.isEmpty() || email.equals("Inserisci un'email per aggiungere...")) {
					JOptionPane.showMessageDialog(
						AggiungiUtente.this, 
						"Il campo email è vuoto!", 
						"Attenzione", 
						JOptionPane.WARNING_MESSAGE
					);
					return; 
				}
				
				String emailRegex = "^[A-Za-z0-9+_.-]+@(studenti\\.unina\\.it|[A-Za-z0-9.-]+\\.(com|it))$";		        
				if (!email.matches(emailRegex)) {
					JOptionPane.showMessageDialog(
						AggiungiUtente.this, 
						"Formato email non valido. Assicurati di usare la @ e un dominio corretto.", 
						"Errore di Formato", 
						JOptionPane.ERROR_MESSAGE
					);
					return; 
				}
				
				txtAreaLista.append("- " + email + "\n");
				txtInserisciEmail.setText(""); 
				contentPane.requestFocus();
			}
		});
		
		// 5. Bottone Principale Finale
		JButton btnConfermaInviti = new JButton("CONFERMA INVITI");
		btnConfermaInviti.setForeground(Color.BLACK);
		btnConfermaInviti.setFont(new Font("Arial", Font.BOLD, 12));
		btnConfermaInviti.setFocusPainted(false);
		btnConfermaInviti.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConfermaInviti.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnConfermaInviti.setBackground(new Color(218, 232, 252));
		btnConfermaInviti.setBounds(160, 200, 140, 35); 
		
		btnConfermaInviti.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// --- MODIFICA 2: Controllo lista vuota ---
				String listaUtenti = txtAreaLista.getText().trim();
				
				if (listaUtenti.isEmpty()) {
					// Se la lista è vuota, blocca tutto e mostra l'errore
					JOptionPane.showMessageDialog(
							AggiungiUtente.this, 
							"Errore: non hai aggiunto nessun utente alla lista degli inviti!", 
							"Nessun utente inserito", 
							JOptionPane.ERROR_MESSAGE
						);
					return; // Esce e non salva
				}
				// ------------------------------------------
				
				// Se arriva qui, vuol dire che c'è almeno un'email. 
				// Qui farai l'INSERT nella tabella INVITO
				JOptionPane.showMessageDialog(
						AggiungiUtente.this, 
						"Tutti gli inviti sono stati inviati con successo!", 
						"Fatto", 
						JOptionPane.INFORMATION_MESSAGE
					);
				
				// (Opzionale) Svuota la lista dopo aver salvato
				txtAreaLista.setText("");
			}
		});
		contentPane.add(btnConfermaInviti);
	}
}