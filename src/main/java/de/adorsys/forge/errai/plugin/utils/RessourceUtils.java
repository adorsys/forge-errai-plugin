/**
 * Implemented by adorsys GmbH & Co. KG in 2013
 * http://www.adorsys.de
 */
package de.adorsys.forge.errai.plugin.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.JavaSourceFacet;
import org.jboss.forge.resources.DirectoryResource;
import org.jboss.forge.resources.FileResource;
import org.jboss.forge.resources.Resource;

import de.adorsys.forge.errai.plugin.facet.ErraiPluginFacet;

/**
 * @author bwa
 * 
 */
public class RessourceUtils {

	private final Project project;

	public RessourceUtils(Project project) {
		if (project == null) {
			throw new RuntimeException("Null Project !");
		}
		this.project = project;
	}

	public DirectoryResource getRootDirectory() {
		return project.getProjectRoot();
	}

	public static DirectoryResource getSubDirectory(DirectoryResource directorResource, String subDirectoryName) {
		return directorResource.getOrCreateChildDirectory(subDirectoryName);
	}

	public void createFiles(DirectoryResource directoryResource, String[]... fileNames) {
		for (int i = 0; i < fileNames.length; i++) {
			String[] fileMapping = fileNames[i];
			String resourceToLoad = fileMapping[0];
			InputStream resourceAsStream = ErraiPluginFacet.class.getResourceAsStream(resourceToLoad);
			if (resourceToLoad.endsWith(".java")) {
				resourceAsStream = updatePackageName(resourceAsStream, getBasePackage());
			}
			FileResource<?> child = (FileResource<?>) directoryResource.getChild(fileMapping[1]);
			writeFile(resourceAsStream, child);
		}
	}

	public void createViewFromTemplate(String viewName, DirectoryResource directoryResource) {

		InputStream viewTemplateStream = ErraiPluginFacet.class.getResourceAsStream("/errai-resources/views/ViewTemplate.java");
		String finalViewFileName = viewName + "View.java";
		InputStream viewTemplateWithReplacedViewName = replaceViewName(viewName, viewTemplateStream);
		viewTemplateWithReplacedViewName = updatePackageName(viewTemplateWithReplacedViewName, getBasePackage());
		FileResource<?> finalViewFileResource = (FileResource<?>) directoryResource.getChild(finalViewFileName);
		writeFile(viewTemplateWithReplacedViewName, finalViewFileResource);

		InputStream xmlUiViewTemplateStream = ErraiPluginFacet.class.getResourceAsStream("/errai-resources/views/ViewTemplate.ui.xml");
		String finalUiXmlViewFileName = viewName + "View.ui.xml";
		FileResource<?> finalUiXmlViewFileResource = (FileResource<?>) directoryResource.getChild(finalUiXmlViewFileName);
		writeFile(xmlUiViewTemplateStream, finalUiXmlViewFileResource);
	}

	private void writeFile(InputStream xmlUiViewTemplateStream, FileResource<?> finalUiXmlViewFileResource) {
		if (finalUiXmlViewFileResource.createNewFile()) {
			finalUiXmlViewFileResource.setContents(xmlUiViewTemplateStream);
		}
	}

	private InputStream replaceViewName(String viewName, InputStream inputStream) {
		String updatedFileContent = updateFileKeys(inputStream, "${className}", viewName);
		return new ByteArrayInputStream(updatedFileContent.getBytes());
	}

	public DirectoryResource getWebRootDirectory() {
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("webapp");
	}

	public DirectoryResource getBasePackageDirectory() {
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("java");
	}

	public DirectoryResource getResourceDirectory() {
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("main").getOrCreateChildDirectory("resources");
	}

	public DirectoryResource getTestResourceDirectory() {
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("test").getOrCreateChildDirectory("java");
	}

	public DirectoryResource getTestBasePackageDirectory() {
		return getRootDirectory().getOrCreateChildDirectory("src").getOrCreateChildDirectory("test").getOrCreateChildDirectory("resources");
	}

	public String getBasePackage() {
		JavaSourceFacet javaSourceFacet = project.getFacet(JavaSourceFacet.class);
		return javaSourceFacet.getBasePackage();
	}

	public DirectoryResource getJavaBasePackageResource() {
		return project.getFacet(JavaSourceFacet.class).getBasePackageResource();
	}

	public DirectoryResource getValueBoxEditorDecoratorPackage() {
		return getBasePackageDirectory().getOrCreateChildDirectory("com").getOrCreateChildDirectory("google")
				.getOrCreateChildDirectory("gwt").getOrCreateChildDirectory("editor").getOrCreateChildDirectory("ui")
				.getOrCreateChildDirectory("client");
	}

	/**
	 * @param fileInputStream
	 * @param key
	 * @param value
	 * @return String
	 * @throws Exception
	 */
	public String updateFileKeys(InputStream fileInputStream, String key, String value) {
		String templateJavaFileString = new java.util.Scanner(fileInputStream).useDelimiter("\\A").next();
		return templateJavaFileString.replace(key, value);
	}

	public String updateFileKeys(InputStream fileInputStream, String key, String value, String delimiter) {
		String templateJavaFileString = new java.util.Scanner(fileInputStream).useDelimiter(delimiter).next();
		if (templateJavaFileString.contains(key)) {
			templateJavaFileString = templateJavaFileString.replace(key, value);
		}
		return templateJavaFileString;
	}

	public InputStream updatePackageName(InputStream fileInputStream, String packageName) {
		String updatedFileContent = updateFileKeys(fileInputStream, "${packageName}", packageName);
		return new ByteArrayInputStream(updatedFileContent.getBytes());
	}

	public static String convertStreamToString(java.io.InputStream is) {
		java.util.Scanner s = new java.util.Scanner(is).useDelimiter("\\A");
		String convertionResult = s.hasNext() ? s.next() : "";
		s.close();
		return convertionResult;
	}

	public ByteArrayInputStream xmlKeyModifier(InputStream fileInputStream, String key, String value, String delimiter) {
		Scanner fileInputStreamScanner = new java.util.Scanner(fileInputStream).useDelimiter(delimiter);
		StringBuffer stringBuffer = new StringBuffer("");
		while (fileInputStreamScanner.hasNext()) {
			String nextLine = fileInputStreamScanner.nextLine();
			if (nextLine.contains(key)) {
				nextLine = nextLine.replace(key, value);
			}
			stringBuffer.append(nextLine + "\n");
		}
		fileInputStreamScanner.close();
		return new ByteArrayInputStream(stringBuffer.toString().getBytes());
	}

	public boolean fileExist(DirectoryResource directoryResource,String fileName) {
		List<Resource<?>> listResources = directoryResource.listResources();
		for (Resource<?> resource : listResources) {
			if(resource.getName().equals(fileName)) {
				return true;
			}
		}
		return false ;
	}
}
