$(document).ready(function() {
	
	var filmFilterInput = $('#filmFilterInput');
	var tipProjekcijeFilterInput = $('#tipProjekcijeFilterInput');
	var salaFilterInput = $('#salaFilterInput');
	//var datumPrikazivanjaFilterInput = $('#datumPrikazivanjaFilterInput');
	var mindatvrFilterInput = $('mindatvrFilterInput');
	var maxdatvrFilterInput = $('maxdatvrFilterInput');
	var lowCenaFilterInput = $('#lowCenaFilterInput');
	var highCenaFilterInput = $('#highCenaFilterInput');
	
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
		var film = filmFilterInput.val();
		var tipProjekcije = tipProjekcijeFilterInput.val();
		var sala = salaFilterInput.val();
		var mindatvr = mindatvrFilterInput.val();
		var maxdatvr = maxdatvrFilterInput.val();
		//var datumPrikazivanja = datumPrikazivanjaFilterInput.val();
		var lowCena = lowCenaFilterInput.val();
		var highCena = highCenaFilterInput.val();
		console.log('film:' + film);
		console.log('tipProjekcije:' + tipProjekcije);
		console.log('sala:' + sala);
		console.log('mindatvr:' + mindatvr);
		console.log('maxdatvr:' + maxdatvr);
		console.log('lowCena:' + lowCena);
		console.log('highCena:' + highCena);
		
		var params = {
			'film': film,
			'tipProjekcije': tipProjekcije,
			'sala': sala,
			'mindatvr':mindatvr,
			'maxdatvr':maxdatvr,
			//'datumPrikazivanja': datumPrikazivanja,
			'lowCena': lowCena,
			'highCena': highCena
		};
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
				//console.log(data.filteredProjekcije);
				console.log(data.danasnjeProjekcije);
				console.log(data.status);


				projekcijeTable.find('tr:gt(0)').remove();
				var danasnjeProjekcije = data.danasnjeProjekcije;
				for(it in danasnjeProjekcije){
					projekcijeTable.append(
						'<tr class="item" style="text-align:left; ">' +
							'<td colspan="3"><a id="naziv" href="Projekcija.html?id=' + danasnjeProjekcije[it].idProjekcija +
							'">' + danasnjeProjekcije[it].film.naziv + '</a><br />' +
							"Tip Projekcije:  " + '<a class="parametri">' + danasnjeProjekcije[it].tipProjekcije.naziv + '</a><br />' +
							"Sala:  " + '<a class="parametri">' +danasnjeProjekcije[it].sala.naziv + '</a><br />' + 
							"Datum Prikazivanja:  " + '<a class="parametri">' +formatDate(new Date(danasnjeProjekcije[it].datumPrikazivanja)) + '</a><br />' +
							"Cena:  " + '<a class="parametri">' +danasnjeProjekcije[it].cena + '</a>' + 
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
		userParagraph.show();
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
	
	function getAdminInterface() {
		adminParagraph.parent().show();
		userParagraph.show();
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
	
	function getKorisnik(){
		$.get('UserServlet', {'action': 'loggedInUserRole'}, function(data) {
			console.log(data);
			if (data.status == 'success') {
				if (data.loggedInUserRole == 'USER'){
					getUserInterface();
				}
				else if (data.loggedInUserRole == 'ADMIN'){
					getAdminInterface();
				}
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
	
	getUnregisteredInterface();
	getKorisnik();
	getProjekcije();
});