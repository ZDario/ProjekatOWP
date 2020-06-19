$(document).ready(function() {
	
	var adminParagraph = $('#adminParagraph');
	var userParagraph = $('#userParagraph');
	var unregisteredParagraph = $('.unregisteredParagraph');
	var projekcijeTable = $('#projekcijeTable');
	
	function getUnregisteredInterface(){
		adminParagraph.parent().hide();
		userParagraph.hide();
		unregisteredParagraph.parent().show();
	}
	
	function getProjekcije(){
		$.get('GlavnaStranicaServlet', params, function(data){
			console.log("SS");

			console.log(data);
			console.log("SS");

//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
			if(data.status == 'success'){
				console.log("Success");
				console.log(data.filteredProjekcije);
				console.log(data.status);


				projekcijeTable.find('tr:gt(0)').remove();
				var filteredProjekcije = data.filteredProjekcije;
				for(it in filteredProjekcije){
					projekcijeTable.append(
						'<tr class="item" style="text-align:center;">' +
							'<td><a href="Film.html?id=' + filteredProjekcije[it].film.idFilm +
							'">' + filteredProjekcije[it].film.naziv + '</a></td>' +
							'<td>' + filteredProjekcije[it].tipProjekcije.naziv + '</td>' +
							'<td>' + filteredProjekcije[it].sala.naziv + '</td>' +
							'<td>' + formatDate(new Date(filteredProjekcije[it].datumPrikazivanja)) + '</td>' +
							'<td>' + filteredProjekcije[it].cena + '</td>' +
							'<td>' +
						'</td>' +
							/*'<td>' + 
								'<form>' +
									'<input type="text" size="3">&nbsp;' +
									'<input type="submit" value="Kupi kartu" class="kupiKartuSubmit" projekcijaID="' + filteredProjekcije[it].id + '">' +
								'</form>' +
							'</td>' +*/
						'</tr>'
					);
				}
			}
		});
	}
	
	function getUserInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);
			
			if (data.status == 'success') {
				if (data.loggedInUserRole == 'USER') {
					userParagraph.parent().show();
					unregisteredParagraph.hide();
					
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
				}
			}
		});
	}
	
	function getAdminInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);
			
			adminParagraph.empty();
			if (data.status == 'success') {
				adminParagraph.empty();
				if (data.loggedInUserRole == 'ADMIN') {
					adminParagraph.parent().show();
					userParagraph.parent().show();
					$('#adminParagraph').append('<a href="ListaUsera.html">Lista Korisnika</a>');
					unregisteredParagraph.hide();
					
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
				}
			}
		});
	}
	getUnregisteredInterface();
	getUserInterface();
	getAdminInterface();
	getProjekcije();
});