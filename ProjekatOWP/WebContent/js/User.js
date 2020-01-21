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
	
	var userName = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(userName);
	
	function getUser() {
		$.get('UserServlet', {'userName': userName}, function(data) {
			console.log(data);
			
			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
			
			if (data.status == 'success') {
				var user = data.user;
				if (data.loggedInUserRole == 'ADMIN') {
					$('#adminForm').show();
					
					var userNameInput = $('#userNameInput');
					var passwordInput = $('#passwordInput');
					
					userNameInput.val(user.userName);
					passwordInput.val(user.password);
					
					$('#updateSubmit').on('click', function(event) {
						var userName = userNameInput.val();
						var password = passwordInput.val();
						console.log('userName: ' + userName);
						console.log('password: ' + password);
						
						params = {
							'action': 'update',
							'userName': userName,
							'password': password,
							'role': role
						};
						console.log(params);
						$.post('UserServlet', params, function(data) {
							if (data.status == 'unauthenticated') {
								window.location.replace('ListaUsera.html');
								return;
							}

							if (data.status == 'success') {
								window.location.replace('ListaUsera.html');
								return;
							}
						});
						event.preventDefault();
						return false;
					});
					$('#deleteSubmit').on('click', function(event) {
						params = {
							'action': 'delete',
							'userName': userName, 
						};
						console.log(params);
						$.post('UserServlet', params, function(data) {
							if (data.status == 'unauthenticated') {
								window.location.replace('ListaUsera.html');
								return;
							}

							if (data.status == 'success') {
								window.location.replace('ListaUsera.html');
								return;
							}
						});

						event.preventDefault();
						return false;
					});
				}
			}
			
		});
	}
	getUser();
});