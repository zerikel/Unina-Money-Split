
package boundaries;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

// --- IMPORT PER JAVAFX ---
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.Group;

public class Report extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Report frame = new Report();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Report() {
		setTitle("Report Statistico del Gruppo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 560); 
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblTitolo = new JLabel("Report Statistico del Gruppo");
		lblTitolo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitolo.setForeground(Color.BLACK);
		lblTitolo.setFont(new Font("Tahoma", Font.BOLD, 16));
		lblTitolo.setBounds(10, 20, 544, 25);
		contentPane.add(lblTitolo);
		
		JSeparator separator = new JSeparator();
		separator.setForeground(Color.GRAY);
		separator.setBounds(30, 55, 500, 2);
		contentPane.add(separator);
		
		JLabel lblStatsTitolo = new JLabel("STATISTICHE GENERALI (Viaggio a Roma)");
		lblStatsTitolo.setForeground(Color.BLACK);
		lblStatsTitolo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblStatsTitolo.setBounds(30, 75, 400, 20);
		contentPane.add(lblStatsTitolo);
		
		JLabel lblNumSpese = new JLabel("- Numero totale spese registrate: 3");
		lblNumSpese.setForeground(Color.BLACK);
		lblNumSpese.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblNumSpese.setBounds(30, 100, 300, 20);
		contentPane.add(lblNumSpese);
		
		JLabel lblImporto = new JLabel("- Importo monetario complessivo: 180.00 \u20AC");
		lblImporto.setForeground(Color.BLACK);
		lblImporto.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblImporto.setBounds(30, 120, 300, 20);
		contentPane.add(lblImporto);
		

		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBounds(30, 150, 500, 220);
		contentPane.add(fxPanel);
		
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				creaGraficoJavaFX(fxPanel);
			}
		});
		
		JTextArea txtTabella = new JTextArea();
		txtTabella.setEditable(false);
		txtTabella.setBackground(Color.WHITE); 
		txtTabella.setForeground(Color.BLACK); 
		txtTabella.setFont(new Font("Monospaced", Font.PLAIN, 13)); 
		txtTabella.setBounds(30, 390, 500, 100);
		
		String tabella = 
				"Partecipante | Importo Speso | Saldo Finale Corrente\n" +
				"----------------------------------------------------\n" +
				"Paolo        | 100.00 \u20AC      | + 20.00 \u20AC (Credito)\n" +
				"Nicola       | 80.00  \u20AC      | - 20.00 \u20AC (Debito)";
		txtTabella.setText(tabella);
		
		contentPane.add(txtTabella);
	}
	
	private void creaGraficoJavaFX(JFXPanel fxPanel) {
		ObservableList<PieChart.Data> datiTorta = FXCollections.observableArrayList(
			new PieChart.Data("Spese Comuni (88.9%)", 88.9),
			new PieChart.Data("Spese Personali (11.1%)", 11.1)
		);
		
		PieChart graficoTorta = new PieChart(datiTorta);
		graficoTorta.setTitle("Ripartizione delle Spese");
		graficoTorta.setLegendVisible(true); 
		
		Scene scene = new Scene(new Group(graficoTorta));
		
		graficoTorta.setPrefSize(500, 220); 
		
		fxPanel.setScene(scene);
	}
}