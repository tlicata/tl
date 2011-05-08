var swfobject, tl;

if (!tl) {
	tl = {};
}

tl.youtubes = (function () {

	var decode = decodeURIComponent;
	var encode = encodeURIComponent;
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
						thumb: vid.media$group.media$thumbnail[1].url,
						title: vid.title && vid.title.$t,
						viewed: vid.yt$statistics && vid.yt$statistics.viewCount
					});
				}
			}
			return videos;
		};

		var html = function (videos, query) {
			var outer = $("<div/>").attr("id", resultsDivId);
			$.each(videos, function (idx, vid) {
				outer.append($("<div/>").append(
					$("<img/>")
						.attr("src", vid.thumb),
					$("<a/>")
						.attr("href", vid.id.concat("#", query))
						.html(vid.title),
					$("<span/>")
						.addClass("views")
						.html(vid.viewed.concat(" views"))
				));
			});
			return outer;
		};

		var remove = function () {
			var resultsDiv = $("#" + resultsDivId);
			if (resultsDiv) {
				resultsDiv.remove();
			}
		};

		var render = function (vids, query) {
			remove();
			searchDiv.append(html(vids, query));
		};

		var renderError = function () {
			remove();
			var p = document.createElement("p");
			p.innerHTML = "Something went wrong";
			searchDiv.append(p);
		};

		var success = function (json, query) {
			window.location.hash = query;
			render(clean(json), query);
		};

		return function (query) {
			$.ajax({
				data: {alt: "json", q: query},
				dataType: "jsonp",
				error: renderError,
				success: function (json) {
					success(json, encode(query));
				},
				timeout: 5000,
				url: searchUrl
			});
		};
	}());

	$(document).ready(function () {
		searchDiv = $("#youtubes-search");
		searchDiv.find("form").submit(function () {
			search(searchDiv.find(":text").val());
			return false;
		});
		if (window.location.hash) {
			var noHash = window.location.hash.substr(1);
			search(decode(noHash));
		}
	});

	return {
		play: play,
		search: search
	};
}());
