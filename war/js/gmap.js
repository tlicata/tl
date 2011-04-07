var google, tl;

if (!tl) {
	tl = {};
}

tl.gmap = (function () {

	var init = function () {
		var latlng = new google.maps.LatLng(37.760401, -122.427521);
		var options = {
			zoom: 11,
			center: latlng,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
		var map = new google.maps.Map(document.getElementById("gmap"), options);
	};

	$(document).ready(init);

}());
