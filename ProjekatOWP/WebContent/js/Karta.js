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
	
	var idKarta = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(idKarta);
	
	var messageParagraph = $('#messageParagraph');
	
	function getKarta(){
		$.get('KartaServlet', {'idKarta': idKarta}, function(data){
			console.log(data);
			
//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
			
			if(data.status == 'success'){
				var karta = data.karta;
				$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data){
					
				if(data.loggedInUserRole == 'ADMIN'){
					$('#adminForm').show();
					
					$('#projekcijaCell').text(karta.projekcija.film.naziv);
					$('#sedisteCell').text(karta.sediste.idSediste);
					$('#vremeProdajeCell').text(formatDate(new Date(karta.vremeProdaje)));
					$('#korisnikCell').text(karta.user.userName);
					$('#tipProjekcijeCell').text(karta.projekcija.tipProjekcije.naziv);
					$('#salaCell').text(karta.projekcija.sala.naziv);
					$('#cenaCell').text(karta.projekcija.cena);
					
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
					
					$('#deleteSubmit').on('click', function(event) {
						console.log("sdaasdsdasdasdasda");
						event.preventDefault();
						var txt;
						var potvrdi = $('#potvrdi');
						var potvrdi = confirm("Da li ste sigurni da zelite da obrisete ovu Kartu?");
						if (potvrdi == true){
							params = {
									'action': 'delete',
									'idKarta': idKarta
								};
								console.log(params);
								$.post('KartaServlet', params, function(data) {
									if (data.status == 'unauthenticated') {
										window.location.replace('GlavnaStranica.html');
										return;
									}
				
									if (data.status == 'success') {
										window.location.replace('ListaKarata.html');
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
	getKarta();
});