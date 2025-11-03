(ns run-tests
  (:require [clojure.test :as test]))

;; Carregar os testes
(try
  (require 'integrador.core-test)
  (catch Exception e
    (println "Erro ao carregar testes:" (.getMessage e))))

(defn run-all-tests []
  (println "=== EXECUTANDO TESTES TDD ===")
  (println)

  ;; Executar todos os testes
  (let [results (test/run-tests 'integrador.core-test)]
    (println)
    (println "=== RESULTADO DOS TESTES ===")
    (println (format "Testes executados: %d" (:test results)))
    (println (format "Testes que passaram: %d" (:pass results)))
    (println (format "Testes que falharam: %d" (:fail results)))
    (println (format "Testes com erro: %d" (:error results)))

    (if (and (= 0 (:fail results)) (= 0 (:error results)))
      (println "Todos os testes passaram!")
      (println "Alguns testes falharam ou apresentaram erros."))))

;; Executar os testes
(run-all-tests)