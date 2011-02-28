var $, swfobject, tl;

if (!tl) {
	tl = {};
}

tl.youtubes = (function () {

	return {
		init: function (videos, base) {
			$(function () {
				if (swfobject) {
					$("#youtubes").find("a").each(function (idx, val) {
						$(this).click(function () {
							swfobject.embedSWF(base.concat(videos[idx]),
											   "swf-div",
											   "100%",
											   "100%",
											   "9",
											   null,
											   {autoplay: 1,
												loop: 1});
							return false;
						});
					});
				}
			});
		}
	};
}());
