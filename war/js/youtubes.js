var $, swfobject, tl;

if (!tl) {
	tl = {};
}

tl.youtubes = (function () {

	return {
		play: function (video, autoplay, loopMode) {
			$(function () {
				swfobject.embedSWF(video, "swf-div", "100%", "290px", "9", null,
								   {autoplay: autoplay, loop: loopMode});
			});
		}
	};
}());
