package org.os.git4dummy;

import org.os.git4dummy.model.Referentiel;

public final class Setup {
	private final static String FOLDER_GIT_OPL = "../.git";
	private final static String FOLDER_GIT_OPLF = "../.git";
	private final static String OPL_REMOTE = "origin";
	private final static String OPLF_REMOTE = "origin";
	private final static Referentiel OPL = new Referentiel(FOLDER_GIT_OPL);
	private final static Referentiel OPLF = new Referentiel(FOLDER_GIT_OPLF);
	/**
	 * @return the folderGitOpl
	 */
	public static final String getFolderGitOpl() {
		return FOLDER_GIT_OPL;
	}
	/**
	 * @return the folderGitOplf
	 */
	public static final String getFolderGitOplf() {
		return FOLDER_GIT_OPLF;
	}
	/**
	 * @return the folderGitOplRemote
	 */
	public static final String getOplRemote() {
		return OPL_REMOTE;
	}
	/**
	 * @return the folderGitOplfRemote
	 */
	public static final String getOplfRemote() {
		return OPLF_REMOTE;
	}
	/**
	 * @return the opl
	 */
	public static final Referentiel getOpl() {
		return OPL;
	}
	/**
	 * @return the oplf
	 */
	public static final Referentiel getOplf() {
		return OPLF;
	}
	
	
	
}
