/**
 * 
 */
package org.os.git4dummy.ui;

import java.util.ArrayList;
import java.util.List;

import org.os.git4dummy.model.FileToCommit;

/**
 * @author olsanche
 *
 */
public class FileToCommitModel {
	private List<FileToCommit> list = new ArrayList<FileToCommit>();
	
	public FileToCommitModel(List<FileToCommit> list) {
		this.list = list;
	}
	
	public List<FileToCommit> getFileWaitings() {
		return list;
	}
	
}
