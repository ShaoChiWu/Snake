package s105031212;

//SnakeView.java
import javax.swing.*;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Observer;

public class SnakeView implements Observer {
	SnakeControl control = null;
	SnakeModel model = null;

	JFrame mainFrame;
	Canvas paintCanvas;
	JLabel labelScore, labelMove, labelLife;

	public static final int canvasWidth = 1200;
	public static final int canvasHeight = 600;

	public static final int nodeWidth = 10;
	public static final int nodeHeight = 10;

	public SnakeView(SnakeModel model, SnakeControl control) {
		this.model = model;
		this.control = control;

		mainFrame = new JFrame("NTHU Snake");

		Container cp = mainFrame.getContentPane();
		// Top
		JPanel panelTop = new JPanel();
		panelTop.setLayout(new BorderLayout());
		labelScore = new JLabel("Score:", JLabel.CENTER);
		panelTop.add(labelScore, BorderLayout.NORTH);
		labelMove = new JLabel("Move:", JLabel.CENTER);
		panelTop.add(labelMove, BorderLayout.CENTER);
		labelLife = new JLabel("Life:", JLabel.CENTER);
		panelTop.add(labelLife, BorderLayout.SOUTH);
		cp.add(panelTop, BorderLayout.NORTH);
		// middle
		paintCanvas = new Canvas();
		paintCanvas.setSize(canvasWidth + 1, canvasHeight + 1);
		paintCanvas.addKeyListener(control);
		cp.add(paintCanvas, BorderLayout.CENTER);
		// buttom
		JPanel panelButtom = new JPanel();
		panelButtom.setLayout(new BorderLayout());
		JLabel labelHelp;
		labelHelp = new JLabel("PageUp, PageDown for speed;", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.NORTH);
		labelHelp = new JLabel("ENTER or R or S for restart;", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.CENTER);
		labelHelp = new JLabel("SPACE or P for pause", JLabel.CENTER);
		panelButtom.add(labelHelp, BorderLayout.SOUTH);
		cp.add(panelButtom, BorderLayout.SOUTH);

		mainFrame.addKeyListener(control);
		mainFrame.pack();
		mainFrame.setResizable(false);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mainFrame.setVisible(true);
	}

	void repaint() {
		Graphics g = paintCanvas.getGraphics();

		// draw background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, canvasWidth, canvasHeight);

		// draw the snake
		g.setColor(Color.GREEN);
		LinkedList na = model.nodeArray;
		Iterator it = na.iterator();
		while (it.hasNext()) {
			Node n = (Node) it.next();
			drawNode(g, n);
		}

		// draw the food
		if (model.numofFood == 0) {
			g.setColor(Color.RED);
			Node f = model.food[0];
			Node f1 = model.food[1];
			drawNode(g, f);
			drawNode(g, f1);
			model.eaten = 2;
			model.numofFood = 2;
		} else {
			if (model.eaten == 0) {
				g.setColor(Color.WHITE);
				Node f = model.food[0];
				drawNode(g, f);
				g.setColor(Color.RED);
				Node f1 = model.food[1];
				drawNode(g, f1);
			} else if (model.eaten == 1) {
				g.setColor(Color.WHITE);
				Node f = model.food[1];
				drawNode(g, f);
				g.setColor(Color.RED);
				Node f1 = model.food[0];
				drawNode(g, f1);
			} else {
				g.setColor(Color.RED);
				Node f = model.food[0];
				Node f1 = model.food[1];
				drawNode(g, f);
				drawNode(g, f1);
				model.eaten = 2;
			}
		}

		updateScore();
		updateMove();
		updateLife();

		g.setColor(Color.BLACK);
		Node w = model.wall[0];
		Node w1 = new Node(w.x + 1, w.y);
		Node w2 = new Node(w.x + 2, w.y);
		Node w3 = new Node(w.x + 3, w.y);
		Node w4 = new Node(w.x + 4, w.y);
		Node w5 = new Node(w.x + 5, w.y);
		Node ww = model.wall[1];
		Node ww1 = new Node(ww.x + 1, ww.y);
		Node ww2 = new Node(ww.x + 2, ww.y);
		Node ww3 = new Node(ww.x + 3, ww.y);
		Node ww4 = new Node(ww.x + 4, ww.y);
		Node ww5 = new Node(ww.x + 5, ww.y);

		drawNode(g, w);
		drawNode(g, w1);
		drawNode(g, w2);
		drawNode(g, w3);
		drawNode(g, w4);
		drawNode(g, w5);
		drawNode(g, ww);
		drawNode(g, ww1);
		drawNode(g, ww2);
		drawNode(g, ww3);
		drawNode(g, ww4);
		drawNode(g, ww5);

		g.setColor(Color.GRAY);
		Node h = model.hole[0];
		Node h1 = model.hole[1];
		drawNode(g, h);
		drawNode(g, h1);
	}

	private void drawNode(Graphics g, Node n) {
		g.fillRect(n.x * nodeWidth, n.y * nodeHeight, nodeWidth - 1, nodeHeight - 1);
	}

	public void updateScore() {
		String s = "Score: " + model.score;
		labelScore.setText(s);
	}

	public void updateMove() {
		String s = "Move: " + model.countMove;
		labelMove.setText(s);
	}

	public void updateLife() {
		String s = "Life: " + model.life;
		labelLife.setText(s);
	}

	public void update(Observable o, Object arg) {
		repaint();
	}
}