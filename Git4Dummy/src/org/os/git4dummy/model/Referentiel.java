/**
 * 
 */
package org.os.git4dummy.model;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.eclipse.jgit.api.DiffCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.diff.RawTextComparator;
import org.eclipse.jgit.errors.AmbiguousObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.NoWorkTreeException;
import org.eclipse.jgit.errors.RevisionSyntaxException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.os.git4dummy.Setup;

/**
 * @author olsanche
 *
 */
public class Referentiel {
	
	private Repository localRepos;
	private Repository remoteRepos;
	private Git git;
	private int nbFileWaiting = 0;
	
	public Referentiel(String repo) {
		try {
			this.localRepos = new FileRepository(repo);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		this.git = new Git(this.localRepos);
	}
	
	
	/**
	 * @return the localRepos
	 */
	public final Repository getLocalRepos() {
		return localRepos;
	}
	/**
	 * @param localRepos the localRepos to set
	 */
	public final void setLocalRepos(Repository localRepos) {
		this.localRepos = localRepos;
	}
	/**
	 * @return the remoteRepos
	 */
	public final Repository getRemoteRepos() {
		return remoteRepos;
	}
	/**
	 * @param remoteRepos the remoteRepos to set
	 */
	public final void setRemoteRepos(Repository remoteRepos) {
		this.remoteRepos = remoteRepos;
	}
	/**
	 * @return the git
	 */
	public final Git getGit() {
		return git;
	}
	/**
	 * @param git the git to set
	 */
	public final void setGit(Git git) {
		this.git = git;
	}
	
	/**
	 * 
	 * @return Nombre de fichier en attente de commit
	 */
	public final int getNbFileWaiting() {
		return this.nbFileWaiting;
	}
	
	/**
	 * 
	 * @return nombre de fichier en attente de commit
	 */
	public List<FileToCommit> getFileWaiting() {
		List<FileToCommit> list=  new ArrayList<FileToCommit>();

		try {
			Status status = this.git.status().call();
			System.out.println("Liste des fichiers en attente d'un commit :");
			System.out.println("------------------------------------------------------------------");
			
			//nb = status.getUncommittedChanges().size();
			System.out.println("Added: " + status.getAdded());
			System.out.println("Changed: " + status.getChanged());
			System.out.println("Conflicting: " + status.getConflicting());
			System.out.println("ConflictingStageState: "
					+ status.getConflictingStageState());
			System.out.println("IgnoredNotInIndex: "
					+ status.getIgnoredNotInIndex());
			System.out.println("Missing: " + status.getMissing());
			System.out.println("Modified: " + status.getModified());
			System.out.println("Removed: " + status.getRemoved());
			System.out.println("Untracked: " + status.getUntracked());
			System.out.println("UntrackedFolders: " + status.getUntrackedFolders());

			// Use iterator to display contents of al
			Iterator<String> itr = status.getAdded().iterator();
			while (itr.hasNext()) {
				Object element = itr.next();
				list.add(new FileToCommit((String)element, TypeFile.UNTRACKED));
			}

			Iterator<String> itr4 = status.getUntracked().iterator();
			while (itr4.hasNext()) {
				Object element = itr4.next();
				list.add(new FileToCommit((String)element, TypeFile.UNTRACKED));
			}

			Iterator<String> itr1 = status.getModified().iterator();
			while (itr1.hasNext()) {
				Object element = itr1.next();
				list.add(new FileToCommit((String)element, TypeFile.CHANGED));

			}

			Iterator<String> itr2 = status.getRemoved().iterator();
			while (itr2.hasNext()) {
				Object element = itr2.next();
				list.add(new FileToCommit((String)element, TypeFile.REMOVED));
			}
			
			Iterator<String> itr3 = status.getChanged().iterator();
			while (itr3.hasNext()) {
				Object element = itr3.next();
				list.add(new FileToCommit((String)element, TypeFile.CHANGED));
			}
			
			Iterator<String> itr5 = status.getMissing().iterator();
			while (itr5.hasNext()) {
				Object element = itr5.next();
				list.add(new FileToCommit((String)element, TypeFile.REMOVED));
			}
			
		} catch (NoWorkTreeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("------------------------------------------------------------------");
		
		this.nbFileWaiting = list.size();
		return list;
		
	}
	
	/**
	 * 
	 * @return nombre de commit en attente de push
	 * @throws IOException 
	 * @throws IncorrectObjectTypeException 
	 * @throws AmbiguousObjectException 
	 * @throws RevisionSyntaxException 
	 */
	public int getNumberPushWaiting()  {
	
		int nb = 0;
		
			try {
				Iterator<RevCommit> list = this.getListCommit();
				
				while(list.hasNext()) {
					list.next();
					nb++;
				}
				
			} catch (RevisionSyntaxException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return nb;
		
	}
	
	/**
	 * 
	 * @return Liste des commits en attente de push
	 * @throws IOException 
	 * @throws IncorrectObjectTypeException 
	 * @throws AmbiguousObjectException 
	 * @throws RevisionSyntaxException 
	 */
	public Iterator<RevCommit> getListCommit() throws RevisionSyntaxException, AmbiguousObjectException, IncorrectObjectTypeException, IOException {

		RevWalk walk = new RevWalk(this.localRepos);
		ObjectId from = this.localRepos.resolve("refs/heads/master");
		ObjectId to = this.localRepos.resolve("refs/remotes/origin/master");
		walk.markStart(walk.parseCommit(from));
		walk.markUninteresting(walk.parseCommit(to));
		
		return walk.iterator();
		
	}
	
	/**
	 * 
	 * @param String du commit
	 * @return la liste des fichiers d'un commit
	 */
	public List<String> getFilesInCommit(String c) {
		List<String> list = new ArrayList<String>();

		RevWalk rw = null;

		try {
			rw = new RevWalk(this.localRepos);
			ObjectId head = this.localRepos.resolve(c);
			RevCommit commit = rw.parseCommit(head);
			RevCommit parent = rw.parseCommit(commit.getParent(0).getId());
			DiffFormatter df = new DiffFormatter(DisabledOutputStream.INSTANCE);
			df.setRepository(this.localRepos);
			df.setDiffComparator(RawTextComparator.DEFAULT);
			df.setDetectRenames(true);
			List<DiffEntry> diffs = df.scan(parent.getTree(), commit.getTree());
			
			System.out.println("Liste des fichiers du commit: " + c);
			System.out.println("--------------------------------------------- ");
			
			for (DiffEntry diff : diffs) {
				System.out.println("Fichier: " + diff.getNewPath());
				list.add(diff.getNewPath());
			}

		} catch (Throwable t) {

		} finally {

			rw.dispose();
		}
		
		System.out.println("-------------------------------------------------");
		return list;
	}
	
	/**
	 * 
	 * @param Chemin de fichier
	 * @return diff avec l'ancienne version.
	 */
	public String showDiff(String path) {
		System.out.println("ShowDiff:  " + path);
		System.out.println("------------------------------------------------");

		DiffCommand diff = this.git.diff();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			diff.setPathFilter(PathFilter.create(path)).setOutputStream(out)
					.call();
			return out.toString();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

		

	}

}
