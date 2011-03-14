(ns tl.pages.home
  (:use [clojure.contrib.str-utils :only [str-join]]
		[hiccup.page-helpers :only [link-to]]
		[tl.pages.global :only [swfobject]])
  (:require [appengine-magic.services.user :as us]
			[com.reasonr.scriptjure :as script]))

(def welcome-blurb [:p (str-join "  -  " ["Tim Licata"
										  "Programmer"
										  "( Golfer | Pingponger )"
										  "( Lockport, NY | Washingon, DC )"])])

(defn home-page []
  {:title ["Home"]
   :body [welcome-blurb]})

(defn contact-page []
  {:title ["Contact"]
   :body [[:div [:p "You can write to me at tim at this domain name."]]]})

(defn admin-page []
  {:title ["Admin"]
   :body [[:div
		   [:p "You're an admin baby"]
		   (link-to (us/logout-url) "Log out")]]})

(defn login-page []
  {:title ["Login"]
   :body [[:div [:p (if (us/user-logged-in?)
					  (link-to (us/logout-url) "Log out")
					  (link-to (us/login-url) "Log in"))]]]})

(def videos [{:title "Ronald Jenkees - Laid Back Organ Jam (for my peeps)" :id "zuJW7H08HrQ"}
			 {:title "Ronald Jenkees - All of my Love" :id "j-ryKQx4DdQ"}
			 {:title "Tragically Hip - New Orleans is Sinking (w/ Ahead by a Century)" :id "AZwm_OKh6bw"}
			 {:title "Tragically Hip - Fully Completely" :id "pEGyKECUh80"}
			 {:title "Tragically Hip - 38 Years Old" :id "rsj9fXH2Psw"}
			 {:title "Ratatat - Seventeen Years" :id "z6GbC2F5RfU"}
			 {:title "Ratatat - Mi Viejo + Mirando" :id "oM3MdixTdfA"}
			 {:title "Kid Cudi ft. Ratatat - Pursuit of Happiness" :id "NzT8fHpAU3w"}
			 {:title "Handsome Jack - 5-1-08 Camera 1 (and only)" :id "4tc_GxVtcPE"}
			 {:title "Handsome Jack - last 1/2 of Love Machine" :id "5QFnWu0WRnY"}
			 {:title "Neil Young - Heart of Gold" :id "Eh44QPT1mPE"}
			 {:title "Neil Young & Pearl Jam" :id "PTTsyk-pyd8"}
			 {:title "Rush - Subdivisions" :id "DNoMCh6okuo"}
			 {:title "Rush - Tom Sawyer (featuring South Park)" :id "JFGVDWc_5Q8"}
			 {:title "Rush - Workin Man" :id "EYRYGr9vynw"}
			 {:title "MIKE V SKATES LOCKPORT" :id "xxBdPUokZHo"}])

(def embed-url "http://www.youtube.com/v/")
(def link-url "http://www.youtube.com/watch?v=")

(defn youtube-list []
  (vec (conj (map (fn [v]
					[:li (link-to (:id v) (:title v))])
				  videos)
			 :ul)))

(defn youtubes [ & [video]]
  {:js #{"/js/youtubes.js" swfobject}
   :title ["Hello Youtubes"]
   :body [[:div#youtubes
		   [:div.left (youtube-list)]
		   [:div.right [:div#swf-div]]]
		  (when video
			[:script
			 (script/js (tl.youtubes.play (script/clj (str embed-url video))
										  (script/clj 1)
										  (script/clj 1)))])]})
