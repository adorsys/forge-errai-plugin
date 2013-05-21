/**
 * Implemented by adorsys GmbH & Co. KG in 2013
 * http://www.adorsys.de
 */
package de.adorsys.forge.errai.plugin.facet;

import java.io.InputStream;

import javax.inject.Inject;

import org.apache.maven.project.MavenProject;
import org.jboss.forge.maven.MavenCoreFacet;
import org.jboss.forge.project.facets.BaseFacet;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.RequiresFacet;

import de.adorsys.forge.errai.plugin.utils.RessourceUtils;

/**
 * @author bwa
 * 
 */
@RequiresFacet({ JavaSourceFacet.class, WebResourceFacet.class })
public class ErraiPluginFacet extends BaseFacet {

	private RessourceUtils resourceUtils;
	@Inject
	ShellPrompt shellPrompt;

	@Override
	public boolean install() {
		this.resourceUtils = new RessourceUtils(project);
		shellPrompt.promptBoolean("This Will  Overide Some Of Your Files, Continue ?");
		createWebappFiles();
		createAppFiles();
		createErraiPropertiesFiles();
		createTestFiles();
		generatePOMFile();
		return true;
	}

	private void generatePOMFile() {
		FileResource<?> pomFile = (FileResource<?>) resourceUtils.getRootDirectory().getChild("pom.xml");
		InputStream stream = ErraiPluginFacet.class.getResourceAsStream("/errai-resources/pom.xml");

		MavenCoreFacet mavenCoreFacet = project.getFacet(MavenCoreFacet.class);
		MavenProject mavenProject = mavenCoreFacet.getMavenProject();
		String groupId = mavenProject.getGroupId();
		String artifactId = mavenProject.getArtifactId();
		String version = mavenProject.getVersion();
		System.out.println(groupId+", "+artifactId+", "+version);
		stream = resourceUtils.xmlKeyModifier(stream, "${groupId}", groupId, "\n");
		stream = resourceUtils.xmlKeyModifier(stream, "${artifactId}", artifactId,"\n");
		stream = resourceUtils.xmlKeyModifier(stream, "${version}", version, "\n");
		stream = resourceUtils.xmlKeyModifier(stream, "${basePackage}", resourceUtils.getBasePackage(), "\n");

		pomFile.setContents(stream);
	}

	private void createTestFiles() {
	}

	private void createErraiPropertiesFiles() {
		DirectoryResource resourceDirectory = resourceUtils.getResourceDirectory();
		String[][] erraiPropertiesFiles = { { "/errai-resources/ErraiApp.properties", "ErraiApp.properties" },
				{ "/errai-resources/ErraiService.properties", "ErraiService.properties" }};
		resourceUtils.createFiles(resourceDirectory, erraiPropertiesFiles);
	}

	private void createAppFiles() {
		resourceUtils.getBasePackageDirectory().createNewFile();
		DirectoryResource javaBasePackageResource = resourceUtils.getJavaBasePackageResource();
		String[][] uiFiles = { { "/errai-resources/welcome-ui/App.java", "App.java" },
				{ "/errai-resources/welcome-ui/BodyCore.java", "BodyCore.java" },
				{ "/errai-resources/welcome-ui/BodyCore.ui.xml", "BodyCore.ui.xml" },
				{"/errai-resources/validation/ValidatorFactory.java","ValidatorFactory.java"}};

		createGwtXmlFile();
		resourceUtils.createFiles(javaBasePackageResource, uiFiles);
	}

	private void createGwtXmlFile() {
		FileResource<?> gwtXmlFile = (FileResource<?>) resourceUtils.getJavaBasePackageResource().getChild("App.gwt.xml");
		InputStream stream = ErraiPluginFacet.class.getResourceAsStream( "/errai-resources/welcome-ui/App.gwt.xml");
		System.out.println("Base Package : "+resourceUtils.getBasePackage());
		stream = resourceUtils.xmlKeyModifier(stream, "${packageName}", resourceUtils.getBasePackage(), "\n");
		gwtXmlFile.setContents(stream);
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
				{ "/errai-resources/js/bootstrap.js", "bootstrap.js" },
				{ "/errai-resources/js/bootstrap-datepicker.js", "bootstrap-datepicker.js" },
				{ "/errai-resources/js/bootstrap-dropdown.js", "bootstrap-dropdown.js" },
				{ "/errai-resources/js/bootstrap-modal.js", "bootstrap-modal.js" },
				{ "/errai-resources/js/bootstrap-popover.js", "bootstrap-popover.js" },
				{ "/errai-resources/js/bootstrap-scrollspy.js", "bootstrap-scrollspy.js" },
				{ "/errai-resources/js/bootstrap-tab.js", "bootstrap-tab.js" },
				{ "/errai-resources/js/bootstrap-tooltip.js", "bootstrap-tooltip.js" },
				{ "/errai-resources/js/bootstrap-transition.js", "bootstrap-transition.js" },
				{ "/errai-resources/js/bootstrap-typeahead.js", "bootstrap-typeahead.js" },
				{ "/errai-resources/js/bootstrap-affix.js", "bootstrap-affix.js" },
				{ "/errai-resources/js/bootstrap-alert.js", "bootstrap-alert.js" },
				{ "/errai-resources/js/bootstrap-button.js", "bootstrap-button.js" },
				{ "/errai-resources/js/bootstrap-carousel.js", "bootstrap-carousel.js" },
				{ "/errai-resources/js/bootstrap-collapse.js", "bootstrap-collapse.js" },
				{ "/errai-resources/js/application.js", "application.js" } };
		String[][] imagesFileNames = { { "/errai-resources/img/glyphicons-halflings-white.png", "glyphicons-halflings-white.png" },
				{ "/errai-resources/img/glyphicons-halflings.png", "glyphicons-halflings.png" } };
		String[][] webXmlFileNames = { { "/errai-resources/WEB-INF/web.xml", "web.xml" } };
		String[][] indexFileNames = { { "/errai-resources/index.html", "index.html" } };
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


	public void createViewWithGivenName(String capitalizedName) {
		DirectoryResource valueBoxEditorDecoratorPackage = resourceUtils.getValueBoxEditorDecoratorPackage();
		DirectoryResource javaBasePackageResource = resourceUtils.getJavaBasePackageResource();

		String[][] validationFactoryDecorator = {{"/errai-resources/validation/ValueBoxEditorDecorator.ui.xml","ValueBoxEditorDecorator.ui.xml"}};

		if(!resourceUtils.fileExist(valueBoxEditorDecoratorPackage, "ValueBoxEditorDecorator.ui.xml")) {
			resourceUtils.createFiles(valueBoxEditorDecoratorPackage, validationFactoryDecorator);
		}
		resourceUtils.createViewFromTemplate(capitalizedName, javaBasePackageResource);
	}
}
