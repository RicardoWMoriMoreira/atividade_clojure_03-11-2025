# Sistema de Gerenciamento de Alunos - Testes TDD

Este projeto implementa um sistema simples de gerenciamento de alunos em Clojure, com uma suíte completa de testes TDD (Test-Driven Development).

## Estrutura do Projeto

```
atividade_03-11-2025/
├── src/integrador/core.clj      # Código principal do sistema
├── test/integrador/core_test.clj # Testes TDD
├── project.clj                  # Configuração do Leiningen
├── run_tests.clj               # Script para executar testes
└── README.md                   # Este arquivo
```

## Funcionalidades do Sistema

O sistema possui as seguintes funções principais:

1. **exibir-menu** - Exibe o menu principal
2. **ler-opcao** - Lê opção do usuário
3. **ler-nota** - Lê nota do usuário com validação
4. **adicionar-status** - Adiciona status (Aprovado/Reprovado) baseado na nota
5. **calcular-media** - Calcula a média das notas dos alunos
6. **buscar-aluno** - Busca aluno por nome (case insensitive)
7. **estatisticas-gerais** - Mostra estatísticas gerais da turma
8. **relatorio-notas** - Gera relatório completo de notas
9. **cadastrar-alunos** - Permite cadastrar novos alunos
10. **-main** - Função principal que controla o fluxo do programa

## Testes TDD Implementados

### Cobertura de Testes

✅ **Funções Puras** (sem efeitos colaterais):
- `adicionar-status` - Testa aprovação/reprovação baseada em nota
- `calcular-media` - Testa cálculo de médias com diferentes cenários

✅ **Funções com I/O**:
- `ler-opcao` - Testa leitura de opções válidas/inválidas
- `ler-nota` - Testa leitura de notas válidas/inválidas
- `exibir-menu` - Testa que não lança exceções

✅ **Funções de Relatório**:
- `estatisticas-gerais` - Testa estatísticas com diferentes cenários
- `relatorio-notas` - Testa relatórios de alunos aprovados/reprovados

✅ **Funções de Busca e Cadastro**:
- `buscar-aluno` - Testa busca por nome (case insensitive)
- `cadastrar-alunos` - Testa cadastro válido/inválido de alunos

### Estratégias de Teste

- **Funções Puras**: Testes diretos de entrada/saída
- **Funções com I/O**: Uso de `with-in-str` e `with-out-str` para simular entrada/saída
- **Cenários de Edge Cases**: Listas vazias, valores limite, entradas inválidas
- **Validação de Regras de Negócio**: Aprovação >= 7.0, notas entre 0-10

## Como Executar os Testes

### Opção 1: Leiningen (Recomendado)

```bash
lein test
```

### Opção 2: Clojure CLI

```bash
clojure -Spath "src:test" -M -e "(require 'integrador.core-test) (clojure.test/run-tests 'integrador.core-test)"
```

### Opção 3: Script Personalizado

```bash
clojure -M run_tests.clj
```

## Exemplos de Testes

### Teste de Função Pura
```clojure
(deftest test-adicionar-status
  (testing "Nota >= 7.0 deve ser aprovado"
    (let [aluno {:nome "João" :nota 8.5}
          resultado (adicionar-status aluno)]
      (is (= "Aprovado" (:status resultado))))))
```

### Teste com Simulação de I/O
```clojure
(deftest test-ler-nota
  (testing "Ler nota válida"
    (is (= 8.5 (with-in-str "8.5" (ler-nota))))))
```

### Teste de Relatório
```clojure
(deftest test-estatisticas-gerais
  (testing "Estatísticas com alunos"
    (let [output (with-out-str (estatisticas-gerais alunos))]
      (is (.contains output "Total de alunos cadastrados: 3")))))
```

## Benefícios do TDD Implementado

1. **Cobertura Completa**: Todos os caminhos de código testados
2. **Isolamento**: Testes independentes não afetam uns aos outros
3. **Manutenibilidade**: Código testado é mais fácil de refatorar
4. **Documentação Viva**: Testes servem como exemplos de uso
5. **Detecção de Regressões**: Quebra automática se algo parar de funcionar

## Como Usar o Sistema

Execute o programa principal:

```bash
clojure -M -m integrador.core
```

Ou com Leiningen:
```bash
lein run
```

O sistema apresentará um menu interativo para:
- Cadastrar alunos
- Buscar alunos por nome
- Visualizar relatórios de notas
- Ver estatísticas gerais
