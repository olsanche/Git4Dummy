/**
 * 
 */
package org.os.git4dummy.ui;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.wb.swt.SWTResourceManager;
import org.os.git4dummy.model.FileToCommit;

public class FileToCommitViewer extends TableViewer {
	private static Image IMG_ADD = SWTResourceManager.getImage(MainUI.class,
			"/res/001_01.png");
	private static Image IMG_CHANGED = SWTResourceManager.getImage(
			MainUI.class, "/res/001_11.png");
	private static Image IMG_DEL = SWTResourceManager.getImage(MainUI.class,
			"/res/001_05.png");

	public FileToCommitViewer(Composite parent, int style) {
		super(parent, style);

		Table table = getTable();
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, true);
		table.setLayoutData(gridData);
		createColumns();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		setContentProvider(new FileToCommitProvider());
	}

	private void createColumns() {

		

		TableViewerColumn column1 = createTableViewerColumn("Type", 30, 0);
		column1.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				return super.getText(element);
			}

			public Image getImage(Object element) {
				Image img = null;
				if (element instanceof FileToCommit) {
					System.out.println(((FileToCommit) element).getType());
					switch (((FileToCommit) element).getType()) {
					case UNTRACKED:
						img = IMG_ADD;
						break;
					case CHANGED:
						img = IMG_CHANGED;
						break;
					case REMOVED:
						img = IMG_DEL;
						break;
					}
				}
				return img;
			}
		});
		
		TableViewerColumn column = createTableViewerColumn("Fichier", 150, 1);
		column.setLabelProvider(new ColumnLabelProvider() {
			public String getText(Object element) {
				if (element instanceof FileToCommit)
					return ((FileToCommit) element).getPath();
				return super.getText(element);
			}
		});

		// column.setEditingSupport(new OptionEditingSupport(this));
	}

	private TableViewerColumn createTableViewerColumn(String header, int width,
			int idx) {
		TableViewerColumn column = new TableViewerColumn(this, SWT.LEFT, idx);
		column.getColumn().setText(header);
		column.getColumn().setWidth(width);
		column.getColumn().setResizable(true);
		column.getColumn().setMoveable(true);

		return column;
	}
}
