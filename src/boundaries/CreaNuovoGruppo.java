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

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public CreaNuovoGruppo() {
		
		// 1. Impostazioni Finestra
		setTitle("Finestra: Crea Nuovo Gruppo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 360, 360); // Ingrandita leggermente per farci stare tutto
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 2. Sezione "Nome Gruppo"
		JLabel lblNomeGruppo = new JLabel("Nome Gruppo:");
		lblNomeGruppo.setBounds(30, 30, 90, 24);
		contentPane.add(lblNomeGruppo);
		
		txtNomeGruppo = new JTextField();
		txtNomeGruppo.setToolTipText("Es. Viaggio a Roma");
		txtNomeGruppo.setBounds(120, 30, 180, 24);
		contentPane.add(txtNomeGruppo);
		txtNomeGruppo.setColumns(10);
		
		// 3. Sezione "Invita Partecipanti"
		JLabel lblInvita = new JLabel("Invita Partecipanti:");
		lblInvita.setFont(new Font("Tahoma", Font.BOLD, 12)); // Grassetto come nell'immagine
		lblInvita.setBounds(30, 80, 150, 24);
		contentPane.add(lblInvita);
		
		txtInserisciEmail = new JTextField();
		txtInserisciEmail.setBounds(30, 110, 163, 24);
		contentPane.add(txtInserisciEmail);
		txtInserisciEmail.setColumns(10);
		
		// 4. Bottone "Aggiungi" (Pulsante viola)
		JButton btnAggiungi = new JButton("Aggiungi");
		btnAggiungi.setBackground(new Color(225, 213, 231)); // Colore viola chiaro
		btnAggiungi.setBorder(new LineBorder(new Color(150, 115, 166), 1, true)); // Bordo viola scuro arrotondato
		btnAggiungi.setFocusPainted(false);
		btnAggiungi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAggiungi.setBounds(205, 110, 95, 24);
		contentPane.add(btnAggiungi);
		
		// 5. Sezione "Lista invitati temporanea"
		JLabel lblListaTemp = new JLabel("Lista invitati temporanea:");
		lblListaTemp.setBounds(30, 155, 180, 20);
		contentPane.add(lblListaTemp);
		
		// Usiamo una JTextArea senza bordo e con lo sfondo grigio per simulare il testo libero
		JTextArea txtAreaLista = new JTextArea();
		txtAreaLista.setBackground(contentPane.getBackground());
		txtAreaLista.setEditable(false);
		txtAreaLista.setBounds(30, 175, 270, 60);
		contentPane.add(txtAreaLista);
		
		// Logica dinamica: quando clicco Aggiungi, l'email va nella lista sotto
		btnAggiungi.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        String email = txtInserisciEmail.getText().trim();
		        
		        // 1. Controllo base: il campo non deve essere vuoto
		        if (email.isEmpty() || email.equals("Inserisci un'email per aggiungere...")) {
		            javax.swing.JOptionPane.showMessageDialog(
		                CreaNuovoGruppo.this, 
		                "Il campo email è vuoto!", 
		                "Attenzione", 
		                javax.swing.JOptionPane.WARNING_MESSAGE
		            );
		            return; // Blocca l'esecuzione
		        }
		        
		        // 2. Controllo formato (Regex): Verifica la presenza di caratteri, @, e un dominio
		     // Accetta qualsiasi prefisso valido, ma impone che il dominio sia @studenti.unina.it 
		     // OPPURE termini con .com o .it
		     String emailRegex = "^[A-Za-z0-9+_.-]+@(studenti\\.unina\\.it|[A-Za-z0-9.-]+\\.(com|it))$";		        
		        if (!email.matches(emailRegex)) {
		            // Se l'email NON rispetta il formato, mostriamo un errore
		            javax.swing.JOptionPane.showMessageDialog(
		                CreaNuovoGruppo.this, 
		                "Formato email non valido. Assicurati di usare la @ e un dominio corretto.", 
		                "Errore di Formato", 
		                javax.swing.JOptionPane.ERROR_MESSAGE
		            );
		            return; // Blocca l'esecuzione
		        }
		        
		        // 3. Se supera i controlli grafici, aggiungiamo alla lista visiva
		        // (In futuro, qui chiamerai anche: controller.verificaEmailEsistente(email) prima di aggiungere)
		        txtAreaLista.append("- " + email + "\n");
		        txtInserisciEmail.setText(""); 
		        
		        // Ripristiniamo il placeholder
		        contentPane.requestFocus();
		    }
		});
		
		// 6. Bottone Principale "CREA GRUPPO" (Pulsante azzurro)
		JButton btnCreaGruppo = new JButton("CREA GRUPPO");
		btnCreaGruppo.setForeground(Color.BLACK);
		btnCreaGruppo.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreaGruppo.setFocusPainted(false);
		btnCreaGruppo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreaGruppo.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnCreaGruppo.setBackground(new Color(218, 232, 252));
		btnCreaGruppo.setBounds(160, 260, 140, 35); // Spostato in basso a destra
		
		btnCreaGruppo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.out.println("Gruppo Creato!");
				// Qui poi inserirai l'INSERT nel database per la tabella GRUPPO e INVITO [cite: 34]
			}
		});
		contentPane.add(btnCreaGruppo);
	}
}