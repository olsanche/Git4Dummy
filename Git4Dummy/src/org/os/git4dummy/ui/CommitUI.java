package org.os.git4dummy.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.os.git4dummy.model.FileToCommit;
import org.os.git4dummy.model.Referentiel;
import org.os.git4dummy.model.TypeFile;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

public class CommitUI extends Composite {

	private Table table;
	private Table table_1;
	private Text txtCommit;
	private Label lblRepository;
	private StyledTextExtended diffText;
	private TableViewer viewerCommit;
	private TableViewer viewerWaiting;

	private Referentiel ref;

	private List<FileToCommit> listSelected = new ArrayList<FileToCommit>();
	private List<FileToCommit> listWaiting = new ArrayList<FileToCommit>();

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public CommitUI(final Composite parent, int style, Referentiel ref) {
		super(parent, style);
		this.ref = ref;
		setLayout(new BorderLayout(0, 0));

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.NORTH);
		composite.setLayout(new RowLayout(SWT.HORIZONTAL));

		Label lbl = new Label(composite, SWT.NONE);
		lbl.setAlignment(SWT.RIGHT);
		lbl.setText("Repository:");

		lblRepository = new Label(composite, SWT.NONE);
		lblRepository.setText("New Label");

		Composite composite_7 = new Composite(this, SWT.NONE);
		composite_7.setLayoutData(BorderLayout.CENTER);
		composite_7.setLayout(new FillLayout(SWT.HORIZONTAL));

		SashForm sashForm = new SashForm(composite_7, SWT.BORDER | SWT.SMOOTH);

		Composite composite_1 = new Composite(sashForm, SWT.NONE);
		composite_1.setLayout(new GridLayout(1, false));

		viewerWaiting = new FileToCommitViewer(composite_1, SWT.BORDER
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);
		viewerCommit = new FileToCommitViewer(composite_1, SWT.BORDER
				| SWT.MULTI | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL);


		listWaiting = this.ref.getFileWaiting();
		viewerWaiting.setInput(new FileToCommitModel(listWaiting));

		viewerWaiting
				.addSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {

					}
				});

		table = viewerWaiting.getTable();

		GridData gd_table = new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_table.heightHint = 211;
		table.setLayoutData(gd_table);
		table.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ShowDiff(table.getSelection()[0].getText(1));
			}
		});

		Composite composite_2 = new Composite(composite_1, SWT.NONE);
		composite_2.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		composite_2.setSize(new Point(200, 100));
		composite_2.setLayout(new RowLayout(SWT.HORIZONTAL));
		
		
		// Transefert les Fichiers à commiter
		Button btnNewButton = new Button(composite_2, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) viewerWaiting
						.getSelection();

				Iterator<FileToCommit> it = selection.iterator();
				while (it.hasNext()) {
					FileToCommit f = it.next();
					listSelected.add(f);
					listWaiting.remove(f);
				}

				viewerWaiting.setInput(new FileToCommitModel(listWaiting));
				viewerCommit.setInput(new FileToCommitModel(listSelected));
				viewerWaiting.refresh();
				viewerCommit.refresh();

			}
		});
		btnNewButton.setImage(SWTResourceManager.getImage(CommitUI.class,
				"/res/001_26.png"));
		btnNewButton.setText("A commiter");
		
		// Supprime les Fichiers à commiter
		Button btnNewButton_1 = new Button(composite_2, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				IStructuredSelection selection = (IStructuredSelection) viewerCommit
						.getSelection();

				Iterator<FileToCommit> it = selection.iterator();
				while (it.hasNext()) {
					FileToCommit f = it.next();
					listWaiting.add(f);
					listSelected.remove(f);
				}

				viewerWaiting.setInput(new FileToCommitModel(listWaiting));
				viewerCommit.setInput(new FileToCommitModel(listSelected));
				viewerWaiting.refresh();
				viewerCommit.refresh();
			}
		});
		btnNewButton_1.setImage(SWTResourceManager.getImage(CommitUI.class,
				"/res/001_28.png"));
		btnNewButton_1.setText("\u00F4ter du commit");

		
		table_1 = viewerCommit.getTable();
		table_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
		// table_1.setLayoutData(new RowData(SWT.DEFAULT, 225));

		Composite composite_3 = new Composite(sashForm, SWT.NONE);
		composite_3.setLayout(new GridLayout(1, false));

		Label lblMessageDuCommit = new Label(composite_3, SWT.NONE);
		lblMessageDuCommit.setText("Message du commit:");

		// Champ texte pour Message du commit
		txtCommit = new Text(composite_3, SWT.BORDER | SWT.MULTI);
		GridData gd_txtCommit = new GridData();
		gd_txtCommit.heightHint = 59;
		gd_txtCommit.horizontalAlignment = SWT.FILL;
		gd_txtCommit.grabExcessHorizontalSpace = true;
		txtCommit.setLayoutData(gd_txtCommit);
		// StyledText pour afficher le "diff" avec affichage des scrolling
		diffText = new StyledTextExtended(composite_3);
		GridData gd_diffText = new GridData();
		gd_diffText.verticalAlignment = SWT.FILL;
		gd_diffText.horizontalAlignment = GridData.FILL;
		gd_diffText.grabExcessVerticalSpace = true;
		gd_diffText.grabExcessHorizontalSpace = true;
		diffText.setLayoutData(gd_diffText);
		// sashForm.setWeights(new int[] { 1, 1, 1 });
		// diffText.setLayoutData(GridDataFactory.fillDefaults().grab(true,
		// true).span(2, 1).create());

		Composite composite_4 = new Composite(this, SWT.NONE);
		composite_4.setLayoutData(BorderLayout.SOUTH);
		composite_4.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		// Bouton de commit
		Button btnCommit = new Button(composite_4, SWT.NONE);
		btnCommit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				Commit();
			}
		});
		btnCommit.setText("Commit");

		/**
		 * Bouton Cancel
		 * 
		 * Retourne au panneau de status
		 */
		Button btnCancel = new Button(composite_4, SWT.NONE);
		btnCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				StatusUI statusUI = new StatusUI(parent, SWT.NONE);
				statusUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = statusUI;
				parent.layout(true);
			}
		});
		btnCancel.setText("Cancel");

		// tableItem.setText("New TableItem");

		table.pack();

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	// private AbstractTreeIterator getTreeIterator(String name)
	// throws IOException {
	// final ObjectId id = db.resolve(name);
	// if (id == null)
	// throw new IllegalArgumentException(name);
	// final CanonicalTreeParser p = new CanonicalTreeParser();
	// final ObjectReader or = db.newObjectReader();
	// try {
	// p.reset(or, new RevWalk(db).parseTree(id));
	// return p;
	// } finally {
	// or.release();
	// }
	// }

	private void Commit() {
		System.out.println("Envoie du commit:");
		System.out.println("-----------------------------");
		for (int i = 0; i < listSelected.size(); i++) {
			
			FileToCommit file = listSelected.get(i);
			
			System.out.println("Fichier: " + file.getPath() + "; Type: " + file.getType());
			
			try {
				
				if(file.getType() == TypeFile.REMOVED) {
					this.ref.getGit().rm().addFilepattern(file.getPath()).call();
				}
				else {
					this.ref.getGit().add().addFilepattern(file.getPath()).call();
				}
				
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("File Git: " + file.getPath());
			this.ref.getGit().commit().setOnly(file.getPath());
			
		}
		
		try {

			if (txtCommit.getText() != "") {

				this.ref.getGit().commit().setMessage(txtCommit.getText()).call(); // Définit le message du commit et envoie du commit
				
				// Vide le tableau
				listSelected.clear();
				viewerCommit.setInput(new FileToCommitModel(listSelected));
				viewerCommit.refresh();
				
				System.out.println("Commit envoyé !");
				
			} else {
				MessageBox msgBox = new MessageBox(Display.getCurrent()
						.getActiveShell(), SWT.ICON_WARNING);
				msgBox.setText("Erreur !");
				msgBox.setMessage("Veuillez saisir un message !");
				msgBox.open();
				System.out.println("Commit non envoyé !");
				System.out.println("----------------------------------");
			}

		} catch (NoFilepatternException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void ShowDiff(String txt) {
		diffText.setText("");
		diffText.colorizeText(this.ref.showDiff(txt));
		diffText.layout();
	}
}
