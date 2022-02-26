package Project1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RecordFrame extends JFrame {
	
	private JPanel panel;
	
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private Object ob[][] = new Object[0][3];
	private JTable table;
	private JScrollPane scroll;
	private String colName[] = { "회차", "이름", "시간 기록" };
	private DefaultTableModel model;
	
	public RecordFrame() {
		panel = new JPanel();
		model = new DefaultTableModel(ob, colName);
		table = new JTable(model);
		scroll = new JScrollPane(table);
		this.add("Center",scroll);
		setTitle("게임 기록");
		setBounds(400, 400, 400, 400);
		setVisible(true);
        select();
	}

	public void select() {
		String sql = "select * from record";
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/game", "root", "apmsetup");
            pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
            while(rs.next()){            
			int num = rs.getInt("num");
			String name = rs.getString("name");
			String time = rs.getString("time");
			Object data[] = {num, name, time};
			model.addRow(data);
            }
		} catch(Exception e) {
			System.out.println(e.getMessage());
		} finally {
			try {
			rs.close();
			pstmt.close();
			conn.close();
			} catch(Exception ex){ }
		}
	}

}