(ns wyssacademy.visual-synthesis.about
  (:require
   [wyssacademy.visual-synthesis.components.typography :as typography]))

(defn about []
  [:div.w-full.justify-center.grid.px-10.pb-10.transition-opacity.opacity-100
   [:div {:class ["text-justify" "max-w-xl"]}
    [typography/paragraph
     "Many social-ecological systems are in an unsustainable state. Bringing
     together disjunct published findings on complex interactions in
     social-ecological systems may enable the identification of leverage points
     for transformations towards sustainability. However, such
     interdisciplinary synthesis studies on specific regional social-ecological
     systems remain rare."]
    [typography/paragraph
     "Here, we pair a review of systematically identified studies with a
     cross-impact analysis to create an interactive visual social-ecological
     systems synthesis on conflicts and synergies between land use,
     biodiversity conservation, and human wellbeing in north-eastern
     Madagascar."]
    [typography/paragraph
     [:a {:href "https://visualsynthesis.wyssacademy.org"
          :style {:color "blue"}}
      "The interactive visual synthesis"]
     " depicts an archetypical regional landscape with 22 factors comprising
     physical landscape elements, ecosystem services, wellbeing, human
     activities, and telecouplings. To understand the connections between these
     factors, we assess directional causal links based on literature
     sources. The visual synthesis shows that research has so far focused on
     links between land use and biodiversity while links to human wellbeing
     were studied more seldomly. We then identify chains and cycles that emerge
     from the links between factors and rate them based on their plausibility
     and relevance."]
    [typography/paragraph
     "All eight top-rated chains and cycles relate to subsistence and
     commercial agriculture, revealing promising leverage points at which
     interventions could improve outcomes for biodiversity and wellbeing. In
     sum, we show how interactive visual syntheses can be a useful way to make
     disjunct published findings on regional social-ecological systems more
     accessible, to find research gaps, and to identify leverage points for
     sustainability transformations."]
    [:div.py-3]
    [typography/paragraph
     [:b "Please cite this work as: "]
     [:br]
     [:div.py-1]
     "Martin, D.A., Pham- Truffert, M., Gillham, L., Andriamihaja, O.R.,
     Andriatsitohaina, R.N.N., Diebold, C.L., Fulgence, T.R., Kellner, E.,
     Llopis, J.C., Messerli, P., Rakotomalala, A.N.A., Raveloaritiana, E.,
     Wurz, A., Zaehringer, J.G., & Heinimann, A."
     [:br]
     "Interactive visual syntheses for social-ecological systems understanding"
     [:br]
     [:div.text-left
      [:em "Socio-Environmental Systems Modelling, vol. 6, 18637, 2024, "
       [:a {:href "https://doi.org/10.18174/sesmo.18637"
        :style {:color "blue"}} "doi:10.18174/sesmo.18637"]]]]
    [:div.py-3]
    [typography/paragraph
     "The code of the visual synthesis is " [:a {:href "https://github.com/davidpham87/visual-synthesis"
               :style {:color "blue"}} "open-source"] "."]]])
