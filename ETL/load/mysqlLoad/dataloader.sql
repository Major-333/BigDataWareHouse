create database MOVIE_STAR;

use MOVIE_STAR;

load data infile '/var/lib/mysql/dw_data/mysqlData/emotion_score_schema'
    into table emotion_score
    fields terminated by ','
    lines terminated by '\n'
    ignore 1 lines
    (emotion_score, count);

load data infile '/var/lib/mysql/dw_data/mysqlData/score_schema'
    into table score
    fields terminated by ','
    lines terminated by '\n'
    ignore 1 lines
    (score, count);

load data infile '/var/lib/mysql/dw_data/mysqlData/day_schema'
    ignore into table time
    fields terminated by ','
    lines terminated by '\n'
    ignore 1 lines
    (year, month, day, season, day_count, month_count, season_count, year_count, time_id);

load data infile '/var/lib/mysql/dw_data/mysqlData/user_schema'
    ignore into table user
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (profile_name, user_id);

load data infile '/var/lib/mysql/dw_data/mysqlData/movie_schema'
    ignore into table movie
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (product_id, @score_score, @emotion_score_emotion_score, @time_time_id)
    set
        time_time_id = NULLIF(@time_time_id, ''),
        score_score = NULLIF(@score_score, ''),
        emotion_score_emotion_score = NULLIF(@emotion_score_emotion_score, '');

load data infile '/var/lib/mysql/dw_data/mysqlData/actor_schema'
    ignore into table actor_movie
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (actor_name, product_id, movie_count);

load data infile '/var/lib/mysql/dw_data/mysqlData/director_schema'
    ignore into table director
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (director_name, product_id, movie_count);

load data infile '/var/lib/mysql/dw_data/mysqlData/label_schema'
    ignore into table label_movie
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (label_name, product_id, movie_count);

load data infile '/var/lib/mysql/dw_data/mysqlData/review_schema'
    ignore into table review
    fields terminated by ','
    OPTIONALLY ENCLOSED BY '"'
    lines terminated by '\n'
    ignore 1 lines
    (movie_product_id, user_user_id, up,down,score,time_stamp,emotion_score,id);