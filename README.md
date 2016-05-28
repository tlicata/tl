# tl

Tim's Online World

## Local Development

### Dependencies

- Leiningen
- Foreman

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
