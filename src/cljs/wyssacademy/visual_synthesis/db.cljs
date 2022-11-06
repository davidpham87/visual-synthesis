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
   {:src "img/artisanal-mines.webp" :style {:top "40%" :right "-5%" :zoom 0.125}}

   :biodiversity
   {:src "img/biodiversity.webp" :style {:top "40%" :right "-5%"}}

   :clove-based-agroforest
   {:src "img/clove-agf.webp" :style {:top "40%" :left "50%"}}

   :climate-change
   {:src "img/climate-change.webp" :style {:top "0%" :left "0%" :zoom 1.2}}

   :commercial-agriculture
   {:src "img/commercial-agriculture.webp" :style {:top "40%" :left "50%%" :zoom 0.15}}

   :cultural-ecosystem-services
   {:src "img/cultural-es.webp" :style {:top "40%" :left "50%%" :zoom 0.2}}

   :fallow-derived-vanilla-agroforest
   {:src "img/fallow-vanilla.webp" :style {:top "40%" :left "50%%" :zoom 0.1}}

   :forest-derived-vanilla-agroforest
   {:src "img/vanilla-forest.webp" :style {:top "40%" :left "50%%" :zoom 0.1}}

   :forest-fragment
   {:src "img/forest-fragment.webp" :style {:top "50%" :left "10%" :zoom 0.12}}

   :irrigated-rice-paddy
   {:src "img/rice-paddy-cut.webp" :style {:top "50%" :left "20%"
                                           :zoom 0.125}}

   :infrastructure-sm
   {:src "img/infrastructure-sm.webp" :style {:top "50%" :left "20%"
                                              :zoom 0.52}}
   :infrastructure
   {:src "img/infrastructure.webp" :style {:top "50%" :left "20%"
                                           :zoom 0.2}}

   :out-of-landscape-influences
   {:src "img/out-landscape-influences-4.webp"
    :style {:top "50%" :left "20%" :zoom 0.25}}

   :old-growth-forest
   {:src "img/old_growth_forest.png" :style {:top "60%" :left "-8%"}}

   :non-agricultural-income
   {:src "img/off-farm-income.webp" :style {:top "60%" :left "-8%"
                                            :zoom 0.065}}
   :pasture
   {:src "img/pasture.webp" :style {:top "65%" :left "35%" :zoom 0.12}}

   :protected-old-growth-forest
   {:src "img/protected-forest.webp" :style {:top "56%" :left "50%"}}

   :shifting-cultivation
   {:src "img/shifting-cultivation.webp" :style {:top "56%" :left "50%"
                                                 :zoom 0.15}}

   :subsistence-agriculture
   {:src "img/subsistence-agriculture.webp" :style {:top "56%" :left "50%"}}

   :rice-paddy
   {:src "img/rice-paddy.webp" :style {:top "65%" :left "35%" :zoom 0.10}}

   :regulating-ecosystem-services
   {:src "img/regulating-es.webp" :style {:top "56%" :left "50%" :zoom 0.1}}

   :unprotected-old-growth-forest
   {:src "img/unprotected-forest.webp" :style {:top "56%" :left "50%"}}

   :use-of-provisioning-ecosystem-services
   {:src "img/provision-services.webp" :style {:top "56%" :left "50%"}}

   :waterbodies
   {:src "img/water-bodies-4-3.webp"
    :style {:top "0%" :left "0%" :zoom 0.13}}

   :wellbeing
   {:src "img/wellbeing.webp" :style {:top "56%" :left "50%" :zoom 0.2}}})


(def categories
  (mapv
   (fn [m]
     (-> m
         (assoc :image (merge (get images (rand-nth (keys images))) (:image m)))
         (assoc-in [:image :style]
                   (merge {:top (str (+ 40 (rand-int 25)) "%")
                           :left (str (rand-int 90) "%")}
                          (get-in m [:image :style])))))
   [#_{:key :waterbodies, :label "Waterbodies"
     :image (:waterbodies images)
     :position {:x 0 :y 370}}

    {:key :climate-change, :label "Climate Change"
     :image (:climate-change images)
     :position {:x 0 :y 0}}

    {:key :artisanal-mines, :label "Artisanal mines"
     :image (:artisanal-mines images)
     :position {:x 650 :y 380}}

    {:key :biodiversity, :label "Biodiversity"
     :image (:biodiversity images)
     :position {:x 240 :y 90}}

    {:key :forest-derived-vanilla-agroforest,
     :label "Forest-derived vanilla agroforest"
     :image (:forest-derived-vanilla-agroforest images)
     :position {:x 830 :y 220}}

    {:key :clove-based-agroforest, :label "Clove-based agroforest"
     :image (:clove-based-agroforest images)
     :position {:x 300 :y 250}}

    {:key :commercial-agriculture,
     :label "Commercial agriculture"
     :image (:commercial-agriculture images)
     :position {:x 230 :y 690}}

    {:key :cultural-ecosystem-services,
     :label "Cultural ecosystem services"
     :image (:cultural-ecosystem-services images)
     :position {:x 870 :y 360}}

    {:key :forest-fragment, :label "Forest fragment"
     :image (:forest-fragment images)
     :position {:x 950 :y 650}}

    {:key :irrigated-rice-paddy, :label "Irrigated rice paddy"
     :image (:irrigated-rice-paddy images)
     :position {:x 440 :y 740}}

    {:key :non-agricultural-income,
     :label "Non Agricultural Income"
     :image (:non-agricultural-income images)
     :position {:x 420 :y 620}}

    {:key :pasture, :label "Pasture"
     :image (:pasture images)
     :position {:x 1140 :y 510}}

    {:key :protected-old-growth-forest, :label "Protected old-growth forest"
     :image (:protected-old-growth-forest images)
     :position {:x 0 :y 150}}

    {:key :infrastructure,
     :label "Infrastructure"
     :image (:infrastructure-sm images)
     :position {:x 520 :y 480}}

    {:key :regulating-ecosystem-services,
     :label "Regulating ecosystem services"
     :image (:regulating-ecosystem-services images)
     :position {:x 755 :y 570}}

    {:key :shifting-cultivation, :label "Shifting cultivation"
     :image (:shifting-cultivation images)
     :position {:x 40 :y 280}}

    {:key :subsistence-agriculture,
     :label "Subsistence agriculture"
     :image (:subsistence-agriculture images)
     :position {:x 10 :y 550}}

    {:key :unprotected-old-growth-forest, :label "Unprotected old-growth forest"
     :image (:unprotected-old-growth-forest images)
     :position {:x 440 :y 130}}

    {:key :use-of-provisioning-ecosystem-services,
     :label "Use of provisioning ecosystem services"
     :image (:use-of-provisioning-ecosystem-services images)
     :position {:x 1050 :y 265}}

    {:key :out-of-landscape-influences,
     :label "Out of landscape influences"
     :image (:out-of-landscape-influences images)
     :position {:x 1000 :y -10}}

    {:key :wellbeing,
     :label "Wellbeing"
     :image (:wellbeing images)
     :position {:x 280 :y 400}}

    ;; order matter for the z position for the hover
    {:key :fallow-derived-vanilla-agroforest,
     :label "Fallow-derived vanilla agroforest"
     :image (:fallow-derived-vanilla-agroforest images)
     :position {:x 650 :y 215}}]))

(def categories-keys (mapv :key categories))
(def categories-map (reduce (fn [m {:keys [key label]}]
                              (assoc m key label)) {} categories))

(def categories-map-data (reduce (fn [m {:keys [key] :as data}]
                                   (assoc m key data)) {} categories))

(def categories-react-flow
  (mapv (fn [m] {:id (name (or (:key m) "unfounded"))
                 :type :image
                 :data {:key (:key m)
                        :src (-> m :image :src)
                        :zoom (or (-> m :image :style :zoom) 0.12)}
                 :position (or (:position m)
                               {:x (+ 20 (rand-int 800))
                                :y (+ 200 (rand-int 200))})})
        categories))


(comment
  (count categories)
  (vec (sort (mapv :key categories)))

)
