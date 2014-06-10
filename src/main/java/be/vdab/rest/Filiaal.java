package be.vdab.rest;

import java.math.BigDecimal;
import java.sql.Date;

public class Filiaal {
	private long id;
	private String naam;
	private Adres adres;
	private boolean hoofdFiliaal;
	private BigDecimal waardeGebouw;
	private Date inGebruikName;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNaam() {
		return naam;
	}
	public void setNaam(String naam) {
		this.naam = naam;
	}
	public Adres getAdres() {
		return adres;
	}
	public void setAdres(Adres adres) {
		this.adres = adres;
	}
	public boolean isHoofdFiliaal() {
		return hoofdFiliaal;
	}
	public void setHoofdFiliaal(boolean hoofdFiliaal) {
		this.hoofdFiliaal = hoofdFiliaal;
	}
	public BigDecimal getWaardeGebouw() {
		return waardeGebouw;
	}
	public void setWaardeGebouw(BigDecimal waardeGebouw) {
		this.waardeGebouw = waardeGebouw;
	}
	public Date getInGebruikName() {
		return inGebruikName;
	}
	public void setInGebruikName(Date inGebruikName) {
		this.inGebruikName = inGebruikName;
	}
}
