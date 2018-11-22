var l1 = 0, l2 = 0;
var init = function() {
	// Prevent scrolling http://www.html5rocks.com/en/mobile/touch/
	function noScroll(event) {
		prev(event);
	}
	document.body.on('touchmove', noScroll);

	var _headerHeight = document.getElementById('header').offsetHeight;

	LAYOUT.maxHeight -= _headerHeight;

	var $title = $$('#title');

	// var levels = JSON.parse(localStorage['levels']);

	var game = new LaserGame();

	// Resize
	window.requestAnimationFrame = window.requestAnimationFrame || window.webkitRequestAnimationFrame;
	localStorage['reload'] = localStorage['reload'] || false;
	function t() {
		_headerHeight = document.getElementById('header').offsetHeight;
		var w = document.documentElement.clientWidth,
			h = document.documentElement.clientHeight - _headerHeight;
		game.reload(w, h);
	}
	// https://developer.mozilla.org/en-US/docs/Web/Events/resize#requestAnimationFrame
	var optimizedResize = (function() {
		var timeoutID;
		var running = false;
		// fired on resize event
		function resize() {
			if (!running) {
				running = true;
				if (window.requestAnimationFrame) {
					window.requestAnimationFrame(runCallbacks);
				}
				else {
					clearTimeout(timeoutID);
					timeoutID = setTimeout(runCallbacks, 500);
				}
			}
		}
		function runCallbacks() {
			t();
			running = false;
		}
		return {
			init: function() {
				window.addEventListener('resize', resize);
			},
			stop: function() {
				window.removeEventListener('resize', resize);
			}
		};
	}());
	var $reload = $$('#reload');
	if (localStorage['reload'] === "true") {
		$reload.checked = true;
		optimizedResize.init();
	}

	// Overlay
	var $overlayLinks = $('.js-overlay'),
		$overlayDivs = {},
		$main = $$('.main'),
		s = 'selected',
		o = 'overlay-show';
	for (var i = 0; i < $overlayLinks.length; i++) {
		var id = $overlayLinks[i].getAttribute('href');
		$overlayDivs[id] = $$(id);

		$overlayLinks[i].on('click', function(event) {
			prev(event);

			var id = this.getAttribute('href'),
				$id = $$(id);

			if (hasClass(this, s)) {
				removeClass(this, s);
				removeClass($id, o);
				document.body.on('touchmove', noScroll);
			}
			else {
				for (var m = 0; m < $overlayLinks.length; m++) {
					removeClass($overlayLinks[m], s);
					removeClass($overlayDivs[ $overlayLinks[m].getAttribute('href') ], o);
				}
				addClass(this, s);
				addClass($id, o);
				document.body.removeEventListener('touchmove', noScroll);
			}
			eraseVictory();
		});
	}

	
	// Prev/next buttons
	var $navButtons = $('.js-nav-button');
	for (var m = 0; m < $navButtons.length; m++) {
		$navButtons[m].on('click', function(event) {
			prev(event);
			if (this.id === 'next') {
				l2++;
				if (l2 >= levels[l1].length) {
					l2 = 0;
					l1++;
					if (l1 >= levels.length) {
						l1 = 0;
					}
				}
			}
			else if (this.id === 'prev') {
				l2--;
				if (l2 < 0) {
					l1--;
					if (l1 < 0) {
						l1 = levels.length - 1;
					}
					l2 = levels[l1].length - 1;
				}
			}
			eraseVictory();
			setGame();
		});
	}

	// Victory
	var victory = [];
	if (window.localStorage && typeof localStorage.victory !== "undefined") {
		victory = JSON.parse(localStorage.victory);
	}
	for (var i = 0; i < victory.length; i++) {
		addClass($$('#l' + victory[i].l1 + '-' + victory[i].l2), 'level-done');
	}
	var $victory = $('.victory')[0],
		$canvas = $$('#' + GAME_OPTS.canvasId);
	window.drawVictory = function() {
		// alert('won');
		addClass($victory, 'show');
		$canvas.on('mousedown', eraseVictory);
		$canvas.on('touchstart', eraseVictory);
	};
	function eraseVictory() {
		removeClass($victory, 'show');
		$canvas.removeEventListener('mousedown', eraseVictory);
		$canvas.removeEventListener('touchstart', eraseVictory);
	}
	window.saveVictory = function(l1, l2) {
		var _l1 = l1 - 1,
			_l2 = l2 - 1;
		victory.push({ l1: _l1, l2: _l2 });
		addClass($$('#l' + _l1 + '-' + _l2), 'level-done');
		if (window.localStorage) {
			localStorage.victory = JSON.stringify(victory);
		}
	};

	// Solution
	function eraseSolution() {
		removeClass($solution, 'show');
		removeClass($title, s);
		document.body.removeEventListener('mousedown', checkSolution);
		document.body.removeEventListener('touchstart', checkSolution);
	}
	function checkSolution(event) {
		if (event.target.id !== 'level-solution' && event.target.id !== 'title') {
			eraseSolution();
		}
	}
	var $levelSolution = $$('#level-solution'),
		$solution = $$('#solution');
	$levelSolution.on('click', function(event) {
		prev(event);
		eraseSolution();
		game.solution();
	});
	$title.on('click', function(event) {
		if (hasClass($solution, 'show')) {
			eraseSolution();
		}
		else {
			removeClass($$('.' + s), s);
			removeClass($$('.' + o), o);
			addClass($solution, 'show');
			addClass(this, s);
			document.body.on('mousedown', checkSolution);
			document.body.on('touchstart', checkSolution);
		}
	});

	// Load from the last visited level
	l1 = (localStorage['l1'] || l1) * 1;
	l2 = (localStorage['l2'] || l2) * 1;

	// Init level
	function setGame(immediately) {
		localStorage['l1'] = l1 * 1;
		localStorage['l2'] = l2 * 1;
		game.init(levels[l1][l2]);
	}
	setGame(true);
	removeClass(document.body, 'loading');
};

window.onload = init;