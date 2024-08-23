package es.dsw.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.support.SessionStatus;


@Controller
public class MainController 
{
	
	/*
	 * Controlador de Login
	 */
	@GetMapping(value = {"/login"})
	public String login(SessionStatus status) 
	{
		status.setComplete();
		return "login";
	}
	
	/*
	 * Controlador de página principal
	 */
	@GetMapping(value = {"/index"})
	public String index(@RequestHeader (value = "Authorization") String jwt) 
	{
		if(jwt == null || jwt.isEmpty() || !jwt.startsWith("Bearer ")) 
		{
			return "redirect:/login";
		}
		
		return "index";	
	}	

	/*
	 * Controlador de subvista.
	 */
	
	@GetMapping(value={"/repository"}) 
	public String repository(Model objModel) { return "subviews/alumnos";}

	
}
	