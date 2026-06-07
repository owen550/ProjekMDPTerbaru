const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const Courses = sequelize.define(
  "courses",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    title: {
      type: DataTypes.STRING(150),
      allowNull: false,
    },
    category: {
      type: DataTypes.ENUM('mathematics', 'science', 'art', 'technology', 'social'),
      allowNull: false,
    },
    teacher_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users", // Menghubungkan ke tabel users
        key: "id",
      },
    },
    created_at: {
      type: DataTypes.DATE,
      field: 'created_at',
    },
    updated_at: {
      type: DataTypes.DATE,
      field: 'updated_at',
    },
    deleted_at: {
      type: DataTypes.DATE,
      field: 'deleted_at',
      allowNull: true,
    },
  },
  {
    tableName: "courses",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete jika kelas dihapus guru
    underscored: true,
  }
);

module.exports = Courses;