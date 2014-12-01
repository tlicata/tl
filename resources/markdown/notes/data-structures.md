Data Structures
===============

> In computer science, a data structure is a particular way of
> organizing data in a computer so that it can be used efficiently.
> -- <cite>[Wikipedia][data_structure]</cite>

__Common Data Structures__

* Arrays
* Linked Lists
* Trees

and [more][list_of_structures], including

* Hashes

A couple of those look familiar from JavaScript and Ruby. `Arrays` and
`Hashes` are present in both languages. (In JavaScript, hashes are
called Objects). Most of your programming needs can be solved using
one of those popular structures. However, to call yourself a good
Computer Scientist you should be knowledable about a few others.

__For the most part, in your day-to-day programming, stick to the data
structures provided by your language. Don't start your first day on
some job by implementing your own linked list.__

Array
-----

You probably have a good understanding of Arrays from using them in
your day-to-day programming. They are one of the simplest and most
common data structures.

```Ruby
foods = ["kale", "broccoli", "carrots"]
foods[0] # accesses the first element (i.e., "kale")
foods[1] # accesses the second element (i.e., "broccoli")
```

### Efficiency ###

Remember the quote at the top of the page.  Not only do we want to
organize data, but we want to do it _efficiently_. Let's think about
how good or bad Arrays might be for:

* retrieving an element
    * from the beginning
    * from the middle
    * from the end
* changing an element
* removing an element
* adding an element

To do this, we may want to pause and think about how Arrays are
stored in memory. What _is_ an Array, really?

-------------------------------------------------------------------------

#### Memory ####

Your computer's memory can be thought of as a place to store
stuff. Maybe we can think of them as mailboxes. Depending on how much
memory you have, you might have X mailboxes, each with their own
address.

An Array requests access to mailboxes that are at sequential
addresses.  If I want to have an Array with 10 elements in it, I
reserve 10 mailboxes that are all right next to each other.

This is nice because it's very fast to find the _nth_ element in an
Array. You just take the starting address and move over _n_ spots.

This is bad because if you want to add an element to the array, you
need to acquire an additional mailbox, but one that is next to your
existing mailboxes might not be available. In that case you'll need to
request 11 sequential addresses and copy your whole array into the new
space.

_This is an oversimplification but it is close enough of a model to
illustrate the tradeoffs being made with a theoretical Array. In
practice, Arrays in JavaScript or Ruby will be smarter than this and
do a better job of managing memory._

-------------------------------------------------------------------------

Linked List
-----------

A [__Linked List__][wiki_list] (or __Singly Linked List__) is another
way of organizing a list of elements.

Instead of requesting _n_ contiguous memory locations, you just create
_n_ nodes (or vertices) and link them yourself.  Each node can be
stored anywhere in memory, and you also store where to find the next
element in the list in each node.

There is also a version called a __Doubly Linked List__ that has links
going in both directions. So, instead of each node having a only a
`next` property, they also have a `previous` property.

### Efficiency ###

We already thought about the performance characteristics of the
following for Arrays.  How do they compare for Linked Lists?

* retrieving an element
    * from the beginning
    * from the middle
    * from the end
* changing an element
* removing an element
* adding an element

### Exercise ###

__Implement a Linked List__

1. Need to create a representation of a Node (or Vertex).
2. Write a method `add_to_tail` that appends a new value to the end.
3. Write a method `length` that returns the length of a list.
4. Overide the `to_s` method to nicely print the list.
5. Bonus: Write a function `remove` to remove a node from the list.

### Solutions ###

1. Ruby is an Object-Oriented language, so creating a class for a Node
   seems like a reasonable idea.

```ruby
class Node
  attr_accessor :val, :next
  def initialize(value)
    @val = value
  end
end
```

2. In order for a method `add_to_tail` to append a value to the end,
   it needs to locate the end, then add a new Node, linking it to the
   previous tail.

```ruby
  def add_to_tail(value)
    if @next == nil
      @next = Node.new(value)
    else
      @next.add_to_tail(value)
    end
  end
```

3. In order to calculate the length of the list, we can use a
   recursive call. We're starting to see some of the shortcomings of
   our design decisions :)

```ruby
  def length
    if @next == nil
      @val ? 1 : 0
    else
      1 + @next.length
    end
  end
```

4. We can also use a recursive call to nicely print out the list.

```ruby
  def to_s
    if @next == nil
      @val.to_s
    else
      "#{@val} #{@next}"
    end
  end
```

5. I chose a limited representation by only have Nodes be linked to
   other Nodes. So the `remove` method should return the new head of
   the list in case the old head was the Node removed. Perhaps this
   indicates I should have used a more robust setup, such as a
   LinkedList class that encapsulates the Nodes, but I liked this for
   simplicity and illustrative purposes.

```ruby
  def remove(value)
    if @val == value
      if @next
        return @next
      else
        return Node.new(nil)
      end
    elsif @next and @next.val == value
      @next = @next.next
    else
      @next.remove(value)
    end
    return self
  end
```

You can test our all the code we just wrote like so:

```ruby
list = Node.new(7)
list.add_to_tail(14)
list.add_to_tail(21)

puts "list.length is #{list.length}"
puts "list is (#{list})"

list = list.remove(21)
list = list.remove(14)

puts "list.length is #{list.length}"
puts "list is (#{list})"
```

Tree
----

In a way, a [Tree][wiki_trees] is like a more generalized version of a
Linked List where each Node can have more than one child.

* Tree nodes have a parent-child relationship.
* Trees cannot contain cycles (i.e., branches can't intertwine)
* There is a starting node called a `root node`.
* A `leaf node` is one that has no children.

### Traversal ###

Searching a Tree is more complicated than searching a linear data
structure such as an Array or Linked List.

The two main approaches are:

* [Depth-first search][wiki_dfs], and
* [Breadth-first search][wiki_bfs]

DFS is the easiest to implement is the one you should tackle first in
your programming careers.

### Exercise ###

__Implement a Tree__

1. Need to create a representation of a Node (or Vertex).
2. Write a method `add_child` to add a new child with the given value.
3. Write a method `find` to search a Tree for a value. (Hint: DFS)

### Specific Types of Trees ###

* Binary Trees
    - Each node has zero, one, or two children. This assertion makes
      many tree operations simple and efficient.
* Binary Search Trees
    - A binary tree where any left child node has a value less than
      its parent node and any right child node has a value greater
      than or equal to that of its parent node.
* Heaps
    - See [wikibooks][wiki_heaps] for proper definition.
    - Simplified version: a (usually binary) tree where the biggest
      element is always at the top.
* [Tries][wiki_tries]
    - Useful for autocompletion.

Big-O Notation
--------------

> In computer science, big O notation is used to classify
> algorithms by how they respond (e.g., in their processing time
> or working space requirements) to changes in input size.
> -- <cite>[Wikipedia][wiki_bigo]</cite>

Since a big issue when discussing data structures is their efficiency
in the face of certain tasks, we want to use a common language to
discuss such (in)efficiencies. Normally, we are interested in how much
memory or processing time is needed to complete the task depending on
the size of the input. We often let _n_ represent the input size.

So, an algorithm that is O(1), is said to be "Big O of 1" or
"constant", and does not vary depending on the size of the input. This
is good. This is fast even for very large _n_.

An algorithm that is O(n), is said to be "Big O of _n_" or "linear", and
this indicates that the resources required grow proportionally to the
size of the input. This is reasonable performance.

Another that is O(n^2), is said to be "Big O of _n_ squared" and it means
the resources grow in proportion to the square of the input. This is
slow. Think of really big numbers and then think of them squared.

See the [Cheat Sheet][bigo_cheat] for some other common time
(processing time) and space (memory) complexities and their notations.

Resources
---------

* [Data Structure on Wikipedia][data_structure]
* [Array on Wikipedia][wiki_array]
* [Trees on Wikibooks][wiki_trees]
* [Heaps on Wikibooks][wiki_heaps]
* [Big-O Cheat Sheet][bigo_cheat]
* [64-bit Computing][wiki_64_bit]

[data_structure]: http://en.wikipedia.org/wiki/Data_structure
[list_of_structures]: http://en.wikipedia.org/wiki/List_of_data_structures
[wiki_array]: http://en.wikipedia.org/wiki/Array_data_structure
[wiki_list]: http://en.wikipedia.org/wiki/Linked_list
[wiki_trees]: http://en.wikibooks.org/wiki/Data_Structures/Trees
[wiki_heaps]: http://en.wikibooks.org/wiki/Data_Structures/Min_and_Max_Heaps
[wiki_tries]: http://en.wikipedia.org/wiki/Trie
[wiki_bigo]: http://en.wikipedia.org/wiki/Big_O_notation
[bigo_cheat]: http://bigocheatsheet.com/
[wiki_64_bit]: http://en.wikipedia.org/wiki/64-bit_computing
[wiki_dfs]: http://en.wikipedia.org/wiki/Depth-first_search
[wiki_bfs]: http://en.wikipedia.org/wiki/Breadth-first_search
