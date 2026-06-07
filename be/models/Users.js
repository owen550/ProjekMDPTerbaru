const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const Users = sequelize.define(
  "users",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    name: {
      type: DataTypes.STRING,
      allowNull: false,
    },
    username: {
      type: DataTypes.STRING,
      allowNull: true,
      unique: true
    },
    password: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    email: {
      type: DataTypes.STRING,
      allowNull: false,
      unique: true
    },
    google_id: {
      type: DataTypes.STRING,
      allowNull: true,
    },
    birthday_date: {
      type: DataTypes.DATEONLY, // DATEONLY : cuma tanggal, DATE : lengkap tanggal dan waktu
      allowNull: true, // jaga jaga kalau login with google
    },
    role: {
      type: DataTypes.ENUM('student','teacher','admin'),
      allowNull: false,
    },
    tier: {
      type: DataTypes.ENUM('free','premium','none'),
      defaultValue: 'free',
      allowNull: true,
    },
    points: {
      type: DataTypes.INTEGER,
      defaultValue: 0,
      allowNull: true,
    },
    created_at: {
      type: DataTypes.DATE,
      defaultValue: DataTypes.NOW,
    },
    updated_at: {
      type: DataTypes.DATE,
      defaultValue: DataTypes.NOW,
    },
    deleted_at: {
      type: DataTypes.DATE,
      allowNull: true,
    },
  },
  {
    tableName: "users",
    timestamps: true,
    paranoid: true,
    underscored: true // Memastikan Sequelize mencari created_at alih-alih createdAt
  }
);

module.exports = Users;
