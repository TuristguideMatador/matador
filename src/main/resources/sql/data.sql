SET FOREIGN_KEY_CHECKS = 0;

TRUNCATE TABLE attraction_tag;
TRUNCATE TABLE color;
TRUNCATE TABLE location;
TRUNCATE TABLE tag;
TRUNCATE TABLE tourist_attraction;

SET FOREIGN_KEY_CHECKS = 1;

START TRANSACTION;

-- Lookup tables

INSERT INTO tag (tag_id, tag_name) VALUES
                                       (1,'Børnevenlig'),
                                       (2,'Handicapvenlig'),
                                       (3,'Gratis'),
                                       (4,'Udendørs'),
                                       (5,'Indendørs'),
                                       (6,'Underholdning'),
                                       (7,'Restaurant'),
                                       (8,'Kultur'),
                                       (9,'Toilet'),
                                       (10,'Natur');

INSERT INTO color (color_id, hex, name) VALUES
                                            (1,'#8BC0D0','Lyseblå'),
                                            (2,'#DC8890','Rosa'),
                                            (3,'#E49249','Orange'),
                                            (4,'#D93F51','Rød'),
                                            (5,'#E8CB65','Gul'),
                                            (6,'#5674AA','Grøn'),
                                            (7,'#358A69','Mørkeblå'),
                                            (8,'#674C8D','Lilla'),
                                            (9,'#E5DBD2','Hvid'),
                                            (10,'#8C8982','Grå');


-- Location data

INSERT INTO location (location_id, name, color_id) VALUES
                                                       (1,'Rødovrevej',1),
                                                       (2,'Hvidovrevej',1),
                                                       (3,'Roskildevej',2),
                                                       (4,'Valby Langgade',2),
                                                       (5,'Allégade',2),
                                                       (6,'Frederiksberg Allé',3),
                                                       (7,'Bülowsvej',3),
                                                       (8,'Gl. Kongevej',3),
                                                       (9,'Bernstorffsvej',10),
                                                       (10,'Hellerupvej',10),
                                                       (11,'Strandvejen',10),
                                                       (12,'Trianglen',4),
                                                       (13,'Østerbrogade',4),
                                                       (14,'Grønningen',4),
                                                       (15,'Bredgade',6),
                                                       (16,'Kgs. Nytorv',6),
                                                       (17,'Østergade',6),
                                                       (18,'Amagertorv',5),
                                                       (19,'Vimmelskaftet',5),
                                                       (20,'Nygade',5),
                                                       (21,'Frederiksberggade',8),
                                                       (22,'Rådhuspladsen',8)
;

-- Tourist Attraction Data

INSERT INTO tourist_attraction (attraction_id, name, description, location_id) VALUES
                                                                                   (1,
                                                                                    'Toftegårds Bodega',
                                                                                    'Et af de sidste bastioner som endnu ikke er top gentrificeret.
                                                                                    Dog siger rygtet at bartenderen er ret ubehagelig',
                                                                                    4),
                                                                                   (2,
                                                                                    'BT Kiosk',
                                                                                    'Ikonisk arkitektur med mange år på bagen. Køb en avis eller en dårlig kop kaffe',
                                                                                    12),
                                                                                   (3,
                                                                                    'Tivoli',
                                                                                    'En klassiker udi morskab og spas - tag gerne lidt jyske dollars med',
                                                                                    9);

-- Tags for Tourist Attractions

INSERT INTO attraction_tag (attraction_id, tag_id) VALUES
                                                       (1,8),
                                                       (1,5),
                                                       (1,6),
                                                       (2,8),
                                                       (2,9),
                                                       (3,8),
                                                       (3,7),
                                                       (3,1),
                                                       (3,9),
                                                       (3,4);

COMMIT;

SELECT * FROM tourist_attraction;

SELECT
    tourist_attraction.name AS Name,
    tourist_attraction.description AS Description,
    location.name AS Location,
    color.hex AS Color
FROM
    tourist_attraction
        JOIN location
             ON tourist_attraction.location_id = location.location_id
        JOIN color
             ON location.color_id = color.color_id;

