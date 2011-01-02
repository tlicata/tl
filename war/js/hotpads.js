(function () {

	var
	mapper_api = "http://hotpads.com"
	mapper_location = "http://filenet.hotpads.com/mapping/mapper/mapper_lasso23.swf";

	var insertMap = function(varsXml, flashProxyId, dev, noPermalink) {
		
		var flashVars = {"varsXml": varsXml,
						 "dev": dev};

		var params = {"quality": "high",
					  "menu": "false",
					  "scale": "noscale",
					  "allowScriptAccess": "always",
					  "swLiveConnect": "true"};

		var so = swfobject.embedSWF(mapper_location,
									"mapDiv",
									"100%",
									"100%",
									"9",
									"http://hotpads.com/FlashJavascriptIntegration/expressInstall.swf",
									flashVars,
									params);
	};

	var init = function () {
		var varsXml = "<mapXml>";
		varsXml += "<MapConfig>";
		varsXml += "<lat>42.938</lat><lon>-78.986</lon>";
		varsXml += "<defaultLocation>" + mapper_api + "</defaultLocation>";
		varsXml += "<appdata>" + mapper_api + "/appdata" + "</appdata>";
		varsXml += "<menus></menus>";
		varsXml += "</MapConfig>";

		// no listing types since can't get lisings x-domain
		varsXml += "<listingTypes></listingTypes>";
		varsXml += "</mapXml>";

		var flashProxyId = "1";
		var dev = false;
		var noPermalink = false;

		insertMap(varsXml,
				  flashProxyId,
				  dev,
				  noPermalink);
	};

	$(document).ready(init);

})();