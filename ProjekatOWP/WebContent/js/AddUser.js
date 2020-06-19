$(document).ready(function() {
	$('#logoutLink').on('click', function(event) {
		$.get('LogoutServlet', function(data) {
			console.log(data);

			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
		});
	
		event.preventDefault();
		return false;
	});
	
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	var roleSelect = $('#roleSelect');
	
	var messageParagraph = $('#messageParagraph');
	
	$('#userSubmit').on('click', function(event) {
		event.preventDefault();
		var userName = userNameInput.val();
		var password = passwordInput.val();
		var role = roleSelect.val();
		
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		console.log('role: ' + role);
		
		if(userName=="" && password==""){
			$('#messageParagraph').text("Polje Korisnicko Ime i Lozinke je prazno");
			userNameInput.val('');
			passwordInput.val('');
		}
		else if(userName==""){
			$('#messageParagraph').text("Polje Korisnicko Ime je prazno");
			userNameInput.val('');
			passwordInput.val('');
		}
		else if(password==""){
			$('#messageParagraph').text("Polje Lozinka je prazno");
			userNameInput.val('');
			passwordInput.val('');
		}
		else{
			var params = {
					'action': 'add',
					'userName': userName,
					'password': password,
					'role': role
			};
			$.post('UserServlet', params, function(data) {
				console.log(data);
	
				if (data.status == 'unauthenticated') {
					window.location.replace('ListaUsera.html');
					return;
				}
				
				if (data.status == 'failure') {
					messageParagraph.text(data.message);
					userNameInput.val('');
					passwordInput.val('');
	
					return;
				}
	
				if (data.status == 'success') {
					window.location.replace('ListaUsera.html');
				}
			});
		}
		return false;
	});
});