
package ${packageName};

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;

import org.jboss.errai.enterprise.client.jaxrs.api.RestClient;
import org.jboss.errai.ioc.client.api.EntryPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.user.client.ui.RootPanel;
import com.google.inject.Inject;
/**
 * Template entry point.
 * @author w2b
 *
 */
@EntryPoint
public class App {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	/** Application's layout */
	@Inject
	BodyCore bodyCore;
	static {
		RestClient.setApplicationRoot("/erraitemplate.server/rest");
		RestClient.setJacksonMarshallingActive(true);
	}

	@PostConstruct
	public void initApp() {
		RootPanel.get().add(bodyCore);
	}
}
