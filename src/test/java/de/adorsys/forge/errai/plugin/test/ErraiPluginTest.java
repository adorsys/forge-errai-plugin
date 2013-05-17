package de.adorsys.forge.errai.plugin.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.forge.test.AbstractShellTest;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Test;

import de.adorsys.forge.errai.plugin.AdorsysErraiPlugin;

public class ErraiPluginTest extends AbstractShellTest
{
	@Deployment
	public static JavaArchive getDeployment()
	{
		return AbstractShellTest.getDeployment()
				.addPackages(true, AdorsysErraiPlugin.class.getPackage());
	}

	@Test
	public void testDefaultCommand() throws Exception
	{
		getShell().execute("adorsyserraiplugin setup");
	}

}
