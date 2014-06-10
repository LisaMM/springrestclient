package be.vdab.rest;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
public class FiliaalListItemREST {
	@XmlAttribute
	private long id;
	@XmlAttribute
	private String naam;
	private Link link;
	
	public long getId() {
		return id;
	}
	public String getNaam() {
		return naam;
	}
	public Link getLink() {
		return link;
	}
}
