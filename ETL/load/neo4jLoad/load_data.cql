// create index and constraint
USING PERIODIC COMMIT 500
LOAD CSV WITH HEADERS FROM 'file:///movie_schema' AS row
merge (m:Movie {product_id: row.product_id})
SET m.score = toInteger(row.score)
SET m.emotion_score = toInteger(row.emotion_score)
SET m.title = row.title;

// actor_schema
using periodic commit 500
load csv with headers from 'file:///actor_schema' as row
match (m:Movie) where m.product_id = row.p_id
merge (a:Actor {actor: row.actor})
set a.count = toInteger(row.count)
merge (a) -[:act_movie]->(m);
// merge (m) -[:movie_act]->(a);


// director_schema
using periodic commit 500
load csv with headers from 'file:///director_schema' as row
match (m:Movie) where m.product_id = row.p_id
merge (d:Director {director: row.director})
set d.count = toInteger(row.count)
merge (d) -[:director_movie]->(m);
// merge (m) -[:movie_director]->(d);

// label_schema
using periodic commit 500
load csv with headers from 'file:///label_schema' as row
match (m:Movie) where m.product_id = row.p_id
merge (l:Label {label: row.label})
set l.count = toInteger(row.count)
merge (l) -[:label_movie]->(m);
// merge (m) -[:movie_label]->(l);

// time_schema
using periodic commit 500
load csv with headers from 'file:///day_schema' as row
merge (t:Time {day_id: row.day_id})
set t.year = toInteger(row.year)
set t.season = toInteger(row.season)
set t.month = toInteger(row.month)
set t.day = toInteger(row.day)
set t.year_count = toInteger(row.year_count)
set t.season_count = toInteger(row.season_count)
set t.month_count = toInteger(row.month_count)
set t.day_count = toInteger(row.day_count);

// movie-time
using periodic commit 500
load csv with headers from 'file:///movie_schema' as row
match (m:Movie) where m.product_id = row.product_id
match (t:Time) where t.day_id = row.day_id
// merge (m) -[:movie_time]->(t)
merge (t) -[:time_movie]->(m);

// pre-calculate 
match (a:Actor)-[:act_movie]->(:Movie)<-[:act_movie]-(b:Actor)
where a.actor <> b.actor and size((b)-->(a))=0
merge (a)-[r:actor_actor]->(b)
set r.count = toInteger(0);

match (a:Actor)-[:act_movie]->(:Movie)<-[:act_movie]-(b:Actor)
where a.actor <> b.actor and size((b)-->(a))=0
with a,b
match (a)-[r]->(b)
set r.count = r.count+1;

match (a:Actor)-[:act_movie]->(:Movie)<-[:director_movie]-(b:Director)
merge (a)-[r:actor_director]->(b)
set r.count = case r.count
when r.count then toInteger(0)
else r.count+1
end;

match (a:Director)-[:director_movie]->(:Movie)<-[:director_movie]-(b:Director)
where a.director <> b.director and size((b)-->(a))=0
merge (a)-[r:director_director]->(b)
set r.count = toInteger(0);

match (a:Director)-[:director_movie]->(:Movie)<-[:director_movie]-(b:Director)
where a.director <> b.director and size((b)-->(a))=0
with a,b
match (a)-[r]->(b)
set r.count = r.count+1;

