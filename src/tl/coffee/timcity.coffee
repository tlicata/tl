canvas = null
ctx = null

drawSquare = (x, y, width) ->
    ctx.strokeRect(x, y, width, width)

drawGrid = (xOff = 0, yOff = 0, rows = 50, cols = 50, square = 25) ->
    ctx.clearRect(0, 0, rows*square, cols*square)
    ctx.strokeRect(1, 1, canvas.width - 2, canvas.height - 2)
    for y in [0..rows]
        for x in [0..cols]
            drawSquare(xOff+(x*square), yOff+(y*square), square)

init = () ->
    canvas = $("<canvas/>").get(0)
    canvas.height = 400;
    canvas.width = 1000;

    ctx = canvas.getContext("2d") if canvas.getContext
    if ctx
        ctx.strokeStyle = "rgb(50,50,50)"
        drawGrid()

    $("#timcity").css("position", "relative").append(canvas);

$(init)


