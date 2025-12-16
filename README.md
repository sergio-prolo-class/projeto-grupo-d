# Decodificador de Resistores

Projeto desenvolvido em Java para decodificar resistores a partir das cores de suas faixas, conforme o padrão eletrônico.

O programa suporta resistores de:
- 4 faixas
- 5 faixas
- 6 faixas (com coeficiente de temperatura)

Em caso de entrada inválida, o programa exibe a mensagem de erro e encerra a execução imediatamente.

---

## Estrutura do projeto

```
PROJETO-GRUPO-D/
├── src/
│   ├── calculo/
│   │   └── CalculoResistencia.java
│   ├── formatacao/
│   │   └── FormatadorResistencia.java
│   └── Projeto/
│       └── DecodificadorResistor.java
├── testes/
│   └── teste_erro.txt
├── .gitignore
├── gabarito.txt
├── README.md
├── resistores.txt
```

---

## Compilação

A partir da raiz do projeto, execute:

```bash
javac src/calculo/*.java src/formatacao/*.java src/Projeto/*.java
```

---

## Execução

### Execução com argumentos (um resistor)

```bash
java -cp src Projeto.DecodificadorResistor amarelo roxo vermelho ouro
```

---

### Execução em lote (entrada padrão)

Linux / macOS:

```bash
java -cp src Projeto.DecodificadorResistor < resistores.txt
```

Windows (PowerShell):

```powershell
Get-Content resistores.txt | java -cp src Projeto.DecodificadorResistor
```

---

### Redirecionamento da saída

Linux / macOS:

```bash
java -cp src Projeto.DecodificadorResistor < resistores.txt > saidas/saida.txt
```

Windows (PowerShell):

```powershell
Get-Content resistores.txt | java -cp src Projeto.DecodificadorResistor > saidas/saida.txt
```

---

## Testes

Arquivo de teste para validação de erro:

```bash
java -cp src Projeto.DecodificadorResistor < testes/teste_erro.txt
```

O programa deve encerrar a execução imediatamente ao encontrar a primeira entrada inválida.

---

## Observações

- Arquivos compilados (.class) não fazem parte do repositório.
- A pasta `saidas/` contém apenas arquivos gerados em tempo de execução.
- A organização separa código-fonte, testes e saídas.