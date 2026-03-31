package msView.frame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import dto.emulator.EmulInfo;
import dto.emulator.ProcessControlBlock;
import msConstants.ViewConsts;
import msObserver.FinishProcessObserver;
import msRun.Init.ConvertActionHandler;
import msView.emulPanel.MemoryPanel;
import msView.emulPanel.cpu.CPUPanel;

public class EmulViewFrame extends AbstFrame implements FinishProcessObserver{
	private static final long serialVersionUID = 1L;
	private EmulPanel emulPanel;
	private EmulBottomPanel bottomPanel;
	private JButton nextCommandBtn;
	private JButton nextProcessBtn;
	private JButton finishButton;
	private int btnProcessCount;
	private int maxProcessCount;
	private boolean finishProcess;

	public EmulViewFrame(ConvertActionHandler convertFrameListener) {
		super(convertFrameListener);
		setLayout(new BorderLayout());
		InstProcessBtnHandler instProcessListener = new InstProcessBtnHandler();
		nextCommandBtn = new JButton(ViewConsts.Emul.nextCommandBtn);
		nextCommandBtn.addActionListener(instProcessListener);
		nextCommandBtn.addKeyListener(instProcessListener);
		nextCommandBtn.setActionCommand(ViewConsts.Emul.nextCommand);
		
		nextProcessBtn = new JButton(ViewConsts.Emul.nextProcessBtn);
		nextProcessBtn.addActionListener(instProcessListener);
		nextProcessBtn.addKeyListener(instProcessListener);
		nextProcessBtn.setActionCommand(ViewConsts.Emul.nextProcess);
		
		finishButton = new JButton(ViewConsts.Emul.finishBtn);
		finishButton.addActionListener(convertFrameListener);
		finishButton.addKeyListener(convertFrameListener);
		finishButton.setActionCommand(ViewConsts.Emul.finishActionCommand);
		emulPanel = new EmulPanel();
		bottomPanel = new EmulBottomPanel();
		add(emulPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}
	@Override
	public JButton getConvertButton() {
		return finishButton;
	}
	@Override
	public void initialize() {
		btnProcessCount = 0;
		nextCommandBtn.setVisible(true);
		nextProcessBtn.setVisible(false);
		finishButton.setVisible(false);
		emulController.setFinishObserver(this);
		ProcessControlBlock pcb = emulController.getPCB();
		finishProcess = false;
		EmulInfo emulInfo = pcb.getEmulInfo();
		emulPanel.initialize(emulInfo);
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				nextCommandBtn.requestFocus();
			}
		});
	}
	@Override
	protected JButton createConvertButton() {
		JButton button = new JButton(ViewConsts.Emul.buttonName);
		button.setActionCommand(ViewConsts.Emul.finishActionCommand);
		return button;
	}
	@Override
	protected Dimension getFrameDim() {
		return ViewConsts.Emul.viewDim;
	}
	@Override
	protected String getFrameTitle() {
		return ViewConsts.Emul.viewTitle;
	}
	private class InstProcessBtnHandler extends KeyAdapter implements ActionListener {
		@Override
		public void keyPressed(KeyEvent e) {
			if (e.getSource().equals(nextCommandBtn)) 
				nextCommand();
			else if (e.getSource().equals(nextProcessBtn)) 
				nextProcess();
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if (actionCommand.equals(ViewConsts.Emul.nextCommand)) 
				nextCommand();
			else if (actionCommand.equals(ViewConsts.Emul.nextProcess)) 
				nextProcess();
			
		}
	}
	protected void nextCommand() {
		EmulInfo emulInfo = emulController.nextEmulate();
		if (emulInfo != null) emulPanel.updateView(emulInfo);
		nextCommandBtn.setVisible(false);
		nextProcessBtn.setVisible(true);
		maxProcessCount = emulController.getMaxProcessCount();
		btnProcessCount = 0;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				nextProcessBtn.requestFocus();
			}
		});
	}
	protected void nextProcess() {
		EmulInfo emulInfo = emulController.nextProcess();
		if (emulInfo != null) emulPanel.updateView(emulInfo);
		btnProcessCount++;
		if (btnProcessCount == maxProcessCount) {
			nextProcessBtn.setVisible(false);
			if (finishProcess) {
				finishButton.setVisible(true);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						finishButton.requestFocus();
					}
				});
			}
			else {
				nextCommandBtn.setVisible(true);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						nextCommandBtn.requestFocus();
					}
				});
			}
		}
	}
	private class EmulPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private CPUPanel cpuPanel;
		private MemoryPanel memoryPanel;
		public EmulPanel() {
			setLayout(new GridLayout(2, 1));
			cpuPanel = new CPUPanel();
			memoryPanel = new MemoryPanel();
			setBorder(BorderFactory.createLineBorder(Color.black));
			add(cpuPanel);
			add(memoryPanel);
		}
		public void initialize(EmulInfo emulInfo) {
			cpuPanel.initialize(emulInfo);
			memoryPanel.initialize(emulInfo);
		}
		public void updateView(EmulInfo emulInfo) {
			cpuPanel.updateView(emulInfo);
			memoryPanel.updateView(emulInfo);
		}
	}
	private class EmulBottomPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public EmulBottomPanel() {
			setLayout(new BorderLayout());
			JPanel convertButtonPanel = new JPanel();
			convertButtonPanel.add(convertButton);
			add(convertButtonPanel, BorderLayout.WEST);
			JPanel processButtonPanel = new JPanel();
			processButtonPanel.add(nextCommandBtn);
			processButtonPanel.add(nextProcessBtn);
			processButtonPanel.add(finishButton);
			add(processButtonPanel, BorderLayout.CENTER);
		}
	}
	@Override
	public void setFinishProcess(boolean finishProcess) {
		this.finishProcess = finishProcess;
	}
}
