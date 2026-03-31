package msView.emulPanel;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import dto.emulator.EmulInfo;
import msConstants.ModelConsts;
import msConstants.ViewConsts;
import msView.tableTemplate.TablePanelTemplate;
import msView.tableTemplate.strategy.initTable.segments.CSInitializer;
import msView.tableTemplate.strategy.initTable.segments.DSInitializer;
import msView.tableTemplate.strategy.initTable.segments.HSInitializer;
import msView.tableTemplate.strategy.initTable.segments.SSInitializer;
import msView.tableTemplate.strategy.updateRow.DSUpdater;

public class MemoryPanel extends ComponentPanel {
	private static final long serialVersionUID = 1L;
	private SegmentsPanel segmentsPanel;
	public MemoryPanel() {
		super(ViewConsts.Emul.MemorySegment.labelName, 
				ViewConsts.Emul.MemorySegment.labelSize);
		segmentsPanel = new SegmentsPanel();
		add(segmentsPanel, BorderLayout.CENTER);
	}
	@Override
	public void updateView(EmulInfo emulInfo) {
		segmentsPanel.updateView(emulInfo);
	}
	public void initialize(EmulInfo emulInfo) {
		segmentsPanel.initialize(emulInfo);
	}
	private class SegmentsPanel extends JPanel {
		private static final long serialVersionUID = 1L;
		private TablePanelTemplate csPanel;
		private TablePanelTemplate dsPanel;
		private TablePanelTemplate ssPanel;
		private TablePanelTemplate hsPanel;
		public SegmentsPanel() {
			csPanel = new TablePanelTemplate(
					ViewConsts.Emul.MemorySegment.CS.labelName, 
					ViewConsts.Emul.MemorySegment.CS.columns, 
					ViewConsts.Emul.MemorySegment.segmentLabelSize, 
					ViewConsts.Emul.MemorySegment.CS.dim);
			dsPanel = new TablePanelTemplate(
					ViewConsts.Emul.MemorySegment.DS.labelName, 
					ViewConsts.Emul.MemorySegment.DS.columns,
					ViewConsts.Emul.MemorySegment.segmentLabelSize, 
					ViewConsts.Emul.MemorySegment.DS.dim);
			ssPanel = new TablePanelTemplate(
					ViewConsts.Emul.MemorySegment.SS.labelName, 
					ViewConsts.Emul.MemorySegment.SS.columns,
					ViewConsts.Emul.MemorySegment.segmentLabelSize, 
					ViewConsts.Emul.MemorySegment.SS.dim);
			hsPanel = new TablePanelTemplate(
					ViewConsts.Emul.MemorySegment.HS.labelName, 
					ViewConsts.Emul.MemorySegment.HS.columns,
					ViewConsts.Emul.MemorySegment.segmentLabelSize, 
					ViewConsts.Emul.MemorySegment.HS.dim);
			
			add(csPanel);
			add(dsPanel);
			add(ssPanel);
			add(hsPanel);
		}
		public void initialize(EmulInfo emulInfo) {
			csPanel.initialize(new CSInitializer(
					csPanel, emulInfo.getCs(), 
					emulInfo.getCsAlloc()));
			dsPanel.initialize(new DSInitializer(
					dsPanel, emulInfo.getDs(), 
					emulInfo.getDsAlloc()));
			ssPanel.initialize(new SSInitializer(
					ssPanel, emulInfo.getSs(),
					emulInfo.getSsAlloc()));
			hsPanel.initialize(new HSInitializer(
					hsPanel, emulInfo.getHs(), 
					emulInfo.getHsAlloc()));
		}
		public void updateView(EmulInfo emulInfo) {
			if (emulInfo.getCsIdx() == ModelConsts.clearSelection)
				csPanel.clearSelection();
			else {
				csPanel.updateView(emulInfo.getCsIdx(), emulInfo.getCsIdx());
				csPanel.scrollOneRow(emulInfo.getCsIdx(), 
						ViewConsts.Emul.MemorySegment.maxRowCount);
			}
			if (emulInfo.getUpdateDS() != null &&
					emulInfo.getDsIdx() != ModelConsts.clearSelection) {
				dsPanel.updateRow(new DSUpdater(dsPanel, 
						emulInfo.getDsIdx(), emulInfo.getUpdateDS()));
				dsPanel.updateView(emulInfo.getDsIdx(), emulInfo.getDsIdx());
			} else if (emulInfo.getDsIdx() == ModelConsts.clearSelection) 
				dsPanel.clearSelection();
			else {
				dsPanel.updateView(emulInfo.getDsIdx(), emulInfo.getDsIdx());
				dsPanel.scrollOneRow(emulInfo.getDsIdx(), 
						ViewConsts.Emul.MemorySegment.maxRowCount);
			}
		}
	}
}
