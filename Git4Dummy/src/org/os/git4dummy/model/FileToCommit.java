/**
 * 
 */
package org.os.git4dummy.model;


/**
 * @author olsanche
 *
 */
public class FileToCommit {
	private String path = "";
	private TypeFile type;
	
	public FileToCommit(String path, TypeFile type) {
		this.setPath(path);
		this.setType(type);
	}

	public TypeFile getType() {
		return type;
	}

	public void setType(TypeFile type) {
		this.type = type;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
	
}
