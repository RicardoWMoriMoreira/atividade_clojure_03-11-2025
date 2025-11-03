(ns executar-testes
  (:require [clojure.test :as t]))

(defn -main []
  (println "=== EXECUTANDO TESTES TDD ===")
  (println "Tentando carregar testes do integrador...")

  (try
    (require 'integrador.core-test)
    (println "✅ Testes carregados com sucesso!")
    (println)

    (let [results (t/run-tests 'integrador.core-test)]
      (println)
      (println "=== RESULTADO DOS TESTES ===")
      (println (str "Testes executados: " (:test results)))
      (println (str "Testes que passaram: " (:pass results)))
      (println (str "Testes que falharam: " (:fail results)))
      (println (str "Testes com erro: " (:error results)))

      (if (and (= 0 (:fail results)) (= 0 (:error results)))
        (println "✅ Todos os testes passaram!")
        (println "❌ Alguns testes falharam ou apresentaram erros.")))

    (catch Exception e
      (println "❌ Erro ao executar testes:")
      (println (.getMessage e))
      (println)
      (println "Verifique se:")
      (println "1. Você está no diretório correto do projeto")
      (println "2. Os arquivos de teste existem")
      (println "3. O classpath está configurado corretamente"))))
