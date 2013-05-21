package de.adorsys.forge.errai.plugin;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.apache.commons.lang.StringUtils;
import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.Option;
import org.jboss.forge.shell.plugins.PipeIn;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresFacet;
import org.jboss.forge.shell.plugins.RequiresProject;
import org.jboss.forge.shell.plugins.SetupCommand;

import de.adorsys.forge.errai.plugin.facet.ErraiPluginFacet;


/**
 *
 */
@Alias("adorsyserraiplugin")
@RequiresProject
@RequiresFacet(value= {ErraiPluginFacet.class})
public class AdorsysErraiPlugin implements Plugin {
	@Inject
	private ShellPrompt prompt;

	final Project project;

	final Event<InstallFacets> installFacets;
	@Inject
	public AdorsysErraiPlugin(final Project project,  final Event<InstallFacets> event) {
		this.installFacets = event;
		this.project = project;
	}

	@SetupCommand(help="Configure your project to an Errai projec")
	//	@Command(value="setup",help="Configure your project to an Errai project")
	public void setUpCommand(@PipeIn String in, PipeOut out) {
		if(!isErraiPluginFacetInstalled()) {
			installFacets.fire(new InstallFacets(ErraiPluginFacet.class));
			out.println(ShellColor.GREEN, "Plugin Successfully Installed !");
		}else {
			out.println(ShellColor.BLUE, "The Plugin Is already Installed");
		}
	}

	@Command(value="newView",help="Create an UI Binder View")
	public void createUiViewCommand(@Option(name="name",required=true)String viewName,@PipeIn String in,PipeOut out) {
		if(!isErraiPluginFacetInstalled()) {
			throw new RuntimeException("The Plugin "+ErraiPluginFacet.class.getName()+", Is not Installed !");
		}
		ErraiPluginFacet erraiPluginFacet = project.getFacet(ErraiPluginFacet.class);
		String capitalizedName = StringUtils.capitalize(viewName);
		erraiPluginFacet.createViewWithGivenName(capitalizedName);
		out.println("*** "+capitalizedName+"View Created with success !");
	}

	boolean isErraiPluginFacetInstalled() {
		return project.hasFacet(ErraiPluginFacet.class) && project.hasFacet(WebResourceFacet.class);
	}
}
