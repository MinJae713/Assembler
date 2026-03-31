package msConstants;

import java.awt.Dimension;

public class ViewConsts {
	public class Code {
		public static final Dimension viewDim = new Dimension(900, 600);
		public static final String viewTitle = "Code View";
		public static final String buttonName = "emulator";
		public static final String actionCommand = "code";
		public static final String parserActionCommand = "parser";
		public static final String assemblyLabel = "assembly code";
		public static final boolean assemblyEditable = true;
		public static final Dimension assemblyDim = new Dimension(280, 0);
		public static final String mILabel = "machine instruction";
		public static final boolean mIEditable = false;
		public static final Dimension mIDim = new Dimension(300, 0);
		public static final String assemblyToMI = "기계어 변환";
		public static final String noAssemblyCodeMessage = "어셈블리 코드를 입력해주세요";
		public static final String noAssemblyCodeTitle = "코드 미입력 에러";
		public static final String noAssembledMessage = "먼저 어셈블을 해주세요";
		public static final String noAssembledTitle = "어셈블 미실시 에러";
		public static final String segmentMissmatchTitle = "세그먼트 에러";
		public static final String segmentMissmatch = "세그먼트 종류 및 순서가 올바르지 않습니다";
		public static final String[] segmentOrdinal = 
			{"program", "header", "data", "code", "end"};
	}
	
	public class Table {
		public static final int labelSize = 15;
		public static final String headerLabel = "Header Result";
		public static final String dataLabel = "Data Result";
		public static final String[] dataAllocColumns = {"name", "offset", "size"};
		public static final String[] symbolColumns = {"name", "type", "size", "offset"};
		public static final String[] instColumns = {"offset", "type", "operand1", "operand2"};
		public static final String[] mIColumns = {"type", "operand1", "operand2", "hex", "binary"};
		public static final Dimension dim = new Dimension(280, 180);
		public static final Dimension parserDim = new Dimension(410, 170);
		public static final int oneRowCount = 16;
	}

	public class Parser {
		public static final Dimension viewDim = new Dimension(900, 600);
		public static final String viewTitle = "Parser View";
		public static final String buttonName = "Code Generate";
		public static final String actionCommand = "genCode";
		public static final String nextButtonTitle = "Next Token";
		public class Token {
			public static final Dimension panelDim = new Dimension(130, 0);
			public static final Dimension listPanelDim = new Dimension(110, 235);
			public static final String listLabelName = "Tokens";
			public static final int labelSize = 15;
			public static final int listScrollValue = 20;
			public static final int listMaxScrollValue = 200;
		}
		public class CurTokenHeader {
			public static final int tokenSize = 18;
			public static final Dimension tokenDim = new Dimension(300, 0);
			public static final Dimension contentDim = new Dimension(280, 240);
			public static final String labelText = "현재 토큰 : ";
			public static final Dimension dim = new Dimension(280, 250);
			public static final String labelName = "Header Segment";
			public static final int headerSize = 25;
			public static final int labelSize = 28;
			public static final String stackLabelName = " Stack Size";
			public static final String heapLabelName = " Heap Size";
		}
		public class NextTokenDS {
			public static final Dimension contentDim = new Dimension(440, 250);
			public static final String tableName = "Data Segment";
			public static final String nextTokenLabelName = " 다음 토큰 : ";
			public static final int labelSize = 18;
			public static final String yes = "있음";
			public static final String no = "없음";
		}
		public class South {
			public static final String symbolTableTitle = "Symbol Table";
			public static final String csTableTitle = "Code Segment";
			public static final int symbolMaxCount = 162;
		}
	}

	public class Generator {
		public static final Dimension viewDim = new Dimension(900, 600);
		public static final String viewTitle = "Code Generator View";
		public static final String buttonName = "finish assemble";
		public static final String actionCommand = "genToCode";
		public static final String nextButtonTitle = "Next Instruction";
		public static final int maxRowCount = 144;
		public class CurNextCommon {
			public static final int labelSize = 20;
		}
		public class CurInst {
			public static final String labelName = " 현재 명령어 : ";
		}
		public class NextInst {
			public static final String labelName = " 다음 명령어 : ";
			public static final String no = "없음";
		}
		public class InstTable {
			public static final String labelName = "Assembly Instructions";
			public static final Dimension tableDim = new Dimension(410, 190);
		}
		public class SymbolTable {
			public static final String labelName = "Symbol Table";
			public static final Dimension tableDim = new Dimension(410, 190);
		}
		public class MITable {
			public static final String labelName = "Machine Instructions";
			public static final Dimension tableDim = new Dimension(410, 420);
		}
	}
	
	public class Emul {
		public static final Dimension viewDim = new Dimension(1400, 800);
		public static final String viewTitle = "Computer View";
		public static final String buttonName = "code";
		public static final String finishActionCommand = "com";
		public static final String nextCommandBtn = "다음 명령어";
		public static final String nextProcessBtn = "다음";
		public static final String nextCommand = "nextCommand";
		public static final String nextProcess = "nextProcess";
		public static final String finishBtn = "명령어 처리 종료";
		
		public class CPU {
			public static final String labelName = "CPU";
			public static final int labelSize = 20;
		}
		
		public class Register {
			public static final int initValue = 0;
			public class General {
				public static final String labelName = "범용 레지스터";
				public static final int labelSize = 15;
				public static final int[] regisGap = {30, 10};
				public static final int regiFontSize = 18;
				public static final Dimension regisDim = new Dimension(100, 40);
			}
			public class Base {
				public static final String labelName = "베이스 레지스터";
				public static final int labelSize = 15;
				public static final int regiFontSize = 18;
				public static final Dimension regisDim = new Dimension(100, 35);
				public static final Dimension sideDim = new Dimension(180, 100);
			}
			public class SP {
				public static final Dimension regiDim = new Dimension(150, 45);
				public static final int regiFontSize = 25;
				public static final Dimension panelDim = new Dimension(180, 55);
			}
			
			public class AM {
				public static final Dimension regisDim = new Dimension(90, 45);
				public static final int regiFontSize = 30;
			}
			
			public class CUALU {
				public static final Dimension dim = new Dimension(260, 210);
				public static final int labelSize = 30;
				public static final boolean editable = false;
			}
			
			public class Status {
				public static final String labelName = "상태 레지스터";
				public static final int labelSize = 15;
				public static final int regiFontSize = 20;
				public static final Dimension regisDim = new Dimension(50, 25);
				public static final Dimension sideDim = new Dimension(110, 100);
			}
		}
		public class MemorySegment {
			public static final String labelName = "Memory";
			public static final int labelSize = 20;
			public static final int segmentLabelSize = 15;
			public static final int maxRowCount = 208;
			public class CS {
				public static final String labelName = "Code Segment";
				public static final String[] columns = {"Address", "OPCODE", "Operand1", "Operand2"};
				public static final Dimension dim = new Dimension(300, 260);
			}
			public class DS {
				public static final String labelName = "Data Segment";
				public static final String[] columns = {"Address", "Name", "Value", "Size"};
				public static final Dimension dim = new Dimension(360, 260);
			}
			public class SS {
				public static final String labelName = "Stack Segment";
				public static final String[] columns = {"Address", "Function Name", "Record Type", "Size"};
				public static final Dimension dim = new Dimension(360, 260);
			}
			public class HS {
				public static final String labelName = "Heap Segment";
				public static final String[] columns = {"Address", "Type Name", "Size"};
				public static final Dimension dim = new Dimension(280, 260);
			}
		}
	}
}
