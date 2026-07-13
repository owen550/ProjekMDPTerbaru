const app = require("./app");
const { sequelize } = require("./config/sequelize");

const PORT = process.env.PORT || 3000;

async function startServer() {
  try {
    await sequelize.authenticate();

    console.log("Database connected successfully.");

    app.listen(PORT, () => {
      console.log(`Server berjalan di http://localhost:${PORT}`);
    });
    app.get("/", async (req, res) => {
      res.status(200).send("Pesan Terkirim");
    });
  } catch (err) {
    console.error("Database connection failed.");

    console.error(err);
  }
}

startServer();
