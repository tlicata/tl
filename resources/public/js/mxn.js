$(document).ready(function () {
    var map = new mxn.Mapstraction("mxn", "googlev3");
    map.setCenterAndZoom(new mxn.LatLonPoint(37.75,-122.44), 12);

    var marker = new mxn.Marker(new mxn.LatLonPoint(37.75,-122.44));
    map.addMarkerWithData(marker, {
        infoBubble: '<a href="http://www.google.com">Advanced Marker</a>',
        label: "Tooltip"
    });
});
