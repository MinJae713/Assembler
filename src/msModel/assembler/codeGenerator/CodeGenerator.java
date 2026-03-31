package msModel.assembler.codeGenerator;
import dto.assembler.GenerateInfo;
import msModel.assembler.CodeGetter;
import msModel.assembler.node.segments.Program;

public class CodeGenerator implements CodeGetter{

	private Program program;
	public void associate(Program program) {
		this.program = program;
	}
	@Override
	public GenerateInfo getCode(int instCount, 
			GenerateInfo generateInfo) {
		return program.getCode(instCount, generateInfo);
	}
	public void initialize() {}
	public void finish() {}
}
