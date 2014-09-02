package pl.maciejkizlich.interview.mail;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupDir;

import pl.maciejkizlich.interview.mail.templates.EMAIL_TEMPLATES;

public class MailerConfig {

	private final String recipientEmail;
	private final EMAIL_TEMPLATES template;
	private final Map<String, Object> valuesMap;
	private ST stTemplate;

	public String getRecipientEmail() {
		return recipientEmail;
	}

	public EMAIL_TEMPLATES getTemplate() {
		return template;
	}

	public Map<String, Object> getValuesMap() {
		return valuesMap;
	}

	public ST getStTemplate() {
		return stTemplate;
	}

	public MailerConfig(EmailBuilder emailBuilder) {
		this.recipientEmail = emailBuilder.recipientEmail;
		this.template = emailBuilder.template;
		this.valuesMap = emailBuilder.valuesMap;
		this.stTemplate = emailBuilder.stTemplate;
	}

	public static class EmailBuilder {
		
		private static final char TAG_DELIMITER = '$';
		
		private static final String TEMPLATES_DIR = "templates";
		
		private String recipientEmail;
		private EMAIL_TEMPLATES template;
		private Map<String, Object> valuesMap;
		private ST stTemplate;
		private boolean skipValidation = false;

		public EmailBuilder(String recipient) {
			this.recipientEmail = recipient;
		}

		public EmailBuilder template(EMAIL_TEMPLATES template) {
			this.template = template;
			this.stTemplate = new STGroupDir(TEMPLATES_DIR, TAG_DELIMITER, TAG_DELIMITER).getInstanceOf(this.template.getTemplateFileName());
			return this;
		}
		
		public EmailBuilder skipValidation(){
			this.skipValidation = true;
			return this;
		}

		public MailerConfig build() {
			if(null == this.valuesMap){
				throw new IllegalStateException("Email variables are not set!");
			}
			
			if (skipValidation == false) {
				this.validate();
			}

			populateTemplate();

			return new MailerConfig(this);
		}

		private void populateTemplate() {
			for (Entry<String, Object> entry : valuesMap.entrySet()) {
				stTemplate.add(entry.getKey(), entry.getValue());
			}
		}

		public void validate() {
			
			if(null == this.recipientEmail){
				throw new IllegalStateException("Email recipient is not set!");
			}
			
			if(null == this.template){
				throw new IllegalStateException("Email template is not set!");
			}
			
			boolean isValid = true;
			
			List<String> missingFields = new ArrayList<String>();

			for (String obligatoryKey : getObligatoryValues()) {
				if (valuesMap.containsKey(obligatoryKey) == false || null == valuesMap.get(obligatoryKey)) {
					missingFields.add(obligatoryKey);
					isValid = false;
				}
			}

			if (isValid == false) {
				throw new IllegalStateException("Email template [" + template.toString() + "] is not valid, missing parameter: [" + StringUtils.join(missingFields.toArray()) + "]");
			}
		}

		public EmailBuilder supplyVariables(Map<String, Object> values) {
			this.valuesMap = values;
			return this;
		}

		private List<String> getObligatoryValues() {
			Map<String, Object> attributes = stTemplate.getAttributes();

			List<String> fieldNames = new ArrayList<String>();

			for (Entry<String, Object> entry : attributes.entrySet()) {
				fieldNames.add(entry.getKey());
			}
			return fieldNames;
		}
	}
}
