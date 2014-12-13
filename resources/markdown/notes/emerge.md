Emerge
======

I recently had to give a coding demonstration. I planned to walk
through a tutorial in front of the students. I practiced ahead of
time, storing my code in a Git repository along the way. During the
presentation, I coded from scratch so they could follow along, and I
stored the results in completely new Git repository.

The resulting code ended up being a bit different than in my trial
run. After class, I ended up in the situation where each repository
had some code I wanted to appear in the final version.  The problem
was that the Git repositories did not share a common commit/ancestor
(since I created the second one from scratch as part of the
demonstration). As far as I know, Git does not support merging files
that are not part of the same repo.

So I looked for a Unix utility to do the merge for me. I ran across
[sdiff](http://www.gnu.org/software/diffutils/manual/html_node/Interactive-Merging.html#Interactive-Merging)
which looked like it had potential:

> With sdiff, you can merge two files interactively based on a
side-by-side -y format comparison
[see Side by Side](http://www.gnu.org/software/diffutils/manual/html_node/Side-by-Side.html#Side-by-Side). Use
--output=file (-o file) to specify where to put the merged text. See
[Invoking sdiff](http://www.gnu.org/software/diffutils/manual/html_node/Invoking-sdiff.html#Invoking-sdiff),
for more details on the options to sdiff.

So I ran the command

```sh
sdiff --output version3.js version1.js version2.js
```

but couldn't quite make sense of the interface after that. I had the
urge to give up because I had seen a more interesting path a few pages
earlier. The `sdiff` page had also mentioned,

> Another way to merge files interactively is to use the Emacs Lisp
package emerge.

Well, you don't need to tell me twice when it comes to Emacs-based
solutions. I head to the
[emerge documentation](https://www.gnu.org/software/emacs/manual/html_node/emacs/Emerge.html)
and start reading.

_I read the docs for 30 minutes. Sometimes I have to fight away the
thought that I'm taking longer than is needed.  Is there not a
simpler, brute force solution? Isn't there an xkcd comic about this? I
also grab a snack since I'm a little hungry._

Ok, after reading "Overview of Emerge" and "Submodes of Emerge", I
decide to run the Emacs command

```
M-x emerge-files
```

(I could have used `M-x emerge-buffers` too, I suppose.)

I'm prompted for two files I'd like to merge. I am shown the files
side-by-side, with a merge buffer underneath. Initially, the merge
buffer appears the same as the first file and says "diff 0 of 5" in
the status bar.

I read the
["Merge Commands"](https://www.gnu.org/software/emacs/manual/html_node/emacs/Merge-Commands.html#Merge-Commands)
section and learn about the key commands available. I press:

- <kbd>n</kbd> to go to the _next_ difference (or, first, in this
case)
- <kbd>a</kbd> to choose the left version
- <kbd>b</kbd> to chose the right version
- <kbd>p</kbd> to go to the _previous_ diff, and
- <kbd>q</kbd> to quit,  when I'm satisfied with my changes.

So there you go.  Emacs' emerge is pretty cool. I didn't have to deal
with manually merging individual diffs, but I assume I could just
switch to the merge buffer and edit it like I would a normal buffer. I
can't say I'm blown away, but I'll learn some more the next time I
need to merge files without the help of git (and
[magit](https://github.com/magit/magit)).
