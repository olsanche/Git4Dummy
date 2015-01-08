package org.os.git4dummy.ui;

import java.io.IOException;

import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.SWT;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.os.git4dummy.Setup;
import org.os.git4dummy.model.Referentiel;
import org.eclipse.swt.graphics.Point;

public class MainUI {

	protected Shell shell;
	protected Composite composite;
	protected StackLayout sl;

	

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			MainUI window = new MainUI();
			
			window.open();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setMinimumSize(new Point(800, 600));
		shell.setSize(800, 600);
		shell.setText("SWT Application");
		shell.setLayout(new BorderLayout(0, 0));
		
		
		Menu menu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(menu);
		
		MenuItem mntmCommit_1 = new MenuItem(menu, SWT.CASCADE);
		mntmCommit_1.setText("Commit");
		
		Menu menu_1 = new Menu(mntmCommit_1);
		mntmCommit_1.setMenu(menu_1);
		
		MenuItem mntmFaire = new MenuItem(menu_1, SWT.NONE);
		mntmFaire.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CommitUI commitUI = new CommitUI(composite, SWT.NONE, Setup.getOpl());
				commitUI.setLayoutData(BorderLayout.CENTER);
				sl.topControl = commitUI;
				composite.layout(true);
			}
		});
		mntmFaire.setText("\u00C0 faire");
		
		MenuItem mntmSend = new MenuItem(menu_1, SWT.NONE);
		mntmSend.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PushUI pushUI;
		
				pushUI = new PushUI(composite, SWT.NONE, Setup.getOpl());
			
				pushUI.setLayoutData(BorderLayout.CENTER);
				sl.topControl = pushUI;
				composite.layout(true);
			}
		});
		mntmSend.setText("\u00C0 valider");
		
		MenuItem mntmCommit = new MenuItem(menu, SWT.NONE);
		mntmCommit.setText("Commit");
		
		MenuItem mntmNewItem = new MenuItem(menu, SWT.NONE);
		mntmNewItem.setText("New Item");

		
		sl = new StackLayout();
		sl.marginWidth = 10;
		sl.marginHeight = 10;
		composite = new Composite(shell, SWT.NONE);
		composite.setLayoutData(BorderLayout.CENTER);
		composite.setLayout(sl);
		
		StatusUI status = new StatusUI(composite, SWT.NONE);
		sl.topControl = status;
		composite.layout(true);
	}

}