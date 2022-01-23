(ns wyssacademy.visual-synthesis.db
  (:require
   [datascript.core :as d]))

(def schema
  #:interaction{:id {:db/unique :db.unique/identity}
                :in {:db/index true}
                :out {:db/index true}})

(def empty-ds (d/create-conn schema))

(def default-db
  {:project "visual-synthesis"
   :ds {:interaction empty-ds}
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
  (mapv #(-> %
             (assoc :image (get images (rand-nth (keys images))))
             (assoc-in [:image :style] {:top (str (+ 40 (rand-int 25)) "%")
                                        :left (str (rand-int 90) "%")
                                        :zoom (+ 0.25 (* 0.5 (rand)))}))
       [{:key :unprotected-old-growth-forest, :label "Unprotected old-growth forest"}
        {:key :protected-old-growth-forest, :label "Protected old-growth forest"}
        {:key :forest-fragment, :label "Forest fragment "}
        {:key :forest-derived-vanilla-agroforest,
         :label "Forest-derived vanilla agroforest"}
        {:key :shifting-cultivation, :label "Shifting cultivation"}
        {:key :fallow-derived-vanilla-agroforest,
         :label "Fallow-derived vanilla agroforest"}
        {:key :irrigated-rice-paddy, :label "Irrigated rice paddy"}
        {:key :clove-based-agroforest, :label "Clove-based agroforest"}
        {:key :artisanal-mines, :label "Artisanal mines"}
        {:key :pasture, :label "Pasture"}
        {:key :waterbodies, :label "Waterbodies"}
        {:key :wellbeing, :label "Wellbeing"}
        {:key :biodiversity, :label "Biodiversity"}
        {:key :cultural-ecosystem-services, :label "Cultural ecosystem services"}
        {:key :regulating-ecosystem-services, :label "Regulating ecosystem services"}
        {:key :use-of-provisioning-ecosystem-services,
         :label "Use of provisioning ecosystem services"}
        {:key :commercial-agriculture, :label "Commercial agriculture"}
        {:key :off-land-income, :label "Off-land income"}
        {:key :subsistence-agriculture, :label "Subsistence agriculture"}
        {:key :out-of-land-influences, :label "Out-of-land influences"}]))



(def categories-keys (mapv :key categories))
(def categories-map (reduce (fn [m {:keys [key label]}]
                              (assoc m key label)) {} categories))

(comment
  (count categories)
  )
