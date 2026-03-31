package msView.parserPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import dto.assembler.ParsingInfo;
import msConstants.ViewConsts;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.addRows.DataAllocAdder;
import status.EParsingStatus;

public class NorthPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private TokenList tokenList;
	private CurTokenLabel curTokenLabel;
	private NextTokenLabel nextTokenLabel;
	private HeaderSizeLabel stackLabel;
	private HeaderSizeLabel heapLabel;
	private TablePanelTemplate dataAllocTablePanel;
	private JScrollBar listBar;
	public NorthPanel() {
		setLayout(new BorderLayout());
		tokenList = new TokenList();
		curTokenLabel = new CurTokenLabel();
		stackLabel = new HeaderSizeLabel();
		heapLabel = new HeaderSizeLabel();
		nextTokenLabel = new NextTokenLabel();
		dataAllocTablePanel = new TablePanelTemplate(
				ViewConsts.Parser.NextTokenDS.tableName,
				ViewConsts.Table.dataAllocColumns,
				ViewConsts.Table.labelSize, 
				ViewConsts.Table.parserDim);
		add(new TokenPanel(), BorderLayout.WEST);
		add(new HeaderDataSegmentPanel(), BorderLayout.CENTER);
	}
	public void updateView(ParsingInfo parsingInfo) {
		dataAllocTablePanel.clearSelection();
		tokenList.updateView(parsingInfo.getTokenIdx());
		curTokenLabel.updateView(parsingInfo.getCurToken());
		stackLabel.initialize(true); 
		heapLabel.initialize(true); 
		Vector<String> tokenVector = tokenList.getTokenVector();
		nextTokenLabel.updateView(parsingInfo.getNextToken(tokenVector));
		if (parsingInfo.getStatus() == EParsingStatus.eStackSize) 
			stackLabel.updateView(parsingInfo.getStackSize());
		else if (parsingInfo.getStatus() == EParsingStatus.eHeapSize)
			heapLabel.updateView(parsingInfo.getHeapSize());
		else if (parsingInfo.getStatus() == EParsingStatus.eData)
			dataAllocTablePanel.addRow(new DataAllocAdder(
					parsingInfo.getDataAlloc(), dataAllocTablePanel));
	}
	public void initialize(Vector<String> tokenVector) {
		tokenList.initialize(tokenVector);
		curTokenLabel.initialize();
		stackLabel.initialize(false); 
		heapLabel.initialize(false); 
		nextTokenLabel.initialize();
		dataAllocTablePanel.initialize();
		listBar.setValue(0);
	}
	private class TokenPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public TokenPanel() {
			setPreferredSize(ViewConsts.Parser.Token.panelDim);
			setLayout(new GridBagLayout());
			
			JPanel panelContent = new JPanel();
			panelContent.setBorder(BorderFactory.createLineBorder(Color.black));
			panelContent.setPreferredSize(ViewConsts.Parser.Token.listPanelDim);
			panelContent.setLayout(new BorderLayout());
			JLabel listLabel = new JLabel(ViewConsts.Parser.Token.listLabelName);
			listLabel.setFont(new Font(listLabel.getFont().getName(), 
					listLabel.getFont().getStyle(), 
					ViewConsts.Parser.Token.labelSize));
			panelContent.add(listLabel, BorderLayout.NORTH);
			
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setViewportView(tokenList);
			listBar = scrollPane.getVerticalScrollBar();
			panelContent.add(scrollPane, BorderLayout.CENTER);
			add(panelContent);
		}
	}
	public class TokenList extends JList<String> {
		private static final long serialVersionUID = 1L;
		private Vector<String> tokenVector;
		private int count;
		public void updateView(int tokenIdx) {
			setSelectedIndex(tokenIdx);
			count += ViewConsts.Parser.Token.listScrollValue;
			if (count <= ViewConsts.Parser.Token.listMaxScrollValue) 
				return;
			SwingUtilities.invokeLater(new Runnable() {
				@Override
				public void run() {
					listBar.setValue(count-
							ViewConsts.Parser.Token.listMaxScrollValue);
				}
			});
		}
		public void initialize(Vector<String> tokenVector) {
			this.tokenVector = tokenVector;
			setListData(tokenVector);
			count = 0;
		}
		public Vector<String> getTokenVector() {
			return tokenVector;
		}
	}
	private class HeaderDataSegmentPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public HeaderDataSegmentPanel() {
			setLayout(new BorderLayout());
			add(new CurTokenHeaderPanel(), BorderLayout.WEST);
			add(new IsNextDataPanel(), BorderLayout.CENTER);
		}
	}
	private class CurTokenHeaderPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public CurTokenHeaderPanel() {
			setPreferredSize(ViewConsts.Parser.CurTokenHeader.tokenDim);
			setLayout(new GridBagLayout());
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			content.setPreferredSize(
					ViewConsts.Parser.CurTokenHeader.contentDim);
			content.add(curTokenLabel, BorderLayout.NORTH);
			content.add(new HSPanel(), BorderLayout.CENTER);
			add(content);
		}
	}
	public class CurTokenLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		public CurTokenLabel() {
			setFont(new Font(getFont().getName(), 
					getFont().getStyle(), 
					ViewConsts.Parser.CurTokenHeader.tokenSize));
			initialize();
		}
		public void initialize() {
			setText(ViewConsts.Parser.CurTokenHeader.labelText);
		}
		public void updateView(String curToken) {
			setText(ViewConsts.Parser.CurTokenHeader.labelText+curToken);
		}
	}
	private class HSPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public HSPanel() {
			setLayout(new BorderLayout());
			setPreferredSize(ViewConsts.Parser.CurTokenHeader.dim);
			setBorder(BorderFactory.createLineBorder(Color.black));
			JLabel title = new JLabel(ViewConsts.Parser.CurTokenHeader.labelName);
			title.setBorder(BorderFactory.createLineBorder(Color.black));
			title.setHorizontalAlignment(JLabel.CENTER);
			title.setFont(new Font(getFont().getName(), Font.BOLD, 
					ViewConsts.Parser.CurTokenHeader.headerSize));
			JPanel segPanel = new JPanel();
			segPanel.setLayout(new GridLayout(2, 1));
			segPanel.setBorder(BorderFactory.createLineBorder(Color.black));
			JPanel stackPanel = new HeaderPanel(
					ViewConsts.Parser.CurTokenHeader.stackLabelName, 
					stackLabel);
			JPanel heapPanel = new HeaderPanel(
					ViewConsts.Parser.CurTokenHeader.heapLabelName, 
					heapLabel);
			segPanel.add(stackPanel);
			segPanel.add(heapPanel);
			add(title, BorderLayout.NORTH);
			add(segPanel, BorderLayout.CENTER);
		}
	}
	private class HeaderPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public HeaderPanel(String segSize, JLabel segLabel) {
			setLayout(new BorderLayout());
			JLabel titleLabel = new JLabel(segSize+" : ");
			titleLabel.setFont(new Font(getFont().getName(), 
					Font.BOLD, 
					ViewConsts.Parser.CurTokenHeader.labelSize));
			titleLabel.setVerticalAlignment(JLabel.CENTER);
			add(titleLabel, BorderLayout.WEST);
			add(segLabel, BorderLayout.CENTER);
		}
	}
	private class HeaderSizeLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		private int size;
		public HeaderSizeLabel() {
			setFont(new Font(getFont().getName(), 
					getFont().getStyle(), 
					ViewConsts.Parser.CurTokenHeader.labelSize));
			setVerticalAlignment(JLabel.CENTER);
			initialize(false); 
		}
		public void initialize(boolean isUpdate) {
			setForeground(Color.BLACK);
			if (!isUpdate) size = 0;
			setText(""+size);
			
		}
		public void updateView(int size) {
			this.size = size;
			setForeground(Color.RED);
			setText(""+size);
		}
	}
	private class IsNextDataPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		public IsNextDataPanel() {
			setLayout(new GridBagLayout());
			JPanel content = new JPanel();
			content.setLayout(new BorderLayout());
			content.setPreferredSize(ViewConsts.Parser.NextTokenDS.contentDim);
			content.add(nextTokenLabel, BorderLayout.NORTH);
			JPanel tablePanel = new JPanel();
			tablePanel.setLayout(new GridBagLayout());
			tablePanel.add(dataAllocTablePanel);
			content.add(tablePanel, BorderLayout.CENTER);
			add(content);
		}
	}
	public class NextTokenLabel extends JLabel {
		private static final long serialVersionUID = 1L;
		public NextTokenLabel() {
			setFont(new Font(getFont().getName(), 
					Font.BOLD, 
					ViewConsts.Parser.NextTokenDS.labelSize));
			initialize();
		}
		public void updateView(String nextToken) {
			setText(ViewConsts.Parser.NextTokenDS.nextTokenLabelName+nextToken);
		}
		public void initialize() {
			setText(ViewConsts.Parser.NextTokenDS.nextTokenLabelName+
					ViewConsts.Parser.NextTokenDS.no);
		}
	}
}
