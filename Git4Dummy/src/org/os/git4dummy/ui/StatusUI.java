package org.os.git4dummy.ui;

import org.eclipse.jgit.api.Status;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.RowData;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;

import swing2swt.layout.BorderLayout;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.os.git4dummy.Setup;
import org.os.git4dummy.model.Referentiel;

public class StatusUI extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	private Image green = SWTResourceManager.getImage(Status.class,
			"/res/glossy-green-icon-button-32.png");
	private Image red = SWTResourceManager.getImage(Status.class,
			"/res/glossy-red-icon-button-32.png");
	private Button btnCommitOPL;
	private Button btnCommitOPLF;
	private Button btnDeployOPL;
	private Button btnDeployOPLF;
	private Referentiel refOPL = Setup.getOpl();
	private Referentiel refOPLF = Setup.getOplf();
	
	public StatusUI(final Composite parent, int style) {
		super(parent, style);
		setLayout(new BorderLayout(0, 0));

		Label lblSatut = new Label(this, SWT.NONE);
		lblSatut.setLayoutData(BorderLayout.NORTH);
		lblSatut.setFont(SWTResourceManager.getFont("Segoe UI", 11, SWT.BOLD
				| SWT.ITALIC));
		lblSatut.setText("Satut des r\u00E9f\u00E9rentiels");

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayout(new FillLayout(SWT.HORIZONTAL));

		Composite composite_opl = new Composite(composite, SWT.BORDER);
		composite_opl.setLayout(new RowLayout(SWT.HORIZONTAL));

		Composite composite_1 = new Composite(composite_opl, SWT.NONE);

		Label lblNewLabel = new Label(composite_1, SWT.NONE);
		lblNewLabel.setFont(SWTResourceManager
				.getFont("Segoe UI", 10, SWT.BOLD));
		lblNewLabel.setLocation(0, 0);
		lblNewLabel.setSize(342, 33);
		lblNewLabel.setText("OPL");

		Composite composite_2 = new Composite(composite_opl, SWT.NONE);
		composite_2.setLayoutData(new RowData(340, SWT.DEFAULT));
		RowLayout rl_composite_2 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_2.center = true;
		composite_2.setLayout(rl_composite_2);

		Canvas canvasCommitWaitOPL = new Canvas(composite_2, SWT.NONE);
		canvasCommitWaitOPL.addPaintListener(new StatusWaitingListener(this.refOPL));
		canvasCommitWaitOPL.setLayoutData(new RowData(32, 32));

		Label lblCommitWaitingOPL = new Label(composite_2, SWT.NONE);
		lblCommitWaitingOPL.setText(Integer.toString(this.refOPL.getFileWaiting().size()));
		lblCommitWaitingOPL.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblCommitWaitingOPL.setAlignment(SWT.CENTER);

		Label lblCommitsEnAttente = new Label(composite_2, SWT.NONE);
		lblCommitsEnAttente.setText("Fichiers en attente de commit");

		Composite composite_3 = new Composite(composite_opl, SWT.NONE);
		RowLayout rl_composite_3 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_3.center = true;
		composite_3.setLayout(rl_composite_3);

		Canvas canvasPushWaitingOPL = new Canvas(composite_3, SWT.NONE);
		canvasPushWaitingOPL.setLayoutData(new RowData(32, 32));
		canvasPushWaitingOPL.addPaintListener(new StatusPushListener(this.refOPL));

		Label lblPushWaitingOPL = new Label(composite_3, SWT.NONE);
		lblPushWaitingOPL.setText(Integer.toString(this.refOPL.getNumberPushWaiting()));
		lblPushWaitingOPL.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		lblPushWaitingOPL.setAlignment(SWT.CENTER);

		Label label_1 = new Label(composite_3, SWT.NONE);
		label_1.setText("Commits en attente de d\u00E9ploiement");

		Composite composite_4 = new Composite(composite_opl, SWT.NONE);
		composite_4.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		// Bouton commit OPL
		btnCommitOPL = new Button(composite_4, SWT.CENTER);
		btnCommitOPL.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CommitUI commitUI = new CommitUI(parent, SWT.NONE, Setup.getOpl());
				commitUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = commitUI;
				parent.layout(true);
			}
		});
		btnCommitOPL.setText("Voir les fichiers");
		
		// Bouton Push OPL
		btnDeployOPL = new Button(composite_4, SWT.NONE);
		btnDeployOPL.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PushUI pushUI = new PushUI(parent, SWT.NONE, Setup.getOpl());
				pushUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = pushUI;
				parent.layout(true);
			}
		});
		btnDeployOPL.setText("Voir les commits");

		canvasCommitWaitOPL.setLayoutData(new RowData(32, 32));

		Composite composite_oplf = new Composite(composite, SWT.BORDER);
		composite_oplf.setLayout(new RowLayout(SWT.HORIZONTAL));

		Composite composite_6 = new Composite(composite_oplf, SWT.NONE);

		Label lblOplf = new Label(composite_6, SWT.NONE);
		lblOplf.setText("OPLF");
		lblOplf.setFont(SWTResourceManager.getFont("Segoe UI", 10, SWT.BOLD));
		lblOplf.setBounds(0, 0, 342, 33);

		Composite composite_7 = new Composite(composite_oplf, SWT.NONE);
		RowLayout rl_composite_7 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_7.center = true;
		composite_7.setLayout(rl_composite_7);

		Canvas canvasCommitWaitOPLF = new Canvas(composite_7, SWT.NONE);
		canvasCommitWaitOPLF.addPaintListener(new StatusWaitingListener(this.refOPLF));
		canvasCommitWaitOPLF.setLayoutData(new RowData(32, 32));

		Label lblCommitWaitingOPLF = new Label(composite_7, SWT.NONE);
		lblCommitWaitingOPLF.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		//lblCommitWaitingOPLF.setText(Integer.toString(MainUI.getOpl().getFileWaiting().size()));
		lblCommitWaitingOPLF.setAlignment(SWT.CENTER);

		Label label_2 = new Label(composite_7, SWT.NONE);
		label_2.setText("Fichiers en attente de commit");

		Composite composite_8 = new Composite(composite_oplf, SWT.NONE);
		RowLayout rl_composite_8 = new RowLayout(SWT.HORIZONTAL);
		rl_composite_8.center = true;
		composite_8.setLayout(rl_composite_8);

		Canvas canvasPushWaitOPLF = new Canvas(composite_8, SWT.NONE);
		canvasPushWaitOPLF.addPaintListener(new StatusPushListener(this.refOPLF));
		canvasPushWaitOPLF.setLayoutData(new RowData(32, 32));

		Label lblPushWaitingOPLF = new Label(composite_8, SWT.NONE);
		lblPushWaitingOPLF.setFont(SWTResourceManager.getFont("Segoe UI", 9,
				SWT.BOLD));
		//lblPushWaitingOPLF.setText(Integer.toString(MainUI.getOpl().getFileWaiting().size()));
		lblPushWaitingOPLF.setAlignment(SWT.CENTER);

		Label label_4 = new Label(composite_8, SWT.NONE);
		label_4.setText("Commits en attente de d\u00E9ploiement");
		
		Composite composite_5 = new Composite(composite_oplf, SWT.NONE);
		composite_5.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		btnCommitOPLF = new Button(composite_5, SWT.CENTER);
		btnCommitOPLF.setText("Voir les fichiers");
		btnCommitOPLF.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CommitUI commitUI = new CommitUI(parent, SWT.NONE, Setup.getOplf());
				commitUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = commitUI;
				parent.layout(true);
			}
		});
		
		btnDeployOPLF = new Button(composite_5, SWT.NONE);
		btnDeployOPL.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PushUI pushUI = new PushUI(parent, SWT.NONE, Setup.getOplf());
				pushUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = pushUI;
				parent.layout(true);
			}
		});
		btnDeployOPLF.setText("Voir les commits");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
	
	/**
	 *  
	 * @author olsanche
	 *
	 */
	class StatusWaitingListener implements PaintListener {
		private Referentiel ref;
		public StatusWaitingListener(Referentiel ref) {
			this.ref = ref;
		}
		@Override
		public void paintControl(PaintEvent e) {
			if(this.ref.getNbFileWaiting() == 0) {
				e.gc.drawImage(green, 0, 0, green.getBounds().width, green.getBounds().height, 0, 0, e.width, e.height);
			}
			else {
				e.gc.drawImage(red, 0, 0, green.getBounds().width, red.getBounds().height, 0, 0, e.width, e.height);
			}
		}
	}
	
	/**
	 * 
	 * @author olsanche
	 *
	 */
	class StatusPushListener implements PaintListener {
		private Referentiel ref;
		public StatusPushListener(Referentiel ref) {
			this.ref = ref;
		}
		@Override
		public void paintControl(PaintEvent e) {
			if(this.ref.getNumberPushWaiting() == 0) {
				e.gc.drawImage(green, 0, 0, green.getBounds().width, green.getBounds().height, 0, 0, e.width, e.height);
			}
			else {
				e.gc.drawImage(red, 0, 0, green.getBounds().width, red.getBounds().height, 0, 0, e.width, e.height);
			}
		}
	}

}
