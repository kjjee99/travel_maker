-- 사용자
CREATE TABLE `user` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(15) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phonenumber` varchar(45) NOT NULL,
  `profileimg` varchar(255) DEFAULT NULL,
  `role` varchar(45) DEFAULT 'basic',
  PRIMARY KEY (`idx`),
  UNIQUE KEY `id_UNIQUE` (`idx`),
  UNIQUE KEY `user_id_UNIQUE` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


-- 게시글
CREATE TABLE `post` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(15) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  `content` varchar(1023) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `createdat` datetime DEFAULT NULL,
  `figures` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  `like` int DEFAULT '0',
  `postimg` varchar(1023) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin DEFAULT NULL,
  `roads` varchar(255) COLLATE utf8mb3_bin DEFAULT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- 해시태그
CREATE TABLE `hashtags` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `tagname` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`idx`),
  UNIQUE KEY `idx_UNIQUE` (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- 게시글 & 해시태그 연결
CREATE TABLE `post_hashtag` (
  `postid` int NOT NULL,
  `tagid` int NOT NULL,
  PRIMARY KEY (`postid`,`tagid`),
  KEY `fk_tag_id_idx` (`tagid`),
  CONSTRAINT `fk_post_idx` FOREIGN KEY (`postid`) REFERENCES `post` (`idx`) ON UPDATE CASCADE,
  CONSTRAINT `fk_tag_idx` FOREIGN KEY (`tagid`) REFERENCES `hashtags` (`idx`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- 경로 팁
CREATE TABLE `roads` (
  `idx` int NOT NULL,
  `postid` int NOT NULL,
  `placename` varchar(45) COLLATE utf8mb3_bin NOT NULL,
  `tips` varchar(255) COLLATE utf8mb3_bin NOT NULL,
  PRIMARY KEY (`idx`),
  KEY `fk_post_id_idx` (`postid`),
  CONSTRAINT `fk_post_id` FOREIGN KEY (`postid`) REFERENCES `post` (`idx`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- 좋아요
CREATE TABLE `heart` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `userid` varchar(15) COLLATE utf8mb3_bin NOT NULL,
  `postidx` int NOT NULL,
  PRIMARY KEY (`idx`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_bin;

-- 팔로잉
CREATE TABLE `follow` (
  `idx` int NOT NULL AUTO_INCREMENT,
  `useridx` int NOT NULL,
  `following` int NOT NULL,
  PRIMARY KEY (`idx`),
  UNIQUE KEY `id_UNIQUE` (`idx`),
  KEY `fk_user_id_idx` (`useridx`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`useridx`) REFERENCES `user` (`idx`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
