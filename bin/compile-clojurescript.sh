#!/bin/bash

rm -rf war/js/bin/out
mkdir -p war/js/bin/out
cljsc src/tl/cljs > war/js/bin/all.js
mv out war/js/bin/
