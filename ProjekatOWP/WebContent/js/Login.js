$(document).ready(function() { // izvršava se nakon što se izgradi DOM stablo HTML dokumenta
	// keširanje referenci da se ne bi ponavljale pretrage kroz DOM stablo
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');

	$('#loginSubmit').on('click', function(event) { // izvršava se na klik na dugme
		var userName = userNameInput.val();
		var password = passwordInput.val();
		console.log('userName: ' + userName);
		console.log('passwrod: ' + password);		

		var params = {
			'userName': userName, 
			'password': password
		}
		// kontrola toka se račva na 2 grane
		$.post('LoginServlet', params, function(data) { // u posebnoj programskoj niti se šalje (asinhroni) zahtev
			// tek kada stigne odgovor izvršiće se ova anonimna funkcija
			console.log('stigao odgovor!')
			console.log(data);

			if (data.status == 'failure') {
				userNameInput.val('');
				passwordInput.val('');

				return;
			}
			if (data.status == 'success') {
				window.location.replace('WebShop.html');
			}
		});
		// program se odmah nastavlja dalje, pre nego što stigne odgovor
		console.log('poslat zahtev!')

		// zabraniti da browser obavi podrazumevanu akciju pri događaju
		event.preventDefault();
		return false;
	});
});