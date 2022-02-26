package Project1;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

class MainFrame extends JFrame implements MouseListener, Runnable {
		
	private JLabel lb_title = new JLabel();
	private JLabel lb_time = new JLabel();
	private JButton bt_start = new JButton("START");
	private JButton bt_reset = new JButton("RESET");
	private JButton bt_record = new JButton("RECORD");
		
	SimpleDateFormat time_format;
	String show_time;
	long start_time, current_time, actual_time;

	boolean time_run = false;
	boolean tf = true;
		
	Thread th;
	GamePanel gp = new GamePanel();
	DataBase db = new DataBase();

	MainFrame() {
		init();
		start();
		setTitle("1 부터 50 까지!");
		setSize(600, 600);
		Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
		int xpos = (int) (screen.getWidth() / 2 - getWidth() / 2);
		int ypos = (int) (screen.getHeight() / 2 - getHeight() / 2);
		setLocation(xpos, ypos);
		setResizable(false);
		setVisible(true);
	}

	public void init() {
		Container con = this.getContentPane();
		con.setLayout(null);
		con.setBackground(Color.lightGray);
		lb_title.setBounds(190, 10, 300, 30);
		lb_title.setFont(new Font("Default", Font.CENTER_BASELINE, 35));
		con.add(lb_title);
		lb_time.setBounds(400, 50, 150, 30);
		lb_time.setFont(new Font("Default", Font.BOLD, 20));
		con.add(lb_time);
		bt_start.setBounds(100, 520, 100, 30);
		bt_start.setFont(new Font("Default", Font.BOLD, 15));
		con.add(bt_start);
		bt_reset.setBounds(250, 520, 100, 30);
		bt_reset.setFont(new Font("Default", Font.BOLD, 15));
		con.add(bt_reset);
		bt_record.setBounds(400, 520, 100, 30);
		bt_record.setFont(new Font("Default", Font.BOLD, 15));
		con.add(bt_record);
		gp.setBounds(100, 100, 410, 410);
		con.add(gp);
	}

	public void start() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addMouseListener(this);
		bt_start.addMouseListener(this);
		bt_reset.addMouseListener(this);			
		bt_record.addMouseListener(this);
			
		th = new Thread(this);
		th.start();

		time_format = new SimpleDateFormat("HH:mm:ss.SSS");
		lb_time.setText("00:00:00.000");
		lb_title.setText("1부터 50까지!");
		gp.gameStart(false);
	}

	public void run() {
		while (tf) {
			try {
				repaint();
				TimeCheck();
				Thread.sleep(15);
			} catch (Exception e) { }
		}
	}

	public void TimeCheck() {
		if (time_run) {
			current_time = System.currentTimeMillis();
			actual_time = current_time - start_time;
			gp.countDown((int) actual_time / 1000);
			if (!gp.game_start && gp.check <= 50) {
				show_time = time_format.format(actual_time - 32403000);
				lb_time.setText(show_time);
			}
		}

		if (gp.check > 50) {
			gp.ClearTime(lb_time.getText());
			String name = JOptionPane.showInputDialog("이름을 입력하세요!");
			db.record(show_time, name);
			tf = false;
		}

	}

	public void mouseClicked(MouseEvent e) {
		if (e.getSource() == bt_start) {
			if (!time_run && !gp.game_start) {
				start_time = System.currentTimeMillis();
				gp.rect_select.clear();
				time_run = true;
				gp.gameStart(true);
			}
		} else if (e.getSource() == bt_reset) {
			start_time = 0;
			lb_time.setText("00:00:00.000");
			gp.rect_select.clear();
			gp.countDown(0);
			time_run = false;
			gp.gameStart(false);
			gp.check = 0;
			tf = true;
			start();
			} else {
				new RecordFrame();
			}

	}
		
	public void mousePressed(MouseEvent e) {
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

}
