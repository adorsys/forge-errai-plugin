/**
 * 
 */
package ${packageName};

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
/**
 * KTB's layout.
 * @author bwa
 * 
 */
public class BodyCore extends Composite {

	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	private static BodyCoreUiBinder uiBinder = GWT.create(BodyCoreUiBinder.class);

	interface BodyCoreUiBinder extends UiBinder<Widget, BodyCore> {
	}
	public BodyCore() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@PostConstruct
	public  void initBody() {
	}
}
