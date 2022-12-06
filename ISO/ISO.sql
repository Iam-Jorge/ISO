CREATE DATABASE  IF NOT EXISTS `iso` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `iso`;
-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: iso
-- ------------------------------------------------------
-- Server version	5.5.60-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `clientes` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `vip` tinyint(4) DEFAULT NULL,
  `numcuenta` int(25) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
INSERT INTO `clientes` VALUES ('00412615H','Jermey','Tristin Boyer',617743810,NULL,NULL,'prueba','JermeyTB@gmail.com'),('37293322H','Vidal','Jerome Lesch',651680387,NULL,NULL,'prueba','VidalJL@gmail.com'),('57428879H','Loyce','Ollie Lehner',123,NULL,NULL,'prueba','qqq@gmail.com'),('65036829M','Buford','Jaylen Feil',612479077,NULL,NULL,'','BufordJF@gmail.com'),('93119557D','Dana','Daniela Kozey',699248798,1,NULL,'','DinaDK@gmail.com');
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cuentabancaria`
--

DROP TABLE IF EXISTS `cuentabancaria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cuentabancaria` (
  `numCuenta` int(25) NOT NULL,
  `saldo` decimal(15,0) DEFAULT NULL,
  `dni` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`numCuenta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cuentabancaria`
--

LOCK TABLES `cuentabancaria` WRITE;
/*!40000 ALTER TABLE `cuentabancaria` DISABLE KEYS */;
/*!40000 ALTER TABLE `cuentabancaria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `empleados`
--

DROP TABLE IF EXISTS `empleados`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `empleados` (
  `dni` varchar(9) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL,
  `apellidos` varchar(50) DEFAULT NULL,
  `telefono` int(9) DEFAULT NULL,
  `sueldo` decimal(10,0) DEFAULT NULL,
  `numCuenta` int(25) DEFAULT NULL,
  `cargo` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`dni`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `empleados`
--

LOCK TABLES `empleados` WRITE;
/*!40000 ALTER TABLE `empleados` DISABLE KEYS */;
INSERT INTO `empleados` VALUES ('00583516Y','Odie','Pinkie Erdman',693157650,NULL,NULL,'limpieza','OdiePE@gmail.com',''),('03054288A','Alena','Nikita Gleason',608318693,NULL,NULL,'limpieza','AlenaNG@gmail.com','prueba'),('32450442H','Zion','Tressa Block',686243448,NULL,690498272,'conserjería','ZionTB@gmail.com','prueba'),('45485171A','Neymar','Jr',617743810,0,1000,'administración','administrador@gmail.com',''),('47757962A','Ardella','Jillian Keeling',676717183,NULL,NULL,'limpieza','ArdellaJK@gmail.com','prueba'),('49864462W','Alfred','Spencer Willms',650366626,NULL,NULL,'limpieza','AlfredSW@gmail.com','prueba'),('59778365P','Giovanna','Rosalee Shanahan',691617720,NULL,NULL,'administración','GiovannaRS@gmail.com','prueba'),('63679393Y','Fabian','Esta Purdy',673422702,20,NULL,'conserjería','FabianEP@gmail.com','prueba');
/*!40000 ALTER TABLE `empleados` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `idPedido` int(11) NOT NULL,
  `codServicio` int(11) DEFAULT NULL,
  `nombre` varchar(45) DEFAULT NULL,
  `precio` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`idPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservado`
--

DROP TABLE IF EXISTS `reservado`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservado` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `dniCliente` varchar(9) DEFAULT NULL,
  `idReserva` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservado`
--

LOCK TABLES `reservado` WRITE;
/*!40000 ALTER TABLE `reservado` DISABLE KEYS */;
INSERT INTO `reservado` VALUES (1,'65036829M',1),(2,'65036829M',2),(9,'00412615H',3);
/*!40000 ALTER TABLE `reservado` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservas`
--

DROP TABLE IF EXISTS `reservas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `precio` decimal(10,0) DEFAULT NULL,
  `fechaInicio` datetime DEFAULT NULL,
  `fechaFin` datetime DEFAULT NULL,
  `estado` varchar(45) DEFAULT NULL,
  `localizacion` varchar(70) DEFAULT NULL,
  `superficie` decimal(10,0) DEFAULT NULL,
  `aparcamiento` tinyint(4) DEFAULT NULL,
  `piscina` tinyint(4) DEFAULT NULL,
  `wifi` tinyint(4) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservas`
--

LOCK TABLES `reservas` WRITE;
/*!40000 ALTER TABLE `reservas` DISABLE KEYS */;
INSERT INTO `reservas` VALUES (1,250,'2022-01-01 00:00:00','2022-01-01 00:00:00','ocupado','Narnia',500,1,0,1),(2,350,'2022-01-01 00:00:00','2022-01-01 00:00:00','disponible','Hogwarts',800,1,1,1),(3,25,'2022-01-01 00:00:00','2022-01-01 00:00:00','disponible','Mordor',1200,0,0,0),(4,120,NULL,NULL,'disponible','El País de las Maravillas',25000,1,1,1),(5,50,NULL,NULL,'ocupado','Naboo',576,0,0,0),(6,75,NULL,NULL,'disponible','Tierra media',850,1,1,0),(7,23,NULL,NULL,'disponible','Bikini bottom ',500,0,1,1),(8,78,NULL,NULL,'limpieza','Springfeild ',793,1,1,1),(9,47,NULL,NULL,'desperfecto','Gotham ',120,1,1,0),(10,2,NULL,NULL,'limpieza','Shrek swamp',200,0,0,0),(11,14,NULL,NULL,'desperfecto','Vice City',333,1,1,0),(12,36,NULL,NULL,'desperfecto','Anor Londo',222,0,0,0),(13,9,NULL,NULL,'ocupado','Dust2',57,0,0,0),(14,150,'2022-01-01 00:00:00','2023-05-01 00:00:00','disponible','Bernabéu',9000,1,0,1),(15,5,NULL,NULL,'limpieza','Plaza mayor',200,0,0,0);
/*!40000 ALTER TABLE `reservas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tarjetacredito`
--

DROP TABLE IF EXISTS `tarjetacredito`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tarjetacredito` (
  `numTarjeta` int(11) NOT NULL,
  `dniPropietario` varchar(45) DEFAULT NULL,
  `saldo` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`numTarjeta`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tarjetacredito`
--

LOCK TABLES `tarjetacredito` WRITE;
/*!40000 ALTER TABLE `tarjetacredito` DISABLE KEYS */;
INSERT INTO `tarjetacredito` VALUES (123456789,'00412615H',100),(599450253,'37293322H',60),(604319158,'00583516Y',50),(690498272,'32450442H',0),(902673339,'45485171A',0);
/*!40000 ALTER TABLE `tarjetacredito` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'iso'
--

--
-- Dumping routines for database 'iso'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-12-06 12:37:13
