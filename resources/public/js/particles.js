/*global THREE */

var tl;

if (!tl) {
    tl = {};
}

tl.particles = (function () {

    var container, camera, height, group, renderer, scene, width;

    var updateSize = function () {
        if (container) {
            height = container.height();
            width = container.width();
        }
    };

    var init = function () {
        container = $("#particles");
        updateSize();

        var angle = 75;
        var aspect =  width / height;
        var far = 3000;
        var near = 1;

        camera = new THREE.PerspectiveCamera(angle, aspect, near, far);
        camera.position.z = 900;

        scene = new THREE.Scene();

        var program = function (context) {
            context.beginPath();
            context.arc(0, 0, 1, 0, Math.PI*2, true);
            context.closePath();
            context.fill();
        };

        group = new THREE.Object3D();
        scene.add(group);

        for (var i = 0; i < 500; i++) {
            var material = new THREE.ParticleCanvasMaterial({
                color: Math.random() * 0xAAEE55,
                program: program
            });
            var particle = new THREE.Particle(material);
            particle.position.x = Math.random() * 2000 - 1000;
            particle.position.y = Math.random() * 2000 - 1000;
            particle.position.z = Math.random() * 2000 - 1000;
            particle.scale.x = particle.scale.y = Math.random() * 10 + 7;
            group.add(particle);
        }

        renderer = new THREE.CanvasRenderer();
        renderer.setSize(width, height);
        container.append(renderer.domElement);
    };

    var mouseX = 0;
    var mouseY = 0;
    var oldMouseX = 0;
    var oldMouseY = 0;
    var oldSpeedX = 0;
    var oldSpeedY = 0;
    var render = function () {

        var time = new Date().getTime() / 5000;
        var sin = Math.sin(time) / 5;

        var dull = width * 2;
        var forceX = (mouseX - oldMouseX + sin) / dull;
        var forceY = (mouseY - oldMouseY + sin) / dull;
        var speedX = (oldSpeedX/1.05) + forceX;
        var speedY = (oldSpeedY/1.05) + forceY;

        group.rotation.x += speedY;
        group.rotation.y += speedX;

        oldMouseX = mouseX;
        oldMouseY = mouseY;
        oldSpeedX = speedX;
        oldSpeedY = speedY;

        renderer.render(scene, camera);
    };
    var onMouseMove = function (event) {
        mouseX = event.pageX;
        mouseY = event.pageY;
    };
    var onSwipe = function (event, custom) {
        var delta = custom && custom.delta && custom.delta[0];
        if (delta && delta.lastX) {
            mouseX = mouseX + delta.lastX;
            mouseY = mouseY + delta.lastY;
        }
    };

    return {
        init: function () {
            init();
            render();
            setInterval(render, 50);
            $(window).resize(function () {
                updateSize();
                camera.aspect = width / height;
                //camera.fov = ( 360 / Math.PI ) * Math.atan( tanFOV * ( window.innerHeight / windowHeight ) );
                camera.updateProjectionMatrix();
                camera.lookAt( scene.position );
                renderer.setSize(width, height);
            }).mousemove(onMouseMove).bind("swipemove", onSwipe);
        }
    };
})();

$(document).ready(function () {
    tl.particles.init();
});
