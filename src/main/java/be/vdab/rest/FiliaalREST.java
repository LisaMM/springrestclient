package be.vdab.rest;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.beans.BeanUtils;

@XmlRootElement(name = "filiaal")
public class FiliaalREST extends Filiaal {
	private Link link;
	
	public Filiaal toFiliaal() {
		Filiaal filiaal = new Filiaal();
		BeanUtils.copyProperties(this, filiaal);
		return filiaal;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

}
