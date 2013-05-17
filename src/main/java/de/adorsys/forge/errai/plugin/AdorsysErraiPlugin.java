package de.adorsys.forge.errai.plugin;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.jboss.forge.project.Project;
import org.jboss.forge.project.facets.WebResourceFacet;
import org.jboss.forge.project.facets.events.InstallFacets;
import org.jboss.forge.shell.ShellColor;
import org.jboss.forge.shell.ShellPrompt;
import org.jboss.forge.shell.plugins.Alias;
import org.jboss.forge.shell.plugins.Command;
import org.jboss.forge.shell.plugins.PipeIn;
import org.jboss.forge.shell.plugins.PipeOut;
import org.jboss.forge.shell.plugins.Plugin;
import org.jboss.forge.shell.plugins.RequiresProject;

import de.adorsys.forge.errai.plugin.facet.ErraiPluginFacet;


/**
 *
 */
@Alias("adorsyserraiplugin")
@RequiresProject
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
	@Command(value="setup",help="Configure your project to an Errai project")
	public void setUpCommand(@PipeIn String in, PipeOut out) {
		if(!isErraiPluginFacetInstalled()) {
			installFacets.fire(new InstallFacets(ErraiPluginFacet.class));
			out.println(ShellColor.GREEN, "Plugin Successfully Installed !");
		}else {
			out.println(ShellColor.BLUE, "The Plugin Is already Installed");
		}
	}

	boolean isErraiPluginFacetInstalled() {
		return project.hasFacet(ErraiPluginFacet.class) && project.hasFacet(WebResourceFacet.class);
	}
}
