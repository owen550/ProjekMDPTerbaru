const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const Payments = sequelize.define(
  "payments",
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
        model: "users", // Menghubungkan ke tabel users
        key: "id",
      },
    },
    order_id: {
      type: DataTypes.STRING(100),
      allowNull: false,
      unique: true, // ID transaksi dari sistem ChronosEdu harus unik
    },
    payment_token: {
      type: DataTypes.STRING(255),
      allowNull: false, // Token transaksi wajib disimpan untuk verifikasi Axios/Snap
    },
    amount: {
      type: DataTypes.DECIMAL(10, 2),
      allowNull: false,
    },
    payment_method: {
      type: DataTypes.ENUM('qris', 'va_bca', 'va_mandiri', 'va_bni', 'va_bri'),
      allowNull: false,
    },
    va_number: {
      type: DataTypes.STRING(50),
      allowNull: true, // Nullable jika user memilih metode QRIS
    },
    qr_url: {
      type: DataTypes.TEXT,
      allowNull: true, // Nullable jika user memilih metode Virtual Account
    },
    status: {
      type: DataTypes.ENUM('pending', 'success', 'failed', 'expired'),
      defaultValue: 'pending',
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
    tableName: "payments",
    timestamps: true,
    paranoid: true, // Mengaktifkan fitur soft delete (deleted_at)
    underscored: true, // Memastikan sinkronisasi snake_case di database
  }
);

module.exports = Payments;