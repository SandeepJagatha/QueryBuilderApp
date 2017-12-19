package queryBuilder.model;

public class Parameter {
	private String specific_name = null;
	private String parameter_mode = null;
	private String parameter_name = null;
	private String character_maximum_length = null;
	private String dtd_identifier = null;

	public String getSpecific_name() {
		return specific_name;
	}

	public void setSpecific_name(String specific_name) {
		this.specific_name = specific_name;
	}

	public String getParameter_mode() {
		return parameter_mode;
	}

	public void setParameter_mode(String parameter_mode) {
		this.parameter_mode = parameter_mode;
	}

	public String getParameter_name() {
		return parameter_name;
	}

	public void setParameter_name(String parameter_name) {
		this.parameter_name = parameter_name;
	}

	public String getCharacter_maximum_length() {
		return character_maximum_length;
	}

	public void setCharacter_maximum_length(String character_maximum_length) {
		this.character_maximum_length = character_maximum_length;
	}

	public String getDtd_identifier() {
		return dtd_identifier;
	}

	public void setDtd_identifier(String dtd_identifier) {
		this.dtd_identifier = dtd_identifier;
	}

	@Override
	public String toString() {
		return "Parameter [specific_name=" + specific_name + ", parameter_mode=" + parameter_mode + ", parameter_name="
				+ parameter_name + ", character_maximum_length=" + character_maximum_length + ", dtd_identifier="
				+ dtd_identifier + "]";
	}

}
