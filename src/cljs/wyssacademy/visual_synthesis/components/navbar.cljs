(ns wyssacademy.visual-synthesis.components.navbar
  (:require
   [wyssacademy.visual-synthesis.utils :refer (defc)]
   ["@material-tailwind/react/Nav$default" :as mt-nav]
   ["@material-tailwind/react/NavLink$default" :as mt-nav-link]
   ["@material-tailwind/react/Navbar$default" :as mt-navbar]
   ["@material-tailwind/react/NavbarContainer$default" :as mt-navbar-container]
   ["@material-tailwind/react/NavbarWrapper$default" :as mt-navbar-wrapper]))

(defc link [m s]
  [:div.cursor-pointer.hover:underline.text-xl m s])

(defn navbar []
  [:> mt-navbar {:color "teal" :navbar true :class "h-36"}
   [:> mt-navbar-container
    [:> mt-navbar-wrapper
     [:> mt-nav
      [:div {:class "flex flex-col z-50 lg:flex-row lg:items-center"}
       [:> mt-nav-link {:ripple "light"}
        [link "Visual Synthesis"]]
       [:> mt-nav-link {:ripple "light"}
        [link  "Intro"]]
       [:> mt-nav-link {:ripple "light"}
        [link  "About"]]]]]]])
