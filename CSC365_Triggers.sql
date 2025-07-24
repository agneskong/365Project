DELIMITER $$

CREATE TRIGGER prevent_duplicate_genres
BEFORE INSERT ON MovieGenres
FOR EACH ROW
BEGIN
    IF EXISTS (
        SELECT 1 FROM MovieGenres WHERE LOWER(genre) = LOWER(NEW.genre)
    ) THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Duplicate genre not allowed.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER validate_rating_insert
BEFORE INSERT ON UserRating
FOR EACH ROW
BEGIN
    IF NEW.rating < 1 OR NEW.rating > 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Rating must be between 1 and 10.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER validate_rating_update
BEFORE UPDATE ON UserRating
FOR EACH ROW
BEGIN
    IF NEW.rating < 1 OR NEW.rating > 10 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Rating must be between 1 and 10.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER check_movie_year_insert
BEFORE INSERT ON Movies
FOR EACH ROW
BEGIN
    IF YEAR(NEW.date) < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Movie year must be AD.';
    END IF;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER update_movie_rating_after_insert
AFTER INSERT ON UserRating
FOR EACH ROW
BEGIN
    UPDATE Movies
    SET rating = (
        SELECT AVG(rating)
        FROM UserRating
        WHERE mid = NEW.mid
    )
    WHERE mid = NEW.mid;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER update_movie_rating_after_update
AFTER UPDATE ON UserRating
FOR EACH ROW
BEGIN
    UPDATE Movies
    SET rating = (
        SELECT AVG(rating)
        FROM UserRating
        WHERE mid = NEW.mid
    )
    WHERE mid = NEW.mid;
END$$

DELIMITER ;


DELIMITER $$

CREATE TRIGGER update_movie_rating_after_delete
AFTER DELETE ON UserRating
FOR EACH ROW
BEGIN
    UPDATE Movies
    SET rating = (
        SELECT AVG(rating)
        FROM UserRating
        WHERE mid = OLD.mid
    )
    WHERE mid = OLD.mid;
END$$

DELIMITER ;
