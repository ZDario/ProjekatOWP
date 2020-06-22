$(document).ready(function(){
	
	var filmFilterInput = $('#filmFilterInput');
	var tipProjekcijeFilterInput = $('#tipProjekcijeFilterInput');
	var salaFilterInput = $('#salaFilterInput');
	//var datumPrikazivanjaFilterInput = $('#datumPrikazivanjaFilterInput');
	var mindatvrFilterInput = $('mindatvrFilterInput');
	var maxdatvrFilterInput = $('maxdatvrFilterInput');
	var lowCenaFilterInput = $('#lowCenaFilterInput');
	var highCenaFilterInput = $('#highCenaFilterInput');
	
	var projekcijeTable = $('#projekcijeTable');
	var kupljeneKarteLink = $('#kupljeneKarteLink');
	
	var adminParagraph = $('#adminParagraph');
	var userParagraph = $('#userParagraph');
	var unregisteredParagraph = $('.unregisteredParagraph');
	//var kupikar = $('#kupikar');
	
	
	function getUnregisteredInterface(){	
		adminParagraph.hide();
		userParagraph.hide();
		unregisteredParagraph.show();
		kupljeneKarteLink.hide();
		//kupikar.parent().hide();
		
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
		$.get('ListaProjekcijaServlet', params, function(data){
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


				projekcijeTable.find('tr:gt(1)').remove();
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
							'<form>' +
								'<input type="submit" value="Kupi kartu" class="kupiKartuSubmit" idProjekcija="' + filteredProjekcije[it].idProjekcija + '">' +
							//'<a class="kupiKartuSubmit" href="" id="kupikar" projekcijaID="' + filteredProjekcije[it].id + '">Kupi kartu</a>' +// + filteredProjekcije[it].id + '">' +
							'</form>' +
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
		userParagraph.show();
		unregisteredParagraph.hide();
		kupljeneKarteLink.show();
		
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
		$('#adminParagraph').append('<a href="AddFilm.html">Dodavanje filma</a>');
		//$('#userParagraph').append('<a href="" id="logoutLink">Odjava</a>');
		adminParagraph.show();
		userParagraph.show();
		unregisteredParagraph.hide();
		kupljeneKarteLink.show();
		
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
	
	function getKupljeneKarteSize(){
		$.get('KupljenaKartaServlet' , {'action': 'size'}, function(data){
			console.log(data);
			
			
			if(data.status == 'success') {
				kupljeneKarteLink.text('Prikaz kupljenih karata (' + data.size + ')');
			}
		});
	}
	
	projekcijeTable.on('click', 'input.kupiKartuSubmit', function(event){
		
		var idProjekcija = $(this).attr('idProjekcija');
		var url = "IzaberiSediste.html?id=" + idProjekcija;
		var dugme = $('#kupikar');
		dugme.attr("href", url);
		
		params = {
			'action': 'add',
			'idProjekcija': idProjekcija
		}
		$.get('KupiKartuServlet', function(data){
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
			}
			
			if(data.status == 'success'){
				window.location.replace('IzaberiSediste.html');
			}
			getKupljeneKarteSize();
		});
		event.preventDefault();
		return false;
	});
	
	filmFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
	tipProjekcijeFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
	salaFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
	mindatvrFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
	maxdatvrFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
//	datumPrikazivanjaFilterInput.on('keyup', function(event){
//		getProjekcije();
//		
//		event.preventDefault();
//		return false;
//	});
	lowCenaFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
	highCenaFilterInput.on('keyup', function(event){
		getProjekcije();
		
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
	
	getProjekcije();
	getKupljeneKarteSize();
	getUnregisteredInterface();
	getKorisnik();
});