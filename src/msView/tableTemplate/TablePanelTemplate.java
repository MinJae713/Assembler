package msView.tableTemplate;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import msConstants.ModelConsts;
import msConstants.ViewConsts;
import msView.msLabel.LabelPanel;
import msView.tableTemplate.strategy.addRows.MIAdder;
import msView.tableTemplate.strategy.addRows.RowAdder;
import msView.tableTemplate.strategy.initTable.TableInitializer;
import msView.tableTemplate.strategy.removeRow.RowRemover;
import msView.tableTemplate.strategy.setRow.RowSetter;
import msView.tableTemplate.strategy.setTable.TableSetter;
import msView.tableTemplate.strategy.updateRow.RowUpdater;

public class TablePanelTemplate extends JPanel{
	private static final long serialVersionUID = 1L;
	protected DefaultTableModel tableModel;
	protected LabelPanelAlignment labelPanel;
	protected TablePanel tablePanel;
	private JScrollBar bar;
	private int barCount;
	public TablePanelTemplate(String labelName, 
			String[] columns, int labelSize, Dimension dimension) {
		setBorder(BorderFactory.createLineBorder(Color.black));
		setLayout(new BorderLayout());
		labelPanel = new LabelPanelAlignment(labelName, labelSize);
		add(labelPanel, BorderLayout.NORTH);
		tablePanel = new TablePanel(columns, dimension);
		add(tablePanel, BorderLayout.CENTER);
	}
	public DefaultTableModel getTableModel() {
		return tableModel;
	}
	public void updateView(int fromIdx, int toIdx) {
		// 테이블 행 선택(from부터 to까지)
		tablePanel.updateView(fromIdx, toIdx);
	}
	public void removeSelection(int fromIdx, int toIdx) {
		tablePanel.removeSelection(fromIdx, toIdx);
	}
	public int getAlignSize() {
		return labelPanel.getAlignSize();
	}
	public void setAlignSize(int alignSize, boolean isInit) {
		// 테이블의 할당 크기 값 지정(초기화 여부에 따라 붉은색 or 검은색 표시)
		// ㄴ true - 모든 initializer, initialize()
		// ㄴ false - 모든 adder, 모든 setter
		labelPanel.setAlignSize(alignSize, isInit);
	}
	public void truncateTable() {
		// 테이블 행 전체 삭제
		int count = tableModel.getRowCount();
		for (int i = 0; i < count; i++) 
			tableModel.removeRow(0);
	}
	public void initialize() {
		// 테이블 초기화(할당 크기-0 및 행 전체 삭제)
		labelPanel.setAlignSize(0, true);
		truncateTable();
	}
	public void updateRow(RowUpdater rowUpdater) {
		rowUpdater.updateRow();
	}
	public void initialize(TableInitializer initializer) {
		// 위랑 같은데 초기화 후 테이블에 행 새로 지정 시 사용
		// ㄴ initializer에서 위 initialize() 호출함
		// 미사용 - DataAlloc, MI
		// 사용 - Inst, Symbol, CS, DS, HS, SS
		// 초기화해도 값을 새로 넣는 작업이 없는 경우는 안씀
		initializer.initialize();
		scrollMinimum();
		barCount = 0;
	}
	public void setTable(TableSetter setter) {
		// 테이블에 행 새로 지정
		setter.setTable();
		clearSelection(); 
		// setter에서 RowAdd시 행 추가 이후 행 선택 취소
		// ㄴ set에서 alignSize false 했다가 
		//    clearSelection에서 다시 true로 바꿈
	}
	public void setRow(RowSetter setter, int maxCount) {
		setter.setRow();
		int idx = setter.getIdx();
		updateView(idx, idx);
		scrollOneRow(idx, maxCount);
	}
	public void addRow(RowAdder adder) {
		// 테이블에 행 추가, 추가 이후 추가된 행 선택
		// ㄴ MI는 한번에 행이 둘 이상 추가되서 추가 행을 범위로 지정
		adder.addRow();
		if (adder instanceof MIAdder) {
			MIAdder miAdder = (MIAdder)adder;
			updateView(tableModel.getRowCount()-
					miAdder.getRowCount(), 
					tableModel.getRowCount()-1);
		} else updateView(tableModel.getRowCount()-1, 
					tableModel.getRowCount()-1);
		scrollMaximum();
	}
	public void removeRow(RowRemover remover) {
		remover.removeRow();
	}
	public void scrollMinimum() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bar.setValue(0);
			}
		});
	}
	public void scrollMaximum() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bar.setValue(bar.getMaximum());
			}
		});
	}
	public void scrollOneRow(int rowIdx, int maxCount) {
		barCount = rowIdx * ViewConsts.Table.oneRowCount;
		if (barCount <= maxCount) return;
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				bar.setValue(barCount-maxCount);
			}
		});
	}
	public void clearSelection() {
		// 행 선택 및 alignSize 색상 초기화
		// public인 이유? - ParserView의 North, South에서 update할 때
		// 테이블 선택 및 alignSize를 초기화해야 되기 때문임
		tablePanel.clearSelection();
		labelPanel.initialize();
	}
	private class LabelPanelAlignment extends LabelPanel {
		private static final long serialVersionUID = 1L;
		private int alignSize;
		private JLabel sizeLabel;
		public LabelPanelAlignment(String labelName, int labelSize) {
			super(labelName, labelSize);
			alignSize = 0;
			JLabel quoteStart = new JLabel(" [할당 크기 : ");
			Font qsFont = quoteStart.getFont();
			quoteStart.setFont(new Font(qsFont.getName(), qsFont.getStyle(), labelSize));
			add(quoteStart);
			sizeLabel = new JLabel(alignSize+"");
			Font sFont = sizeLabel.getFont();
			sizeLabel.setFont(new Font(sFont.getName(), sFont.getStyle(), labelSize));
			add(sizeLabel);
			JLabel quoteEnd = new JLabel("]");
			Font qeFont = quoteEnd.getFont();
			quoteEnd.setFont(new Font(qeFont.getName(), qeFont.getStyle(), labelSize));
			add(quoteEnd);
		}
		public int getAlignSize() {
			return alignSize;
		}
		public void setAlignSize(int alignSize, boolean isInit) {
			this.alignSize = alignSize;
			if (isInit) initialize();
			else updateView();
		}
		private void initialize() {
			sizeLabel.setText(alignSize+"");
			sizeLabel.setForeground(Color.black);
		}
		private void updateView() {
			sizeLabel.setText(alignSize+"");
			sizeLabel.setForeground(Color.red);
		}
	}
	private class TablePanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private SegmentTable table;
		public TablePanel(String[] columns, Dimension dimension) {
			setBorder(BorderFactory.createLineBorder(Color.black));
			FlowLayout layout = new FlowLayout();
			layout.setAlignment(FlowLayout.LEADING);
			setLayout(layout);
			table = new SegmentTable(columns);
			JScrollPane scrollPane = new JScrollPane(table);
			scrollPane.setPreferredSize(dimension);
			scrollPane.setHorizontalScrollBarPolicy(
					JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
			scrollPane.setVerticalScrollBarPolicy(
					JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
			bar = scrollPane.getVerticalScrollBar();
			add(scrollPane);
		}
		public void updateView(int fromIdx, int toIdx) {
			table.updateView(fromIdx, toIdx);
		}
		public void removeSelection(int fromIdx, int toIdx) {
			table.removeRowSelectionInterval(fromIdx, toIdx);
		}
		public void clearSelection() {
			table.clearSelection();
		}
	}
	private class SegmentTable extends JTable {
		private static final long serialVersionUID = 1L;
		public SegmentTable(String[] columns) {
			tableModel = new DefaultTableModel(null, columns);
			setModel(tableModel);
		}
		public void updateView(int fromIdx, int toIdx) {
			if (fromIdx == ModelConsts.clearSelection || 
					toIdx == ModelConsts.clearSelection) 
				clearSelection();
			else setRowSelectionInterval(fromIdx, toIdx);
		}
	}
}
