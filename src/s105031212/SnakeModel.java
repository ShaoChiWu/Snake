package s105031212;

//SnakeModel.java
import javax.swing.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Observable;
import java.util.Random;

class SnakeModel extends Observable implements Runnable {
	boolean[][] matrix; // anything there?
	LinkedList nodeArray = new LinkedList(); // the snake
	Node[] food = new Node[2]; // the foods
	Node[] wall = new Node[8]; // the walls(each has 4 nodes)
	Node[] hole = new Node[2]; // the hole
	int maxX;
	int maxY;
	int direction = UP; // default direction
	boolean running = false;

	int timeInterval = 200;
	double speedChangeRate = 0.75;
	boolean paused = false;

	int score = 0;
	int countMove = 0; // how far the snake goes
	int snakeSize = 0;
	int numofFood = 0;
	int eaten = 2;
	int life = 2;

	// UP and DOWN should be even
	// RIGHT and LEFT should be odd
	public static final int UP = 2;
	public static final int DOWN = 4;
	public static final int LEFT = 1;
	public static final int RIGHT = 3;

	public SnakeModel(int maxX, int maxY) {
		this.maxX = maxX;
		this.maxY = maxY;
		reset();
	}

	public void reset() {
		direction = SnakeModel.UP;
		timeInterval = 200;
		speedChangeRate = 0.75;
		paused = false;
		score = 0;
		countMove = 0;
		snakeSize = 0;
		numofFood = 0;
		eaten = 2;
		// initial the matrix
		matrix = new boolean[maxX][];
		for (int i = 0; i < maxX; ++i) {
			matrix[i] = new boolean[maxY];
			Arrays.fill(matrix[i], false);
		}
		// initial the snake
		int initArrayLength = 2;
		nodeArray.clear();
		for (int i = 0; i < initArrayLength; ++i) {
			int x = maxX / 2 + i;
			int y = maxY / 2;
			nodeArray.addLast(new Node(x, y));
			matrix[x][y] = true;
		}
		// the order of creating: hole, wall, food
		hole[0] = createHole();
		matrix[hole[0].x][hole[0].y] = true;
		hole[1] = createHole();
		hole[1].x = hole[0].x + 40;
		hole[1].y = hole[0].y;
		matrix[hole[1].x][hole[1].y] = true;

		for (int i = 0; i < 2; i++) {
			wall[i] = createWall();
			matrix[wall[i].x][wall[i].y] = true;
			matrix[wall[i].x + 1][wall[i].y] = true;
			matrix[wall[i].x + 2][wall[i].y] = true;
			matrix[wall[i].x + 3][wall[i].y] = true;
			matrix[wall[i].x + 4][wall[i].y] = true;
			matrix[wall[i].x + 5][wall[i].y] = true;
		}
		for (int i = 0; i < 2; i++) {
			food[i] = createFood();
			matrix[food[i].x][food[i].y] = true;
			numofFood++;
		}
	}

	// same direction and opposite direction is not allowed
	public void changeDirection(int newDirection) {
		if (direction % 2 != newDirection % 2) {
			direction = newDirection;
		}
	}

	public boolean moveOn() {
		Node n = (Node) nodeArray.getFirst();
		int x = n.x;
		int y = n.y;
		// set x,y according to direction
		switch (direction) {
		case UP:
			y--;
			break;
		case DOWN:
			y++;
			break;
		case LEFT:
			x--;
			break;
		case RIGHT:
			x++;
			break;
		}
		// new location is valid, do something
		if ((0 <= x && x < maxX) && (0 <= y && y < maxY)) {

			if (matrix[x][y]) { // there is something
				if (x == food[0].x && y == food[0].y) {// food
					eaten = 0;
					score++;
					numofFood--;
					nodeArray.addFirst(food[0]);// become longer
					food[0] = createFood();
					matrix[food[0].x][food[0].y] = true;// location for new food
					return true;
				} else if (x == food[1].x && y == food[1].y) {// similar as above
					eaten = 1;
					score++;
					numofFood--;
					nodeArray.addFirst(food[1]);
					food[1] = createFood();
					matrix[food[1].x][food[1].y] = true;
					return true;
				} else if (x == hole[0].x && y == hole[0].y) {// hole
					snakeSize = nodeArray.size();
					for (int i = 0; i < nodeArray.size(); i++) {
						n = (Node) nodeArray.removeFirst();// become shorter
						matrix[n.x][n.y] = false;
					}
					try {
						Thread.sleep(1000);
					} // stay for a while
					catch (InterruptedException e) {
						e.printStackTrace();
					}

					for (int i = 0; i < score + 2; i++) {
						direction = DOWN;
						nodeArray.addFirst(new Node(x + 40, y + 1));// snake returns
						matrix[x + 40][y + 1] = true;

					}
					nodeArray.removeFirst();
					System.out.print("in");
					matrix[x][y] = true;
					return true;
				} else if (x == hole[1].x && y == hole[1].y) {// similar as above
					snakeSize = nodeArray.size();
					for (int i = 0; i < nodeArray.size(); i++) {
						n = (Node) nodeArray.removeFirst();
						matrix[n.x][n.y] = false;
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}

					for (int i = 0; i < score + 2; i++) {
						direction = DOWN;
						nodeArray.addFirst(new Node(x - 40, y + 1));
						matrix[x - 40][y + 1] = true;
					}
					nodeArray.removeFirst();
					System.out.print("in");
					matrix[x][y] = true;
					return true;
				} else
					return false;

			} else { // meet nothing, keep going
				nodeArray.addFirst(new Node(x, y));
				matrix[x][y] = true;
				n = (Node) nodeArray.removeLast();
				matrix[n.x][n.y] = false;
				countMove++;
				return true;
			}
		} // go through the boundry
		if (direction == RIGHT) {
			nodeArray.addFirst(new Node(0, y));
			matrix[0][y] = true;
			n = (Node) nodeArray.removeLast();
			matrix[n.x][n.y] = false;
			countMove++;
			return true;
		}
		if (direction == DOWN) {
			nodeArray.addFirst(new Node(x, 0));
			matrix[x][0] = true;
			n = (Node) nodeArray.removeLast();
			matrix[n.x][n.y] = false;
			countMove++;
			return true;
		}
		if (direction == LEFT) {
			nodeArray.addFirst(new Node(maxX - 1, y));
			matrix[maxX - 1][y] = true;
			n = (Node) nodeArray.removeLast();
			matrix[n.x][n.y] = false;
			countMove++;
			return true;
		}
		if (direction == UP) {
			nodeArray.addFirst(new Node(x, maxY - 1));
			matrix[x][maxY - 1] = true;
			n = (Node) nodeArray.removeLast();
			matrix[n.x][n.y] = false;
			countMove++;
			return true;
		}
		return false; // run to the snake itself, fail
	}

	public void run() {
		running = true;
		while (running) {
			try {
				Thread.sleep(timeInterval);
			} catch (Exception e) {
				break;
			}

			if (!paused) {
				if (moveOn()) {
					setChanged(); // model inform view the data has changed
					notifyObservers();
				} else {
					if (life == 0) {
						Sound.stop();
						Sound noti = new Sound("3D.wav");
						Scoreboard.printResult(score);
						Sound.play();
						JOptionPane.showMessageDialog(null, "you failed", "Game Over", JOptionPane.INFORMATION_MESSAGE);
						break;
					} else {
						life--;
						Scoreboard.printResult(score);
						reset();
					}
				}
			}
		}
		running = false;
	}

//all createxxx() have feature out-of-boundry-proof and overlap-proof 
	private Node createFood() {
		int x = 0;
		int y = 0;
		do {
			Random r = new Random();
			x = r.nextInt(maxX);
			y = r.nextInt(maxY);
		} while (matrix[x][y]);

		return new Node(x, y);
	}

	private Node createWall() {
		int x = 0;
		int y = 0;
		do {
			Random r = new Random();
			x = r.nextInt(maxX - 7);
			y = r.nextInt(maxY - 7);
		} while (matrix[x][y] || matrix[x + 1][y] || matrix[x + 2][y] || matrix[x + 3][y] || matrix[x + 4][y]
				|| matrix[x + 5][y]);
		return new Node(x, y);
	}

	private Node createHole() {
		int x = 0;
		int y = 0;
		do {
			Random r = new Random();
			x = r.nextInt(maxX - 70);
			y = r.nextInt(maxY - 10);
		} while (matrix[x][y] || matrix[x + 1][y] || matrix[x + 2][y]);
		return new Node(x, y);
	}

	public void speedUp() {
		timeInterval *= speedChangeRate;
	}

	public void speedDown() {
		timeInterval /= speedChangeRate;
	}

	public void changePauseState() {
		paused = !paused;
	}

	public String toString() {
		String result = "";
		for (int i = 0; i < nodeArray.size(); ++i) {
			Node n = (Node) nodeArray.get(i);
			result += "[" + n.x + "," + n.y + "]";
		}
		return result;
	}
}

class Node {
	int x;
	int y;

	Node(int x, int y) {
		this.x = x;
		this.y = y;
	}
}