/*
SQLyog Ultimate v12.4.1 (64 bit)
MySQL - 5.7.23 : Database - baseteste
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`baseteste` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `baseteste`;

/*Table structure for table `clientes` */

DROP TABLE IF EXISTS `clientes`;

CREATE TABLE `clientes` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(60) DEFAULT NULL,
  `Sobrenome` varchar(150) DEFAULT NULL,
  `DDD_Telefone` char(2) DEFAULT NULL,
  `Telefone` varchar(25) DEFAULT NULL,
  `DDD_Celular` char(2) DEFAULT NULL,
  `Celular` varchar(20) DEFAULT NULL,
  `Email` varchar(100) DEFAULT NULL,
  `Data_Cadastro` datetime DEFAULT NULL,
  `Ultima_Alteracao` datetime DEFAULT NULL,
  `ID_Bairro` int(11) DEFAULT NULL,
  `Observacao` longtext,
  `Tipo` enum('FISICO','JURIDICO','FISICA_JURIDICA') NOT NULL,
  `Situacao` enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes` */

/*Table structure for table `clientes_contatos` */

DROP TABLE IF EXISTS `clientes_contatos`;

CREATE TABLE `clientes_contatos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Cliente` int(11) NOT NULL,
  `Nome` varchar(150) DEFAULT NULL,
  `DDD_Telefone` varchar(2) DEFAULT NULL,
  `Telefone` varchar(10) DEFAULT NULL,
  `DDD_Celular` varchar(2) DEFAULT NULL,
  `Celular` varchar(10) DEFAULT NULL,
  `Observacao` longtext,
  `Situacao` enum('ATIVO') DEFAULT NULL,
  PRIMARY KEY (`ID`,`ID_Cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes_contatos` */

/*Table structure for table `clientes_enderecos` */

DROP TABLE IF EXISTS `clientes_enderecos`;

CREATE TABLE `clientes_enderecos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Cliente` int(11) NOT NULL,
  `ID_Bairro` int(11) NOT NULL,
  `Endereco` varchar(150) NOT NULL,
  `Numero` varchar(10) NOT NULL,
  `CEP` varchar(10) DEFAULT NULL,
  `Referencia` varchar(150) DEFAULT NULL,
  `Observacao` longtext,
  `Situacao` enum('ATIVO','INATIVO') DEFAULT NULL,
  PRIMARY KEY (`ID`,`ID_Cliente`,`ID_Bairro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `clientes_enderecos` */

/*Table structure for table `empresas` */

DROP TABLE IF EXISTS `empresas`;

CREATE TABLE `empresas` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Bairro` int(11) NOT NULL,
  `Nome_Fantasia` varchar(150) NOT NULL,
  `Razao_Social` varchar(150) DEFAULT NULL,
  `CNPJ` varchar(15) DEFAULT NULL,
  `Endereco` varchar(150) DEFAULT NULL,
  `Numero` varchar(5) DEFAULT NULL,
  `CEP` varchar(10) DEFAULT NULL,
  `Complemento` varchar(250) DEFAULT NULL,
  `Data_Cadastro` datetime NOT NULL,
  `Situacao` enum('ATIVO','INATIVO') DEFAULT NULL,
  PRIMARY KEY (`ID`,`ID_Bairro`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresas` */

/*Table structure for table `empresas_contatos` */

DROP TABLE IF EXISTS `empresas_contatos`;

CREATE TABLE `empresas_contatos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Empresa` int(11) NOT NULL,
  `DDD_Telefone` varchar(2) DEFAULT NULL,
  `Telefone` varchar(10) DEFAULT NULL,
  `Observacao` text,
  `Tipo` enum('TELEFONE','CELULAR','FAX') DEFAULT NULL,
  PRIMARY KEY (`ID`,`ID_Empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `empresas_contatos` */

/*Table structure for table `mesas` */

DROP TABLE IF EXISTS `mesas`;

CREATE TABLE `mesas` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(50) DEFAULT NULL,
  `Observacao` text,
  `Quantidade_Cadeiras` int(5) DEFAULT NULL,
  `Status` enum('USO','LIVRE','RESERVA') DEFAULT NULL,
  `Horario_Reserva` time DEFAULT NULL,
  `Tipo` enum('INTERNO','EXTERNO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `mesas` */

/*Table structure for table `parametros` */

DROP TABLE IF EXISTS `parametros`;

CREATE TABLE `parametros` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Chave` varchar(20) DEFAULT NULL,
  `Descricao` varchar(100) DEFAULT NULL,
  `Valor` varchar(100) DEFAULT NULL,
  `Observacao` text,
  `Tipo` enum('INT','FLOAT','STRING','DOUBLE','BOOLEAN') DEFAULT NULL,
  `Situacao` enum('ATIVO','INATIVO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `parametros` */

/*Table structure for table `produtos` */

DROP TABLE IF EXISTS `produtos`;

CREATE TABLE `produtos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(150) DEFAULT NULL,
  `Observacao` varbinary(300) DEFAULT NULL,
  `Codigo_Barras` varchar(35) DEFAULT NULL,
  `Unidade` varbinary(5) DEFAULT NULL,
  `Data_Cadastro` datetime DEFAULT NULL,
  `Tipo` enum('PRODUCAO','MATERIAPRIMA','SERVICO') DEFAULT NULL,
  `Peso_Bruto` double DEFAULT NULL,
  `Peso_Liquido` double DEFAULT NULL,
  `Situacao` enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `produtos` */

/*Table structure for table `produtos_imagens` */

DROP TABLE IF EXISTS `produtos_imagens`;

CREATE TABLE `produtos_imagens` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `ID_Produto` int(11) DEFAULT NULL,
  `NomeImagem` varchar(100) DEFAULT NULL,
  `Imagem` longblob,
  `Padrao` enum('SIM','NAO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `produtos_imagens` */

/*Table structure for table `produtos_sabores` */

DROP TABLE IF EXISTS `produtos_sabores`;

CREATE TABLE `produtos_sabores` (
  `ID_Produto` int(11) NOT NULL,
  `ID_Sabor` int(11) NOT NULL,
  PRIMARY KEY (`ID_Produto`,`ID_Sabor`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `produtos_sabores` */

/*Table structure for table `produtos_tamanhos` */

DROP TABLE IF EXISTS `produtos_tamanhos`;

CREATE TABLE `produtos_tamanhos` (
  `ID_Produto` int(11) NOT NULL,
  `ID_Tamanho` int(11) NOT NULL,
  PRIMARY KEY (`ID_Produto`,`ID_Tamanho`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `produtos_tamanhos` */

/*Table structure for table `sabores` */

DROP TABLE IF EXISTS `sabores`;

CREATE TABLE `sabores` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(150) DEFAULT NULL,
  `Observacao` varchar(300) DEFAULT NULL,
  `Situacao` enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `sabores` */

/*Table structure for table `tamanhos` */

DROP TABLE IF EXISTS `tamanhos`;

CREATE TABLE `tamanhos` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Sigla` varchar(5) DEFAULT NULL,
  `Descricao` varchar(150) DEFAULT NULL,
  `Quantidade_Pedacos` int(3) DEFAULT NULL,
  `Quantidade_Sabores` int(3) DEFAULT NULL,
  `Situacao` enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `tamanhos` */

/*Table structure for table `usuarios` */

DROP TABLE IF EXISTS `usuarios`;

CREATE TABLE `usuarios` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `Loguin` char(20) NOT NULL,
  `Nome` varchar(255) DEFAULT NULL,
  `Senha` varchar(250) DEFAULT NULL,
  `ID_Empresa` int(11) NOT NULL,
  `Situacao` enum('ATIVO','INATIVO','EXCLUIDO') DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Data_Cadastro` datetime DEFAULT NULL,
  `Observacao` longtext,
  `Imagem` longblob,
  `Nivel` enum('USUARIO','ADMINISTRADOR','TOTAL') DEFAULT NULL,
  PRIMARY KEY (`ID`,`Loguin`,`ID_Empresa`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `usuarios` */

insert  into `usuarios`(`ID`,`Loguin`,`Nome`,`Senha`,`ID_Empresa`,`Situacao`,`Email`,`Data_Cadastro`,`Observacao`,`Imagem`,`Nivel`) values 
(1,'ADMIN','ADMIN','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3',0,'ATIVO',NULL,'2019-07-27 15:17:23',NULL,NULL,'ADMINISTRADOR'),
(2,'SHIYOKEN',NULL,'A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3',0,'ATIVO',NULL,'2019-07-27 15:17:23',NULL,NULL,'TOTAL'),
(3,'USUARIO','USUARIO','A665A45920422F9D417E4867EFDC4FB8A04A1F3FFF1FA07E998E86F7F7A27AE3',0,'ATIVO',NULL,'2019-07-27 15:17:23',NULL,NULL,'USUARIO');

/*Table structure for table `usuarios_empresas` */

DROP TABLE IF EXISTS `usuarios_empresas`;

CREATE TABLE `usuarios_empresas` (
  `Id_Empresa` int(11) NOT NULL,
  `Id_Usuario` int(11) NOT NULL,
  PRIMARY KEY (`Id_Empresa`,`Id_Usuario`),
  UNIQUE KEY `usuariosempresas` (`Id_Usuario`,`Id_Empresa`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `usuarios_empresas` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
