$(document).ready(function() {
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
	
	var nazivFilterInput = $('#nazivFilterInput');
	var zanroviFilterInput = $('#zanroviFilterInput');
	var distributerFilterInput = $('#distributerFilterInput');
	var zemljaPoreklaFilterInput = $('#zemljaPoreklaFilterInput');
	
	var lowDurationFilterInput = $('#lowDurationFilterInput');
	var highDurationFilterInput = $('#highDurationFilterInput');
	var lowYearOfProductionFilterInput = $('#lowYearOfProductionnFilterInput');
	var highYearOfProductionFilterInput = $('#highYearOfProductionFilterInput');
	
	var filmsTable = $('#filmsTable');
	
	var adminParagraph = $('#adminParagraph');
	
	function getFilms() {
		var nazivFilter = nazivFilterInput.val();
		var zanroviFilter = zanroviFilterInput.val();
		var lowDurationFilter = lowDurationFilterInput.val();
		var highDurationFilter = highDurationFilterInput.val();
		var distributerFilter = distributerFilterInput.val();
		var zemljaPoreklaFilter = zemljaPoreklaFilterInput.val();
		var lowYearOfProductionFilter = lowYearOfProductionFilterInput.val();
		var highYearOfProductionFilter = highYearOfProductionFilterInput.val();
		console.log('nazivFilter: ' + nazivFilter);
		console.log('zanroviFilter: ' + zanroviFilter);
		console.log('lowDurationFilter: ' + lowDurationFilter);
		console.log('highDurationFilter: ' + highDurationFilter);
		console.log('distributerFilter: ' + distributerFilter);
		console.log('zemljaPoreklaFilter: ' + zemljaPoreklaFilter);
		console.log('lowYearOfProductionFilter: ' + lowYearOfProductionFilter);
		console.log('highYearOfProductionFilter: ' + highYearOfProductionFilter);
		
		var params = {
				'nazivFilter': nazivFilter,
				'zanroviFilter': zanroviFilter,
				'lowDurationFilter': lowDurationFilter,
				'highDurationFilter': highDurationFilter,
				'distributerFilter': distributerFilter,
				'zemljaPoreklaFilter': zemljaPoreklaFilter,
				'lowYearOfProductionFilter': lowYearOfProductionFilter,
				'highYearOfProductionFilter': highYearOfProductionFilter
		};
		$.get('ListaFilmovaServlet', params, function(data){
			console.log(data);
			
			if (data.status == 'unauthenticated') {
				window.location.replace('Login.html');
				return;
			}
			
			if (data.status == 'success') {
				filmsTable.find('tr:gt(1)').remove();

				var filteredFilms = data.filteredFilms;
				for (it in filteredFilms) {
					filmsTable.append(
						'<tr>' + 
							'<td><a href="Film.html?id=' + filteredFilms[it].idFilm + '">' + filteredFilms[it].naziv + '</a></td>' + 
							'<td>' + filteredFilms[it].zanrovi + '</td>' + '<td>' + filteredFilms[it].trajanje + '</td>' + 
							'<td>' + filteredFilms[it].distributer + '</td>' + '<td>' + filteredFilms[it].zemljaPorekla + '</td>' + 
							'<td>' + filteredFilms[it].godinaProizvodnje + '</td>' + 
							'<td>' + 
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
					$('#adminParagraph').append('<a href="AddFilm.html">Dodavanje filma</a>');
				}
			}
		});
	}
	nazivFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	zanroviFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	lowDurationFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	highDurationFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	distributerFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	zemljaPoreklaFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});

	lowYearOfProductionFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});
	highYearOfProductionFilterInput.on('keyup', function(event) {
		getFilms();

		event.preventDefault();
		return false;
	});

	// lista proizvoda se osvežava jednom na početku, nakon učitavanja HTML dokumenta
	getFilms();
	getAdminInterface();
});