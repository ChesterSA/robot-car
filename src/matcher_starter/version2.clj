(ns matcher-starter.version2
  (:require [org.clojars.cognesence.breadth-search.core :refer :all]
            [org.clojars.cognesence.matcher.core :refer :all]
            [org.clojars.cognesence.ops-search.core :refer :all]))

(use 'org.clojars.cognesence.ops-search.core)
(use 'org.clojars.cognesence.breadth-search.core)
(use 'org.clojars.cognesence.matcher.core)

;(ops-search state1 '((on book bench)) ops)

(defn gen-moves [n]
  (list (* n 10) (/ n 2) (+ n 5) (- n 3)))

(def world-junc
  '#{
     (connects c1 j1)
     (connects c4 j1)
     (has c1 b1)
     (has c4 b4)
     (orientation c1 vertical)
     (orientation c4 vertical)
     (contains z1 c1)
     (contains z1 c4)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b4)}
  )

(def world-exchange
  '#{
     (has c4 b4)
     (orientation c4 vertical)
     (contains z1 c4)
     (contains z1 b4)

     (has c4 e1)
     (has c5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (has c5 b5)
     (orientation c5 vertical)
     (contains z2 c5)
     (contains z2 b5)}
  )

(def world-all
  '#{
     (connects c1 j1)
     (connects c2 j1)
     (connects c3 j1)
     (connects c4 j1)
     (has c1 b1)
     (has c2 b2)
     (has c3 b3)
     (has c4 b4)
     (orientation c1 horizontal)
     (orientation c2 vertical)
     (orientation c3 horizontal)
     (orientation c4 vertical)
     (contains z1 c1)
     (contains z1 c2)
     (contains z1 c3)
     (contains z1 c4)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b2)
     (contains z1 b3)
     (contains z1 b4)

     (has c4 e1)
     (has c5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (connects c5 j2)
     (connects c6 j2)
     (has c5 b5)
     (has c6 b6)
     (contains z2 c5)
     (contains z2 c6)
     (contains z2 b5)
     (contains z2 b6)
     (contains z2 j2)
     }
  )

(def world-test
  '#{
     (connects c1 j1)
     (connects c4 j1)
     (has c1 b1)
     (has c4 b4)
     (orientation c1 horizontal)
     (orientation c4 vertical)
     (contains z1 c1)
     (contains z1 c4)
     (contains z1 j1)
     (contains z1 b1)
     (contains z1 b4)

     (has c4 e1)
     (has c5 e1)
     (contains z1 e1)
     (contains z2 e1)

     (connects c5 j2)
     (connects c6 j2)
     (has c5 b5)
     (has c6 b6)
     (orientation c5 vertical)
     (orientation c6 horizontal)
     (contains z2 c5)
     (contains z2 c6)
     (contains z2 b5)
     (contains z2 b6)
     (contains z2 j2)
     }
  )

(def state-exchange
  '#{(manipulable box)
     (stores b4 box)
     (contains z1 box)

     (car robot-1)
     (in robot-1 c4)
     (contains z1 robot-1)
     (orientation robot-1 vertical)
     (holds robot-1 nothing)

     (car robot-2)
     (in robot-2 c5)
     (contains z2 robot-2)
     (orientation robot-2 vertical)
     (holds robot-2 nothing)
     }
  )

(def state-junc
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (in robot-1 c4)
     (contains z1 robot-1)
     (holds robot-1 nothing)
     (orientation robot-1 vertical)
     }
  )

(def state-all
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (orientation robot-1 horizontal)
     (in robot-1 c1)
     (contains z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (orientation robot-2 vertical)
     (in robot-2 c5)
     (contains z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def state-test
  '#{(manipulable box)
     (stores b1 box)
     (contains z1 box)

     (car robot-1)
     (orientation robot-1 horizontal)
     (in robot-1 c1)
     (contains z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (orientation robot-2 vertical)
     (in robot-2 c5)
     (contains z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def ops
  '{collect-stock {:pre ( (car ?agent)
                          (manipulable ?obj)
                          (near ?agent ?bay)
                          (stores ?bay ?obj)
                          (holds ?agent nothing))
                   :add ( (holds ?agent ?obj))
                   :del ( (stores ?bay ?obj)
                          (holds ?agent nothing))
                   :txt (?agent collects ?obj from ?bay)
                   :cmd [collect-stock ?obj]
                   }
    deposit-stock {:pre ( (near ?agent ?bay)
                          (holds ?agent ?obj)
                          )
                   :add ( (holds ?agent nothing)
                          (stores ?bay ?obj))
                   :del ((holds ?agent ?obj)
                          (stores ?bay ?nothing))
                   :txt (?agent deposits ?obj at ?bay)
                   :cmd [deposit-stock ?obj]
                   }
    move-to-bay {:pre ( (car ?agent)
                        (in ?agent ?corridor)
                        (has ?corridor ?bay)
                        (contains ?zone ?agent)
                        (contains ?zone ?bay)
                        )
                 :add ((near ?agent ?bay))
                 :del ()
                 :txt (move ?agent to B- ?bay in C- ?corridor )
                 :cmd [B-move ?agent to ?bay]
                 }
    move-to-junction {:pre ( (car ?agent)
                             (in ?agent ?corridor)
                             (connects ?corridor ?junction)
                             (contains ?zone ?agent)
                             (contains ?zone ?junction)
                             (near ?agent ?bay)
                             )
                      :add ((at ?agent ?junction))
                      :del ((in ?agent ?corridor)
                             (near ?agent ?bay))
                      :txt (move ?agent from C- ?corridor to J- ?junction ... ?bay)
                      :cmd [J-move ?agent to ?junction]
                      }
    move-to-corridor {:pre ( (car ?agent)
                             (at ?agent ?junction)
                             (orientation ?agent ?orientation)
                             (orientation ?corridor ?orientation)
                             (connects ?corridor ?junction)
                             (contains ?zone ?agent)
                             (contains ?zone ?corridor)
                             )
                      :add ((in ?agent ?corridor))
                      :del ((at ?agent ?junction)
                             (near ?agent ?bay))
                      :txt (move ?agent from J- ?junction to C- ?corridor)
                      :cmd [C-move ?agent to ?corridor]
                      }
    rotate-car {:pre ( (at ?agent ?junction)
                       (holds ?agent ?item)
                       (connects ?corridor ?junction)
                       (orientation ?corridor ?orientation1)
                       (orientation ?agent ?orientation2)
                       )
                :add ((orientation ?agent ?orientation1))
                :del ((orientation ?agent ?orientation2))
                :txt (rotate ?agent from ?orientation2 to ?orientation1)
                :cmd [rotate ?agent]
                }
    })