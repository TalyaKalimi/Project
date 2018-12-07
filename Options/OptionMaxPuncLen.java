package Options;

public class OptionMaxPuncLen extends Option {

	int maxLen;
	
	public OptionMaxPuncLen(boolean visible, int maxLen) {
		super(visible);
		this.maxLen = maxLen;
	}
	
	public OptionMaxPuncLen(boolean visible) {
		super(visible);
	}
	

}
