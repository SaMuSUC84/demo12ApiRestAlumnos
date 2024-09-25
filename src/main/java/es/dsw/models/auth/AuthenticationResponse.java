package es.dsw.models.auth;

import java.io.Serializable;

@SuppressWarnings("serial")
public class AuthenticationResponse implements Serializable {
	
	private String jwt;	

	public AuthenticationResponse(String jwt) {
		super();
		this.jwt = jwt;
	}
	
	public AuthenticationResponse() {
		super();
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}
}
