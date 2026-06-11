package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class DettagliGruppo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DettagliGruppo frame = new DettagliGruppo();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public DettagliGruppo() {
		// 1. Impostazioni Finestra Principale
		setTitle("DETTAGLI GRUPPO");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 600, 450);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// 2. Creazione del JTabbedPane (Il gestore delle schede)
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(10, 10, 564, 390);
		// Font un po' più grande per le linguette in alto
		tabbedPane.setFont(new Font("Tahoma", Font.BOLD, 12)); 
		contentPane.add(tabbedPane);

		/* =========================================================
		 * TAB 1: STORICO SPESE (Prima Immagine)
		 * ========================================================= */
		JPanel panelSpese = new JPanel();
		panelSpese.setLayout(null);
		tabbedPane.addTab("STORICO SPESE", null, panelSpese, null);

		// Area di testo per simulare la tabella delle spese
		JTextArea txtSpese = new JTextArea();
		txtSpese.setFont(new Font("Monospaced", Font.PLAIN, 12));
		txtSpese.setEditable(false);
		txtSpese.setText("Data       | Descrizione    | Pagato Da | Importo  | Tipo Spesa\n"
				       + "-------------------------------------------------------------------\n"
				       + "15/05/2026 | Biglietti Treno| Paolo     | 100.00 € | COMUNE\n"
				       + "16/05/2026 | Souvenir Roma  | Nicola    |  20.00 € | PERSONALE\n"
				       + "17/05/2026 | Cena Trattoria | Nicola    |  60.00 € | COMUNE");
		
		JScrollPane scrollSpese = new JScrollPane(txtSpese);
		scrollSpese.setBounds(20, 20, 520, 200);
		panelSpese.add(scrollSpese);

		// Bottone "+ INSERISCI SPESA" (Azzurro)
		JButton btnInserisciSpesa = new JButton("+ INSERISCI SPESA");
		btnInserisciSpesa.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnInserisciSpesa.setBackground(new Color(218, 232, 252));
		btnInserisciSpesa.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnInserisciSpesa.setFocusPainted(false);
		btnInserisciSpesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInserisciSpesa.setBounds(20, 280, 160, 40);
		panelSpese.add(btnInserisciSpesa);

		// Bottone "VEDI REPORT" (Viola)
		JButton btnVediReport = new JButton("VEDI REPORT");
		btnVediReport.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnVediReport.setBackground(new Color(225, 213, 231));
		btnVediReport.setBorder(new LineBorder(new Color(150, 115, 166), 1, true));
		btnVediReport.setFocusPainted(false);
		btnVediReport.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnVediReport.setBounds(380, 280, 160, 40);
		panelSpese.add(btnVediReport);

		
		/* =========================================================
		 * TAB 2: SALDI E RIMBORSI (Seconda Immagine)
		 * ========================================================= */
		JPanel panelSaldi = new JPanel();
		panelSaldi.setLayout(null);
		tabbedPane.addTab("SALDI E RIMBORSI", null, panelSaldi, null);

		// Saldo Paolo (Credito - Verde)
		JLabel lblPaolo = new JLabel("• Paolo Augusto (Tu):");
		lblPaolo.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblPaolo.setBounds(30, 40, 200, 25);
		panelSaldi.add(lblPaolo);

		JLabel lblCredito = new JLabel("+ 20.00 € (Credito)");
		lblCredito.setForeground(new Color(34, 139, 34)); // Verde Scuro
		lblCredito.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblCredito.setBounds(250, 40, 200, 25);
		panelSaldi.add(lblCredito);

		// Saldo Nicola (Debito - Rosso)
		JLabel lblNicola = new JLabel("• Nicola Barricelli:");
		lblNicola.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNicola.setBounds(30, 90, 200, 25);
		panelSaldi.add(lblNicola);

		JLabel lblDebito = new JLabel("- 20.00 € (Debito)");
		lblDebito.setForeground(Color.RED);
		lblDebito.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblDebito.setBounds(250, 90, 200, 25);
		panelSaldi.add(lblDebito);

		// Bottone "EFFETTUA UN RIMBORSO" (Verde)
		JButton btnRimborso = new JButton("EFFETTUA UN RIMBORSO");
		btnRimborso.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnRimborso.setBackground(new Color(213, 232, 212));
		btnRimborso.setBorder(new LineBorder(new Color(130, 179, 102), 1, true));
		btnRimborso.setFocusPainted(false);
		btnRimborso.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnRimborso.setBounds(30, 280, 200, 40);
		panelSaldi.add(btnRimborso);
	}
}