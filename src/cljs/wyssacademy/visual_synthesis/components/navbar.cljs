(ns wyssacademy.visual-synthesis.components.navbar
  (:require
   ["@material-tailwind/react/Nav$default" :as mt-nav]
   ["@material-tailwind/react/NavLink$default" :as mt-nav-link]
   ["@material-tailwind/react/Navbar$default" :as mt-navbar]
   ["@material-tailwind/react/NavbarContainer$default" :as mt-navbar-container]
   ["@material-tailwind/react/NavbarWrapper$default" :as mt-navbar-wrapper]
   [re-frame.core :as rf]
   [wyssacademy.visual-synthesis.utils :refer (defc)]))

(defc link [m s]
  [:div.cursor-pointer.hover:underline.text-xl m s])

(defn navbar [{:keys [event]}]
  [:> mt-navbar {:color "teal" :navbar true :class "h-36"}
   [:> mt-navbar-container
    [:> mt-navbar-wrapper
     [:> mt-nav
      [:div {:class [:flex :z-50 :lg:items-center]}
       [:> mt-nav-link {:ripple "light"
                        :on-click #(rf/dispatch (conj event :visual-synthesis))}
        [link "Visual Synthesis"]]
       #_[:> mt-nav-link {:ripple "light"
                        :on-click #(rf/dispatch (conj event :intro))}
        [link  "Intro"]]
       [:> mt-nav-link {:ripple "light"
                        :on-click #(rf/dispatch (conj event :about))}
        [link  "About"]]]]]]])
