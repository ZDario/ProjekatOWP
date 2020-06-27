$(document).ready(function() { // izvršava se nakon što se izgradi DOM stablo HTML dokumenta
	// keširanje referenci da se ne bi ponavljale pretrage kroz DOM stablo
	var userNameInput = $('#userNameInput');
	var passwordInput = $('#passwordInput');
	
	var messageParagraph = $('#messageParagraph');

	$('#loginSubmit').on('click', function(event) { // izvršava se na klik na dugme
		event.preventDefault();
		var userName = userNameInput.val();
		var password = passwordInput.val();
		console.log('userName: ' + userName);
		console.log('passwrod: ' + password);		

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
				'userName': userName, 
				'password': password
			}
			// kontrola toka se račva na 2 grane
			$.post('LoginServlet', params, function(data) { // u posebnoj programskoj niti se šalje (asinhroni) zahtev
				// tek kada stigne odgovor izvršiće se ova anonimna funkcija
				console.log('stigao odgovor!')
				console.log(data);
	
				if (data.status == 'success') {
					window.location.replace('GlavnaStranica.html');
					window.opener.location.reload(true);
					window.close();
				}
			});
			// program se odmah nastavlja dalje, pre nego što stigne odgovor
			console.log('poslat zahtev!')
			
	
			// zabraniti da browser obavi podrazumevanu akciju pri događaju
		}
		return false;
	});
});