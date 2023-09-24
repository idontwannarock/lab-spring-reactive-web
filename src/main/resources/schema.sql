DROP TABLE IF EXISTS MESSAGE;
DROP TABLE IF EXISTS CHATROOM;

CREATE TABLE CHATROOM
(
    ID          SERIAL PRIMARY KEY AUTO_INCREMENT,
    STATUS      INT                                    NOT NULL,
    CREATOR_ID  INT                                    NOT NULL,
    CREATE_TIME DATETIME(3) DEFAULT (UTC_TIMESTAMP(3)) NOT NULL
);

CREATE TABLE MESSAGE
(
    ID          SERIAL PRIMARY KEY AUTO_INCREMENT,
    CHATROOM_ID BIGINT                                 NOT NULL,
    CONTENT     VARCHAR(255),
    STATUS      INT                                    NOT NULL,
    CREATOR_ID  INT                                    NOT NULL,
    CREATE_TIME DATETIME(3) DEFAULT (UTC_TIMESTAMP(3)) NOT NULL
);
