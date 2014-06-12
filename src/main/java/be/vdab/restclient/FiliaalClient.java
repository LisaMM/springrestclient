package be.vdab.restclient;

import java.net.URI;

import be.vdab.rest.*;

public interface FiliaalClient {
	FiliaalListREST findAll();
	FiliaalREST find(FiliaalListItemREST filiaalListItemREST);
	FiliaalREST read(long id);
	void update(FiliaalREST filiaal);
	URI create(Filiaal filiaal);
	void delete(long id);
	boolean kanVerwijderdWorden(long id);
}
