(function () {

	var init = function () {
		var po = org.polymaps;

		var map = po.map()
		.container(document.getElementById("map").appendChild(po.svg("svg")))
		.center({lat: 42.938, lon: -78.986})
		.zoom(8)
		.zoomRange([2, 11])
		.add(po.interact());

		map.add(po.image()
				.url("http://s3.amazonaws.com/com.modestmaps.bluemarble/{Z}-r{Y}-c{X}.jpg"));
	};

	$(document).ready(init);

})();