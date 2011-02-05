(function () {

	var init = function () {
		var po = org.polymaps;

		var map = po.map()
		.container(document.getElementById("pmap").appendChild(po.svg("svg")))
		.center({lat: 37.760401, lon: -122.427521})
		.zoom(8)
		.zoomRange([2, 11])
		.add(po.interact());

		map.add(po.image()
				.url("http://s3.amazonaws.com/com.modestmaps.bluemarble/{Z}-r{Y}-c{X}.jpg"));
	};

	$(document).ready(init);

})();