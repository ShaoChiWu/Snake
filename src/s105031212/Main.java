package s105031212;

import javax.swing.JOptionPane;

public class Main {
	public static void main(String[] args) {
		Scoreboard sc = new Scoreboard("Scoreboard.txt");
		Sound bgm = new Sound("4RBt_tiankong.wav");
		bgm.loop();
		SnakeModel model = new SnakeModel(120, 60);
		SnakeControl control = new SnakeControl(model);
		SnakeView view = new SnakeView(model, control);
		// add a observer, and let view to observe model
		model.addObserver(view);
		Scoreboard.bubbleSort();
		JOptionPane.showMessageDialog(null,
				"Get Ready To BKEAK THE RECORD: " + Scoreboard.keys.get(Scoreboard.keys.size() - 1), "Start",
				JOptionPane.INFORMATION_MESSAGE);
		// announce the highest pt.
		(new Thread(model)).start();
	}
}