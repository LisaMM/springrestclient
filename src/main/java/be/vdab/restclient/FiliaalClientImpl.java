package be.vdab.restclient;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.*;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
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
		RestTemplate restTemplate, @Value("${filiaalServiceUsername}") String username,
		@Value("${filiaalServicePassword}") String password) {
		this.filiaalServiceURI = filiaalServiceURI;
		this.restTemplate = restTemplate;
		filiaalURITemplate = new UriTemplate(filiaalServiceURI + "/{id}");
		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new SecurityInterceptor(username, password));
		this.restTemplate.setInterceptors(interceptors);
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
	
	private static class SecurityInterceptor implements ClientHttpRequestInterceptor {
		private final String authenticatie;
		
		public SecurityInterceptor(String username, String password) {
			authenticatie = "Basic " + Base64.encodeBase64String((username +
					":" + password).getBytes(Charset.forName("UTF-8")));
		}

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body,
				ClientHttpRequestExecution execution) throws IOException {
			request.getHeaders().add("Authorization", authenticatie);
			return execution.execute(request, body);
		}
		
	}
}
