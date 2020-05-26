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
	
	var nazivFilmaImeCellInput = $('#filmoviSvi');
	var tipProjekcijeCellInput = $('#projekcijeSve');
	var salaCellInput = $('#saleSve');
	var datVrCellInput = $('#datumVr');
	var cenaKarCellInput = $('#cenaKarteInput');
	
	var select = document.getElementById("filmoviSvi");
	var messageParagraph = $('#messageParagraph');
	
	$.get('ListaFilmovaServlet', function(data) {
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if (data.status == 'failure') {
			messageParagraph.text(data.message);

			return;
		}
		
		if (data.status == 'success') {

			var filteredFilmovi = data.filteredFilmovi;
			for (it in filteredFilmovi) {
				select.options[select.options.length] = new Option(filteredFilmovi[it].naziv, it);
			}
		}
	});

	var selects = document.getElementById("saleSve");
	
	$.get('ListaSalaServlet', function(data) {
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if (data.status == 'failure') {
			messageParagraph.text(data.message);

			return;
		}
		
		if (data.status == 'success') {

			var filteredSale = data.filteredSale;
			for (it in filteredSale) {
				selects.options[selects.options.length] = new Option(filteredSale[it].naziv, it);
			}
		}
	});
	$('#dodajPrSubmit').on('click', function(event){
		var nazivFilma = nazivFilmaImeCellInput.val();
		var tipProjek = tipProjekcijeCellInput.val();
		var salaPr = salaCellInput.val();
		var datumvremee = datVrCellInput.val();
		var datumvreme = formatDate(datumvremee);
		var cenazakartu = cenaKarCellInput.val();
		console.log('nazivFilma:' + nazivFilma);
		console.log('tipProjek:' + tipProjek);
		console.log('salaPr:' + salaPr);
		console.log('datumvreme:' + datumvreme);
		console.log('cenazakartu:' + cenazakartu);
		
		params = {
			'action': 'add',
			'nazivFilma': nazivFilma,
			'tipProjekc': tipProjekc,
			'salaPr': salaPr,
			'datumvreme': datumvreme,
			'cenazakartu': cenazakartu
		}
		
		console.log('-----------------------');
		
		$.post('AddProjekcijaServlet', params, function(data){
			console.log('-----------------------');
			console.log(data);
			console.log('-----------------------');
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				window.location.replace('ListaProjekcija.html');
				return;
			}
		});
		event.preventDefault();
		return false;
	});
	
	$.get('ListaFilmovaServlet', function(data) {
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if (data.status == 'failure') {
			messageParagraph.text(data.message);

			return;
		}
		
		if (data.status == 'success') {

			var filteredFilmovi = data.filteredFilmovi;
			for (it in filteredFilmovi) {
				select.options[select.options.length] = new Option(filteredFilmovi[it].naziv, it);
			}
		}
	});

	var selects = document.getElementById("saleSve");
	
	$.get('ListaSalaServlet', function(data) {
		console.log(data);
		
		if(data.status == 'unauthenticated'){
			window.location.replace('Login.html');
			return;
		}
		
		if (data.status == 'failure') {
			messageParagraph.text(data.message);

			return;
		}
		
		if (data.status == 'success') {

			var filteredSale = data.filteredSale;
			for (it in filteredSale) {
				selects.options[selects.options.length] = new Option(filteredSale[it].naziv, it);
			}
		}
	});
	
	function formatDate(date) {
		var day = date.getDate();
		var monthIndex = date.getMonth();
		var year = date.getFullYear();
		var hour = date.getHours();
		var minute = date.getMinutes();
		var sekunde = date.getSeconds();
		
		var months = ["Januar", "Februar", "Mart", "April", "Maj", "Jun", "Jul", "Avgust", "Septembar", "Oktobar", "Novembar", "Decembar"];
		
		return year + "-" + months[monthIndex] + "-" + day + " " + hour + ":" + minute + ":" + sekunde;
	}
});