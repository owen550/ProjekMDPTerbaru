const { Sequelize } = require("sequelize");
require("dotenv").config();

function getMissingDbEnvVars() {
  return ["DB_NAME", "DB_USER", "DB_HOST"].filter((key) => !process.env[key]);
}

// setup sequelize
const sequelize = new Sequelize(
  process.env.DB_NAME,
  process.env.DB_USER,
  process.env.DB_PASSWORD,
  {
    host: process.env.DB_HOST,
    port: process.env.DB_PORT ? Number(process.env.DB_PORT) : 3306,
    dialect: process.env.DB_DIALECT || "mysql",
    timezone: "+07:00",
  }
);

async function testDB() {
  try {
    const missingDbEnvVars = getMissingDbEnvVars();

    if (missingDbEnvVars.length > 0) {
      console.warn(
        `Skipping database connection check. Missing environment variables: ${missingDbEnvVars.join(", ")}`
      );
      return;
    }

    await sequelize.authenticate();
    console.log("Database connected!");
  } catch (error) {
    console.error("Connection error:", error);
  }
}

testDB();

module.exports = { sequelize };
