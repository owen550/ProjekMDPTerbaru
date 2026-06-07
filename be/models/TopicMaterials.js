const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const TopicMaterials = sequelize.define(
  "topic_materials",
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
        model: "course_topics",
        key: "id",
      },
    },
    video_url: {
      type: DataTypes.STRING(255),
      allowNull: true, // Nullable jika pertemuan tidak ada video penjelasnya
    },
    attachment_file: {
      type: DataTypes.STRING(255),
      allowNull: true, // Nullable jika tidak ada file PDF/ZIP tambahan
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
    tableName: "topic_materials",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete untuk file materi
    underscored: true,
  }
);

module.exports = TopicMaterials;