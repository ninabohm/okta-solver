class App {
	public static void main (String args []) {
		Oktadoku.Style style = Oktadoku.Style.normal;
		if (args.length > 0 && args[0].equals("-x"))
		    style = Oktadoku.Style.withDiagonals;
		Oktadoku okta = new Oktadoku(style);

		okta.getOctadokuFromUser();
		okta.printResult();
		if (okta.checkIfValidOctadoku())
			okta.solveOctadoku();
		else
			System.out.println("Not a valid Octadoku");
	}
}
	
