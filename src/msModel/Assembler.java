package msModel;

import java.util.List;
import java.util.Scanner;
import java.util.Vector;

import dto.assembler.GenerateInfo;
import dto.assembler.ParsingInfo;
import dto.common.mI.MInstruction;
import dto.emulator.ProcessControlBlock;
import msConstants.ModelConsts;
import msModel.assembler.codeGenerator.CodeGenerator;
import msModel.assembler.lexicalAnalyzer.LexicalAnalyzer;
import msModel.assembler.node.entity.DataAlloc;
import msModel.assembler.node.segments.Instruction;
import msModel.assembler.parser.Parser;
import msModel.assembler.symbolTable.Symbol;
import msModel.assembler.symbolTable.SymbolTable;

public class Assembler {
	private Vector<String> tokenVector;
	private int tokenCount;
	private int instCount;
	
	private Vector<DataAlloc> headerAllocs;
	private boolean firstGenerate;
	
	private SymbolTable symbolTable;
	private LexicalAnalyzer lexicalAnalyzer;
	private Parser parser;
	private CodeGenerator codeGenerator;
	
	public Assembler() {
		this.symbolTable = new SymbolTable();
		this.lexicalAnalyzer = new LexicalAnalyzer(symbolTable);
		this.parser = new Parser(symbolTable);
		this.codeGenerator = new CodeGenerator();
		
		this.parser.associate(lexicalAnalyzer);
	}
	public Vector<String> initialize() {
		tokenCount = 0;
		instCount = 0;
		headerAllocs = new Vector<DataAlloc>();
		firstGenerate = true;
		
		this.symbolTable.initialize();
		this.lexicalAnalyzer.initialize();
		this.parser.initialize();
		this.codeGenerator.initialize();
		return tokenVector;
	}
	public void finish() {
		this.symbolTable.finish();
		this.lexicalAnalyzer.finish();
		this.parser.finish();
		this.codeGenerator.finish();
	}
	public void setAssemblyCode(String assemblyCode) {
		tokenVector = getTokenVector(assemblyCode);
	}
	public Vector<String> getTokenVector(String assemblyCode) {
		Vector<String> tokenVector = new Vector<String>();
		Scanner scanner = new Scanner(assemblyCode);
		while (scanner.hasNext())
			tokenVector.add(scanner.next());
		scanner.close();
		return tokenVector;
	}
	public ParsingInfo parse() {
		ParsingInfo parsingInfo = null;
		try {
			parsingInfo = parser.parse(tokenVector, tokenCount);
			tokenCount++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parsingInfo;
	}
	public GenerateInfo generate() {
		GenerateInfo generateInfo = new GenerateInfo();
		if (firstGenerate) {
			this.codeGenerator.associate(parser.getProgram());
			firstGenerate = false;
		}
		generateInfo = codeGenerator.getCode(instCount, generateInfo);
		
		instCount++;
		return generateInfo;
	}
	public void fixHeaderAllocs() {
		int offset = 0;
		int stackSize = parser.getProgram().getHeaderSegment().getSizeStack();
		int heapSize = parser.getProgram().getHeaderSegment().getSizeHeap();
		int codeSize = parser.getProgram().getCodeSegment().getMInstructions().size()*4;
		headerAllocs.add(new DataAlloc(ModelConsts.csName, codeSize, offset));
		offset += codeSize;
		headerAllocs.add(new DataAlloc(ModelConsts.dsName, getDSAllocSize(), offset));
		offset += getDSAllocSize();
		headerAllocs.add(new DataAlloc(ModelConsts.ssName, stackSize, offset));
		offset += stackSize;
		offset += heapSize;
		headerAllocs.add(new DataAlloc(ModelConsts.hsName, heapSize, offset));
	}
	private int getDSAllocSize() {
		int size = 0;
		List<DataAlloc> dsAllocs = parser.getProgram().getDataSegment().getDataSegment();
		for (DataAlloc alloc : dsAllocs) size += alloc.getSize();
		return size;
	}
	public ProcessControlBlock getPCB() {
		return new ProcessControlBlock(
				headerAllocs, 
				parser.getProgram().getDataSegment().getDataSegment(), 
				parser.getProgram().getCodeSegment().getMInstructions());
	}
	public List<DataAlloc> getHeaderAllocs() {
		return headerAllocs;
	}
	public List<DataAlloc> getDataSegment() {
		return parser.getProgram().getDataSegment().getDataSegment();
	}
	public Vector<MInstruction> getMInstructions() {
		return parser.getProgram().getCodeSegment().getMInstructions();
	}
	public Vector<Symbol> getSymbolTable() {
		return symbolTable.getSymbolTable();
	}
	public Vector<Instruction> getInstructions() {
		return parser.getProgram().getCodeSegment().getInstructions();
	}
	public void setSymbolTable(Vector<Symbol> symbolTable) {
		this.symbolTable.setSymbolTable(symbolTable);
	}
}
