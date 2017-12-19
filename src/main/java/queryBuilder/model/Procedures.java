package queryBuilder.model;

import java.util.ArrayList;
import java.util.List;

public class Procedures {
	private String name;
	private List<Parameter> inputs = new ArrayList<Parameter>();
	private List<Parameter> outputs = new ArrayList<Parameter>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Parameter> getInputs() {
		return inputs;
	}

	public void setInputs(List<Parameter> inputs) {
		this.inputs = inputs;
	}

	public List<Parameter> getOutputs() {
		return outputs;
	}

	public void setOutputs(List<Parameter> outputs) {
		this.outputs = outputs;
	}

	@Override
	public String toString() {
		return "Procedures [name=" + name + ", inputs=" + inputs + ", outputs=" + outputs + "]";
	}

}
