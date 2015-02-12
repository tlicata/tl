Everything I know about Shells in Emacs
=======================================

Or how I learned to stop worrying and love the buffer.

The Good, the Bad, and the Ugly
-------------------------------

One possible programming workflow is to use Emacs for all your text
editing needs then switch to a Terminal for your terminal-related
needs. This is good and all, but sometimes when I'm in terminal I miss
having all my Emacs niceties (e.g., keyboard navigation, copy & paste
commands, window movement). Luckily, there are several ways to run
shells right inside of Emacs.

Eshell, Shell, Term (& Ansi-term)
---------------------------------

There are at least 3 ways to run a shell inside of Emacs.

- `M-x eshell`, will run a shell that has been implemented in elisp.
- `M-x shell`, will run a terminal emulator.
- `M-x term`, will proxy commands to and from your actual shell.
- `M-x ansi-term`, same as `M-x term`, as far as I can tell.

They all have trade-offs and figuring out the best solution for you is
part of the fun. I tend to use eshell as much as I can. When it falls
over on certain tasks (e.g., won't `clear`), I switch to `M-x
ansi-term`.

Not Quite Right
---------------

So pretend you ran `M-x eshell` and you're facing the eshell
prompt. You start treating it like a normal shell.

```sh
$ ls
```

```sh
$ cd ..
```

Sweet, it's like a normal shell but you can move through your history
with normal Emacs commands (<kbd>C-p</kbd> to move up a line,
<kbd>M-v</kbd> to move up a page, <kbd>C-r</kbd> to search backwards,
<kbd>M-r</kbd> to search through previous commands).

There are a couple of issues, however. `clear` doesn't work. Grr. Any
bash aliases you have also won't be present. Grr. You need a special
eshell alias file for that. You may get strange characters appearing
in your output from time to time. It also interacts poorly with any
commands that use a pager (e.g., `git log`). I don't really want a
pager in eshell because Emacs itself is a pretty good pager. I added
the following line to my .emacs.d config file to effectively disable
paging in Emacs.

```lisp
(setenv "PAGER" "cat")
```

One neat feature is if I run a `grep` command from eshell it pops me
into an Emacs-y results window. Try it and enjoy.

Eshell is also crazy because it's a shell that also acts like a lisp
REPL. I haven't totally figured out the benefit(s) of that, but I
assume there's power there.

http://www.howardism.org/Technical/Emacs/eshell-fun.html

Real Shells In Emacs
--------------------

If you run `M-x ansi-term` you'll get a buffer with a more realistic
shell inside of it. `clear` works! The kicker is that many of your
Emacs keybindings don't work anymore. <kbd>C-p</kbd> cycles through
your previously run commands (as it would in your normal Terminal)
instead of moving up a line (as it would in normal Emacs). You can
switch into "emacs mode" with <kbd>C-c C-j</kbd> and back into
"terminal mode" with <kbd>C-c C-k</kbd>.
