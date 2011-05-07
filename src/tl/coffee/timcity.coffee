drawSquare = (ctx, x, y, width) ->
    ctx.fillRect(x, y, width, width)

drawGrid = (ctx, xOff = 0, yOff = 0, rows = 50, cols = 50, square = 25) ->
    ctx.clearRect(0, 0, rows*square, cols*square)
    for y in [0..rows]
        for x in [0..cols]
            drawSquare(ctx, xOff+(x*square), yOff+(y*square), square-1)

init = () ->
    canvas = $("<canvas/>").get(0)
    canvas.height = 400;
    canvas.width = 1000;

    ctx = canvas.getContext("2d") if canvas.getContext
    if ctx
        ctx.fillStyle = "rgb(200,0,0)"
        drawGrid(ctx)

    $("#timcity").css("position", "relative").append(canvas);

$(init)


