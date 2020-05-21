$(document).ready(function() {
	
	var adminParagraph = $('#adminParagraph');
	var userParagraph = $('#userParagraph');
	var unregisteredParagraph = $('.unregisteredParagraph');
	
	function getUnregisteredInterface(){
		adminParagraph.parent().hide();
		userParagraph.parent().hide();
		unregisteredParagraph.parent().show();
	}
	
	function getUserInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);
			
			userParagraph.empty();
			if (data.status == 'success') {
				userParagraph.empty();
				if (data.loggedInUserRole == 'USER') {
					userParagraph.parent().show();
					$('#userParagraph').append('<a href="" id="logoutLink">Odjava</a>');
					unregisteredParagraph.hide();
					
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
				}
			}
		});
	}
	
	function getAdminInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);
			
			adminParagraph.empty();
			userParagraph.empty();
			if (data.status == 'success') {
				adminParagraph.empty();
				userParagraph.empty();
				if (data.loggedInUserRole == 'ADMIN') {
					adminParagraph.parent().show();
					userParagraph.parent().show();
					$('#adminParagraph').append('<a href="ListaUsera.html">Lista Korisnika</a>');
					$('#userParagraph').append('<a href="" id="logoutLink">Odjava</a>');
					unregisteredParagraph.hide();
					
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
				}
			}
		});
	}
	getUnregisteredInterface();
	getUserInterface();
	getAdminInterface();
});