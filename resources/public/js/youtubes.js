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
    var searchUrl = "https://www.googleapis.com/youtube/v3/search";

    var search = (function () {

        var clean = function (json) {
            var items = json && json.items;
            var videos = [];
            for (var i = 0; i < items.length; i++) {
                var vid = items[i];
                var snip = vid && vid.snippet;
                var thumb = snip.thumbnails && snip.thumbnails.default;
                videos.push({
                    id: vid.id && vid.id.videoId,
                    thumb:  thumb && thumb.url,
                    title: snip && snip.title
                });
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
                    outer.append($("<tr/>").append(
                        $("<td/>").append(img).css("width", "130px"),
                        $("<td/>").append(link).css("vertical-align", "middle")
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
            remove();

            if (query === "list") {
                $.get("/youtubes/list.html", function (html) {
                    render(html);
                });
            } else if (query) {
                $.ajax({
                    data: {
                        q: query,
                        key: "AIzaSyBDSymHCx1ESegvp09VMSFT6e9vdPUtkrc",
                        maxResults: 50,
                        part: "snippet",
                        type: "video"
                    },
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
            }
        };
    }());

    $(document).ready(function () {

        // Grab DOM elements
        searchDiv = $("#youtubes-search");
        var queryInput = searchDiv.find('[name="query"]');

        // Bind event handlers to the search form.
        searchDiv.find("form").submit(function () {
            var query = encode(queryInput.val());
            // If browser supports "hashchange" will rely on that to
            // trigger the search, otherwise search manually.
            if ("onhashchange" in window) {
                window.location.hash = query;
            } else {
                search(query);
            }
            return false;
        });

        // The hash represents a search. If one exists,
        // then load search results for it. Also, use
        // it to pre-populate the search box.
        var getHash = function () {
            return decode(window.location.hash.substr(1));
        };
        if (window.location.hash) {
            var hash = getHash();
            search(hash);
            queryInput.val(hash).select();
        }

        // If browser supports it, listen for the hash change event
        // and update the search results.
        window.addEventListener("hashchange", function () {
            var hash = getHash();
            queryInput.val(decode(hash));
            search(hash);
        });
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
                        $.get(window.location.pathname + "/watch");
                    }
                }
            }
        });
    };

    return {
        search: search
    };
}());
