(ns wyssacademy.visual-synthesis.components.dropdown
  (:require
   ["@material-tailwind/react/Dropdown$default" :as mui-dropdown]
   ["@material-tailwind/react/DropdownItem$default" :as mui-dropdown-item]
   [reagent.core :as reagent]))

(def dropdown (reagent/adapt-react-class mui-dropdown))
(def item (reagent/adapt-react-class mui-dropdown-item))
