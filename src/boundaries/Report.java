
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
		// 1. Impostazioni Finestra (Ingrandita l'altezza per farci stare la torta)
		setTitle("Report Statistico del Gruppo");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 560); 
		
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE); 
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		// 2. Titolo
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
		
		// 3. Statistiche Generali
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
		
		// 4. VERO GRAFICO A TORTA CON JAVAFX
		// Creiamo il pannello ponte tra Swing e JavaFX
		JFXPanel fxPanel = new JFXPanel();
		fxPanel.setBounds(30, 150, 500, 220); // Spazio più grande per il grafico
		contentPane.add(fxPanel);
		
		// JavaFX ha bisogno di essere avviato nel suo "Thread" (processo) specifico
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				creaGraficoJavaFX(fxPanel);
			}
		});
		
		// 5. Tabella finale (Spostata più in basso per fare spazio al grafico)
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
	
	/**
	 * Metodo che disegna fisicamente il grafico a torta usando JavaFX.
	 */
	private void creaGraficoJavaFX(JFXPanel fxPanel) {
		// 1. Inseriamo i dati del grafico
		ObservableList<PieChart.Data> datiTorta = FXCollections.observableArrayList(
			new PieChart.Data("Spese Comuni (88.9%)", 88.9),
			new PieChart.Data("Spese Personali (11.1%)", 11.1)
		);
		
		// 2. Creiamo il grafico a torta e gli passiamo i dati
		PieChart graficoTorta = new PieChart(datiTorta);
		graficoTorta.setTitle("Ripartizione delle Spese");
		graficoTorta.setLegendVisible(true); // Mostra la legenda con i colori
		
		// 3. Creiamo la scena JavaFX e la agganciamo al pannello ponte di Swing
		Scene scene = new Scene(new Group(graficoTorta));
		
		// Impostiamo le dimensioni del grafico uguali a quelle del pannello (500x220)
		graficoTorta.setPrefSize(500, 220); 
		
		fxPanel.setScene(scene);
	}
}