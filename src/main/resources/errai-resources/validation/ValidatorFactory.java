/**
 * 
 */
package ${packageName};

import javax.validation.Validator;
import javax.validation.groups.Default;

import com.google.gwt.core.client.GWT;
import com.google.gwt.validation.client.AbstractGwtValidatorFactory;
import com.google.gwt.validation.client.GwtValidation;
import com.google.gwt.validation.client.impl.AbstractGwtValidator;

/**
 * @author bwa
 *
 */
public final class ValidatorFactory extends AbstractGwtValidatorFactory{
	/*
	 * add class to be validated, in the value field.
	 */
	@GwtValidation(value={},groups={Default.class})
	public interface AuthClientValidator extends Validator{
		
	}
	@Override
	public AbstractGwtValidator createValidator() {
		return GWT.create(AuthClientValidator.class);
	}

}
