; Copyright (c) 2012 Pascal Chatterjee

; Permission is hereby granted, free of charge, to any person obtaining
; a copy of this software and associated documentation files (the
; "Software"), to deal in the Software without restriction, including
; without limitation the rights to use, copy, modify, merge, publish,
; distribute, sublicense, and/or sell copies of the Software, and to
; permit persons to whom the Software is furnished to do so, subject to
; the following conditions:

; The above copyright notice and this permission notice shall be
; included in all copies or substantial portions of the Software.

; THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
; EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF
; MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
; NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
; LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
; OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION
; WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.

(ns ttmachines.client.animate
  (:use [ttmachines.client.util :only [after]])
  (:require [one.dispatch :as dispatch]
            [domina.domina :as dom]
            [domina.domina.css :as css]
            [ttmachines.client.layout :as layout]
            [ttmachines.client.animate.effects :as effects]))

;; We want to animate all our layout targets
(doseq [t (vals layout/targets)]
  (let [element (css/sel t)]
    (dom/add-class! element "animated")))

(defn animate [effect & selectors]
  (doseq [selector selectors]
    (let [element (css/sel selector)]
      (doseq [c (dom/classes element)]
        (when (contains? effects/effects c)
          (dom/remove-class! element c)))
      (dom/add-class! element effect))))

;; Convenience functions for popular effects

(def fade-in-left (partial animate "fadeInLeft"))
(def fade-out-left (partial animate "fadeOutLeft"))
(def fade-in-right (partial animate "fadeInRight"))
(def fade-out-right (partial animate "fadeOutRight"))

;; Triggers

(dispatch/react-to #{:history-state-change} {:priority 0}
  (fn [_ _]
    (.log js/console "Fading out")
    (fade-out-left  "#text")
    (fade-out-left  "#main")
    (fade-out-left  "#below-main")
    (fade-out-right "#sidebar")))

(dispatch/react-to #{:switch-page} {:priority 2}
  (fn [_ _]
    (.log js/console "Fading in")
    (fade-in-left  "#text")
    (fade-in-left  "#main")
    (fade-in-left  "#below-main")
    (fade-in-right "#sidebar")))