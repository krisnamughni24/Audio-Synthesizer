package musical;

public enum NoteName
{
	C, C_SHARP, D, D_SHARP, E, F, F_SHARP, G, G_SHARP, A, A_SHARP, B;

	private static final String SHARP_IDENTIFIER = "_SHARP";

	public boolean isSharp()
	{
		return this.toString().contains(SHARP_IDENTIFIER);
	}

	public String getName()
	{
		String sharpSign = this.isSharp() ? "#" : "";
		char noteBaseName = this.toString().charAt(0);

		return noteBaseName + sharpSign;
	}
}
