package msView.frame;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import msRun.Init.ConvertActionHandler;

public abstract class StatusViewFrame extends AbstFrame {
	private static final long serialVersionUID = 1L;
	protected JButton nextButton;
	protected int btnCount;
	protected int maxCount;

	public StatusViewFrame(ConvertActionHandler convertFrameListener) {
		super(convertFrameListener);
		setLayout(new BorderLayout());
		nextButton = new JButton(getNextButtonTitle());
		ParsingListener listener = new ParsingListener();
		nextButton.addActionListener(listener);
		nextButton.addKeyListener(listener);
		
		btnCount = 0;
		JPanel bottomPanel = new JPanel();
		bottomPanel.add(nextButton);
		bottomPanel.add(convertButton);
		addStatusPanel();
		add(bottomPanel, BorderLayout.SOUTH);
	}
	@Override
	public void initialize() {
		btnCount = 0;
		nextButton.setVisible(true);
		convertButton.setVisible(false);
	}
	protected abstract String getNextButtonTitle();
	protected abstract void addStatusPanel();
	protected abstract void updateView();
	protected void fixSegmentAlloc() {};
	protected void setMaxCount(int maxCount) {
		this.maxCount = maxCount;
	}
	private class ParsingListener extends KeyAdapter implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			action();
		}
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER)
				action();
		}
	}
	private void action() {
		updateView();
		btnCount++;
		if (btnCount == maxCount) {
			fixSegmentAlloc();
			nextButton.setVisible(false);
			convertButton.setVisible(true);
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					convertButton.requestFocus();
				}
			});
		}
	}
}
