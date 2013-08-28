package cz.muni.fi.pv207.unigift.products;

import java.io.Serializable;

public class Product implements Serializable {

	private static final long serialVersionUID = 3843548982211999727L;

	private String name = "";

	private String description = "";

	public Product() {
		// Auto-generated constructor stub
	}

	public Product(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public String toString() {
		return "Product " + name + " (" + description+")";
	}
}
