mysql --user=root --password=root -e  "drop DATABASE thales;"
mysql --user=root --password=root -e  "CREATE DATABASE thales CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci; GRANT ALL ON `thales`.* TO 'root'@'localhost';"
