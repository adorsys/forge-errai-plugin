/**
 * Implemented by adorsys GmbH & Co. KG in 2013
 * http://www.adorsys.de
 */
package de.adorsys.forge.errai.plugin.facet;

import java.io.InputStream;

import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.plugins.RequiresFacet;

import de.adorsys.forge.errai.plugin.utils.RessourceUtils;

/**
 * @author bwa
 * 
 */
@RequiresFacet({ WebResourceFacet.class })
public class ErraiPluginFacet extends BaseFacet {

	private RessourceUtils resourceUtils;

	@Override
	public boolean install() {
		this.resourceUtils = new RessourceUtils(project);
		resourceUtils.getRootDirectory();
		createWebappFiles();
		createAppFiles();
		createResourceFiles();
		createTestFiles();
		generatePOMFile();
		return true;
	}

	private void generatePOMFile() {
		FileResource<?> pomFile = (FileResource<?>) resourceUtils.getRootDirectory().getChild("pom.xml");
		InputStream stream = ErraiPluginFacet.class.getResourceAsStream("/errai-resources/pom.xml");
		pomFile.setContents(stream);
	}

	private void createTestFiles() {
	}

	private void createResourceFiles() {

	}

	private void createAppFiles() {
	}

	private void createWebappFiles() {
		DirectoryResource cssDirectory = resourceUtils.getWebRootDirectory().getOrCreateChildDirectory("css");
		DirectoryResource javaScriptDirectory = resourceUtils.getWebRootDirectory().getOrCreateChildDirectory("js");
		DirectoryResource imageDirectory = resourceUtils.getWebRootDirectory().getOrCreateChildDirectory("img");
		DirectoryResource webInfDirectory = resourceUtils.getWebRootDirectory().getOrCreateChildDirectory("WEB-INF");
		DirectoryResource webRootDirectory = resourceUtils.getWebRootDirectory();
		// webapp ressources files
		String[][] cssFilesNames = { { "/errai-resources/css/bootstrap-responsive.css", "bootstrap-responsive.css" },
				{ "/errai-resources/css/bootstrap.css", "bootstrap.css" } };
		String[][] javaScriptsFileNames = { { "/errai-resources/js/jquery.js", "jquery.js" },
				{ "/errai-resources/js/bootstrap.js", "bootstrap.js" } ,
				{ "/errai-resources/js/bootstrap-datepicker.js", "bootstrap-datepicker.js" } ,
				{ "/errai-resources/js/bootstrap-dropdown.js", "bootstrap-dropdown.js" } ,
				{ "/errai-resources/js/bootstrap-modal.js", "bootstrap-modal.js" } ,
				{ "/errai-resources/js/bootstrap-popover.js", "bootstrap-popover.js" } ,
				{ "/errai-resources/js/bootstrap-scrollspy.js", "bootstrap-scrollspy.js" } ,
				{ "/errai-resources/js/bootstrap-tab.js", "bootstrap-tab.js" } ,
				{ "/errai-resources/js/bootstrap-tooltip.js", "bootstrap-tooltip.js" } ,
				{ "/errai-resources/js/bootstrap-transition.js", "bootstrap-transition.js" } ,
				{ "/errai-resources/js/bootstrap-typeahead.js", "bootstrap-typeahead.js" },
				{ "/errai-resources/js/bootstrap-affix.js", "bootstrap-affix.js" } ,
				{ "/errai-resources/js/bootstrap-alert.js", "bootstrap-alert.js" } ,
				{ "/errai-resources/js/bootstrap-button.js", "bootstrap-button.js" } ,
				{ "/errai-resources/js/bootstrap-carousel.js", "bootstrap-carousel.js" } ,
				{ "/errai-resources/js/bootstrap-collapse.js", "bootstrap-collapse.js" } ,
				{ "/errai-resources/js/application.js", "application.js" }  };
		String[][] imagesFileNames = { { "/errai-resources/img/glyphicons-halflings-white.png", "glyphicons-halflings-white.png" },
				{ "/errai-resources/img/glyphicons-halflings.png", "glyphicons-halflings.png" } };
		String[][] webXmlFileNames = { { "/errai-resources/WEB-INF/web.xml", "web.xml" } };
		String[][] indexFileNames = { { "/errai-resources/index.html", "index.html" } };
		//create files
		resourceUtils.createFiles(cssDirectory, cssFilesNames);
		resourceUtils.createFiles(javaScriptDirectory, javaScriptsFileNames);
		resourceUtils.createFiles(imageDirectory, imagesFileNames);
		resourceUtils.createFiles(webInfDirectory, webXmlFileNames);
		resourceUtils.createFiles(webRootDirectory, indexFileNames);
	}

	@Override
	public boolean isInstalled() {
		return project.hasFacet(ErraiPluginFacet.class);
	}
}
