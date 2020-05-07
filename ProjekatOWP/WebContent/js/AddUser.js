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
		var userName = userNameInput.val();
		var password = passwordInput.val();
		var role = roleSelect.val();
		
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		console.log('role: ' + role);
		
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
		event.preventDefault();
		return false;
	});
});