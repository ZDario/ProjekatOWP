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
	
	var userNameFilterInput = $('#userNameFilterInput');
	var passwordFilterInput = $('#passwordFilterInput');
	var roleFilterInput = $('#roleFilterInput');
	var dateOfRegistrationFilterInput = $("#dateOfRegistrationFilterInput");
	
	var usersTable = $('#usersTable');
	
	var adminParagraph = $('#adminParagraph');
	
	function getUsers() {
		var userNameFilter = userNameFilterInput.val();
		var passwordFilter = passwordFilterInput.val();
		var dateOfRegistrationFilter = dateOfRegistrationFilterInput.val();
		var roleFilter = roleFilterInput.val();
		
		console.log('userNameFilter: ' + userNameFilter);
		console.log('passwordFilter: ' + passwordFilter);
		console.log('dateOfRegistrationFilter: ' + dateOfRegistrationFilter);
		console.log('roleFilter: ' + roleFilter);
		
		var params = {
				'userNameFilter': userNameFilter,
				'passwordFilter': passwordFilter,
				'dateOfRegistrationFilter': dateOfRegistrationFilter,
				'roleFilter': roleFilter
		};
		$.get('ListaUseraServlet', params, function(data){
			console.log(data);
			
			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
			
			if (data.status == 'success') {
				usersTable.find('tr:gt(0)').remove();

				var filteredUsers = data.filteredUsers;
				for (it in filteredUsers) {
					usersTable.append(
						'<tr>' + 
							'<td colspan="4"><a href="User.html?id=' + filteredUsers[it].userName + '">' +'Korisnicko Ime: '+ filteredUsers[it].userName + '</a><br />' + 
							'Lozinka: ' + filteredUsers[it].password + '<br />' + 'Datum Registracije: ' +  filteredUsers[it].dateOfRegistration + '<br />' +
							'Uloga: ' + filteredUsers[it].role + 
							'</td>' + 
						'</tr>'
					)
				}
			}
		});
	}
	function getAdminInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);

			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}

			adminParagraph.empty();
			if (data.status == 'success') {
				adminParagraph.empty();
				if (data.loggedInUserRole == 'ADMIN') {
					$('#adminParagraph').append('<a href="AddUser.html">Dodavanje korisnika</a>');
				}
			}
		});
	}
	userNameFilterInput.on('keyup', function(event) {
		getUsers();

		event.preventDefault();
		return false;
	});
	passwordFilterInput.on('keyup', function(event) {
		getUsers();

		event.preventDefault();
		return false;
	});
	
	dateOfRegistrationFilterInput.on('keyup', function(event) {
		getUsers();

		event.preventDefault();
		return false;
	});
	
	roleFilterInput.on('keyup', function(event) {
		getUsers();

		event.preventDefault();
		return false;
	});
	getUsers();
	getAdminInterface();
});