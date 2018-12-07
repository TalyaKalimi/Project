package Grammar;

public class Pair {

	  private String string;
	  private int freq;
	
	  public Pair(String string, int freq) {
		this.string = string;
		this.freq = freq;
	}

	public int getFreq() {
		return freq;
	}
	public String getString() {
		return string;
	}
}
