use MOVIE_STAR;

-- 写一个查询X年以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_year;
DELIMITER $$
create procedure find_movie_by_year(IN ye integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with year_c as (select distinct (year), year_count
                        from time
                        where year > ye)
        select sum(year_count)
        from year_c;
    elseIF after = 'less' then
        with year_c as (select distinct (year), year_count
                        from time
                        where year < ye)
        select sum(year_count)
        from year_c;
    ELSEIF after = 'equal' then
        select distinct year_count
        from time
        where year = ye;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年Y月以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_month;
DELIMITER $$
create procedure find_movie_by_month(IN ye integer, IN mon integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with month_c as (select distinct (year), month, month_count
                         from time
                         where year > ye
                            or (year = ye
                             and month > mon))
        select sum(month_count)
        from month_c;
    elseif after = 'less' then
        with month_c as (select distinct (year), month, month_count
                         from time
                         where year < ye
                            or (year = ye
                             and month < mon))
        select sum(month_count)
        from month_c;
    ELSEIF after = 'equal' then
        select distinct month_count
        from time
        where year = ye
          and month = mon;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年Y月Z日以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_day;
DELIMITER $$
create procedure find_movie_by_day(IN ye integer, IN mon integer, IN da integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with dat_c as (select distinct (year), month, day, day_count
                       from time
                       where (year = ye and month = mon and day > da)
                          or ((year = ye and month > mon) or year > ye))
        select sum(day_count)
        from dat_c;
    elseif after = 'less' then
        with day_c as (select distinct (year), month, day, day_count
                       from time
                       where (year = ye and month = mon and day < da)
                          or ((year = ye and month < mon) or year < ye))
        select sum(day_count)
        from day_c;
    ELSEIF after = 'equal' then
        select distinct day_count
        from time
        where year = ye
          and month = mon
          and day = da;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年W季度的电影总数 返回电影数量
drop procedure if exists find_movie_by_season;
DELIMITER $$
create procedure find_movie_by_season(IN ye integer, IN sea integer)
BEGIN
    select distinct (season_count)
    from time
    where year = ye
      and season = sea;
end $$
DELIMITER ;


-- XX导演共有多少电影 返回电影列表
drop procedure if exists find_director_movie;
DELIMITER $$
CREATE PROCEDURE find_director_movie(IN dir varchar(255))
BEGIN
    select director_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from director
             natural join movie
    where director_name = dir;
end $$
DELIMITER ;

-- XX演员共有多少电影 返回电影列表
drop procedure if exists find_actor_movie;
DELIMITER $$
CREATE PROCEDURE find_actor_movie(IN act varchar(255))
BEGIN
    select actor_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from actor_movie
             natural join movie
    where actor_name = act;
end $$
DELIMITER ;

-- XX标签共有多少电影 返回电影列表
drop procedure if exists find_label_movie;
DELIMITER $$
CREATE PROCEDURE find_label_movie(IN lab varchar(255))
BEGIN
    select label_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from label_movie
             natural join movie
    where label_name = lab;
end $$
DELIMITER ;

-- XX分数以上/以下的有多少电影 返回电影数量
drop procedure if exists find_movie_count_by_score;
DELIMITER $$
CREATE PROCEDURE find_movie_count_by_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select sum(count)
        from score
        where score > sco;
    ELSEIF larger = 'less' THEN
        select sum(count)
        from score
        where score < sco;
    ELSEIF larger = 'equal' THEN
        select count
        from score
        where score = sco;
    END IF;
END $$
DELIMITER ;

-- 查询某个分数的电影 返回列表
drop procedure if exists find_movie_by_score;
DELIMITER $$
CREATE PROCEDURE find_movie_by_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select *
        from movie
        where score_score > sco
        LIMIT 200;
    ELSEIF larger = 'less' THEN
        select *
        from movie
        where score_score < sco
        LIMIT 200;
    ELSEIF larger = 'equal' THEN
        select *
        from movie
        where score_score = sco
        LIMIT 10;
    end if;
END
$$
DELIMITER ;


-- 情感偏向正负向的电影 返回电影数量
drop procedure if exists find_movie_count_by_emo_score;
DELIMITER $$
CREATE PROCEDURE find_movie_count_by_emo_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select sum(count)
        from emotion_score
        where emotion_score > sco;
    ELSEIF larger = 'less' THEN
        select sum(count)
        from emotion_score
        where emotion_score < sco;
    ELSEIF larger = 'equal' THEN
        select count
        from emotion_score
        where emotion_score = sco;

    END IF;
END $$
DELIMITER ;

-- 查询某个情感分数的电影
drop procedure if exists find_movie_by_emotion_score;
DELIMITER $$
CREATE PROCEDURE find_movie_by_emotion_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select *
        from movie
        where emotion_score_emotion_score > sco
        LIMIT 200;
    ELSEIF larger = 'less' THEN
        select *
        from movie
        where emotion_score_emotion_score < sco
        LIMIT 200;
    ELSEIF larger = 'equal' THEN
        select *
        from movie
        where emotion_score_emotion_score = sco
        LIMIT 10;
    end if;
END
$$
DELIMITER ;


-- 查询和X导演合作过的演员有哪些 返回演员名字和电影id（后最好改成title）
drop procedure if exists find_actor_by_director;
DELIMITER $$
CREATE PROCEDURE find_actor_by_director(IN dir varchar(255))
BEGIN
    select actor_name, count(product_id) cooperation
    from actor_movie
             natural join director
    where director_name = dir
    group by actor_name
    order by cooperation;
END $$
DELIMITER ;

-- 查询和X导演合作过的导演有哪些 返回导演名字和电影id（后最好改成title）
drop procedure if exists find_director_by_director;
DELIMITER $$
CREATE PROCEDURE find_director_by_director(IN dir varchar(255))
BEGIN
    select D2.director_name, count(D2.product_id) cooperation
    from director D1
             join director D2 on D1.product_id = D2.product_id
    where D1.director_name = dir
      and D2.director_name <> dir
    group by D2.director_name
    order by cooperation;
END $$
DELIMITER ;


-- 查询和Y演员合作过的导演有哪些 返回导演名字和电影id（后最好改成title）
drop procedure if exists find_director_by_actor;
DELIMITER $$
CREATE PROCEDURE find_director_by_actor(IN act varchar(255))
BEGIN
    select director_name, count(product_id) cooperation
    from actor_movie
             natural join director
    where actor_name = act
    group by director_name
    order by cooperation;
END $$
DELIMITER ;


-- 查询和Y演员合作过的演员有哪些 返回演员名字和电影id（后最好改成title）
drop procedure if exists find_actor_by_actor;
DELIMITER $$
CREATE PROCEDURE find_actor_by_actor(IN act varchar(255))
BEGIN
    select A2.actor_name, count(A1.product_id) cooperation
    from actor_movie A1
             join actor_movie A2
                  on A1.product_id = A2.product_id
    where A1.actor_name = act
      and A2.actor_name <> act
    group by A2.actor_name
    order by cooperation;
END $$
DELIMITER ;


select D2.director_name, count(D2.product_id) cooperation
from director D1
         join director D2 on D1.product_id = D2.product_id
where D1.director_name = 'ChiHwa Chen'
  and D2.director_name <> 'ChiHwa Chen'
group by D2.director_name
order by cooperation;-- 写一个查询X年以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_year;
DELIMITER $$
create procedure find_movie_by_year(IN ye integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with year_c as (select distinct (year), year_count
                        from time
                        where year > ye)
        select sum(year_count)
        from year_c;
    elseIF after = 'less' then
        with year_c as (select distinct (year), year_count
                        from time
                        where year < ye)
        select sum(year_count)
        from year_c;
    ELSEIF after = 'equal' then
        select distinct year_count
        from time
        where year = ye;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年Y月以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_month;
DELIMITER $$
create procedure find_movie_by_month(IN ye integer, IN mon integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with month_c as (select distinct (year), month, month_count
                         from time
                         where year > ye
                            or (year = ye
                             and month > mon))
        select sum(month_count)
        from month_c;
    elseif after = 'less' then
        with month_c as (select distinct (year), month, month_count
                         from time
                         where year < ye
                            or (year = ye
                             and month < mon))
        select sum(month_count)
        from month_c;
    ELSEIF after = 'equal' then
        select distinct month_count
        from time
        where year = ye
          and month = mon;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年Y月Z日以后/前的电影总数 返回电影数量
drop procedure if exists find_movie_by_day;
DELIMITER $$
create procedure find_movie_by_day(IN ye integer, IN mon integer, IN da integer, IN after varchar(20))
BEGIN
    if after = 'greater' then
        with dat_c as (select distinct (year), month, day, day_count
                       from time
                       where (year = ye and month = mon and day > da)
                          or ((year = ye and month > mon) or year > ye))
        select sum(day_count)
        from dat_c;
    elseif after = 'less' then
        with day_c as (select distinct (year), month, day, day_count
                       from time
                       where (year = ye and month = mon and day < da)
                          or ((year = ye and month < mon) or year < ye))
        select sum(day_count)
        from day_c;
    ELSEIF after = 'equal' then
        select distinct day_count
        from time
        where year = ye
          and month = mon
          and day = da;
    end if;
end $$
DELIMITER ;

-- 写一个查询X年W季度的电影总数 返回电影数量
drop procedure if exists find_movie_by_season;
DELIMITER $$
create procedure find_movie_by_season(IN ye integer, IN sea integer)
BEGIN
    select distinct (season_count)
    from time
    where year = ye
      and season = sea;
end $$
DELIMITER ;


-- XX导演共有多少电影 返回电影列表
drop procedure if exists find_director_movie;
DELIMITER $$
CREATE PROCEDURE find_director_movie(IN dir varchar(255))
BEGIN
    select director_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from director
             natural join movie
    where director_name = dir;
end $$
DELIMITER ;

-- XX演员共有多少电影 返回电影列表
drop procedure if exists find_actor_movie;
DELIMITER $$
CREATE PROCEDURE find_actor_movie(IN act varchar(255))
BEGIN
    select actor_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from actor_movie
             natural join movie
    where actor_name = act;
end $$
DELIMITER ;

-- XX标签共有多少电影 返回电影列表
drop procedure if exists find_label_movie;
DELIMITER $$
CREATE PROCEDURE find_label_movie(IN lab varchar(255))
BEGIN
    select label_name, movie_count, product_id, title, emotion_score_emotion_score, score_score, time_time_id
    from label_movie
             natural join movie
    where label_name = lab;
end $$
DELIMITER ;

-- XX分数以上/以下的有多少电影 返回电影数量
drop procedure if exists find_movie_count_by_score;
DELIMITER $$
CREATE PROCEDURE find_movie_count_by_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select sum(count)
        from score
        where score > sco;
    ELSEIF larger = 'less' THEN
        select sum(count)
        from score
        where score < sco;
    ELSEIF larger = 'equal' THEN
        select count
        from score
        where score = sco;
    END IF;
END $$
DELIMITER ;

-- 查询某个分数的电影 返回列表
drop procedure if exists find_movie_by_score;
DELIMITER $$
CREATE PROCEDURE find_movie_by_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select *
        from movie
        where score_score > sco
        LIMIT 200;
    ELSEIF larger = 'less' THEN
        select *
        from movie
        where score_score < sco
        LIMIT 200;
    ELSEIF larger = 'equal' THEN
        select *
        from movie
        where score_score = sco
        LIMIT 10;
    end if;
END
$$
DELIMITER ;


-- 情感偏向正负向的电影 返回电影数量
drop procedure if exists find_movie_count_by_emo_score;
DELIMITER $$
CREATE PROCEDURE find_movie_count_by_emo_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select sum(count)
        from emotion_score
        where emotion_score > sco;
    ELSEIF larger = 'less' THEN
        select sum(count)
        from emotion_score
        where emotion_score < sco;
    ELSEIF larger = 'equal' THEN
        select count
        from emotion_score
        where emotion_score = sco;

    END IF;
END $$
DELIMITER ;

-- 查询某个情感分数的电影
drop procedure if exists find_movie_by_emotion_score;
DELIMITER $$
CREATE PROCEDURE find_movie_by_emotion_score(IN sco integer, IN larger varchar(20))
BEGIN
    IF larger = 'greater' THEN
        select *
        from movie
        where emotion_score_emotion_score > sco
        LIMIT 200;
    ELSEIF larger = 'less' THEN
        select *
        from movie
        where emotion_score_emotion_score < sco
        LIMIT 200;
    ELSEIF larger = 'equal' THEN
        select *
        from movie
        where emotion_score_emotion_score = sco
        LIMIT 10;
    end if;
END
$$
DELIMITER ;


-- 查询和X导演合作过的演员有哪些 返回演员名字和电影id（后最好改成title）
drop procedure if exists find_actor_by_director;
DELIMITER $$
CREATE PROCEDURE find_actor_by_director(IN dir varchar(255))
BEGIN
    select actor_name, count(product_id) cooperation
    from actor_movie
            join director on director.product_id = actor_movie.product_id
    where director_name = dir
    group by actor_name
    order by cooperation;
END $$
DELIMITER ;

-- 查询和X导演合作过的导演有哪些 返回导演名字和电影id（后最好改成title）
drop procedure if exists find_director_by_director;
DELIMITER $$
CREATE PROCEDURE find_director_by_director(IN dir varchar(255))
BEGIN
    select D2.director_name, count(D2.product_id) cooperation
    from director D1
             join director D2 on D1.product_id = D2.product_id
    where D1.director_name = dir
      and D2.director_name <> dir
    group by D2.director_name
    order by cooperation;
END $$
DELIMITER ;


-- 查询和Y演员合作过的导演有哪些 返回导演名字和电影id（后最好改成title）
drop procedure if exists find_director_by_actor;
DELIMITER $$
CREATE PROCEDURE find_director_by_actor(IN act varchar(255))
BEGIN
    select director_name, count(product_id) cooperation
    from actor_movie
             join director on director.product_id = actor_movie.product_id
    where actor_name = act
    group by director_name
    order by cooperation;
END $$
DELIMITER ;


-- 查询和Y演员合作过的演员有哪些 返回演员名字和电影id（后最好改成title）
drop procedure if exists find_actor_by_actor;
DELIMITER $$
CREATE PROCEDURE find_actor_by_actor(IN act varchar(255))
BEGIN
    select A2.actor_name, count(A1.product_id) cooperation
    from actor_movie A1
             join actor_movie A2
                  on A1.product_id = A2.product_id
    where A1.actor_name = act
      and A2.actor_name <> act
    group by A2.actor_name
    order by cooperation;
END $$
DELIMITER ;


