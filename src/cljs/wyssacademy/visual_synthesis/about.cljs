(ns wyssacademy.visual-synthesis.about
  (:require
   [wyssacademy.visual-synthesis.components.typography :as typography]))

(defn about []
  [:div.w-full.justify-center.grid.pb-10.transition-opacity.opacity-100
   [:div.w-96.text-justify
    [typography/paragraph "The synthesis of scientific knowledge becomes ever more important given
  the exponentially increasing number of published research articles and
  social, ecological and climate crises. To date, most syntheses have focused
  on few variables across regions, while interdisciplinary syntheses on local
  social-ecological systems remain rare."]
    [typography/paragraph "At Wyss Academy, we believe that such social-ecological systems
  syntheses may be well suited for reaching diverse audiences in an accessible
  way and to offer an entry point for targeted interventions. To illustrate the
  case, we have conducted a systematic review resulting in an interactive
  visual synthesis on the nexus between land use, conservation, and
  human-wellbeing in north-eastern Madagascar."]
    [typography/paragraph "This visual synthesis depicts an archetypical landscape in north-eastern
  Madagascar. Within the landscape, 22 elements illustrate biophysical
  landscape elements, ecosystem services, wellbeing, human activities, and
  telecouplings. Links between the elements – each supported by literature
  sources – highlight the connections between elements."]
    [typography/h6 "Please note that this is a Beta-Version. We are working on a full documentation of the goals, methods, and outcomes."]]])
