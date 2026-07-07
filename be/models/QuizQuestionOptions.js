const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const QuizQuestionOptions = sequelize.define(
  "quiz_question_options",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    quiz_question_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "quiz_questions", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    option_letter: {
      type: DataTypes.ENUM("A", "B", "C", "D"),
      allowNull: false,
    },
    option_text: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      field: "created_at",
    },
    updated_at: {
      type: DataTypes.DATE,
      field: "updated_at",
    },
    deleted_at: {
      type: DataTypes.DATE,
      field: "deleted_at",
      allowNull: true,
    },
  },
  {
    tableName: "quiz_question_options",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete berdasarkan deleted_at
    underscored: true,
  }
);

module.exports = QuizQuestionOptions;