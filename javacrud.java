package project;

import java.awt.EventQueue;
import java.sql.*;


import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import net.proteanit.sql.DbUtils;

import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.DriverManager;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class javacrud {

	private JFrame frame;
	private JTextField nametxt;
	private JTextField lastnametxt;
	private JTextField phonetxt;
	private JTable table;
	private JTextField contactid;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					javacrud window = new javacrud();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public javacrud() {
		initialize();
		Connect();
		displayTable();
	}
	
	Connection conection;
	PreparedStatement preStatement;
	ResultSet resSet;
	
	
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conection = DriverManager.getConnection("jdbc:mysql://localhost/crud","root", "");
			
		}catch(ClassNotFoundException ex) {
			
			
			
		}catch(SQLException ex) {
			
		}
	}
	
	
	
	public void displayTable() {
		try {
			
			preStatement = conection.prepareStatement("SELECT * FROM contact");
			resSet = preStatement.executeQuery();
			table.setModel(DbUtils.resultSetToTableModel(resSet));
			
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		
		
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 869, 565);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Contact List");
		lblNewLabel.setFont(new Font("Yu Gothic", Font.BOLD, 20));
		lblNewLabel.setBounds(362, 10, 148, 58);
		frame.getContentPane().add(lblNewLabel);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Registration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(31, 58, 780, 140);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel_1 = new JLabel("Name: ");
		lblNewLabel_1.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		lblNewLabel_1.setBounds(27, 36, 58, 32);
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel_1_1 = new JLabel("Last Name");
		lblNewLabel_1_1.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		lblNewLabel_1_1.setBounds(256, 40, 83, 32);
		panel.add(lblNewLabel_1_1);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("Phone:");
		lblNewLabel_1_1_1.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		lblNewLabel_1_1_1.setBounds(501, 40, 58, 32);
		panel.add(lblNewLabel_1_1_1);
		
		nametxt = new JTextField();
		nametxt.setBounds(120, 40, 96, 19);
		panel.add(nametxt);
		nametxt.setColumns(10);
		
		lastnametxt = new JTextField();
		lastnametxt.setColumns(10);
		lastnametxt.setBounds(349, 40, 96, 19);
		panel.add(lastnametxt);
		
		phonetxt = new JTextField();
		phonetxt.setColumns(10);
		phonetxt.setBounds(593, 45, 96, 19);
		panel.add(phonetxt);
		
		JButton btnNewButton = new JButton("Save");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String contactName;
				String contactLastname;
				String contactPhone;
				
				contactName= nametxt.getText();
				contactLastname = lastnametxt.getText();
				contactPhone = phonetxt.getText();
				
				try {
					preStatement = conection.prepareStatement("INSERT INTO contact(Name,LastName,Phone)VALUES(?,?,?)");
					preStatement.setString(1, contactName);
					preStatement.setString(2, contactLastname);
					preStatement.setString(3, contactPhone);
					preStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Contact Created!!");
					
					displayTable();
					nametxt.setText("");
					lastnametxt.setText("");
					phonetxt.setText("");
					nametxt.requestFocus();
					
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
			}
		});
		btnNewButton.setBounds(238, 82, 85, 21);
		panel.add(btnNewButton);
		
		JButton btnClear = new JButton("Clear");
		btnClear.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				nametxt.setText("");
				lastnametxt.setText("");
				phonetxt.setText("");
				contactid.setText("");
				nametxt.requestFocus();
				
				
				
			}
		});
		btnClear.setBounds(398, 82, 85, 21);
		panel.add(btnClear);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(409, 236, 363, 282);
		frame.getContentPane().add(scrollPane);
		
		table = new JTable();
		scrollPane.setViewportView(table);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "Search Contact", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_1.setBounds(58, 238, 296, 117);
		frame.getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		JLabel lblNewLabel_1_2 = new JLabel("Contact ID:");
		lblNewLabel_1_2.setFont(new Font("Yu Gothic", Font.BOLD, 12));
		lblNewLabel_1_2.setBounds(10, 25, 83, 32);
		panel_1.add(lblNewLabel_1_2);
		
		contactid = new JTextField();
		contactid.addKeyListener(new KeyAdapter() {
		
		});
		contactid.setColumns(10);
		contactid.setBounds(103, 29, 96, 19);
		panel_1.add(contactid);
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String contactName;
				String contactLastname;
				String contactPhone;
				String contactId ;
				
				contactName= nametxt.getText();
				contactLastname = lastnametxt.getText();
				contactPhone = phonetxt.getText();
				contactId = contactid.getText(); 
				
				try {
					preStatement = conection.prepareStatement("UPDATE contact SET Name =?, LastName = ?, Phone = ? WHERE Id =?");
					preStatement.setString(1, contactName);
					preStatement.setString(2, contactLastname);
					preStatement.setString(3, contactPhone);
					preStatement.setString(4, contactId);
					preStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Contact Updated!!");
					
					displayTable();
					nametxt.setText("");
					lastnametxt.setText("");
					phonetxt.setText("");
					nametxt.requestFocus();
					
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
						
							
			}
		});
		btnUpdate.setBounds(103, 67, 85, 21);
		panel_1.add(btnUpdate);
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				String contactId ;
				
				
				contactId = contactid.getText(); 
				
				try {
					preStatement = conection.prepareStatement("DELETE FROM contact WHERE Id =?");
				
					preStatement.setString(1, contactId);
					preStatement.executeUpdate();
					JOptionPane.showMessageDialog(null, "Contact Deleted!!");
					
					displayTable();
					nametxt.setText("");
					lastnametxt.setText("");
					phonetxt.setText("");
					nametxt.requestFocus();
					
					
				}
				catch(SQLException e1) {
					e1.printStackTrace();
				}
				
				
				
				
			}
		});
		btnDelete.setBounds(201, 67, 85, 21);
		panel_1.add(btnDelete);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					
					String contactId = contactid.getText();
				
					preStatement = conection.prepareStatement("SELECT Name, LastName, Phone FROM contact WHERE Id = ?");
					preStatement.setString(1, contactId);
					ResultSet rs = preStatement.executeQuery();
					
					if(rs.next() == true) {
						String name = rs.getString(1);
						String lastName = rs.getString(2);
						String phone = rs.getString(3);
						

						nametxt.setText(name);
						lastnametxt.setText(lastName);
						phonetxt.setText(phone);
					}else {
						
						nametxt.setText("");
						lastnametxt.setText("");
						phonetxt.setText("");
						
					}
					
					
					
				}catch(SQLException ex){
					
				}
			}
		});
		btnSearch.setBounds(10, 67, 85, 21);
		panel_1.add(btnSearch);
		
		JButton btnExit = new JButton("Exit");
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		btnExit.setBounds(736, 27, 85, 21);
		frame.getContentPane().add(btnExit);
	}
}
