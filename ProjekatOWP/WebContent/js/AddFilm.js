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
	
	var nazivInput = $('#nazivInput');
	var reziserInput = $('#reziserInput');
	var glumciInput = $('#glumciInput');
	var zanroviInput = $('#zanroviInput');
	var trajanjeInput = $('#trajanjeInput');
	var distributerInput = $('#distributerInput');
	var zemljaPoreklaInput = $('#zemljaPoreklaInput');
	var godinaProizvodnjeInput = $('#godinaProizvodnjeInput');
	var opisInput = $('#opisInput');
	
	var messageParagraph = $('#messageParagraph');
	
	$('#filmSubmit').on('click', function(event) {
		event.preventDefault();
		var naziv = nazivInput.val();
		var reziser = reziserInput.val();
		var glumci = glumciInput.val();
		var zanrovi = zanroviInput.val();
		var trajanje = trajanjeInput.val();
		var distributer = distributerInput.val();
		var zemljaPorekla = zemljaPoreklaInput.val();
		var godinaProizvodnje = godinaProizvodnjeInput.val();
		var opis =  opisInput.val();
		
		console.log('naziv: ' + naziv);
		console.log('reziser: ' + reziser);
		console.log('glumci: ' + glumci);
		console.log('zanrovi: ' + zanrovi);
		console.log('trajanje: ' + trajanje);
		console.log('distributer: ' + distributer);
		console.log('zemljaPorekla: ' + zemljaPorekla);
		console.log('godinaProizvodnje: ' + godinaProizvodnje);
		console.log('opis: ' + opis);
		
		if(naziv==""){
			$('#messageParagraph').text("Polje Naziv filma je prazno");
		}
		else if(reziser==""){
			$('#messageParagraph').text("Polje Reziser je prazan");
		}
		else if(glumci==""){
			$('#messageParagraph').text("Polje Glumci je prazno");
		}
		else if(zanrovi==""){
			$('#messageParagraph').text("Polje Zanrovi je prazno");
		}
		else if(trajanje==""){
			$('#messageParagraph').text("Polje Trajanje je prazno");
		}
		else if(distributer==""){
			$('#messageParagraph').text("Polje Distributer je prazno");
		}
		else if(zemljaPorekla==""){
			$('#messageParagraph').text("Polje Zemlja Porekla je prazno");
		}
		else if(godinaProizvodnje==""){
			$('#messageParagraph').text("Polje Godina Proizvodnje je prazno");
		}
		else if(opis==""){
			$('#messageParagraph').text("Polje Opis je prazno");
		}
		else{
			params = {
					'action': 'add',
					'naziv': naziv,
					'reziser': reziser,
					'glumci': glumci,
					'zanrovi': zanrovi,
					'trajanje': trajanje,
					'distributer': distributer,
					'zemljaPorekla': zemljaPorekla,
					'godinaProizvodnje': godinaProizvodnje,
					'opis': opis
				};
				$.post('FilmServlet', params, function(data) {
					
					console.log(data);

					if (data.status == 'unauthenticated') {
						window.location.replace('ListaFilmova.html');
						return;
					}

					if (data.status == 'success') {
						window.location.replace('ListaFilmova.html');
					}
				});
		}
		return false;
	});
});