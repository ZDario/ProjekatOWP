$(document).ready(function() {

	var idFilm = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idFilm);
	
	var messageParagraph = $('#messageParagraph');
	

	function getFilm() {
		$.get('FilmServlet', {'idFilm': idFilm}, function(data) {
			console.log(data);
			
//			if (data.status == 'unauthenticated') {
//				window.location.replace('Login.html');
//				return;
//			}
			
			if (data.status == 'success') {
				var film = data.film;
				$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){
				if (data.loggedInUserRole == 'USER') {
//					adminForm.empty();
					$('#userTable').show();
					$('#adminForm').hide();
					$('.unregisteredParagraph').hide();
					$('#userParagraph').show();
					
					$('#nazivCell').text(film.naziv);
					$('#reziserCell').text(film.reziser);
					$('#glumciCell').text(film.glumci);
					$('#zanroviCell').text(film.zanrovi);
					$('#trajanjeCell').text(film.trajanje);
					$('#distributerCell').text(film.distributer);
					$('#zemljaPoreklaCell').text(film.zemljaPorekla);
					$('#godinaProizvodnjeCell').text(film.godinaProizvodnje);
					$('#opisCell').text(film.opis);
				
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
				
				} else if (data.loggedInUserRole == 'ADMIN') {
//					userTable.empty();
//					unregisteredParagraph.empty();
					$('#userTable').hide();
					$('.unregisteredParagraph').hide();
					$('#adminForm').show();
					$('#userParagraph').show();
					
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
				
					$('#updateSubmit').on('click', function(event) {
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
							return false;
						}
					});
					$('#deleteSubmit').on('click', function(event) {
						event.preventDefault();
						var txt;
						var potvrdi = $('#potvrdi');
						var potvrdi = confirm("Da li ste sigurni da zelite da obrisete ovaj film?");
						if (potvrdi == true){
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
								return false;
						} else {
							txt ="Ponistili ste brisanje";
						}
						document.getElementById("poruka1").innerHTML = txt;
					});
				}
				});
			} else{
				var film = data.film; 
				
				$('#userTable').show();
				$('#adminForm').hide();
				$('.unregisteredParagraph').show();
				$('#userParagraph').hide();
				
				$('#nazivCell').text(film.naziv);
				$('#reziserCell').text(film.reziser);
				$('#glumciCell').text(film.glumci);
				$('#zanroviCell').text(film.zanrovi);
				$('#trajanjeCell').text(film.trajanje);
				$('#distributerCell').text(film.distributer);
				$('#zemljaPoreklaCell').text(film.zemljaPorekla);
				$('#godinaProizvodnjeCell').text(film.godinaProizvodnje);
				$('#opisCell').text(film.opis);
			}
		});
	}
	getFilm();
});