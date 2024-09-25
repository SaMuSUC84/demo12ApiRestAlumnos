package es.dsw.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestParam;


import es.dsw.models.auth.AuthenticationRequest;
import es.dsw.models.auth.AuthenticationResponse;
import es.dsw.service.auth.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class MainController {
	
	@Autowired
	private AuthenticationService authService;
	
	/*
	 * Controlador de Login
	 */
	@GetMapping(value = {"/login"})
	public String login() {	
		
		

		return "login";
	}
	
	/*
	 * Contoladora Index sin parametros
	 */
	 @GetMapping(value = {"/","/home"})
	    public String home() {	    	
	   
	    	return "home";
	 } 
	 
	 /*
	  * Procesa el login y crea una cookie con el token JWT
	  */
	 @PostMapping(value = {"/authentication"})
	 public String authentication(@RequestParam(name = "username", required=true) String username,
			 					  @RequestParam(name = "password", required=true) String password,
			 					  HttpServletResponse response) {
		 
		 
		 AuthenticationRequest authRequest = new AuthenticationRequest (username,password);
		 AuthenticationResponse rsp = authService.login(authRequest);
		 boolean isAuthenticated = authService.validateToken(rsp.getJwt());
		 
		// Crear la cookie
		Cookie objCookie = new Cookie("token", rsp.getJwt());
		// La cookie expira después de 7 días.
		objCookie.setMaxAge(7 * 24 * 60 * 60);         
		// La cookie solo puede ser accedida por el servidor.
		// Si l ponemos a true, a la cookie no se podrá acceder desde el lado del cliente (Frontend, Framework como JS, JQUERY, etc..)
		objCookie.setHttpOnly(false); 
		// Definimos la ruta para la cual la cookie es válida.
		// con el path base "/" la cookie se enviará para todas las solicitudes al dominio, sin importar la ruta.
		 objCookie.setPath("/");
		// Añade la cookie al objeto response
		 response.addCookie(objCookie);
		 
		 
		 String redirect = !isAuthenticated ? "redirect:/login" : "redirect:/home";		 
		 
		 return redirect;
	 }
	 
	 
	   
	
	/*
	 * Controlador de página principal
	 */	
	 /*
    @GetMapping(value = {"/","/home"})
    public String home(//@CookieValue(value= "token", required = true)String token,
    					@RequestParam(value="auth", required = true) String token,
    					HttpServletResponse resp) {
    	
    	resp.addHeader("Authorization", "Bearer "+token);    	
    	
    	System.out.println("Mi Header desde la controladora Index: "+ resp.getHeader("Authorization"));
    	
    	String redirect = !resp.getHeader("Authorization").isEmpty() && 
    			          !token.isEmpty() ?
    			          "/home?auth="+token : "redirect:/login";
   
    	return redirect;
    }
   */
    
    
    
	/*
	 * Controlador de subvista.
	 */	
	@GetMapping(value={"/repository"}) 
	public String repository(Model objModel) { return "subviews/alumnos";}	
}
	