package be.vdab.restclient;

import java.net.URI;
import java.util.Set;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.*;
import org.springframework.web.util.UriTemplate;

import be.vdab.rest.*;

@Component
class FiliaalClientImpl implements FiliaalClient {
	private final URI filiaalServiceURI;
	private final RestTemplate restTemplate;
	private final UriTemplate filiaalURITemplate;

	@Autowired
	public FiliaalClientImpl(
			@Value("${filiaalServiceURI}") URI filiaalServiceURI,
			RestTemplate restTemplate) {
		this.filiaalServiceURI = filiaalServiceURI;
		this.restTemplate = restTemplate;
		filiaalURITemplate = new UriTemplate(filiaalServiceURI + "/{id}");
	}

	@Override
	public FiliaalListREST findAll() {
		return restTemplate.getForObject(filiaalServiceURI,
				FiliaalListREST.class);
	}

	@Override
	public FiliaalREST find(FiliaalListItemREST filiaalListItemREST) {
		try {
			return restTemplate.getForObject(filiaalListItemREST.getLink()
					.getHref(), FiliaalREST.class);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
			}
			throw ex;
		}
	}

	@Override
	public FiliaalREST read(long id) {
		URI filiaalURI = filiaalURITemplate.expand(id);
		try {
			return restTemplate.getForObject(filiaalURI, FiliaalREST.class);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				return null;
			}
			throw ex;
		}
	}

	@Override
	public void update(FiliaalREST filiaal) {
		try {
			restTemplate.put(filiaal.getLink().getHref(), filiaal.toFiliaal());
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new IllegalArgumentException("Filiaal bestaat niet meer");
			}
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new IllegalArgumentException(ex.getResponseBodyAsString());
			}
			throw ex;
		}
	}

	@Override
	public URI create(Filiaal filiaal) {
		try {
			return restTemplate.postForLocation(filiaalServiceURI, filiaal);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new IllegalArgumentException(ex.getResponseBodyAsString());
			}
			throw ex;
		}
	}

	@Override
	public void delete(long id) {
		URI filiaalURI = filiaalURITemplate.expand(id);
		try {
			restTemplate.exchange(filiaalURI, HttpMethod.DELETE, null,
					String.class);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new IllegalArgumentException("Filiaal niet gevonden");
			}
			if (ex.getStatusCode() == HttpStatus.CONFLICT) {
				throw new IllegalArgumentException(ex.getResponseBodyAsString());
			}
			throw ex;
		}
	}

	@Override
	public boolean kanVerwijderdWorden(long id) {
		URI filiaalURI = filiaalURITemplate.expand(id);
		try {
			Set<HttpMethod> httpMethods = restTemplate
					.optionsForAllow(filiaalURI);
			return httpMethods.contains(HttpMethod.DELETE);
		} catch (HttpClientErrorException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new IllegalArgumentException();
			}
			throw ex;
		}
	}
}
