package es.dsw.config.security;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import es.dsw.service.UsuarioService;


@Configuration
public class SecurityBeansInjector
{	
	/*
	 *  La anotación @Lazy puede ser una solución efectiva para romper el ciclo de dependencias  
	 *  con un problema de dependencia circular en tu aplicación Spring Boot. 
	 *  Cuando  se marca un bean como @Lazy, Spring creará una instancia de ese bean la primera vez que se solicite, 
	 *  en lugar de en el momento de la inicialización del contexto de la aplicación
	 *  
	 *  Creamos nuestros propios Beans para inyectarlos gracias a la anotación @Configuration
	 */
	
	@Autowired
	@Lazy
	private UsuarioService objUsuarioService;

	@Bean
	@Lazy
	/*
	 *  Método que crea un bean para AuthenticationManager que es 
	 *  responsable de la autenticación en Spring Security. 
	 *  Utiliza AuthenticationConfiguration para obtener el AuthenticationManager
	 */
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception 
	{
		return authenticationConfiguration.getAuthenticationManager();		
	}
	 
	/*
	@SuppressWarnings("removal")
	@Bean
	@Lazy
	 AuthenticationManager autManager(HttpSecurity http) throws Exception
	{
		return (AuthenticationManager) http
				.getSharedObject(AuthenticationManagerBuilder.class)
				.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder())
				.and()
				.build();	
	}
	*/
	
	@Bean
	@Lazy
	AuthenticationProvider authenticationProvider() 
	{
		DaoAuthenticationProvider objAuthenticationStrategy = new DaoAuthenticationProvider();
		objAuthenticationStrategy.setPasswordEncoder(passwordEncoder());
		objAuthenticationStrategy.setUserDetailsService(userDetailsService());
		
		return objAuthenticationStrategy;
				
	}

	
	/*	
	 * Otra forma de hacer el AuthenticationProvider 
	 * importamos las dependencias correspondientes de org.springframework.security.core
	 *
	@Bean
	@Lazy
	 /*
	 * Método crea un bean para AuthenticationProvider que es responsable de proporcionar 
	 * la lógica específica para la autenticación. En este caso, se crea una implementación 
	 * anónima de AuthenticationProvider que autentica a los usuarios basándose en su nombre de usuario y contraseña, 
	 * sobrescribiendo con @Override los métodos del objeto Authentication de Spring Security.
	 * Si las credenciales son correctas, se devuelve un UsernamePasswordAuthenticationToken que representa al usuario autenticado.
	 * Si las credenciales son incorrectas, se lanza una excepción BadCredentialsException.
	 * 
	AuthenticationProvider authenticationProvider(UsuarioService usuarioService) {
	    return new AuthenticationProvider() {
	        @Override
	        public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	        	String username = authentication.getName();
	            String password = authentication.getCredentials().toString();

	            UserDetails user = usuarioService.findByUsername(username);

	            if (user != null && passwordEncoder().matches(password, user.getPassword())) {
	                return new UsernamePasswordAuthenticationToken(user, password, user.getAuthorities());
	            } else {
	                throw new BadCredentialsException("Autenticación fallida");
	            }
	        }

	        @Override
	        public boolean supports(Class<?> authentication) {
	            return authentication.equals(UsernamePasswordAuthenticationToken.class);
	        }
	    };
	}
*/
	@Bean
	@Lazy
	/*
	 * Método crea un bean para PasswordEncoder que es responsable de codificar las contraseñas.  
	 * En este caso, se utiliza BCryptPasswordEncoder, que es una implementación de PasswordEncoder 
	 * que utiliza el algoritmo BCrypt para codificar las contraseñas	  
	 */
	
	PasswordEncoder passwordEncoder() 
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Lazy
	UserDetailsService userDetailsService() 
	{
		return (username) -> {
			return objUsuarioService.findByUsername(username);			
		};
	}
}