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
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(id);
	
	function getProjekcija(){
		$.get('ProjekcijaServlet', {'id': id}, function(data){
			console.log(data);
			
//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
			
			if(data.status == 'success'){
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
				
				var projekcija = data.projekcija;
				if(data.loggedInUserRole == 'USER'){
					$('#userTable').show();
					$('#adminForm').hide();
					$('.unregisteredParagraph').hide();
					$('#userParagraph').show();
					$('#userParagraph').append('<a href="" id="logoutLink">Odjava</a>');
					
					$('#nazivFilmaImeCell').text(projekcija.film.naziv);
					$('#tipProjekcijeCell').text(projekcija.tipProjekcije.naziv);
					$('#salaCell').text(projekcija.sala.naziv);
					$('#datumPrikazivanjaCell').text(formatDate(new Date(projekcija.datumPrikazivanja)));
					$('#cenaKarCell').text(projekcija.cena);
					
				}else if(data.loggedInUserRole == 'ADMIN'){
					$('#userTable').hide();
					$('.unregisteredParagraph').hide();
					$('#adminForm').show();
					$('#userParagraph').show();
					$('#userParagraph').append('<a href="" id="logoutLink">Odjava</a>');
					
					var nazivFilmaImeCellInput = $('#nazivFilmaImeCellInput');
					var tipProjekcijeCellInput = $('#tipProjekcijeCellInput');
					var salaCellInput = $('#salaCellInput');
					var datumPrikazivanjaCellInput = $('#datumPrikazivanjaCellInput');
					var cenaKarCellInput = $('#cenaKarCellInput');
				
					nazivFilmaImeCellInput.val(projekcija.film.naziv);
					tipProjekcijeCellInput.val(projekcija.tipProjekcije.naziv);
					salaCellInput .val(projekcija.sala.naziv);
					datVrCellInput.val(formatDate(new Date(projekcija.datumPrikazivanja)));
					cenaKarCellInput.val(projekcija.cena);
					
					$('deleteProjSubmit').on('click', function(event){
						params = {
							'action': 'delete',
							'idProjekcija': idProjekcija
						};
						console.log(params);
						$.post('DeleteProjekcijaServlet', params, function(data){
							if(data.status == 'unauthenticated'){
								window.location.replace('Login.html');
								return;
							}
							if(data.status == 'success'){
								window.location.replace('Projekcije.html');
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
	
	function formatDate(date) {
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		
		return year + "-" + months[monthIndex] + "-" + day;
	}
	getProjekcija();
});