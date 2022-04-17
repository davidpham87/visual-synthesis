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
  {:artisanal-mines
   {:src "img/artisanal-mines.webp" :style {:top "40%" :right "-5%"}}

   :biodiversity
   {:src "img/biodiversity.webp" :style {:top "40%" :right "-5%"}}

   :clove-based-agroforest
   {:src "img/clove-agf.webp" :style {:top "40%" :left "50%%"}}

   :commercial-agriculture
   {:src "img/commercial-agriculture.webp" :style {:top "40%" :left "50%%"}}

   :cultural-ecosystem-services
   {:src "img/cultural-es-draft.webp" :style {:top "40%" :left "50%%"}}

   :fallow-derived-vanilla-agroforest
   {:src "img/fallow-vanilla.webp" :style {:top "40%" :left "50%%"}}

   :forest-fragment
   {:src "img/forest-fragment.webp" :style {:top "50%" :left "10%"}}

   :irrigated-rice-paddy
   {:src "img/rice.webp" :style {:top "50%" :left "20%"
                                 :zoom 0.2}}

   :out-of-land-influences
   {:src "img/out-of-land-income.webp" :style {:top "50%" :left "20%"}}

   :old-growth-forest
   {:src "img/old_growth_forest.png" :style {:top "60%" :left "-8%"}}

   :off-land-income
   {:src "img/off-land-income.webp" :style {:top "60%" :left "-8%"
                                            :zoom 0.065}}

   :pasture
   {:src "img/pasture.webp" :style {:top "65%" :left "35%"}}

   :protected-old-growth-forest
   {:src "img/protected-forest.webp" :style {:top "56%" :left "50%"}}

   :shifting-cultivation
   {:src "img/shifting-cultivation.webp" :style {:top "56%" :left "50%"}}

   :subsistence-agriculture
   {:src "img/subsistence-agriculture.webp" :style {:top "56%" :left "50%"}}

   :rice-paddy
   {:src "img/rice.webp" :style {:top "65%" :left "35%"}}

   :regulating-ecosystem-services
   {:src "img/regulating-es.webp" :style {:top "56%" :left "50%"}}

   :water-body
   {:src "img/regulating-es.webp" :style {:top "56%" :left "50%"}}

   :unprotected-old-growth-forest
   {:src "img/unprotected-forest.webp" :style {:top "56%" :left "50%"}}

   :use-of-provisioning-ecosystem-services
   {:src "img/provision-services.webp" :style {:top "56%" :left "50%"}}

   :wellbeing
   {:src "img/wellbeing.webp" :style {:top "56%" :left "50%" :zoom 0.275}}})


(def categories
  (mapv
   (fn [m]
     (-> m
         (assoc :image (merge (get images (rand-nth (keys images))) (:image m)))
         (assoc-in [:image :style]
                   (merge {:top (str (+ 40 (rand-int 25)) "%")
                           :left (str (rand-int 90) "%")}
                          (get-in m [:image :style])))))
   [{:key :artisanal-mines, :label "Artisanal mines"
     :image (:artisanal-mines images)
     :position {:x 610 :y 500}}

    {:key :biodiversity, :label "Biodiversity"
     :image (:biodiversity images)
     :position {:x 710 :y 125}}

    {:key :clove-based-agroforest, :label "Clove-based agroforest"
     :image (:clove-based-agroforest images)
     :position {:x 10 :y 110}}

    {:key :commercial-agriculture,
     :label "Commercial agriculture"
     :image (:commercial-agriculture images)
     :position {:x 160 :y 430}}

    {:key :cultural-ecosystem-services,
     :label "Cultural ecosystem services"
     :image (:cultural-ecosystem-services images)
     :position {:x 740 :y 100}}

    {:key :fallow-derived-vanilla-agroforest,
     :label "Fallow-derived vanilla agroforest"
     :image (:fallow-derived-vanilla-agroforest images)
     :position {:x 200 :y 200}}

    {:key :forest-derived-vanilla-agroforest,
     :label "Forest-derived vanilla agroforest"
     :image (:forest-fragment images)
     :position {:x -10 :y 165}}

    {:key :forest-fragment, :label "Forest fragment"
     :image (:forest-fragment images)
     :position {:x 530 :y 530}}

    {:key :irrigated-rice-paddy, :label "Irrigated rice paddy"
     :image (:irrigated-rice-paddy images)
     :position {:x 740 :y 400}}

    {:key :off-land-income,
     :label "Off-land income"
     :image (:off-land-income images)
     :position {:x 400 :y 450}}

    {:key :pasture, :label "Pasture"
     :image (:pasture images)
     :position {:x 1080 :y 550}}

    {:key :subsistence-agriculture,
     :label "Subsistence agriculture"
     :image (:subsistence-agriculture images)
     :position {:x 740 :y 500}}

    {:key :unprotected-old-growth-forest, :label "Unprotected old-growth forest"
     :image (:unprotected-old-growth-forest images)
     :position {:x 340 :y 85}}

    {:key :protected-old-growth-forest, :label "Protected old-growth forest"
     :image (:protected-old-growth-forest images)
     :position {:x 620 :y 180}}

    {:key :shifting-cultivation, :label "Shifting cultivation"
     :image (:shifting-cultivation images)
     :position {:x -10 :y 165}}



    {:key :waterbodies, :label "Waterbodies"
     :image (:water-body images)
     :position {:x 520 :y 500}}



    {:key :regulating-ecosystem-services,
     :label "Regulating ecosystem services"
     :image (:regulating-ecosystem-services images)
     :position {:x 740 :y 500}}

    {:key :use-of-provisioning-ecosystem-services,
     :label "Use of provisioning ecosystem services"
     :image (:use-of-provisioning-ecosystem-services images)
     :position {:x 740 :y 500}}







    {:key :out-of-land-influences,
     :label "Out-of-land influences"
     :image (:out-of-land-influences images)
     :position {:x 740 :y 500}}

    {:key :wellbeing,
     :label "Wellbeing"
     :image (:wellbeing images)
     :position {:x 250 :y 190}}]))

(def categories-keys (mapv :key categories))
(def categories-map (reduce (fn [m {:keys [key label]}]
                              (assoc m key label)) {} categories))

(def categories-react-flow
  (mapv (fn [m] {:id (name (or (:key m) "unfounded"))
                 :type :image
                 :data {:key (:key m)
                        :src (-> m :image :src)
                        :zoom (or (-> m :image :style :zoom) 0.1)}
                 :position (or (:position m)
                               {:x (+ 20 (rand-int 800))
                                :y (+ 200 (rand-int 200))})})
        categories))


(comment
  (count categories)
  (vec (sort (mapv :key categories)))
  )
