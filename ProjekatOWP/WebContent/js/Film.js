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

	var idFilm = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idFilm);
	
	function getFilm() {
		$.get('FilmServlet', {'idFilm': idFilm}, function(data) {
			console.log(data);
			
			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
			
			if (data.status == 'success') {
				var film = data.film;
				if(data.loggedInUserRole == 'USER') {
					$('#userTable').show();
					$('#adminForm').hide();
					
					$('#nazivCell').text(film.naziv);
					$('#reziserCell').text(film.reziser);
					$('#glumciCell').text(film.glumci);
					$('#zanroviCell').text(film.zanrovi);
					$('#trajanjeCell').text(film.trajanje);
					$('#distributerCell').text(film.distributer);
					$('#zemljaPoreklaCell').text(film.zemljaPorekla);
					$('#godinaProizvodnjeCell').text(film.godinaProizvodnje);
					$('#opisCell').text(film.opis);
					
				} else if (data.loggedInUserRole == 'ADMIN') {
					$('#userTable').hide()
					$('#adminForm').show();
					
					var nazivInput = $('#nazivInput');
					var reziserInput = $('#reziserInput');
					var glumciInput = $('#glumciInput');
					var zanroviInput = $('#zanroviInput');
					var trajanjeInput = $('#trajanjeInput');
					var distributerInput = $('#distributerInput');
					var zemljaPoreklaInput = $('#zemljaPoreklaInput');
					var godinaProizvodnjeInput = $('#godinaProizvodnjeInput');
					var opisInput = $('#opisInput');
					
					nazivInput.val(film.naziv);
					reziserInput.val(film.reziser);
					glumciInput.val(film.glumci);
					zanroviInput.val(film.zanrovi);
					trajanjeInput.val(film.trajanje);
					distributerInput.val(film.distributer);
					zemljaPoreklaInput.val(film.zemljaPorekla);
					godinaProizvodnjeInput.val(film.godinaProizvodnje);
					opisInput.val(film.opis);
					
					$('#updateSubmit').on('click', function(event) {
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
						
						params = {
							'action': 'update',
							'idFilm': idFilm,
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
						console.log(params);
						$.post('FilmServlet', params, function(data) {
							if (data.status == 'unauthenticated') {
								window.location.replace('ListaFilmova.html');
								return;
							}

							if (data.status == 'success') {
								window.location.replace('ListaFilmova.html');
								return;
							}
						});
						event.preventDefault();
						return false;
					});
					$('#deleteSubmit').on('click', function(event) {
						params = {
							'action': 'delete',
							'idFilm': idFilm, 
						};
						console.log(params);
						$.post('FilmServlet', params, function(data) {
							if (data.status == 'unauthenticated') {
								window.location.replace('ListaFilmova.html');
								return;
							}

							if (data.status == 'success') {
								window.location.replace('ListaFilmova.html');
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
	getFilm();
});