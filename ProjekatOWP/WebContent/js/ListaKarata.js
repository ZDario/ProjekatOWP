$(document).ready(function() {
	
	var projekcijaFilterInput = $('#projekcijaFilterInput');
	var sedisteFilterInput = $('#sedisteFilterInput');
	var vremeProdajeFilterInput = $('#vremeProdajeFilterInput');
	var korisnikFilterInput = $("#korisnikFilterInput");
	
	var karteTable = $('#karteTable');
	
	var adminParagraph = $('#adminParagraph');
	
	function getKarte() {
		var projekcijaFilter = projekcijaFilterInput.val();
		var sedisteFilter = sedisteFilterInput.val();
		var vremeProdajeFilter = vremeProdajeFilterInput.val();
		var korisnikFilter = korisnikFilterInput.val();
		
		console.log('projekcijaFilter: ' + projekcijaFilter);
		console.log('sedisteFilter: ' + sedisteFilter);
		console.log('vremeProdajeFilter: ' + vremeProdajeFilter);
		console.log('korisnikFilter: ' + korisnikFilter);
		
		var params = {
				'projekcijaFilter': projekcijaFilter,
				'sedisteFilter': sedisteFilter,
				'vremeProdajeFilter': vremeProdajeFilter,
				'korisnikFilter': korisnikFilter
		};
		console.log("0000000000000000000000000000000000");
		$.get('ListaKarataServlet', params, function(data){
			console.log(data);
			console.log("0000000000000000000000000000000000");
			
			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
			
			if (data.status == 'success') {
				karteTable.find('tr:gt(1)').remove();

				var filteredKarte = data.filteredKarte;
				for (it in filteredKarte) {
					karteTable.append(
						'<tr class="item" style="text-align:center;">' + 
							'<td><a href="Karta.html?id=' + filteredKarte[it].idKarta + '">' + filteredKarte[it].projekcija.film.naziv + '</a><br />' + '</td>'+ 
							'<td>' + filteredKarte[it].sediste.idSediste + '</td>' +'<br />' +  '<td>' + formatDate(new Date(filteredKarte[it].vremeProdaje)) + '<br />' + '</td>' +
							'<td>' + filteredKarte[it].user.userName + '</td>' + 
							'</td>' + 
						'</tr>'
					)
				}
			}
		});
	}
	function getAdminInterface() {
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);

			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}

			adminParagraph.empty();
			if (data.status == 'success') {
				adminParagraph.empty();
				if (data.loggedInUserRole == 'ADMIN') {
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
	projekcijaFilterInput.on('keyup', function(event) {
		getKarte();

		event.preventDefault();
		return false;
	});
	sedisteFilterInput.on('keyup', function(event) {
		getKarte();

		event.preventDefault();
		return false;
	});
	
	vremeProdajeFilterInput.on('keyup', function(event) {
		getKarte();

		event.preventDefault();
		return false;
	});
	
	korisnikFilterInput.on('keyup', function(event) {
		getKarte();

		event.preventDefault();
		return false;
	});
	
	function formatDate(date) {
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		var hour = date.getHours();
		var minute = date.getMinutes();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		
		return year + "-" + months[monthIndex] + "-" + day + " " + hour + ":" + minute;
	}
	
	getKarte();
	getAdminInterface();
});