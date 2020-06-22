$(document).ready(function(){
	$('#logoutLink').on('click', function(event){
		$.get('LogoutServlet', function(data){
			console.log(data);
			
			if(data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
		});
		
		event.preventDefault();
		return false;
	});
	
	var karteTable = $('#karteTable');
	var totalnaCena = $('#totalnaCena');
	
	function getKupljeneKarte(){
		$.get('KupljenaKartaServlet', {'action': 'all'}, function(data){
			console.log("-------------------");
			console.log(data.kupljeneKarte.karte[0]);
			console.log("-------------------");
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				
				karteTable.find('tr:gt(1)').remove();
				
				var kartee = data.kupljeneKarte.karte;
				console.log("////////////////////");

//				console.log(data.kupljeneKarte[0]);
				
				for(i=0; i< kartee.length;i++){
					console.log('kartekarte');
					console.log(kartee[i]);
					console.log('kartekarte');
					karteTable.append(
						'<tr>' +
							'<td>' + kartee[i].projekcija.film.naziv + '</td>' +
							'<td>' + formatDate(new Date(kartee[i].projekcija.datumPrikazivanja)) + '</td>' +
							'<td>' + kartee[i].projekcija.tipPrjoekcije.naziv + '</td>' +
							'<td>' + kartee[i].sediste.sala.naziv + '</td>' +
							'<td>' + kartee[i].sediste.idSediste + '</td>' +
							'<td>' + kartee[i].projekcija.cena + '</td>' +
							'<td><form><input type="submit" value="Obrisi" class="obrisiKartuSubmit"></form></td>' +
						'</tr>'
					)
				}
			}
		});
	}
	
	karteTable.on('click', 'input.obrisiKartuSubmit', function(event){
		var row = $(this).closest('tr'); //najblizi red za input koji je proizveo događaj je onaj red koji ga obuhvata
		
		var index = row.index() -1; //zbog zaglavlja
		console.log('index:' + index);
		
		var params = {
			'action': 'remove',
			'index': index
		};
		$.post('KupljenaKartaServlet', params, function(data){
			console.log(data);
			
			if(data.status == 'unauthenticated'){
				window.location.replace('Login.html');
				return;
			}
			
			if(data.status == 'success'){
				row.remove();
				
			}
		});
		
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
	
	getKupljeneKarte();
});