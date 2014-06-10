package be.vdab.rest;

import java.util.*;

import javax.xml.bind.annotation.*;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "filialen")
public class FiliaalListREST {
	@XmlElement(name = "filiaal")
	private List<FiliaalListItemREST> filialen = new ArrayList<>();
	private Link link;
	
	public List<FiliaalListItemREST> getFilialen() {
		return filialen;
	}
	
	public Link getLink() {
		return link;
	}
}
