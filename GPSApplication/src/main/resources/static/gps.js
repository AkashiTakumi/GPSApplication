$(function(){
	$('#start_gps').click(function(){
		navigator.geolocation.watchPosition(
			function(position){
                var latitude = position.coords.latitude;
                var longitude = position.coords.longitude;
                console.log(latitude);
                console.log(longitude);
				$('#latitude').html(latitude); //緯度
				$('#longitude').html(longitude); //経度
                Cookies.set('latitude', latitude);
                Cookies.set('longitude', longitude);
			}
		);
	});
});