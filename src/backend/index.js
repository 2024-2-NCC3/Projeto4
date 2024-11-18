const express = require("express");
const bodyParser = require("body-parser");
const sqlite3 = require("sqlite3").verbose();
const cors = require("cors");
const crypto = require("crypto");

const app = express();
const port = process.env.PORT || 3000;

app.use(cors()); // Ativa o CORS para todas as rotas
app.use(express.json());
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Banco de Dados
const caminhoBanco = "bancoo.db";
const banco = new sqlite3.Database(caminhoBanco);

banco.run(`CREATE TABLE IF NOT EXISTS users (
  id       INTEGER PRIMARY KEY AUTOINCREMENT,
  username TEXT    NOT NULL,
  email    TEXT    UNIQUE,
  password TEXT    NOT NULL,
  salt     TEXT    NOT NULL
)`);

// Função de Hashing com Salt
function hashedPassword(password, salt) {
  // Cria o hash usando o PBKDF2 com SHA-1
  const hash = crypto.pbkdf2Sync(password, salt, 65536, 24, "sha1");
  return hash.toString("base64"); // Retorna o hash em base64 para compatibilidade com o Android
}

// Rota para Criar Usuário
app.post("/criarUsuario", (req, res) => {
  const { username, email, password } = req.body;

  if (!password) {
    return res.status(400).json({ message: "Necessário a senha" });
  }

  // Gera o salt aleatório
  const salt = crypto.randomBytes(16).toString("base64");
  const passwordHashed = hashedPassword(password, salt);

  banco.run(
    `INSERT INTO users (username, email, password, salt) VALUES (?, ?, ?, ?)`,
    [username, email, passwordHashed, salt],
    function (err) {
      if (err) {
        return res.status(500).send(err.message);
      }
      res.json({ message: "Usuário criado com sucesso" });
    }
  );
});

// Rota para Login (Autenticação)
app.post("/login", (req, res) => {
  const { username, password } = req.body;
  console.log(req.body); // Verifique o que está sendo enviado

  if (!username || !password) {
    return res
      .status(400)
      .json({ message: "Nome de usuário e senha são obrigatórios" });
  }

  banco.get(
    `SELECT * FROM users WHERE username = ?`,
    [username],
    function (err, row) {
      if (err) {
        return res.status(500).send(err.message);
      }

      if (row) {
        // Gera o hash da senha usando o salt armazenado
        const passwordHashed = hashedPassword(password, row.salt);
        console.log("Password hash fornecido:", passwordHashed);
        console.log("Password hash fornecido:", passwordHashed);
        console.log("Password hash esperado:", row.password); // Para comparação direta

        // Compara o hash da senha fornecida com o hash armazenado
        if (passwordHashed === row.password) {
          console.log("Usuário encontrado e senha válida");
          return res.json({
            message: "Login bem-sucedido",
            salt: row.salt,
            hashedPassword: row.password,
          });
        } else {
          console.log("Senha incorreta");
          return res.status(401).json({ message: "Senha incorreta" });
        }
      } else {
        console.log("Usuário não encontrado");
        return res.status(401).json({ message: "Usuário não encontrado" });
      }
    }
  );
});

// Rota inicial
app.get("/", (req, res) => {
  res.send("Bem-vindo ao servidor!");
});

// Rota para testenp
app.get("/teste", (req, res) => {
  res.send("Passei por aqui!");
});

// Rota para ver todos os usuários
app.get("/tudo", (req, res) => {
  banco.all(`SELECT * FROM users`, [], (err, rows) => {
    if (err) {
      return res.status(500).send(err.message);
    }
    res.json(rows);
  });
});

// Inicializa o servidor
app.listen(port, () => {
  console.log(`Servidor rodando na porta ${port}`);
});
