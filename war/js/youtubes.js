var swfobject, tl;

if (!tl) {
	tl = {};
}

tl.util = {
	addCommas: function (num) {
		num += "";
		var arr = num.split(".");
		var whole = arr[0];
		var fract = arr.length > 1 ? "." + arr[1] : "";
		var rgx = /(\d+)(\d{3})/;
		while (rgx.test(whole)) {
			whole = whole.replace(rgx, '$1' + ',' + '$2');
		}
		return whole + fract;
	}
};

tl.youtubes = (function () {

	var decode = decodeURIComponent;
	var encode = encodeURIComponent;
	var resultsDivId = "search-results";
	var searchDiv = null;
	var api = {
		play: "http://www.youtube.com/v/",
		search: "http://gdata.youtube.com/feeds/api/videos"
	};

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

	var jsonToHtml = function (videos) {
		var html = [];
		$.each(videos, function (idx, vid) {
			html.push($("<div/>").append(
				$("<img/>")
					.attr("src", vid.thumb),
				$("<a/>")
					.attr("href", vid.id.concat(window.location.hash))
					.html(vid.title),
				$("<span/>")
					.addClass("views")
					.html(tl.util.addCommas(vid.viewed).concat(" views"))
			));
		});
		return html;
	};

	var play = function (video, autoplay, loopMode) {
		$(function () {
			var url = api.play.concat(video);
			swfobject.embedSWF(url, "swf", "100%", "290px", "9", null,
							   {autoplay: autoplay, loop: loopMode});
		});
	};

	var search = (function () {

		var html = function (videos, query) {

			var zebra = function (arr) {
				arr.each(function (idx, elem) {
					if (idx % 2 == 0) {
						$(elem).css("background", "#111");
					}
				});
			};

			var outer = $("<div/>").attr("id", resultsDivId);
			outer.append.apply(outer, jsonToHtml(videos));
			zebra(outer.children());

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

		var success = function (json, query) {
			window.location.hash = query;
			render(clean(json));
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
				url: api.search
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
