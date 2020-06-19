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
	
	var messageParagraph = $('#messageParagraph');
	
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
					
					$('#dateCell').text(user.dateOfRegistration);
					$('#roleCell').text(user.role);
					userNameInput.val(user.userName);
					passwordInput.val(user.password);
					
					
					
					$('#updateSubmit').on('click', function(event) {
						event.preventDefault();
						var userName = userNameInput.val();
						var password = passwordInput.val();
						console.log('userName: ' + userName);
						console.log('password: ' + password);
						
						if(userName==""){
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
							params = {
								'action': 'update',
								'userName': userName,
								'password': password
							};
							console.log(params);
							$.post('UserServlet', params, function(data) {
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
									return;
								}
							});
							return false;
						}
					});
					
					
					
					$('#deleteSubmit').on('click', function(event) {
						event.preventDefault();
						var txt;
						var potvrdi = $('#potvrdi');
						var potvrdi = confirm("Da li ste sigurni da zelite da obrisete ovog korisnika?");
						if (potvrdi == true){
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
								return false;
						} else {
							txt ="Ponistili ste brisanje";
						}
						document.getElementById("poruka1").innerHTML = txt;
					});
				}
			}
			
		});
	}
	getUser();
});