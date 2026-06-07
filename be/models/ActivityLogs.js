const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const ActivityLogs = sequelize.define(
  "activity_logs",
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
    activity: {
      type: DataTypes.STRING(255),
      allowNull: false,
    },
    ip_address: {
      type: DataTypes.STRING(45),
      allowNull: false,
    },
    created_at: {
      type: DataTypes.DATE,
      field: 'created_at',
    },
  },
  {
    tableName: "activity_logs",
    timestamps: true,
    createdAt: "created_at",
    updatedAt: false, // Dimatikan: Log audit tidak boleh di-update
    paranoid: false,  // Dimatikan: Log audit tidak boleh di-delete
    underscored: true,
  }
);

module.exports = ActivityLogs;