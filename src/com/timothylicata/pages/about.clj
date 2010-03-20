(ns com.timothylicata.pages.about
  (:use compojure.html)
  (:use com.timothylicata.pages.global))

(defn about-page
  [request]
  (page request
     {:title "About Tim"
      :body
      [:div
       [:div.blurb
        [:h1 "Manifesto"]
        [:p "Manifestos are cool.  Everyone knows that.  According to "
         (link-to "http://en.wikipedia.org/wiki/Manifesto" "Wikipedia") ","]
        [:blockquote "A manifesto is a public declaration of principles and intentions, often political in nature. However, manifestos relating to religious belief are generally referred to as a creed. Manifestos may also be life stance-related."]
        [:p "Let's take a look at some of the notable manifestos listed."]
        [:ul
         [:li (link-to "http://en.wikipedia.org/wiki/United_States_Declaration_of_Independence" "United States Declaration of Independence")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Communist_Manifesto" "The Communist Manifesto")]
         [:li (link-to "http://en.wikisource.org/wiki/Industrial_Society_and_Its_Future" "Industrial Society and Its Future")]
         [:li (link-to "http://en.wikipedia.org/wiki/The_Cathedral_and_the_Bazaar" "The Cathedral and the Bazaar")]
         [:li (link-to "http://www.phrack.org/issues.html?issue=7&id=3&mode=txt" "The Hacker's Manifesto")]
         [:li (link-to "http://en.wikipedia.org/wiki/Agile_Manifesto" "Agile Manifesto (?)")]]
        [:p "Most of these seem to be extreme in both their viewpoints and their word counts.  Neither of which are my style.  I think what I'm after is the  "
         (link-to "http://en.wikipedia.org/wiki/Life_stance" "Life Stance")
         ".  I believe it was Ferris Bueller who said:"]
         [:blockquote "Isms in my opinion are not good. A person should not believe in an ism, he should believe in himself. I quote John Lennon:"
          [:blockquote "I don't believe in Beatles, I just believe in me."] "Good point there.  After all, he was the Walrus."]
        [:p "So that's me.  I don't believe in an Isms, I just believe in myself and, of course, my Life Stance.  That I haven't written yet."]]
       [:div.blurb
        [:h1 "FAQ"]
        [:div.question
         [:p "Do you want to make more money?"]]
        [:div.answer
         [:p "Sure, we all do."]]]]}))
