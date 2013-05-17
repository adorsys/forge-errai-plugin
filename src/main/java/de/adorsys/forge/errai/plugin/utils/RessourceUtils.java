/**
 * Implemented by adorsys GmbH & Co. KG in 2013
 * http://www.adorsys.de
 */
package de.adorsys.forge.errai.plugin.utils;

import java.io.InputStream;

import org.jboss.forge.project.Project;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;

import de.adorsys.forge.errai.plugin.facet.ErraiPluginFacet;

/**
 * @author bwa
 *
 */
public class RessourceUtils {

	private final Project project;

	public RessourceUtils(Project  project) {
		if(project == null) {
			throw new RuntimeException("Null Project !");
		}
		this.project = project;
	}
	public DirectoryResource getRootDirectory() {
		return project.getProjectRoot();
	}
	public static DirectoryResource getSubDirectory(DirectoryResource directorResource,String subDirectoryName) {
		return directorResource.getOrCreateChildDirectory(subDirectoryName);
	}
	public void createFiles(DirectoryResource directoryResource,String[] ... fileNames) {
		for (int i = 0; i < fileNames.length; i++) {
			String[] fileMapping = fileNames[i];
			InputStream resourceAsStream = ErraiPluginFacet.class.getResourceAsStream(fileMapping[0]);
			FileResource<?> child = (FileResource<?>) directoryResource.getChild(fileMapping[1]);
			if(child.createNewFile()) {
				child.setContents(resourceAsStream);
			}
		}
	}
	public DirectoryResource getWebRootDirectory(){
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("webapp");
	}

	public DirectoryResource getBasePackageDirectory(){
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("java");
	}

	public DirectoryResource getResourceDirectory(){
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("resources");
	}

	public DirectoryResource getTestResourceDirectory(){
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("test").getOrCreateChildDirectory("java");
	}

	public DirectoryResource getTestBasePackageDirectory(){
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("test").getOrCreateChildDirectory("resources");
	}

}
