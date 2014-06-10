package be.vdab.rest;

public class Adres {
	private String straat;
	private String huisNr;
	private Integer postcode;
	private String gemeente;
	
	public String getStraat() {
		return straat;
	}
	
	public void setStraat(String straat) {
		this.straat = straat;
	}
	
	public String getHuisNr() {
		return huisNr;
	}
	
	public void setHuisNr(String huisNr) {
		this.huisNr = huisNr;
	}
	
	public Integer getPostcode() {
		return postcode;
	}
	
	public void setPostcode(Integer postcode) {
		this.postcode = postcode;
	}
	
	public String getGemeente() {
		return gemeente;
	}
	
	public void setGemeente(String gemeente) {
		this.gemeente = gemeente;
	}
}
