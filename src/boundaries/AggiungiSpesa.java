package boundaries;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane; // Serve per i pop-up di errore
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class AggiungiSpesa extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtDescrizione;
	private JTextField txtImporto;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AggiungiSpesa frame = new AggiungiSpesa();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public AggiungiSpesa() {
		setTitle("Registra Nuova Spesa");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 310);
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblDescrizione = new JLabel("Descrizione:");
		lblDescrizione.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblDescrizione.setForeground(Color.BLACK);
		lblDescrizione.setBounds(40, 40, 100, 25);
		contentPane.add(lblDescrizione);
		
		txtDescrizione = new JTextField();
		txtDescrizione.setBounds(150, 40, 190, 25);
		contentPane.add(txtDescrizione);
		txtDescrizione.setColumns(10);
		
		JLabel lblImporto = new JLabel("Importo Tot (\u20AC):");
		lblImporto.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblImporto.setForeground(Color.BLACK);
		lblImporto.setBounds(40, 90, 110, 25);
		contentPane.add(lblImporto);
		
		txtImporto = new JTextField();
		txtImporto.setBounds(150, 90, 190, 25);
		contentPane.add(txtImporto);
		txtImporto.setColumns(10);
		
		JLabel lblTipoSpesa = new JLabel("Tipo Spesa:");
		lblTipoSpesa.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblTipoSpesa.setForeground(Color.BLACK);
		lblTipoSpesa.setBounds(40, 140, 100, 25);
		contentPane.add(lblTipoSpesa);
		
		JComboBox<String> comboTipoSpesa = new JComboBox<String>();
		comboTipoSpesa.addItem("COMUNE");
		comboTipoSpesa.addItem("PERSONALE"); 
		comboTipoSpesa.setBackground(Color.WHITE);
		comboTipoSpesa.setBounds(150, 140, 190, 25);
		contentPane.add(comboTipoSpesa);
		
		JButton btnSalvaSpesa = new JButton("SALVA SPESA");
		btnSalvaSpesa.setBackground(new Color(40, 55, 75));
		btnSalvaSpesa.setForeground(Color.WHITE);
		btnSalvaSpesa.setFont(new Font("Tahoma", Font.BOLD, 12));
		btnSalvaSpesa.setFocusPainted(false);
		btnSalvaSpesa.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnSalvaSpesa.setBounds(180, 200, 160, 40);
		
		btnSalvaSpesa.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String descrizione = txtDescrizione.getText().trim();
				String importoStr = txtImporto.getText().trim();
				
				if (descrizione.isEmpty() || importoStr.isEmpty()) {
					JOptionPane.showMessageDialog(
							AggiungiSpesa.this, 
							"Attenzione: Devi compilare tutti i campi!", 
							"Campi vuoti", 
							JOptionPane.WARNING_MESSAGE);
					return; 
				}
				
				try {
					importoStr = importoStr.replace(",", ".");
					double importoNum = Double.parseDouble(importoStr);
					
					if (importoNum <= 0) {
						JOptionPane.showMessageDialog(
								AggiungiSpesa.this, 
								"Errore: L'importo deve essere maggiore di zero!", 
								"Importo non valido", 
								JOptionPane.ERROR_MESSAGE);
						return; 
					}
					
					String tipoSelezionato = (String) comboTipoSpesa.getSelectedItem();
					
					JOptionPane.showMessageDialog(
							AggiungiSpesa.this, 
							"Spesa di " + importoNum + "€ (" + descrizione + ") salvata come " + tipoSelezionato + "!", 
							"Successo", 
							JOptionPane.INFORMATION_MESSAGE);
					
					txtDescrizione.setText("");
					txtImporto.setText("");
					txtDescrizione.requestFocus();
					
				} catch (NumberFormatException ex) {
					JOptionPane.showMessageDialog(
							AggiungiSpesa.this, 
							"Errore: Hai inserito dei caratteri non validi nell'importo. Inserisci solo numeri!", 
							"Formato errato", 
							JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		contentPane.add(btnSalvaSpesa);
	}
}