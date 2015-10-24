# tl

Tim's Online World

## Local Development

### Dependencies

Heroku Toolbelt

(Ubuntu)

    sudo gem install heroku
    sudo gem install foreman

(MacOS)

    brew install heroku-toolbelt
    gem install foreman

Leiningen

(MacOS)

    brew install leiningen

### Starting Server

    lein uberjar && foreman start

Or, through emacs and [cider](https://github.com/clojure-emacs/cider).

    M-x cider-jack-in
    C-c C-k

In cider buffer,

    user> (tl.core/dev-main)

Get clojurescript compiling. In new terminal,

    $ lein cljsbuild auto     # prod profile w/ advanced optimizations
    $ lein cljsbuild auto dev # dev profile w/ pretty print

## Deploy

Add heroku repo as git remote and push.

    git remote add heroku <app-url.git>
    git push heroku <your-branch>:master

## License
Copyright &copy; 2015 Timothy Licata
