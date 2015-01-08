/**
 * 
 */
package org.os.git4dummy.ui;

import java.util.ArrayList;
import java.util.List;

import org.os.git4dummy.model.FileToCommit;
import org.os.git4dummy.model.Referentiel;

/**
 * @author olsanche
 *
 */
public class CommitToPushModel {
	private List<FileToCommit> list = new ArrayList<FileToCommit>();
	private Referentiel ref;
	
	public CommitToPushModel(Referentiel ref) {
		this.setRef(ref);
	}
	
	public List<FileToCommit> getFileFromCommit() {
		return list;
	}

	public Referentiel getRef() {
		return ref;
	}

	public void setRef(Referentiel ref) {
		this.ref = ref;
	}

}
