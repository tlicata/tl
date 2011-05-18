ID_SPLIT = "-"

litSquares = []
mouseDownSquare = null
tools = null

interior = (sq1, sq2, width) ->
    sq1 = $(sq1)
    sq2 = $(sq2)

    left = parseInt(sq1.css("left"))
    top = parseInt(sq1.css("top"))
    right = parseInt(sq2.css("left"))
    bottom = parseInt(sq2.css("top"))

    if right < left
        temp = left
        left = right
        right = temp
    if bottom < top
        temp = top
        top = bottom
        bottom = temp

    ids = []
    for x in [left..right] by width
        for y in [top..bottom] by width
            ids.push x+ID_SPLIT+y

    ($("#"+id).get(0) for id in ids)

unlight = (squares) ->
    $.each squares, () ->
        $(this).css("background", "")

highlight = (squares) ->
    $.each squares, () ->
        $(this).css("background", "#369")

refresh = (mouseSquare, width) ->
    if mouseDownSquare
        unlight litSquares
        litSquares = interior(mouseDownSquare, mouseSquare, width)
        highlight litSquares

drawSquare = (x, y, width) ->
    square = $("<div/>").css
        border: "1px solid #333"
        height: width
        left: x
        position: "absolute"
        top: y
        width: width
    .attr
        id: x + ID_SPLIT + y
    .mousedown (e) ->
        mouseDownSquare = this
        unlight litSquares
    .mousemove (e) ->
        refresh this, width
    .mouseup (e) ->
        refresh this, width
        mouseDownSquare = null

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


