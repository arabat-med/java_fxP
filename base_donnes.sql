 CREATE DATABASE student;
 use student;
 show student;
 /*creation du table student*/
  CREATE TABLE student (
    ->     ID INT PRIMARY KEY,
    ->     Nom VARCHAR(50),
    ->     Prenom VARCHAR(50),
    ->     CNE VARCHAR(20)
    -> );
    /*insertion dans la table students les donnes */
    INSERT INTO student (ID, Nom, Prenom, Code_massar)
    -> VALUES (1, 'ARABAT', 'John', '123456789'),
    ->        (2, 'Smith', 'Jane', '987654321'),
    ->        (3, 'Williams', 'David', '456789123');
    /*affichage du table student*/
    select *FROM student;