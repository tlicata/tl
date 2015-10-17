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

    var sliceCommand = function (query) {
        return query.slice(1);  // commands start with ":"
    };
    var isCommand = function (query) {
        return query.charAt(0) === ":";
    };
    var isLocalCommand = function (query) {
        return sliceCommand(query) === "buttons";
    };

    var search = (function () {

        var history = (function () {
            var last = null;
            return {
                push: function (previous) {
                    last = previous;
                },
                last: function () {
                    return last;
                }
            };
        })();

        var clean = function (json) {
            var items = json && json.items;
            var videos = [];
            if (items) {
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
            }
            return videos;
        };

        var html = function (videos, query) {
            var outer = $("<table/>").addClass("table table-striped");
            if (videos.length) {
                $.each(videos, function (idx, vid) {
                    var left = null;
                    if (vid.thumb) {
                        left = $("<img/>").attr("src", vid.thumb);
                    } else if (vid.count) {
                        left = $("<span/>").text(vid.count);
                    } else {
                        left = $("<span/>").text(idx + 1);
                    }
                    var link = $("<a/>")
                        .attr("href", vid.id.concat("#", query))
                        .html(vid.title).addClass("btn vid-link");
                    outer.append($("<tr/>").append(
                        $("<td/>").append(left),
                        $("<td/>").append(link),
                        $("<td/>").addClass("plusMinus")
                    ));
                });
            } else {
                outer.html($("<tr/>").append($("<td/>").text("No results found")));
            }
            return outer;
        };

        var showButtons = function (list) {
            var isPlus = list === "history";
            $(".plusMinus").each(function (idx, row) {
                var btn = $("<a/>")
                    .addClass("btn")
                    .text(isPlus ? "+" : "-")
                    .on("click", function (e) {
                        var td = $(row).parent();
                        var link = td.find(".vid-link").attr("href");
                        var id = link.substring(0, link.indexOf("#"));
                        if (id) {
                            var btn = $(e.target);
                            var cmd = isPlus ? "add" : "remove";
                            btn.html("...");
                            $.ajax({
                                url: "/youtubes/list",
                                data: {cmd: ["list", cmd, "sharib", id].join(" ")},
                                success: function (data) {
                                    if (isPlus) {
                                        btn.html("X");
                                    } else {
                                        td.remove();
                                    }
                                },
                                error: function (err) {
                                    btn.html(isPlus ? "+" : "-");
                                }
                            });
                        }
                    });
                $(row).html(btn);
            });
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

        // Take appropriate action based on search input.
        var handleCommand = function (query, previous) {
            var cmd = sliceCommand(query);
            if (cmd === "buttons") {
                showButtons(sliceCommand(previous));
                return false; // don't add to history
            } else {
                $.get("/youtubes/list", {cmd: cmd}, function (json) {
                    render(html(json, query));
                });
                return true; // add to history
            }
        };
        var handleSearch = function (query) {
            $.ajax({
                data: {
                    q: query,
                    key: "AIzaSyBDSymHCx1ESegvp09VMSFT6e9vdPUtkrc",
                    maxResults: 50,
                    part: "snippet",
                    type: "video"
                },
                dataType: "jsonp",
                error: renderError,
                success: function (json) {
                    renderSuccess(clean(json), query);
                },
                timeout: 5000,
                url: searchUrl
            });
            return true; // add to history
        };

        // search
        return function (query) {
            var addToHistory = true;
            if (query) {
                var handler = isCommand(query) ? handleCommand : handleSearch;
                addToHistory = handler(query, history.last());
            }
            if (addToHistory) {
                history.push(query);
                remove();
            }
            return history.last();
        };
    }());

    $(document).ready(function () {

        // Grab DOM elements
        searchDiv = $("#youtubes-search");
        var queryInput = searchDiv.find('[name="query"]');

        // Bind event handlers to the search form.
        searchDiv.find("form").submit(function () {
            var query = queryInput.val();
            // If browser supports "hashchange" will rely on that to
            // trigger the search, otherwise search manually, unless
            // it's a command b/c we don't want commands in the url.
            if ("onhashchange" in window && !isLocalCommand(query)) {
                window.location.hash = encode(query);
            } else {
                queryInput.val(search(query));
            }
            return false;
        });

        // The hash represents a search. If one exists,
        // then load search results for it. Also, use
        // it to pre-populate the search box.
        var updateFromHash = function () {
            var hash = decode(window.location.hash.substr(1));
            return queryInput.val(search(hash));
        };
        if (window.location.hash) {
            updateFromHash().select();
        }

        // If browser supports it, listen for the hash change event
        // and update the search results.
        window.addEventListener("hashchange", updateFromHash);
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
