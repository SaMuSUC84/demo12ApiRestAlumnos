$(document).ready(()=> {
	/*
	$("#loginForm")?.submit( e => {
		e.preventDefault();
		$('#login').attr("disabled", true);

		const username = $("#username").val();
		const password = $("#password").val();
		const AUTH_URL = '/api/demo12/auth/authenticate';			

		if (!username || !password) {
			$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Por favor, ingrese su nombre de usuario y contraseña.</p>');
			$('#login').attr("disabled", false);
			return;
		}

		$.ajax( {
			url: AUTH_URL,
			method: 'POST',				
			contentType: 'application/json',
			dataType: 'json',
			data: JSON.stringify({ username, password }),
			headers: {
				'Content-Type' : 'application/json',					
			},	
			success: res => {					
				console.log(`Respuesta del servidor: ${res.jwt}`);	
				$('#loginForm')[0].reset();	
				$('#login').attr("disabled", false);
				$("#prueba").html('<p style="color: green;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Iniciando sesión ... Entrando ...</p>');	
				// Almacenar el token JWT en una cookie
                document.cookie = `token=${res.jwt}; path=/; secure`;					
			},
			error: error => {
				console.log(`Error: ${error}`)
				$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Error en la solicitud</p>');
				$('#login').attr("disabled", false);
			}	
		}); 
		// Función para leer la Cookie
		// devuelve la cookie con el nombre dado,
		// o undefined si no la encuentra
		const getCookie = name => {
			let matches = document.cookie.match(new RegExp(
			"(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
			));
			let cookieValue = matches ? decodeURIComponent(matches[1]) : undefined;

			console.log(`La Cookie se lee bien ${cookieValue}`)

			return cookieValue;
		};
    	// Realizar una solicitud GET a la página principal con el token JWT
		$.ajax({
			url: '/api/demo12/home',
			method: 'GET',
			beforeSend: xhr =>{
				const token = getCookie('token');
				xhr.setRequestHeader('Authorization', 'Bearer ' + token);
			},
			success: () => {
				console.log('Página principal cargada con éxito');
					
			},
			error: error => {
				console.log(`Error al cargar la página principal: ${error}`);
			}
		});		
	});	
	*/
});