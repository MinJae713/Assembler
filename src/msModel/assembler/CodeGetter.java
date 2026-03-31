package msModel.assembler;
import dto.assembler.GenerateInfo;

public interface CodeGetter {
	public GenerateInfo getCode(int instCount, GenerateInfo generateInfo);
}
