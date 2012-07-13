# tl
A site written in Clojure.

## Local Development

### Dependencies

Ruby. Use, RVM to install and manage it.

(Ubuntu)

    bash -s stable < <(curl -s https://raw.github.com/wayneeseguin/rvm/master/binscripts/rvm-installer)
    rvm install 1.8.7 ;docs say install (and possible "use") 1.8 before 1.9?
    rvm install 1.9.2
    rvm use 1.9.2
    rvm notes ;special instructions for screen users, etc.

(MacOS)

it was pre-installed or obtained through macport when installing git.

Heroku Toolbelt

(Ubuntu)

    sudo gem install heroku
    sudo gem install foreman

(MacOS)

Download Heroku Toolbelt for Mac OS X from the heroku site. Run installer.

Leiningen

(MacOS)

Install by hand (unless using homebrew)

    wget --no-check-certificate https://raw.github.com/technomancy/leiningen/stable/bin/lein
    chmod 755 ~/bin/lein
    [put in on path]

Redis

(Ubuntu)

    wget http://redis.googlecode.com/files/redis-2.4.7.tar.gz
    tar -xzf redis-2.4.7.tar.gz
    cd redis-2.4.7.tar.gz
    make
    make install

(MacOS w/ macports)

    sudo port install redis

Heroku Environment

lein-cljsbuild was causing a boot timeout error when trying to compile.

    heroku[web.1]: Error R10 (Boot timeout) -> Web process failed to bind to $PORT within 60 seconds of launch

To get around that, setup :aot compilation.

    heroku config:add LEIN_BUILD_TASK="compile :all"

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

(Ubuntu)

    ./path/to/redis-2.4.7/src/redis-server

(MacOS)

    redis-server /opt/local/etc/redis.conf

## Deploy

Add heroku repo as git remote and push.

    git remote add heroku <app-url.git>
    git push heroku <your-branch>:master

## License
Copyright &copy; 2012 Timothy Licata