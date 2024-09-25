package es.dsw.config.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


import es.dsw.config.security.filter.JwtAuthenticationFilter;


import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class HttpSecurityConfig {	
	/*
	 * Configuración de nuestro Http, donde pasamos nuestros filtros
	 * y nuestra lógica a la hora de hacer nuestras peticiones.
	 * Inyectamos nuestras dependencias y una de ellas en especial 
	 * nuestro filtro JWT llamado JwtAuthenticationFilter.
	 */	
	
	@Autowired
	private AuthenticationProvider authenticationProvider;
	
	
	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		return http
				/*
				 * Configuramos los cors para las peticiones
				 * hacemos una importación static del Customizer.
				 * import static org.springframework.security.config.Customizer.withDefaults
				 */
				.cors(withDefaults())
				/*
				 * Desactivamos la protección CSRF (Cross-Site Request Forgery) 
				 * debido a que nosotros manejaremos nuestro propio token JWT.
				 */
				.csrf((csrfConfig) -> csrfConfig.disable())
				/*
				 * Establecemos la política de gestión de sesiones a STATELESS,
				 * lo que significa que el servidor no creará ni gestionará sesiones.
				 */
				.sessionManagement(sessionMagConfig -> 
					sessionMagConfig
				   	    		    .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				/*
				 * Establecemos el proveedor de autenticación.
				 */
				.authenticationProvider(authenticationProvider)
				/*
				 *  Configuramos el mapeo de nuestro propio formulario para login.
				 */
				.formLogin(form -> {
					form
				        .loginPage("/login")
				        .loginProcessingUrl("/authentication")
				        //.defaultSuccessUrl("/home", true)
				        .successHandler(successHandler())
				        .permitAll();				
				})
				.logout((logout) -> logout.logoutSuccessUrl("/login?logout").permitAll())
				/*
				 * Configuramos la autorización para las solicitudes HTTP de los endpoints.
				 */
				.authorizeHttpRequests(auth -> {
					auth
						.requestMatchers("/auth/**").permitAll()
						.requestMatchers("/authentication").permitAll()
						.requestMatchers("/error").permitAll()
						.requestMatchers("/css/**").permitAll()
						.requestMatchers("/js/**").permitAll()
						.anyRequest().authenticated();
				})
				/*
				 * Añadimos los filtros en indicamos nuestro filtro personalizado JWT 
				 * antes de uno propio que proporciona Spring Security.
				 */
				.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)									
				/*
				 *  Al final construimos y devolvemos el SecurityFilterChain.
				 */
				.build();
	}
	
	
	/*
	 * Configuración de los CORS para todos los controladores
	 */	
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
	    CorsConfiguration configuration = new CorsConfiguration();
	    // Configuramos los origenes
	    configuration.setAllowedOrigins(Arrays.asList("https://localhost:8080"));
	    //configuration.setAllowedOrigins(Arrays.asList("https://localhost:8080/api/demo12/","127.0.0.1:5500"));
	    // Configuramos los métodos Http para GET, POST,.. ponemos un * para decir a todos.
	    configuration.setAllowedMethods(Arrays.asList("*"));
	    // Configuramos los Headers.
	    //configuration.setAllowedHeaders(Arrays.asList("*"));
	    configuration.setAllowedHeaders(Arrays.asList("Authorization" , "Content-Type"));
	    // Configuramos las credenciales.
	    configuration.setAllowCredentials(true);	    
	    
	    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();	    
	    //Se deja así para dejar la configuración predeterminada a todos los controladores.
	    source.registerCorsConfiguration("/**", configuration);
	    return source;
	}
	
	
    public AuthenticationSuccessHandler successHandler() {
        return ((req, res, auth) -> {      	
        	/*
        	Cookie[] cookies = req.getCookies();
        	Cookie cookie = cookies[1];
        	String token = cookie.getValue();
        	*/
        	auth.isAuthenticated();
        	//res.addHeader("Authorization", "Bearer "+token);
        	
            res.sendRedirect("home");
        });
    }
}