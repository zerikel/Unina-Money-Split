package boundaries;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Cursor;
import javax.swing.border.LineBorder;

import control.MainController;

import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import control.MainController;
public class LoginFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtEmail;
	private JPasswordField passwordField;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginFrame frame = new LoginFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginFrame() {
		setTitle("Login Utente");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 428, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblEmail = new JLabel("Email:");
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblEmail.setBounds(22, 57, 47, 43);
		contentPane.add(lblEmail);
		
		txtEmail = new JTextField();
		txtEmail.setBounds(110, 65, 218, 32);
		contentPane.add(txtEmail);
		txtEmail.setColumns(10);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblPassword.setBounds(22, 110, 79, 43);
		contentPane.add(lblPassword);
		
		JButton btnAccedi = new JButton("ACCEDI");
		btnAccedi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String emailInserita = txtEmail.getText();
				String passwordInserita = new String(passwordField.getPassword());
				
				boolean loginSuccesso = MainController.getInstance().effettuaLogin(emailInserita, passwordInserita);
				
				if (loginSuccesso)
				{
					dispose();
;				}
			}
		});
		btnAccedi.setBorder(new LineBorder(new Color(108, 142, 191), 1, true));
		btnAccedi.setFocusPainted(false);
		btnAccedi.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnAccedi.setFont(new Font("Arial", Font.BOLD, 12));
		btnAccedi.setForeground(new Color(0, 0, 0));
		btnAccedi.setBackground(new Color(218, 232, 252));
		btnAccedi.setBounds(110, 183, 218, 49);
		contentPane.add(btnAccedi);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(110, 118, 218, 32);
		contentPane.add(passwordField);

	}
}
