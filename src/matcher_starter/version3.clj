(ns matcher-starter.version3
  (:require [org.clojars.cognesence.breadth-search.core :refer :all]
            [org.clojars.cognesence.matcher.core :refer :all]
            [org.clojars.cognesence.ops-search.core :refer :all]))

(use 'org.clojars.cognesence.ops-search.core)
(use 'org.clojars.cognesence.breadth-search.core)
(use 'org.clojars.cognesence.matcher.core)

;(time ((ops-search state-all '((stores b6 box)) ops :world world-all) :txt) )

(defn gen-moves2 [n]
  (list (* n 10) (/ n 2) (+ n 5) (- n 3)))

(def world-all
  '#{
     (connects b1 j1)
     (connects b2 j1)
     (connects b3 j1)
     (connects b4 j1)
     (orientation b1 horizontal)
     (orientation b2 vertical)
     (orientation b3 horizontal)
     (orientation b4 vertical)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b2)
     (contains z1 b3)
     (contains z1 b4)

     (connects b4 e1)
     (connects b5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (connects b5 j2)
     (connects b6 j2)
     (orientation b5 vertical)
     (orientation b6 horizontal)
     (contains z2 b5)
     (contains z2 b6)
     (contains z2 j2)
     }
  )

(def world-one
  '#{
     (connects b1 j1)
     (connects b4 j1)
     (orientation b1 horizontal)
     (orientation b4 vertical)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b4)
     }
  )

(def world-two
  '#{
     (connects b1 j1)
     (connects b4 j1)
     (orientation b1 horizontal)
     (orientation b4 vertical)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b4)

     (connects b4 e1)
     (connects b5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (connects b5 j2)
     (orientation b5 vertical)
     (contains z2 b5)
     (contains z2 j2)
     }
  )

(def world-three
  '#{
     (connects b1 j1)
     (connects b4 j1)
     (orientation b1 horizontal)
     (orientation b4 vertical)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b4)

     (connects b4 e1)
     (connects b5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (connects b5 j2)
     (connects b6 j2)
     (orientation b5 vertical)
     (orientation b6 horizontal)
     (contains z2 b5)
     (contains z2 b6)
     (contains z2 j2)
     }
  )

(def state-all
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (orientation robot-1 vertical)
     (at robot-1 b2)
     (contains z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (orientation robot-2 vertical)
     (at robot-2 b5)
     (contains z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def state-one
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (at robot-1 j1)
     (contains z1 robot-1)
     (holds robot-1 nothing)
     (orientation robot-1 vertical)
     }
  )

(def state-two
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (orientation robot-1 vertical)
     (at robot-1 j1)
     (contains z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (orientation robot-2 vertical)
     (at robot-2 j2)
     (contains z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def state-three
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (orientation robot-1 horizontal)
     (at robot-1 j1)
     (contains z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (orientation robot-2 vertical)
     (at robot-2 j2)
     (contains z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def ops
  '{collect-stock {:pre ( (car ?agent)
                          (manipulable ?obj)
                          (at ?agent ?bay)
                          (stores ?bay ?obj)
                          (holds ?agent nothing))
                   :add ( (holds ?agent ?obj))
                   :del ( (stores ?bay ?obj)
                          (holds ?agent nothing))
                   :txt (?agent collects ?obj from ?bay)
                   :cmd [collect-stock ?obj]
                   }
    deposit-stock {:pre ( (at ?agent ?bay)
                          (holds ?agent ?obj)
                          )
                   :add ( (holds ?agent nothing)
                          (stores ?bay ?obj))
                   :del ( (holds ?agent ?obj)
                          (stores ?bay ?nothing))
                   :txt (?agent deposits ?obj at ?bay)
                   :cmd [deposit-stock ?obj]
                   }
    move-to-bay {:pre ( (car ?agent)
                        (at ?agent ?junction)
                        (connects ?bay ?junction)
                        (orientation ?agent ?orientation)
                        (orientation ?bay ?orientation)
                        (contains ?zone ?agent)
                        (contains ?zone ?bay)
                        )
                 :add ((at ?agent ?bay))
                 :del ((at ?agent ?junction))
                 :txt (move ?agent to B- ?bay)
                 :cmd [B-move ?agent to ?bay]
                 }
    move-to-junction {:pre ( (car ?agent)
                             (at ?agent ?place)
                             (connects ?place ?junction)
                             (contains ?zone ?agent)
                             (contains ?zone ?junction)
                             )
                      :add ((at ?agent ?junction))
                      :del ((at ?agent ?place))
                      :txt (move ?agent from P- ?place to J- ?junction)
                      :cmd [J-move ?agent to ?junction]
                      }
    rotate-car {:pre ( (at ?agent ?junction)
                       (holds ?agent ?item)
                       (connects ?bay ?junction)
                       (orientation ?bay ?orientation1)
                       (orientation ?agent ?orientation2)
                       )
                :add ((orientation ?agent ?orientation1))
                :del ((orientation ?agent ?orientation2))
                :txt (rotate ?agent from ?orientation2 to ?orientation1)
                :cmd [rotate ?agent]
                }
    })