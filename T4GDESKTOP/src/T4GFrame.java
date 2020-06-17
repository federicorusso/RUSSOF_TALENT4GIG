import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import javax.swing.DefaultComboBoxModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class T4GFrame {

	private JFrame frame;
	
	private String[] columnNames = {"ID",
            "Name",
            "Description",
            "Periodicity",
            "Start Time",
            "End Time",
            "Status"};
	private JPanel tablePanel;
	private JButton actionButton;
	private JComboBox cbbox;
	private JTextArea txtRetrieveInformationAbout;
	private JTextField txtField;
	private JTextField textField_1;
	private JTable tableData;
	private JScrollPane scrollPane;
	private JTextField txtApplicationDevelopedBy;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					T4GFrame window = new T4GFrame();
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
	public T4GFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 838, 526);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		txtField = new JTextField();
		txtField.setColumns(10);
		txtField.setBounds(274, 120, 126, 20);
		frame.getContentPane().add(txtField);
		
		cbbox = new JComboBox();
		cbbox.setModel(new DefaultComboBoxModel(ProcessStateEnum.values()));
		cbbox.setBounds(419, 119, 126, 22);
		frame.getContentPane().add(cbbox);
		
		tablePanel = new JPanel();
		tablePanel.setLayout(null);
		tablePanel.setBounds(98, 186, 631, 220);
		frame.getContentPane().add(tablePanel);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 0, 611, 220);
		tablePanel.add(scrollPane);
		
		tableData = new JTable();
		tableData.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "NAME", "DESCRIPTION", "PERIODICITY", "START TIME", "END TIME", "STATUS"
			}
		));
		scrollPane.setViewportView(tableData);
		
		actionButton = new JButton("Retrieve data");
		actionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Integer processId = null;
				if (!txtField.getText().isEmpty()) {
					try {
						processId = Integer.parseInt(txtField.getText());
					} catch (Exception ex) {
						JOptionPane.showMessageDialog(null, "Invalid input provided as process ID:\nenter a valid number and try again.", "T4G Process Monitor", JOptionPane.ERROR_MESSAGE);
						return;
					}
				}
				
				ProcessStateEnum enm = ProcessStateEnum.valueOf(cbbox.getSelectedItem().toString());
				
				String queryParam = "";
				if (processId != null && enm != ProcessStateEnum.NONE) {
					queryParam = "?processId="+processId.toString()+"&processStatus="+enm.toString();
				} else if (processId != null) {
					queryParam = "?processId="+processId.toString();
				} else if (enm != ProcessStateEnum.NONE) {
					queryParam = "?processStatus="+enm.toString();
				}
				
				List<ProcessInfo> list = new ArrayList<ProcessInfo>();
				// For testing purposes
				list.add(new ProcessInfo(1, "Process 1", "First process", "WEEKLY", new Date(1592406705000L), new Date(1592409045000L)));
				list.get(0).setProcessState(ProcessStateEnum.COMPLETED);
				list.add(new ProcessInfo(2, "Process 2", "Second process", "MONTHLY", new Date(1592406705000L), new Date(1592409045000L)));
				list.get(1).setProcessState(ProcessStateEnum.NOTSTARTED);
				
				// FOR BACKEND CONNECTION
				/*URL url;
				try {
					url = new URL("https://myBackend.com" + queryParam);
					HttpURLConnection con = (HttpURLConnection) url.openConnection();
					con.setRequestMethod("GET");
					if (con.getResponseCode() != HttpURLConnection.HTTP_OK) {
						JOptionPane.showMessageDialog(null, "Backend returned code " + String.valueOf(con.getResponseCode()), "T4G Process Monitor", JOptionPane.ERROR_MESSAGE);
						return;
					}
					
					
					try(BufferedReader br = new BufferedReader(
							  new InputStreamReader(con.getInputStream(), "utf-8"))) {
							    StringBuilder jsonInline = new StringBuilder();
							    String responseLine = null;
							    while ((responseLine = br.readLine()) != null) {
							    	jsonInline.append(responseLine.trim());
							    }
							    
							    java.lang.reflect.Type processInfoListType = new TypeToken<ArrayList<ProcessInfo>>(){}.getType();
							    Gson g = new Gson();
							    list = g.fromJson(jsonInline.toString(), processInfoListType);
							    
							}
					
				} catch (MalformedURLException e1) {
					// TODO
					e1.printStackTrace();
				} catch (ProtocolException e1) {
					// TODO
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO
					e1.printStackTrace();
				}
				
				// Implementation when backend is active
				DefaultTableModel tm = (DefaultTableModel) tableData.getModel();
				tm.setRowCount(0);
				if (!list.isEmpty()) {
					for (ProcessInfo pInfo : list) {
						tm.addRow(new Object[]{String.valueOf(pInfo.getId()), pInfo.getName(), pInfo.getDescription(), pInfo.getPeriodicity(),
								pInfo.getProcessingStartTime(), pInfo.getProcessingEndTime(), pInfo.getProcessState().toString()});
					}
				}*/
				
				// OFFLINE implementation
				DefaultTableModel tm = (DefaultTableModel) tableData.getModel();
				tm.setRowCount(0);
				
				if (processId != null && enm != ProcessStateEnum.NONE) {
					if (!list.isEmpty()) {
						for (ProcessInfo pInfo : list) {
							if (pInfo.getId() == processId && pInfo.getProcessState().equals(enm)) {
								tm.addRow(new Object[]{String.valueOf(pInfo.getId()), pInfo.getName(), pInfo.getDescription(), pInfo.getPeriodicity(),
										pInfo.getProcessingStartTime(), pInfo.getProcessingEndTime(), pInfo.getProcessState().toString()});
							}
						}
					}
				} else if (processId != null) {
					if (!list.isEmpty()) {
						for (ProcessInfo pInfo : list) {
							if (pInfo.getId() == processId) {
								tm.addRow(new Object[]{String.valueOf(pInfo.getId()), pInfo.getName(), pInfo.getDescription(), pInfo.getPeriodicity(),
										pInfo.getProcessingStartTime(), pInfo.getProcessingEndTime(), pInfo.getProcessState().toString()});
							}
						}
					}
				} else if (enm != ProcessStateEnum.NONE) {
					if (!list.isEmpty()) {
						for (ProcessInfo pInfo : list) {
							if (pInfo.getProcessState().equals(enm)) {
								tm.addRow(new Object[]{String.valueOf(pInfo.getId()), pInfo.getName(), pInfo.getDescription(), pInfo.getPeriodicity(),
										pInfo.getProcessingStartTime(), pInfo.getProcessingEndTime(), pInfo.getProcessState().toString()});
							}
						}
					}
				} else {
					if (!list.isEmpty()) {
						for (ProcessInfo pInfo : list) {
							tm.addRow(new Object[]{String.valueOf(pInfo.getId()), pInfo.getName(), pInfo.getDescription(), pInfo.getPeriodicity(),
									pInfo.getProcessingStartTime(), pInfo.getProcessingEndTime(), pInfo.getProcessState().toString()});
						}
					}
				}			

			}
		});
		actionButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		actionButton.setBounds(342, 150, 148, 25);
		frame.getContentPane().add(actionButton);
		
		txtRetrieveInformationAbout = new JTextArea();
		txtRetrieveInformationAbout.setText("Retrieve information about all processes,\r\nor specify a process ID and/or a process status");
		txtRetrieveInformationAbout.setFont(new Font("Arial", Font.ITALIC, 12));
		txtRetrieveInformationAbout.setColumns(10);
		txtRetrieveInformationAbout.setBounds(274, 65, 272, 62);
		frame.getContentPane().add(txtRetrieveInformationAbout);
		
		textField_1 = new JTextField();
		textField_1.setText("Process Monitoring by T4G");
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("Arial Black", Font.PLAIN, 14));
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(274, 11, 271, 43);
		frame.getContentPane().add(textField_1);
		
		txtApplicationDevelopedBy = new JTextField();
		txtApplicationDevelopedBy.setHorizontalAlignment(SwingConstants.CENTER);
		txtApplicationDevelopedBy.setText("Application developed by Federico Russo for Talent4Gig. All rights reserved \u00A9 2020");
		txtApplicationDevelopedBy.setBounds(0, 467, 822, 20);
		frame.getContentPane().add(txtApplicationDevelopedBy);
		txtApplicationDevelopedBy.setColumns(10);
		TableModel tModel = new TableModel() {
			
			@Override
			public int getColumnCount() {
		         return columnNames.length;
		    }

		    @Override
		    public boolean isCellEditable(int row, int col) {
		         return false;
		    }


		    @Override
		    public String getColumnName(int index) {
		        return columnNames[index];
		    }
			
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addTableModelListener(TableModelListener l) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public int getRowCount() {
				// TODO Auto-generated method stub
				return 0;
			}
		};
	}
}
