Tic Tac Toe
===========

Summary
-------

A friend and I wanted to learn
[ClojureScript](https://github.com/clojure/clojurescript) and
[core.async](https://github.com/clojure/core.async). ClojureScript
lets you write JavaScript with a [Clojure](http://clojure.org/)-like
syntax. Core.async is supposed to be a fancy new way to handle
asynchronous programming (i.e., callbacks). Tic Tac Toe seemed a
simple-enough yet complex-enough task to get familiar with both. First
I wrote the game in ClojureScript then I rewrote it using core.async
to see how much fancier it really was.

Results
-------

Well, the
[pure ClojureScript version](https://github.com/tlicata/tl/blob/master/cljs/tictactoe.cljs)
and the
[core.async version](https://github.com/tlicata/tl/blob/master/cljs/tictactoe-async.cljs)
weighed in at about the same number of lines of code.  Maybe it's
because I come from JavaScript background, but I found the non-async
version easier to read. It models the code I've written in normal
JavaScript programs more closely. However, when I look at the
core.async version, I notice that all game state is hidden in the game
loop. It's also nice that all the events are passed through channels,
which are only read from in the game-loop as well.


