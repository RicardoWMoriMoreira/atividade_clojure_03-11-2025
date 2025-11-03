(ns integrador.core)

(defn exibir-menu []
  (println "\n=== MENU PRINCIPAL ===")
  (println "1 - Cadastrar Alunos")
  (println "2 - Relatório de Notas")
  (println "3 - Estatísticas Gerais")
  (println "4 - Buscar aluno pelo nome")
  (println "0 - Sair")
  (print "Escolha uma opção: ")
  (flush))

(defn ler-opcao []
  (try
    (Integer/parseInt (read-line))
    (catch Exception _ 0)))

(defn ler-nota []
  (try
    (Double/parseDouble (read-line))
    (catch Exception _ nil)))

(defn adicionar-status [aluno]
  (let [status (if (>= (:nota aluno) 7.0) "Aprovado" "Reprovado")]
    (assoc aluno :status status)))

(defn calcular-media [alunos]
  (if (empty? alunos)
    0.0
    (->> alunos
         (map :nota)
         (apply +)
         (#(/ % (count alunos))))))

(defn buscar-aluno [alunos]
  (if (empty? alunos)
    (println "\nNenhum aluno cadastrado!")
    (do
      (println "\n=== BUSCAR ALUNO ===")
      (print "Digite o nome do aluno: ")
      (flush)
      (let [nome-busca (.trim (read-line))
            aluno-encontrado (->> alunos
                                  (filter #(= (.toLowerCase (:nome %))
                                              (.toLowerCase nome-busca)))
                                  first)]
        (if aluno-encontrado
          (let [aluno-com-status (adicionar-status aluno-encontrado)]
            (println "\nAluno encontrado:")
            (println (format "Nome: %s" (:nome aluno-com-status)))
            (println (format "Nota: %.1f" (:nota aluno-com-status)))
            (println (format "Status: %s" (:status aluno-com-status))))
          (println (format "\nAluno '%s' não encontrado!" nome-busca)))))))

(defn estatisticas-gerais [alunos]
  (if (empty? alunos)
    (println "\nNenhum aluno cadastrado!")
    (let [alunos-com-status (->> alunos
                                 (map adicionar-status))
          total-alunos (count alunos)
          aprovados (->> alunos-com-status
                         (filter #(= (:status %) "Aprovado"))
                         count)
          reprovados (->> alunos-com-status
                          (filter #(= (:status %) "Reprovado"))
                          count)
          notas (->> alunos (map :nota))
          maior-nota (apply max notas)
          menor-nota (apply min notas)
          media-geral (calcular-media alunos)]

      (println "\n=== ESTATÍSTICAS GERAIS ===")
      (println (format "Total de alunos cadastrados: %d" total-alunos))
      (println (format "Número de aprovados: %d" aprovados))
      (println (format "Número de reprovados: %d" reprovados))
      (println (format "Maior nota: %.1f" maior-nota))
      (println (format "Menor nota: %.1f" menor-nota))
      (println (format "Média geral da turma: %.2f" media-geral)))))

(defn relatorio-notas [alunos]
  (if (empty? alunos)
    (println "\nNenhum aluno cadastrado!")
    (let [alunos-com-status (->> alunos
                                 (map adicionar-status))
          aprovados (->> alunos-com-status
                         (filter #(= (:status %) "Aprovado")))
          media-geral (calcular-media alunos)]

      (println "\n=== RELATÓRIO DE NOTAS ===")
      (println "\nTodos os alunos:")
      (->> alunos-com-status
           (map #(format "Nome: %s | Nota: %.1f | Status: %s"
                         (:nome %) (:nota %) (:status %)))
           (map println)
           doall)

      (println "\nAlunos aprovados:")
      (if (empty? aprovados)
        (println "Nenhum aluno aprovado.")
        (->> aprovados
             (map #(format "Nome: %s | Nota: %.1f" (:nome %) (:nota %)))
             (map println)
             doall))

      (println (format "\nMédia geral da turma: %.2f" media-geral)))))

(defn cadastrar-alunos [alunos-existentes]
  (println "\n=== CADASTRO DE ALUNOS ===")
  (println "Digite o nome do aluno (ou deixe em branco para finalizar):")
  (println "Para sair a qualquer momento, digite 'sair' no campo nome.")

  (loop [alunos alunos-existentes]
    (print "Nome: ")
    (flush)
    (let [nome (read-line)]
      (cond
        (or (nil? nome) (empty? (.trim nome)))
        (do
          (println "Cadastro finalizado!")
          alunos)

        (= (.toLowerCase (.trim nome)) "sair")
        (do
          (println "Cadastro cancelado!")
          alunos)

        :else
        (let [nota (loop []
                     (print "Nota: ")
                     (flush)
                     (let [nota-input (ler-nota)]
                       (if (and nota-input (>= nota-input 0) (<= nota-input 10))
                         nota-input
                         (do
                           (println "Nota inválida! Digite um valor entre 0 e 10 (use ponto, não vírgula).")
                           (recur)))))
              novo-aluno {:nome (.trim nome) :nota nota}
              novos-alunos (conj alunos novo-aluno)]
          (println (str "Aluno " (.trim nome) " cadastrado com sucesso!"))
          (recur novos-alunos))))))

(defn -main []
  (loop [alunos []]
    (exibir-menu)
    (let [opcao (ler-opcao)]
      (cond
        (= opcao 0) (println "Saindo...")
        (= opcao 1) (recur (cadastrar-alunos alunos))
        (= opcao 2) (do (relatorio-notas alunos) (recur alunos))
        (= opcao 3) (do (estatisticas-gerais alunos) (recur alunos))
        (= opcao 4) (do (buscar-aluno alunos) (recur alunos))
        :else (do
                (println "Opção inválida!")
                (recur alunos))))))