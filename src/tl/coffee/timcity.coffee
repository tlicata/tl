mouseDownSquare = null
mouseUpSquare = null
tools = null

drawSquare = (x, y, width) ->
    square = $("<div/>").css
        border: "1px solid #333"
        height: width
        left: x
        position: "absolute"
        top: y
        width: width
    .mousedown (e) ->
        mouseDownSquare = this
        $(mouseUpSquare).css("background","")
    .mouseup (e) ->
        mouseUpSquare = this
        if mouseUpSquare == mouseDownSquare
            $(this).css("background", "#369")

drawGrid = (xOff = 0, yOff = 0, rows = 16, cols = 37, square = 25) ->
    grid = $("<div/>")

    for y in [0..rows]
        for x in [0..cols]
            grid.append drawSquare(xOff+(x*square), yOff+(y*square), square)

    grid

drawTools = (x, y) ->
    tools = $("<div/>").css
        "background": "#000"
        "border": "2px solid #FFF"
        "height": "150px"
        "left": "10px"
        "position": "absolute"
        "top": "10px"
        "width": "100px"

init = () ->
    $("#timcity")
        .css("position", "relative")
        .append drawGrid(), drawTools()

$(init)


