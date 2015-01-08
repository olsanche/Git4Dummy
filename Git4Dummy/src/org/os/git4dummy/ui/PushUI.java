package org.os.git4dummy.ui;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.os.git4dummy.model.Referentiel;

import swing2swt.layout.BorderLayout;
import swing2swt.layout.FlowLayout;

public class PushUI extends Composite {

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */

	private StyledTextExtended diffText;
	private String commitSelect;
	private Referentiel ref;

	public PushUI(final Composite parent, int style, Referentiel ref) {
		super(parent, style);
		this.ref = ref;
		setLayout(new BorderLayout(0, 0));

		SashForm sashForm = new SashForm(this, SWT.NONE);

		ScrolledComposite scrolledComposite = new ScrolledComposite(sashForm, SWT.BORDER | SWT.H_SCROLL | SWT.V_SCROLL);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setExpandVertical(true);

		Tree tree = new Tree(scrolledComposite, SWT.BORDER);
		tree.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				TreeItem ti = (TreeItem) e.item;

				if (ti.getParentItem() != null) {
					commitSelect = new String(ti.getParentItem().getText());
					ShowDiff(ti.getText());
				} else {
					commitSelect = new String(ti.getText());
				}

			}
		});

		scrolledComposite.setContent(tree);
		scrolledComposite
				.setMinSize(tree.computeSize(SWT.DEFAULT, SWT.DEFAULT));

		diffText = new StyledTextExtended(sashForm);
		sashForm.setWeights(new int[] { 1, 1 });

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(BorderLayout.SOUTH);
		composite.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Button btnMettreEnRfrence = new Button(composite, SWT.NONE);
		btnMettreEnRfrence.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				PushCommit(commitSelect);
				StatusUI statusUI = new StatusUI(parent, SWT.NONE);
				statusUI.setLayoutData(BorderLayout.CENTER);
				StackLayout sl = (StackLayout) parent.getLayout();
				sl.topControl = statusUI;
				parent.layout(true);
			}
		});
		btnMettreEnRfrence.setText("Mettre en r\u00E9f\u00E9rence");

		Button btnCancel = new Button(composite, SWT.NONE);
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

		Iterator<RevCommit> listCommit;
		try {
			listCommit = this.ref.getListCommit();

			/**
			 * Pour chaque commit, on parcourt la liste des fichiers et on
			 * affiche le tout à l'aide d'un objet Tree
			 */
			while (listCommit.hasNext()) {
				RevCommit commit = listCommit.next(); // Recupere l'instance du
														// commit

				// Creation de l'arbre
				TreeItem trtmNewTreeitem = new TreeItem(tree, SWT.NONE);

				trtmNewTreeitem.setText(commit.getAuthorIdent().getName() + " - " + commit.getAuthorIdent().getWhen().toString());

				for (int i = 0; i < this.ref.getFilesInCommit(
						commit.getName()).size(); i++) {
					TreeItem trtmNewTreeitem_1 = new TreeItem(trtmNewTreeitem, SWT.NONE);
					trtmNewTreeitem_1.setText(this.ref.getFilesInCommit(
							commit.getName()).get(i));
				}
			}
		} catch (RevisionSyntaxException | IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	private void ShowDiff(String txt) {
		diffText.setText("");
		diffText.colorizeText(this.ref.showDiff(txt));
		diffText.layout();

	}

	private void PushCommit(String c) {
		System.out.println("Push");
		System.out.println("--------------------");
		System.out.println(this.ref.getRemoteRepos());
		try {
			Iterable<PushResult> res = this.ref.getGit().push().setRemote("bare").call();
			
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}
}
