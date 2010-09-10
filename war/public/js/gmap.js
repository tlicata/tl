(function () {

	var init = function () {
		var latlng = new google.maps.LatLng(42.938, -78.986);
		var options = {
			zoom: 8,
			center: latlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("map"), options);
	}

	$(document).ready(init);

})();