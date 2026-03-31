package fileIO;

import java.awt.Component;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class DialogFactory {
	public enum EMessageType {
		eSaveEnd("저장이 취소됩니다.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenEnd("불러오기가 취소됩니다.", "Ending Message", JOptionPane.INFORMATION_MESSAGE),
		eSaveSucceed("저장 성공했습니다!", "Success Message", JOptionPane.INFORMATION_MESSAGE),
		eOpenSucceed("불러오기 성공했습니다!", "Success Message", JOptionPane.INFORMATION_MESSAGE);
		private String message;
		private String title;
		private int messageType;
		private EMessageType(String message, String title, int messageType) {
			this.message = message;
			this.title = title;
			this.messageType = messageType;
		}
		public String getMessage() {
			return message;
		}
		public String getTitle() {
			return title;
		}
		public int getMessageType() {
			return messageType;
		}
	}
	Component parentComponent;
	private JFileChooser jFileChooser; 
	public DialogFactory(Component parentComponent) {
		this.parentComponent = parentComponent;
		jFileChooser = new JFileChooser(
				"C:\\Users\\minJae\\Documents\\여러 폴더들\\개발관련"
				+ "\\자바\\자바 플젝들\\주로쓰는자바\\최자바\\System_FinalProject"
				+ "\\source");
		jFileChooser.addChoosableFileFilter(
				new FileNameExtensionFilter("텍스트 파일(*.txt)", "txt")); 
	}
	public File createFileOpenDialog() {
		int option = jFileChooser.showOpenDialog(parentComponent);
		File file = null;
		if (option == JFileChooser.APPROVE_OPTION)
			file = jFileChooser.getSelectedFile();
		else if (option == JFileChooser.CANCEL_OPTION) 
			createMessageDialog(EMessageType.eOpenEnd);
		return file;
	}
	public File createFileSaveDialog() { 
		int option = jFileChooser.showSaveDialog(parentComponent);
		File file = null;
		if (option == JFileChooser.APPROVE_OPTION)
			file = jFileChooser.getSelectedFile();
		else if (option == JFileChooser.CANCEL_OPTION) 
			createMessageDialog(EMessageType.eSaveEnd);
		return file;
	}
	public void createMessageDialog(EMessageType messageType) { 
		JOptionPane.showMessageDialog(
				parentComponent, messageType.getMessage(), 
				messageType.getTitle(), messageType.getMessageType());
	}
}
