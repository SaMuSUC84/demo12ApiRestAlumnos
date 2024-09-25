
// Añadir el token JWT a todas las solicitudes AJAX
$.ajaxSetup({
	beforeSend: xhr => {
		const token = getCookie('token');
		if (token) {
    		xhr.setRequestHeader('Authorization', 'Bearer ' + token);
		}
	}
}); 

// Otros métodos
$("#loginForm")?.submit( e => {
	e.preventDefault();
	document.getElementById('login').disabled = true;

	const username = $("#username").val();
	const password = $("#password").val();			

	if (!username || !password) {
		$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Por favor, ingrese su nombre de usuario y contraseña.</p>');
		document.getElementById('login').disabled = false;
		return;
	}

	login(username,password);
});

// Funcion para optener la Cookie
const getCookie = name => {
	let value = "; " + document.cookie;
	let parts = value.split("; " + name + "=");
	if (parts.length === 2) return parts.pop().split(";").shift();
};

// Función para validar el token y redirigir al index
const validateToken = token => {
	token = getCookie('token');
	
	if (!token) {
		console.error('No se encontró el token en las cookies.');
		return;
	}		
	$.ajax({
		url: `auth/validate-token?jwt=${token}`,
		method: 'GET',
		headers: {
			'Authorization': `Bearer ${token}`,
			'Content-Type': 'application/json'
		},
		success: res => {
			console.log('Token válido:', res);
			//window.location.href = 'home';
			top.location.href = 'home';
		},
		error: error => {
			console.error('Error al validar el token:', error);
		},				
		statusCode: {
			302: () => {
				window.location.reload('home');
			}
		}
	});
};

// Función Login
const login = (username, password) => {
	$.ajax( {
		url: 'auth/authenticate',
		method: 'POST',				
		contentType: 'application/json',
		dataType: 'json',
		data: JSON.stringify({ username, password }),
		headers: {
			'Content-Type' : 'application/json',					
		},	
		success: res => {
			let token = res.jwt;
			document.cookie = `token=${token}; path=/`;
			console.log('Respuesta del servidor:', token);
			console.log('Mi Cookie es: ' + getCookie('token'))					
			$('#loginForm')[0].reset();	
			document.getElementById('login').disabled = false;
			$("#prueba").html('<p style="color: green;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Iniciando sesión ... Entrando ...</p>');					
			setTimeout(() => {
				validateToken();										
			},2000);					
			// Reeseteamos la varible local para eso primero la borramos y luego la añadimos.
			// Aunque cuidado el métodpo clear() las borra todas.
			// Si fuera un entorno en el quie trabjamos con mas varibale usariamos el método
			// localStorage.removeItem("name").
			// localStorage.clear()
			// Establece el token JWT en el almacenamiento local, tambien podemos usarlo en al session con sessionStorage
			// localStorage.setItem('token', jwt);
			// Resteamos el formulario.					
			// Redirige al usuario a la página de inicio						
			//window.location.href = 'http://localhost:8080/api/demo12/index';
			//window.location.href = 'http://localhost:8080/api/demo12/index?token='+localStorage.getItem('token');		
		},
		error: error => {
			console.log(`Error: ${error}`)
			$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">Error en la solicitud</p>');
			document.getElementById('login').disabled = false;
		},
	})
}

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
			'Authorization': 'Bearer ',	
		},
		body : JSON.stringify({ username, password }),
	})
		.then(res => {
			let token = res.jwt;
			console.log('Respuesta del servidor:', token);
			if(res.ok) {
				infoSpan.innerText = 'Sesión iniciada ... Entrando ...';
				infoSpan.style.color = 'green';
				setTimeout(()=>{
					//window.location.href = '/api/demo12/index';
				},2000)
			} else {
				infoSpan.innerText = 'Error al iniciar sesión';
				infoSpan.style.color = 'red';
			}

		})
});
