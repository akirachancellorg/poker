CREATE DATABASE /*!32312 IF NOT EXISTS*/ `cfunds_poker_db` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `cfunds_poker_db`;

DROP TABLE IF EXISTS `player`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `player` (
                          `id` bigint NOT NULL,
                          `name` varchar(255) DEFAULT NULL,
                          `hand` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

