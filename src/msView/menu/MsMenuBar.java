package msView.menu;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JMenuBar;

import fileIO.DialogFactory;
import msObserver.FileObserver;

public class MsMenuBar extends JMenuBar {
	private static final long serialVersionUID = 1L;
	private MsFileMenu fileMenu;
	private ActionListener fileMenuHandler;
	private FileObserver fileObserver;
	private DialogFactory dialogFactory;
	public MsMenuBar(Component superComponent) {
		fileMenuHandler = new FileMenuHandler();
		dialogFactory = new DialogFactory(superComponent);
		fileMenu = new MsFileMenu(fileMenuHandler);
		add(fileMenu);
	}
	private class FileMenuHandler implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String actionCommand = e.getActionCommand();
			if(actionCommand.equals("eNew"))
				fileObserver.initialize();
			else if(actionCommand.equals("eSave")) {
				String source = fileObserver.getAssemblyCode();
				File file = dialogFactory.createFileSaveDialog();
				if (file == null) return;
				save(file, source);
			} else if(actionCommand.equals("eLoad")) {
				File file = dialogFactory.createFileOpenDialog();
				if (file == null) return;
				String source = getSource(file);
				fileObserver.initialize();
				fileObserver.setAssemblyCode(source);
			}
		}

		private String getSource(File file) {
			StringBuffer buffer = new StringBuffer();
			try {
				BufferedReader br = new BufferedReader(
						new FileReader(file));
				while (br.ready()) 
					buffer.append(br.readLine()+"\n");
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return buffer.toString();
		}

		private void save(File file, String source) {
			try {
				String fileName = file.getPath()+".txt";
				BufferedWriter bw = new BufferedWriter(
						new FileWriter(new File(fileName)));
				bw.write(source);
				bw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public void setFileObserver(FileObserver fileObserver) {
		this.fileObserver = fileObserver;
	}
}
