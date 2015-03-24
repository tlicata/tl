var tl;

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
    var searchUrl = "http://gdata.youtube.com/feeds/api/videos";

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
            var outer = $("<table/>").addClass("table table-striped");
            if (videos.length) {
                $.each(videos, function (idx, vid) {
                    var img = $("<img/>").attr("src", vid.thumb);
                    var link = $("<a/>")
                        .attr("href", vid.id.concat("#", query))
                        .html(vid.title);
                    var views = $("<div/>")
                        .addClass("text-muted")
                        .html(tl.util.addCommas(vid.viewed).concat(" views"));
                    outer.append($("<tr/>").append(
                        $("<td/>").append(img).css("width", "130px"),
                        $("<td/>").append(link, views).css("vertical-align", "middle")
                    ));
                });
            } else {
                outer.html($("<p/>").text("No results found"));
            }
            return outer;
        };

        // Delete html of current search results.
        var remove = function () {
            var resultsDiv = $("#" + resultsDivId);
            if (resultsDiv) {
                resultsDiv.remove();
            }
        };

        // Draw new search results.
        var render = function (html) {
            remove();
            var resultsDiv = $("<div/>")
                .attr("id", resultsDivId)
                .addClass("col-md-12");
            searchDiv.after(resultsDiv.append(html));
        };
        var renderSuccess = function (vids, query) {
            render(html(vids, query));
        };
        var renderError = function () {
            render($("<span/>").text("Something went wrong"));
        };

        // search
        return function (query, callback) {
            $.ajax({
                data: {alt: "json", q: query},
                dataType: "jsonp",
                error: function () {
                    renderError();
                    if (callback) {
                        callback(false);
                    }
                },
                success: function (json) {
                    var success = true;
                    try {
                        query = encode(query);
                        window.location.hash = query;
                        renderSuccess(clean(json), query);
                    } catch (e) {
                        success = false;
                    }
                    if (callback) {
                        callback(success);
                    }
                },
                timeout: 5000,
                url: searchUrl
            });
        };
    }());

    $(document).ready(function () {

        // Grab DOM elements
        searchDiv = $("#youtubes-search");
        var queryInput = searchDiv.find('[name="query"]');

        // Bind event handlers to the search form.
        searchDiv.find("form").submit(function () {
            search(queryInput.val());
            return false;
        });

        // The hash represents a search. If one exists,
        // then load search results for it. Also, use
        // it to pre-populate the search box.
        if (window.location.hash) {
            var hash = decode(window.location.hash.substr(1));
            search(hash);
            queryInput.val(hash).select();
        }
    });

    // YouTube IFrame API expects this function to be defined.
    window.onYouTubeIframeAPIReady = function () {
        player = new YT.Player("player", {
            height: "300",
            width: "500",
            videoId: window.video,
            playerVars: {
                autohide: 0,
                autoplay: 1,
                enablejsapi: 1,
                fs: 1,
                modestbranding: 1
            },
            events: {
                onStateChange: function (event) {
                    if (event.target.getPlayerState() === 0) {
                        event.target.playVideo();
                    }
                }
            }
        });
    };

    return {
        search: search
    };
}());
