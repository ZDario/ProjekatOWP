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
	
	var s1 = $('#S1');
	var s2 = $('#S2');
	var s3 = $('#S3');
	var s4 = $('#S4');
	var s5 = $('#S5');
	var s6 = $('#S6');
	var s7 = $('#S7');
	var s8 = $('#S8');
	var s9 = $('#S9');
	var s10 = $('#S10');
	var s11 = $('#S11');
	var s12 = $('#S12');
	var s13 = $('#S13');
	var s14 = $('#S14');
	var s15 = $('#S15');
	var s16 = $('#S16');
	var s17 = $('#S17');
	var s18 = $('#S18');
	var s19 = $('#S19');
	var s20 = $('#S20');
	
	var id = window.location.search.slice(1).split('&')[0].split('=')[1];
	console.log(id + 'ovojeidrpoj');
	
	params = {
		'action': 'add',
		'sediste': sediste,
		'idProjekcija': idProjekcija
	}
	
	function getProjekcijaa(){
		$.get('ProjekcijaServlet', {'id': id}, function(data){
			console.log(data + 'ovojedatazaprojodjesaa');
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				var projekcija = data.projekcija;
				
				$('#nazivFilmaImeCell').text(projekcija.film.naziv);
				$('#tipProjekcijeCell').text(projekcija.tipProjekcije.naziv);
				$('#salaCell').text(projekcija.sala.naziv);
				$('#datVrCell').text(formatDate(new Date(projekcija.datumPrikazivanja)));
				$('#cenaKarCell').text(projekcija.cena);
			}	
		});
	}

	var sediste = new Array();
	$('#drugiKorakSubmit').on('click', function(event){
		$('input[name="locationthemes"]:checked').each(function() {
			sediste.push($(this).val());
			var idSediste = $(this).attr('idKarta');
			var url = "TrecaFaza.html?id=" + idKarta + "&sediste=" + idSediste;
			var dugme = $('#drugiKorakSubmit');
			dugme.attr("href", url);
			
			$.post('ProjekcijaServlet', params, function(data){
				console.log(data);
				
				if(data.status == 'unauthenticated'){
					window.location.replace('Login.html');
				}
				
				if(data.status == 'success'){
					window.location.replace('TreciKorak.html');
				}
			});
		});
		event.preventDefault();
		return false;
	});
	
	function formatDate(date) {
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		
		return year + "-" + months[monthIndex] + "-" + day;
	}
	getProjekcijaa();
});