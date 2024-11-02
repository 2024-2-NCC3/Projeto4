const express = require("express");
const bodyParser = require("body-parser");
const sqlite3 = require("sqlite3").verbose();
const cors = require("cors"); // Adicionado

const app = express();
const port = process.env.PORT || 3000;

app.use(cors()); // Ativar CORS para todas as rotas
app.use(express.json());
// Configurações do Express
app.use(bodyParser.json());
app.use(bodyParser.urlencoded({ extended: true }));

// Banco de Dados
const caminhoBanco = "bancodb.db";
const banco = new sqlite3.Database(caminhoBanco);

banco.run(`CREATE TABLE IF NOT EXISTS users (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username  TEXT  NOT NULL,
    email     TEXT  NOT NULL,
    password  TEXT  NOT NULL
)`);

// Rotas
app.get("/", (req, res) => {
  res.send("Passei por aqui!");
});

app.get("/teste", (req, res) => {
  res.send("Passei por aqui 2!");
});

app.post("/criarUsuario", (req, res) => {
  // console.log(req.body); // verificação dos dados
  let username = req.body.username;
  let email = req.body.email;
  let password = req.body.password;

  banco.run(
    `INSERT INTO users (username, email, password) VALUES (?, ?, ?)`,
    [username, email, password],
    function (err) {
      if (err) {
        res.status(500).send(err.message);
      } else {
        res.json({ message: "Usuário criado com sucesso" });
      }
    }
  );
});

app.get("/tudo", (req, res) => {
  banco.all(`SELECT * FROM users`, [], (err, rows) => {
    if (err) {
      res.send(err.message);
    } else {
      res.json(rows);
    }
  });
});

app.listen(port, () => {
  console.log(`Servidor rodando na porta ${port}`);
});
