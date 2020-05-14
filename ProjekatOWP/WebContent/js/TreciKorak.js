$(document).ready(function(){
	$('#logoutLink').on('click', function(event){
		$.get('LogoutServlet', function(data){
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
		});
		event.preventDefault();
		return false;
	});
	var nazivFilmaImeCell = $('#nazivFilmaImeeCell');
	var tipProjekcijeCell = $('#tipProjekcijeeCell');
	var salaCell = $('#salaaCell');
	var datVrCell = $('#datVrrCell');
	var cenaKarCell = $('#cenaKarrCell');
	var sedisteKarCell = $('#sedisteKarrCell');
	
	var kartaaTable = $('#kartaaTable');
	var konacnoKupiKartu = $('#konacnoKupiKartu');
	
	var id = window.location.search.split('&')[0].split('=')[1];
	var idSjedista = window.location.search.split('&')[1].split('=')[1];
	console.log(idSedista + '  ovojeidsedista');
	console.log(idProjekcija + '  ovojeidproj');
	
	function getProjekcijaa(){
		$.get('ProjekcijaServlet', {'idProjekcija': idProjekcija}, function(data){
			console.log(data + 'ovojedatazaprojodjesaa');
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				var projekcija = data.projekcija;
				console.log(projekcija + 'projekcija');
				var sediste = idSedista;
				$('#nazivFilmaImeeCell').text(projekcija.film.naziv);
				$('#tipProjekcijeeCell').text(projekcija.tipProjekcije.naziv);
				$('#salaaCell').text(projekcija.sala.naziv);
				$('#datVrrCell').text(formatDate(new Date(projekcija.datumPrikazivanja)));
				$('#cenaKarrCell').text(projekcija.cenaKarte);
				$('#sedisteKarrCell').text(sediste);
				konacnoKupiKartu.on('click', function(event){
					var idProjekcijaa = idProjekcija;
					var nazivfilmaa = nazivFilmaImeCell.text();
					alert(nazivFilmaImeCell.text());
					var tipProjekcijee = tipProjekcijeCell.text();
					var salaaa = salaCell.text();
					var datVremee = datVrCell.text();
					var cenaKartee = cenaKarCell.text();
					var sedisteKartee = sedisteKarCell.text();
					console.log('idProjekcijaa: ' + idProjekcijaa);
					console.log('nazivfilmaa: ' + nazivfilmaa);
					console.log('tipProjekcijee: ' + tipProjekcijee);
					console.log('salaaa: ' + salaaa);
					console.log('datVremee: ' + datVremee);
					console.log('cenaKartee: ' + cenaKartee);
					console.log('sedisteKartee: ' + sedisteKartee);
					params = {
						'action': 'add',
						'idProjekcija': idProjekcija,
						'nazivFilmaa': nazivfilmaa,
						'tipProjekcijee': tipProjekcijee,
						'salaaa': salaaa,
						'datVremee': datVremee,
						'cenaKartee': cenaKartee,
						'sedisteKartee':sedisteKartee
					}
					$.post('KupiKartuServlet', params, function(data){
						console.log(data);
						
						if(data.status == 'unauthenticated'){
							window.location.replace('Login.html');
							return;
						}
						
						if(data.status == 'success'){
							alert('karta uspjesno kupljena');
						}
					});
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
		var sekunde = date.getSeconds();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		//var months = [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12];
		
		return year + "-" + months[monthIndex] + "-" + day + " " + hour + ":" + minute + ":" + sekunde;
	}
	getProjekcijaa();
});