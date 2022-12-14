package advent.of.code.day7.file;

public class Root extends Dir {

	public Root() {
		super("", null);
	}

	@Override
	public Dir getParent() {
		return this;
	}

}
