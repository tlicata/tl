(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
		[tl.pages.global :only [page]]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "Golfer"
										  "Pingponger"
										  "Lockport, NY & Washingon, DC"])])

(defn home-page [request]
  (page {:title "Home"
		 :html [welcome-blurb]}))
