if (!tl) {
	var tl = {};
}

tl.polymaps = (function () {

	var po = org.polymaps;

	var insertMap = function () {
		var container = $("#polymaps-container")[0];
		var map = po.map()
		.container(container.appendChild(po.svg("svg")))
		.center({lat: 42.938, lon: -78.986})
		.zoom(8)
		.zoomRange([2, 9])
		.add(po.interact());

		map.add(po.image()
				.url("http://s3.amazonaws.com/com.modestmaps.bluemarble/{Z}-r{Y}-c{X}.jpg"));
	};

	$(document).ready(function () {
		insertMap();
	});

})();