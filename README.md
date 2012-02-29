# tl
A site written in Clojure.

## Local Development

### Dependencies

Ruby. Use, RVM to install and manage it.

    bash -s stable < <(curl -s https://raw.github.com/wayneeseguin/rvm/master/binscripts/rvm-installer)
    rvm install 1.8.7 ;docs say install (and possible "use") 1.8 before 1.9?
    rvm install 1.9.2
    rvm use 1.9.2
    rvm notes ;special instructions for screen users, etc.

Heroku Client & Foreman

    sudo gem install heroku
    sudo gem install foreman

Redis

    wget http://redis.googlecode.com/files/redis-2.4.7.tar.gz
    tar -xzf redis-2.4.7.tar.gz
    cd redis-2.4.7.tar.gz
    make
    make install

Environment & Leiningen 1.7

lein-cljsbuild is a dev-dependency that needs to be present on
the dyno to compile the clojurescript.  By default dev-dependencies
are not downloaded, so a an environment variable needs to be set
to do that.

    heroku config:add LEIN_DEV=y
    heroku config:add LEIN_BUILD_TASK=compile

In order for config vars to work, have to install labs plugin
and enable user_env_compile.

    heroku plugins:install http://github.com/heroku/heroku-labs.git
    heroku labs:enable user_env_compile -a <app-name>

### Starting Server

Foreman will launch a local verion of the site
from the command line. This is fine for html/js/css
development, but has the drawback of needing to
restart for refreshing jvm changes.

    foreman start

The better way is to get interactive dev going
through emacs and Swank Clojure.

First, follow the installation instructions: http://github.com/technomancy/swank-clojure.
Then, in Emacs, open core.clj,

    M-x clojure-jack-in
    C-c C-k

At Slime repl, start server

    user> (tl.core/dev-main)

Get clojurescript compiling. In new terminal,

    $ lein cljsbuild auto

For either method, redis must be started seperately.

    ./path/to/redis-2.4.7/src/redis-server

## Deploy

Add heroku repo as git remote and push.

    git remote add heroku <app-url.git>
    git push heroku <your-branch>:master

## License
Copyright &copy; 2012 Timothy Licata
