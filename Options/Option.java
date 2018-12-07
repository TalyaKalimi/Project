package Options;

public abstract class Option {
	
	boolean visible;
	
	public Option(boolean visible) {
		this.visible = visible;
	}
	
	public boolean IsVisible()
	{
		return visible;
	}

}
