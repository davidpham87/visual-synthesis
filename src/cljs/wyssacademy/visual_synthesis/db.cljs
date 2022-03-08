(ns wyssacademy.visual-synthesis.db
  (:require
   [reagent.core :as reagent]
   [datascript.core :as d]))

(def schema
  #:interaction{:id {:db/unique :db.unique/identity}
                :in {:db/index true}
                :out {:db/index true}})

(def empty-ds (d/create-conn schema))

(def default-db
  {:project "visual-synthesis"
   :ds {:interaction empty-ds}
   :wyssacademy.visual-synthesis.components.tabs/tab
   {:wyssacademy.visual-synthesis.views/info :landscape-elements}
   :user-input {}
   :ui-states {}})

(def images
  {:biodiversity      {:src "img/biodiversity.png"
                       :style {:top "40%" :right "-5%"}}
   :forest-fragment   {:src "img/forest_fragment.png"
                       :style {:top "50%" :left "10%"}}
   :old-growth-forest {:src "img/old_growth_forest.png"
                       :style {:top "60%" :left "-8%"}}
   :rice-paddy        {:src "img/rice_paddy.png"
                       :style {:top "65%" :left "35%"}}
   :water-body        {:src "img/water_body.png"
                       :style {:top "56%" :left "50%"}}})

(def categories
  (mapv
   (fn [m]
     (-> m
         (assoc :image (merge (get images (rand-nth (keys images))) (:image m)))
         (assoc-in [:image :style]
                   (merge {:top (str (+ 40 (rand-int 25)) "%")
                           :left (str (rand-int 90) "%")
                           :zoom (+ 0.25 (* 0.5 (rand)))}
                          (get-in m [:image :style])))))
   [{:key :unprotected-old-growth-forest, :label "Unprotected old-growth forest"
     :image (:old-growth-forest images)
     :position {:x 340 :y 85}}

    {:key :protected-old-growth-forest, :label "Protected old-growth forest"
     :image (:old-growth-forest images)
     :position {:x 620 :y 180}}

    {:key :forest-fragment, :label "Forest fragment"
     :image (:forest-fragment images)
     :position {:x 380 :y 500}}

    {:key :forest-derived-vanilla-agroforest,
     :label "Forest-derived vanilla agroforest"
     :image (:forest-fragment images)
     :position {:x -10 :y 165}}

    {:key :shifting-cultivation, :label "Shifting cultivation"}

    {:key :fallow-derived-vanilla-agroforest,
     :label "Fallow-derived vanilla agroforest"
     :image (:forest-fragment images)
     :position {:x 800 :y 220}}

    {:key :irrigated-rice-paddy, :label "Irrigated rice paddy"
     :image (:rice-paddy images)
     :position {:x 320 :y 250}}

    {:key :clove-based-agroforest, :label "Clove-based agroforest"
     :image (:old-growth-forest images)
     :position {:x 250 :y 150}}

    {:key :artisanal-mines, :label "Artisanal mines"
     :image (:biodiversity images)
     :position {:x 740 :y 500}}

    {:key :pasture, :label "Pasture"}

    {:key :waterbodies, :label "Waterbodies"
     :image (:water-body images)
     :position {:x 520 :y 500}}

    {:key :wellbeing, :label "Wellbeing"
     :image (:water-body images)
     :position {:x 850 :y 400}}

    {:key :biodiversity, :label "Biodiversity"
     :image (:biodiversity images)
     :position {:x 800 :y 320}}

    {:key :cultural-ecosystem-services,
     :label "Cultural ecosystem services"}

    {:key :regulating-ecosystem-services,
     :label "Regulating ecosystem services"}

    {:key :use-of-provisioning-ecosystem-services,
     :label "Use of provisioning ecosystem services"}

    {:key :commercial-agriculture,
     :label "Commercial agriculture"}

    {:key :off-land-income,
     :label "Off-land income"}

    {:key :subsistence-agriculture,
     :label "Subsistence agriculture"}

    {:key :out-of-land-influences,
     :label "Out-of-land influences"}]))

(def categories-keys (mapv :key categories))
(def categories-map (reduce (fn [m {:keys [key label]}]
                              (assoc m key label)) {} categories))

(def categories-react-flow
  (mapv (fn [m] {:id (name (or (:key m) "unfounded"))
                 :type :image
                 :data {:key (:key m)
                        :src (-> m :image :src)
                        :zoom 0.65}
                 :position (or (:position m)
                               {:x (+ 20 (rand-int 800))
                                :y (+ 200 (rand-int 200))})})
        categories))


(comment
  (count categories)
  )
