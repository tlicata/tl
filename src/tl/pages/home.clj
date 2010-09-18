(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
		[tl.pages.global :only [page]]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Lockport, NY"
										  "Programmer"
										  "Golfer"
										  "Cool Guy"])])

(defn home-page [request]
  (page {:title "Home"
		 :html [welcome-blurb]}))
