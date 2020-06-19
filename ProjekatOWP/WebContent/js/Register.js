$(document).ready(function() {
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	var repeatedPasswordInput = $('#repeatedPasswordInput');

	var messageParagraph = $('#messageParagraph');

	$('#registrationSubmit').on('click', function(event) {
		var userName = userNameInput.val();
		var password = passwordInput.val();
		var repeatedPassword = repeatedPasswordInput.val();
		console.log('userName: ' + userName);
		console.log('password: ' + password);
		console.log('repeated: ' + repeatedPassword);

		if (password != repeatedPassword) {
			messageParagraph.text('Lozinke se ne podudaraju!');
		}
		else if(userName=="" && password==""){
			$('#messageParagraph').text("Polje Korisnicko Ime i Lozinka je prazno");
			userNameInput.val('');
			passwordInput.val('');
			repeatedPasswordInput.val('');
		}
		else if(userName==""){
			$('#messageParagraph').text("Polje Korisnicko Ime je prazno");
			userNameInput.val('');
			passwordInput.val('');
			repeatedPasswordInput.val('');
		}
		else if(password==""){
			$('#messageParagraph').text("Polje Lozinka je prazno");
			userNameInput.val('');
			passwordInput.val('');
			repeatedPasswordInput.val('');
		}
		else{
			var params = {
				'userName': userName, 
				'password': password
			}
			$.post('RegisterServlet', params, function(data) {
				console.log(data);
	
				if (data.status == 'failure') {
					messageParagraph.text(data.message);
					userNameInput.val('');
					passwordInput.val('');
					repeatedPasswordInput.val('');
					
					return;
				}
				if (data.status == 'success') {
					window.location.replace('Login.html');
				}
			});
		}
		return false;
	});
});