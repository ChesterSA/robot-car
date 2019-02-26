(ns matcher-starter.core
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
     (connects j1 c1)
     (connects j1 c4)
     (at b1 c1)
     (at b4 c4)
     (has c1 b1)
     (has c4 b4)
     (junction j1)
     (corridor c1)
     (corridor c4)
     (orientation c1 vertical)
     (orientation c4 vertical)
     (in z1 c1)
     (in z1 c4)
     (in z1 j1)
     (in z1 b1)
     (in z1 b4)}
  )

(def world-exchange
  '#{
     (at b4 c4)
     (has c4 b4)
     (corridor c4)
     (orientation c4 vertical)
     (in z1 c4)
     (in z1 b4)

     (has c4 e1)
     (has c5 e1)
     (at e1 c4)
     (at e1 c5)
     (in z1 e1)
     (in z2 e1)

     (at b5 c5)
     (has c5 b5)
     (corridor c5)
     (orientation c5 vertical)
     (in z2 c5)
     (in z2 b5)
     }
  )

(def world-all
  '#{
     (connects c1 j1)
     (connects c2 j1)
     (connects c3 j1)
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
     (junction j1)
     (corridor c1)
     (corridor c2)
     (corridor c3)
     (corridor c4)
     (orientation c1 horizontal)
     (orientation c2 vertical)
     (orientation c3 horizontal)
     (orientation c4 vertical)
     (in z1 c1)
     (in z1 c2)
     (in z1 c3)
     (in z1 c4)
     (in z1 j1)
     (in z1 b1)
     (in z1 b2)
     (in z1 b3)
     (in z1 b4)

     (has c4 e1)
     (has c5 e1)
     (at e1 c4)
     (at e1 c5)
     (in z1 e1)
     (in z2 e1)

     (connects c5 j2)
     (connects c6 j2)
     (at b5 c5)
     (at b6 c6)
     (has c5 b5)
     (has c6 b6)
     (junction j2)
     (corridor c5)
     (corridor c6)
     (in z2 c5)
     (in z2 c6)
     (in z2 b5)
     (in z2 b6)
     (in z2 j2)
     }
  )

(def world-test
  '#{
     (connects c1 j1)
     (connects c4 j1)
     (connects j1 c1)
     (connects j1 c4)
     (at b1 c1)
     (at b4 c4)
     (has c1 b1)
     (has c4 b4)
     (junction j1)
     (corridor c1)
     (corridor c4)
     (orientation c1 horizontal)
     (orientation c4 vertical)
     (in z1 c1)
     (in z1 c4)
     (in z1 j1)
     (in z1 b1)
     (in z1 b4)

     (has c4 e1)
     (has c5 e1)
     (at e1 c4)
     (at e1 c5)
     (in z1 e1)
     (in z2 e1)

     (connects c5 j2)
     (connects c6 j2)
     (at b5 c5)
     (at b6 c6)
     (has c5 b5)
     (has c6 b6)
     (junction j2)
     (corridor c5)
     (corridor c6)
     (orientation c5 vertical)
     (orientation c6 horizontal)
     (in z2 c5)
     (in z2 c6)
     (in z2 b5)
     (in z2 b6)
     (in z2 j2)
     }
  )

(def state-exchange
  '#{(manipulable box)
     (in box b4)
     (in z1 box)

     (car robot-1)
     (at robot-1 c4)
     (in z1 robot-1)
     (orientation robot-1 vertical)
     (holds robot-1 nothing)

     (car robot-2)
     (at robot-2 c5)
     (in z2 robot-2)
     (orientation robot-2 vertical)
     (holds robot-2 nothing)
     }
  )

(def state-junc
  '#{(manipulable box)
     (in box b1)
     (in z1 box)

     (car robot-1)
     (at robot-1 c4)
     (in z1 robot-1)
     (holds robot-1 nothing)
     (orientation robot-1 vertical)
     }
  )

(def state-all
  '#{(manipulable box)
     (in box b2)
     (in z1 box)

     (car robot-1)
     (at robot-1 c4)
     (in z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (at robot-2 c5)
     (in z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def state-test
  '#{(manipulable box)
     (in box b1)
     (in z1 box)

     (car robot-1)
     (orientation robot-1 horizontal)
     (at robot-1 c1)
     (in z1 robot-1)
     (holds robot-1 nothing)

     (car robot-2)
     (at robot-2 c5)
     (orientation robot-2 vertical)
     (in z2 robot-2)
     (holds robot-2 nothing)
     }
  )

(def ops
  '{collect-stock {:pre ( (car ?agent)
                   (manipulable ?obj)
                   (at ?agent ?bay)
                   (in ?obj ?bay)
                   (holds ?agent nothing))
            :add ( (holds ?agent ?obj))
            :del ( (at ?obj  ?bay)
                   (holds ?agent nothing))
            :txt (collect ?obj from ?bay)
            :cmd [collect-stock ?obj]
            }
    deposit-stock {:pre ( (at ?agent ?bay)
                    (holds ?agent ?obj)
                    )
             :add ( (holds ?agent nothing)
                    (in ?obj ?bay))
             :del ((holds ?agent ?obj))
             :txt (deposit ?obj at ?bay)
             :cmd [deposit-stock ?obj]
             }
    move-to-bay {:pre ( (car ?agent)
                        (at ?agent ?corridor)
                        (has ?corridor ?bay)
                        (in ?zone ?agent)
                        (in ?zone ?bay)
               )
          :add ((at ?agent ?bay))
          :del ((at ?agent ?corridor))
          :txt (move ?agent from C- ?corridor to B- ?bay)
          :cmd [B-move ?agent to ?bay]
          }
    move-to-junction {:pre ( (car ?agent)
                        (at ?agent ?corridor)
                        (corridor ?corridor)
                        (junction ?junction)
                        (connects ?corridor ?junction)
                        (in ?zone ?agent)
                        (in ?zone ?junction)
                        )
                 :add ((at ?agent ?junction))
                 :del ((at ?agent ?corridor))
                 :txt (move ?agent from C- ?corridor to J- ?junction)
                 :cmd [J-move ?agent to ?junction]
                 }
    move-to-corridor-from-bay {:pre ( (car ?agent)
                                      (at ?agent ?bay)
                                      (corridor ?corridor)
                                      (at ?bay ?corridor)
                                      (in ?zone ?agent)
                                      (in ?zone ?corridor)
                             )
                      :add ((at ?agent ?corridor))
                      :del ((at ?agent ?bay))
                      :txt (move ?agent from B- ?bay to C- ?corridor)
                      :cmd [C-move ?agent to ?corridor]
                      }
    move-to-corridor-from-junction {:pre ( (car ?agent)
                                           (at ?agent ?junction)
                                           (orientation ?agent ?orientation)
                                           (orientation ?corridor ?orientation)
                                           (corridor ?corridor)
                                           (junction ?junction)
                                           (connects ?junction ?corridor)
                                           (in ?zone ?agent)
                                           (in ?zone ?corridor)
                                      )
                               :add ((at ?agent ?corridor))
                               :del ((at ?agent ?junction))
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
