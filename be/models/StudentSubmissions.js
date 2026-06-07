const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const StudentSubmissions = sequelize.define(
  "student_submissions",
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
        model: "quizzes",
        key: "id",
      },
    },
    student_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users",
        key: "id",
      },
    },
    essay_answer: {
      type: DataTypes.TEXT,
      allowNull: true, // Nullable jika tugas berupa upload file/PG
    },
    file_url: {
      type: DataTypes.STRING(255),
      allowNull: true, // Nullable jika tugas berupa jawaban teks langsung
    },
    score: {
      type: DataTypes.INTEGER,
      allowNull: true, // Nullable sebelum dinilai oleh guru/sistem
    },
    teacher_comment: {
      type: DataTypes.TEXT,
      allowNull: true,
    },
    status: {
      type: DataTypes.ENUM('submitted', 'graded'),
      defaultValue: 'submitted',
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
    tableName: "student_submissions",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete untuk fitur unsubmit tugas
    underscored: true,
  }
);

module.exports = StudentSubmissions;