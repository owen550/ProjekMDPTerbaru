const { sequelize } = require("../config/sequelize");
const { DataTypes } = require("sequelize");

const CourseEnrollments = sequelize.define(
  "course_enrollments",
  {
    id: {
      type: DataTypes.INTEGER,
      autoIncrement: true,
      primaryKey: true,
    },
    student_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "users", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    course_id: {
      type: DataTypes.INTEGER,
      allowNull: false,
      references: {
        model: "courses", // Merujuk ke nama tabel asli di database
        key: "id",
      },
    },
    is_bookmarked: {
      type: DataTypes.BOOLEAN,
      defaultValue: false,
      allowNull: false,
    },
    status: {
      type: DataTypes.ENUM('active', 'completed'),
      defaultValue: 'active',
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
    tableName: "course_enrollments",
    timestamps: true,
    paranoid: true, // Mengaktifkan soft delete jika siswa unenroll kelas
    underscored: true,
  }
);

module.exports = CourseEnrollments;