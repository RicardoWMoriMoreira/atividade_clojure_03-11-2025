(ns test-simple
  (:require [clojure.test :refer [deftest is run-tests testing]]))

(deftest simple-test
  (testing "Basic arithmetic"
    (is (= 4 (+ 2 2)))
    (is (= 6 (* 2 3)))))

(run-tests)
