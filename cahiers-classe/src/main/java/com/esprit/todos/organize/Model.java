package com.esprit.todos.organize;

public class Model {

	private String groupe;
	private String module;

	public Model() {
	}

	@Override
	public String toString() {
		return "Model [groupe=" + groupe + ", module=" + module + "]";
	}

	public Model(String groupe, String module) {
		super();
		this.groupe = groupe;
		this.module = module;
	}

	public String getGroupe() {
		return groupe;
	}

	public void setGroupe(String groupe) {
		this.groupe = groupe;
	}

	public String getModule() {
		return module;
	}

	public void setModule(String module) {
		this.module = module;
	}

}
