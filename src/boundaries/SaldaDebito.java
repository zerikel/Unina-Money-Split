package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SaldaDebito extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtImporto;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SaldaDebito frame = new SaldaDebito();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public SaldaDebito() {
		// 1. Impostazioni Finestra
		setTitle("Registra Rimborso (Movimento)");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 420, 270); 
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null); 
		
		// 2. Sezione "Seleziona quota debito aperta"
		JLabel lblSelezionaQuota = new JLabel("Seleziona quota debito aperta:");
		lblSelezionaQuota.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSelezionaQuota.setForeground(Color.BLACK); 
		lblSelezionaQuota.setBounds(40, 30, 250, 25);
		contentPane.add(lblSelezionaQuota);
		
		JComboBox<String> comboQuota = new JComboBox<String>();
		comboQuota.addItem("17/05 - Quota di 30.00 \u20AC a Nicola per Cena");
		comboQuota.setBackground(Color.WHITE);
		comboQuota.setBounds(40, 60, 320, 25);
		contentPane.add(comboQuota);
		
		// 3. Sezione "Importo"
		JLabel lblImporto = new JLabel("Importo (\u20AC):");
		lblImporto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblImporto.setForeground(Color.BLACK); 
		lblImporto.setBounds(40, 110, 100, 25);
		contentPane.add(lblImporto);
		
		txtImporto = new JTextField();
		txtImporto.setText("30.00"); // Questo in futuro lo caricherete dal database
		
		// --- LA MODIFICA È QUI ---
		txtImporto.setEditable(false); // Impedisce di scriverci dentro!
		txtImporto.setBackground(new Color(240, 240, 240)); // Sfondino grigio chiaro per far capire che è bloccato
		txtImporto.setForeground(Color.DARK_GRAY); // Testo grigio scuro
		// -------------------------
		
		txtImporto.setBounds(140, 110, 220, 25);
		contentPane.add(txtImporto);
		txtImporto.setColumns(10);
		
		// 4. Bottone CONFERMA RIMBORSO
		JButton btnConferma = new JButton("CONFERMA RIMBORSO");
		btnConferma.setBackground(new Color(35, 60, 40)); 
		btnConferma.setForeground(Color.WHITE); 
		btnConferma.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnConferma.setFocusPainted(false);
		btnConferma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnConferma.setBounds(110, 165, 180, 40);
		contentPane.add(btnConferma);
	}
}