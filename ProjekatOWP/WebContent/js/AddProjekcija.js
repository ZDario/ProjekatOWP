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
			console.log("aaaaa");
			messageParagraph.text(data.message);

			return;
		}
		if (data.status == 'success') {
			var filteredFilmovi = data.filteredFilms;
			
			for (it in filteredFilmovi) {
				var it = parseInt(it);
				select.options[select.options.length] = new Option(filteredFilmovi[it].naziv, it+1);
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
				var it = parseInt(it);
				selects.options[selects.options.length] = new Option(filteredSale[it].naziv, it+1);
			}
		}
	});
	$('#dodajPrSubmit').on('click', function(event){
		event.preventDefault();
		var nazivFilma = nazivFilmaImeCellInput.val();
		var tipProjek = tipProjekcijeCellInput.val();
		var salaPr = salaCellInput.val();
		var datumvreme = datVrCellInput.val();
		//var datumvreme = formatDate(datumvremee);
		var cenazakartu = cenaKarCellInput.val();
		console.log('nazivFilma:' + nazivFilma);
		console.log('tipProjek:' + tipProjek);
		console.log('salaPr:' + salaPr);
		console.log('datumvreme:' + datumvreme);
		console.log('cenazakartu:' + cenazakartu);
		
		var validTime = datVrCellInput.val().match(/^(\d{4})\-(\d{2})\-(\d{2}) (\d{2}):(\d{2})$/);
		
		if(datumvreme==""){
			$('#messageParagraph').text("Polje Datum je prazno");
		}
		else if (!validTime) {
			$('#messageParagraph').text("Polje Datum nije ispravno napisano");
		}
		else if(cenazakartu==""){
			$('#messageParagraph').text("Polje Cena je prazno");
		}
		else{
			params = {
				'action': 'add',
				'nazivFilma': nazivFilma,
				'tipProjek': tipProjek,
				'salaPr': salaPr,
				'datumvreme': datumvreme,
				'cenazakartu': cenazakartu
			}
			
			console.log('-----------------------');
			
			$.post('ProjekcijaServlet', params, function(data){
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
		}
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
});