package Project1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Random;
import java.util.Vector;

import javax.swing.JPanel;

class GamePanel extends JPanel implements MouseListener {

	int count = 3;
	int x, y; 
	int check; 

	String time;

	boolean game_start = false;

	Random ran_num = new Random();
	Vector rect_select = new Vector();
		
	NumberSave ns;

	GamePanel() {
		this.addMouseListener(this);
	}

	public void countDown(int count) {
		switch (count) {
		case 0:
			this.count = 3;
			break;
		case 1:
			this.count = 2;
			break;
		case 2:
			this.count = 1;
			break;
		case 3:
			this.count = 0;
			game_start = false;
			break;
		}
	}

	public void gameStart(boolean game_start) {
		this.game_start = game_start;
		if (this.game_start) {
			check = 1;
			for (int i = 0; i < 5; ++i) {
				for (int j = 0; j < 5; ++j) {
					int num = 0;
					int xx = 5 + i * 80;
					int yy = 5 + j * 80;
					num = ran_num.nextInt(25) + 1;
					for (int k = 0; k < rect_select.size(); ++k) {
						ns = (NumberSave) rect_select.get(k);
						if (ns.number == num) {
							num = ran_num.nextInt(25) + 1;
							k = -1;
							}
						}

						ns = new NumberSave(xx, yy, num);
						rect_select.add(ns);

				}

			}

		}

	}

	public void paint(Graphics g) {
		g.drawRect(0, 0, 400, 400);
		if (game_start) {
			g.setFont(new Font("Default", Font.BOLD, 50));
			g.drawString("CountDown", 70, 150);
			g.setFont(new Font("Default", Font.BOLD, 100));
			g.drawString("" + count, 170, 250);
		} else if (!game_start && count == 0) {
			for (int i = 0; i < rect_select.size(); ++i) {
				ns = (NumberSave) rect_select.get(i);
				g.drawRect(ns.x, ns.y, 70, 70);
				g.setFont(new Font("Default", Font.BOLD, 30));
				g.drawString("" + ns.number, ns.x + 22, ns.y + 45);
			}

			g.setColor(Color.red);
			g.drawRect(x * 80 + 5, y * 80 + 5, 70, 70);

		}

		if (check > 50) {
			g.setColor(Color.blue);
			g.setFont(new Font("Default", Font.BOLD, 50));
			g.drawString("GAME CLEAR!", 40, 150);
			g.setColor(Color.red);
			g.setFont(new Font("Default", Font.BOLD, 40));
			g.drawString("" + time, 90, 250);								
		}

	}

	public void ClearTime(String time) {
		this.time = time;
	}

	public void mouseClicked(MouseEvent e) {
			
	}

	public void mousePressed(MouseEvent e) {
		x = e.getX() / 80;
		y = e.getY() / 80;
			
		if (count == 0) {
			for (int i = 0; i < rect_select.size(); ++i) {
				ns = (NumberSave) rect_select.get(i);
				if (x == ns.x / 80 && y == ns.y / 80) {
					int xx = ns.x;
					int yy = ns.y;
					if (ns.number - check == 0) {
						check++;
						rect_select.remove(i);
						if (check < 27) {
							int num = 0;
							num = ran_num.nextInt(25) + 26;
							for (int k = 0; k < rect_select.size(); ++k) {
								ns = (NumberSave) rect_select.get(k);
								if (ns.number == num) {
									num = ran_num.nextInt(25) + 26;
									k = -1;
								}
							}

							ns = new NumberSave(xx, yy, num);
							rect_select.add(ns);

						}
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
			
	}

	public void mouseEntered(MouseEvent e) {
			
	}

	public void mouseExited(MouseEvent e) {
			
	}

}
