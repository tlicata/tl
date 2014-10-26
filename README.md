# tl
A site written in Clojure.

## Local Development

### Dependencies

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

### Starting Server

Foreman will launch a local verion of the site
from the command line. This is fine for html/js/css
development, but has the drawback of needing to
restart for refreshing jvm changes.

    foreman start

The better way is to get interactive dev going
through emacs and nrepl.

First, follow the installation instructions: https://github.com/kingtim/nrepl.el.
Then, in Emacs, open core.clj,

    M-x nrepl-jack-in
    C-c C-k

In nrepl buffer, start server

    user> (tl.core/dev-main)

Get clojurescript compiling. In new terminal,

    $ lein cljsbuild auto

Or, use Austin for ClojureScript REPL,

    user> (cemerick.austin.repls/exec :exec-cmds ["open" "-ga" "/Applications/Google Chrome.app"])

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
