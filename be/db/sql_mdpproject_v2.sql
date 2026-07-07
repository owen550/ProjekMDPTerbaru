/*
SQLyog Community v13.2.1 (64 bit)
MySQL - 10.4.32-MariaDB : Database - mdpproject
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`mdpproject` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */;

USE `mdpproject`;

/*Table structure for table `activity_logs` */

DROP TABLE IF EXISTS `activity_logs`;

CREATE TABLE `activity_logs` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `activity` varchar(255) NOT NULL,
  `ip_address` varchar(45) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `activity_logs_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `activity_logs` */

insert  into `activity_logs`(`id`,`user_id`,`activity`,`ip_address`,`created_at`) values 
(1,2,'Get All User',' - ','2026-07-01 16:14:15'),
(3,2,'Get All User','::1','2026-07-01 16:17:55'),
(4,9,'Get All User','::1','2026-07-01 16:22:18'),
(5,9,'Get Seorang User','::1','2026-07-01 16:29:16'),
(6,6,'User Melakukan Update Data Diri','::1','2026-07-01 16:31:42'),
(7,6,'User Melakukan Update Data Diri','::1','2026-07-01 16:31:55'),
(8,8,'User Didelete','::1','2026-07-01 16:32:57'),
(9,9,'Get All User','::1','2026-07-01 17:41:05'),
(10,9,'Get All User','::1','2026-07-01 17:42:12'),
(11,9,'Get All User','::1','2026-07-01 17:42:14'),
(12,9,'Get All User','::1','2026-07-01 17:42:16'),
(13,9,'Get All User','::1','2026-07-01 17:43:00'),
(14,9,'Get All User','::1','2026-07-01 17:43:02'),
(15,4,'Get All User','::1','2026-07-01 17:43:27'),
(16,4,'Get All User','::1','2026-07-01 18:22:08'),
(17,4,'Get All User','::1','2026-07-01 18:23:22'),
(18,4,'Get All User','::1','2026-07-01 18:24:04'),
(19,4,'Get All User','::1','2026-07-01 20:49:56'),
(20,10,'Mendaftar Sebagai User Baru','::1','2026-07-01 20:53:05'),
(21,10,'Get All User','::1','2026-07-01 21:07:04'),
(22,10,'Membuat Course Baru Berjudul Memahami Pola Pikir Neonazi yang Radikal','::1','2026-07-01 21:09:09'),
(23,10,'Membuat Course Baru Berjudul Penerapan Proporsi Seni Untuk Estetika','::1','2026-07-01 21:29:10'),
(24,9,'User Didelete','::1','2026-07-01 21:39:20'),
(25,10,'Get All User','::1','2026-07-01 21:41:08'),
(26,10,'Merubah Judul Dari Penerapan Proporsi Seni Untuk Estetika menjadi Penerapan Matematika Dalam Seni ','::1','2026-07-01 21:45:11'),
(27,10,'Merubah Judul Dari Penerapan Matematika Dalam Seni  menjadi Penerapan Matematika Dalam Seni ','::1','2026-07-01 21:45:13'),
(28,10,'Merubah Judul Dari Penerapan Matematika Dalam Seni  menjadi Color Of Theory','::1','2026-07-01 21:45:40'),
(29,10,'Course Penerapan Matematika Dalam Seni  telah didelete','::1','2026-07-01 21:50:00'),
(30,4,'Get All Course Topic','::1','2026-07-01 23:17:06'),
(31,4,'Get All User','::1','2026-07-01 23:21:39'),
(32,3,'Get All User','::1','2026-07-01 23:21:52'),
(33,4,'Get All Course Topic','::1','2026-07-01 23:22:03'),
(34,4,'Get All Course Topic','::1','2026-07-01 23:36:43'),
(35,4,'Get All Course Topic','::1','2026-07-01 23:40:22'),
(36,4,'Get All Course Topic','::1','2026-07-01 23:40:31'),
(37,4,'Get All Course Topic','::1','2026-07-01 23:40:42'),
(38,4,'Get All Course Topic','::1','2026-07-01 23:40:47'),
(39,4,'Course Topic dengan id 1 diupdate','::1','2026-07-02 00:10:21'),
(40,4,'Merubah Judul Dari Teroi Relativitas Waktu Enstein menjadi Mistis Dari Sudut Pandang Sains','::1','2026-07-02 00:24:32'),
(41,10,'Teacher menambahkan materi berjudul Konsep Proporsi','::1','2026-07-02 00:27:14'),
(42,10,'Get All Course Topic','::1','2026-07-02 00:27:31'),
(43,10,'Get All Course Topic','::1','2026-07-02 00:27:35'),
(44,10,'Teacher menambahkan materi berjudul Konsep Keseimbagan dan Kesetaraan','::1','2026-07-02 00:28:50'),
(45,10,'Get All Course Topic','::1','2026-07-02 00:28:57'),
(46,10,'Teacher menambahkan materi berjudul Hehe ngak jadi','::1','2026-07-02 00:34:40'),
(47,10,'Course Penerapan Proporsi Seni Untuk Estetika telah didelete','::1','2026-07-02 00:38:40'),
(48,4,'Course Topic dengan id 1 diupdate','::1','2026-07-02 00:42:04'),
(49,4,'Course Topic dengan id 1 diupdate','::1','2026-07-02 00:42:25'),
(50,4,'Get All Topic Materialid','::1','2026-07-06 10:19:00'),
(51,4,'Get All Topic Materialid','::1','2026-07-06 10:19:03'),
(52,4,'Get All Topic Materialid','::1','2026-07-06 10:19:50'),
(53,4,'Get All Topic Materialid','::1','2026-07-06 10:19:52'),
(54,4,'Get All Topic Materialid','::1','2026-07-06 10:20:28'),
(55,4,'Get All Topic Materialid','::1','2026-07-06 10:21:03'),
(56,4,'Get All Topic Materialid','::1','2026-07-06 10:21:46'),
(57,4,'Get All Topic Materialid','::1','2026-07-06 10:22:02'),
(58,3,'Get All User','::1','2026-07-06 11:05:44'),
(59,4,'Get All User','::1','2026-07-06 11:05:57'),
(60,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 11:19:08'),
(61,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 11:22:10'),
(62,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 11:23:12'),
(63,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 11:26:29'),
(64,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 11:28:22'),
(65,4,'Get All Topic Materialid','::1','2026-07-06 12:09:56'),
(66,4,'Get All Topic Materialid','::1','2026-07-06 12:09:58'),
(67,4,'Get All Topic Materialid','::1','2026-07-06 12:10:00'),
(68,4,'Get All Topic Materialid','::1','2026-07-06 12:11:02'),
(69,4,'Membuat Topic Material Baru : File untuk vidio relativitas waktu','::1','2026-07-06 12:19:40'),
(70,4,'Get All Topic Material','::1','2026-07-06 12:20:50'),
(71,4,'Get Topic Material By ID','::1','2026-07-06 12:22:31'),
(72,4,'Get Topic Material By ID','::1','2026-07-06 12:23:04'),
(73,4,'Get Topic Material By ID','::1','2026-07-06 12:24:16'),
(74,4,'Get Topic Material By ID','::1','2026-07-06 12:24:24'),
(75,4,'Get Topic Material By ID','::1','2026-07-06 12:24:36'),
(76,4,'Get Topic Material By ID','::1','2026-07-06 12:28:08'),
(77,4,'Get All Topic Material','::1','2026-07-06 17:55:43'),
(78,4,'Get All Topic Material','::1','2026-07-06 18:04:05'),
(79,4,'Get All Quizzes','::1','2026-07-06 18:04:27'),
(80,4,'Get All Quizzes','::1','2026-07-06 18:04:28'),
(81,4,'Get All Quizzes','::1','2026-07-06 18:04:51'),
(82,4,'Get All Quizzes','::1','2026-07-06 18:16:17'),
(83,4,'Teacher menambahkan materi berjudul Kuis Siapa Itu Enstein dan Waktu','::1','2026-07-07 10:20:51'),
(84,4,'Membuat Quizz Baru Dengan ID : 1','::1','2026-07-07 10:26:40'),
(85,4,'Get All Quizzes','::1','2026-07-07 10:26:54'),
(86,4,'Get All Quizzes','::1','2026-07-07 10:27:47'),
(87,4,'Get All Quizzes','::1','2026-07-07 10:28:41'),
(88,4,'Get All Quizzes','::1','2026-07-07 10:32:20'),
(89,4,'Get Quizz By ID','::1','2026-07-07 10:32:32'),
(90,4,'Teacher menambahkan materi berjudul Proyek Relativitas Waktu','::1','2026-07-07 10:41:34'),
(91,4,'Course Topic dengan id 7 diupdate','::1','2026-07-07 10:45:15'),
(92,4,'Membuat Quizz Baru Dengan ID : 2','::1','2026-07-07 10:45:29'),
(93,4,'Get All Quiz Questions','::1','2026-07-07 11:00:48'),
(94,4,'Membuat Quiz Question Baru Dengan ID : 1','::1','2026-07-07 11:24:21'),
(95,4,'Quiz Question dengan id : 1 telah dirubah','::1','2026-07-07 11:26:20'),
(96,4,'Quiz Question dengan id 1 telah didelete','::1','2026-07-07 11:27:09'),
(97,4,'Membuat Quiz Question Baru Dengan ID : 2','::1','2026-07-07 11:27:17'),
(98,4,'Get Quiz Question By ID','::1','2026-07-07 11:27:31'),
(99,4,'Get All Quiz Question Options','::1','2026-07-07 12:25:27'),
(100,4,'Membuat Quiz Question Option Baru Dengan ID : 1','::1','2026-07-07 12:29:09'),
(101,4,'Membuat Quiz Question Option Baru Dengan ID : 2','::1','2026-07-07 12:29:18'),
(102,4,'Membuat Quiz Question Option Baru Dengan ID : 3','::1','2026-07-07 12:29:24'),
(103,4,'Membuat Quiz Question Option Baru Dengan ID : 4','::1','2026-07-07 12:29:37'),
(104,4,'Quiz Question Option dengan id : 2 telah dirubah','::1','2026-07-07 12:48:23'),
(105,4,'Quiz Question Option dengan id : 2 telah dirubah','::1','2026-07-07 12:48:35'),
(106,4,'Quiz Question Option dengan id : 2 telah dirubah','::1','2026-07-07 12:48:46'),
(107,4,'Quiz Question Option dengan id : 2 telah dirubah','::1','2026-07-07 12:49:06'),
(108,4,'Quiz Question Option dengan id : 3 telah dirubah','::1','2026-07-07 12:49:16'),
(109,4,'Quiz Question Option dengan id : 4 telah dirubah','::1','2026-07-07 12:49:30'),
(110,4,'Quiz Question Option dengan id : 4 telah dirubah','::1','2026-07-07 12:56:02'),
(111,4,'Quiz Question Option dengan id : 4 telah dirubah','::1','2026-07-07 12:56:07'),
(112,4,'Membuat Quiz Question Option Baru Dengan ID : 5','::1','2026-07-07 12:57:33'),
(113,4,'Quiz Question Option dengan id 5 telah didelete','::1','2026-07-07 12:57:54'),
(114,4,'Get All Student Submissions','::1','2026-07-07 13:05:53');

/*Table structure for table `admin_messages` */

DROP TABLE IF EXISTS `admin_messages`;

CREATE TABLE `admin_messages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `admin_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `message_title` varchar(150) NOT NULL,
  `message_body` text NOT NULL,
  `is_read` tinyint(1) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `admin_id` (`admin_id`),
  KEY `receiver_id` (`receiver_id`),
  CONSTRAINT `admin_messages_ibfk_1` FOREIGN KEY (`admin_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `admin_messages_ibfk_2` FOREIGN KEY (`receiver_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `admin_messages` */

/*Table structure for table `course_enrollments` */

DROP TABLE IF EXISTS `course_enrollments`;

CREATE TABLE `course_enrollments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `course_id` int(11) NOT NULL,
  `is_bookmarked` tinyint(1) DEFAULT 0,
  `status` enum('active','completed') DEFAULT 'active',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `student_id` (`student_id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_enrollments_ibfk_1` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE,
  CONSTRAINT `course_enrollments_ibfk_2` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `course_enrollments` */

/*Table structure for table `course_topics` */

DROP TABLE IF EXISTS `course_topics`;

CREATE TABLE `course_topics` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `course_id` int(11) NOT NULL,
  `topic_number` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `description` text NOT NULL,
  `content_type` enum('material','quiz') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `course_id` (`course_id`),
  CONSTRAINT `course_topics_ibfk_1` FOREIGN KEY (`course_id`) REFERENCES `courses` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `course_topics` */

insert  into `course_topics`(`id`,`course_id`,`topic_number`,`title`,`description`,`content_type`,`created_at`,`updated_at`,`deleted_at`) values 
(1,2,0,'Enstein Sang Maistro Waktu ','Siapa itu Enstein ? Mari kenal siapa dia bersama sama . . .','material','2026-07-01 23:36:43','2026-07-02 00:42:25',NULL),
(2,2,2,'Memahami Apa Itu Waktu','Waktu sangat dekat dengan kehidupan kita, jam perlahan berdetak, tahun silih berganti namun sebenarnya apa itu waktu ? ','material','2026-07-01 23:40:22','2026-07-01 23:40:22',NULL),
(3,5,0,'Konsep Proporsi','Kenapa gambarmu terlalu hampa ? Kamu menambahkan detail namun sekarang kamu melihat gambarmu over detailing ? Mungkin kamu harus memahamu proporsi','material','2026-07-02 00:27:14','2026-07-02 00:27:14',NULL),
(4,5,0,'Konsep Keseimbagan dan Kesetaraan','Kiri kanan sama berat itu seimbag ? Sayangnya seni bukan timbagan. Mari kita pahami apa itu Keseimbagan dalam seni rupa','material','2026-07-02 00:28:50','2026-07-02 00:28:50',NULL),
(5,5,0,'Hehe ngak jadi','lorem ipsum 10 kali . . . ','material','2026-07-02 00:34:40','2026-07-02 00:37:11','2026-07-02 00:37:11'),
(6,2,0,'Kuis Siapa Itu Enstein dan Waktu','Pada pertemuan ini akan diadqakan kuiss','quiz','2026-07-07 10:20:51','2026-07-07 10:20:51',NULL),
(7,2,0,'Latihan 2 Relativitas Waktu','Kita akan melakukan latihan lagi','quiz','2026-07-07 10:41:34','2026-07-07 10:45:15',NULL);

/*Table structure for table `courses` */

DROP TABLE IF EXISTS `courses`;

CREATE TABLE `courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(150) NOT NULL,
  `category` enum('mathematics','science','art','technology','social') NOT NULL,
  `teacher_id` int(11) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `teacher_id` (`teacher_id`),
  CONSTRAINT `courses_ibfk_1` FOREIGN KEY (`teacher_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `courses` */

insert  into `courses`(`id`,`title`,`category`,`teacher_id`,`created_at`,`updated_at`,`deleted_at`) values 
(1,'Mistis Dari Sudut Pandang Sains','science',4,'2026-07-01 18:22:08','2026-07-02 00:24:32',NULL),
(2,'Teroi Relativitas Waktu Enstein','science',4,'2026-07-01 18:23:22','2026-07-01 18:23:22',NULL),
(3,'Misteri Angka Pi','mathematics',4,'2026-07-01 18:24:04','2026-07-01 18:24:04',NULL),
(4,'Cara Hittler Menguasai Dunia Dengan Seni','art',4,'2026-07-01 20:49:56','2026-07-01 20:49:56',NULL),
(5,'Penerapan Proporsi Seni Untuk Estetika','art',10,'2026-07-01 21:07:04','2026-07-02 00:38:40','2026-07-02 00:38:40'),
(6,'Penerapan Matematika Dalam Seni ','art',10,'2026-07-01 21:09:09','2026-07-01 21:50:00','2026-07-01 21:50:00'),
(7,'Color Of Theory','art',10,'2026-07-01 21:29:10','2026-07-01 21:45:40',NULL);

/*Table structure for table `cs_chatbot_chats` */

DROP TABLE IF EXISTS `cs_chatbot_chats`;

CREATE TABLE `cs_chatbot_chats` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `sender` enum('user','bot') NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `cs_chatbot_chats_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `cs_chatbot_chats` */

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `order_id` varchar(100) NOT NULL,
  `payment_token` varchar(255) NOT NULL,
  `amount` decimal(10,2) NOT NULL,
  `payment_method` enum('qris','va_bca','va_mandiri','va_bni','va_bri') NOT NULL,
  `va_number` varchar(50) DEFAULT NULL,
  `qr_url` text DEFAULT NULL,
  `status` enum('pending','success','failed','expired') DEFAULT 'pending',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `order_id` (`order_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `payments` */

/*Table structure for table `quiz_question_options` */

DROP TABLE IF EXISTS `quiz_question_options`;

CREATE TABLE `quiz_question_options` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quiz_question_id` int(11) NOT NULL,
  `option_letter` enum('A','B','C','D') NOT NULL,
  `option_text` text NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `quiz_question_id` (`quiz_question_id`),
  CONSTRAINT `quiz_question_options_ibfk_1` FOREIGN KEY (`quiz_question_id`) REFERENCES `quiz_questions` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `quiz_question_options` */

insert  into `quiz_question_options`(`id`,`quiz_question_id`,`option_letter`,`option_text`,`created_at`,`updated_at`,`deleted_at`) values 
(1,2,'A','Seorang Fisikawan','2026-07-07 12:29:09','2026-07-07 12:29:09',NULL),
(2,2,'B','Seorang Teolog','2026-07-07 12:29:18','2026-07-07 12:49:06',NULL),
(3,2,'C','Seorang Pendekar','2026-07-07 12:29:24','2026-07-07 12:49:16',NULL),
(4,2,'D','Seorang yang Mencari Jati Diri','2026-07-07 12:29:37','2026-07-07 12:56:07',NULL),
(5,2,'','Seorang yang Mencari Jati Diri','2026-07-07 12:57:33','2026-07-07 12:57:54','2026-07-07 12:57:54');

/*Table structure for table `quiz_questions` */

DROP TABLE IF EXISTS `quiz_questions`;

CREATE TABLE `quiz_questions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quiz_id` int(11) NOT NULL,
  `question_text` text NOT NULL,
  `correct_answer` text DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `quiz_id` (`quiz_id`),
  CONSTRAINT `quiz_questions_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `quiz_questions` */

insert  into `quiz_questions`(`id`,`quiz_id`,`question_text`,`correct_answer`,`created_at`,`updated_at`,`deleted_at`) values 
(1,2,'Mana pernyataan Albert Ensten yang Benar di Bawah Ini','A','2026-07-07 11:24:21','2026-07-07 11:27:09','2026-07-07 11:27:09'),
(2,2,'Siapa Itu Albert Enstein ?','A','2026-07-07 11:27:17','2026-07-07 11:27:17',NULL);

/*Table structure for table `quizzes` */

DROP TABLE IF EXISTS `quizzes`;

CREATE TABLE `quizzes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `quiz_category` enum('tugas','proyek','latihan','ujian') NOT NULL,
  `question_type` enum('multiple_choice','essay') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `quizzes_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `course_topics` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `quizzes` */

insert  into `quizzes`(`id`,`topic_id`,`quiz_category`,`question_type`,`created_at`,`updated_at`,`deleted_at`) values 
(1,6,'latihan','multiple_choice','2026-07-07 10:26:40','2026-07-07 10:40:15','2026-07-07 10:40:15'),
(2,7,'latihan','multiple_choice','2026-07-07 10:45:29','2026-07-07 10:45:29',NULL);

/*Table structure for table `student_submissions` */

DROP TABLE IF EXISTS `student_submissions`;

CREATE TABLE `student_submissions` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `quiz_id` int(11) NOT NULL,
  `student_id` int(11) NOT NULL,
  `essay_answer` text DEFAULT NULL,
  `file_url` varchar(255) DEFAULT NULL,
  `score` int(11) DEFAULT NULL,
  `teacher_comment` text DEFAULT NULL,
  `status` enum('submitted','graded') DEFAULT 'submitted',
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `quiz_id` (`quiz_id`),
  KEY `student_id` (`student_id`),
  CONSTRAINT `student_submissions_ibfk_1` FOREIGN KEY (`quiz_id`) REFERENCES `quizzes` (`id`) ON DELETE CASCADE,
  CONSTRAINT `student_submissions_ibfk_2` FOREIGN KEY (`student_id`) REFERENCES `users` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `student_submissions` */

/*Table structure for table `topic_materials` */

DROP TABLE IF EXISTS `topic_materials`;

CREATE TABLE `topic_materials` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `topic_id` int(11) NOT NULL,
  `video_url` varchar(255) DEFAULT NULL,
  `attachment_file` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `topic_id` (`topic_id`),
  CONSTRAINT `topic_materials_ibfk_1` FOREIGN KEY (`topic_id`) REFERENCES `course_topics` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `topic_materials` */

insert  into `topic_materials`(`id`,`topic_id`,`video_url`,`attachment_file`,`created_at`,`updated_at`,`deleted_at`) values 
(7,2,'https://youtu.be/PjZaETZYJ8A?si=JipfkiHv7dn91czY','Siapa itu enstein','2026-07-06 11:28:22','2026-07-06 12:16:26',NULL),
(8,1,'https://youtu.be/8pYhgt28Tj0?si=RxlOyaZZMBp_SgKo','Vidio apa itu waktu','2026-07-06 12:19:40','2026-07-06 12:29:03',NULL);

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(150) NOT NULL,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `google_id` varchar(255) DEFAULT NULL,
  `birthday_date` date DEFAULT NULL,
  `role` enum('student','teacher','admin') NOT NULL,
  `tier` enum('free','premium','none') DEFAULT 'free',
  `points` int(11) DEFAULT 0,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `deleted_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

/*Data for the table `users` */

insert  into `users`(`id`,`name`,`username`,`password`,`email`,`google_id`,`birthday_date`,`role`,`tier`,`points`,`created_at`,`updated_at`,`deleted_at`) values 
(2,'Admin Chronos','AdminChronosEdu123','$2b$10$fXtoInFA0b2deNcdzZtXfe9l3Ic8B1gddZnpv9tkECMcHkUqzkSiq','admin@chronosedu.com',NULL,NULL,'admin','none',0,'2026-06-07 23:18:17','2026-06-07 23:18:17',NULL),
(3,'Admin Chronos 2','a','$2b$10$2jpK24YEPS2QYX6j2zPBYOgpOK5Ll2tFlCL23Sbn0QJz1zrySlK52','admin2@chronosedu.com',NULL,NULL,'admin','none',0,'2026-06-07 23:20:00','2026-06-07 23:20:00',NULL),
(4,'Hamdani Ahmad Hidayat','t','$2b$10$Sar8BPkeyqQtXtKQv1cNG.cCIvzQssD8XwgItOCEhtOC.hxiuhz4i','hamad123@gmail.com',NULL,NULL,'teacher','none',0,'2026-06-07 23:32:19','2026-06-07 23:32:19',NULL),
(5,'Tirto Ganteng','s','$2b$10$2chpSgw.cjH043yjOpIPvuzXO0/5EvU.CTSFSue4VV/m1clsL2Api','ricTir@istts.edu',NULL,NULL,'student','free',0,'2026-06-07 23:37:07','2026-06-07 23:37:07',NULL),
(6,'Danny','danyzzzz','$2b$10$s2mgj6/a3mAYp9L85MOaMuf7OiIVHQfNrfvTX5eu.xCQDGKxUkyrC','dannysz@istts.edu',NULL,NULL,'student','free',0,'2026-06-08 00:23:32','2026-07-01 16:31:55',NULL),
(7,'James','JamesSukaBelajar','$2b$10$A6aLEsDmJRQeMn79kujqD.VHAZ4Hrm11kc1DQphyDWBkDFAnfQu/y','james08@istts.edu',NULL,NULL,'student','free',0,'2026-07-01 14:53:44','2026-07-01 15:00:36','2026-07-01 15:00:36'),
(8,'Ali','Aliiiiiiii','$2b$10$FNzFBOoc/JBuh7NZD/BDNu/6KYa8aV0gUl2HEAv8uYBlBAa4GT5o2','ali@istts.edu',NULL,'2004-04-06','student','free',0,'2026-07-01 15:06:48','2026-07-01 16:32:57','2026-07-01 16:32:57'),
(9,'Jax','ajaxx','$2b$10$EaAPhKntrkXYMJ.LXVOkdO4g.3GIBYBlx5M9raC3EfFSgN15rVTh.','jax@istts.edu',NULL,'2004-04-06','student','free',0,'2026-07-01 16:22:17','2026-07-01 21:39:20','2026-07-01 21:39:20'),
(10,'Lexy','AkuCintaMDP123','$2b$10$RGIRoDhR8twOLfyH8UffrOzktrjtecehhl4lKPqjfPBfSSNclEeCi','mylexyz@istts.edu',NULL,'2000-02-20','teacher','free',0,'2026-07-01 20:53:05','2026-07-01 20:53:05',NULL);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
