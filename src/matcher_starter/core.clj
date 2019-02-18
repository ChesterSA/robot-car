(ns matcher-starter.core
  (:require [org.clojars.cognesence.breadth-search.core :refer :all]
            [org.clojars.cognesence.matcher.core :refer :all]
            [org.clojars.cognesence.ops-search.core :refer :all]))

(use 'org.clojars.cognesence.ops-search.core)
(use 'org.clojars.cognesence.breadth-search.core)
(use 'org.clojars.cognesence.matcher.core)

(defn gen-moves [n]
   (list (* n 10) (/ n 2) (+ n 5) (- n 3)))

(def world
  '#{(connects c1 j1)
     (connects c2 j1)
     (connects c3 j1 )
     (connects c4 j1)
     (connects j1 c1)
     (connects j1 c2)
     (connects j1 c3)
     (connects j1 c4)
     (at b1 c1)
     (at b2 c2)
     (at b3 c3)
     (at b4 c4)
     (has c1 b1)
     (has c2 b2)
     (has c3 b3)
     (has c4 b4)
     }
  )

(def state1
  '#{(manipulable box)
     (at box b1)
     (car robot)
     (at robot c4)
     (holds robot nothing)
     }
  )

(def ops
  '{pickup {:pre ( (car ?agent)
                   (manipulable ?obj)
                   (at ?agent ?corridor)
                   (at ?bay ?corridor)
                   (at ?obj ?bay)
                   (holds ?agent nothing))
            :add ( (holds ?agent ?obj))
            :del ( (at ?obj  ?bay)
                   (holds ?agent nothing))
            :txt (pickup ?obj from ?bay)
            :cmd [grasp ?obj]
            }
    drop    {:pre ( (at ?agent ?corridor)
                    (holds ?agent ?obj)
                    (at ?bay ?corridor)
                    )
             :add ( (holds ?agent nothing)
                    (at ?obj ?bay))
             :del ((holds ?agent ?obj))
             :txt (drop ?obj at ?bay)
             :cmd [drop ?obj]
             }
    move {:pre ( (car ?agent)
                 (at ?agent ?p1)
                 (connects ?p1 ?p2)
                 )
          :add ((at ?agent ?p2))
          :del ((at ?agent ?p1))
          :txt (move ?p1 to ?p2)
          :cmd [move ?p2]
          }
    })
