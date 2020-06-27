$(document).ready(function(){
//	$('#logoutLink').on('click', function(event){
//		$.get('LogoutServlet', function(data){
//			console.log(data);
//			
//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
//		});
//		event.preventDefault();
//		return false;
//	});
	
	var idProjekcija = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idProjekcija);
	
	var messageParagraph = $('#messageParagraph');
	
	function getProjekcija(){
		$.get('ProjekcijaServlet', {'idProjekcija': idProjekcija}, function(data){
			console.log(data);
			
//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
			
			var projekcija = data.projekcija;
			
			$('#userTable').show();
			$('#adminForm').hide();
			$('.unregisteredParagraph').show();
			$('#userParagraph').hide();
			
			$('#nazivFilmaImeCell').text(projekcija.film.naziv);
			$('#tipProjekcijeCell').text(projekcija.tipProjekcije.naziv);
			$('#salaCell').text(projekcija.sala.naziv);
			$('#datumPrikazivanjaCell').text(formatDate(new Date(projekcija.datumPrikazivanja)));
			$('#cenaKarCell').text(projekcija.cena);
			
			if(data.status == 'success'){
				var projekcija = data.projekcija;
				$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){
				if(data.loggedInUserRole == 'USER'){
					$('#userTable').show();
					$('#adminForm').hide();
					$('.unregisteredParagraph').hide();
					$('#userParagraph').show();
					
					$('#nazivFilmaImeCell').text(projekcija.film.naziv);
					$('#tipProjekcijeCell').text(projekcija.tipProjekcije.naziv);
					$('#salaCell').text(projekcija.sala.naziv);
					$('#datumPrikazivanjaCell').text(formatDate(new Date(projekcija.datumPrikazivanja)));
					$('#cenaKarCell').text(projekcija.cena);
					
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
					
				}else if(data.loggedInUserRole == 'ADMIN'){
					$('#userTable').hide();
					$('.unregisteredParagraph').hide();
					$('#adminForm').show();
					$('#userParagraph').show();
					
					var nazivFilmaImeCellInput = $('#nazivFilmaImeCellInput');
					var tipProjekcijeCellInput = $('#tipProjekcijeCellInput');
					var salaCellInput = $('#salaCellInput');
					var datumPrikazivanjaCellInput = $('#datumPrikazivanjaCellInput');
					var cenaKarCellInput = $('#cenaKarCellInput');
					
					nazivFilmaImeCellInput.val(projekcija.film.naziv);
					tipProjekcijeCellInput.val(projekcija.tipProjekcije.naziv);
					salaCellInput.val(projekcija.sala.naziv);
					datumPrikazivanjaCellInput.val(formatDate(new Date(projekcija.datumPrikazivanja)));
					cenaKarCellInput.val(projekcija.cena);
					
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
						var film = nazivFilmaImeCellInput.val();
						var tipProjekcije =tipProjekcijeCellInput.val();
						var sala = salaCellInput.val();
						var datum = datumPrikazivanjaCellInput.val();
						var cena = cenaKarCellInput.val();

						console.log('film: ' + film);
						console.log('tipProjekcije: ' + tipProjekcije);
						console.log('sala: ' + sala);
						console.log('datum: ' + datum);
						console.log('cena: ' + cena);
						
						if(film==""){
							$('#messageParagraph').text("Polje Naziv filma je prazno");
						}
						else if(tipProjekcije==""){
							$('#messageParagraph').text("Polje tipProjekcije je prazno");
						}
						else if(sala==""){
							$('#messageParagraph').text("Polje Sala je prazno");
						}
						else if(datum==""){
							$('#messageParagraph').text("Polje Datum je prazno");
						}
						else if(cena==""){
							$('#messageParagraph').text("Polje Cena je prazno");
						}

						else{
							params = {
								'action': 'update',
								'idProjekcija': idProjekcija,
								'film': film,
								'tipProjekcije': tipProjekcije,
								'sala': sala,
								'datum': datum,
								'cena': cena,
							};
							console.log(params);
							$.post('ProjekcijaServlet', params, function(data) {
								if (data.status == 'unauthenticated') {
									window.location.replace('ListaProjekcija.html');
									return;
								}
			
								if (data.status == 'success') {
									window.location.replace('ListaProjekcija.html');
									return;
								}
							});
							return false;
						}
					});
					
					$('#deleteSubmit').on('click', function(event) {
						console.log("sdaasdsdasdasdasda");
						event.preventDefault();
						var txt;
						var potvrdi = $('#potvrdi');
						var potvrdi = confirm("Da li ste sigurni da zelite da obrisete ovu Projekciju?");
						if (potvrdi == true){
							params = {
									'action': 'delete',
									'idProjekcija': idProjekcija 
								};
								console.log(params);
								$.post('ProjekcijaServlet', params, function(data) {
									if (data.status == 'unauthenticated') {
										window.location.replace('GlavnaStranica.html');
										return;
									}
				
									if (data.status == 'success') {
										window.location.replace('ListaProjekcija.html');
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
			}	
		});
	}
	
	function formatDate(date) {
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		var hour = date.getHours();
		var minute = date.getMinutes();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		
		return year + "-" + months[monthIndex] + "-" + day + " " + hour + ":" + minute;
	}
	getProjekcija();
});