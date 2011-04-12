var swfobject, tl;

if (!tl) {
	tl = {};
}

tl.youtubes = (function () {

	var resultsDivId = "search-results";
	var searchDiv = null;
	var searchUrl = "http://gdata.youtube.com/feeds/api/videos";

	var play = function (video, autoplay, loopMode) {
		$(function () {
			swfobject.embedSWF(video, "swf", "100%", "290px", "9", null,
							   {autoplay: autoplay, loop: loopMode});
		});
	};

	var search = (function () {

		var clean = function (json) {
			var entry = json && json.feed && json.feed.entry;
			var videos = [];
			var idx = null;
			for (idx in entry) {
				if (entry.hasOwnProperty(idx)) {
					var vid = entry[idx];
					videos.push({
						id: vid.id && vid.id.$t && vid.id.$t.substr(-11),
						title: vid.title && vid.title.$t,
						viewed: vid.yt$statistics && vid.yt$statistics.viewCount
					});
				}
			}
			return videos;
		};

		var html = function (videos) {
			var outer = document.createElement("div"), i;
			outer.setAttribute("id", resultsDivId);
			for (i in videos) {
				if (videos.hasOwnProperty(i)) {
					var v = videos[i];
					var link = document.createElement("a");
					link.setAttribute("href", v.id);
					link.style.display = "block";
					$(link).html(v.title);
					outer.appendChild(link);
				}
			}
			return outer;
		};

		var remove = function () {
			var resultsDiv = $("#" + resultsDivId);
			if (resultsDiv) {
				resultsDiv.remove();
			}
		};

		var render = function (vids) {
			remove();
			searchDiv.append(html(vids));
		};

		var renderError = function () {
			remove();
			var p = document.createElement("p");
			p.innerHTML = "Something went wrong";
			searchDiv.append(p);
		};

		var success = function (json) {
			render(clean(json));
		};

		return function (query) {
			$.ajax({
				data: {alt: "json", q: query},
				dataType: "jsonp",
				error: renderError,
				success: success,
				timeout: 5000,
				url: searchUrl
			});
		};
	}());

	var bind = function () {
		searchDiv.find("form").submit(function () {
			search(searchDiv.find(":text").val());
			return false;
		});
	};

	$(document).ready(function () {
		searchDiv = $("#youtubes-search");
		bind();
	});

	return {
		play: play,
		search: search
	};
}());
