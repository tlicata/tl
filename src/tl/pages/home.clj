(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
		[tl.pages.global :only [page]]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "( Golfer | Pingponger )"
										  "( Lockport, NY | Washingon, DC )"])])

(defn home-page [request]
  (page {:title "Home"
		 :html [welcome-blurb]
		 :request request}))

(defn contact-page [request]
  (page {:title "Contact"
		 :html [[:div [:p "You can write to me at tim at this domain name."]]]
		 :request request}))
