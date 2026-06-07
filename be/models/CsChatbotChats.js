const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const CsChatbotChats = sequelize.define(
  "cs_chatbot_chats",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    user_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users",
        key: "id",
      },
    },
    sender: {
      type: DataTypes.ENUM('user', 'bot'),
      allowNull: false,
    },
    message: {
      type: DataTypes.TEXT,
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
    tableName: "cs_chatbot_chats",
    timestamps: true,
    createdAt: "created_at",
    updatedAt: false, // Kita matikan updatedAt karena log chat tidak memerlukan fitur edit pesan
    paranoid: true,   // Mengaktifkan soft delete jika user membersihkan history chat
    underscored: true,
  }
);

module.exports = CsChatbotChats;