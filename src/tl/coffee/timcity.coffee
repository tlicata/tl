canvas = null
ctx = null
tools = null

drawSquare = (x, y, width) ->
    ctx.strokeRect(x, y, width, width)

drawGrid = (xOff = 0, yOff = 0, rows = 50, cols = 50, square = 25) ->
    ctx.strokeStyle = "rgb(50,50,50)"
    for y in [0..rows]
        for x in [0..cols]
            drawSquare(xOff+(x*square), yOff+(y*square), square)

drawTools = (x, y) ->
    tools = $("<div/>").css
        "background": "#000"
        "border": "2px solid #FFF"
        "height": "150px"
        "left": "10px"
        "position": "absolute"
        "top": "10px"
        "width": "100px"
    .click () ->
        ctx.clearRect(0, 0, canvas.width, canvas.height)

init = () ->
    canvas = $("<canvas/>").get(0)
    canvas.height = 400;
    canvas.width = 1000;

    ctx = canvas.getContext("2d") if canvas.getContext
    if ctx
        drawGrid()
        drawTools()

    $("#timcity")
        .append(canvas, tools)
        .css("position", "relative")
        .mousedown (e) ->
            console.log "mousedown ", e
        .mouseup (e) ->
            console.log "mouseup ", e


$(init)


