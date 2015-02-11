Tic Tac Toe
===========

Summary
-------

A friend and I wanted to learn
[ClojureScript](https://github.com/clojure/clojurescript) and
[core.async](https://github.com/clojure/core.async). ClojureScript
lets you write JavaScript with a [Clojure](http://clojure.org/)-like
syntax. Core.async is supposed to be a fancy new way to handle
asynchronous programming. Tic Tac Toe seemed like a simple-enough yet
complex-enough task to get familiar with both. First I wrote the game
in vanilla ClojureScript then I rewrote it using core.async to see how
much fancier it really was.

Results
-------

Well, the
[pure ClojureScript version](https://github.com/tlicata/tl/blob/master/cljs/tictactoe.cljs)
and the
[core.async version](https://github.com/tlicata/tl/blob/master/cljs/tictactoe-async.cljs)
weighed in at about the same number of lines of code.  Maybe it's
because I come from JavaScript background, but I found the non-async
version easier to read. It models the code I've written for normal
JavaScript programs more closely. However, when I look at the
core.async version, I notice that all game state is contained within
the game loop. It's also nice that all the events are passed through
channels, which are only read from in the game loop as well.

Code
----

I would say the biggest change is that the core.async version
doesn't require any quasi-global variables for tracking game state. In
the normal version I had 3 variables for storing state:

```clojure
(def player (atom ttt/X))
(def board-data (atom nil))
(def board-dom (atom nil))
```

While in the core.async version, the only quasi-global variable is
the channel that passes click events. The rest of the state stays
within the game loop. The real version of the loop can be seen on
github, but this is a pseudo-code version.


```clojure
(defn game-loop [first-player]
  (go
   (loop [board empty-board player first-player]
     (let [event (<! click-channel)]
           new-board (view-to-data dom)
           next-player (if (= player X) O X)]
       (if (win? board)
         (recur (create) first-player))
         (if (full? board)
           (recur (create) first-player))
           (recur new-board next-player)))))
```
