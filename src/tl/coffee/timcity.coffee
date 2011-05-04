container = null

fill = () ->
    w = h = 1500
    i = 50

    latty = (lon) ->
        make = (lat) ->
            container.append $("<div/>").css
                backgroundColor: "#369"
                border: "solid"
                height: i + "px"
                left: lon
                position: "absolute"
                top: lat
                width: i + "px"
        (make y for y in [0..h] by i)
    (latty x for x in [0..w] by i)

init = () ->
    container = $("#timcity").css
        position: "relative"
    .draggable()

    fill()

$(init)


