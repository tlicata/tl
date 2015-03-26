# tl
A site written in Clojure.

## Local Development

### Dependencies

Heroku Toolbelt

(Ubuntu)

    sudo gem install heroku
    sudo gem install foreman

(MacOS)

    brew install heroku-toolbelt
    gem install forman

Leiningen

(MacOS)

    brew install leiningen

### Starting Server

Foreman will launch a local version of the site
from the command line. This is fine for html/js/css
development, but has the drawback of needing to
restart for refreshing jvm changes.

    lein uberjar && foreman start

The better way is to get interactive dev going
through emacs and cider.

First, follow the installation instructions: https://github.com/clojure-emacs/cider.
Then, in Emacs, open core.clj,

    M-x cider-jack-in
    C-c C-k

In cider buffer, start server

    user> (tl.core/dev-main)

Get clojurescript compiling. In new terminal,

    $ lein cljsbuild auto     # prod profile w/ advanced optimizations
    $ lein cljsbuild auto dev # dev profile w/ pretty print

Or, use Austin for ClojureScript REPL,

    user> (cemerick.austin.repls/exec :exec-cmds ["open" "-ga" "/Applications/Google Chrome.app"])

## Deploy

Add heroku repo as git remote and push.

    git remote add heroku <app-url.git>
    git push heroku <your-branch>:master

## License
Copyright &copy; 2012 Timothy Licata
