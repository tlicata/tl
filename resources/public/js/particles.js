/*global THREE */

var tl;

if (!tl) {
    tl = {};
}

tl.particles = (function () {

    var camera, renderer, scene;
    var group;

    var init = function () {
        var container = $("#particles");
        var height = 400;
        var width = container.width();

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

        for (var i = 0; i < 1000; i++) {
            var material = new THREE.ParticleCanvasMaterial({
                color: Math.random() * 0x808008 + 0x808080,
                program: program
            });
            var particle = new THREE.Particle(material);
            particle.position.x = Math.random() * 2000 - 1000;
            particle.position.y = Math.random() * 2000 - 1000;
            particle.position.z = Math.random() * 2000 - 1000;
            particle.scale.x = particle.scale.y = Math.random() * 10 + 5;
            group.add(particle);
        }

        renderer = new THREE.CanvasRenderer();
        renderer.setSize(width, height);
        container.append(renderer.domElement);
    };

    var render = function () {
        //camera.position.x += ( mouseX - camera.position.x ) * 0.05;
        //camera.position.y += ( - mouseY - camera.position.y ) * 0.05;
        //camera.lookAt( scene.position );

        group.rotation.x += 0.01;
        group.rotation.y += 0.02;

        //camera.position.z -= 5;

        renderer.render(scene, camera);
    };

    return {
        init: function () {
            init();
            render();
            setInterval(render, 50);
        }
    };
})();

$(document).ready(function () {
    tl.particles.init();
});
