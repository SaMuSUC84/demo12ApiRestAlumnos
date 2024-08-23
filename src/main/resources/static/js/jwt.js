document.addEventListener("DOMContentLoaded", () => {
	$(document).ready(()=> {
		$('body').on('click', '#boton', e =>{
			e.preventDefault();
			$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">FUNCIONA</p>');
  		});
		
		$("#loginForm").submit( e => {
			e.preventDefault();
			document.getElementById('login').disabled = true;
			const username = $("#username").val();
			const password = $("#password").val();
			
			if (!username || !password) {
				$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Por favor, ingrese su nombre de usuario y contraseña.</p>');
				document.getElementById('login').disabled = false;
				return;
			}
			$.ajax( {
				url: '/api/demo12/auth/authenticate',
				method: 'POST',				
				contentType: 'application/json',
				dataType: 'json',
				data: JSON.stringify({ username, password }),
				headers: {
					'Content-Type' : 'application/json',
					'Authorization': 'Bearer ',					
				},			
				success: res => {
					let token = res.jwt;
					console.log('Respuesta del servidor:', token);
					$('#loginForm')[0].reset();	
					document.getElementById('login').disabled = false;
					$("#prueba").html('<p style="color: green;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Iniciando sesión ... Entrando ...</p>');
					setTimeout(() => {
						$.ajax( {
							url: '/api/demo12/index',
							method: 'GET',
							headers: {
								'Content-Type' : 'application/json',
								'Authorization': 'Bearer ',					
							},
							beforeSend: xhr => {
								// Aquí es donde se añade el token al encabezado de la solicitud
								xhr.setRequestHeader('Authorization', 'Bearer ' + token);
							},
							success: () => {
								
								window.location.href = '/api/demo12/index';
							},
						});							
					},2000);
					/*
					if(res.ok) {
					// Reeseteamos la varible local para eso primero la borramos y luego la añadimos.
					// Aunque cuidado el métodpo clear() las borra todas.
					// Si fuera un entorno en el quie trabjamos con mas varibale usariamos el método
					// localStorage.removeItem("name").
					// localStorage.clear()
					// Establece el token JWT en el almacenamiento local, tambien podemos usarlo en al session con sessionStorage
					// localStorage.setItem('token', jwt);
					// Resteamos el formulario.
						$('#loginForm')[0].reset();	
						document.getElementById('login').disabled = false;
						$("#prueba").html('<p style="color: green;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Iniciando sesión ... Entrando ...</p>');
						setTimeout(()=>{
							window.location.href = '/api/demo12/index';
						},2000)
					// Redirige al usuario a la página de inicio
						
					//window.location.href = 'http://localhost:8080/api/demo12/index';
					//window.location.href = 'http://localhost:8080/api/demo12/index?token='+localStorage.getItem('token');		
					} else {
						$('#loginForm')[0].reset();	
						document.getElementById('login').disabled = false;
						$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Error al iniciar sesión</p>');
					}
					*/		
				},
				error: error => {
					console.log(`Error: ${error}`)
					$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Error en la solicitud</p>');
					document.getElementById('login').disabled = false;
				},				
			});			
		});
	});
});
/*
		$.ajax( {
			url: '/api/demo12/index',
			method: 'GET',
			headers: {
				'Content-Type' : 'application/json',
				'Authorization': 'Bearer ',					
			},
			beforeSend: xhr => {
				// Aquí es donde se añade el token al encabezado de la solicitud
				xhr.setRequestHeader('Authorization', 'Bearer ' + localStorage.getItem('token'));
			},
			success: data => {
				let jwt = data.jwt;
				console.log('Mi token: '+jwt);
			},	
			error: error => {
				console.log(`Error: ${error}`)
			},	
		});
	});
});
*/
/*
 // Fecth con ES6 JavaScript Vanilla
const loginForm =  document.querySelector('#loginForm');
const infoSpan = document.querySelector('#prueba');
loginForm?.addEventListener('submit', e =>{
	e.preventDefault();
	const username = document.querySelector('#username').value;
	const password = document.querySelector('#password').value;
	fetch('/api/demo12/auth/authenticate',{
		method: 'POST',
		headers: {
			'Content-Type' : 'application/json',
		},
		body : JSON.stringify({ username, password }),
	})
		.then(res => {
			if(res.ok) {
				infoSpan.innerText = 'Sesión iniciada ... Entrando ...';
				infoSpan.style.color = 'green';
				setTimeout(()=>{
					window.location.href = '/api/demo12/index';
				},2000)
			} else {
				infoSpan.innerText = 'Error al iniciar sesión';
				infoSpan.style.color = 'red';
			}
		})
});
*/