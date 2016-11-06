(ns clojure-noob.core
  (:gen-class))

(defn my-reduce
  [reducer initial collection]
  (if (empty? collection)
    initial
    (my-reduce reducer (reducer initial (first collection)) (rest collection))))

(def asym-hobbit-parts
  [{:name "head" :size 3}
   {:name "left-eye" :size 1}
   {:name "left-ear" :size 1}
   {:name "mouth" :size 1}
   {:name "nose" :size 1}
   {:name "neck" :size 2}
   {:name "left-shoulder" :size 3}
   {:name "left-upper-arm" :size 3}
   {:name "chest" :size 10}
   {:name "back" :size 10}
   {:name "left-forearm" :size 3}
   {:name "abdomen" :size 6}
   {:name "left-kidney" :size 1}
   {:name "left-hand" :size 2}
   {:name "left-knee" :size 2}
   {:name "left-thigh" :size 4}
   {:name "left-lower-leg" :size 3}
   {:name "left-achilles" :size 1}
   {:name "left-foot" :size 2}])

(defn matching-part
  [{:keys [name size]}]
  {:name (clojure.string/replace name #"^left-" "right-")
   :size size})

(defn symmetrize-body-parts
  "Takes an asymmetrical set of body parts and returns a symmetrical set"
  [asymmetrical-body-parts]
  (my-reduce 
    (fn [acc next]
      (into acc
        (set [next (matching-part next)])))
    []
    asymmetrical-body-parts))

(defn symmetrize
  "Takes an asymmetrical set of body parts and returns a symmetrical set"
  [asym-body-parts]
  (loop [remaining-parts asym-body-parts
         symmetrized-parts []]
    (if (empty? remaining-parts)
      symmetrized-parts
      (let [[part & other-parts] remaining-parts]
        (recur
          other-parts
          (into symmetrized-parts (set [part (matching-part part)])))))))

(defn dec-maker
  [number]
  (fn
    [x]
    (- x number)))

(defn mapset
  [f collection]
  (loop [remaining collection
         result #{}]
    (if (empty? remaining)
      result
      (let [[next & otherRemaining] remaining]
        (recur
          otherRemaining
          (into result #{(f next)}))))))

(defn mapset2
  [f collection]
  (reduce
    (fn [accumulator next]
      (into accumulator #{(f next)}))
    #{}
    collection))           

(defn -main
  []
  (println
    (mapset2
      (fn [num] (* num 2))
      [1 2 3 3])))
 