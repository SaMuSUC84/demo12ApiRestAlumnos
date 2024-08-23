document.addEventListener("DOMContentLoaded", () => 
{
	$(document).ready(()=>
	{
		$('body').on('click', '#boton', event =>
		{
			event.preventDefault();
			$("#prueba").html('<p style="color: red;text-align: left; font-family: "Lato", sans-serif; font-size: 40px;">FUNCIONA</p>');
  		});

		$("#loginForm").submit( event => 
		{
			event.preventDefault();
			document.getElementById('login').disabled = true;

			let username = $("#username").val();
			let password = $("#password").val();

			$.ajax(
			{
				url: 'http://localhost:8080/api/demo12/auth/authenticate',
				type: 'POST',
				data: JSON.stringify({username,password}),
				contentType: 'application/json',
				dataType: "json",
				headers: {
					'Content-Type' : 'application/json',
					'Authorization': 'Bearer ',					
				},
				success: data => {
					let jwt = data.jwt;
					console.log('Mi token: '+jwt);
					// Reeseteamos la varible local para eso primero la borramos y luego la añadimos.
					// Aunque cuidado el métodpo clear() las borra todas.
					// Si fuera un entorno en el quie trabjamos con mas varibale usariamos el método
					// localStorage.removeItem("name").
					localStorage.clear()
					// Establece el token JWT en el almacenamiento local, tambien podemos usarlo en al session con sessionStorage
					localStorage.setItem('token', jwt);
					// Resteamos el formulario.
					$('#loginForm')[0].reset();	
					document.getElementById('login').disabled = false;
					// Redirige al usuario a la página de inicio
					//window.location.href = 'http://localhost:8080/api/demo12/index';
					//window.location.href = 'http://localhost:8080/api/demo12/index?token='+localStorage.getItem('token');				
				},
				error: error =>{
					console.log(`Error: ${error}`)
				},				
			});			
		});

		$.ajax(
			{
			url: 'http://localhost:8080/api/demo12/index',
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
			error: error =>{
				console.log(`Error: ${error}`)
			},	
		});
	});
});


