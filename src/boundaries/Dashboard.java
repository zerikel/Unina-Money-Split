package boundaries;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;

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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.border.LineBorder;
import java.awt.Cursor;

public class Dashboard extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Dashboard frame = new Dashboard();
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
		list.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
			}
		});
		scrollPane.setViewportView(list);
		
		// 1. Creiamo i nostri dati finti (simulando il DB)
		String[] gruppiFinti = {"Viaggio a Roma", "Regalo Laurea Nicola", "Coinquilini Via Claudio"};

		// 2. Creiamo il "Modello" per la JList
		DefaultListModel<String> modelloGruppi = new DefaultListModel<>();

		// 3. Riempiamo il modello con un ciclo
		for(String gruppo : gruppiFinti) {
			modelloGruppi.addElement(gruppo);
		}

		// 4. Assegniamo il modello alla JList creata su WindowBuilder
		list.setModel(modelloGruppi);
		
		JLabel lblNewLabel = new JLabel("I TUOI GRUPPI ATTIVI");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel.setBounds(37, 56, 186, 24);
		contentPane.add(lblNewLabel);
		
		JLabel lblInvitiInSospeso = new JLabel("INVITI IN SOSPESO");
		lblInvitiInSospeso.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblInvitiInSospeso.setBounds(358, 56, 186, 24);
		contentPane.add(lblInvitiInSospeso);
		
		// Il contenitore principale degli inviti
		JPanel panel = new JPanel();
		panel.setBounds(324, 98, 227, 161); 
		contentPane.add(panel);
		
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		// 1. Dati finti degli inviti
		String[] invitiFinti = {"Spese Studio (da nicola@...)", "Cena di classe (da paolo@...)"};

		panel.removeAll(); // Puliamo per sicurezza

		// 2. Per ogni invito nel "finto DB", generiamo la grafica
		for (String testoInvito : invitiFinti) {
			
			// Creiamo il blocco principale del singolo invito
			JPanel pannelloSingoloInvito = new JPanel();
			pannelloSingoloInvito.setLayout(new BorderLayout(0, 5)); 
			
			// Un po' di margine inferiore per separare visivamente gli inviti
			pannelloSingoloInvito.setBorder(new EmptyBorder(0, 0, 15, 0));
			
			// --- LA PRIMA MODIFICA PER TOGLIERE LO SPAZIO ---
			// Diciamo a Java che l'altezza massima di questo blocco non deve superare i 65 pixel
			pannelloSingoloInvito.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));
			
			// Testo dell'invito in alto e centrato
			JLabel lblTesto = new JLabel(testoInvito);
			lblTesto.setHorizontalAlignment(SwingConstants.CENTER);
			lblTesto.setFont(new Font("Tahoma", Font.PLAIN, 13));
			pannelloSingoloInvito.add(lblTesto, BorderLayout.NORTH);
			
			// Il "vassoio" orizzontale per i bottoni
			JPanel pannelloBottoni = new JPanel();
			pannelloBottoni.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 0));
			
			// Bottone Accetta (Verde)
			JButton btnAccetta = new JButton("Accetta");
			btnAccetta.setBackground(new Color(200, 230, 201)); // Verde finto
			btnAccetta.addActionListener(e -> {
				System.out.println("Hai accettato: " + testoInvito);
			});
			
			// Bottone Rifiuta (Rosso)
			JButton btnRifiuta = new JButton("Rifiuta");
			btnRifiuta.setBackground(new Color(255, 205, 210)); // Rosso finto
			btnRifiuta.addActionListener(e -> {
				 System.out.println("Hai rifiutato: " + testoInvito);
			});
			
			// Assembliamo i mattoncini
			pannelloBottoni.add(btnAccetta);
			pannelloBottoni.add(btnRifiuta);
			pannelloSingoloInvito.add(pannelloBottoni, BorderLayout.CENTER);
			
			// Aggiungiamo l'invito completo al contenitore principale
			panel.add(pannelloSingoloInvito);
		}

		// --- LA SECONDA MODIFICA PER TOGLIERE LO SPAZIO ---
		// Aggiungiamo una "molla" invisibile alla fine che prende lo spazio vuoto e spinge gli inviti verso l'alto
		panel.add(Box.createVerticalGlue());
		
		JButton btnCreaGruppo = new JButton("CREA NUOVO GRUPPO");
		btnCreaGruppo.setForeground(Color.BLACK);
		btnCreaGruppo.setFont(new Font("Arial", Font.BOLD, 12));
		btnCreaGruppo.setFocusPainted(false);
		btnCreaGruppo.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCreaGruppo.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnCreaGruppo.setBackground(new Color(218, 232, 252));
		btnCreaGruppo.setBounds(37, 288, 218, 49);
		contentPane.add(btnCreaGruppo);
		
		JButton btnAggiungiUtente = new JButton("AGGIUNGI UTENTE");
		btnAggiungiUtente.setForeground(Color.BLACK);
		btnAggiungiUtente.setFont(new Font("Arial", Font.BOLD, 12));
		btnAggiungiUtente.setFocusPainted(false);
		btnAggiungiUtente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAggiungiUtente.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnAggiungiUtente.setBackground(new Color(218, 232, 252));
		btnAggiungiUtente.setBounds(326, 288, 218, 49);
		contentPane.add(btnAggiungiUtente);

		// Diciamo alla grafica di aggiornarsi
		panel.revalidate();
		panel.repaint();
	}
}