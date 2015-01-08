package org.os.git4dummy.ui;

import java.util.Scanner;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.widgets.Composite;

/**
 * Extension du composant StyledText pour y ajouter la coloration des "diffs" des fichiers
 * @author olsanche
 *
 */
public class StyledTextExtended extends StyledText {

	public StyledTextExtended(Composite parent) {
		super(parent, SWT.BORDER
				| SWT.V_SCROLL | SWT.H_SCROLL | SWT.MULTI);
	}
	
	/**
	 * Colorise le texte en vert pour les lignes ajoutées et en rouge pour les lignes supprimées.
	 * @param s
	 */
	public void colorizeText(String s) {
		Scanner scanner = new Scanner(s);
		
		//System.out.println("colorizeText: " + s);
		//System.out.println("------------- ");
		
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			//System.out.println("Line: " + line);
			this.insert(line + "\r\n");
			if(line.charAt(0) == '-') {
				StyleRange styleRange = new StyleRange();
				styleRange.start = 0;
				styleRange.length = line.length();
				styleRange.fontStyle = SWT.BOLD;
				styleRange.foreground = super.getParent().getDisplay().getSystemColor(SWT.COLOR_RED);
				this.setStyleRange(styleRange);
			}
			if(line.charAt(0) == '+') {
				StyleRange styleRange = new StyleRange();
				styleRange.start = 0;
				styleRange.length = line.length();
				styleRange.fontStyle = SWT.BOLD;
				styleRange.foreground = super.getParent().getDisplay().getSystemColor(SWT.COLOR_GREEN);
				this.setStyleRange(styleRange);
			}
		}
		scanner.close();
	}

}
