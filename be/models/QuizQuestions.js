const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const QuizQuestions = sequelize.define(
  "quiz_questions",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    quiz_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "quizzes", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    question_text: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    correct_answer: {
      type: DataTypes.TEXT,
      allowNull: true, // Nullable untuk tipe soal essay
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
    tableName: "quiz_questions",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete untuk butir soal kuis
    underscored: true,
  }
);

module.exports = QuizQuestions;