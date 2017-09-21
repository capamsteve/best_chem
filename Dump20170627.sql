-- MySQL dump 10.13  Distrib 5.7.9, for Win64 (x86_64)
--
-- Host: localhost    Database: bestchem_db2
-- ------------------------------------------------------
-- Server version	5.7.11-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `bom`
--

DROP TABLE IF EXISTS `bom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `bom` (
  `idbom` int(11) NOT NULL AUTO_INCREMENT,
  `fg_inventorysku` int(11) NOT NULL,
  `units_bom` int(11) DEFAULT NULL,
  `bom_stat` enum('OPEN','DELETED') NOT NULL,
  `pm_sku` varchar(100) DEFAULT NULL,
  `pm_desc` varchar(100) DEFAULT NULL,
  `pm_invent` int(11) DEFAULT NULL,
  PRIMARY KEY (`idbom`),
  KEY `mm_fk_fg_idx` (`fg_inventorysku`),
  KEY `mm_fk_pm_idx` (`pm_invent`),
  CONSTRAINT `mm_fk_fg` FOREIGN KEY (`fg_inventorysku`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_fk_pm` FOREIGN KEY (`pm_invent`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bom`
--

LOCK TABLES `bom` WRITE;
/*!40000 ALTER TABLE `bom` DISABLE KEYS */;
/*!40000 ALTER TABLE `bom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contacts` (
  `idcontacts` int(11) NOT NULL AUTO_INCREMENT,
  `idcustomer` int(11) NOT NULL,
  `name` varchar(100) DEFAULT NULL,
  `number` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`idcontacts`,`idcustomer`),
  KEY `fk_contacts_customer_idx` (`idcustomer`),
  CONSTRAINT `fk_contacts_customer` FOREIGN KEY (`idcustomer`) REFERENCES `customers` (`idcustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

LOCK TABLES `contacts` WRITE;
/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customers`
--

DROP TABLE IF EXISTS `customers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customers` (
  `idcustomer` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(200) DEFAULT NULL,
  `tin` varchar(45) DEFAULT NULL,
  `businessstyle` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `paymentterm` varchar(100) DEFAULT NULL,
  `discount` decimal(10,4) DEFAULT NULL,
  `uom_iduom` varchar(45) NOT NULL,
  `postal_cd` int(11) DEFAULT NULL,
  `vendor_cd` varchar(45) DEFAULT NULL,
  `vat_prcnt` decimal(10,4) DEFAULT NULL,
  `auto_invice` enum('1','0') DEFAULT NULL,
  PRIMARY KEY (`idcustomer`),
  KEY `fk_uom_idx` (`uom_iduom`),
  CONSTRAINT `fk_uom` FOREIGN KEY (`uom_iduom`) REFERENCES `uom` (`uomcol`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customers`
--

LOCK TABLES `customers` WRITE;
/*!40000 ALTER TABLE `customers` DISABLE KEYS */;
INSERT INTO `customers` VALUES (1,'Watsons Personal Care Stores (Philippines, Inc.)','214-706-591','Retail','Unit 211, 2/F The Podium ADB Ave, Ortigas Center Mand. City','COD',5.0000,'CS',1550,'020217',12.0000,'1'),(2,'UNISELL CORPORATION','','','C/O CTSI KM 14 West Service Rd. Paranaque City','COD',0.0000,'CS',0,'16-2359',12.0000,'0');
/*!40000 ALTER TABLE `customers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveryorderitems`
--

DROP TABLE IF EXISTS `deliveryorderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliveryorderitems` (
  `iddeliveryorderitems` int(11) NOT NULL AUTO_INCREMENT,
  `drorder` int(11) NOT NULL,
  `drqty` int(11) NOT NULL,
  `dritemsi` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`iddeliveryorderitems`),
  KEY `dritemfk_idx` (`drorder`),
  KEY `dritemsifk_idx` (`dritemsi`),
  CONSTRAINT `drfk` FOREIGN KEY (`drorder`) REFERENCES `deliveryorders` (`iddeliveryorders`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `dritemfk` FOREIGN KEY (`dritemsi`) REFERENCES `salesorderitems` (`idsalesorderitem`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveryorderitems`
--

LOCK TABLES `deliveryorderitems` WRITE;
/*!40000 ALTER TABLE `deliveryorderitems` DISABLE KEYS */;
INSERT INTO `deliveryorderitems` VALUES (1,200000000,0,1,'Y'),(2,200000000,0,2,'Y'),(3,200000001,2,3,'Y'),(4,200000001,0,6,'Y'),(5,200000001,0,7,'Y'),(6,200000002,2,8,'Y'),(7,200000003,3,9,'Y'),(8,200000003,0,10,'Y'),(9,200000003,2,11,'Y'),(10,200000004,11,12,'Y'),(11,200000004,12,13,'Y'),(12,200000004,2,14,'Y');
/*!40000 ALTER TABLE `deliveryorderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `deliveryorders`
--

DROP TABLE IF EXISTS `deliveryorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `deliveryorders` (
  `iddeliveryorders` int(11) NOT NULL AUTO_INCREMENT,
  `drdate` date DEFAULT NULL,
  `customerid` int(11) NOT NULL,
  `soid` int(11) NOT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `chckby` int(11) NOT NULL,
  `drprint` enum('Y','N') DEFAULT NULL,
  `drstatus` enum('open','complete','cancelled') DEFAULT NULL,
  `drpgi` enum('Y','N') DEFAULT NULL,
  `invcstat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`iddeliveryorders`),
  KEY `custidfk_idx` (`customerid`),
  KEY `soidfk_idx` (`soid`),
  KEY `cbyidfk_idx` (`chckby`),
  CONSTRAINT `cbyidfk` FOREIGN KEY (`chckby`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `custidfk` FOREIGN KEY (`customerid`) REFERENCES `customers` (`idcustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `soidfk` FOREIGN KEY (`soid`) REFERENCES `salesorders` (`idsalesorder`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=200000005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `deliveryorders`
--

LOCK TABLES `deliveryorders` WRITE;
/*!40000 ALTER TABLE `deliveryorders` DISABLE KEYS */;
INSERT INTO `deliveryorders` VALUES (200000000,'2017-06-07',1,100000000,'',2,'N','complete','Y','N'),(200000001,'2017-06-07',1,100000001,'',2,'N','complete','Y','N'),(200000002,'2017-06-07',1,100000002,'',2,'N','complete','Y','N'),(200000003,'2017-06-24',1,100000003,'',2,'N','complete','Y','N'),(200000004,'2017-06-24',1,100000004,'',2,'N','complete','Y','N');
/*!40000 ALTER TABLE `deliveryorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory`
--

DROP TABLE IF EXISTS `inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory` (
  `idinventory` int(11) NOT NULL AUTO_INCREMENT,
  `sku` varchar(45) DEFAULT NULL,
  `skudesc` varchar(45) DEFAULT NULL,
  `skuom` varchar(45) NOT NULL,
  `skuwh` varchar(45) NOT NULL,
  `soh` int(11) DEFAULT NULL,
  `csl` int(11) DEFAULT NULL,
  `units` int(11) NOT NULL,
  `inv_type` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idinventory`),
  KEY `fk_wh_idx` (`skuwh`),
  KEY `fk_uom_idx` (`skuom`),
  CONSTRAINT `fk_uomi` FOREIGN KEY (`skuom`) REFERENCES `uom` (`uomcol`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `fk_whi` FOREIGN KEY (`skuwh`) REFERENCES `warehouses` (`idwarehouses`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=666 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory`
--

LOCK TABLES `inventory` WRITE;
/*!40000 ALTER TABLE `inventory` DISABLE KEYS */;
INSERT INTO `inventory` VALUES (1,'10038957','B TREATS Body Wash Papaya Orange 850ml','CS','BC',-2,18,12,'WASHES'),(2,'10038958','B TREATS Body Wash Papaya Green 850ml','CS','BC',0,18,12,'WASHES'),(3,'10038959','B TREATS Body Wash Virgin Coco 850ml','CS','BC',103,18,12,'WASHES'),(4,'10038960','B TREATS Body Wash Calamansi 850ml','CS','BC',3,18,12,'WASHES'),(5,'10038961','B Treats Body Lotion Papaya Orange 850ml','CS','BC',0,89,12,'HOT'),(6,'10038962','B Treats Body Lotion Papaya Green 850ml','CS','BC',0,44,12,'HOT'),(7,'10038963','B Treats Body Lotion Virgin Coco 850ml','CS','BC',3,44,12,'HOT'),(8,'10038964','B Treats Body Lotion Calamansi 850ml','CS','BC',0,89,12,'HOT'),(9,'10039150','Milk Essence Baby Milk Bsoap Lavander 850ml','CS','BC',0,18,12,'WASHES'),(10,'10039151','Milk Essence Baby Milk Bsoap Sweet Pea 850ml','CS','BC',0,18,12,'WASHES'),(11,'10039152','Milk Essence Baby Milk Lotion Lavander 850ml','CS','BC',0,97,12,'HOT'),(12,'10039153','Milk Essence Baby Milk Lotion Sweet Pea 850ml','CS','BC',0,73,12,'HOT'),(13,'10046036','B Treats WaterMelon Hand Soap 750ml','CS','BC',0,48,12,'WASHES'),(14,'10046038','B Treats Melon Hand Soap 750ml','CS','BC',0,189,12,'WASHES'),(15,'10046039','B Treats Citrus Hand Soap 750ml','CS','BC',0,189,12,'WASHES'),(16,'10046040','B Treats Magnolia Hand Soap 750ml','CS','BC',0,189,12,'WASHES'),(17,'10050491','B-Treats Body Scrub Watermelon 250g x 2s','CS','BC',0,20,12,'HOT'),(18,'10050492','B-Treats Body Scrub Melon 250g x 2s','CS','BC',0,20,12,'HOT'),(19,'10050503','F Treats Ft Scrub Citrus 250g x 2s','CS','BC',0,40,12,'HOT'),(20,'10050504','F Treats Ft Scrub Magnolia 250g x 2s','CS','BC',0,40,12,'HOT'),(21,'10053293','F Treats Ft Spray Citrus 118ml','CS','BC',0,10,48,'HYDRO'),(22,'10053294','F Treats Ft Spray Magnolia 118ml','CS','BC',0,10,48,'HYDRO'),(23,'10053295','F Treats Ft Powder Citrus 100g','CS','BC',0,10,48,'HYDRO'),(24,'10053296','F Treats Ft Powder Magnolia 100g','CS','BC',0,10,48,'HYDRO'),(25,'10053297','F Treats Ft Soak Citrus 250ml','CS','BC',0,16,48,'WASHES'),(26,'10053298','F Treats Ft Soak Magnolia 250ml','CS','BC',0,16,48,'WASHES'),(27,'10059530','B TREATS Body Wash Lavander 510ml','CS','BC',0,14,24,'WASHES'),(28,'10059532','B TREATS Body Wash Evening Primrose 510ml','CS','BC',0,14,24,'WASHES'),(29,'10059535','B TREATS Body Wash Grapeseed 510ml','CS','BC',0,14,24,'WASHES'),(30,'10061191','B Treats Body Spray Ocean Blue 100ml','CS','BC',0,10,48,'HYDRO'),(31,'10061193','B Treats Body Spray Refreshing Green 100ml','CS','BC',0,10,48,'HYDRO'),(32,'10061195','B Treats Body Spray Cotton Blossom 100ml','CS','BC',0,10,48,'HYDRO'),(33,'10061196','B Treats Body Spray Purple Rain 100ml','CS','BC',0,10,48,'HYDRO'),(34,'10061377','B Treats Body Lotion Lavander 510ml','CS','BC',0,18,24,'HOT'),(35,'10061380','B Treats Body Lotion Evening Primrose 510ml','CS','BC',0,18,24,'HOT'),(36,'10061383','B-Treats Body Scrub Lavander Moist 250g x 2s','CS','BC',0,40,12,'HOT'),(37,'10061385','F Treats Ft Scrub Evening Primrose 250gx2s','CS','BC',0,40,12,'HOT'),(38,'10061386','F Treats Ft Scrub Grapeseed 250gx2s','CS','BC',0,40,12,'HOT'),(39,'10061391','F Treats Ft Spray Evening Primrose 118ml','CS','BC',0,10,48,'HYDRO'),(40,'10061394','F Treats Ft Spray Grapeseed 118ml','CS','BC',0,10,48,'HYDRO'),(41,'10061396','F Treats Ft Soak Evening Primrose 250ml','CS','BC',0,16,48,'WASHES'),(42,'10061398','F Treats Ft Soak Grapeseed 250ml','CS','BC',0,16,48,'WASHES'),(43,'10061399','F Treats Ft Powder Evening Primrose 100g','CS','BC',0,10,48,'HYDRO'),(44,'10061401','F Treats Ft Powder Grapeseed 100g','CS','BC',0,10,48,'HYDRO'),(45,'10064388','Glamworks Hair Serum Anti Frizz 30ml','CS','BC',0,8,96,'HYDRO'),(46,'10064394','Glamworks Hair Serum Heat Defense 30ml','CS','BC',0,8,96,'HYDRO'),(47,'10064396','Glamworks Hair Serum Silky Straight 30ml','CS','BC',0,8,96,'HYDRO'),(48,'10064398','Glamworks Hair Serum Deep Repair 30ml','CS','BC',0,8,96,'HYDRO'),(49,'10066446','B TREATS Hand Sanitizer Watermelon 75ml','CS','BC',0,14,96,'HOT'),(50,'10066447','B TREATS Hand Sanitizer Melon 75ml','CS','BC',0,14,96,'HOT'),(51,'10066448','B TREATS Hand Sanitizer Citrus 75ml','CS','BC',0,14,96,'HOT'),(52,'10066450','B TREATS Hand Sanitizer Magnolia 75ml','CS','BC',0,14,96,'HOT'),(53,'10078329','Hair Treats Hair Shine 65ml','CS','BC',0,106,96,'HYDRO'),(54,'10078330','Hair Treats Hair Spa 650g Lavander','CS','BC',0,263,12,'HOT'),(55,'10078331','Hair Treats Hair Spa 650g Cocomilk','CS','BC',0,263,12,'HOT'),(56,'10078332','Hair Treats Hair Spa 650g Virgin Coco','CS','BC',0,263,12,'HOT'),(57,'10078334','Hair Treats Hair Spa 250g Lavander','CS','BC',0,18,48,'HOT'),(58,'10078335','Hair Treats Hair Spa 250g Cocomilk','CS','BC',0,18,48,'HOT'),(59,'10078336','Hair Treats Hair Spa 250g Virgin Coco','CS','BC',0,18,48,'HOT'),(60,'10080925','Labworks Hair Gum 100g','CS','BC',0,11,48,'HOT'),(61,'10080926','Labworks Hair Wax 100g','CS','BC',0,11,48,'HOT'),(62,'10080927','Labworks Hair Clay 100g','CS','BC',0,11,48,'HOT'),(63,'10080933','B Treats Deo Body Spray Aqua Mist 120ml','CS','BC',0,20,24,'HYDRO'),(64,'10080934','B Treats Deo Body Spray Peach Bloom 120ml','CS','BC',0,20,24,'HYDRO'),(65,'10083987','Hair Treats Hair Coat Ginseng 55ml','CS','BC',0,19,96,'HYDRO'),(66,'10083988','Hair Treats Hair Coat Virgin Coco 55ml','CS','BC',0,19,96,'HYDRO'),(67,'10083989','Hair Treats Hair Coat Royal Jelly 55ml','CS','BC',0,19,96,'HYDRO'),(68,'10083990','Hair Treats Hair Coat Macadamia 55ml','CS','BC',0,19,96,'HYDRO'),(69,'10084075','Hair Treats Hair Spa 250g Macademia','CS','BC',0,18,48,'HOT'),(70,'10084076','Hair Treats Hair Spa 650g Macademia','CS','BC',0,175,12,'HOT'),(71,'10084077','Hair Treats Hair Spa 250g Aloe Vera','CS','BC',0,18,48,'HOT'),(72,'10084078','Hair Treats Hair Spa 650g Aloe Vera','CS','BC',0,175,12,'HOT'),(73,'10084081','Hair Treat Hair Shine Pink 30ml','CS','BC',0,32,96,'HYDRO'),(74,'10084082','Hair Treat Hair Shine Green 30ml','CS','BC',0,16,96,'HYDRO'),(75,'10084083','Hair Treat Hair Shine Lavander 30ml','CS','BC',0,32,96,'HYDRO'),(76,'10084084','Hair Treat Hair Shine Brown 30ml','CS','BC',0,16,96,'HYDRO'),(77,'10084085','Hair Treat Hair Shine Yellow 30ml','CS','BC',0,16,96,'HYDRO'),(78,'10085889','Glamworks Hair Luxe Color Treated 650g','CS','BC',0,29,12,'HOT'),(79,'10085890','Glamworks Hair Luxe Damaged Hair 650g','CS','BC',0,29,12,'HOT'),(80,'10086258','Glamworks Hair Luxe Intensive Hair 650g','CS','BC',0,29,12,'HOT'),(81,'10086259','Glamworks Hair Luxe Hair Fall 650g','CS','BC',0,29,12,'HOT'),(82,'10086826','Glamworks Body Wash Sweet Apple 1L','CS','BC',0,91,12,'WASHES'),(83,'10086827','Glamworks Body Wash Cherry Blossom 1L','CS','BC',0,91,12,'WASHES'),(84,'10086830','Glamworks Body Wash Papaya Milk 1L','CS','BC',0,91,12,'WASHES'),(85,'10086931','Glamworks Hair Polish Color Treated Hair 100g','CS','BC',0,32,24,'HOT'),(86,'10086932','Glamworks Hair Polish Damaged  100g','CS','BC',0,32,24,'HOT'),(87,'10086938','Glamworks Lip N Cheek Strawberry Red 10g','CS','BC',0,50,12,'HYDRO'),(88,'10086939','Glamworks Lip N Cheek Peach Pink 10g','CS','BC',0,50,12,'HYDRO'),(89,'10086959','Milk Essence Baby Milk Bsoap Powder 850ml','CS','BC',0,36,12,'WASHES'),(90,'10086960','Milk Essence Baby Milk Lotion Powder 850ml','CS','BC',0,48,12,'HOT'),(91,'10087016','Milk Essence Baby Milk Bsoap White 120ml','CS','BC',0,32,24,'WASHES'),(92,'10087017','Milk Essence Baby Milk Bsoap Pink 120ml','CS','BC',0,32,24,'WASHES'),(93,'10087018','Milk Essence Baby Milk Bsoap Violet 120ml','CS','BC',0,16,24,'WASHES'),(94,'10087019','Milk Essence Baby Milk Blotion White 120ml','CS','BC',0,32,24,'HOT'),(95,'10087020','Milk Essence Baby Milk Blotion Pink 120ml','CS','BC',0,32,24,'HOT'),(96,'10087021','Milk Essence Baby Milk Blotion Violet 120ml','CS','BC',-2,16,24,'HOT'),(97,'10090327','Watsons Body Scrub Cucumber Melon 250ml','CS','BC',0,37,24,'HOT'),(98,'10090328','Watsons Body Scrub Grapefruit Peach 250ml','CS','BC',0,37,24,'HOT'),(99,'10090329','Watsons Body Scrub Strwbrry Citrus 250ml','CS','BC',0,37,24,'HOT'),(100,'10090330','Watsons Body Scrub Peach Mango 250ml','CS','BC',0,37,24,'HOT'),(101,'10090581','B Treats Hand Soap Sweet Pea 750ml','CS','BC',0,189,12,'WASHES'),(102,'10090582','B Treats Hand Soap Green Tea 750ml','CS','BC',0,189,12,'WASHES'),(103,'10091319','Hair Treats Hair Spa Argan Oil 650g','CS','BC',0,175,12,'HOT'),(104,'10091320','Hair Treats Hair Spa Tsubaki 650g','CS','BC',0,175,12,'HOT'),(105,'10091321','Hair Treats Hair Spa Tsubaki 250g','CS','BC',0,18,48,'HOT'),(106,'10091322','Hair Treats Hair Spa Argan Oil 250g','CS','BC',0,18,48,'HOT'),(107,'10092697','B Treats Hand Soap Wild Orchid 750ml','CS','BC',0,189,12,'WASHES'),(108,'10092698','B Treats Hand Soap Cranberry 750ml','CS','BC',0,189,12,'WASHES'),(109,'10093642','Glamworks Body Wash Sweet Pea 1L','CS','BC',0,45,12,'WASHES'),(110,'10093643','Beyond Care Blotion Moonlight Breeze 500ml','CS','BC',0,20,24,'HOT'),(111,'10093644','Beyond Care Blotion Fresh Bloom 500ml','CS','BC',0,20,24,'HOT'),(112,'10093645','Beyond Care Blotion Carribean Escape 500ml','CS','BC',0,20,24,'HOT'),(113,'10095307','Beyond Care Bwash Moonlight Breeze 500ml','CS','BC',0,20,12,'WASHES'),(114,'10095308','Beyond Care Bwash Fresh Bloom 500ml','CS','BC',0,20,12,'WASHES'),(115,'10095309','Beyond Care Bwash Carribean Escape 500ml','CS','BC',0,20,12,'WASHES'),(116,'10095437','Watsons B Lotion Whitening  500ml','CS','BC',0,0,24,'HOT'),(117,'10095438','Watsons B Lotion Deep Moist  500ml','CS','BC',0,0,24,'HOT'),(118,'10095439','Watsons B Lotion Refreshing 500ml','CS','BC',0,0,24,'HOT'),(119,'10097229','Hair Treats Megadose Dry and Damaged 2pc','CS','BC',0,42,12,'HOT'),(120,'10097230','Hair Treats Megadose Intensive 2pc','CS','BC',0,21,12,'HOT'),(121,'10097231','Hair Treats Megadose Color Treated 2pc','CS','BC',0,42,12,'HOT'),(122,'10098147','Watsons B Lotion Whitening  750ml','CS','BC',0,0,12,'HOT'),(123,'10098148','Watsons B Lotion Deep Moist  750ml','CS','BC',0,0,12,'HOT'),(124,'10098149','Watsons B Lotion Refreshing 750ml','CS','BC',0,0,12,'HOT'),(125,'10098597','B Treats Baby Cologne Happy Mist 125ml','CS','BC',0,20,24,'HYDRO'),(126,'10098598','B Treats Baby Cologne Soft Touch 125ml','CS','BC',0,20,24,'HYDRO'),(127,'10098599','B Treats Baby Cologne Sweet Smile 125ml','CS','BC',0,20,24,'HYDRO'),(128,'10098600','B Treats Baby Cologne Pure Heaven 125ml','CS','BC',0,20,24,'HYDRO'),(129,'10098601','B Treats Baby Cologne Bubbles 125ml','CS','BC',0,20,24,'HYDRO'),(130,'10098602','B Treats Deo Spray  White Musk 100ml','CS','BC',0,20,24,'HYDRO'),(131,'10098603','B Treats Deo Spray So Lovely 100ml','CS','BC',0,20,24,'HYDRO'),(132,'10098604','B Treats Deo Spray Sweet Melody 100ml','CS','BC',0,20,24,'HYDRO'),(133,'10099088','B TREATS Body Wash Acai 850ml','CS','BC',0,18,12,'WASHES'),(134,'10099089','B TREATS Body Wash Mangosteen 850ml','CS','BC',0,18,12,'WASHES'),(135,'10099090','B TREATS Body Lotion Acai 850ml','CS','BC',0,24,12,'HOT'),(136,'10099091','B TREATS Body Lotion Mangosteen 850ml','CS','BC',0,24,12,'HOT'),(137,'10099092','B TREATS Whtng Lotion Flawless White 1050ml','CS','BC',0,38,12,'HOT'),(138,'10099093','B TREATS Whtng Lotion Rosy White 1050ml','CS','BC',0,38,12,'HOT'),(139,'10099094','B TREATS Whtng Lotion Radiant White 1050ml','CS','BC',0,38,12,'HOT'),(140,'10099095','B TREATS Whtng Lotion Visibly White 1050ml','CS','BC',0,38,12,'HOT'),(141,'10099989','B TREATS Hand Soap Breezy Days 450ml','CS','BC',0,20,12,'WASHES'),(142,'10099990','B TREATS Hand Soap Satin Ribbons 450ml','CS','BC',0,20,12,'WASHES'),(143,'10099991','B TREATS Hand Soap Love Dazzled 450ml','CS','BC',0,20,12,'WASHES'),(144,'10099992','B TREATS Hand Soap Burst Of Sunshine 450ml','CS','BC',0,20,12,'WASHES'),(145,'10099994','Hair Treats 3Min Wonder Milk Protein 250g','CS','BC',0,417,12,'HOT'),(146,'10099995','Hair Treats 3Min Wonder Moroccan Oil 250g','CS','BC',0,417,12,'HOT'),(147,'10099996','Hair Treats 3Min Wonder Keratin Overload 250g','CS','BC',0,42,12,'HOT'),(148,'10099997','Hair Treats 3Min Wonder Aloe Infusion 250g','CS','BC',0,417,12,'HOT'),(149,'10101074','Beyond Care Anti Oxidant 400ml','CS','BC',0,10,12,'WASHES'),(150,'10101075','Beyond Care Soothing 400ml','CS','BC',0,10,12,'WASHES'),(151,'10101076','Beyond Care Intense Moisturizing 400ml','CS','BC',0,10,12,'WASHES'),(152,'10101077','Beyond Care Lightening 400ml','CS','BC',0,10,12,'WASHES'),(153,'10101331','Hair Treats Hairspa Honey Milk 650g','CS','BC',0,113,12,'HOT'),(154,'10101332','Hair Treats Hairspa Keratin Vit E 650g','CS','BC',0,169,12,'HOT'),(155,'10101333','B TREATS Hand Soap  Fresh Clean 750ml','CS','BC',0,189,12,'WASHES'),(156,'10101543','B TREATS Body Oil Lavander 230ml','CS','BC',0,20,24,'HYDRO'),(157,'10101713','Labworks Deo Spray Burgundy Mist 120ml','CS','BC',0,20,48,'HYDRO'),(158,'10101714','Labworks Deo Spray Saffron Rock 120ml','CS','BC',0,20,48,'HYDRO'),(159,'10101715','Labworks Deo Spray Navy Suede 120ml','CS','BC',0,20,48,'HYDRO'),(160,'10101716','Labworks Deo Spray Cool Blaze 120ml','CS','BC',-12,20,48,'HYDRO'),(161,'10101717','Labworks Deo Spray Aqua Tease 120ml','CS','BC',0,20,48,'HYDRO'),(162,'10101718','Labworks Deo Spray Indigo Spritz 120ml','CS','BC',0,20,48,'HYDRO'),(163,'50006581','B Treats Deo Body Spray Thousand Wishes 120ml','CS','BC',0,20,24,'HYDRO'),(164,'50006582','B Treats Deo Body Spray Magenta Magic 120ml','CS','BC',0,20,24,'HYDRO'),(165,'LAND0010000966','Lander Hand Soap Green Tea 1000ml','CS','BC',0,0,12,'WASHES'),(166,'LAND0010000965','Lander Hand Soap Spring Fresh 1000ml','CS','BC',0,0,12,'WASHES'),(167,'10038957','B TREATS Body Wash Papaya Orange 850ml','PC','BC',0,218,1,'WASHES'),(168,'10038958','B TREATS Body Wash Papaya Green 850ml','PC','BC',0,218,1,'WASHES'),(169,'10038959','B TREATS Body Wash Virgin Coco 850ml','PC','BC',0,218,1,'WASHES'),(170,'10038960','B TREATS Body Wash Calamansi 850ml','PC','BC',0,218,1,'WASHES'),(171,'10038961','B Treats Body Lotion Papaya Orange 850ml','PC','BC',0,1064,1,'HOT'),(172,'10038962','B Treats Body Lotion Papaya Green 850ml','PC','BC',0,532,1,'HOT'),(173,'10038963','B Treats Body Lotion Virgin Coco 850ml','PC','BC',0,532,1,'HOT'),(174,'10038964','B Treats Body Lotion Calamansi 850ml','PC','BC',0,1064,1,'HOT'),(175,'10039150','Milk Essence Baby Milk Bsoap Lavander 850ml','PC','BC',0,218,1,'WASHES'),(176,'10039151','Milk Essence Baby Milk Bsoap Sweet Pea 850ml','PC','BC',0,218,1,'WASHES'),(177,'10039152','Milk Essence Baby Milk Lotion Lavander 850ml','PC','BC',0,1160,1,'HOT'),(178,'10039153','Milk Essence Baby Milk Lotion Sweet Pea 850ml','PC','BC',0,870,1,'HOT'),(179,'10046036','B Treats WaterMelon Hand Soap 750ml','PC','BC',0,580,1,'WASHES'),(180,'10046038','B Treats Melon Hand Soap 750ml','PC','BC',0,2270,1,'WASHES'),(181,'10046039','B Treats Citrus Hand Soap 750ml','PC','BC',0,2270,1,'WASHES'),(182,'10046040','B Treats Magnolia Hand Soap 750ml','PC','BC',0,2270,1,'WASHES'),(183,'10050491','B-Treats Body Scrub Watermelon 250g x 2s','PC','BC',0,483,1,'HOT'),(184,'10050492','B-Treats Body Scrub Melon 250g x 2s','PC','BC',0,483,1,'HOT'),(185,'10050503','F Treats Ft Scrub Citrus 250g x 2s','PC','BC',0,966,1,'HOT'),(186,'10050504','F Treats Ft Scrub Magnolia 250g x 2s','PC','BC',0,966,1,'HOT'),(187,'10053293','F Treats Ft Spray Citrus 118ml','PC','BC',0,482,1,'HYDRO'),(188,'10053294','F Treats Ft Spray Magnolia 118ml','PC','BC',0,482,1,'HYDRO'),(189,'10053295','F Treats Ft Powder Citrus 100g','PC','BC',0,484,1,'HYDRO'),(190,'10053296','F Treats Ft Powder Magnolia 100g','PC','BC',0,484,1,'HYDRO'),(191,'10053297','F Treats Ft Soak Citrus 250ml','PC','BC',0,773,1,'WASHES'),(192,'10053298','F Treats Ft Soak Magnolia 250ml','PC','BC',0,773,1,'WASHES'),(193,'10059530','B TREATS Body Wash Lavander 510ml','PC','BC',0,337,1,'WASHES'),(194,'10059532','B TREATS Body Wash Evening Primrose 510ml','PC','BC',0,337,1,'WASHES'),(195,'10059535','B TREATS Body Wash Grapeseed 510ml','PC','BC',0,337,1,'WASHES'),(196,'10061191','B Treats Body Spray Ocean Blue 100ml','PC','BC',0,486,1,'HYDRO'),(197,'10061193','B Treats Body Spray Refreshing Green 100ml','PC','BC',0,486,1,'HYDRO'),(198,'10061195','B Treats Body Spray Cotton Blossom 100ml','PC','BC',0,486,1,'HYDRO'),(199,'10061196','B Treats Body Spray Purple Rain 100ml','PC','BC',0,486,1,'HYDRO'),(200,'10061377','B Treats Body Lotion Lavander 510ml','PC','BC',0,434,1,'HOT'),(201,'10061380','B Treats Body Lotion Evening Primrose 510ml','PC','BC',0,434,1,'HOT'),(202,'10061383','B-Treats Body Scrub Lavander Moist 250g x 2s','PC','BC',0,966,1,'HOT'),(203,'10061385','F Treats Ft Scrub Evening Primrose 250gx2s','PC','BC',0,966,1,'HOT'),(204,'10061386','F Treats Ft Scrub Grapeseed 250gx2s','PC','BC',0,966,1,'HOT'),(205,'10061391','F Treats Ft Spray Evening Primrose 118ml','PC','BC',0,484,1,'HYDRO'),(206,'10061394','F Treats Ft Spray Grapeseed 118ml','PC','BC',0,484,1,'HYDRO'),(207,'10061396','F Treats Ft Soak Evening Primrose 250ml','PC','BC',0,773,1,'WASHES'),(208,'10061398','F Treats Ft Soak Grapeseed 250ml','PC','BC',0,773,1,'WASHES'),(209,'10061399','F Treats Ft Powder Evening Primrose 100g','PC','BC',0,484,1,'HYDRO'),(210,'10061401','F Treats Ft Powder Grapeseed 100g','PC','BC',0,484,1,'HYDRO'),(211,'10064388','Glamworks Hair Serum Anti Frizz 30ml','PC','BC',0,784,1,'HYDRO'),(212,'10064394','Glamworks Hair Serum Heat Defense 30ml','PC','BC',0,784,1,'HYDRO'),(213,'10064396','Glamworks Hair Serum Silky Straight 30ml','PC','BC',0,784,1,'HYDRO'),(214,'10064398','Glamworks Hair Serum Deep Repair 30ml','PC','BC',0,784,1,'HYDRO'),(215,'10066446','B TREATS Hand Sanitizer Watermelon 75ml','PC','BC',0,1356,1,'HOT'),(216,'10066447','B TREATS Hand Sanitizer Melon 75ml','PC','BC',0,1356,1,'HOT'),(217,'10066448','B TREATS Hand Sanitizer Citrus 75ml','PC','BC',0,1356,1,'HOT'),(218,'10066450','B TREATS Hand Sanitizer Magnolia 75ml','PC','BC',0,1356,1,'HOT'),(219,'10078329','Hair Treats Hair Shine 65ml','PC','BC',0,10197,1,'HYDRO'),(220,'10078330','Hair Treats Hair Spa 650g Lavander','PC','BC',0,3150,1,'HOT'),(221,'10078331','Hair Treats Hair Spa 650g Cocomilk','PC','BC',0,3150,1,'HOT'),(222,'10078332','Hair Treats Hair Spa 650g Virgin Coco','PC','BC',0,3150,1,'HOT'),(223,'10078334','Hair Treats Hair Spa 250g Lavander','PC','BC',0,867,1,'HOT'),(224,'10078335','Hair Treats Hair Spa 250g Cocomilk','PC','BC',0,867,1,'HOT'),(225,'10078336','Hair Treats Hair Spa 250g Virgin Coco','PC','BC',0,867,1,'HOT'),(226,'10080925','Labworks Hair Gum 100g','PC','BC',0,534,1,'HOT'),(227,'10080926','Labworks Hair Wax 100g','PC','BC',0,534,1,'HOT'),(228,'10080927','Labworks Hair Clay 100g','PC','BC',0,534,1,'HOT'),(229,'10080933','B Treats Deo Body Spray Aqua Mist 120ml','PC','BC',0,482,1,'HYDRO'),(230,'10080934','B Treats Deo Body Spray Peach Bloom 120ml','PC','BC',0,482,1,'HYDRO'),(231,'10083987','Hair Treats Hair Coat Ginseng 55ml','PC','BC',0,1832,1,'HYDRO'),(232,'10083988','Hair Treats Hair Coat Virgin Coco 55ml','PC','BC',0,1832,1,'HYDRO'),(233,'10083989','Hair Treats Hair Coat Royal Jelly 55ml','PC','BC',0,1832,1,'HYDRO'),(234,'10083990','Hair Treats Hair Coat Macadamia 55ml','PC','BC',0,1832,1,'HYDRO'),(235,'10084075','Hair Treats Hair Spa 250g Macademia','PC','BC',0,867,1,'HOT'),(236,'10084076','Hair Treats Hair Spa 650g Macademia','PC','BC',0,2100,1,'HOT'),(237,'10084077','Hair Treats Hair Spa 250g Aloe Vera','PC','BC',0,867,1,'HOT'),(238,'10084078','Hair Treats Hair Spa 650g Aloe Vera','PC','BC',0,2100,1,'HOT'),(239,'10084081','Hair Treat Hair Shine Pink 30ml','PC','BC',0,3088,1,'HYDRO'),(240,'10084082','Hair Treat Hair Shine Green 30ml','PC','BC',0,1544,1,'HYDRO'),(241,'10084083','Hair Treat Hair Shine Lavander 30ml','PC','BC',0,3088,1,'HYDRO'),(242,'10084084','Hair Treat Hair Shine Brown 30ml','PC','BC',0,1544,1,'HYDRO'),(243,'10084085','Hair Treat Hair Shine Yellow 30ml','PC','BC',0,1544,1,'HYDRO'),(244,'10085889','Glamworks Hair Luxe Color Treated 650g','PC','BC',0,350,1,'HOT'),(245,'10085890','Glamworks Hair Luxe Damaged Hair 650g','PC','BC',0,350,1,'HOT'),(246,'10086258','Glamworks Hair Luxe Intensive Hair 650g','PC','BC',0,350,1,'HOT'),(247,'10086259','Glamworks Hair Luxe Hair Fall 650g','PC','BC',0,350,1,'HOT'),(248,'10086826','Glamworks Body Wash Sweet Apple 1L','PC','BC',0,1086,1,'WASHES'),(249,'10086827','Glamworks Body Wash Cherry Blossom 1L','PC','BC',0,1086,1,'WASHES'),(250,'10086830','Glamworks Body Wash Papaya Milk 1L','PC','BC',0,1086,1,'WASHES'),(251,'10086931','Glamworks Hair Polish Color Treated Hair 100g','PC','BC',0,767,1,'HOT'),(252,'10086932','Glamworks Hair Polish Damaged  100g','PC','BC',0,767,1,'HOT'),(253,'10086938','Glamworks Lip N Cheek Strawberry Red 10g','PC','BC',0,600,1,'HYDRO'),(254,'10086939','Glamworks Lip N Cheek Peach Pink 10g','PC','BC',0,600,1,'HYDRO'),(255,'10086959','Milk Essence Baby Milk Bsoap Powder 850ml','PC','BC',0,436,1,'WASHES'),(256,'10086960','Milk Essence Baby Milk Lotion Powder 850ml','PC','BC',0,580,1,'HOT'),(257,'10087016','Milk Essence Baby Milk Bsoap White 120ml','PC','BC',0,766,1,'WASHES'),(258,'10087017','Milk Essence Baby Milk Bsoap Pink 120ml','PC','BC',0,766,1,'WASHES'),(259,'10087018','Milk Essence Baby Milk Bsoap Violet 120ml','PC','BC',0,383,1,'WASHES'),(260,'10087019','Milk Essence Baby Milk Blotion White 120ml','PC','BC',0,766,1,'HOT'),(261,'10087020','Milk Essence Baby Milk Blotion Pink 120ml','PC','BC',0,766,1,'HOT'),(262,'10087021','Milk Essence Baby Milk Blotion Violet 120ml','PC','BC',0,383,1,'HOT'),(263,'10090327','Watsons Body Scrub Cucumber Melon 250ml','PC','BC',0,891,1,'HOT'),(264,'10090328','Watsons Body Scrub Grapefruit Peach 250ml','PC','BC',0,891,1,'HOT'),(265,'10090329','Watsons Body Scrub Strwbrry Citrus 250ml','PC','BC',0,891,1,'HOT'),(266,'10090330','Watsons Body Scrub Peach Mango 250ml','PC','BC',0,891,1,'HOT'),(267,'10090581','B Treats Hand Soap Sweet Pea 750ml','PC','BC',0,2270,1,'WASHES'),(268,'10090582','B Treats Hand Soap Green Tea 750ml','PC','BC',0,2270,1,'WASHES'),(269,'10091319','Hair Treats Hair Spa Argan Oil 650g','PC','BC',0,2100,1,'HOT'),(270,'10091320','Hair Treats Hair Spa Tsubaki 650g','PC','BC',0,2100,1,'HOT'),(271,'10091321','Hair Treats Hair Spa Tsubaki 250g','PC','BC',0,867,1,'HOT'),(272,'10091322','Hair Treats Hair Spa Argan Oil 250g','PC','BC',0,867,1,'HOT'),(273,'10092697','B Treats Hand Soap Wild Orchid 750ml','PC','BC',0,2270,1,'WASHES'),(274,'10092698','B Treats Hand Soap Cranberry 750ml','PC','BC',0,2270,1,'WASHES'),(275,'10093642','Glamworks Body Wash Sweet Pea 1L','PC','BC',0,534,1,'WASHES'),(276,'10093643','Beyond Care Blotion Moonlight Breeze 500ml','PC','BC',0,480,1,'HOT'),(277,'10093644','Beyond Care Blotion Fresh Bloom 500ml','PC','BC',0,480,1,'HOT'),(278,'10093645','Beyond Care Blotion Carribean Escape 500ml','PC','BC',0,480,1,'HOT'),(279,'10095307','Beyond Care Bwash Moonlight Breeze 500ml','PC','BC',0,240,1,'WASHES'),(280,'10095308','Beyond Care Bwash Fresh Bloom 500ml','PC','BC',0,240,1,'WASHES'),(281,'10095309','Beyond Care Bwash Carribean Escape 500ml','PC','BC',0,240,1,'WASHES'),(282,'10095437','Watsons B Lotion Whitening  500ml','PC','BC',0,0,1,'HOT'),(283,'10095438','Watsons B Lotion Deep Moist  500ml','PC','BC',0,0,1,'HOT'),(284,'10095439','Watsons B Lotion Refreshing 500ml','PC','BC',0,0,1,'HOT'),(285,'10097229','Hair Treats Megadose Dry and Damaged 2pc','PC','BC',0,1000,1,'HOT'),(286,'10097230','Hair Treats Megadose Intensive 2pc','PC','BC',0,500,1,'HOT'),(287,'10097231','Hair Treats Megadose Color Treated 2pc','PC','BC',0,1000,1,'HOT'),(288,'10098147','Watsons B Lotion Whitening  750ml','PC','BC',0,0,1,'HOT'),(289,'10098148','Watsons B Lotion Deep Moist  750ml','PC','BC',0,0,1,'HOT'),(290,'10098149','Watsons B Lotion Refreshing 750ml','PC','BC',0,0,1,'HOT'),(291,'10098597','B Treats Baby Cologne Happy Mist 125ml','PC','BC',0,484,1,'HYDRO'),(292,'10098598','B Treats Baby Cologne Soft Touch 125ml','PC','BC',0,484,1,'HYDRO'),(293,'10098599','B Treats Baby Cologne Sweet Smile 125ml','PC','BC',0,484,1,'HYDRO'),(294,'10098600','B Treats Baby Cologne Pure Heaven 125ml','PC','BC',0,484,1,'HYDRO'),(295,'10098601','B Treats Baby Cologne Bubbles 125ml','PC','BC',0,484,1,'HYDRO'),(296,'10098602','B Treats Deo Spray  White Musk 100ml','PC','BC',0,482,1,'HYDRO'),(297,'10098603','B Treats Deo Spray So Lovely 100ml','PC','BC',0,482,1,'HYDRO'),(298,'10098604','B Treats Deo Spray Sweet Melody 100ml','PC','BC',0,482,1,'HYDRO'),(299,'10099088','B TREATS Body Wash Acai 850ml','PC','BC',0,218,1,'WASHES'),(300,'10099089','B TREATS Body Wash Mangosteen 850ml','PC','BC',0,218,1,'WASHES'),(301,'10099090','B TREATS Body Lotion Acai 850ml','PC','BC',0,290,1,'HOT'),(302,'10099091','B TREATS Body Lotion Mangosteen 850ml','PC','BC',0,290,1,'HOT'),(303,'10099092','B TREATS Whtng Lotion Flawless White 1050ml','PC','BC',0,458,1,'HOT'),(304,'10099093','B TREATS Whtng Lotion Rosy White 1050ml','PC','BC',0,458,1,'HOT'),(305,'10099094','B TREATS Whtng Lotion Radiant White 1050ml','PC','BC',0,458,1,'HOT'),(306,'10099095','B TREATS Whtng Lotion Visibly White 1050ml','PC','BC',0,458,1,'HOT'),(307,'10099989','B TREATS Hand Soap Breezy Days 450ml','PC','BC',0,240,1,'WASHES'),(308,'10099990','B TREATS Hand Soap Satin Ribbons 450ml','PC','BC',0,240,1,'WASHES'),(309,'10099991','B TREATS Hand Soap Love Dazzled 450ml','PC','BC',0,240,1,'WASHES'),(310,'10099992','B TREATS Hand Soap Burst Of Sunshine 450ml','PC','BC',0,240,1,'WASHES'),(311,'10099994','Hair Treats 3Min Wonder Milk Protein 250g','PC','BC',0,10000,1,'HOT'),(312,'10099995','Hair Treats 3Min Wonder Moroccan Oil 250g','PC','BC',0,10000,1,'HOT'),(313,'10099996','Hair Treats 3Min Wonder Keratin Overload 250g','PC','BC',0,1000,1,'HOT'),(314,'10099997','Hair Treats 3Min Wonder Aloe Infusion 250g','PC','BC',0,10000,1,'HOT'),(315,'10101074','Beyond Care Anti Oxidant 400ml','PC','BC',0,120,1,'WASHES'),(316,'10101075','Beyond Care Soothing 400ml','PC','BC',0,120,1,'WASHES'),(317,'10101076','Beyond Care Intense Moisturizing 400ml','PC','BC',0,120,1,'WASHES'),(318,'10101077','Beyond Care Lightening 400ml','PC','BC',0,120,1,'WASHES'),(319,'10101331','Hair Treats Hairspa Honey Milk 650g','PC','BC',0,1352,1,'HOT'),(320,'10101332','Hair Treats Hairspa Keratin Vit E 650g','PC','BC',0,2028,1,'HOT'),(321,'10101333','B TREATS Hand Soap  Fresh Clean 750ml','PC','BC',0,2270,1,'WASHES'),(322,'10101543','B TREATS Body Oil Lavander 230ml','PC','BC',0,480,1,'HYDRO'),(323,'10101713','Labworks Deo Spray Burgundy Mist 120ml','PC','BC',0,482,1,'HYDRO'),(324,'10101714','Labworks Deo Spray Saffron Rock 120ml','PC','BC',0,482,1,'HYDRO'),(325,'10101715','Labworks Deo Spray Navy Suede 120ml','PC','BC',0,482,1,'HYDRO'),(326,'10101716','Labworks Deo Spray Cool Blaze 120ml','PC','BC',0,482,1,'HYDRO'),(327,'10101717','Labworks Deo Spray Aqua Tease 120ml','PC','BC',0,482,1,'HYDRO'),(328,'10101718','Labworks Deo Spray Indigo Spritz 120ml','PC','BC',-11,482,1,'HYDRO'),(329,'50006581','B Treats Deo Body Spray Thousand Wishes 120ml','PC','BC',0,482,1,'HYDRO'),(330,'50006582','B Treats Deo Body Spray Magenta Magic 120ml','PC','BC',0,482,1,'HYDRO'),(331,'LAND0010000966','Lander Hand Soap Green Tea 1000ml','PC','BC',0,0,1,'WASHES'),(332,'LAND0010000965','Lander Hand Soap Spring Fresh 1000ml','PC','BC',0,0,1,'WASHES'),(333,'10038957','B TREATS Body Wash Papaya Orange 850ml','CS','SG',9,0,12,'WASHES'),(334,'10038958','B TREATS Body Wash Papaya Green 850ml','CS','SG',14,0,12,'WASHES'),(335,'10038959','B TREATS Body Wash Virgin Coco 850ml','CS','SG',14,0,12,'WASHES'),(336,'10038960','B TREATS Body Wash Calamansi 850ml','CS','SG',2,0,12,'WASHES'),(337,'10038961','B Treats Body Lotion Papaya Orange 850ml','CS','SG',4,0,12,'HOT'),(338,'10038962','B Treats Body Lotion Papaya Green 850ml','CS','SG',11,0,12,'HOT'),(339,'10038963','B Treats Body Lotion Virgin Coco 850ml','CS','SG',9,0,12,'HOT'),(340,'10038964','B Treats Body Lotion Calamansi 850ml','CS','SG',24,0,12,'HOT'),(341,'10039150','Milk Essence Baby Milk Bsoap Lavander 850ml','CS','SG',3,0,12,'WASHES'),(342,'10039151','Milk Essence Baby Milk Bsoap Sweet Pea 850ml','CS','SG',0,0,12,'WASHES'),(343,'10039152','Milk Essence Baby Milk Lotion Lavander 850ml','CS','SG',5,0,12,'HOT'),(344,'10039153','Milk Essence Baby Milk Lotion Sweet Pea 850ml','CS','SG',4,0,12,'HOT'),(345,'10046036','B Treats WaterMelon Hand Soap 750ml','CS','SG',23,0,12,'WASHES'),(346,'10046038','B Treats Melon Hand Soap 750ml','CS','SG',3,0,12,'WASHES'),(347,'10046039','B Treats Citrus Hand Soap 750ml','CS','SG',11,0,12,'WASHES'),(348,'10046040','B Treats Magnolia Hand Soap 750ml','CS','SG',18,0,12,'WASHES'),(349,'10050491','B-Treats Body Scrub Watermelon 250g x 2s','CS','SG',2,0,12,'HOT'),(350,'10050492','B-Treats Body Scrub Melon 250g x 2s','CS','SG',26,0,12,'HOT'),(351,'10050503','F Treats Ft Scrub Citrus 250g x 2s','CS','SG',2,0,12,'HOT'),(352,'10050504','F Treats Ft Scrub Magnolia 250g x 2s','CS','SG',16,0,12,'HOT'),(353,'10053293','F Treats Ft Spray Citrus 118ml','CS','SG',5,0,48,'HYDRO'),(354,'10053294','F Treats Ft Spray Magnolia 118ml','CS','SG',9,0,48,'HYDRO'),(355,'10053295','F Treats Ft Powder Citrus 100g','CS','SG',3,0,48,'HYDRO'),(356,'10053296','F Treats Ft Powder Magnolia 100g','CS','SG',2,0,48,'HYDRO'),(357,'10053297','F Treats Ft Soak Citrus 250ml','CS','SG',3,0,48,'WASHES'),(358,'10053298','F Treats Ft Soak Magnolia 250ml','CS','SG',11,0,48,'WASHES'),(359,'10059530','B TREATS Body Wash Lavander 510ml','CS','SG',4,0,24,'WASHES'),(360,'10059532','B TREATS Body Wash Evening Primrose 510ml','CS','SG',7,0,24,'WASHES'),(361,'10059535','B TREATS Body Wash Grapeseed 510ml','CS','SG',6,0,24,'WASHES'),(362,'10061191','B Treats Body Spray Ocean Blue 100ml','CS','SG',1,0,48,'HYDRO'),(363,'10061193','B Treats Body Spray Refreshing Green 100ml','CS','SG',2,0,48,'HYDRO'),(364,'10061195','B Treats Body Spray Cotton Blossom 100ml','CS','SG',0,0,48,'HYDRO'),(365,'10061196','B Treats Body Spray Purple Rain 100ml','CS','SG',0,0,48,'HYDRO'),(366,'10061377','B Treats Body Lotion Lavander 510ml','CS','SG',16,0,24,'HOT'),(367,'10061380','B Treats Body Lotion Evening Primrose 510ml','CS','SG',13,0,24,'HOT'),(368,'10061383','B-Treats Body Scrub Lavander Moist 250g x 2s','CS','SG',11,0,12,'HOT'),(369,'10061385','F Treats Ft Scrub Evening Primrose 250gx2s','CS','SG',3,0,12,'HOT'),(370,'10061386','F Treats Ft Scrub Grapeseed 250gx2s','CS','SG',20,0,12,'HOT'),(371,'10061391','F Treats Ft Spray Evening Primrose 118ml','CS','SG',13,0,48,'HYDRO'),(372,'10061394','F Treats Ft Spray Grapeseed 118ml','CS','SG',9,0,48,'HYDRO'),(373,'10061396','F Treats Ft Soak Evening Primrose 250ml','CS','SG',3,0,48,'WASHES'),(374,'10061398','F Treats Ft Soak Grapeseed 250ml','CS','SG',5,0,48,'WASHES'),(375,'10061399','F Treats Ft Powder Evening Primrose 100g','CS','SG',3,0,48,'HYDRO'),(376,'10061401','F Treats Ft Powder Grapeseed 100g','CS','SG',4,0,48,'HYDRO'),(377,'10064388','Glamworks Hair Serum Anti Frizz 30ml','CS','SG',4,0,96,'HYDRO'),(378,'10064394','Glamworks Hair Serum Heat Defense 30ml','CS','SG',0,0,96,'HYDRO'),(379,'10064396','Glamworks Hair Serum Silky Straight 30ml','CS','SG',6,0,96,'HYDRO'),(380,'10064398','Glamworks Hair Serum Deep Repair 30ml','CS','SG',5,0,96,'HYDRO'),(381,'10066446','B TREATS Hand Sanitizer Watermelon 75ml','CS','SG',0,0,96,'HOT'),(382,'10066447','B TREATS Hand Sanitizer Melon 75ml','CS','SG',3,0,96,'HOT'),(383,'10066448','B TREATS Hand Sanitizer Citrus 75ml','CS','SG',5,0,96,'HOT'),(384,'10066450','B TREATS Hand Sanitizer Magnolia 75ml','CS','SG',2,0,96,'HOT'),(385,'10078329','Hair Treats Hair Shine 65ml','CS','SG',-5,0,96,'HYDRO'),(386,'10078330','Hair Treats Hair Spa 650g Lavander','CS','SG',82,0,12,'HOT'),(387,'10078331','Hair Treats Hair Spa 650g Cocomilk','CS','SG',76,0,12,'HOT'),(388,'10078332','Hair Treats Hair Spa 650g Virgin Coco','CS','SG',64,0,12,'HOT'),(389,'10078334','Hair Treats Hair Spa 250g Lavander','CS','SG',14,0,48,'HOT'),(390,'10078335','Hair Treats Hair Spa 250g Cocomilk','CS','SG',5,0,48,'HOT'),(391,'10078336','Hair Treats Hair Spa 250g Virgin Coco','CS','SG',12,0,48,'HOT'),(392,'10080925','Labworks Hair Gum 100g','CS','SG',5,0,48,'HOT'),(393,'10080926','Labworks Hair Wax 100g','CS','SG',10,0,48,'HOT'),(394,'10080927','Labworks Hair Clay 100g','CS','SG',1,0,48,'HOT'),(395,'10080933','B Treats Deo Body Spray Aqua Mist 120ml','CS','SG',3,0,24,'HYDRO'),(396,'10080934','B Treats Deo Body Spray Peach Bloom 120ml','CS','SG',3,0,24,'HYDRO'),(397,'10083987','Hair Treats Hair Coat Ginseng 55ml','CS','SG',5,0,96,'HYDRO'),(398,'10083988','Hair Treats Hair Coat Virgin Coco 55ml','CS','SG',2,0,96,'HYDRO'),(399,'10083989','Hair Treats Hair Coat Royal Jelly 55ml','CS','SG',2,0,96,'HYDRO'),(400,'10083990','Hair Treats Hair Coat Macadamia 55ml','CS','SG',0,0,96,'HYDRO'),(401,'10084075','Hair Treats Hair Spa 250g Macademia','CS','SG',16,0,48,'HOT'),(402,'10084076','Hair Treats Hair Spa 650g Macademia','CS','SG',12,0,12,'HOT'),(403,'10084077','Hair Treats Hair Spa 250g Aloe Vera','CS','SG',13,0,48,'HOT'),(404,'10084078','Hair Treats Hair Spa 650g Aloe Vera','CS','SG',20,0,12,'HOT'),(405,'10084081','Hair Treat Hair Shine Pink 30ml','CS','SG',4,0,96,'HYDRO'),(406,'10084082','Hair Treat Hair Shine Green 30ml','CS','SG',7,0,96,'HYDRO'),(407,'10084083','Hair Treat Hair Shine Lavander 30ml','CS','SG',7,0,96,'HYDRO'),(408,'10084084','Hair Treat Hair Shine Brown 30ml','CS','SG',30,0,96,'HYDRO'),(409,'10084085','Hair Treat Hair Shine Yellow 30ml','CS','SG',9,0,96,'HYDRO'),(410,'10085889','Glamworks Hair Luxe Color Treated 650g','CS','SG',18,0,12,'HOT'),(411,'10085890','Glamworks Hair Luxe Damaged Hair 650g','CS','SG',36,0,12,'HOT'),(412,'10086258','Glamworks Hair Luxe Intensive Hair 650g','CS','SG',22,0,12,'HOT'),(413,'10086259','Glamworks Hair Luxe Hair Fall 650g','CS','SG',14,0,12,'HOT'),(414,'10086826','Glamworks Body Wash Sweet Apple 1L','CS','SG',12,0,12,'WASHES'),(415,'10086827','Glamworks Body Wash Cherry Blossom 1L','CS','SG',9,0,12,'WASHES'),(416,'10086830','Glamworks Body Wash Papaya Milk 1L','CS','SG',15,0,12,'WASHES'),(417,'10086931','Glamworks Hair Polish Color Treated Hair 100g','CS','SG',10,0,24,'HOT'),(418,'10086932','Glamworks Hair Polish Damaged  100g','CS','SG',13,0,24,'HOT'),(419,'10086938','Glamworks Lip N Cheek Strawberry Red 10g','CS','SG',105,0,12,'HYDRO'),(420,'10086939','Glamworks Lip N Cheek Peach Pink 10g','CS','SG',62,0,12,'HYDRO'),(421,'10086959','Milk Essence Baby Milk Bsoap Powder 850ml','CS','SG',1,0,12,'WASHES'),(422,'10086960','Milk Essence Baby Milk Lotion Powder 850ml','CS','SG',8,0,12,'HOT'),(423,'10087016','Milk Essence Baby Milk Bsoap White 120ml','CS','SG',7,0,24,'WASHES'),(424,'10087017','Milk Essence Baby Milk Bsoap Pink 120ml','CS','SG',3,0,24,'WASHES'),(425,'10087018','Milk Essence Baby Milk Bsoap Violet 120ml','CS','SG',2,0,24,'WASHES'),(426,'10087019','Milk Essence Baby Milk Blotion White 120ml','CS','SG',5,0,24,'HOT'),(427,'10087020','Milk Essence Baby Milk Blotion Pink 120ml','CS','SG',0,0,24,'HOT'),(428,'10087021','Milk Essence Baby Milk Blotion Violet 120ml','CS','SG',7,0,24,'HOT'),(429,'10090327','Watsons Body Scrub Cucumber Melon 250ml','CS','SG',19,0,24,'HOT'),(430,'10090328','Watsons Body Scrub Grapefruit Peach 250ml','CS','SG',37,0,24,'HOT'),(431,'10090329','Watsons Body Scrub Strwbrry Citrus 250ml','CS','SG',46,0,24,'HOT'),(432,'10090330','Watsons Body Scrub Peach Mango 250ml','CS','SG',61,0,24,'HOT'),(433,'10090581','B Treats Hand Soap Sweet Pea 750ml','CS','SG',23,0,12,'WASHES'),(434,'10090582','B Treats Hand Soap Green Tea 750ml','CS','SG',6,0,12,'WASHES'),(435,'10091319','Hair Treats Hair Spa Argan Oil 650g','CS','SG',27,0,12,'HOT'),(436,'10091320','Hair Treats Hair Spa Tsubaki 650g','CS','SG',10,0,12,'HOT'),(437,'10091321','Hair Treats Hair Spa Tsubaki 250g','CS','SG',6,0,48,'HOT'),(438,'10091322','Hair Treats Hair Spa Argan Oil 250g','CS','SG',15,0,48,'HOT'),(439,'10092697','B Treats Hand Soap Wild Orchid 750ml','CS','SG',10,0,12,'WASHES'),(440,'10092698','B Treats Hand Soap Cranberry 750ml','CS','SG',55,0,12,'WASHES'),(441,'10093642','Glamworks Body Wash Sweet Pea 1L','CS','SG',11,0,12,'WASHES'),(442,'10093643','Beyond Care Blotion Moonlight Breeze 500ml','CS','SG',2,0,24,'HOT'),(443,'10093644','Beyond Care Blotion Fresh Bloom 500ml','CS','SG',1,0,24,'HOT'),(444,'10093645','Beyond Care Blotion Carribean Escape 500ml','CS','SG',1,0,24,'HOT'),(445,'10095307','Beyond Care Bwash Moonlight Breeze 500ml','CS','SG',20,0,12,'WASHES'),(446,'10095308','Beyond Care Bwash Fresh Bloom 500ml','CS','SG',24,0,12,'WASHES'),(447,'10095309','Beyond Care Bwash Carribean Escape 500ml','CS','SG',24,0,12,'WASHES'),(448,'10095437','Watsons B Lotion Whitening  500ml','CS','SG',0,0,24,'HOT'),(449,'10095438','Watsons B Lotion Deep Moist  500ml','CS','SG',0,0,24,'HOT'),(450,'10095439','Watsons B Lotion Refreshing 500ml','CS','SG',0,0,24,'HOT'),(451,'10097229','Hair Treats Megadose Dry and Damaged 2pc','CS','SG',10,0,12,'HOT'),(452,'10097230','Hair Treats Megadose Intensive 2pc','CS','SG',31,0,12,'HOT'),(453,'10097231','Hair Treats Megadose Color Treated 2pc','CS','SG',27,0,12,'HOT'),(454,'10098147','Watsons B Lotion Whitening  750ml','CS','SG',0,0,12,'HOT'),(455,'10098148','Watsons B Lotion Deep Moist  750ml','CS','SG',0,0,12,'HOT'),(456,'10098149','Watsons B Lotion Refreshing 750ml','CS','SG',0,0,12,'HOT'),(457,'10098597','B Treats Baby Cologne Happy Mist 125ml','CS','SG',2,0,24,'HYDRO'),(458,'10098598','B Treats Baby Cologne Soft Touch 125ml','CS','SG',5,0,24,'HYDRO'),(459,'10098599','B Treats Baby Cologne Sweet Smile 125ml','CS','SG',3,0,24,'HYDRO'),(460,'10098600','B Treats Baby Cologne Pure Heaven 125ml','CS','SG',4,0,24,'HYDRO'),(461,'10098601','B Treats Baby Cologne Bubbles 125ml','CS','SG',8,0,24,'HYDRO'),(462,'10098602','B Treats Deo Spray  White Musk 100ml','CS','SG',1,0,24,'HYDRO'),(463,'10098603','B Treats Deo Spray So Lovely 100ml','CS','SG',2,0,24,'HYDRO'),(464,'10098604','B Treats Deo Spray Sweet Melody 100ml','CS','SG',2,0,24,'HYDRO'),(465,'10099088','B TREATS Body Wash Acai 850ml','CS','SG',4,0,12,'WASHES'),(466,'10099089','B TREATS Body Wash Mangosteen 850ml','CS','SG',16,0,12,'WASHES'),(467,'10099090','B TREATS Body Lotion Acai 850ml','CS','SG',3,0,12,'HOT'),(468,'10099091','B TREATS Body Lotion Mangosteen 850ml','CS','SG',5,0,12,'HOT'),(469,'10099092','B TREATS Whtng Lotion Flawless White 1050ml','CS','SG',5,0,12,'HOT'),(470,'10099093','B TREATS Whtng Lotion Rosy White 1050ml','CS','SG',14,0,12,'HOT'),(471,'10099094','B TREATS Whtng Lotion Radiant White 1050ml','CS','SG',9,0,12,'HOT'),(472,'10099095','B TREATS Whtng Lotion Visibly White 1050ml','CS','SG',14,0,12,'HOT'),(473,'10099989','B TREATS Hand Soap Breezy Days 450ml','CS','SG',11,0,12,'WASHES'),(474,'10099990','B TREATS Hand Soap Satin Ribbons 450ml','CS','SG',8,0,12,'WASHES'),(475,'10099991','B TREATS Hand Soap Love Dazzled 450ml','CS','SG',8,0,12,'WASHES'),(476,'10099992','B TREATS Hand Soap Burst Of Sunshine 450ml','CS','SG',7,0,12,'WASHES'),(477,'10099994','Hair Treats 3Min Wonder Milk Protein 250g','CS','SG',10,0,12,'HOT'),(478,'10099995','Hair Treats 3Min Wonder Moroccan Oil 250g','CS','SG',2,0,12,'HOT'),(479,'10099996','Hair Treats 3Min Wonder Keratin Overload 250g','CS','SG',17,0,12,'HOT'),(480,'10099997','Hair Treats 3Min Wonder Aloe Infusion 250g','CS','SG',7,0,12,'HOT'),(481,'10101074','Beyond Care Anti Oxidant 400ml','CS','SG',0,0,12,'WASHES'),(482,'10101075','Beyond Care Soothing 400ml','CS','SG',2,0,12,'WASHES'),(483,'10101076','Beyond Care Intense Moisturizing 400ml','CS','SG',5,0,12,'WASHES'),(484,'10101077','Beyond Care Lightening 400ml','CS','SG',4,0,12,'WASHES'),(485,'10101331','Hair Treats Hairspa Honey Milk 650g','CS','SG',36,0,12,'HOT'),(486,'10101332','Hair Treats Hairspa Keratin Vit E 650g','CS','SG',35,0,12,'HOT'),(487,'10101333','B TREATS Hand Soap  Fresh Clean 750ml','CS','SG',8,0,12,'WASHES'),(488,'10101543','B TREATS Body Oil Lavander 230ml','CS','SG',15,0,24,'HYDRO'),(489,'10101713','Labworks Deo Spray Burgundy Mist 120ml','CS','SG',2,0,48,'HYDRO'),(490,'10101714','Labworks Deo Spray Saffron Rock 120ml','CS','SG',3,0,48,'HYDRO'),(491,'10101715','Labworks Deo Spray Navy Suede 120ml','CS','SG',0,0,48,'HYDRO'),(492,'10101716','Labworks Deo Spray Cool Blaze 120ml','CS','SG',0,0,48,'HYDRO'),(493,'10101717','Labworks Deo Spray Aqua Tease 120ml','CS','SG',2,0,48,'HYDRO'),(494,'10101718','Labworks Deo Spray Indigo Spritz 120ml','CS','SG',2,0,48,'HYDRO'),(495,'50006581','B Treats Deo Body Spray Thousand Wishes 120ml','CS','SG',1,0,24,'HYDRO'),(496,'50006582','B Treats Deo Body Spray Magenta Magic 120ml','CS','SG',0,0,24,'HYDRO'),(497,'LAND0010000966','Lander Hand Soap Green Tea 1000ml','CS','SG',484,0,12,'WASHES'),(498,'LAND0010000965','Lander Hand Soap Spring Fresh 1000ml','CS','SG',81,0,12,'WASHES'),(499,'10038957','B TREATS Body Wash Papaya Orange 850ml','PC','SG',203,0,1,'WASHES'),(500,'10038958','B TREATS Body Wash Papaya Green 850ml','PC','SG',200,0,1,'WASHES'),(501,'10038959','B TREATS Body Wash Virgin Coco 850ml','PC','SG',239,0,1,'WASHES'),(502,'10038960','B TREATS Body Wash Calamansi 850ml','PC','SG',102,0,1,'WASHES'),(503,'10038961','B Treats Body Lotion Papaya Orange 850ml','PC','SG',544,0,1,'HOT'),(504,'10038962','B Treats Body Lotion Papaya Green 850ml','PC','SG',166,0,1,'HOT'),(505,'10038963','B Treats Body Lotion Virgin Coco 850ml','PC','SG',149,0,1,'HOT'),(506,'10038964','B Treats Body Lotion Calamansi 850ml','PC','SG',615,0,1,'HOT'),(507,'10039150','Milk Essence Baby Milk Bsoap Lavander 850ml','PC','SG',194,0,1,'WASHES'),(508,'10039151','Milk Essence Baby Milk Bsoap Sweet Pea 850ml','PC','SG',265,0,1,'WASHES'),(509,'10039152','Milk Essence Baby Milk Lotion Lavander 850ml','PC','SG',145,0,1,'HOT'),(510,'10039153','Milk Essence Baby Milk Lotion Sweet Pea 850ml','PC','SG',244,0,1,'HOT'),(511,'10046036','B Treats WaterMelon Hand Soap 750ml','PC','SG',209,0,1,'WASHES'),(512,'10046038','B Treats Melon Hand Soap 750ml','PC','SG',209,0,1,'WASHES'),(513,'10046039','B Treats Citrus Hand Soap 750ml','PC','SG',152,0,1,'WASHES'),(514,'10046040','B Treats Magnolia Hand Soap 750ml','PC','SG',38,0,1,'WASHES'),(515,'10050491','B-Treats Body Scrub Watermelon 250g x 2s','PC','SG',175,0,1,'HOT'),(516,'10050492','B-Treats Body Scrub Melon 250g x 2s','PC','SG',332,0,1,'HOT'),(517,'10050503','F Treats Ft Scrub Citrus 250g x 2s','PC','SG',349,0,1,'HOT'),(518,'10050504','F Treats Ft Scrub Magnolia 250g x 2s','PC','SG',766,0,1,'HOT'),(519,'10053293','F Treats Ft Spray Citrus 118ml','PC','SG',101,0,1,'HYDRO'),(520,'10053294','F Treats Ft Spray Magnolia 118ml','PC','SG',805,0,1,'HYDRO'),(521,'10053295','F Treats Ft Powder Citrus 100g','PC','SG',222,0,1,'HYDRO'),(522,'10053296','F Treats Ft Powder Magnolia 100g','PC','SG',170,0,1,'HYDRO'),(523,'10053297','F Treats Ft Soak Citrus 250ml','PC','SG',454,0,1,'WASHES'),(524,'10053298','F Treats Ft Soak Magnolia 250ml','PC','SG',24,0,1,'WASHES'),(525,'10059530','B TREATS Body Wash Lavander 510ml','PC','SG',265,0,1,'WASHES'),(526,'10059532','B TREATS Body Wash Evening Primrose 510ml','PC','SG',303,0,1,'WASHES'),(527,'10059535','B TREATS Body Wash Grapeseed 510ml','PC','SG',185,0,1,'WASHES'),(528,'10061191','B Treats Body Spray Ocean Blue 100ml','PC','SG',222,0,1,'HYDRO'),(529,'10061193','B Treats Body Spray Refreshing Green 100ml','PC','SG',105,0,1,'HYDRO'),(530,'10061195','B Treats Body Spray Cotton Blossom 100ml','PC','SG',245,0,1,'HYDRO'),(531,'10061196','B Treats Body Spray Purple Rain 100ml','PC','SG',192,0,1,'HYDRO'),(532,'10061377','B Treats Body Lotion Lavander 510ml','PC','SG',414,0,1,'HOT'),(533,'10061380','B Treats Body Lotion Evening Primrose 510ml','PC','SG',15,0,1,'HOT'),(534,'10061383','B-Treats Body Scrub Lavander Moist 250g x 2s','PC','SG',461,0,1,'HOT'),(535,'10061385','F Treats Ft Scrub Evening Primrose 250gx2s','PC','SG',82,0,1,'HOT'),(536,'10061386','F Treats Ft Scrub Grapeseed 250gx2s','PC','SG',321,0,1,'HOT'),(537,'10061391','F Treats Ft Spray Evening Primrose 118ml','PC','SG',916,0,1,'HYDRO'),(538,'10061394','F Treats Ft Spray Grapeseed 118ml','PC','SG',180,0,1,'HYDRO'),(539,'10061396','F Treats Ft Soak Evening Primrose 250ml','PC','SG',399,0,1,'WASHES'),(540,'10061398','F Treats Ft Soak Grapeseed 250ml','PC','SG',362,0,1,'WASHES'),(541,'10061399','F Treats Ft Powder Evening Primrose 100g','PC','SG',109,0,1,'HYDRO'),(542,'10061401','F Treats Ft Powder Grapeseed 100g','PC','SG',184,0,1,'HYDRO'),(543,'10064388','Glamworks Hair Serum Anti Frizz 30ml','PC','SG',118,0,1,'HYDRO'),(544,'10064394','Glamworks Hair Serum Heat Defense 30ml','PC','SG',385,0,1,'HYDRO'),(545,'10064396','Glamworks Hair Serum Silky Straight 30ml','PC','SG',290,0,1,'HYDRO'),(546,'10064398','Glamworks Hair Serum Deep Repair 30ml','PC','SG',3,0,1,'HYDRO'),(547,'10066446','B TREATS Hand Sanitizer Watermelon 75ml','PC','SG',519,0,1,'HOT'),(548,'10066447','B TREATS Hand Sanitizer Melon 75ml','PC','SG',275,0,1,'HOT'),(549,'10066448','B TREATS Hand Sanitizer Citrus 75ml','PC','SG',419,0,1,'HOT'),(550,'10066450','B TREATS Hand Sanitizer Magnolia 75ml','PC','SG',333,0,1,'HOT'),(551,'10078329','Hair Treats Hair Shine 65ml','PC','SG',83,0,1,'HYDRO'),(552,'10078330','Hair Treats Hair Spa 650g Lavander','PC','SG',121,0,1,'HOT'),(553,'10078331','Hair Treats Hair Spa 650g Cocomilk','PC','SG',145,0,1,'HOT'),(554,'10078332','Hair Treats Hair Spa 650g Virgin Coco','PC','SG',112,0,1,'HOT'),(555,'10078334','Hair Treats Hair Spa 250g Lavander','PC','SG',1946,0,1,'HOT'),(556,'10078335','Hair Treats Hair Spa 250g Cocomilk','PC','SG',2085,0,1,'HOT'),(557,'10078336','Hair Treats Hair Spa 250g Virgin Coco','PC','SG',2017,0,1,'HOT'),(558,'10080925','Labworks Hair Gum 100g','PC','SG',492,0,1,'HOT'),(559,'10080926','Labworks Hair Wax 100g','PC','SG',334,0,1,'HOT'),(560,'10080927','Labworks Hair Clay 100g','PC','SG',441,0,1,'HOT'),(561,'10080933','B Treats Deo Body Spray Aqua Mist 120ml','PC','SG',67,0,1,'HYDRO'),(562,'10080934','B Treats Deo Body Spray Peach Bloom 120ml','PC','SG',79,0,1,'HYDRO'),(563,'10083987','Hair Treats Hair Coat Ginseng 55ml','PC','SG',25,0,1,'HYDRO'),(564,'10083988','Hair Treats Hair Coat Virgin Coco 55ml','PC','SG',96,0,1,'HYDRO'),(565,'10083989','Hair Treats Hair Coat Royal Jelly 55ml','PC','SG',785,0,1,'HYDRO'),(566,'10083990','Hair Treats Hair Coat Macadamia 55ml','PC','SG',10,0,1,'HYDRO'),(567,'10084075','Hair Treats Hair Spa 250g Macademia','PC','SG',964,0,1,'HOT'),(568,'10084076','Hair Treats Hair Spa 650g Macademia','PC','SG',91,0,1,'HOT'),(569,'10084077','Hair Treats Hair Spa 250g Aloe Vera','PC','SG',1099,0,1,'HOT'),(570,'10084078','Hair Treats Hair Spa 650g Aloe Vera','PC','SG',8,0,1,'HOT'),(571,'10084081','Hair Treat Hair Shine Pink 30ml','PC','SG',3236,0,1,'HYDRO'),(572,'10084082','Hair Treat Hair Shine Green 30ml','PC','SG',292,0,1,'HYDRO'),(573,'10084083','Hair Treat Hair Shine Lavander 30ml','PC','SG',1589,0,1,'HYDRO'),(574,'10084084','Hair Treat Hair Shine Brown 30ml','PC','SG',435,0,1,'HYDRO'),(575,'10084085','Hair Treat Hair Shine Yellow 30ml','PC','SG',388,0,1,'HYDRO'),(576,'10085889','Glamworks Hair Luxe Color Treated 650g','PC','SG',236,0,1,'HOT'),(577,'10085890','Glamworks Hair Luxe Damaged Hair 650g','PC','SG',331,0,1,'HOT'),(578,'10086258','Glamworks Hair Luxe Intensive Hair 650g','PC','SG',77,0,1,'HOT'),(579,'10086259','Glamworks Hair Luxe Hair Fall 650g','PC','SG',174,0,1,'HOT'),(580,'10086826','Glamworks Body Wash Sweet Apple 1L','PC','SG',373,0,1,'WASHES'),(581,'10086827','Glamworks Body Wash Cherry Blossom 1L','PC','SG',16,0,1,'WASHES'),(582,'10086830','Glamworks Body Wash Papaya Milk 1L','PC','SG',423,0,1,'WASHES'),(583,'10086931','Glamworks Hair Polish Color Treated Hair 100g','PC','SG',497,0,1,'HOT'),(584,'10086932','Glamworks Hair Polish Damaged  100g','PC','SG',492,0,1,'HOT'),(585,'10086938','Glamworks Lip N Cheek Strawberry Red 10g','PC','SG',1466,0,1,'HYDRO'),(586,'10086939','Glamworks Lip N Cheek Peach Pink 10g','PC','SG',948,0,1,'HYDRO'),(587,'10086959','Milk Essence Baby Milk Bsoap Powder 850ml','PC','SG',85,0,1,'WASHES'),(588,'10086960','Milk Essence Baby Milk Lotion Powder 850ml','PC','SG',279,0,1,'HOT'),(589,'10087016','Milk Essence Baby Milk Bsoap White 120ml','PC','SG',288,0,1,'WASHES'),(590,'10087017','Milk Essence Baby Milk Bsoap Pink 120ml','PC','SG',119,0,1,'WASHES'),(591,'10087018','Milk Essence Baby Milk Bsoap Violet 120ml','PC','SG',327,0,1,'WASHES'),(592,'10087019','Milk Essence Baby Milk Blotion White 120ml','PC','SG',242,0,1,'HOT'),(593,'10087020','Milk Essence Baby Milk Blotion Pink 120ml','PC','SG',102,0,1,'HOT'),(594,'10087021','Milk Essence Baby Milk Blotion Violet 120ml','PC','SG',122,0,1,'HOT'),(595,'10090327','Watsons Body Scrub Cucumber Melon 250ml','PC','SG',136,0,1,'HOT'),(596,'10090328','Watsons Body Scrub Grapefruit Peach 250ml','PC','SG',288,0,1,'HOT'),(597,'10090329','Watsons Body Scrub Strwbrry Citrus 250ml','PC','SG',424,0,1,'HOT'),(598,'10090330','Watsons Body Scrub Peach Mango 250ml','PC','SG',735,0,1,'HOT'),(599,'10090581','B Treats Hand Soap Sweet Pea 750ml','PC','SG',1500,0,1,'WASHES'),(600,'10090582','B Treats Hand Soap Green Tea 750ml','PC','SG',1265,0,1,'WASHES'),(601,'10091319','Hair Treats Hair Spa Argan Oil 650g','PC','SG',50,0,1,'HOT'),(602,'10091320','Hair Treats Hair Spa Tsubaki 650g','PC','SG',17,0,1,'HOT'),(603,'10091321','Hair Treats Hair Spa Tsubaki 250g','PC','SG',47,0,1,'HOT'),(604,'10091322','Hair Treats Hair Spa Argan Oil 250g','PC','SG',1567,0,1,'HOT'),(605,'10092697','B Treats Hand Soap Wild Orchid 750ml','PC','SG',8,0,1,'WASHES'),(606,'10092698','B Treats Hand Soap Cranberry 750ml','PC','SG',528,0,1,'WASHES'),(607,'10093642','Glamworks Body Wash Sweet Pea 1L','PC','SG',562,0,1,'WASHES'),(608,'10093643','Beyond Care Blotion Moonlight Breeze 500ml','PC','SG',97,0,1,'HOT'),(609,'10093644','Beyond Care Blotion Fresh Bloom 500ml','PC','SG',183,0,1,'HOT'),(610,'10093645','Beyond Care Blotion Carribean Escape 500ml','PC','SG',162,0,1,'HOT'),(611,'10095307','Beyond Care Bwash Moonlight Breeze 500ml','PC','SG',36,0,1,'WASHES'),(612,'10095308','Beyond Care Bwash Fresh Bloom 500ml','PC','SG',11,0,1,'WASHES'),(613,'10095309','Beyond Care Bwash Carribean Escape 500ml','PC','SG',52,0,1,'WASHES'),(614,'10095437','Watsons B Lotion Whitening  500ml','PC','SG',0,0,1,'HOT'),(615,'10095438','Watsons B Lotion Deep Moist  500ml','PC','SG',0,0,1,'HOT'),(616,'10095439','Watsons B Lotion Refreshing 500ml','PC','SG',0,0,1,'HOT'),(617,'10097229','Hair Treats Megadose Dry and Damaged 2pc','PC','SG',720,0,1,'HOT'),(618,'10097230','Hair Treats Megadose Intensive 2pc','PC','SG',382,0,1,'HOT'),(619,'10097231','Hair Treats Megadose Color Treated 2pc','PC','SG',523,0,1,'HOT'),(620,'10098147','Watsons B Lotion Whitening  750ml','PC','SG',0,0,1,'HOT'),(621,'10098148','Watsons B Lotion Deep Moist  750ml','PC','SG',0,0,1,'HOT'),(622,'10098149','Watsons B Lotion Refreshing 750ml','PC','SG',0,0,1,'HOT'),(623,'10098597','B Treats Baby Cologne Happy Mist 125ml','PC','SG',10,0,1,'HYDRO'),(624,'10098598','B Treats Baby Cologne Soft Touch 125ml','PC','SG',91,0,1,'HYDRO'),(625,'10098599','B Treats Baby Cologne Sweet Smile 125ml','PC','SG',330,0,1,'HYDRO'),(626,'10098600','B Treats Baby Cologne Pure Heaven 125ml','PC','SG',84,0,1,'HYDRO'),(627,'10098601','B Treats Baby Cologne Bubbles 125ml','PC','SG',201,0,1,'HYDRO'),(628,'10098602','B Treats Deo Spray  White Musk 100ml','PC','SG',2,0,1,'HYDRO'),(629,'10098603','B Treats Deo Spray So Lovely 100ml','PC','SG',1,0,1,'HYDRO'),(630,'10098604','B Treats Deo Spray Sweet Melody 100ml','PC','SG',0,0,1,'HYDRO'),(631,'10099088','B TREATS Body Wash Acai 850ml','PC','SG',83,0,1,'WASHES'),(632,'10099089','B TREATS Body Wash Mangosteen 850ml','PC','SG',59,0,1,'WASHES'),(633,'10099090','B TREATS Body Lotion Acai 850ml','PC','SG',185,0,1,'HOT'),(634,'10099091','B TREATS Body Lotion Mangosteen 850ml','PC','SG',228,0,1,'HOT'),(635,'10099092','B TREATS Whtng Lotion Flawless White 1050ml','PC','SG',284,0,1,'HOT'),(636,'10099093','B TREATS Whtng Lotion Rosy White 1050ml','PC','SG',0,0,1,'HOT'),(637,'10099094','B TREATS Whtng Lotion Radiant White 1050ml','PC','SG',526,0,1,'HOT'),(638,'10099095','B TREATS Whtng Lotion Visibly White 1050ml','PC','SG',318,0,1,'HOT'),(639,'10099989','B TREATS Hand Soap Breezy Days 450ml','PC','SG',144,0,1,'WASHES'),(640,'10099990','B TREATS Hand Soap Satin Ribbons 450ml','PC','SG',131,0,1,'WASHES'),(641,'10099991','B TREATS Hand Soap Love Dazzled 450ml','PC','SG',123,0,1,'WASHES'),(642,'10099992','B TREATS Hand Soap Burst Of Sunshine 450ml','PC','SG',89,0,1,'WASHES'),(643,'10099994','Hair Treats 3Min Wonder Milk Protein 250g','PC','SG',176,0,1,'HOT'),(644,'10099995','Hair Treats 3Min Wonder Moroccan Oil 250g','PC','SG',120,0,1,'HOT'),(645,'10099996','Hair Treats 3Min Wonder Keratin Overload 250g','PC','SG',498,0,1,'HOT'),(646,'10099997','Hair Treats 3Min Wonder Aloe Infusion 250g','PC','SG',374,0,1,'HOT'),(647,'10101074','Beyond Care Anti Oxidant 400ml','PC','SG',1,0,1,'WASHES'),(648,'10101075','Beyond Care Soothing 400ml','PC','SG',32,0,1,'WASHES'),(649,'10101076','Beyond Care Intense Moisturizing 400ml','PC','SG',69,0,1,'WASHES'),(650,'10101077','Beyond Care Lightening 400ml','PC','SG',59,0,1,'WASHES'),(651,'10101331','Hair Treats Hairspa Honey Milk 650g','PC','SG',60,0,1,'HOT'),(652,'10101332','Hair Treats Hairspa Keratin Vit E 650g','PC','SG',545,0,1,'HOT'),(653,'10101333','B TREATS Hand Soap  Fresh Clean 750ml','PC','SG',202,0,1,'WASHES'),(654,'10101543','B TREATS Body Oil Lavander 230ml','PC','SG',291,0,1,'HYDRO'),(655,'10101713','Labworks Deo Spray Burgundy Mist 120ml','PC','SG',111,0,1,'HYDRO'),(656,'10101714','Labworks Deo Spray Saffron Rock 120ml','PC','SG',77,0,1,'HYDRO'),(657,'10101715','Labworks Deo Spray Navy Suede 120ml','PC','SG',15,0,1,'HYDRO'),(658,'10101716','Labworks Deo Spray Cool Blaze 120ml','PC','SG',8,0,1,'HYDRO'),(659,'10101717','Labworks Deo Spray Aqua Tease 120ml','PC','SG',3,0,1,'HYDRO'),(660,'10101718','Labworks Deo Spray Indigo Spritz 120ml','PC','SG',6,0,1,'HYDRO'),(661,'50006581','B Treats Deo Body Spray Thousand Wishes 120ml','PC','SG',23,0,1,'HYDRO'),(662,'50006582','B Treats Deo Body Spray Magenta Magic 120ml','PC','SG',37,0,1,'HYDRO'),(663,'LAND0010000966','Lander Hand Soap Green Tea 1000ml','PC','SG',0,0,1,'WASHES'),(664,'LAND0010000965','Lander Hand Soap Spring Fresh 1000ml','PC','SG',0,0,1,'WASHES'),(665,'12334','Handsoap','PC','BC',500,40,1,'WASHES');
/*!40000 ALTER TABLE `inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_adjustment_items`
--

DROP TABLE IF EXISTS `inventory_adjustment_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory_adjustment_items` (
  `idia_items` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) NOT NULL,
  `stock_qty` int(11) NOT NULL,
  `mov` enum('DEC','INC') NOT NULL,
  `status` enum('Y','N') NOT NULL,
  `ia_id` int(11) NOT NULL,
  `iastat` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idia_items`),
  KEY `inventory_ia_fk_idx` (`inventory_id`),
  KEY `ia_id_fk_idx` (`ia_id`),
  CONSTRAINT `ia_id_fk` FOREIGN KEY (`ia_id`) REFERENCES `inventory_adjustments` (`idadjustments`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `inventory_ia_fk` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_adjustment_items`
--

LOCK TABLES `inventory_adjustment_items` WRITE;
/*!40000 ALTER TABLE `inventory_adjustment_items` DISABLE KEYS */;
INSERT INTO `inventory_adjustment_items` VALUES (1,3,2,'INC','Y',700000000,'OPEN'),(2,2,2,'INC','Y',700000000,'OPEN'),(3,4,3,'INC','Y',700000000,'OPEN'),(4,3,100,'INC','Y',700000001,'OPEN'),(5,500,33,'DEC','Y',700000001,'OPEN'),(6,1,3,'INC','Y',700000002,'OPEN'),(7,1,3,'DEC','Y',700000002,'OPEN'),(8,1,2,'DEC','Y',700000003,'OPEN');
/*!40000 ALTER TABLE `inventory_adjustment_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_adjustments`
--

DROP TABLE IF EXISTS `inventory_adjustments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory_adjustments` (
  `idadjustments` int(11) NOT NULL AUTO_INCREMENT,
  `iadte` date DEFAULT NULL,
  `refnum` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `pgistat` enum('Y','N') NOT NULL,
  PRIMARY KEY (`idadjustments`)
) ENGINE=InnoDB AUTO_INCREMENT=700000004 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_adjustments`
--

LOCK TABLES `inventory_adjustments` WRITE;
/*!40000 ALTER TABLE `inventory_adjustments` DISABLE KEYS */;
INSERT INTO `inventory_adjustments` VALUES (700000000,'2017-06-07','3465364','to adjust','Y'),(700000001,'2017-06-06','sdfsdg','fgdfgdf','Y'),(700000002,'2017-06-06','dfdfg','dfgdf','Y'),(700000003,'2017-06-07','sdfsd','To adjust','Y');
/*!40000 ALTER TABLE `inventory_adjustments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `inventory_transactions`
--

DROP TABLE IF EXISTS `inventory_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `inventory_transactions` (
  `idinventory_transactions` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `transac_time` datetime NOT NULL,
  `item` int(11) NOT NULL,
  `qty_adj` int(11) NOT NULL,
  `effect` enum('INC','DEC') DEFAULT NULL,
  `doc_type` varchar(100) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `client_name` varchar(200) DEFAULT NULL,
  `client_type` varchar(200) DEFAULT NULL,
  `doc_it` int(11) DEFAULT NULL,
  `ref_doc` varchar(200) DEFAULT NULL,
  `doc_remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idinventory_transactions`),
  KEY `item_fk_idx` (`item`),
  CONSTRAINT `item_fk` FOREIGN KEY (`item`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `inventory_transactions`
--

LOCK TABLES `inventory_transactions` WRITE;
/*!40000 ALTER TABLE `inventory_transactions` DISABLE KEYS */;
INSERT INTO `inventory_transactions` VALUES (1,'sales','2017-06-06 06:54:40',3,2,'INC','INVADJ',0,'','',700000000,'3465364','to adjust'),(2,'sales','2017-06-06 06:54:40',2,2,'INC','INVADJ',0,'','',700000000,'3465364','to adjust'),(3,'sales','2017-06-06 06:54:40',4,3,'INC','INVADJ',0,'','',700000000,'3465364','to adjust'),(4,'sales','2017-06-06 13:58:25',3,2,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000001,'',''),(5,'sales','2017-06-06 14:01:10',3,100,'INC','INVADJ',0,'','',700000001,'sdfsdg','fgdfgdf'),(6,'sales','2017-06-06 14:01:10',500,33,'DEC','INVADJ',0,'','',700000001,'sdfsdg','fgdfgdf'),(7,'sales','2017-06-06 14:02:05',3,2,'DEC','ManualGoodsIssue',0,'fsdgfd','Customer',600000000,'dsfg','dsfg'),(8,'sales','2017-06-06 14:03:07',3,10,'INC','PostGoodsReceipt',1,'SIEGRANZ Chemworks, Corp.','Supplier',400000000,'','sdfsdfsdfsdf'),(9,'sales','2017-06-06 14:25:26',1,3,'INC','INVADJ',0,'','',700000002,'dfdfg','dfgdf'),(10,'sales','2017-06-06 14:25:26',1,3,'DEC','INVADJ',0,'','',700000002,'dfdfg','dfgdf'),(11,'sales','2017-06-07 08:43:26',1,2,'DEC','INVADJ',0,'','',700000003,'sdfsd','To adjust'),(12,'sales','2017-06-07 10:18:38',3,2,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000002,'',''),(13,'sales','2017-06-19 12:01:57',7,3,'INC','PostGoodsReceipt',1,'SIEGRANZ Chemworks, Corp.','Supplier',400000001,'',NULL),(14,'sales','2017-06-24 09:55:35',3,3,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000003,'',''),(15,'sales','2017-06-24 09:55:35',2,2,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000003,'',''),(16,'sales','2017-06-24 15:31:31',328,11,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000004,'',''),(17,'sales','2017-06-24 15:31:31',160,12,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000004,'',''),(18,'sales','2017-06-24 15:31:31',96,2,'DEC','DeliveryReceipt',1,'Watsons Personal Care Stores (Philippines, Inc.)','Customer',200000004,'','');
/*!40000 ALTER TABLE `inventory_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_goods_issue`
--

DROP TABLE IF EXISTS `manual_goods_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_goods_issue` (
  `idmgi` int(11) NOT NULL AUTO_INCREMENT,
  `mgidte` date NOT NULL,
  `custnme` varchar(100) NOT NULL,
  `contnme` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `renum` varchar(200) NOT NULL,
  `attention` varchar(100) NOT NULL,
  `cby` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idmgi`),
  KEY `cby_mgi_fk_idx` (`cby`),
  CONSTRAINT `cby_mgi_fk` FOREIGN KEY (`cby`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=600000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_goods_issue`
--

LOCK TABLES `manual_goods_issue` WRITE;
/*!40000 ALTER TABLE `manual_goods_issue` DISABLE KEYS */;
INSERT INTO `manual_goods_issue` VALUES (600000000,'2017-06-06','fsdgfd','dfgdf','sdfg','dsfg','dsfg','dfg',2,'Y');
/*!40000 ALTER TABLE `manual_goods_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `manual_goods_receipt`
--

DROP TABLE IF EXISTS `manual_goods_receipt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `manual_goods_receipt` (
  `idmgi` int(11) NOT NULL AUTO_INCREMENT,
  `mgidte` date NOT NULL,
  `custnme` varchar(100) NOT NULL,
  `contnme` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `renum` varchar(200) NOT NULL,
  `attention` varchar(100) NOT NULL,
  `cby` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idmgi`),
  KEY `cby_mgr_fk_idx` (`cby`),
  CONSTRAINT `cby_mgr_fk` FOREIGN KEY (`cby`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=600000003 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manual_goods_receipt`
--

LOCK TABLES `manual_goods_receipt` WRITE;
/*!40000 ALTER TABLE `manual_goods_receipt` DISABLE KEYS */;
INSERT INTO `manual_goods_receipt` VALUES (600000000,'2017-06-06','sdfsd','sdfsd','sdfsd','sdfs','sdfsd','sdfs',2,'Y'),(600000001,'2017-06-06','sdfsdf','sdfsd','sdf','sdfsdf','sdfs','sdf',2,'Y'),(600000002,'2017-06-25','sdfsdf2','dsfsdfs2','sdfsdf2','sdfsdf2','sdfsdfsdfsgd2','wrwerqw2',2,'N');
/*!40000 ALTER TABLE `manual_goods_receipt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mgi_items`
--

DROP TABLE IF EXISTS `mgi_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mgi_items` (
  `idmgi_items` int(11) NOT NULL AUTO_INCREMENT,
  `mgi_id` int(11) NOT NULL,
  `mgi_invent_id` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  `valdec` int(11) DEFAULT NULL,
  PRIMARY KEY (`idmgi_items`),
  KEY `id_mgi_fk_idx` (`mgi_id`),
  KEY `id_invent_fk_idx` (`mgi_invent_id`),
  CONSTRAINT `id_invent_fk` FOREIGN KEY (`mgi_invent_id`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_mgi_fk` FOREIGN KEY (`mgi_id`) REFERENCES `manual_goods_issue` (`idmgi`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mgi_items`
--

LOCK TABLES `mgi_items` WRITE;
/*!40000 ALTER TABLE `mgi_items` DISABLE KEYS */;
INSERT INTO `mgi_items` VALUES (1,600000000,3,'Y','OPEN',2);
/*!40000 ALTER TABLE `mgi_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mgr_items`
--

DROP TABLE IF EXISTS `mgr_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mgr_items` (
  `idmgi_items` int(11) NOT NULL AUTO_INCREMENT,
  `mgi_id` int(11) NOT NULL,
  `mgi_invent_id` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  `valdec` int(11) DEFAULT NULL,
  PRIMARY KEY (`idmgi_items`),
  KEY `id_mgr_fk_idx` (`mgi_id`),
  KEY `id_invent_fk2_idx` (`mgi_invent_id`),
  CONSTRAINT `id_invent_fk2` FOREIGN KEY (`mgi_invent_id`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `id_mgr_fk` FOREIGN KEY (`mgi_id`) REFERENCES `manual_goods_receipt` (`idmgi`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mgr_items`
--

LOCK TABLES `mgr_items` WRITE;
/*!40000 ALTER TABLE `mgr_items` DISABLE KEYS */;
INSERT INTO `mgr_items` VALUES (1,600000000,1,'N','DELETED',10),(2,600000000,1,'Y','OPEN',11),(3,600000001,1,'Y','OPEN',7),(4,600000002,11,'N','DELETED',4),(5,600000002,6,'N','OPEN',2);
/*!40000 ALTER TABLE `mgr_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_inventory_adjustment_items`
--

DROP TABLE IF EXISTS `mm_inventory_adjustment_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_inventory_adjustment_items` (
  `idia_items` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) NOT NULL,
  `stock_qty` int(11) NOT NULL,
  `mov` enum('DEC','INC') NOT NULL,
  `status` enum('Y','N') NOT NULL,
  `ia_id` int(11) NOT NULL,
  `iastat` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idia_items`),
  KEY `mm_inventory_ia_fk_idx` (`inventory_id`),
  KEY `mm_ia_id_fk_idx` (`ia_id`),
  CONSTRAINT `mm_ia_id_fk` FOREIGN KEY (`ia_id`) REFERENCES `mm_inventory_adjustments` (`idadjustments`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_inventory_ia_fk` FOREIGN KEY (`inventory_id`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_inventory_adjustment_items`
--

LOCK TABLES `mm_inventory_adjustment_items` WRITE;
/*!40000 ALTER TABLE `mm_inventory_adjustment_items` DISABLE KEYS */;
INSERT INTO `mm_inventory_adjustment_items` VALUES (1,1,2,'INC','N',700000000,'DELETED'),(2,1,2,'INC','Y',700000000,'OPEN');
/*!40000 ALTER TABLE `mm_inventory_adjustment_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_inventory_adjustments`
--

DROP TABLE IF EXISTS `mm_inventory_adjustments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_inventory_adjustments` (
  `idadjustments` int(11) NOT NULL AUTO_INCREMENT,
  `iadte` date DEFAULT NULL,
  `refnum` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `pgistat` enum('Y','N') NOT NULL,
  PRIMARY KEY (`idadjustments`)
) ENGINE=InnoDB AUTO_INCREMENT=700000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_inventory_adjustments`
--

LOCK TABLES `mm_inventory_adjustments` WRITE;
/*!40000 ALTER TABLE `mm_inventory_adjustments` DISABLE KEYS */;
INSERT INTO `mm_inventory_adjustments` VALUES (700000000,'2017-06-07','dsfg','to adjust this','Y');
/*!40000 ALTER TABLE `mm_inventory_adjustments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_inventory_transactions`
--

DROP TABLE IF EXISTS `mm_inventory_transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_inventory_transactions` (
  `idinventory_transactions` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(100) NOT NULL,
  `transac_time` datetime NOT NULL,
  `item` int(11) NOT NULL,
  `qty_adj` int(11) NOT NULL,
  `effect` enum('INC','DEC') DEFAULT NULL,
  `doc_type` varchar(100) DEFAULT NULL,
  `client_id` int(11) DEFAULT NULL,
  `client_name` varchar(200) DEFAULT NULL,
  `client_type` varchar(200) DEFAULT NULL,
  `doc_it` int(11) DEFAULT NULL,
  `ref_doc` varchar(200) DEFAULT NULL,
  `doc_remark` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`idinventory_transactions`),
  KEY `mm_item_fk_idx` (`item`),
  CONSTRAINT `mm_item_fk` FOREIGN KEY (`item`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_inventory_transactions`
--

LOCK TABLES `mm_inventory_transactions` WRITE;
/*!40000 ALTER TABLE `mm_inventory_transactions` DISABLE KEYS */;
INSERT INTO `mm_inventory_transactions` VALUES (1,'sales','2017-06-06 14:22:23',1,2,'INC','INVADJ',0,'','',700000000,'dsfg','to adjust this'),(2,'sales','2017-06-06 14:38:21',1,10,'DEC','ManualGoodsIssue',0,'sdfsdf','Customer',600000000,'sdfsd','sdfsdfs'),(3,'sales','2017-06-06 16:46:10',1,11,'INC','ManualGoodsReceipt',0,'sdfsd','Customer',600000000,'sdfsd','sdfs'),(4,'sales','2017-06-06 16:50:41',1,7,'INC','ManualGoodsReceipt',0,'sdfsdf','Customer',600000001,'sdfs','sdfsdf'),(5,'sales','2017-06-10 06:05:23',2,10,'DEC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(6,'sales','2017-06-10 06:05:24',27,20,'DEC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(7,'sales','2017-06-10 06:05:24',50,10,'INC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(8,'sales','2017-06-10 06:05:24',75,20,'INC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(9,'sales','2017-06-10 06:11:45',2,10,'DEC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(10,'sales','2017-06-10 06:11:45',27,20,'DEC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(11,'sales','2017-06-10 06:11:45',50,10,'INC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(12,'sales','2017-06-10 06:11:45',75,20,'INC','StockTransfer',0,'dsfs','Customer',2,'sdf','sdf'),(13,'sales','2017-06-19 12:15:57',13,20,'INC','PostGoodsReceipt',1,'FAIR HAVENS ENTERPRISE','Supplier',3,'','dsfdfgdsfg'),(14,'Ma. Jeza Acostoy','2017-06-23 17:00:59',15,340,'DEC','STOCK TRANSMITTAL',0,'SIEGRANZ Chemworks, Corp.','SUPPLIER',6,'',NULL),(15,'Ma. Jeza Acostoy','2017-06-23 17:00:59',29,50,'DEC','STOCK TRANSMITTAL',0,'SIEGRANZ Chemworks, Corp.','SUPPLIER',6,'',NULL);
/*!40000 ALTER TABLE `mm_inventory_transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_manual_goods_issue`
--

DROP TABLE IF EXISTS `mm_manual_goods_issue`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_manual_goods_issue` (
  `idmgi` int(11) NOT NULL AUTO_INCREMENT,
  `mgidte` date NOT NULL,
  `custnme` varchar(100) NOT NULL,
  `contnme` varchar(100) NOT NULL,
  `address` varchar(100) NOT NULL,
  `description` varchar(300) NOT NULL,
  `renum` varchar(200) NOT NULL,
  `attention` varchar(100) NOT NULL,
  `cby` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idmgi`),
  KEY `mm_cby_mgi_fk_idx` (`cby`),
  CONSTRAINT `mm_cby_mgi_fk` FOREIGN KEY (`cby`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=600000001 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_manual_goods_issue`
--

LOCK TABLES `mm_manual_goods_issue` WRITE;
/*!40000 ALTER TABLE `mm_manual_goods_issue` DISABLE KEYS */;
INSERT INTO `mm_manual_goods_issue` VALUES (600000000,'2017-06-06','sdfsdf','sdf','sdfsdfs','sdfsdfs','sdfsd','sdfs',2,'Y');
/*!40000 ALTER TABLE `mm_manual_goods_issue` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_mgi_items`
--

DROP TABLE IF EXISTS `mm_mgi_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_mgi_items` (
  `idmgi_items` int(11) NOT NULL AUTO_INCREMENT,
  `mgi_id` int(11) NOT NULL,
  `mgi_invent_id` int(11) NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  `valdec` int(11) DEFAULT NULL,
  PRIMARY KEY (`idmgi_items`),
  KEY `mm_id_mgi_fk_idx` (`mgi_id`),
  KEY `mm_id_invent_fk_idx` (`mgi_invent_id`),
  CONSTRAINT `mm_id_invent_fk` FOREIGN KEY (`mgi_invent_id`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_id_mgi_fk` FOREIGN KEY (`mgi_id`) REFERENCES `mm_manual_goods_issue` (`idmgi`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_mgi_items`
--

LOCK TABLES `mm_mgi_items` WRITE;
/*!40000 ALTER TABLE `mm_mgi_items` DISABLE KEYS */;
INSERT INTO `mm_mgi_items` VALUES (1,600000000,1,'Y','OPEN',10),(2,600000000,1,'Y','DELETED',10);
/*!40000 ALTER TABLE `mm_mgi_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_pgr`
--

DROP TABLE IF EXISTS `mm_pgr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_pgr` (
  `idpgr` int(11) NOT NULL AUTO_INCREMENT,
  `idpo_doc` int(11) DEFAULT NULL,
  `pgr_dtetme` datetime DEFAULT NULL,
  PRIMARY KEY (`idpgr`),
  KEY `mm_pgr_po_doc_fk_idx` (`idpo_doc`),
  CONSTRAINT `mm_pgr_po_doc_fk` FOREIGN KEY (`idpo_doc`) REFERENCES `mm_purchases` (`idpurchases`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_pgr`
--

LOCK TABLES `mm_pgr` WRITE;
/*!40000 ALTER TABLE `mm_pgr` DISABLE KEYS */;
INSERT INTO `mm_pgr` VALUES (1,3,'2017-06-19 12:15:57');
/*!40000 ALTER TABLE `mm_pgr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_prices`
--

DROP TABLE IF EXISTS `mm_prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_prices` (
  `idPrices` int(11) NOT NULL AUTO_INCREMENT,
  `sellingPrice` decimal(10,4) NOT NULL,
  `poPrice` decimal(10,4) NOT NULL,
  `effectivedte` date NOT NULL,
  `inventory_idinventory` int(11) NOT NULL,
  PRIMARY KEY (`idPrices`,`inventory_idinventory`),
  KEY `mm_fk_prices_inventory1_idx` (`inventory_idinventory`),
  CONSTRAINT `mm_fk_prices_inventory1` FOREIGN KEY (`inventory_idinventory`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_prices`
--

LOCK TABLES `mm_prices` WRITE;
/*!40000 ALTER TABLE `mm_prices` DISABLE KEYS */;
INSERT INTO `mm_prices` VALUES (1,0.0000,0.0000,'2017-06-06',1),(2,0.0000,21.4500,'2017-06-09',4),(3,0.0000,22.5000,'2017-06-09',4),(4,0.0000,21.4600,'2017-06-12',4),(5,0.0000,23.5000,'2017-06-12',13),(6,0.0000,24.7000,'2017-06-16',13),(7,0.0000,34.6000,'2017-06-14',12),(8,0.0000,10.5000,'2017-06-15',13),(9,0.0000,35.6000,'2017-06-15',12),(10,0.0000,33.1000,'2017-06-19',12);
/*!40000 ALTER TABLE `mm_prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_purchaseitems`
--

DROP TABLE IF EXISTS `mm_purchaseitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_purchaseitems` (
  `idpurchaseitems` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) NOT NULL,
  `po_qty` int(11) DEFAULT NULL,
  `amount` decimal(10,4) NOT NULL,
  `vat_amount` decimal(10,4) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `unitprice` decimal(10,4) NOT NULL,
  `actual_qty` int(11) DEFAULT NULL,
  `batchnum` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idpurchaseitems`),
  KEY `mm_poid_fk_idx` (`purchase_id`),
  KEY `mm_inventory_id_fk2_idx` (`inventory_id`),
  CONSTRAINT `mm_inventory_id_fk2` FOREIGN KEY (`inventory_id`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_poid_fk` FOREIGN KEY (`purchase_id`) REFERENCES `mm_purchases` (`idpurchases`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_purchaseitems`
--

LOCK TABLES `mm_purchaseitems` WRITE;
/*!40000 ALTER TABLE `mm_purchaseitems` DISABLE KEYS */;
INSERT INTO `mm_purchaseitems` VALUES (1,13,23,565.8000,633.7000,3,'OPEN','Y',24.6000,20,'dsfdfgdsfg'),(2,12,12,415.2000,465.0200,3,'DELETED','N',34.6000,NULL,NULL);
/*!40000 ALTER TABLE `mm_purchaseitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_purchases`
--

DROP TABLE IF EXISTS `mm_purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_purchases` (
  `idpurchases` int(11) NOT NULL AUTO_INCREMENT,
  `sup_id` int(11) DEFAULT NULL,
  `sup_c_name` varchar(45) DEFAULT NULL,
  `po_dte` date DEFAULT NULL,
  `po_dr_dte` date DEFAULT NULL,
  `cby_id` int(11) DEFAULT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `status` enum('open','cancelled','complete') NOT NULL,
  `prntstat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idpurchases`),
  KEY `mm_sup_id_fk_idx` (`sup_id`),
  KEY `mm_cby_po_id_fk_idx` (`cby_id`),
  CONSTRAINT `mm_cby_po_id_fk` FOREIGN KEY (`cby_id`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_sup_id_fk` FOREIGN KEY (`sup_id`) REFERENCES `mm_suppliers` (`idsuppliers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_purchases`
--

LOCK TABLES `mm_purchases` WRITE;
/*!40000 ALTER TABLE `mm_purchases` DISABLE KEYS */;
INSERT INTO `mm_purchases` VALUES (3,1,'','2017-06-19','2017-06-26',2,'Y','complete','Y');
/*!40000 ALTER TABLE `mm_purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_returns`
--

DROP TABLE IF EXISTS `mm_returns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_returns` (
  `idreturns` int(11) NOT NULL AUTO_INCREMENT,
  `sku` varchar(100) DEFAULT NULL,
  `desc` varchar(100) DEFAULT NULL,
  `retuom` varchar(45) NOT NULL,
  `retwhs` varchar(45) NOT NULL,
  `soh` int(11) NOT NULL,
  PRIMARY KEY (`idreturns`),
  KEY `mm_retuomfk_idx` (`retuom`),
  KEY `mm_retwhsfk_idx` (`retwhs`),
  CONSTRAINT `mm_retuomfk` FOREIGN KEY (`retuom`) REFERENCES `uom` (`uomcol`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `mm_retwhsfk` FOREIGN KEY (`retwhs`) REFERENCES `warehouses` (`idwarehouses`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_returns`
--

LOCK TABLES `mm_returns` WRITE;
/*!40000 ALTER TABLE `mm_returns` DISABLE KEYS */;
INSERT INTO `mm_returns` VALUES (1,'343423535412','dfgfsdg12','CS','BC',503);
/*!40000 ALTER TABLE `mm_returns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_returns_adjustments`
--

DROP TABLE IF EXISTS `mm_returns_adjustments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_returns_adjustments` (
  `idreturns_adjustments` int(11) NOT NULL AUTO_INCREMENT,
  `rs_dte` date DEFAULT NULL,
  `refnum` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `pgistat` varchar(45) NOT NULL,
  PRIMARY KEY (`idreturns_adjustments`)
) ENGINE=InnoDB AUTO_INCREMENT=500000005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_returns_adjustments`
--

LOCK TABLES `mm_returns_adjustments` WRITE;
/*!40000 ALTER TABLE `mm_returns_adjustments` DISABLE KEYS */;
INSERT INTO `mm_returns_adjustments` VALUES (500000000,'2017-06-06','dfdgs','dsfgfd','Y'),(500000001,'2017-06-06','fgdfgdf','gdfgdfg','Y'),(500000002,'2017-06-06','dfgdf','dfgsdg','Y'),(500000003,'2017-06-06','fdgdfg','dfgdfgdf','Y'),(500000004,'2017-06-06','','','Y');
/*!40000 ALTER TABLE `mm_returns_adjustments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_returns_adjustments_items`
--

DROP TABLE IF EXISTS `mm_returns_adjustments_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_returns_adjustments_items` (
  `idretadtems` int(11) NOT NULL AUTO_INCREMENT,
  `return_id` int(11) NOT NULL,
  `stock_qty` int(11) NOT NULL,
  `mov` enum('INC','DEC') DEFAULT NULL,
  `status` enum('Y','N') DEFAULT NULL,
  `retadj_id` int(11) NOT NULL,
  `itemstatus` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idretadtems`),
  KEY `mm_ret_id_fk_idx` (`return_id`),
  KEY `mm_ret_adj_id_fk_idx` (`retadj_id`),
  CONSTRAINT `mm_ret_adj_id_fk` FOREIGN KEY (`retadj_id`) REFERENCES `mm_returns_adjustments` (`idreturns_adjustments`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `mm_ret_id_fk` FOREIGN KEY (`return_id`) REFERENCES `mm_returns` (`idreturns`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_returns_adjustments_items`
--

LOCK TABLES `mm_returns_adjustments_items` WRITE;
/*!40000 ALTER TABLE `mm_returns_adjustments_items` DISABLE KEYS */;
INSERT INTO `mm_returns_adjustments_items` VALUES (1,1,3,'INC','Y',500000000,'OPEN'),(2,1,2,'INC','Y',500000001,'OPEN'),(3,1,3,'INC','Y',500000002,'OPEN'),(4,1,3,'INC','Y',500000003,'OPEN'),(5,1,3,'INC','Y',500000004,'OPEN');
/*!40000 ALTER TABLE `mm_returns_adjustments_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_supcontacts`
--

DROP TABLE IF EXISTS `mm_supcontacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_supcontacts` (
  `idsupcontacts` int(11) NOT NULL AUTO_INCREMENT,
  `supcname` varchar(45) DEFAULT NULL,
  `supccontact` varchar(45) DEFAULT NULL,
  `supcemail` varchar(100) DEFAULT NULL,
  `sup_id` int(11) NOT NULL,
  PRIMARY KEY (`idsupcontacts`,`sup_id`),
  KEY `mm_fk_supplier_idx` (`sup_id`),
  CONSTRAINT `mm_fk_supplier` FOREIGN KEY (`sup_id`) REFERENCES `mm_suppliers` (`idsuppliers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_supcontacts`
--

LOCK TABLES `mm_supcontacts` WRITE;
/*!40000 ALTER TABLE `mm_supcontacts` DISABLE KEYS */;
INSERT INTO `mm_supcontacts` VALUES (1,'GLECY BAYSIC','3666967','glecyfhe@gmail.com /fairhaven06@gmail.com',1);
/*!40000 ALTER TABLE `mm_supcontacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mm_suppliers`
--

DROP TABLE IF EXISTS `mm_suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `mm_suppliers` (
  `idsuppliers` int(11) NOT NULL AUTO_INCREMENT,
  `supname` varchar(200) DEFAULT NULL,
  `suptin` varchar(45) DEFAULT NULL,
  `supaddress` varchar(300) DEFAULT NULL,
  `supbusines` varchar(45) DEFAULT NULL,
  `suppayment` varchar(45) DEFAULT NULL,
  `postal` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idsuppliers`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mm_suppliers`
--

LOCK TABLES `mm_suppliers` WRITE;
/*!40000 ALTER TABLE `mm_suppliers` DISABLE KEYS */;
INSERT INTO `mm_suppliers` VALUES (1,'FAIR HAVENS ENTERPRISE','171-080-418-000','99 BUSA ST. BRGY SAN JOSE CALOOCAN CITY','SINGLE PROP','90Days','1404'),(2,'C A J Packaging Solutions','008-833-999-000','UNIT 41 and 42 PARK HOUSE 1 227 EDSA GREENHILLS, MANDALUYONG CITY','TRADING','90 Days','1555'),(3,'﻿SIEGRANZ CHEMWORKS CORP.','224-138-721-000','15 SAPPHIRE ST.DBP FARM ROAD DBP FARM SUBD. PULANG LUPA 2 LAS PINAS CITY','MANUFACTURING','90DAYS','1742\r\n'),(4,'TRADESPHERE INDUSTRIAL COMMODITIES, INC.','000-168-157-000','12 ACSIE(MAIN)AVE., SEVERINA INDUSTRIAL ESTATE KM.16 SOUTH SUPERHIGHWAY, P\'QUE CITY','MANUFACTURING','60days','1700\r\n'),(5,'ZELLER PLASTIK PHILIPPINES INC.','000-150-900-000','BLDG3 PHILCREST COMPOUND KM.23 WEST SERVICE ROAD CUPANG MUNTINLUPA CITY','MANUFACTURING','30DAYS','1771\r\n'),(6,'ARTS AND COLOURS PRINTING SERVICES','230-782-045-000','4TH FLOOR, STRATA 100, EMERALD AVENUE, ORTIGAS CENTER, PASIG CITY','PRINTING SERVICES','60DAYS','1605\r\n'),(7,'SUPERIOR PACKAGING CORPORATION','000-380-903-000','No.5 TANDANG SORA EXT., BRGY TALIPAPA NOVALICHES Q.C','MANUFACTURING','30DAYS','1116\r\n'),(8,'VALUE-RX, INC.','210-477-228-000','2/F Madison Square Bldg., E. Rodriguez Jr. Avenue, Brgy. Bagumbayan, Quezon City, 1100 Metro Manila','OTHER FULL SELLING','30DAYS','1110\r\n'),(9,'FLASHPACK MARKETING CORPORATION','','BLK 25 LOT 5 METRO COR. B HOMES, TALON 5 LAS PINAS','TRADING & MANUFACTURING','30DAYS','1747\r\n'),(10,'BENIZON CORPORATION','004-715-686-000','GREENWAY BUSINESS PARK GOVERNORS DRIVE BULIHAN SILANG CAVITE','MANUFACTURER','30DAYS','4118\r\n'),(11,'LABELMEN ENTERPRISES','133-718-472-000','57 SHAW BLVD.. DAANG BAKAL, MANDALUYONG CITY','GENERAL MERCHANDISING','30days','1551\r\n'),(12,'GLOBAL COMPAK, INC.','216-852-613-000','SAMPALOCAN ROAD BRGY. SAN ROQUE STO. TOMAS BATANGAS','MANUFACTURING','60days','4234\r\n'),(13,'INCON INDUSTRIAL CORPORATION','000-234-953-000','193 PIO VALENZUELA ST. MARULAS VALENZUELA CITY','MANUFACTURING','90DAYS','1440\r\n');
/*!40000 ALTER TABLE `mm_suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pgr`
--

DROP TABLE IF EXISTS `pgr`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pgr` (
  `idpgr` int(11) NOT NULL AUTO_INCREMENT,
  `idpo_doc` int(11) DEFAULT NULL,
  `pgr_dtetme` datetime DEFAULT NULL,
  `stdoc` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idpgr`),
  KEY `pgr_po_doc_fk_idx` (`idpo_doc`),
  CONSTRAINT `pgr_po_doc_fk` FOREIGN KEY (`idpo_doc`) REFERENCES `purchases` (`idpurchases`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pgr`
--

LOCK TABLES `pgr` WRITE;
/*!40000 ALTER TABLE `pgr` DISABLE KEYS */;
INSERT INTO `pgr` VALUES (1,400000000,'2017-06-06 14:03:07','N'),(2,400000001,'2017-06-19 12:01:57','Y');
/*!40000 ALTER TABLE `pgr` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pm_inventory`
--

DROP TABLE IF EXISTS `pm_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `pm_inventory` (
  `idinventory` int(11) NOT NULL AUTO_INCREMENT,
  `sku` varchar(100) DEFAULT NULL,
  `skudesc` varchar(100) DEFAULT NULL,
  `skuom` varchar(45) NOT NULL,
  `skuwh` varchar(45) NOT NULL,
  `soh` int(11) DEFAULT NULL,
  `csl` int(11) DEFAULT NULL,
  `units` int(11) NOT NULL,
  `inv_type` varchar(45) NOT NULL,
  PRIMARY KEY (`idinventory`),
  KEY `mm_fk_wh_idx` (`skuwh`),
  KEY `mm_fk_uom_idx` (`skuom`),
  CONSTRAINT `mm_fk_uomi` FOREIGN KEY (`skuom`) REFERENCES `uom` (`uomcol`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `mm_fk_whi` FOREIGN KEY (`skuwh`) REFERENCES `warehouses` (`idwarehouses`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=99 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pm_inventory`
--

LOCK TABLES `pm_inventory` WRITE;
/*!40000 ALTER TABLE `pm_inventory` DISABLE KEYS */;
INSERT INTO `pm_inventory` VALUES (1,'sdfsdf','sdgsdg','CS','BC',510,40,2,''),(2,'FSG2041S0118','20/410 GREEN SPRAYER 118ML','PC','BC',480,100,1,''),(3,'FSO2041S0118','20/410 ORANGE SPRAYER 118ML','PC','BC',500,100,1,''),(4,'FSP2041S0118','20/410 PINK SPRAYER 118ML','PC','BC',500,100,1,''),(5,'FSV2041S0118','20/410 VIOLET SPRAYER 118ML','PC','BC',500,100,1,''),(6,'OPORNGEU0510','28/410 OPAQUE ORANGE PUMP 510ML','PC','BC',500,100,1,''),(7,'OPPINKPU0510','28/410 OPAQUE PINK PUMP 510ML','PC','BC',500,100,1,''),(8,'OPVIOLTU0510','28/410 OPAQUE VIOLET PUMP 510ML','PC','BC',500,100,1,''),(9,'BCBWFTCC0400','28MM FLIPTOP CLEAR CAP 400ML','PC','BC',500,100,1,''),(10,'LHSBLUEP1000','33/410 BLUE PUMP','PC','BC',500,100,1,''),(11,'HS33410U0750','33/410 HAND SOAP PUMP 750ML','PC','BC',500,100,1,''),(12,'FLG3341P0500','33/410 OPAQUE GREEN PUMP 500ML(130MM)','PC','BC',500,100,1,''),(13,'FLG3341P0750','33/410 OPAQUE GREEN PUMP 750ML(160MM)','PC','BC',520,100,1,''),(14,'FLO3341P0500','33/410 OPAQUE ORANGE PUMP 500ML(130MM)','PC','BC',500,100,1,''),(15,'FLO3341P0750','33/410 OPAQUE ORANGE PUMP 750ML(160MM)','PC','BC',160,100,1,''),(16,'FLP3341P0500','33/410 OPAQUE PINK PUMP 500ML(130MM)','PC','BC',500,100,1,''),(17,'FLP3341P0750','33/410 OPAQUE PINK PUMP 750ML(160MM)','PC','BC',500,100,1,''),(18,'BWPETBTU0850','33/410 ORIGINAL PUMP 850ML','PC','BC',500,100,1,''),(19,'MESKTPPU0850','33/410 SKIRTED PUMP 850ML','PC','BC',500,100,1,''),(20,'3MTMASTR0250','3MINUTE  COR.CARTON 250ML','PC','BC',500,100,1,''),(21,'3MWTALIL0250','3MINUTE WONDER TREATMENT ALOE INFUSSION 250ML','PC','BC',500,100,1,''),(22,'3MWTMLPL0250','3MINUTE WONDER TREATMENT MILK PROTEIN 250ML','PC','BC',500,100,1,''),(23,'3MWTMOOL0250','3MINUTE WONDER TREATMENT MOROCCAN OIL 250ML','PC','BC',500,100,1,''),(24,'BCPLSTCP0125','BABY COLOGNE 125ML PLASTIC','PC','BC',500,100,1,''),(25,'BCBUBBLL0125','Baby Cologne Bubbles 125ml','PC','BC',500,100,1,''),(26,'BCMASTRB0125','BABY COLOGNE COR.CARTON 125ML','PC','BC',500,100,1,''),(27,'BCHPYMSL0125','Baby Cologne Happy Mist 125ml','PC','BC',460,100,1,''),(28,'BCINRBXI0125','BABY COLOGNE INNER BOX 125ML','PC','BC',500,100,1,''),(29,'BCPRHVNL0125','Baby Cologne PureHeaven 125ml','PC','BC',450,100,1,''),(30,'BCSOTCHL0125','Baby Cologne Soft Touch 125ml','PC','BC',500,100,1,''),(31,'BCSWSMLL0125','Baby Cologne Sweet Smile 125ml','PC','BC',500,100,1,''),(32,'BCWLMSTR0500','BC WASH/LOTION  COR.CARTON 500ML','PC','BC',500,100,1,''),(33,'WCBLKTBC0250','BLACK TUBE CAP 250ML','PC','BC',500,100,1,''),(34,'BCBWMSTR0400','BODY  WASH COR.CARTON 400ML','PC','BC',500,100,1,''),(35,'BLACBRYL0850','Body Lotion Acai Berry 850ml','PC','BC',500,100,1,''),(36,'BLCLMNSL0850','Body Lotion Calamansi 850ml','PC','BC',500,100,1,''),(37,'BCLCESPL0500','Body Lotion Carribean Escape 500ml','PC','BC',500,100,1,''),(38,'BCLFBLML0500','Body Lotion Fresh Bloom 500ml','PC','BC',500,100,1,''),(39,'BLGPPYAL0850','Body Lotion Green Papaya  850ml','PC','BC',500,100,1,''),(40,'BLMNGOSL0850','Body Lotion Mangosteen 850ml','PC','BC',500,100,1,''),(41,'BLMINBXI0510','BODY LOTION MOIST  INNER BOX 510ML','PC','BC',500,100,1,''),(42,'BLMMASTR0510','BODY LOTION MOIST COR.CARTON 510ML','PC','BC',500,100,1,''),(43,'BLMEPRML0510','Body Lotion Moist Evening Primrose 510ml','PC','BC',500,100,1,''),(44,'BLMLVNDL0510','Body Lotion Moist Lavender 510ml','PC','BC',500,100,1,''),(45,'BCLMBRZL0500','Body Lotion Moonlight Breeze 500ml','PC','BC',500,100,1,''),(46,'BLOPPYAL0850','Body Lotion Orange Papaya  850ml','PC','BC',500,100,1,''),(47,'BLVIRGNL0850','Body Lotion Virgin Coco 850ml','PC','BC',500,100,1,''),(48,'BOMTRBXI0230','BODY OIL  INNER BOX 230','PC','BC',500,100,1,''),(49,'BOPL230P0000','BODY OIL 230 PLASTIC','PC','BC',500,100,1,''),(50,'FSG2041S0118','20/410 GREEN SPRAYER 118ML','PC','SG',520,100,1,''),(51,'FSO2041S0118','20/410 ORANGE SPRAYER 118ML','PC','SG',500,100,1,''),(52,'FSP2041S0118','20/410 PINK SPRAYER 118ML','PC','SG',500,100,1,''),(53,'FSV2041S0118','20/410 VIOLET SPRAYER 118ML','PC','SG',500,100,1,''),(54,'OPORNGEU0510','28/410 OPAQUE ORANGE PUMP 510ML','PC','SG',500,100,1,''),(55,'OPPINKPU0510','28/410 OPAQUE PINK PUMP 510ML','PC','SG',500,100,1,''),(56,'OPVIOLTU0510','28/410 OPAQUE VIOLET PUMP 510ML','PC','SG',500,100,1,''),(57,'BCBWFTCC0400','28MM FLIPTOP CLEAR CAP 400ML','PC','SG',500,100,1,''),(58,'LHSBLUEP1000','33/410 BLUE PUMP','PC','SG',500,100,1,''),(59,'HS33410U0750','33/410 HAND SOAP PUMP 750ML','PC','SG',500,100,1,''),(60,'FLG3341P0500','33/410 OPAQUE GREEN PUMP 500ML(130MM)','PC','SG',500,100,1,''),(61,'FLG3341P0750','33/410 OPAQUE GREEN PUMP 750ML(160MM)','PC','SG',500,100,1,''),(62,'FLO3341P0500','33/410 OPAQUE ORANGE PUMP 500ML(130MM)','PC','SG',500,100,1,''),(63,'FLO3341P0750','33/410 OPAQUE ORANGE PUMP 750ML(160MM)','PC','SG',500,100,1,''),(64,'FLP3341P0500','33/410 OPAQUE PINK PUMP 500ML(130MM)','PC','SG',500,100,1,''),(65,'FLP3341P0750','33/410 OPAQUE PINK PUMP 750ML(160MM)','PC','SG',500,100,1,''),(66,'BWPETBTU0850','33/410 ORIGINAL PUMP 850ML','PC','SG',500,100,1,''),(67,'MESKTPPU0850','33/410 SKIRTED PUMP 850ML','PC','SG',500,100,1,''),(68,'3MTMASTR0250','3MINUTE  COR.CARTON 250ML','PC','SG',500,100,1,''),(69,'3MWTALIL0250','3MINUTE WONDER TREATMENT ALOE INFUSSION 250ML','PC','SG',500,100,1,''),(70,'3MWTMLPL0250','3MINUTE WONDER TREATMENT MILK PROTEIN 250ML','PC','SG',500,100,1,''),(71,'3MWTMOOL0250','3MINUTE WONDER TREATMENT MOROCCAN OIL 250ML','PC','SG',500,100,1,''),(72,'BCPLSTCP0125','BABY COLOGNE 125ML PLASTIC','PC','SG',500,100,1,''),(73,'BCBUBBLL0125','Baby Cologne Bubbles 125ml','PC','SG',500,100,1,''),(74,'BCMASTRB0125','BABY COLOGNE COR.CARTON 125ML','PC','SG',500,100,1,''),(75,'BCHPYMSL0125','Baby Cologne Happy Mist 125ml','PC','SG',540,100,1,''),(76,'BCINRBXI0125','BABY COLOGNE INNER BOX 125ML','PC','SG',500,100,1,''),(77,'BCPRHVNL0125','Baby Cologne PureHeaven 125ml','PC','SG',500,100,1,''),(78,'BCSOTCHL0125','Baby Cologne Soft Touch 125ml','PC','SG',500,100,1,''),(79,'BCSWSMLL0125','Baby Cologne Sweet Smile 125ml','PC','SG',500,100,1,''),(80,'BCWLMSTR0500','BC WASH/LOTION  COR.CARTON 500ML','PC','SG',500,100,1,''),(81,'WCBLKTBC0250','BLACK TUBE CAP 250ML','PC','SG',500,100,1,''),(82,'BCBWMSTR0400','BODY  WASH COR.CARTON 400ML','PC','SG',500,100,1,''),(83,'BLACBRYL0850','Body Lotion Acai Berry 850ml','PC','SG',500,100,1,''),(84,'BLCLMNSL0850','Body Lotion Calamansi 850ml','PC','SG',500,100,1,''),(85,'BCLCESPL0500','Body Lotion Carribean Escape 500ml','PC','SG',500,100,1,''),(86,'BCLFBLML0500','Body Lotion Fresh Bloom 500ml','PC','SG',500,100,1,''),(87,'BLGPPYAL0850','Body Lotion Green Papaya  850ml','PC','SG',500,100,1,''),(88,'BLMNGOSL0850','Body Lotion Mangosteen 850ml','PC','SG',500,100,1,''),(89,'BLMINBXI0510','BODY LOTION MOIST  INNER BOX 510ML','PC','SG',500,100,1,''),(90,'BLMMASTR0510','BODY LOTION MOIST COR.CARTON 510ML','PC','SG',500,100,1,''),(91,'BLMEPRML0510','Body Lotion Moist Evening Primrose 510ml','PC','SG',500,100,1,''),(92,'BLMLVNDL0510','Body Lotion Moist Lavender 510ml','PC','SG',500,100,1,''),(93,'BCLMBRZL0500','Body Lotion Moonlight Breeze 500ml','PC','SG',500,100,1,''),(94,'BLOPPYAL0850','Body Lotion Orange Papaya  850ml','PC','SG',500,100,1,''),(95,'BLVIRGNL0850','Body Lotion Virgin Coco 850ml','PC','SG',500,100,1,''),(96,'BOMTRBXI0230','BODY OIL  INNER BOX 230','PC','SG',500,100,1,''),(97,'BOPL230P0000','BODY OIL 230 PLASTIC','PC','SG',500,100,1,''),(98,'HASDHIDF','sdfsdf','CS','BC',500,20,2,'');
/*!40000 ALTER TABLE `pm_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `price_list`
--

DROP TABLE IF EXISTS `price_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `price_list` (
  `idpricelist` int(11) NOT NULL AUTO_INCREMENT,
  `mm_prices_idPrices` int(11) NOT NULL,
  `mm_prices_inventory_idinventory` int(11) NOT NULL,
  `mm_suppliers_idsuppliers` int(11) NOT NULL,
  PRIMARY KEY (`idpricelist`,`mm_prices_idPrices`,`mm_prices_inventory_idinventory`,`mm_suppliers_idsuppliers`),
  KEY `fk_price_list_mm_prices1_idx` (`mm_prices_idPrices`),
  KEY `fk_price_list_mm_supplier2_idx` (`mm_suppliers_idsuppliers`),
  CONSTRAINT `fk_price_list_mm_prices1` FOREIGN KEY (`mm_prices_idPrices`) REFERENCES `mm_prices` (`idPrices`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_price_list_mm_supplier2` FOREIGN KEY (`mm_suppliers_idsuppliers`) REFERENCES `mm_suppliers` (`idsuppliers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `price_list`
--

LOCK TABLES `price_list` WRITE;
/*!40000 ALTER TABLE `price_list` DISABLE KEYS */;
INSERT INTO `price_list` VALUES (4,2,4,1),(5,3,4,2),(6,4,4,1),(7,5,13,1),(8,6,13,1),(9,7,12,1),(10,8,13,2),(11,9,12,2),(12,10,12,2);
/*!40000 ALTER TABLE `price_list` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prices`
--

DROP TABLE IF EXISTS `prices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `prices` (
  `idPrices` int(11) NOT NULL AUTO_INCREMENT,
  `sellingPrice` decimal(10,4) NOT NULL,
  `poPrice` decimal(10,4) NOT NULL,
  `effectivedte` date NOT NULL,
  `inventory_idinventory` int(11) NOT NULL,
  PRIMARY KEY (`idPrices`,`inventory_idinventory`),
  KEY `fk_prices_inventory1_idx` (`inventory_idinventory`),
  CONSTRAINT `fk_prices_inventory1` FOREIGN KEY (`inventory_idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=666 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prices`
--

LOCK TABLES `prices` WRITE;
/*!40000 ALTER TABLE `prices` DISABLE KEYS */;
INSERT INTO `prices` VALUES (1,972.0000,372.0000,'2017-06-03',1),(2,972.0000,372.0000,'2017-06-03',2),(3,972.0000,372.0000,'2017-06-03',3),(4,972.0000,372.0000,'2017-06-03',4),(5,1042.2000,456.0000,'2017-06-03',5),(6,1042.2000,456.0000,'2017-06-03',6),(7,1042.2000,456.0000,'2017-06-03',7),(8,1042.2000,456.0000,'2017-06-03',8),(9,1162.2000,418.4400,'2017-06-03',9),(10,1162.2000,418.4400,'2017-06-03',10),(11,1042.2000,456.0000,'2017-06-03',11),(12,1042.2000,456.0000,'2017-06-03',12),(13,708.0000,252.0000,'2017-06-03',13),(14,708.0000,252.0000,'2017-06-03',14),(15,708.0000,252.0000,'2017-06-03',15),(16,708.0000,252.0000,'2017-06-03',16),(17,850.2000,153.0000,'2017-06-03',17),(18,850.2000,153.0000,'2017-06-03',18),(19,784.8000,147.0000,'2017-06-03',19),(20,784.8000,147.0000,'2017-06-03',20),(21,1987.2000,660.0000,'2017-06-03',21),(22,1987.2000,660.0000,'2017-06-03',22),(23,1411.2000,348.0000,'2017-06-03',23),(24,1411.2000,348.0000,'2017-06-03',24),(25,1987.2000,660.0000,'2017-06-03',25),(26,1987.2000,660.0000,'2017-06-03',26),(27,1216.8000,396.0000,'2017-06-03',27),(28,1216.8000,396.0000,'2017-06-03',28),(29,1216.8000,396.0000,'2017-06-03',29),(30,2145.6000,864.0000,'2017-06-03',30),(31,2145.6000,864.0000,'2017-06-03',31),(32,2145.6000,864.0000,'2017-06-03',32),(33,2145.6000,864.0000,'2017-06-03',33),(34,1425.6000,456.0000,'2017-06-03',34),(35,1425.6000,456.0000,'2017-06-03',35),(36,850.2000,135.0000,'2017-06-03',36),(37,784.8000,135.0000,'2017-06-03',37),(38,784.8000,135.0000,'2017-06-03',38),(39,1987.2000,660.0000,'2017-06-03',39),(40,1987.2000,660.0000,'2017-06-03',40),(41,1987.2000,660.0000,'2017-06-03',41),(42,1987.2000,660.0000,'2017-06-03',42),(43,1411.2000,348.0000,'2017-06-03',43),(44,1411.2000,348.0000,'2017-06-03',44),(45,4550.4000,1440.0000,'2017-06-03',45),(46,4550.4000,1440.0000,'2017-06-03',46),(47,4550.4000,1440.0000,'2017-06-03',47),(48,4550.4000,1440.0000,'2017-06-03',48),(49,2275.2000,792.0000,'2017-06-03',49),(50,2275.2000,792.0000,'2017-06-03',50),(51,2275.2000,792.0000,'2017-06-03',51),(52,2275.2000,792.0000,'2017-06-03',52),(53,4368.0000,2319.6600,'2017-06-03',53),(54,900.0000,428.1600,'2017-06-03',54),(55,900.0000,428.1600,'2017-06-03',55),(56,900.0000,428.1600,'2017-06-03',56),(57,2160.0000,810.2400,'2017-06-03',57),(58,2160.0000,833.2800,'2017-06-03',58),(59,2160.0000,833.2800,'2017-06-03',59),(60,2448.0000,1296.0000,'2017-06-03',60),(61,2448.0000,1440.0000,'2017-06-03',61),(62,2448.0000,1656.0000,'2017-06-03',62),(63,1512.0000,588.0000,'2017-06-03',63),(64,1512.0000,588.0000,'2017-06-03',64),(65,3400.3200,1980.4800,'2017-06-03',65),(66,3400.3200,1980.4800,'2017-06-03',66),(67,3400.3200,1980.4800,'2017-06-03',67),(68,3400.3200,1980.4800,'2017-06-03',68),(69,2160.0000,833.2800,'2017-06-03',69),(70,900.0000,428.1600,'2017-06-03',70),(71,2160.0000,833.2800,'2017-06-03',71),(72,900.0000,439.6800,'2017-06-03',72),(73,2275.2000,1272.9600,'2017-06-03',73),(74,2275.2000,1272.9600,'2017-06-03',74),(75,2275.2000,1272.9600,'2017-06-03',75),(76,2275.2000,1272.9600,'2017-06-03',76),(77,2275.2000,1272.9600,'2017-06-03',77),(78,928.8000,450.0000,'2017-06-03',78),(79,928.8000,444.0000,'2017-06-03',79),(80,928.8000,444.0000,'2017-06-03',80),(81,928.8000,450.0000,'2017-06-03',81),(82,859.2000,390.0000,'2017-06-03',82),(83,859.2000,390.0000,'2017-06-03',83),(84,859.2000,390.0000,'2017-06-03',84),(85,924.0000,362.4000,'2017-06-03',85),(86,924.0000,372.0000,'2017-06-03',86),(87,576.0000,192.0000,'2017-06-03',87),(88,576.0000,192.0000,'2017-06-03',88),(89,1162.2000,418.4400,'2017-06-03',89),(90,1042.2000,456.0000,'2017-06-03',90),(91,655.2000,138.0000,'2017-06-03',91),(92,655.2000,138.0000,'2017-06-03',92),(93,655.2000,138.0000,'2017-06-03',93),(94,655.2000,156.0000,'2017-06-03',94),(95,655.2000,156.0000,'2017-06-03',95),(96,655.2000,156.0000,'2017-06-03',96),(97,1216.8000,348.0000,'2017-06-03',97),(98,1216.8000,348.0000,'2017-06-03',98),(99,1216.8000,348.0000,'2017-06-03',99),(100,1216.8000,348.0000,'2017-06-03',100),(101,708.0000,252.0000,'2017-06-03',101),(102,708.0000,252.0000,'2017-06-03',102),(103,900.0000,430.4400,'2017-06-03',103),(104,900.0000,462.8400,'2017-06-03',104),(105,2160.0000,913.9200,'2017-06-03',105),(106,2160.0000,821.7600,'2017-06-03',106),(107,708.0000,252.0000,'2017-06-03',107),(108,708.0000,252.0000,'2017-06-03',108),(109,859.2000,390.0000,'2017-06-03',109),(110,1296.0000,535.2000,'2017-06-03',110),(111,1296.0000,535.2000,'2017-06-03',111),(112,1296.0000,535.2000,'2017-06-03',112),(113,648.0000,258.0000,'2017-06-03',113),(114,648.0000,258.0000,'2017-06-03',114),(115,648.0000,258.0000,'2017-06-03',115),(116,1912.8000,0.0000,'2017-06-03',116),(117,1912.8000,0.0000,'2017-06-03',117),(118,1912.8000,0.0000,'2017-06-03',118),(119,1251.6000,300.0000,'2017-06-03',119),(120,1251.6000,300.0000,'2017-06-03',120),(121,1251.6000,300.0000,'2017-06-03',121),(122,1142.4000,0.0000,'2017-06-03',122),(123,1142.4000,0.0000,'2017-06-03',123),(124,1142.4000,0.0000,'2017-06-03',124),(125,840.0000,408.0000,'2017-06-03',125),(126,840.0000,408.0000,'2017-06-03',126),(127,840.0000,408.0000,'2017-06-03',127),(128,840.0000,408.0000,'2017-06-03',128),(129,840.0000,408.0000,'2017-06-03',129),(130,1440.0000,588.0000,'2017-06-03',130),(131,1440.0000,588.0000,'2017-06-03',131),(132,1440.0000,588.0000,'2017-06-03',132),(133,972.0000,372.0000,'2017-06-03',133),(134,972.0000,372.0000,'2017-06-03',134),(135,1042.2000,456.0000,'2017-06-03',135),(136,1042.2000,456.0000,'2017-06-03',136),(137,1200.0000,516.0000,'2017-06-03',137),(138,1200.0000,516.0000,'2017-06-03',138),(139,1200.0000,516.0000,'2017-06-03',139),(140,1200.0000,516.0000,'2017-06-03',140),(141,624.0000,198.0000,'2017-06-03',141),(142,624.0000,198.0000,'2017-06-03',142),(143,624.0000,198.0000,'2017-06-03',143),(144,624.0000,198.0000,'2017-06-03',144),(145,624.0000,240.0000,'2017-06-03',145),(146,624.0000,240.0000,'2017-06-03',146),(147,624.0000,240.0000,'2017-06-03',147),(148,624.0000,240.0000,'2017-06-03',148),(149,816.0000,0.0000,'2017-06-03',149),(150,816.0000,0.0000,'2017-06-03',150),(151,816.0000,0.0000,'2017-06-03',151),(152,816.0000,0.0000,'2017-06-03',152),(153,900.0000,456.0000,'2017-06-03',153),(154,900.0000,456.0000,'2017-06-03',154),(155,744.0000,252.0000,'2017-06-03',155),(156,2145.6000,1320.0000,'2017-06-03',156),(157,2832.0000,1176.0000,'2017-06-03',157),(158,2832.0000,1176.0000,'2017-06-03',158),(159,2832.0000,1176.0000,'2017-06-03',159),(160,2832.0000,1176.0000,'2017-06-03',160),(161,2832.0000,1176.0000,'2017-06-03',161),(162,2832.0000,1176.0000,'2017-06-03',162),(163,1416.0000,588.0000,'2017-06-03',163),(164,1416.0000,588.0000,'2017-06-03',164),(165,924.0000,312.0000,'2017-06-03',165),(166,924.0000,312.0000,'2017-06-03',166),(167,81.0000,31.0000,'2017-06-03',167),(168,81.0000,31.0000,'2017-06-03',168),(169,81.0000,31.0000,'2017-06-03',169),(170,81.0000,31.0000,'2017-06-03',170),(171,86.8500,38.0000,'2017-06-03',171),(172,86.8500,38.0000,'2017-06-03',172),(173,86.8500,38.0000,'2017-06-03',173),(174,86.8500,38.0000,'2017-06-03',174),(175,96.8500,34.8700,'2017-06-03',175),(176,96.8500,34.8700,'2017-06-03',176),(177,86.8500,38.0000,'2017-06-03',177),(178,86.8500,38.0000,'2017-06-03',178),(179,59.0000,21.0000,'2017-06-03',179),(180,59.0000,21.0000,'2017-06-03',180),(181,59.0000,21.0000,'2017-06-03',181),(182,59.0000,21.0000,'2017-06-03',182),(183,70.8500,12.7500,'2017-06-03',183),(184,70.8500,12.7500,'2017-06-03',184),(185,65.4000,12.2500,'2017-06-03',185),(186,65.4000,12.2500,'2017-06-03',186),(187,41.4000,13.7500,'2017-06-03',187),(188,41.4000,13.7500,'2017-06-03',188),(189,29.4000,7.2500,'2017-06-03',189),(190,29.4000,7.2500,'2017-06-03',190),(191,41.4000,13.7500,'2017-06-03',191),(192,41.4000,13.7500,'2017-06-03',192),(193,50.7000,16.5000,'2017-06-03',193),(194,50.7000,16.5000,'2017-06-03',194),(195,50.7000,16.5000,'2017-06-03',195),(196,44.7000,18.0000,'2017-06-03',196),(197,44.7000,18.0000,'2017-06-03',197),(198,44.7000,18.0000,'2017-06-03',198),(199,44.7000,18.0000,'2017-06-03',199),(200,59.4000,19.0000,'2017-06-03',200),(201,59.4000,19.0000,'2017-06-03',201),(202,70.8500,11.2500,'2017-06-03',202),(203,65.4000,11.2500,'2017-06-03',203),(204,65.4000,11.2500,'2017-06-03',204),(205,41.4000,13.7500,'2017-06-03',205),(206,41.4000,13.7500,'2017-06-03',206),(207,41.4000,13.7500,'2017-06-03',207),(208,41.4000,13.7500,'2017-06-03',208),(209,29.4000,7.2500,'2017-06-03',209),(210,29.4000,7.2500,'2017-06-03',210),(211,47.4000,15.0000,'2017-06-03',211),(212,47.4000,15.0000,'2017-06-03',212),(213,47.4000,15.0000,'2017-06-03',213),(214,47.4000,15.0000,'2017-06-03',214),(215,23.7000,8.2500,'2017-06-03',215),(216,23.7000,8.2500,'2017-06-03',216),(217,23.7000,8.2500,'2017-06-03',217),(218,23.7000,8.2500,'2017-06-03',218),(219,45.4000,24.1100,'2017-06-03',219),(220,75.0000,35.6800,'2017-06-03',220),(221,75.0000,35.6800,'2017-06-03',221),(222,75.0000,35.6800,'2017-06-03',222),(223,45.0000,16.8800,'2017-06-03',223),(224,45.0000,17.3600,'2017-06-03',224),(225,45.0000,17.3600,'2017-06-03',225),(226,51.0000,27.0000,'2017-06-03',226),(227,51.0000,30.0000,'2017-06-03',227),(228,51.0000,34.5000,'2017-06-03',228),(229,63.0000,24.5000,'2017-06-03',229),(230,63.0000,24.5000,'2017-06-03',230),(231,35.4200,20.6300,'2017-06-03',231),(232,35.4200,20.6300,'2017-06-03',232),(233,35.4200,20.6300,'2017-06-03',233),(234,35.4200,20.6300,'2017-06-03',234),(235,45.0000,17.3600,'2017-06-03',235),(236,75.0000,35.6800,'2017-06-03',236),(237,45.0000,17.3600,'2017-06-03',237),(238,75.0000,36.6400,'2017-06-03',238),(239,23.7000,13.2600,'2017-06-03',239),(240,23.7000,13.2600,'2017-06-03',240),(241,23.7000,13.2600,'2017-06-03',241),(242,23.7000,13.2600,'2017-06-03',242),(243,23.7000,13.2600,'2017-06-03',243),(244,77.4000,37.5000,'2017-06-03',244),(245,77.4000,37.0000,'2017-06-03',245),(246,77.4000,37.0000,'2017-06-03',246),(247,77.4000,37.5000,'2017-06-03',247),(248,71.6000,32.5000,'2017-06-03',248),(249,71.6000,32.5000,'2017-06-03',249),(250,71.6000,32.5000,'2017-06-03',250),(251,38.5000,15.1000,'2017-06-03',251),(252,38.5000,15.5000,'2017-06-03',252),(253,48.0000,16.0000,'2017-06-03',253),(254,48.0000,16.0000,'2017-06-03',254),(255,96.8500,34.8700,'2017-06-03',255),(256,86.8500,38.0000,'2017-06-03',256),(257,27.3000,5.7500,'2017-06-03',257),(258,27.3000,5.7500,'2017-06-03',258),(259,27.3000,5.7500,'2017-06-03',259),(260,27.3000,6.5000,'2017-06-03',260),(261,27.3000,6.5000,'2017-06-03',261),(262,27.3000,6.5000,'2017-06-03',262),(263,50.7000,14.5000,'2017-06-03',263),(264,50.7000,14.5000,'2017-06-03',264),(265,50.7000,14.5000,'2017-06-03',265),(266,50.7000,14.5000,'2017-06-03',266),(267,59.0000,21.0000,'2017-06-03',267),(268,59.0000,21.0000,'2017-06-03',268),(269,75.0000,35.8700,'2017-06-03',269),(270,75.0000,38.5700,'2017-06-03',270),(271,45.0000,19.0400,'2017-06-03',271),(272,45.0000,17.1200,'2017-06-03',272),(273,59.0000,21.0000,'2017-06-03',273),(274,59.0000,21.0000,'2017-06-03',274),(275,71.6000,32.5000,'2017-06-03',275),(276,54.0000,22.3000,'2017-06-03',276),(277,54.0000,22.3000,'2017-06-03',277),(278,54.0000,22.3000,'2017-06-03',278),(279,54.0000,21.5000,'2017-06-03',279),(280,54.0000,21.5000,'2017-06-03',280),(281,54.0000,21.5000,'2017-06-03',281),(282,79.7000,0.0000,'2017-06-03',282),(283,79.7000,0.0000,'2017-06-03',283),(284,79.7000,0.0000,'2017-06-03',284),(285,104.3000,25.0000,'2017-06-03',285),(286,104.3000,25.0000,'2017-06-03',286),(287,104.3000,25.0000,'2017-06-03',287),(288,95.2000,0.0000,'2017-06-03',288),(289,95.2000,0.0000,'2017-06-03',289),(290,95.2000,0.0000,'2017-06-03',290),(291,35.0000,17.0000,'2017-06-03',291),(292,35.0000,17.0000,'2017-06-03',292),(293,35.0000,17.0000,'2017-06-03',293),(294,35.0000,17.0000,'2017-06-03',294),(295,35.0000,17.0000,'2017-06-03',295),(296,60.0000,24.5000,'2017-06-03',296),(297,60.0000,24.5000,'2017-06-03',297),(298,60.0000,24.5000,'2017-06-03',298),(299,81.0000,31.0000,'2017-06-03',299),(300,81.0000,31.0000,'2017-06-03',300),(301,86.8500,38.0000,'2017-06-03',301),(302,86.8500,38.0000,'2017-06-03',302),(303,100.0000,43.0000,'2017-06-03',303),(304,100.0000,43.0000,'2017-06-03',304),(305,100.0000,43.0000,'2017-06-03',305),(306,100.0000,43.0000,'2017-06-03',306),(307,52.0000,16.5000,'2017-06-03',307),(308,52.0000,16.5000,'2017-06-03',308),(309,52.0000,16.5000,'2017-06-03',309),(310,52.0000,16.5000,'2017-06-03',310),(311,52.0000,20.0000,'2017-06-03',311),(312,52.0000,20.0000,'2017-06-03',312),(313,52.0000,20.0000,'2017-06-03',313),(314,52.0000,20.0000,'2017-06-03',314),(315,68.0000,0.0000,'2017-06-03',315),(316,68.0000,0.0000,'2017-06-03',316),(317,68.0000,0.0000,'2017-06-03',317),(318,68.0000,0.0000,'2017-06-03',318),(319,75.0000,38.0000,'2017-06-03',319),(320,75.0000,38.0000,'2017-06-03',320),(321,62.0000,21.0000,'2017-06-03',321),(322,89.4000,55.0000,'2017-06-03',322),(323,59.0000,24.5000,'2017-06-03',323),(324,59.0000,24.5000,'2017-06-03',324),(325,59.0000,24.5000,'2017-06-03',325),(326,59.0000,24.5000,'2017-06-03',326),(327,59.0000,24.5000,'2017-06-03',327),(328,59.0000,24.5000,'2017-06-03',328),(329,59.0000,24.5000,'2017-06-03',329),(330,59.0000,24.5000,'2017-06-03',330),(331,77.0000,26.0000,'2017-06-03',331),(332,77.0000,26.0000,'2017-06-03',332),(333,972.0000,372.0000,'2017-06-03',333),(334,972.0000,372.0000,'2017-06-03',334),(335,972.0000,372.0000,'2017-06-03',335),(336,972.0000,372.0000,'2017-06-03',336),(337,1042.2000,456.0000,'2017-06-03',337),(338,1042.2000,456.0000,'2017-06-03',338),(339,1042.2000,456.0000,'2017-06-03',339),(340,1042.2000,456.0000,'2017-06-03',340),(341,1162.2000,418.4400,'2017-06-03',341),(342,1162.2000,418.4400,'2017-06-03',342),(343,1042.2000,456.0000,'2017-06-03',343),(344,1042.2000,456.0000,'2017-06-03',344),(345,708.0000,252.0000,'2017-06-03',345),(346,708.0000,252.0000,'2017-06-03',346),(347,708.0000,252.0000,'2017-06-03',347),(348,708.0000,252.0000,'2017-06-03',348),(349,850.2000,153.0000,'2017-06-03',349),(350,850.2000,153.0000,'2017-06-03',350),(351,784.8000,147.0000,'2017-06-03',351),(352,784.8000,147.0000,'2017-06-03',352),(353,1987.2000,660.0000,'2017-06-03',353),(354,1987.2000,660.0000,'2017-06-03',354),(355,1411.2000,348.0000,'2017-06-03',355),(356,1411.2000,348.0000,'2017-06-03',356),(357,1987.2000,660.0000,'2017-06-03',357),(358,1987.2000,660.0000,'2017-06-03',358),(359,1216.8000,396.0000,'2017-06-03',359),(360,1216.8000,396.0000,'2017-06-03',360),(361,1216.8000,396.0000,'2017-06-03',361),(362,2145.6000,864.0000,'2017-06-03',362),(363,2145.6000,864.0000,'2017-06-03',363),(364,2145.6000,864.0000,'2017-06-03',364),(365,2145.6000,864.0000,'2017-06-03',365),(366,1425.6000,456.0000,'2017-06-03',366),(367,1425.6000,456.0000,'2017-06-03',367),(368,850.2000,135.0000,'2017-06-03',368),(369,784.8000,135.0000,'2017-06-03',369),(370,784.8000,135.0000,'2017-06-03',370),(371,1987.2000,660.0000,'2017-06-03',371),(372,1987.2000,660.0000,'2017-06-03',372),(373,1987.2000,660.0000,'2017-06-03',373),(374,1987.2000,660.0000,'2017-06-03',374),(375,1411.2000,348.0000,'2017-06-03',375),(376,1411.2000,348.0000,'2017-06-03',376),(377,4550.4000,1440.0000,'2017-06-03',377),(378,4550.4000,1440.0000,'2017-06-03',378),(379,4550.4000,1440.0000,'2017-06-03',379),(380,4550.4000,1440.0000,'2017-06-03',380),(381,2275.2000,792.0000,'2017-06-03',381),(382,2275.2000,792.0000,'2017-06-03',382),(383,2275.2000,792.0000,'2017-06-03',383),(384,2275.2000,792.0000,'2017-06-03',384),(385,4368.0000,2319.6600,'2017-06-03',385),(386,900.0000,428.1600,'2017-06-03',386),(387,900.0000,428.1600,'2017-06-03',387),(388,900.0000,428.1600,'2017-06-03',388),(389,2160.0000,810.2400,'2017-06-03',389),(390,2160.0000,833.2800,'2017-06-03',390),(391,2160.0000,833.2800,'2017-06-03',391),(392,2448.0000,1296.0000,'2017-06-03',392),(393,2448.0000,1440.0000,'2017-06-03',393),(394,2448.0000,1656.0000,'2017-06-03',394),(395,1512.0000,588.0000,'2017-06-03',395),(396,1512.0000,588.0000,'2017-06-03',396),(397,3400.3200,1980.4800,'2017-06-03',397),(398,3400.3200,1980.4800,'2017-06-03',398),(399,3400.3200,1980.4800,'2017-06-03',399),(400,3400.3200,1980.4800,'2017-06-03',400),(401,2160.0000,833.2800,'2017-06-03',401),(402,900.0000,428.1600,'2017-06-03',402),(403,2160.0000,833.2800,'2017-06-03',403),(404,900.0000,439.6800,'2017-06-03',404),(405,2275.2000,1272.9600,'2017-06-03',405),(406,2275.2000,1272.9600,'2017-06-03',406),(407,2275.2000,1272.9600,'2017-06-03',407),(408,2275.2000,1272.9600,'2017-06-03',408),(409,2275.2000,1272.9600,'2017-06-03',409),(410,928.8000,450.0000,'2017-06-03',410),(411,928.8000,444.0000,'2017-06-03',411),(412,928.8000,444.0000,'2017-06-03',412),(413,928.8000,450.0000,'2017-06-03',413),(414,859.2000,390.0000,'2017-06-03',414),(415,859.2000,390.0000,'2017-06-03',415),(416,859.2000,390.0000,'2017-06-03',416),(417,924.0000,362.4000,'2017-06-03',417),(418,924.0000,372.0000,'2017-06-03',418),(419,576.0000,192.0000,'2017-06-03',419),(420,576.0000,192.0000,'2017-06-03',420),(421,1162.2000,418.4400,'2017-06-03',421),(422,1042.2000,456.0000,'2017-06-03',422),(423,655.2000,138.0000,'2017-06-03',423),(424,655.2000,138.0000,'2017-06-03',424),(425,655.2000,138.0000,'2017-06-03',425),(426,655.2000,156.0000,'2017-06-03',426),(427,655.2000,156.0000,'2017-06-03',427),(428,655.2000,156.0000,'2017-06-03',428),(429,1216.8000,348.0000,'2017-06-03',429),(430,1216.8000,348.0000,'2017-06-03',430),(431,1216.8000,348.0000,'2017-06-03',431),(432,1216.8000,348.0000,'2017-06-03',432),(433,708.0000,252.0000,'2017-06-03',433),(434,708.0000,252.0000,'2017-06-03',434),(435,900.0000,430.4400,'2017-06-03',435),(436,900.0000,462.8400,'2017-06-03',436),(437,2160.0000,913.9200,'2017-06-03',437),(438,2160.0000,821.7600,'2017-06-03',438),(439,708.0000,252.0000,'2017-06-03',439),(440,708.0000,252.0000,'2017-06-03',440),(441,859.2000,390.0000,'2017-06-03',441),(442,1296.0000,535.2000,'2017-06-03',442),(443,1296.0000,535.2000,'2017-06-03',443),(444,1296.0000,535.2000,'2017-06-03',444),(445,648.0000,258.0000,'2017-06-03',445),(446,648.0000,258.0000,'2017-06-03',446),(447,648.0000,258.0000,'2017-06-03',447),(448,1912.8000,0.0000,'2017-06-03',448),(449,1912.8000,0.0000,'2017-06-03',449),(450,1912.8000,0.0000,'2017-06-03',450),(451,1251.6000,300.0000,'2017-06-03',451),(452,1251.6000,300.0000,'2017-06-03',452),(453,1251.6000,300.0000,'2017-06-03',453),(454,1142.4000,0.0000,'2017-06-03',454),(455,1142.4000,0.0000,'2017-06-03',455),(456,1142.4000,0.0000,'2017-06-03',456),(457,840.0000,408.0000,'2017-06-03',457),(458,840.0000,408.0000,'2017-06-03',458),(459,840.0000,408.0000,'2017-06-03',459),(460,840.0000,408.0000,'2017-06-03',460),(461,840.0000,408.0000,'2017-06-03',461),(462,1440.0000,588.0000,'2017-06-03',462),(463,1440.0000,588.0000,'2017-06-03',463),(464,1440.0000,588.0000,'2017-06-03',464),(465,972.0000,372.0000,'2017-06-03',465),(466,972.0000,372.0000,'2017-06-03',466),(467,1042.2000,456.0000,'2017-06-03',467),(468,1042.2000,456.0000,'2017-06-03',468),(469,1200.0000,516.0000,'2017-06-03',469),(470,1200.0000,516.0000,'2017-06-03',470),(471,1200.0000,516.0000,'2017-06-03',471),(472,1200.0000,516.0000,'2017-06-03',472),(473,624.0000,198.0000,'2017-06-03',473),(474,624.0000,198.0000,'2017-06-03',474),(475,624.0000,198.0000,'2017-06-03',475),(476,624.0000,198.0000,'2017-06-03',476),(477,624.0000,240.0000,'2017-06-03',477),(478,624.0000,240.0000,'2017-06-03',478),(479,624.0000,240.0000,'2017-06-03',479),(480,624.0000,240.0000,'2017-06-03',480),(481,816.0000,0.0000,'2017-06-03',481),(482,816.0000,0.0000,'2017-06-03',482),(483,816.0000,0.0000,'2017-06-03',483),(484,816.0000,0.0000,'2017-06-03',484),(485,900.0000,456.0000,'2017-06-03',485),(486,900.0000,456.0000,'2017-06-03',486),(487,744.0000,252.0000,'2017-06-03',487),(488,2145.6000,1320.0000,'2017-06-03',488),(489,2832.0000,1176.0000,'2017-06-03',489),(490,2832.0000,1176.0000,'2017-06-03',490),(491,2832.0000,1176.0000,'2017-06-03',491),(492,2832.0000,1176.0000,'2017-06-03',492),(493,2832.0000,1176.0000,'2017-06-03',493),(494,2832.0000,1176.0000,'2017-06-03',494),(495,1416.0000,588.0000,'2017-06-03',495),(496,1416.0000,588.0000,'2017-06-03',496),(497,924.0000,312.0000,'2017-06-03',497),(498,924.0000,312.0000,'2017-06-03',498),(499,81.0000,31.0000,'2017-06-03',499),(500,81.0000,31.0000,'2017-06-03',500),(501,81.0000,31.0000,'2017-06-03',501),(502,81.0000,31.0000,'2017-06-03',502),(503,86.8500,38.0000,'2017-06-03',503),(504,86.8500,38.0000,'2017-06-03',504),(505,86.8500,38.0000,'2017-06-03',505),(506,86.8500,38.0000,'2017-06-03',506),(507,96.8500,34.8700,'2017-06-03',507),(508,96.8500,34.8700,'2017-06-03',508),(509,86.8500,38.0000,'2017-06-03',509),(510,86.8500,38.0000,'2017-06-03',510),(511,59.0000,21.0000,'2017-06-03',511),(512,59.0000,21.0000,'2017-06-03',512),(513,59.0000,21.0000,'2017-06-03',513),(514,59.0000,21.0000,'2017-06-03',514),(515,70.8500,12.7500,'2017-06-03',515),(516,70.8500,12.7500,'2017-06-03',516),(517,65.4000,12.2500,'2017-06-03',517),(518,65.4000,12.2500,'2017-06-03',518),(519,41.4000,13.7500,'2017-06-03',519),(520,41.4000,13.7500,'2017-06-03',520),(521,29.4000,7.2500,'2017-06-03',521),(522,29.4000,7.2500,'2017-06-03',522),(523,41.4000,13.7500,'2017-06-03',523),(524,41.4000,13.7500,'2017-06-03',524),(525,50.7000,16.5000,'2017-06-03',525),(526,50.7000,16.5000,'2017-06-03',526),(527,50.7000,16.5000,'2017-06-03',527),(528,44.7000,18.0000,'2017-06-03',528),(529,44.7000,18.0000,'2017-06-03',529),(530,44.7000,18.0000,'2017-06-03',530),(531,44.7000,18.0000,'2017-06-03',531),(532,59.4000,19.0000,'2017-06-03',532),(533,59.4000,19.0000,'2017-06-03',533),(534,70.8500,11.2500,'2017-06-03',534),(535,65.4000,11.2500,'2017-06-03',535),(536,65.4000,11.2500,'2017-06-03',536),(537,41.4000,13.7500,'2017-06-03',537),(538,41.4000,13.7500,'2017-06-03',538),(539,41.4000,13.7500,'2017-06-03',539),(540,41.4000,13.7500,'2017-06-03',540),(541,29.4000,7.2500,'2017-06-03',541),(542,29.4000,7.2500,'2017-06-03',542),(543,47.4000,15.0000,'2017-06-03',543),(544,47.4000,15.0000,'2017-06-03',544),(545,47.4000,15.0000,'2017-06-03',545),(546,47.4000,15.0000,'2017-06-03',546),(547,23.7000,8.2500,'2017-06-03',547),(548,23.7000,8.2500,'2017-06-03',548),(549,23.7000,8.2500,'2017-06-03',549),(550,23.7000,8.2500,'2017-06-03',550),(551,45.4000,24.1100,'2017-06-03',551),(552,75.0000,35.6800,'2017-06-03',552),(553,75.0000,35.6800,'2017-06-03',553),(554,75.0000,35.6800,'2017-06-03',554),(555,45.0000,16.8800,'2017-06-03',555),(556,45.0000,17.3600,'2017-06-03',556),(557,45.0000,17.3600,'2017-06-03',557),(558,51.0000,27.0000,'2017-06-03',558),(559,51.0000,30.0000,'2017-06-03',559),(560,51.0000,34.5000,'2017-06-03',560),(561,63.0000,24.5000,'2017-06-03',561),(562,63.0000,24.5000,'2017-06-03',562),(563,35.4200,20.6300,'2017-06-03',563),(564,35.4200,20.6300,'2017-06-03',564),(565,35.4200,20.6300,'2017-06-03',565),(566,35.4200,20.6300,'2017-06-03',566),(567,45.0000,17.3600,'2017-06-03',567),(568,75.0000,35.6800,'2017-06-03',568),(569,45.0000,17.3600,'2017-06-03',569),(570,75.0000,36.6400,'2017-06-03',570),(571,23.7000,13.2600,'2017-06-03',571),(572,23.7000,13.2600,'2017-06-03',572),(573,23.7000,13.2600,'2017-06-03',573),(574,23.7000,13.2600,'2017-06-03',574),(575,23.7000,13.2600,'2017-06-03',575),(576,77.4000,37.5000,'2017-06-03',576),(577,77.4000,37.0000,'2017-06-03',577),(578,77.4000,37.0000,'2017-06-03',578),(579,77.4000,37.5000,'2017-06-03',579),(580,71.6000,32.5000,'2017-06-03',580),(581,71.6000,32.5000,'2017-06-03',581),(582,71.6000,32.5000,'2017-06-03',582),(583,38.5000,15.1000,'2017-06-03',583),(584,38.5000,15.5000,'2017-06-03',584),(585,48.0000,16.0000,'2017-06-03',585),(586,48.0000,16.0000,'2017-06-03',586),(587,96.8500,34.8700,'2017-06-03',587),(588,86.8500,38.0000,'2017-06-03',588),(589,27.3000,5.7500,'2017-06-03',589),(590,27.3000,5.7500,'2017-06-03',590),(591,27.3000,5.7500,'2017-06-03',591),(592,27.3000,6.5000,'2017-06-03',592),(593,27.3000,6.5000,'2017-06-03',593),(594,27.3000,6.5000,'2017-06-03',594),(595,50.7000,14.5000,'2017-06-03',595),(596,50.7000,14.5000,'2017-06-03',596),(597,50.7000,14.5000,'2017-06-03',597),(598,50.7000,14.5000,'2017-06-03',598),(599,59.0000,21.0000,'2017-06-03',599),(600,59.0000,21.0000,'2017-06-03',600),(601,75.0000,35.8700,'2017-06-03',601),(602,75.0000,38.5700,'2017-06-03',602),(603,45.0000,19.0400,'2017-06-03',603),(604,45.0000,17.1200,'2017-06-03',604),(605,59.0000,21.0000,'2017-06-03',605),(606,59.0000,21.0000,'2017-06-03',606),(607,71.6000,32.5000,'2017-06-03',607),(608,54.0000,22.3000,'2017-06-03',608),(609,54.0000,22.3000,'2017-06-03',609),(610,54.0000,22.3000,'2017-06-03',610),(611,54.0000,21.5000,'2017-06-03',611),(612,54.0000,21.5000,'2017-06-03',612),(613,54.0000,21.5000,'2017-06-03',613),(614,79.7000,0.0000,'2017-06-03',614),(615,79.7000,0.0000,'2017-06-03',615),(616,79.7000,0.0000,'2017-06-03',616),(617,104.3000,25.0000,'2017-06-03',617),(618,104.3000,25.0000,'2017-06-03',618),(619,104.3000,25.0000,'2017-06-03',619),(620,95.2000,0.0000,'2017-06-03',620),(621,95.2000,0.0000,'2017-06-03',621),(622,95.2000,0.0000,'2017-06-03',622),(623,35.0000,17.0000,'2017-06-03',623),(624,35.0000,17.0000,'2017-06-03',624),(625,35.0000,17.0000,'2017-06-03',625),(626,35.0000,17.0000,'2017-06-03',626),(627,35.0000,17.0000,'2017-06-03',627),(628,60.0000,24.5000,'2017-06-03',628),(629,60.0000,24.5000,'2017-06-03',629),(630,60.0000,24.5000,'2017-06-03',630),(631,81.0000,31.0000,'2017-06-03',631),(632,81.0000,31.0000,'2017-06-03',632),(633,86.8500,38.0000,'2017-06-03',633),(634,86.8500,38.0000,'2017-06-03',634),(635,100.0000,43.0000,'2017-06-03',635),(636,100.0000,43.0000,'2017-06-03',636),(637,100.0000,43.0000,'2017-06-03',637),(638,100.0000,43.0000,'2017-06-03',638),(639,52.0000,16.5000,'2017-06-03',639),(640,52.0000,16.5000,'2017-06-03',640),(641,52.0000,16.5000,'2017-06-03',641),(642,52.0000,16.5000,'2017-06-03',642),(643,52.0000,20.0000,'2017-06-03',643),(644,52.0000,20.0000,'2017-06-03',644),(645,52.0000,20.0000,'2017-06-03',645),(646,52.0000,20.0000,'2017-06-03',646),(647,68.0000,0.0000,'2017-06-03',647),(648,68.0000,0.0000,'2017-06-03',648),(649,68.0000,0.0000,'2017-06-03',649),(650,68.0000,0.0000,'2017-06-03',650),(651,75.0000,38.0000,'2017-06-03',651),(652,75.0000,38.0000,'2017-06-03',652),(653,62.0000,21.0000,'2017-06-03',653),(654,89.4000,55.0000,'2017-06-03',654),(655,59.0000,24.5000,'2017-06-03',655),(656,59.0000,24.5000,'2017-06-03',656),(657,59.0000,24.5000,'2017-06-03',657),(658,59.0000,24.5000,'2017-06-03',658),(659,59.0000,24.5000,'2017-06-03',659),(660,59.0000,24.5000,'2017-06-03',660),(661,59.0000,24.5000,'2017-06-03',661),(662,59.0000,24.5000,'2017-06-03',662),(663,77.0000,26.0000,'2017-06-03',663),(664,77.0000,26.0000,'2017-06-03',664),(665,0.0000,0.0000,'2017-06-24',665);
/*!40000 ALTER TABLE `prices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `print_invoice`
--

DROP TABLE IF EXISTS `print_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `print_invoice` (
  `idprint_invoice` int(11) NOT NULL AUTO_INCREMENT,
  `sales_invoice_id` int(11) NOT NULL,
  `status` enum('PRINT','CANCELLED') NOT NULL,
  `billing_id` int(11) NOT NULL,
  `prnt_dte` date NOT NULL,
  PRIMARY KEY (`idprint_invoice`),
  KEY `bi_id_invoice_fk_idx` (`billing_id`),
  CONSTRAINT `bi_id_invoice_fk` FOREIGN KEY (`billing_id`) REFERENCES `salesinvoices` (`idsalesinvoices`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `print_invoice`
--

LOCK TABLES `print_invoice` WRITE;
/*!40000 ALTER TABLE `print_invoice` DISABLE KEYS */;
INSERT INTO `print_invoice` VALUES (1,34234,'CANCELLED',300000001,'2017-06-07'),(2,34234,'CANCELLED',300000001,'2017-06-07'),(3,234234,'CANCELLED',300000001,'2017-06-07'),(4,213,'CANCELLED',300000001,'2017-06-07'),(5,34234,'CANCELLED',300000001,'2017-06-07'),(6,76476,'CANCELLED',300000001,'2017-06-07'),(7,4234,'PRINT',300000001,'2017-06-09'),(8,567,'PRINT',300000002,'2017-06-21'),(9,2453,'PRINT',300000003,'2017-06-24');
/*!40000 ALTER TABLE `print_invoice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchaseitems`
--

DROP TABLE IF EXISTS `purchaseitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchaseitems` (
  `idpurchaseitems` int(11) NOT NULL AUTO_INCREMENT,
  `inventory_id` int(11) NOT NULL,
  `po_qty` int(11) DEFAULT NULL,
  `amount` decimal(10,4) NOT NULL,
  `vat_amount` decimal(10,4) NOT NULL,
  `purchase_id` int(11) NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `unitprice` decimal(10,4) NOT NULL,
  `actual_qty` int(11) DEFAULT NULL,
  `batchnum` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idpurchaseitems`),
  KEY `poid_fk_idx` (`purchase_id`),
  KEY `inventory_id_fk2_idx` (`inventory_id`),
  CONSTRAINT `inventory_id_fk2` FOREIGN KEY (`inventory_id`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `poid_fk` FOREIGN KEY (`purchase_id`) REFERENCES `purchases` (`idpurchases`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchaseitems`
--

LOCK TABLES `purchaseitems` WRITE;
/*!40000 ALTER TABLE `purchaseitems` DISABLE KEYS */;
INSERT INTO `purchaseitems` VALUES (1,3,10,3720.0000,4166.4000,400000000,'OPEN','Y',372.0000,10,'sdfsdfsdfsdf'),(2,10,3,1255.3200,1405.9600,400000001,'DELETED','N',418.4400,NULL,NULL),(3,7,3,1368.0000,1532.1600,400000001,'OPEN','Y',456.0000,3,NULL);
/*!40000 ALTER TABLE `purchaseitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `purchases`
--

DROP TABLE IF EXISTS `purchases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `purchases` (
  `idpurchases` int(11) NOT NULL AUTO_INCREMENT,
  `sup_id` int(11) DEFAULT NULL,
  `sup_c_name` varchar(45) DEFAULT NULL,
  `po_dte` date DEFAULT NULL,
  `po_dr_dte` date DEFAULT NULL,
  `cby_id` int(11) DEFAULT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  `status` enum('open','cancelled','complete') NOT NULL,
  `prntstat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idpurchases`),
  KEY `sup_id_fk_idx` (`sup_id`),
  KEY `cby_po_id_fk_idx` (`cby_id`),
  CONSTRAINT `cby_po_id_fk` FOREIGN KEY (`cby_id`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `sup_id_fk` FOREIGN KEY (`sup_id`) REFERENCES `suppliers` (`idsuppliers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=400000002 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `purchases`
--

LOCK TABLES `purchases` WRITE;
/*!40000 ALTER TABLE `purchases` DISABLE KEYS */;
INSERT INTO `purchases` VALUES (400000000,1,'fsdfs','2017-06-06','2017-06-06',2,'Y','complete','N'),(400000001,1,'Marcus2','2017-06-15','2017-06-17',2,'Y','complete','Y');
/*!40000 ALTER TABLE `purchases` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returns`
--

DROP TABLE IF EXISTS `returns`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `returns` (
  `idreturns` int(11) NOT NULL AUTO_INCREMENT,
  `sku` varchar(100) DEFAULT NULL,
  `desc` varchar(100) DEFAULT NULL,
  `retuom` varchar(45) NOT NULL,
  `retwhs` varchar(45) NOT NULL,
  `soh` int(11) NOT NULL,
  PRIMARY KEY (`idreturns`),
  KEY `retuomfk_idx` (`retuom`),
  KEY `retwhsfk_idx` (`retwhs`),
  CONSTRAINT `retuomfk` FOREIGN KEY (`retuom`) REFERENCES `uom` (`uomcol`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `retwhsfk` FOREIGN KEY (`retwhs`) REFERENCES `warehouses` (`idwarehouses`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returns`
--

LOCK TABLES `returns` WRITE;
/*!40000 ALTER TABLE `returns` DISABLE KEYS */;
INSERT INTO `returns` VALUES (1,'324234','dgdfgdf','CS','BC',507);
/*!40000 ALTER TABLE `returns` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returns_adjustments`
--

DROP TABLE IF EXISTS `returns_adjustments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `returns_adjustments` (
  `idreturns_adjustments` int(11) NOT NULL AUTO_INCREMENT,
  `rs_dte` date DEFAULT NULL,
  `refnum` varchar(100) DEFAULT NULL,
  `description` varchar(300) DEFAULT NULL,
  `pgistat` varchar(45) NOT NULL,
  PRIMARY KEY (`idreturns_adjustments`)
) ENGINE=InnoDB AUTO_INCREMENT=500000002 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returns_adjustments`
--

LOCK TABLES `returns_adjustments` WRITE;
/*!40000 ALTER TABLE `returns_adjustments` DISABLE KEYS */;
INSERT INTO `returns_adjustments` VALUES (500000000,'2017-06-06','545345','shsdfhsf','Y'),(500000001,'2017-06-06','gfgdf','fsgdf','Y');
/*!40000 ALTER TABLE `returns_adjustments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `returns_adjustments_items`
--

DROP TABLE IF EXISTS `returns_adjustments_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `returns_adjustments_items` (
  `idretadtems` int(11) NOT NULL AUTO_INCREMENT,
  `return_id` int(11) NOT NULL,
  `stock_qty` int(11) NOT NULL,
  `mov` enum('INC','DEC') DEFAULT NULL,
  `status` enum('Y','N') DEFAULT NULL,
  `retadj_id` int(11) NOT NULL,
  `itemstatus` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idretadtems`),
  KEY `ret_id_fk_idx` (`return_id`),
  KEY `ret_adj_id_fk_idx` (`retadj_id`),
  CONSTRAINT `ret_adj_id_fk` FOREIGN KEY (`retadj_id`) REFERENCES `returns_adjustments` (`idreturns_adjustments`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `ret_id_fk` FOREIGN KEY (`return_id`) REFERENCES `returns` (`idreturns`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `returns_adjustments_items`
--

LOCK TABLES `returns_adjustments_items` WRITE;
/*!40000 ALTER TABLE `returns_adjustments_items` DISABLE KEYS */;
INSERT INTO `returns_adjustments_items` VALUES (1,1,2,'INC','Y',500000000,'OPEN'),(2,1,3,'DEC','Y',500000001,'OPEN');
/*!40000 ALTER TABLE `returns_adjustments_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesinvoiceitems`
--

DROP TABLE IF EXISTS `salesinvoiceitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesinvoiceitems` (
  `idsalesinvoiceitems` int(11) NOT NULL AUTO_INCREMENT,
  `si_id` int(11) NOT NULL,
  `dr_id` int(11) NOT NULL,
  `status` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idsalesinvoiceitems`),
  KEY `dr_idfk_idx` (`dr_id`),
  KEY `si_idfk_idx` (`si_id`),
  CONSTRAINT `dr_idfk` FOREIGN KEY (`dr_id`) REFERENCES `deliveryorders` (`iddeliveryorders`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `si_idfk` FOREIGN KEY (`si_id`) REFERENCES `salesinvoices` (`idsalesinvoices`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesinvoiceitems`
--

LOCK TABLES `salesinvoiceitems` WRITE;
/*!40000 ALTER TABLE `salesinvoiceitems` DISABLE KEYS */;
INSERT INTO `salesinvoiceitems` VALUES (1,300000000,200000000,'OPEN'),(2,300000001,200000001,'OPEN'),(3,300000002,200000002,'OPEN'),(4,300000003,200000003,'OPEN'),(5,300000004,200000004,'OPEN');
/*!40000 ALTER TABLE `salesinvoiceitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesinvoices`
--

DROP TABLE IF EXISTS `salesinvoices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesinvoices` (
  `idsalesinvoices` int(11) NOT NULL AUTO_INCREMENT,
  `customeridiv` int(11) NOT NULL,
  `soidinvc` int(11) NOT NULL,
  `remarks` varchar(300) DEFAULT NULL,
  `cby` int(11) NOT NULL,
  `status` enum('open','complete') DEFAULT NULL,
  `truckername` varchar(100) DEFAULT NULL,
  `drivername` varchar(100) DEFAULT NULL,
  `plateno` varchar(45) DEFAULT NULL,
  `sidte` date DEFAULT NULL,
  `printstat` enum('N','Y','REPRINTED') DEFAULT NULL,
  PRIMARY KEY (`idsalesinvoices`),
  KEY `custidsivfk_idx` (`customeridiv`),
  KEY `soidivfk_idx` (`soidinvc`),
  KEY `usridinvcfk_idx` (`cby`),
  CONSTRAINT `custidsivfk` FOREIGN KEY (`customeridiv`) REFERENCES `customers` (`idcustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `soidivfk` FOREIGN KEY (`soidinvc`) REFERENCES `salesorders` (`idsalesorder`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `usridinvcfk` FOREIGN KEY (`cby`) REFERENCES `users` (`idusers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=300000005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesinvoices`
--

LOCK TABLES `salesinvoices` WRITE;
/*!40000 ALTER TABLE `salesinvoices` DISABLE KEYS */;
INSERT INTO `salesinvoices` VALUES (300000000,1,100000000,'N/A',2,'open','N/A','N/A','N/A','2017-06-05','N'),(300000001,1,100000001,'N/A',2,'open','N/A','N/A','N/A','2017-06-05','Y'),(300000002,1,100000002,'N/A',2,'open','N/A','N/A','N/A','2017-06-07','Y'),(300000003,1,100000003,'N/A',2,'open','N/A','N/A','N/A','2017-06-24','Y'),(300000004,1,100000004,'N/A',2,'open','N/A','N/A','N/A','2017-06-24','N');
/*!40000 ALTER TABLE `salesinvoices` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesorderitems`
--

DROP TABLE IF EXISTS `salesorderitems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesorderitems` (
  `idsalesorderitem` int(11) NOT NULL AUTO_INCREMENT,
  `ordrqty` varchar(45) DEFAULT NULL,
  `salesorder_idsalesorder` int(11) NOT NULL,
  `inventory_idinventory` int(11) NOT NULL,
  `discnt` decimal(10,4) DEFAULT NULL,
  `unitprice` decimal(10,4) DEFAULT NULL,
  `amount` decimal(10,4) DEFAULT NULL,
  `vat` decimal(10,4) DEFAULT NULL,
  `itemstatus` enum('OPEN','COMPLETE','DELETED') DEFAULT NULL,
  PRIMARY KEY (`idsalesorderitem`,`salesorder_idsalesorder`,`inventory_idinventory`),
  KEY `fk_salesorderitems_inventory1_idx` (`inventory_idinventory`),
  KEY `fk_salesorders_parent_idx` (`salesorder_idsalesorder`),
  CONSTRAINT `fk_salesorderitems_inventory1` FOREIGN KEY (`inventory_idinventory`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_salesorders_parent` FOREIGN KEY (`salesorder_idsalesorder`) REFERENCES `salesorders` (`idsalesorder`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesorderitems`
--

LOCK TABLES `salesorderitems` WRITE;
/*!40000 ALTER TABLE `salesorderitems` DISABLE KEYS */;
INSERT INTO `salesorderitems` VALUES (1,'2',100000000,5,5.0000,1042.2000,2084.4000,2334.5300,'OPEN'),(2,'3',100000000,6,5.0000,1042.2000,3126.6000,3501.7900,'OPEN'),(3,'2',100000001,3,5.0000,972.0000,1944.0000,2177.2800,'OPEN'),(4,'3',100000001,9,5.0000,1162.2000,3486.6000,3904.9900,'DELETED'),(5,'4',100000001,7,5.0000,1042.2000,4168.8000,4669.0600,'DELETED'),(6,'2',100000001,6,5.0000,1042.2000,2084.4000,2334.5300,'OPEN'),(7,'3',100000001,11,5.0000,1042.2000,3126.6000,3501.7900,'OPEN'),(8,'2',100000002,3,5.0000,972.0000,1944.0000,2177.2800,'OPEN'),(9,'3',100000003,3,5.0000,972.0000,2916.0000,3265.9200,'OPEN'),(10,'4',100000003,4,5.0000,972.0000,3888.0000,4354.5600,'OPEN'),(11,'8',100000003,2,5.0000,972.0000,7776.0000,8709.1200,'OPEN'),(12,'11',100000004,328,5.0000,59.0000,649.0000,726.8800,'OPEN'),(13,'12',100000004,160,5.0000,2832.0000,33984.0000,38062.0800,'OPEN'),(14,'2',100000004,96,5.0000,655.2000,1310.4000,1467.6500,'OPEN');
/*!40000 ALTER TABLE `salesorderitems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `salesorders`
--

DROP TABLE IF EXISTS `salesorders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `salesorders` (
  `idsalesorder` int(11) NOT NULL AUTO_INCREMENT,
  `customerpo` varchar(45) DEFAULT NULL,
  `customer_idcustomer` int(11) NOT NULL,
  `sodate` date DEFAULT NULL,
  `sodeliverydate` date DEFAULT NULL,
  `status` enum('open','cancelled','complete','With DR','Partially Delivered') DEFAULT NULL,
  PRIMARY KEY (`idsalesorder`,`customer_idcustomer`),
  KEY `fk_salesorder_customer1_idx` (`customer_idcustomer`),
  CONSTRAINT `fk_salesorder_customer1` FOREIGN KEY (`customer_idcustomer`) REFERENCES `customers` (`idcustomer`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=100000005 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `salesorders`
--

LOCK TABLES `salesorders` WRITE;
/*!40000 ALTER TABLE `salesorders` DISABLE KEYS */;
INSERT INTO `salesorders` VALUES (100000000,'BC-24732',1,'2017-06-05','2017-06-07','complete'),(100000001,'BC-3247',1,'2017-06-05','2017-06-07','complete'),(100000002,'BC-2383',1,'2017-06-07','2017-06-07','complete'),(100000003,'BC-8261',1,'2017-06-24','2017-06-24','complete'),(100000004,'',1,'2017-06-24','2017-06-24','complete');
/*!40000 ALTER TABLE `salesorders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `st_items`
--

DROP TABLE IF EXISTS `st_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `st_items` (
  `idstitems` int(11) NOT NULL AUTO_INCREMENT,
  `st_idinvent` int(11) DEFAULT NULL,
  `invent_id_frm` int(11) DEFAULT NULL,
  `invent_qty` int(11) DEFAULT NULL,
  `invent_id_to` int(11) DEFAULT NULL,
  `st_status` enum('OPEN','DELETED') NOT NULL,
  PRIMARY KEY (`idstitems`),
  KEY `fk_invent1_id_idx` (`invent_id_frm`),
  KEY `fk_invent2_id_idx` (`invent_id_to`),
  KEY `fk_st_id_idx` (`st_idinvent`),
  CONSTRAINT `fk_invent1_id` FOREIGN KEY (`invent_id_frm`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_invent2_id` FOREIGN KEY (`invent_id_to`) REFERENCES `pm_inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_st_id` FOREIGN KEY (`st_idinvent`) REFERENCES `stocktransfers` (`idstocktransfers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `st_items`
--

LOCK TABLES `st_items` WRITE;
/*!40000 ALTER TABLE `st_items` DISABLE KEYS */;
INSERT INTO `st_items` VALUES (1,2,2,10,50,'OPEN'),(2,2,27,20,75,'OPEN');
/*!40000 ALTER TABLE `st_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `stocktransfers`
--

DROP TABLE IF EXISTS `stocktransfers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `stocktransfers` (
  `idstocktransfers` int(11) NOT NULL AUTO_INCREMENT,
  `st_dte` date DEFAULT NULL,
  `dr_dte` date DEFAULT NULL,
  `sldto` varchar(200) DEFAULT NULL,
  `attent` varchar(200) DEFAULT NULL,
  `refernum` varchar(100) DEFAULT NULL,
  `address` varchar(200) DEFAULT NULL,
  `ponum` varchar(200) DEFAULT NULL,
  `trucknme` varchar(100) DEFAULT NULL,
  `drvrnme` varchar(100) DEFAULT NULL,
  `pltno` varchar(100) DEFAULT NULL,
  `stdesc` varchar(200) DEFAULT NULL,
  `remarks` varchar(200) DEFAULT NULL,
  `st_stat` enum('OPEN','COMPLETE') NOT NULL,
  PRIMARY KEY (`idstocktransfers`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `stocktransfers`
--

LOCK TABLES `stocktransfers` WRITE;
/*!40000 ALTER TABLE `stocktransfers` DISABLE KEYS */;
INSERT INTO `stocktransfers` VALUES (2,'2017-06-09','2017-06-10','dsfs','sdf','sdf','sdf','sdf','sdf','sdf','sdf','sdf','sdf','COMPLETE');
/*!40000 ALTER TABLE `stocktransfers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `supcontacts`
--

DROP TABLE IF EXISTS `supcontacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `supcontacts` (
  `idsupcontacts` int(11) NOT NULL AUTO_INCREMENT,
  `supcname` varchar(45) DEFAULT NULL,
  `supccontact` varchar(45) DEFAULT NULL,
  `supcemail` varchar(45) DEFAULT NULL,
  `sup_id` int(11) NOT NULL,
  PRIMARY KEY (`idsupcontacts`,`sup_id`),
  KEY `fk_supplier_idx` (`sup_id`),
  CONSTRAINT `fk_supplier` FOREIGN KEY (`sup_id`) REFERENCES `suppliers` (`idsuppliers`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `supcontacts`
--

LOCK TABLES `supcontacts` WRITE;
/*!40000 ALTER TABLE `supcontacts` DISABLE KEYS */;
/*!40000 ALTER TABLE `supcontacts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `suppliers`
--

DROP TABLE IF EXISTS `suppliers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `suppliers` (
  `idsuppliers` int(11) NOT NULL AUTO_INCREMENT,
  `supname` varchar(200) DEFAULT NULL,
  `suptin` varchar(45) DEFAULT NULL,
  `supaddress` varchar(300) DEFAULT NULL,
  `supbusines` varchar(45) DEFAULT NULL,
  `suppayment` varchar(45) DEFAULT NULL,
  `postal` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idsuppliers`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `suppliers`
--

LOCK TABLES `suppliers` WRITE;
/*!40000 ALTER TABLE `suppliers` DISABLE KEYS */;
INSERT INTO `suppliers` VALUES (1,'SIEGRANZ Chemworks, Corp.','224-138-721-000','15 Sapphire St., DBP Farm Rd. DBP Farm Subd., Pulang Lupa 2, Las Pinas City','Manufacturing','COD','2021');
/*!40000 ALTER TABLE `suppliers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transmittal`
--

DROP TABLE IF EXISTS `transmittal`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `transmittal` (
  `idtransmittal` int(11) NOT NULL AUTO_INCREMENT,
  `supname` varchar(200) DEFAULT NULL,
  `conname` varchar(200) DEFAULT NULL,
  `gi_date` date DEFAULT NULL,
  `addres` varchar(255) DEFAULT NULL,
  `refnum` varchar(200) DEFAULT NULL,
  `gidesc` varchar(200) DEFAULT NULL,
  `grnum` int(11) DEFAULT NULL,
  `trn_type` enum('MANUAL','PGR') DEFAULT NULL,
  `pgistat` enum('N','Y') NOT NULL,
  PRIMARY KEY (`idtransmittal`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transmittal`
--

LOCK TABLES `transmittal` WRITE;
/*!40000 ALTER TABLE `transmittal` DISABLE KEYS */;
INSERT INTO `transmittal` VALUES (11,'sdfsdf','gdfgdfg','2017-06-27','dfgdfg','terger','dfgdfg',0,'MANUAL','N');
/*!40000 ALTER TABLE `transmittal` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tritems`
--

DROP TABLE IF EXISTS `tritems`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tritems` (
  `idtritems` int(11) NOT NULL AUTO_INCREMENT,
  `fginvent_id` int(11) DEFAULT NULL,
  `fginvent_sku` varchar(255) DEFAULT NULL,
  `fginvent_desc` varchar(255) DEFAULT NULL,
  `order_qty` int(11) DEFAULT NULL,
  `pm_sku` varchar(255) DEFAULT NULL,
  `pm_desc` varchar(255) DEFAULT NULL,
  `bom_wh` varchar(45) DEFAULT NULL,
  `unit_qty` int(11) DEFAULT NULL,
  `item_stat` enum('OPEN','DELETED') NOT NULL,
  `transmittal_id` int(11) DEFAULT NULL,
  `pm_invent_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`idtritems`),
  KEY `fk_fginvent_tritem_idx` (`fginvent_id`),
  KEY `fk_transmital_id_idx` (`transmittal_id`),
  CONSTRAINT `fk_fginvent_tritem` FOREIGN KEY (`fginvent_id`) REFERENCES `inventory` (`idinventory`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_transmital_id` FOREIGN KEY (`transmittal_id`) REFERENCES `transmittal` (`idtransmittal`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tritems`
--

LOCK TABLES `tritems` WRITE;
/*!40000 ALTER TABLE `tritems` DISABLE KEYS */;
INSERT INTO `tritems` VALUES (15,5,'10038961','B Treats Body Lotion Papaya Orange 850ml',28,'FSP2041S0118','20/410 PINK SPRAYER 118ML','SG',2660,'OPEN',11,8),(16,5,'10038961','B Treats Body Lotion Papaya Orange 850ml',28,'FSV2041S0118','20/410 VIOLET SPRAYER 118ML','SG',28,'OPEN',11,12),(17,1,'﻿10038957','B TREATS Body Wash Papaya Orange 850ml',5,'FSP2041S0118','20/410 PINK SPRAYER 118ML','SG',475,'DELETED',11,13),(18,1,'﻿10038957','B TREATS Body Wash Papaya Orange 850ml',5,'FSV2041S0118','20/410 VIOLET SPRAYER 118ML','SG',15,'DELETED',11,14),(19,1,'﻿10038957','B TREATS Body Wash Papaya Orange 850ml',5,'BCBWFTCC0400','28MM FLIPTOP CLEAR CAP 400ML','SG',15,'DELETED',11,15);
/*!40000 ALTER TABLE `tritems` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `uom`
--

DROP TABLE IF EXISTS `uom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `uom` (
  `uomcol` varchar(45) NOT NULL,
  PRIMARY KEY (`uomcol`),
  UNIQUE KEY `uomcol_UNIQUE` (`uomcol`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `uom`
--

LOCK TABLES `uom` WRITE;
/*!40000 ALTER TABLE `uom` DISABLE KEYS */;
INSERT INTO `uom` VALUES ('CS'),('PC');
/*!40000 ALTER TABLE `uom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_type`
--

DROP TABLE IF EXISTS `user_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_type` (
  `type_name` varchar(100) NOT NULL,
  PRIMARY KEY (`type_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_type`
--

LOCK TABLES `user_type` WRITE;
/*!40000 ALTER TABLE `user_type` DISABLE KEYS */;
INSERT INTO `user_type` VALUES ('ADMINISTRATOR'),('MATERIAL_MANAGER'),('SALES_MANAGER');
/*!40000 ALTER TABLE `user_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `idusers` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `usertype` varchar(100) NOT NULL,
  `name` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`idusers`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  KEY `usrtypefk_idx` (`usertype`),
  CONSTRAINT `utp` FOREIGN KEY (`usertype`) REFERENCES `user_type` (`type_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','jGl25bVBBBW96Qi9Te4V37Fnqchz/Eu4qB9vKrRIqRg=','ADMINISTRATOR','Ms. Carmen'),(2,'sales','TdWNMJyAjxiw/9yi2rURYZQZk92LY0dE7VDnUFXqQds=','SALES_MANAGER','Ma. Jeza Acostoy'),(3,'material','29JmJUCuIU2fWX9ruxLrfbMtwjBAkMM5r2YzHETgSIk=','MATERIAL_MANAGER','Virgie Castillo');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `warehouses`
--

DROP TABLE IF EXISTS `warehouses`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `warehouses` (
  `idwarehouses` varchar(45) NOT NULL,
  PRIMARY KEY (`idwarehouses`),
  UNIQUE KEY `idwarehouses_UNIQUE` (`idwarehouses`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `warehouses`
--

LOCK TABLES `warehouses` WRITE;
/*!40000 ALTER TABLE `warehouses` DISABLE KEYS */;
INSERT INTO `warehouses` VALUES ('BC'),('SG');
/*!40000 ALTER TABLE `warehouses` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'bestchem_db2'
--
/*!50003 DROP PROCEDURE IF EXISTS `ADD_UOM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ADD_UOM`(val VARCHAR(45))
BEGIN
	INSERT INTO `uom` (`uomcol`) VALUES (val);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BOM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `BOM_ADD`(fg_inventorysku INT, units_bom INT, pm_sku VARCHAR(100), pm_desc VARCHAR(100), pm_invent INT)
BEGIN
	INSERT INTO `bom` (`fg_inventorysku`, `units_bom`, `pm_sku`, `pm_desc`, `pm_invent`) 
    VALUES (fg_inventorysku, units_bom, pm_sku, pm_desc, pm_invent);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BOM_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `BOM_DELETE`(idbom INT)
BEGIN
	UPDATE `bestchem_db2`.`bom` SET `bom_stat`='DELETED' WHERE `idbom`= idbom;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BOM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `BOM_EDIT`(idbompm INT, units INT)
BEGIN
	UPDATE `bestchem_db2`.`bom` SET `units_bom`= units WHERE `idbom`= idbompm;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BOM_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `BOM_GET_ITEMS`(idinventfg INT)
BEGIN
	SELECT * FROM bestchem_db2.bom
    where bom_stat = 'OPEN' and fg_inventorysku = idinventfg;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `BOM_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `BOM_REPORT`()
BEGIN
	SELECT sku, skudesc, skuom, pm_sku, pm_desc, units_bom FROM bom 
	INNER JOIN inventory on inventory.idinventory = bom.fg_inventorysku 
    where bom.bom_stat = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CONTACT_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CONTACT_ADD`(custid INT(11), cname VARCHAR(100), cnum VARCHAR(100), email VARCHAR(100))
BEGIN
	INSERT INTO `contacts` (`idcustomer`, `name`, `number`, `email`) 
    VALUES (custid, cname, cnum, email);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CONTACT_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CONTACT_DELETE`(idcontact INT)
BEGIN
	DELETE FROM `contacts` WHERE `idcontacts`=idcontact;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CONTACT_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CONTACT_EDIT`(conid INT, custid INT(11), cname VARCHAR(100), cnum VARCHAR(100), email VARCHAR(100))
BEGIN
	UPDATE `contacts` SET `name`=cname, `number`=cnum, `email`=email 
    WHERE `idcontacts`=conid and`idcustomer`=custid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CONTACT_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CONTACT_GET`(custid INT(11))
BEGIN
	SELECT * FROM contacts where contacts.idcustomer = custid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CUSTOMER_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CUSTOMER_ADD`(compname VARCHAR(200), tin VARCHAR(45), bsstyle VARCHAR(100), address VARCHAR(200), ptterm VARCHAR(100), dscnt DECIMAL(10,4), uom VARCHAR(45), pstl INT(11), vndr VARCHAR(45), vat DECIMAL(10,4), ai ENUM('1', '0'))
BEGIN
	INSERT INTO `customers` (`name`, `tin`, `businessstyle`, `address`, `paymentterm`, `discount`, `uom_iduom`, `postal_cd`, `vendor_cd`, `vat_prcnt`, `auto_invice`) 
    VALUES (compname, tin, bsstyle, address, ptterm, dscnt, uom, pstl, vndr, vat, ai);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CUSTOMER_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CUSTOMER_EDIT`(idc INT, compname VARCHAR(60), tin VARCHAR(45), bsstyle VARCHAR(100), address VARCHAR(200), ptterm VARCHAR(100), dscnt DECIMAL(10,4), uom VARCHAR(45), pstl INT(11), vndr VARCHAR(45), vat DECIMAL(10,4), ai ENUM('1', '0'))
BEGIN
	UPDATE `customers` SET `name`=compname, `tin`=tin, `businessstyle`=bsstyle, `address`=address, `paymentterm`=ptterm, `discount`=dscnt, `uom_iduom`=uom, `postal_cd`=pstl, `vendor_cd`=vndr, `vat_prcnt`=vat, `auto_invice`=ai
    WHERE `idcustomer`=idc;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CUSTOMER_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CUSTOMER_GET`(company varchar(200))
BEGIN
	SELECT * FROM customers WHERE customers.name = company;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `CUSTOMER_GET_ALL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `CUSTOMER_GET_ALL`()
BEGIN
	SELECT * FROM CUSTOMERS ORDER BY name;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_ADD`(drdte DATE, cid INT, sid INT, rm VARCHAR(200), cby INT, drp ENUM('Y', 'N'), drs ENUM('open', 'complete'), drpgi ENUM('Y', 'N'))
BEGIN
	INSERT INTO `deliveryorders` (`drdate`, `customerid`, `soid`, `remarks`, `chckby`, `drprint`, `drstatus`, `drpgi`) 
    VALUES (drdte, cid, sid, rm, cby, drp, drs, drpgi);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_CHANGE_PGI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_CHANGE_PGI`(did INT, stat VARCHAR(45))
BEGIN
	UPDATE `deliveryorders` SET `drpgi`=stat WHERE `iddeliveryorders`=did;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_CHANGE_STATUS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_CHANGE_STATUS`(did INT, stat VARCHAR(45))
BEGIN
	UPDATE `deliveryorders` SET `drstatus`=stat WHERE `iddeliveryorders`=did;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_EDIT`(drdte DATE, rm VARCHAR(200), iddr INT)
BEGIN
	UPDATE `deliveryorders` SET `drdate`= drdte, `remarks`=rm 
    WHERE `iddeliveryorders`= iddr;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_ITEM_ADD`(dritm INT, drqt INT, dritmsi INT)
BEGIN
	INSERT INTO `deliveryorderitems` (`drorder`, `drqty`, `dritemsi`) 
    VALUES (dritm, drqt, dritmsi);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_ITEM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_ITEM_EDIT`(dritm INT, drqt INT)
BEGIN
	UPDATE `deliveryorderitems` SET `drqty`=drqt 
    WHERE `iddeliveryorderitems`=dritm;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_ITEM_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_ITEM_GET`(drid INT)
BEGIN
	SELECT iddeliveryorderitems, idsalesorderitem, ordrqty, idinventory, sku, skudesc, deliveryorderitems.drqty, skuom, SUM(drqty), ordrqty - SUM(drqty) AS 'Remaining_Quantity', soh FROM salesorderitems
		INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
		INNER JOIN deliveryorderitems on deliveryorderitems.dritemsi = salesorderitems.idsalesorderitem
		where deliveryorderitems.drorder = drid
		GROUP BY idsalesorderitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'iddeliveryorders', 'drdate', 'soid', 'idcustomer', 'name', 'paymentterm', 'sku', 'skudesc', 'drqty', 'skuom', 'unitprice', 'skuwh' 
	UNION ALL
	(SELECT iddeliveryorders, drdate, soid, idcustomer, customers.name, paymentterm, sku, skudesc, drqty, skuom, unitprice, skuwh from deliveryorders
		INNER JOIN deliveryorderitems on deliveryorders.iddeliveryorders = deliveryorderitems.drorder
		INNER JOIN salesorderitems on deliveryorderitems.dritemsi = salesorderitems.idsalesorderitem
		INNER JOIN customers on customers.idcustomer = deliveryorders.customerid
		INNER JOIN salesorders on salesorders.idsalesorder = deliveryorders.soid
		INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
        where drdate BETWEEN fromdte AND todte
		ORDER BY iddeliveryorders);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `DR_UPDATE_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `DR_UPDATE_ITEMS`(iddoid INT)
BEGIN
	UPDATE `deliveryorderitems` SET `pgistat`='Y' 
    WHERE `iddeliveryorderitems`=iddoid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `EDIT_UOM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `EDIT_UOM`(val VARCHAR(45), id VARCHAR(45))
BEGIN
	UPDATE `uom` SET `uomcol`= val WHERE `uomcol`= id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `EDIT_WHS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `EDIT_WHS`(val VARCHAR(45), id VARCHAR(45))
BEGIN
	UPDATE `warehouses` SET `idwarehouses`= val WHERE `idwarehouses`=id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GET_ALL_LINE_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `GET_ALL_LINE_ITEMS`(soid INT, custid INT)
BEGIN
	SELECT idinventory, sku, skudesc, SUM(drqty) AS 'sumdrqty', skuom, unitprice, discnt, SUM(drqty) * unitprice AS 'totamt', (SUM(drqty) * unitprice) * (1 + customers.vat_prcnt / 100) AS 'vatinc' FROM customers, salesorderitems
	INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
	INNER JOIN deliveryorders on deliveryorders.soid = salesorderitems.salesorder_idsalesorder
	INNER JOIN deliveryorderitems on salesorderitems.idsalesorderitem = deliveryorderitems.dritemsi
    where salesorderitems.salesorder_idsalesorder = soid AND customers.idcustomer = custid AND drpgi = 'Y' AND drstatus ='complete'
    GROUP BY idsalesorderitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GET_PRICE_INVENTORY` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `GET_PRICE_INVENTORY`()
BEGIN
	SELECT * FROM inventory 
	INNER JOIN prices ON prices.inventory_idinventory = inventory.idinventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GET_PRICE_INVENTORY_SKU` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `GET_PRICE_INVENTORY_SKU`(skuS VARCHAR(45))
BEGIN
	SELECT * FROM inventory 
	INNER JOIN prices ON prices.inventory_idinventory = inventory.idinventory
    where sku LIKE skuS;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `GET_UOM` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `GET_UOM`()
BEGIN
	SELECT * FROM uom;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_ADD`(sku VARCHAR(45), description VARCHAR(45), uom VARCHAR(45), whs VARCHAR(45), soh INT(11), csl INT(11))
BEGIN
	INSERT INTO `inventory` (`sku`, `skudesc`, `skuom`, `skuwh`, `soh`, `csl`) 
    VALUES (sku, description, uom, whs, soh, csl);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_ADJ_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_ADJ_ADD`(iadte DATE, refnum VARCHAR(100), description VARCHAR(300), pgistat ENUM('Y','N'))
BEGIN
	INSERT INTO `inventory_adjustments` (`iadte`, `refnum`, `description`, `pgistat`) 
    VALUES (iadte, refnum, description, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_ADJ_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_ADJ_ADD_ITEMS`(inventory_id INT, stock_qty INT, mov ENUM('DEC', 'INC'), status1 ENUM('Y', 'N'), ia_id INT)
BEGIN
	INSERT INTO `inventory_adjustment_items` (`inventory_id`, `stock_qty`, `mov`, `status`, `ia_id`) 
    VALUES (inventory_id, stock_qty, mov, status1, ia_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_ADJ_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_ADJ_EDIT`(iadte DATE, refnum VARCHAR(200), description VARCHAR(300), idadj INT)
BEGIN
	UPDATE `inventory_adjustments` SET `iadte`=iadte, `refnum`=refnum, `description`=description 
    WHERE `idadjustments`=idadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_ADJ_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_ADJ_GET_ITEMS`(iaid INT)
BEGIN
	SELECT * FROM inventory_adjustment_items
	INNER JOIN inventory on idinventory = inventory_id
	where ia_id = iaid and iastat = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_CRITICAL_STOCK_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_CRITICAL_STOCK_REPORT`()
BEGIN
	SELECT 'sku', 'skudesc','skuom','skuwh','soh', 'csl', 'stock needed'

	UNION ALL

	SELECT sku, skudesc, skuom, skuwh, soh, csl, csl - soh AS 'stock needed' FROM inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_DTLD_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_DTLD_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idinventory_transactions', 'transac_time', 'doc_type', 'client_id', 'client_name', 'client_type', 'doc_it', 'ref_doc', 'doc_remark', 'sku', 'skudesc', 'skuom', 'skuwh', 'qty_adj', 'effect'
	UNION ALL
	SELECT idinventory_transactions, transac_time, doc_type, client_id, client_name, client_type, doc_it, ref_doc, doc_remark, sku, skudesc, skuom, skuwh, qty_adj, effect FROM inventory_transactions
		INNER JOIN inventory on inventory.idinventory = inventory_transactions.item
        where transac_time BETWEEN fromdte AND todte;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_EDIT`(sku VARCHAR(45), description VARCHAR(45), uom VARCHAR(45), whs VARCHAR(45), csl INT, iid INT, units INT, inv_type VARCHAR(45))
BEGIN
	UPDATE `inventory` SET `sku`=sku, `skudesc`=description, `skuom`=uom, `skuwh`=whs, `csl`= csl, `units` = units, `inv_type` = inv_type
    WHERE `idinventory`=iid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_GET`()
BEGIN

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_GET_ALL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_GET_ALL`()
BEGIN
	SELECT * FROM inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_PRICE_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_PRICE_ADD`(sku VARCHAR(45), description VARCHAR(45), uom VARCHAR(45), whs VARCHAR(45), soh INT(11), csl INT(11), selp DECIMAL(10,4), pop DECIMAL(10,4), effDte DATE, units INT, inv_type VARCHAR(45))
BEGIN
	INSERT INTO `bestchem_db2`.`inventory` (`sku`, `skudesc`, `skuom`, `skuwh`, `soh`, `csl`, `units`, `inv_type`) 
    VALUES (sku, description, uom, whs, soh, csl, units, inv_type);
    
    INSERT INTO `bestchem_db2`.`prices` (`sellingPrice`, `poPrice`, `effectivedte`, `inventory_idinventory`) 
    VALUES (selp, pop, effDte, (SELECT LAST_INSERT_ID()));
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_PRICE_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_PRICE_GET`(ivid INT)
BEGIN
	SELECT idinventory, inventory.sku, skudesc, skuom, skuwh, sellingPrice, poPrice, B.maxdte, units from inventory 
	INNER JOIN prices A on A.inventory_idinventory = idinventory
	INNER JOIN (SELECT inventory_idinventory, max(effectivedte) maxdte FROM prices where effectivedte <= curdate() group by inventory_idinventory) B
	ON A.inventory_idinventory = B.inventory_idinventory AND A.effectivedte = B.maxdte
	where idinventory = ivid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_SUMMARY_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_SUMMARY_REPORT`()
BEGIN
	SELECT 'sku', 'skudesc','skuom','skuwh','soh'

	UNION ALL

	SELECT sku, skudesc, skuom, skuwh, soh FROM inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `INVENTORY_TRANSACT_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `INVENTORY_TRANSACT_ADD`(username VARCHAR(100), item INT, qty_adj INT, effect ENUM('INC','DEC'), doc_type VARCHAR(100), client_id INT, client_name VARCHAR(200), client_type VARCHAR(100), doc_it INT, ref_doc VARCHAR(200), doc_remark VARCHAR(500))
BEGIN
	INSERT INTO `bestchem_db2`.`inventory_transactions` (`username`, `transac_time`, `item`, `qty_adj`, `effect`, `doc_type`, `client_id`, `client_name`, `client_type`, `doc_it`, `ref_doc`, `doc_remark`) 
    VALUES (username, NOW(), item, qty_adj, effect, doc_type, client_id, client_name, client_type, doc_it, ref_doc, doc_remark);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_ADD`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), cby INT)
BEGIN
	INSERT INTO `manual_goods_issue` (`mgidte`, `custnme`, `contnme`, `address`, `description`, `renum`, `attention`, `cby`) 
    VALUES (mgidte, custnme, contnme, address, description, renum, attention, cby);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_EDIT`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), mgiid INT)
BEGIN
	UPDATE `manual_goods_issue` SET `mgidte` = mgidte, `custnme`=custnme, `contnme`=contnme, `address`=address, `description`=description, `renum`=renum, `attention`=attention 
    WHERE `idmgi`=mgiid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_ITEM_ADD`(mgi_id INT, mgi_invent_id INT, valdec INT)
BEGIN
	INSERT INTO `mgi_items` (`mgi_id`, `mgi_invent_id`, `valdec`) 
    VALUES (mgi_id, mgi_invent_id, valdec);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_ITEM_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_ITEM_DELETE`(idmgi_items INT, mgi_invent_id INT, mgi_id INT)
BEGIN
	UPDATE `mgi_items` SET `status`='DELETED'
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_ITEM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_ITEM_EDIT`(idmgi_items INT, mgi_invent_id INT, mgi_id INT, valdec INT)
BEGIN
	UPDATE `mgi_items` SET `valdec`=valdec 
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGI_ITEM_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGI_ITEM_GET`(idmgitem INT)
BEGIN
	SELECT * FROM mgi_items 
	
    INNER JOIN inventory on inventory.idinventory = mgi_items.mgi_invent_id
    
    where mgi_id = idmgitem and mgi_items.status = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_ADD`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), cby INT)
BEGIN
	INSERT INTO `manual_goods_receipt` (`mgidte`, `custnme`, `contnme`, `address`, `description`, `renum`, `attention`, `cby`) 
    VALUES (mgidte, custnme, contnme, address, description, renum, attention, cby);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_EDIT`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), mgiid INT)
BEGIN
	UPDATE `manual_goods_receipt` SET `mgidte` = mgidte, `custnme`=custnme, `contnme`=contnme, `address`=address, `description`=description, `renum`=renum, `attention`=attention 
    WHERE `idmgi`=mgiid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_ITEM_ADD`(mgi_id INT, mgi_invent_id INT, valdec INT)
BEGIN
	INSERT INTO `mgr_items` (`mgi_id`, `mgi_invent_id`, `valdec`) 
    VALUES (mgi_id, mgi_invent_id, valdec);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_ITEM_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_ITEM_DELETE`(idmgi_items INT, mgi_invent_id INT, mgi_id INT)
BEGIN
	UPDATE `mgr_items` SET `status`='DELETED'
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_ITEM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_ITEM_EDIT`(idmgi_items INT, mgi_invent_id INT, mgi_id INT, valdec INT)
BEGIN
	UPDATE `mgr_items` SET `valdec`=valdec 
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MGR_ITEM_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MGR_ITEM_GET`(idmgitem INT)
BEGIN
	SELECT * FROM mgr_items 
	
    INNER JOIN pm_inventory on pm_inventory.idinventory = mgr_items.mgi_invent_id
    
    where mgi_id = idmgitem and mgr_items.status = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_ADJ_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_ADJ_ADD`(iadte DATE, refnum VARCHAR(100), description VARCHAR(300), pgistat ENUM('Y','N'))
BEGIN
  INSERT INTO `mm_inventory_adjustments` (`iadte`, `refnum`, `description`, `pgistat`) 
    VALUES (iadte, refnum, description, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_ADJ_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_ADJ_ADD_ITEMS`(inventory_id INT, stock_qty INT, mov ENUM('DEC', 'INC'), status1 ENUM('Y', 'N'), ia_id INT)
BEGIN
  INSERT INTO `mm_inventory_adjustment_items` (`inventory_id`, `stock_qty`, `mov`, `status`, `ia_id`) 
    VALUES (inventory_id, stock_qty, mov, status1, ia_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_ADJ_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_ADJ_EDIT`(iadte DATE, refnum VARCHAR(200), description VARCHAR(300), idadj INT)
BEGIN
  UPDATE `mm_inventory_adjustments` SET `iadte`=iadte, `refnum`=refnum, `description`=description 
    WHERE `idadjustments`=idadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_ADJ_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_ADJ_GET_ITEMS`(iaid INT)
BEGIN
  SELECT * FROM mm_inventory_adjustment_items
  INNER JOIN pm_inventory on idinventory = inventory_id
  where ia_id = iaid and iastat = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_CRITICAL_STOCK_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_CRITICAL_STOCK_REPORT`()
BEGIN
	SELECT 'sku', 'skudesc','skuom','skuwh','soh', 'csl', 'stock needed'

	UNION ALL

	SELECT sku, skudesc, skuom, skuwh, soh, csl, csl - soh AS 'stock needed' FROM pm_inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_DTLD_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_DTLD_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idinventory_transactions', 'transac_time', 'doc_type', 'client_id', 'client_name', 'client_type', 'doc_it', 'ref_doc', 'doc_remark', 'sku', 'skudesc', 'skuom', 'skuwh', 'qty_adj', 'effect'
	UNION ALL
	SELECT idinventory_transactions, transac_time, doc_type, client_id, client_name, client_type, doc_it, ref_doc, doc_remark, sku, skudesc, skuom, skuwh, qty_adj, effect FROM mm_inventory_transactions
		INNER JOIN pm_inventory on pm_inventory.idinventory = mm_inventory_transactions.item
        where transac_time BETWEEN fromdte AND todte;
        
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_EDIT`(sku VARCHAR(45), description VARCHAR(45), uom VARCHAR(45), whs VARCHAR(45), csl INT, iid INT, units INT, inv_type VARCHAR(45))
BEGIN
	UPDATE `pm_inventory` SET `sku`=sku, `skudesc`=description, `skuom`=uom, `skuwh`=whs, `csl`= csl, `units` = units, `inv_type` = inv_type
    WHERE `idinventory`=iid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_GET_ALL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_GET_ALL`()
BEGIN
	SELECT * FROM pm_inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_PRICE_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_PRICE_ADD`(sku VARCHAR(45), description VARCHAR(45), uom VARCHAR(45), whs VARCHAR(45), soh INT(11), csl INT(11), selp DECIMAL(10,4), pop DECIMAL(10,4), effDte DATE, units INT, inv_type VARCHAR(45))
BEGIN
	INSERT INTO `bestchem_db2`.`pm_inventory` (`sku`, `skudesc`, `skuom`, `skuwh`, `soh`, `csl`, `units`, `inv_type`) 
    VALUES (sku, description, uom, whs, soh, csl, units, inv_type);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_SUMMARY_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_SUMMARY_REPORT`()
BEGIN
	SELECT 'sku', 'skudesc','skuom','skuwh','soh'

	UNION ALL

	SELECT sku, skudesc, skuom, skuwh, soh FROM pm_inventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_INVENTORY_TRANSACT_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_INVENTORY_TRANSACT_ADD`(username VARCHAR(100), item INT, qty_adj INT, effect ENUM('INC','DEC'), doc_type VARCHAR(100), client_id INT, client_name VARCHAR(200), client_type VARCHAR(100), doc_it INT, ref_doc VARCHAR(200), doc_remark VARCHAR(500))
BEGIN
  INSERT INTO `bestchem_db2`.`mm_inventory_transactions` (`username`, `transac_time`, `item`, `qty_adj`, `effect`, `doc_type`, `client_id`, `client_name`, `client_type`, `doc_it`, `ref_doc`, `doc_remark`) 
    VALUES (username, NOW(), item, qty_adj, effect, doc_type, client_id, client_name, client_type, doc_it, ref_doc, doc_remark);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_ADD`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), cby INT)
BEGIN
  INSERT INTO `mm_manual_goods_issue` (`mgidte`, `custnme`, `contnme`, `address`, `description`, `renum`, `attention`, `cby`) 
    VALUES (mgidte, custnme, contnme, address, description, renum, attention, cby);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_EDIT`(mgidte DATE, custnme VARCHAR(100), contnme VARCHAR(100), address VARCHAR(100), description VARCHAR(300), renum VARCHAR(200), attention VARCHAR(100), mgiid INT)
BEGIN
  UPDATE `mm_manual_goods_issue` SET `mgidte` = mgidte, `custnme`=custnme, `contnme`=contnme, `address`=address, `description`=description, `renum`=renum, `attention`=attention 
    WHERE `idmgi`=mgiid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_ITEM_ADD`(mgi_id INT, mgi_invent_id INT, valdec INT)
BEGIN
  INSERT INTO `mm_mgi_items` (`mgi_id`, `mgi_invent_id`, `valdec`) 
    VALUES (mgi_id, mgi_invent_id, valdec);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_ITEM_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_ITEM_DELETE`(idmgi_items INT, mgi_invent_id INT, mgi_id INT)
BEGIN
  UPDATE `mm_mgi_items` SET `status`='DELETED'
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_ITEM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_ITEM_EDIT`(idmgi_items INT, mgi_invent_id INT, mgi_id INT, valdec INT)
BEGIN
  UPDATE `mm_mgi_items` SET `valdec`=valdec 
    WHERE `idmgi_items`=idmgi_items and `mgi_invent_id` = mgi_invent_id and `mgi_id` = mgi_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_MGI_ITEM_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_MGI_ITEM_GET`(idmgitem INT)
BEGIN
  SELECT * FROM mm_mgi_items 
  
    INNER JOIN pm_inventory on pm_inventory.idinventory = mm_mgi_items.mgi_invent_id
    
    where mgi_id = idmgitem and mm_mgi_items.status = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PO_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PO_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idpurchases', 'po_dte', 'po_dr_dte', 'idsuppliers', 'supname', 'suppayment', 'sku', 'skudesc', 'po_qty', 'skuom', 'unitprice', 'actual_qty', 'GR ID', 'GR Date'

	UNION ALL

	(SELECT idpurchases, po_dte, po_dr_dte, idsuppliers, supname, suppayment, sku, skudesc, po_qty, skuom, unitprice, actual_qty, idpgr, pgr_dtetme from mm_purchases
		INNER JOIN mm_suppliers on mm_suppliers.idsuppliers = mm_purchases.sup_id
		INNER JOIN mm_purchaseitems on mm_purchaseitems.purchase_id = mm_purchases.idpurchases
		INNER JOIN pm_inventory on pm_inventory.idinventory = mm_purchaseitems.inventory_id
        INNER JOIN mm_pgr on mm_pgr.idpo_doc = mm_purchases.idpurchases
        where mm_purchases.pgistat = 'Y' and mm_purchaseitems.status = 'OPEN' AND po_dte BETWEEN fromdte AND todte
		ORDER BY mm_purchases.idpurchases, mm_purchases.po_dte);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PRG_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PRG_ADD`(pgrdoc INT)
BEGIN
	INSERT INTO `mm_pgr` (`idpo_doc`, `pgr_dtetme`) VALUES (pgrdoc, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PRICES_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PRICES_EDIT`(pid INT, selp DECIMAL(10,4), pop DECIMAL(10,4), effDte DATE, id INT(11))
BEGIN
	UPDATE `mm_prices` SET `sellingPrice`='0.0', `poPrice`=pop, `effectivedte`=effDte 
    WHERE `idPrices`=pid and`inventory_idinventory`=id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PRICES_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PRICES_GET`(supplier_id INT)
BEGIN
	SELECT * FROM bestchem_db2.price_list
	INNER JOIN mm_prices on mm_prices_idPrices = mm_prices.idPrices
    INNER JOIN pm_inventory on pm_inventory.idinventory = mm_prices_inventory_idinventory
    where mm_suppliers_idsuppliers = supplier_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PURCHASE_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PURCHASE_ADD`(sup_id INT, sup_c_name VARCHAR(45), po_dte DATE, po_dr_dte DATE, cby_id INT, pgistat ENUM('Y', 'N'))
BEGIN
	INSERT INTO `mm_purchases` (`sup_id`, `sup_c_name`, `po_dte`, `po_dr_dte`, `cby_id`, `pgistat`) 
    VALUES (sup_id, sup_c_name, po_dte, po_dr_dte, cby_id, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PURCHASE_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PURCHASE_ADD_ITEMS`(inventory_id INT, po_qty INT, unitprice DECIMAL(10,4), amount DECIMAL(10,4), vat_amount DECIMAL(10,4), purchase_id INT)
BEGIN
	INSERT INTO `mm_purchaseitems` (`inventory_id`, `po_qty`, `unitprice`, `amount`, `vat_amount`, `purchase_id`) 
    VALUES (inventory_id, po_qty, unitprice, amount, vat_amount, purchase_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PURCHASE_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PURCHASE_EDIT`(sup_c_name VARCHAR(45), po_dte DATE, po_dr_dte DATE, idpur INT)
BEGIN
	UPDATE `mm_purchases` SET `sup_c_name`=sup_c_name, `po_dte`=po_dte, `po_dr_dte`=po_dr_dte 
    WHERE `idpurchases`= idpur;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_PURCHASE_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_PURCHASE_GET_ITEMS`(purch_id INT)
BEGIN
	SELECT * from mm_purchaseitems
	INNER JOIN pm_inventory on pm_inventory.idinventory = mm_purchaseitems.inventory_id
	where `purchase_id` = purch_id and `status` = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADD`(sku VARCHAR(100), desc1 VARCHAR(100), retuom VARCHAR(45), retwhs VARCHAR(45), soh INT)
BEGIN
  INSERT INTO `mm_returns` (`sku`, `desc`, `retuom`, `retwhs`, `soh`) 
    VALUES (sku, desc1, retuom, retwhs, soh);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_ADD`(rs_dte DATE, refnum VARCHAR(100), description VARCHAR(300), pgistat ENUM('Y','N'))
BEGIN
  INSERT INTO `mm_returns_adjustments` (`rs_dte`, `refnum`, `description`, `pgistat`) 
    VALUES (rs_dte, refnum, description, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_ADD_ITEMS`(return_id INT, stock_qty INT, mov ENUM('INC', 'DEC'), status1 ENUM('Y', 'N'), retadj_id INT)
BEGIN
  INSERT INTO `mm_returns_adjustments_items` (`return_id`, `stock_qty`, `mov`, `status`, `retadj_id`) 
    VALUES (return_id, stock_qty, mov, status1, retadj_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_EDIT`(rs_dte DATE, refnum VARCHAR(100), description VARCHAR(300), idretadj INT)
BEGIN
  UPDATE `mm_returns_adjustments` SET `rs_dte`=rs_dte, `refnum`=refnum, `description`=description 
    WHERE `idreturns_adjustments`=idretadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_EDIT_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_EDIT_ITEMS`(idretitem INT)
BEGIN
  UPDATE `mm_returns_adjustments_items` SET `itemstatus`='DELETED' 
    WHERE `idretadtems`=idretitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_GET_ITEMS`(retid INT)
BEGIN
  SELECT * FROM mm_returns_adjustments_items 
  
    INNER JOIN mm_returns on mm_returns.idreturns = mm_returns_adjustments_items.return_id
    
  where retadj_id = retid and itemstatus = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_PGI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_PGI`(idretam INT)
BEGIN
  UPDATE `mm_returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_ADJ_PGI_STAT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_ADJ_PGI_STAT`(idretadj INT)
BEGIN
  UPDATE `mm_returns_adjustments` SET `pgistat`='Y'
    WHERE `idreturns_adjustments`= idretadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_EDIT`(sku VARCHAR(100), desc1 VARCHAR(100), retuom VARCHAR(45), retwhs VARCHAR(45), retid INT)
BEGIN
  UPDATE `mm_returns` SET `sku`=sku, `desc`= desc1, `retuom`=retuom, `retwhs`= retwhs 
    WHERE `idreturns`= retid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_RETURNS_SUMMARY_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_RETURNS_SUMMARY_REPORT`()
BEGIN
	SELECT 'sku', 'desc','retuom','retwhs','soh'

	UNION ALL

	SELECT sku, `desc` AS 'description', retuom, retwhs, soh FROM mm_returns;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SR_DTLD_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SR_DTLD_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'ID Return Adjustment', 'RS DATE', 'Reference Number', 'sku', 'description', 'uom', 'whs', 'Stock Qty', 'Effect'

	UNION ALL 

	(SELECT idreturns_adjustments, rs_dte, refnum, sku, `desc`, retuom, retwhs, stock_qty, mov FROM mm_returns_adjustments
		INNER JOIN mm_returns_adjustments_items on mm_returns_adjustments.idreturns_adjustments = mm_returns_adjustments_items.retadj_id
		INNER JOIN mm_returns on mm_returns.idreturns = mm_returns_adjustments_items.return_id
        where rs_dte BETWEEN fromdte AND todte);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SUPPLIER_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SUPPLIER_ADD`(supname VARCHAR(200), suptin VARCHAR(45), supaddrs VARCHAR(300), supbsns VARCHAR(100), suppymnt VARCHAR(100), postal VARCHAR(45))
BEGIN
  INSERT INTO `mm_suppliers` (`supname`, `suptin`, `supaddress`, `supbusines`, `suppayment`, `postal`) 
    VALUES (supname, suptin, supaddrs, supbsns, suppymnt, postal);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SUPPLIER_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SUPPLIER_EDIT`(supname VARCHAR(60), suptin VARCHAR(45), supaddrs VARCHAR(100), supbsns VARCHAR(100), suppymnt VARCHAR(100), postal VARCHAR(45), sup_id INT)
BEGIN
  UPDATE `mm_suppliers` SET `supname` = supname, `suptin`= suptin, `supaddress`=supaddrs, `supbusines`=supbsns, `suppayment`=suppymnt, `postal`=postal 
    WHERE `idsuppliers`=sup_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SUP_CONTACT_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SUP_CONTACT_ADD`(supcname VARCHAR(45), supccontact VARCHAR(45), supcemail VARCHAR(100), sup_id INT)
BEGIN
  INSERT INTO `mm_supcontacts` (`supcname`, `supccontact`, `supcemail`, `sup_id`) 
    VALUES (supcname, supccontact, supcemail, sup_id);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SUP_CONTACT_DEL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SUP_CONTACT_DEL`(supconid INT)
BEGIN
  DELETE FROM `mm_supcontacts` 
    WHERE `idsupcontacts`=supconid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_SUP_CONTACT_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_SUP_CONTACT_EDIT`(supcname VARCHAR(45), supccontact VARCHAR(45), supcemail VARCHAR(45), idsupcon INT)
BEGIN
  UPDATE `mm_supcontacts` SET `supcname`=supcname, `supccontact`=supccontact, `supcemail`=supcemail 
    WHERE `idsupcontacts`=idsupcon;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_UPDATE_INVENTORY` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_UPDATE_INVENTORY`(idinvent INT, subval INT)
BEGIN
	SELECT soh from pm_inventory where idinventory != idinvent FOR UPDATE;
	UPDATE pm_inventory set soh = (soh - subval) where idinventory = idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_UPDATE_INVENTORY_INC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_UPDATE_INVENTORY_INC`(idinvent INT, subval INT)
BEGIN
	SELECT soh from pm_inventory where idinventory != idinvent FOR UPDATE;
	UPDATE pm_inventory set soh = (soh + subval) where idinventory = idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_UPDATE_RETURNS_DEC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_UPDATE_RETURNS_DEC`(idretam INT, idret INT, subval INT)
BEGIN
  UPDATE `mm_returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;

  SELECT soh from mm_returns where idreturns != idret FOR UPDATE;
  UPDATE mm_returns set soh = (soh - subval) where idreturns = idret;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `MM_UPDATE_RETURNS_INC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `MM_UPDATE_RETURNS_INC`(idretam INT, idret INT, subval INT)
BEGIN
  UPDATE `mm_returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;

  SELECT soh from mm_returns where idreturns != idret FOR UPDATE;
  UPDATE mm_returns set soh = (soh + subval) where idreturns = idret;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PO_PRICES_GET_BY_SUP` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PO_PRICES_GET_BY_SUP`(supid INT)
BEGIN
	CREATE TEMPORARY TABLE IF NOT EXISTS sup_price AS 
		(SELECT * FROM bestchem_db2.price_list 
			INNER JOIN mm_prices on mm_prices.idPrices = price_list.mm_prices_idPrices 
			INNER JOIN mm_suppliers on mm_suppliers.idsuppliers = price_list.mm_suppliers_idsuppliers
			INNER JOIN pm_inventory on pm_inventory.idinventory = price_list.mm_prices_inventory_idinventory
			where mm_suppliers_idsuppliers = supid);

	CREATE TEMPORARY TABLE IF NOT EXISTS sup_price2 AS 
			(SELECT * FROM bestchem_db2.price_list 
				INNER JOIN mm_prices on mm_prices.idPrices = price_list.mm_prices_idPrices 
				INNER JOIN mm_suppliers on mm_suppliers.idsuppliers = price_list.mm_suppliers_idsuppliers
				INNER JOIN pm_inventory on pm_inventory.idinventory = price_list.mm_prices_inventory_idinventory
				where mm_suppliers_idsuppliers = supid);

	CREATE TEMPORARY TABLE IF NOT EXISTS dates AS 
		(SELECT inventory_idinventory, max(effectivedte) maxdte

			FROM sup_price
			
		where effectivedte <= curdate()
		group by inventory_idinventory) ;

	SELECT idPrices, idinventory, sku, skudesc, skuom, skuwh, poPrice, supname, units, soh, csl from sup_price2
		INNER JOIN dates on dates.inventory_idinventory = sup_price2.idinventory AND dates.maxdte = sup_price2.effectivedte;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PO_PRICES_GET_BY_SUP_SKU` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PO_PRICES_GET_BY_SUP_SKU`(supid INT, skusearch VARCHAR(100))
BEGIN
	CREATE TEMPORARY TABLE IF NOT EXISTS sup_price AS 
		(SELECT * FROM bestchem_db2.price_list 
			INNER JOIN mm_prices on mm_prices.idPrices = price_list.mm_prices_idPrices 
			INNER JOIN mm_suppliers on mm_suppliers.idsuppliers = price_list.mm_suppliers_idsuppliers
			INNER JOIN pm_inventory on pm_inventory.idinventory = price_list.mm_prices_inventory_idinventory
			where mm_suppliers_idsuppliers = supid);

	CREATE TEMPORARY TABLE IF NOT EXISTS sup_price2 AS 
			(SELECT * FROM bestchem_db2.price_list 
				INNER JOIN mm_prices on mm_prices.idPrices = price_list.mm_prices_idPrices 
				INNER JOIN mm_suppliers on mm_suppliers.idsuppliers = price_list.mm_suppliers_idsuppliers
				INNER JOIN pm_inventory on pm_inventory.idinventory = price_list.mm_prices_inventory_idinventory
				where mm_suppliers_idsuppliers = supid);

	CREATE TEMPORARY TABLE IF NOT EXISTS dates AS 
		(SELECT inventory_idinventory, max(effectivedte) maxdte

			FROM sup_price
			
		where effectivedte <= curdate()
		group by inventory_idinventory) ;

	SELECT idPrices, idinventory, sku, skudesc, skuom, skuwh, poPrice, supname, units, soh, csl from sup_price2
		INNER JOIN dates on dates.inventory_idinventory = sup_price2.idinventory AND dates.maxdte = sup_price2.effectivedte
		where sku LIKE skusearch;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PO_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PO_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idpurchases', 'po_dte', 'po_dr_dte', 'idsuppliers', 'supname', 'suppayment', 'sku', 'skudesc', 'po_qty', 'skuom', 'unitprice', 'actual_qty', 'GR ID', 'GR Date'

	UNION ALL

	(SELECT idpurchases, po_dte, po_dr_dte, idsuppliers, supname, suppayment, sku, skudesc, po_qty, skuom, unitprice, actual_qty, idpgr, pgr_dtetme from purchases
		INNER JOIN suppliers on suppliers.idsuppliers = purchases.sup_id
		INNER JOIN purchaseitems on purchaseitems.purchase_id = purchases.idpurchases
		INNER JOIN pm_inventory on pm_inventory.idinventory = purchaseitems.inventory_id
        INNER JOIN pgr on pgr.idpo_doc = purchases.idpurchases
        where purchases.pgistat = 'Y' AND po_dte BETWEEN fromdte AND todte
		ORDER BY purchases.idpurchases, purchases.po_dte);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRG_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRG_ADD`(pgrdoc INT)
BEGIN
	INSERT INTO `pgr` (`idpo_doc`, `pgr_dtetme`) VALUES (pgrdoc, NOW());
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRICES_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRICES_ADD`(selp DECIMAL(10,4), pop DECIMAL(10,4), effDte DATE, id INT(11))
BEGIN
	INSERT INTO `prices` (`sellingPrice`, `poPrice`, `effectivedte`, `inventory_idinventory`) 
    VALUES (selp, pop, effDte, id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRICES_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRICES_EDIT`(pid INT, selp DECIMAL(10,4), pop DECIMAL(10,4), effDte DATE, id INT(11))
BEGIN
	UPDATE `prices` SET `sellingPrice`=selp, `poPrice`=pop, `effectivedte`=effDte 
    WHERE `idPrices`=pid and`inventory_idinventory`=id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRICES_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRICES_GET`(id INT(11))
BEGIN
	SELECT * FROM prices 
    WHERE idPrices = id
    ORDER BY effectivedte;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRICES_GET_ALL` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRICES_GET_ALL`()
BEGIN
	SELECT * FROM prices
    ORDER BY effectivedte;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRICES_SUPPLIER_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRICES_SUPPLIER_ADD`(pop DECIMAL(10,4), effDte DATE, id INT(11), supplier_id INT)
BEGIN
	INSERT INTO `mm_prices` (`sellingPrice`, `poPrice`, `effectivedte`, `inventory_idinventory`) 
    VALUES ('0.0', pop, effDte, id);
    
    INSERT INTO `price_list` (`mm_prices_idPrices`, `mm_prices_inventory_idinventory`, `mm_suppliers_idsuppliers`) 
    VALUES ((SELECT LAST_INSERT_ID()), id, supplier_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PRINT_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PRINT_ADD`(sales_invoice_id INT, status1 ENUM('PRINT', 'CANCELLED'), billing_id INT, prnt_dte DATE)
BEGIN
	INSERT INTO `print_invoice` (`sales_invoice_id`, `status`, `billing_id`, `prnt_dte`) 
    VALUES (sales_invoice_id, status1 , billing_id, prnt_dte);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PURCHASE_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PURCHASE_ADD`(sup_id INT, sup_c_name VARCHAR(45), po_dte DATE, po_dr_dte DATE, cby_id INT, pgistat ENUM('Y', 'N'))
BEGIN
	INSERT INTO `purchases` (`sup_id`, `sup_c_name`, `po_dte`, `po_dr_dte`, `cby_id`, `pgistat`) 
    VALUES (sup_id, sup_c_name, po_dte, po_dr_dte, cby_id, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PURCHASE_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PURCHASE_ADD_ITEMS`(inventory_id INT, po_qty INT, unitprice DECIMAL(10,4), amount DECIMAL(10,4), vat_amount DECIMAL(10,4), purchase_id INT)
BEGIN
	INSERT INTO `purchaseitems` (`inventory_id`, `po_qty`, `unitprice`, `amount`, `vat_amount`, `purchase_id`) 
    VALUES (inventory_id, po_qty, unitprice, amount, vat_amount, purchase_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PURCHASE_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PURCHASE_EDIT`(sup_c_name VARCHAR(45), po_dte DATE, po_dr_dte DATE, idpur INT)
BEGIN
	UPDATE `purchases` SET `sup_c_name`=sup_c_name, `po_dte`=po_dte, `po_dr_dte`=po_dr_dte 
    WHERE `idpurchases`= idpur;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `PURCHASE_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `PURCHASE_GET_ITEMS`(purch_id INT)
BEGIN
	SELECT * from purchaseitems
	INNER JOIN inventory on inventory.idinventory = purchaseitems.inventory_id
	where `purchase_id` = purch_id and `status` = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `REMAINING_DR_TEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `REMAINING_DR_TEMS`(soid INT)
BEGIN
	SELECT idsalesorderitem, idinventory, ordrqty, sku, skudesc, skuom, drqty, SUM(drqty), ordrqty - SUM(drqty) AS 'Remaining_Quantity',soh FROM salesorderitems
	INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
    INNER JOIN deliveryorderitems on deliveryorderitems.dritemsi = salesorderitems.idsalesorderitem
    
    where salesorderitems.salesorder_idsalesorder = soid and  deliveryorderitems.pgistat = 'Y'
    GROUP BY idsalesorderitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADD`(sku VARCHAR(100), desc1 VARCHAR(100), retuom VARCHAR(45), retwhs VARCHAR(45), soh INT)
BEGIN
	INSERT INTO `returns` (`sku`, `desc`, `retuom`, `retwhs`, `soh`) 
    VALUES (sku, desc1, retuom, retwhs, soh);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_ADD`(rs_dte DATE, refnum VARCHAR(100), description VARCHAR(300), pgistat ENUM('Y','N'))
BEGIN
	INSERT INTO `returns_adjustments` (`rs_dte`, `refnum`, `description`, `pgistat`) 
    VALUES (rs_dte, refnum, description, pgistat);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_ADD_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_ADD_ITEMS`(return_id INT, stock_qty INT, mov ENUM('INC', 'DEC'), status1 ENUM('Y', 'N'), retadj_id INT)
BEGIN
	INSERT INTO `returns_adjustments_items` (`return_id`, `stock_qty`, `mov`, `status`, `retadj_id`) 
    VALUES (return_id, stock_qty, mov, status1, retadj_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_EDIT`(rs_dte DATE, refnum VARCHAR(100), description VARCHAR(300), idretadj INT)
BEGIN
	UPDATE `returns_adjustments` SET `rs_dte`=rs_dte, `refnum`=refnum, `description`=description 
    WHERE `idreturns_adjustments`=idretadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_EDIT_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_EDIT_ITEMS`(idretitem INT)
BEGIN
	UPDATE `returns_adjustments_items` SET `itemstatus`='DELETED' 
    WHERE `idretadtems`=idretitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_GET_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_GET_ITEMS`(retid INT)
BEGIN
	SELECT * FROM returns_adjustments_items 
	
    INNER JOIN returns on returns.idreturns = returns_adjustments_items.return_id
    
	where retadj_id = retid and itemstatus = 'OPEN';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_PGI` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_PGI`(idretam INT)
BEGIN
	UPDATE `returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_ADJ_PGI_STAT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_ADJ_PGI_STAT`(idretadj INT)
BEGIN
	UPDATE `returns_adjustments` SET `pgistat`='Y'
    WHERE `idreturns_adjustments`= idretadj;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_EDIT`(sku VARCHAR(100), desc1 VARCHAR(100), retuom VARCHAR(45), retwhs VARCHAR(45), retid INT)
BEGIN
	UPDATE `returns` SET `sku`=sku, `desc`= desc1, `retuom`=retuom, `retwhs`= retwhs 
    WHERE `idreturns`= retid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `RETURNS_SUMMARY_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `RETURNS_SUMMARY_REPORT`()
BEGIN
	SELECT 'sku', 'desc','retuom','retwhs','soh'

	UNION ALL

	SELECT sku, `desc` AS 'description', retuom, retwhs, soh FROM returns;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SALESORDERITEMS_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SALESORDERITEMS_ADD`(qty INT(11), sid INT(11), inid INT(11), dscnt DECIMAL(10,4), unitprice DECIMAL(10,4), amt DECIMAL(10,4), vatval DECIMAL(10,4), stat ENUM('OPEN', 'COMPLETE', 'DELETED'))
BEGIN
	INSERT INTO `salesorderitems` (`ordrqty`, `salesorder_idsalesorder`, `inventory_idinventory`, `discnt`, `unitprice`, `amount`, `vat`, `itemstatus`)
    VALUES (qty, sid, inid, dscnt, unitprice, amt, vatval, stat);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SALESORDERITEMS_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SALESORDERITEMS_EDIT`(qty INT(11), sid INT(11), inid INT(11), dscnt DECIMAL(10,4), unitprice DECIMAL(10,4), amt DECIMAL(10,4), vatval DECIMAL(10,4), idsoitem INT)
BEGIN
	UPDATE `salesorderitems` SET `ordrqty`=qty, `discnt`=dscnt, `unitprice` = unitprice, `amount`=amt, `vat`=vatval 
    WHERE `idsalesorderitem`=idsoitem and`salesorder_idsalesorder`=sid and`inventory_idinventory`=inid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SalesOrder_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SalesOrder_ADD`(custpo VARCHAR(45), custid INT(11), sdate DATE, sddate DATE)
BEGIN
	INSERT INTO `salesorders` (`customerpo`, `customer_idcustomer`, `sodate`, `sodeliverydate`, `status`) 
    VALUES (custpo, custid, sdate, sddate, 'open');
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SALESORDER_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SALESORDER_EDIT`(custpo VARCHAR(45), custid INT(11), sdate DATE, sddate DATE, soid INT)
BEGIN
	UPDATE `salesorders` SET `customerpo`=custpo, `sodate`=sdate, `sodeliverydate`=sddate 
    WHERE `idsalesorder`=soid and`customer_idcustomer`=custid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SALES_ORDERITEMS_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SALES_ORDERITEMS_GET`(sid INT, stat ENUM('OPEN', 'COMPLETE', 'DELETED'))
BEGIN
	SELECT idsalesorderitem, idinventory, ordrqty, sku, skudesc, skuom, soh FROM salesorderitems
	INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
    where salesorderitems.salesorder_idsalesorder = sid AND salesorderitems.itemstatus = stat
    GROUP BY idsalesorderitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SALES_ORDER_ITEMS_GET_PRICE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SALES_ORDER_ITEMS_GET_PRICE`(soid INT, custid INT)
BEGIN
	SELECT idsalesorderitem, ordrqty, C.idinventory, C.sku, C.skudesc, C.skuom, C.sellingPrice, C.poPrice,  
    (C.sellingPrice * ordrqty) totamt,
    (C.sellingPrice * ordrqty) * (1 + customers.vat_prcnt / 100) vatinc
    
    FROM customers, salesorderitems
	
    INNER JOIN 
    
		(SELECT idinventory, inventory.sku, skudesc, skuom, skuwh, sellingPrice, poPrice, B.maxdte from inventory 
	INNER JOIN prices A on A.inventory_idinventory = idinventory
	INNER JOIN (SELECT inventory_idinventory, max(effectivedte) maxdte FROM prices where effectivedte < curdate() group by inventory_idinventory) B
	ON A.inventory_idinventory = B.inventory_idinventory AND A.effectivedte = B.maxdte
	where idinventory = inventory.idinventory) C
    
    
    on C.idinventory = salesorderitems.inventory_idinventory
    INNER JOIN deliveryorderitems on deliveryorderitems.dritemsi = salesorderitems.idsalesorderitem
    where salesorderitems.salesorder_idsalesorder = soid AND customers.idcustomer = custid
    GROUP BY idsalesorderitem;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SI_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SI_ADD`(customeridiv INT, soidinvc INT, remarks VARCHAR(200), cby INT, status ENUM('open', 'complete'), truckername VARCHAR(100), drivername VARCHAR(100), plateno VARCHAR(45), sidte DATE, printstat ENUM('N', 'Y', 'REPRINTED'))
BEGIN
	INSERT INTO `salesinvoices` (`customeridiv`, `soidinvc`, `remarks`, `cby`, `status`, `truckername`, `drivername`, `plateno`, `sidte`, `printstat`) 
    VALUES (customeridiv, soidinvc, remarks, cby, status, truckername, drivername, plateno, sidte, printstat);
    
    SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SI_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SI_EDIT`(remarks VARCHAR(200), truckername VARCHAR(100), drivername VARCHAR(100), plateno VARCHAR(45), sidte DATE, siid INT)
BEGIN
	UPDATE `salesinvoices` SET `remarks`=remarks, `truckername`=truckername, `drivername`=drivername, `plateno`=plateno, `sidte`=sidte 
    WHERE `idsalesinvoices`=siid;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SI_ITEMS_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SI_ITEMS_ADD`(si_id INT, dr_id INT)
BEGIN
	INSERT INTO `salesinvoiceitems` (`si_id`, `dr_id`) 
    VALUES (si_id, dr_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SI_ITEMS_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SI_ITEMS_GET`(siid INT)
BEGIN
	SELECT * FROM salesinvoiceitems 
	INNER JOIN deliveryorders on salesinvoiceitems.dr_id = deliveryorders.iddeliveryorders
    where si_id = siid and `status` = 'OPEN' ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SI_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SI_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idsalesinvoices', 'sidte', 'salesinvoices.sidte', 'customers.idcustomer', 'customers.name', 'paymentterm', 'soidinvc', 'customerpo', 'sodate', 'sodeliverydate', 'sku', 'skudesc', 'SUM(drqty)', 'ordrqty', 'skuom', 'unitprice', 'discnt', 'skuwh'

	UNION ALL

	SELECT idsalesinvoices, sidte, salesinvoices.sidte, customers.idcustomer, customers.name, paymentterm, soidinvc, customerpo, sodate, sodeliverydate, sku, skudesc, SUM(drqty), ordrqty, skuom, unitprice, salesorderitems.discnt, skuwh from salesinvoices
		INNER JOIN salesinvoiceitems on salesinvoiceitems.si_id = salesinvoices.idsalesinvoices
		INNER JOIN deliveryorderitems on salesinvoiceitems.dr_id = deliveryorderitems.drorder
		INNER JOIN salesorderitems on deliveryorderitems.dritemsi = salesorderitems.idsalesorderitem
		INNER JOIN customers on customers.idcustomer = salesinvoices.customeridiv
		INNER JOIN salesorders on salesorders.idsalesorder = salesinvoices.soidinvc
		INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
		where salesinvoiceitems.status = 'OPEN' AND sidte BETWEEN fromdte AND todte
		GROUP BY si_id, idinventory;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SOITEMS_EDIT_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SOITEMS_EDIT_GET`(soid INT, stat ENUM('OPEN', 'COMPLETE', 'DELETED'))
BEGIN
	SELECT idsalesorderitem, inventory_idinventory, sku, skudesc, ordrqty, skuom, discnt, unitprice, amount, vat FROM salesorderitems
	 INNER JOIN inventory on idinventory = inventory_idinventory
	 where salesorder_idsalesorder = soid AND itemstatus = stat;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SOITEMS_LINE_ITEMS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SOITEMS_LINE_ITEMS`(soid INT)
BEGIN
	SELECT idinventory, idsalesorderitem, inventory_idinventory, sku, skudesc, ordrqty, skuom, discnt, unitprice, amount, vat FROM salesorderitems
	 INNER JOIN inventory on idinventory = inventory_idinventory
	 where salesorder_idsalesorder = soid AND itemstatus != 'DELETED';
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SOITEM_DELETE` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SOITEM_DELETE`(soiditem INT, soid INT, idinvent INT)
BEGIN
	UPDATE `salesorderitems` SET `itemstatus`='DELETED' 
    WHERE `idsalesorderitem`=soiditem and `salesorder_idsalesorder`=soid and`inventory_idinventory`=idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SO_CHANGE_ITEMS_STATUS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SO_CHANGE_ITEMS_STATUS`(soid INT, stat ENUM('OPEN', 'COMPLETE', 'DELETED'))
BEGIN
	SET SQL_SAFE_UPDATES = 0;
	UPDATE `salesorderitems` SET `itemstatus`= stat 
    WHERE `idsalesorderitem`IN 
    (SELECT idsalesorderitem from (SELECT * from salesorderitems) AS mytable where salesorder_idsalesorder = soid);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SO_CHANGE_STATUS` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SO_CHANGE_STATUS`(soid INT, custid INT, stat ENUM('open', 'cancelled', 'complete', 'With DR', 'Partially Delivered'))
BEGIN
	UPDATE `salesorders` SET `status`= stat 
    WHERE `idsalesorder`= soid and`customer_idcustomer`= custid;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SO_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SO_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'idsalesorder', 'sodate', 'customerpo', 'sodeliverydate', 'idcustomer', 'name', 'address', 'paymentterm', 'sku', 'skudesc', 'ordrqty', 'skuom', 'unitprice', 'discnt', 'status', 'inv_type'

UNION ALL

(SELECT idsalesorder, sodate, customerpo, sodeliverydate, idcustomer, `name`, address, paymentterm, sku, skudesc, ordrqty, skuom, unitprice, discnt, `status`, inv_type FROM salesorders
	INNER JOIN customers on customers.idcustomer = salesorders.customer_idcustomer
	INNER JOIN salesorderitems on salesorders.idsalesorder = salesorderitems.salesorder_idsalesorder
    INNER JOIN inventory on inventory.idinventory = salesorderitems.inventory_idinventory
    where sodate BETWEEN fromdte AND todte
    ORDER BY salesorders.idsalesorder, sodate) ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `SR_DTLD_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `SR_DTLD_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'ID Return Adjustment', 'RS DATE', 'Reference Number', 'sku', 'description', 'uom', 'whs', 'Stock Qty', 'Effect'

	UNION ALL 

	(SELECT idreturns_adjustments, rs_dte, refnum, sku, `desc`, retuom, retwhs, stock_qty, mov FROM returns_adjustments
		INNER JOIN returns_adjustments_items on returns_adjustments.idreturns_adjustments = returns_adjustments_items.retadj_id
		INNER JOIN `returns` on returns.idreturns = returns_adjustments_items.return_id
        where rs_dte BETWEEN fromdte AND todte);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ST_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ST_ADD`(st_dte DATE, dr_dte DATE, sldto VARCHAR(200), attent VARCHAR(200), refernum VARCHAR(200), address VARCHAR(200), ponum VARCHAR(100), trucknme VARCHAR(100), drvrnme VARCHAR(100), pltno VARCHAR(100), stdesc VARCHAR(200), remarks VARCHAR(200))
BEGIN
	INSERT INTO `bestchem_db2`.`stocktransfers` (`st_dte`, `dr_dte`, `sldto`, `attent`, `refernum`, `address`, `ponum`, `trucknme`, `drvrnme`, `pltno`, `stdesc`, `remarks`) 
    VALUES (st_dte, dr_dte, sldto, attent, refernum, address, ponum, trucknme, drvrnme, pltno, stdesc, remarks);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ST_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ST_EDIT`(st_dte DATE, dr_dte DATE, sldto VARCHAR(200), attent VARCHAR(200), refernum VARCHAR(200), address VARCHAR(200), ponum VARCHAR(100), trucknme VARCHAR(100), drvrnme VARCHAR(100), pltno VARCHAR(100), stdesc VARCHAR(200), remarks VARCHAR(200), idst INT)
BEGIN
	UPDATE `bestchem_db2`.`stocktransfers` SET `st_dte`=st_dte, `dr_dte`=dr_dte, `sldto`=sldto, `attent`=attent, `refernum`=refernum, `address`=address, `ponum`=ponum, `trucknme`=trucknme, `drvrnme`=drvrnme, `pltno`=pltno, `stdesc`=stdesc, `remarks`=remarks 
    WHERE `idstocktransfers`=idst;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ST_ITEMS_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ST_ITEMS_GET`(idinvent INT)
BEGIN
	SELECT idstitems, st_idinvent, invent_id_frm, A.sku as sku1, A.skudesc as skudesc1, A.skuwh as skuwh1, invent_qty, invent_id_to, B.sku as sku2, B.skudesc as skudesc2, B.skuwh as skuwh2 FROM st_items
	INNER JOIN pm_inventory A on A.idinventory = st_items.invent_id_frm
    INNER JOIN pm_inventory B on B.idinventory = st_items.invent_id_to
    where st_idinvent = idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ST_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ST_ITEM_ADD`(st_idinvent INT, invent_id_frm INT, invent_qty INT, invent_id_to INT)
BEGIN
	INSERT INTO `bestchem_db2`.`st_items` (`st_idinvent`, `invent_id_frm`, `invent_qty`, `invent_id_to`) 
    VALUES (st_idinvent, invent_id_frm, invent_qty, invent_id_to);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `ST_REPORT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `ST_REPORT`(fromdte DATE, todte DATE)
BEGIN
	SELECT 'Material Doc ID', 'Transaction_Time', 'Client Name', 'DR ID', 'pm code', 'skudesc', 'uom', 'qty_adj', 'effect', 'warehouse'

	UNION ALL

	SELECT idinventory_transactions, transac_time, client_name, doc_it, sku, skudesc, skuom, qty_adj, effect, skuwh FROM bestchem_db2.mm_inventory_transactions 
	INNER JOIN pm_inventory on item = pm_inventory.idinventory
    where doc_type = 'StockTransfer' and transac_time BETWEEN fromdte AND todte ;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TRM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `TRM_ADD`(supname VARCHAR(200), conname VARCHAR(200), gi_date DATE, addres VARCHAR(255), refnum VARCHAR(200), gidesc VARCHAR(255), grnum INT, trn_type ENUM('MANUAL','PGR'))
BEGIN
	INSERT INTO `bestchem_db2`.`transmittal` (`supname`, `conname`, `gi_date`, `addres`, `refnum`, `gidesc`, `grnum`, `trn_type`) 
    VALUES (supname, conname, gi_date, addres, refnum, gidesc, grnum, trn_type);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TRM_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `TRM_EDIT`(supname VARCHAR(200), conname VARCHAR(200), gi_date DATE, addres VARCHAR(255), refnum VARCHAR(200), gidesc VARCHAR(255), idtrans INT)
BEGIN
	UPDATE `transmittal` SET `supname`=supname, `conname`=conname, `gi_date`=gi_date, `addres`=addres, `refnum`=refnum, `gidesc`=gidesc 
    WHERE `idtransmittal`= idtrans;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `TRM_ITEM_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `TRM_ITEM_ADD`(fginvent_id INT, fginvent_sku VARCHAR(255), fginvent_desc VARCHAR(255), order_qty INT, pm_sku VARCHAR(255), pm_desc VARCHAR(255), bom_wh VARCHAR(255), unit_qty INT, transmittal_id INT, pm_invent_id INT)
BEGIN
	INSERT INTO `bestchem_db2`.`tritems` (`fginvent_id`, `fginvent_sku`, `fginvent_desc`, `order_qty`, `pm_sku`, `pm_desc`, `bom_wh`, `unit_qty`, `transmittal_id`, `pm_invent_id`) 
    VALUES (fginvent_id, fginvent_sku, fginvent_desc, order_qty, pm_sku, pm_desc, bom_wh, unit_qty, transmittal_id,pm_invent_id);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_INVENTORY` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `UPDATE_INVENTORY`(idinvent INT, subval INT)
BEGIN
	SELECT soh from inventory where idinventory != idinvent FOR UPDATE;
	UPDATE inventory set soh = (soh - subval) where idinventory = idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_INVENTORY_INC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `UPDATE_INVENTORY_INC`(idinvent INT, subval INT)
BEGIN
	SELECT soh from inventory where idinventory != idinvent FOR UPDATE;
	UPDATE inventory set soh = (soh + subval) where idinventory = idinvent;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_RETURNS_DEC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `UPDATE_RETURNS_DEC`(idretam INT, idret INT, subval INT)
BEGIN
	UPDATE `returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;

	SELECT soh from returns where idreturns != idret FOR UPDATE;
	UPDATE returns set soh = (soh - subval) where idreturns = idret;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `UPDATE_RETURNS_INC` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `UPDATE_RETURNS_INC`(idretam INT, idret INT, subval INT)
BEGIN
	UPDATE `returns_adjustments_items` SET `status`='Y' 
    WHERE `idretadtems`= idretam;

	SELECT soh from returns where idreturns != idret FOR UPDATE;
	UPDATE returns set soh = (soh + subval) where idreturns = idret;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `USER_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `USER_ADD`(uname VARCHAR(45), passwd VARCHAR(255), ustyp VARCHAR(100))
BEGIN
	INSERT INTO `users` (`username`, `password`, `usertype`) 
    VALUES (uname, passwd, ustyp);
    
    SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `USER_EDIT` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `USER_EDIT`()
BEGIN

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `USER_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `USER_GET`(username VARCHAR(45), password VARCHAR(255))
BEGIN
	SELECT * FROM users 
    where users.username = username AND users.password = password;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `WAREHOUSE_ADD` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `WAREHOUSE_ADD`(warehouse VARCHAR(45))
BEGIN
	INSERT INTO `warehouses` (`idwarehouses`) VALUES (warehouse);
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `WAREHOUSE_GET` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8 */ ;
/*!50003 SET character_set_results = utf8 */ ;
/*!50003 SET collation_connection  = utf8_general_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_AUTO_CREATE_USER,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`admin`@`localhost` PROCEDURE `WAREHOUSE_GET`()
BEGIN
	SELECT * FROM warehouses;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-27 16:09:26
