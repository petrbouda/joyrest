package org.joyrest.validation;

import javax.validation.ConstraintViolation;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public final class ValidationError {

	private String message;
	private String messageTemplate;
	private String propertyPath;
	private String invalidValue;

	public ValidationError() {
	}

	public ValidationError(ConstraintViolation<?> v) {
		this(v.getMessage(), v.getMessageTemplate(), v.getPropertyPath().toString(), v.getInvalidValue().toString());
	}

	public ValidationError(String message, String messageTemplate, String propertyPath, String invalidValue) {
		this.message = message;
		this.messageTemplate = messageTemplate;
		this.propertyPath = propertyPath;
		this.invalidValue = invalidValue;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessageTemplate() {
		return messageTemplate;
	}

	public void setMessageTemplate(String messageTemplate) {
		this.messageTemplate = messageTemplate;
	}

	public String getPropertyPath() {
		return propertyPath;
	}

	public void setPropertyPath(String propertyPath) {
		this.propertyPath = propertyPath;
	}

	public String getInvalidValue() {
		return invalidValue;
	}

	public void setInvalidValue(String invalidValue) {
		this.invalidValue = invalidValue;
	}
}
