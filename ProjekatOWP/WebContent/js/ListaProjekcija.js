$(document).ready(function(){
	
	var filmFilterInput = $('#filmFilterInput');
	var tipProjekcijeFilterInput = $('#tipProjekcijeFilterInput');
	var salaFilterInput = $('#salaFilterInput');
	var datumPrikazivanjaFilterInput = $('#datumPrikazivanjaFilterInput');
//	var mindatvrFilterInput = $('mindatvrFilterInput');
//	var maxdatvrFilterInput = $('maxdatvrFilterInput');
	var lowCenaFilterInput = $('#lowCenaFilterInput');
	var highCenaFilterInput = $('#highCenaFilterInput');
	
	var projekcijeTable = $('#projekcijeTable');
	var kupljeneKarteLink = $('#kupljeneKarteLink');
	
	var adminParagraph = $('#adminParagraph');
	var adminnParagraph = $('#adminnParagraph');
	var userParagraph = $('#userParagraph');
	var unregisteredParagraph = $('.unregisteredParagraph');
	//var kupikar = $('#kupikar');
	
	
	function getUnregisteredInterface(){	
		adminParagraph.hide();
		adminnParagraph.hide();
		userParagraph.hide();
		unregisteredParagraph.show();
		kupljeneKarteLink.hide();
		//kupikar.parent().hide();
		
	}
	
	function getProjekcije(){
		var filmFilter = filmFilterInput.val();
		var tipProjekcijeFilter = tipProjekcijeFilterInput.val();
		var salaFilter = salaFilterInput.val();
//		var mindatvrFilter = mindatvrFilterInput.val();
//		var maxdatvrFilter = maxdatvrFilterInput.val();
		var datumPrikazivanjaFilter = datumPrikazivanjaFilterInput.val();
		var lowCenaFilter = lowCenaFilterInput.val();
		var highCenaFilter = highCenaFilterInput.val();
		console.log('filmFilter:' + filmFilter);
		console.log('tipProjekcijeFilter:' + tipProjekcijeFilter);
		console.log('salaFilter:' + salaFilter);
		console.log('datumPrikazivanjaFilter:' + datumPrikazivanjaFilter);
//		console.log('mindatvrFilter:' + mindatvrFilter);
//		console.log('maxdatvrFilter:' + maxdatvrFilter);
		console.log('lowCenaFilter:' + lowCenaFilter);
		console.log('highCenaFilter:' + highCenaFilter);
		
		var params = {
			'filmFilter': filmFilter,
			'tipProjekcijeFilter': tipProjekcijeFilter,
			'salaFilter': salaFilter,
//			'mindatvrFilter': mindatvrFilter,
//			'maxdatvrFilter': maxdatvrFilter,
			'datumPrikazivanjaFilter': datumPrikazivanjaFilter,
			'lowCenaFilter': lowCenaFilter,
			'highCenaFilter': highCenaFilter
		};
		$.get('ListaProjekcijaServlet', params, function(data){
			console.log(data);

//			if(data.status == 'unauthenticated'){
//				window.location.replace('Login.html');
//				return;
//			}
			if(data.status == 'success'){
				projekcijeTable.find('tr:gt(1)').remove();
				
				var filteredProjekcije = data.filteredProjekcije;
				for(it in filteredProjekcije){
					projekcijeTable.append(
						'<tr class="item" style="text-align:center;">' +
							'<td><a href="Projekcija.html?id=' + filteredProjekcije[it].idProjekcija +
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
		adminnParagraph.hide();
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
		adminParagraph.show();
		adminnParagraph.show();
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
		var dugme = $('.kupiKartuSubmit');
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
//	mindatvrFilterInput.on('keyup', function(event){
//		getProjekcije();
//		
//		event.preventDefault();
//		return false;
//	});
//	maxdatvrFilterInput.on('keyup', function(event){
//		getProjekcije();
//		
//		event.preventDefault();
//		return false;
//	});
	datumPrikazivanjaFilterInput.on('keyup', function(event){
		getProjekcije();
		
		event.preventDefault();
		return false;
	});
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