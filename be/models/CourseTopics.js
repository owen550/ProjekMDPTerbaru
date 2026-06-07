const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const CourseTopics = sequelize.define(
  "course_topics",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    course_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "courses", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    topic_number: {
      type: DataTypes.INTEGER,
      allowNull: false,
    },
    title: {
      type: DataTypes.STRING(255),
      allowNull: false,
    },
    description: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    content_type: {
      type: DataTypes.ENUM('material', 'quiz'),
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
    tableName: "course_topics",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete jika topik pertemuan dihapus guru
    underscored: true,
  }
);

module.exports = CourseTopics;