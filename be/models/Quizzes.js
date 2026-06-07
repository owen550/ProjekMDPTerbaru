const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const Quizzes = sequelize.define(
  "quizzes",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    topic_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "course_topics", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    quiz_category: {
      type: DataTypes.ENUM('tugas', 'proyek', 'latihan', 'ujian'),
      allowNull: false,
    },
    question_type: {
      type: DataTypes.ENUM('multiple_choice', 'essay'),
      allowNull: false,
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
    tableName: "quizzes",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete untuk wadah kuis
    underscored: true,
  }
);

module.exports = Quizzes;