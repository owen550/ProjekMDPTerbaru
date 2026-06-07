const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const AdminMessages = sequelize.define(
  "admin_messages",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    admin_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users",
        key: "id",
      },
    },
    receiver_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users",
        key: "id",
      },
    },
    message_title: {
      type: DataTypes.STRING(150),
      allowNull: false,
    },
    message_body: {
      type: DataTypes.TEXT,
      allowNull: false,
    },
    is_read: {
      type: DataTypes.BOOLEAN,
      defaultValue: false,
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      field: 'created_at',
    },
    deleted_at: {
      type: DataTypes.DATE,
      field: 'deleted_at',
      allowNull: true,
    },
  },
  {
    tableName: "admin_messages",
    timestamps: true,
    createdAt: "created_at",
    updatedAt: false, // Pesan yang sudah dikirim umumnya tidak di-edit untuk menjaga validitas informasi
    paranoid: true,   // Mengaktifkan soft delete jika salah satu pihak menghapus pesan dari inbox/outbox
    underscored: true,
  }
);

module.exports = AdminMessages;