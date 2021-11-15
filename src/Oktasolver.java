class Oktasolver {
	public static void main (String args []) {
		Oktadoku.Style style = Oktadoku.Style.withDiagonals;
		if (args.length > 0 && args[0].equals("-x"))
		    style = Oktadoku.Style.normal;
		Oktadoku okta = new Oktadoku(style);

		okta.getUserInput();
		okta.printBoard();
		if (okta.checkIfValidOktadoku())
			okta.solveOktadoku();
		else
			System.out.println("Please provide a valid Oktadoku");
	}
}
	
