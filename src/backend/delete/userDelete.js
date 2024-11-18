const sqlite3 = require("sqlite3").verbose();
const banco = new sqlite3.Database("bancoo.db");

function deletarUser(username, email, res) {
  // Verifica se os dados necessários foram fornecidos
  if (!username || !email) {
    return res
      .status(400)
      .json({ message: "O username e email são obrigatórios" });
  }

  banco.get(
    `SELECT * FROM users WHERE username = ? AND email = ?`,
    [username, email],
    function (err, row) {
      if (err) {
        return res.status(500).json({ message: err.message });
      }

      if (row) {
        // Se o usuário existe, realiza a deleção
        banco.run(
          `DELETE FROM users WHERE username = ? AND email = ?`,
          [username, email],
          function (err) {
            if (err) {
              return res.status(500).json({ message: err.message });
            }
            console.log("Usuário deletado com sucesso");
            return res.json({ message: "Usuário deletado com sucesso" });
          }
        );
      } else {
        // Caso o usuário não exista
        return res.status(404).json({ message: "Usuário não encontrado" });
      }
    }
  );
}

module.exports = { deletarUser };
