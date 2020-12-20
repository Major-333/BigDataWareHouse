import pandas as pd
import numpy as np
from datetime import datetime
import os
import numexpr as ne
import re


class MovieTransform:

    def __init__(self, movie_path='./processedData/movies.csv', schema_path='./processedData/mysqlData/'):
        self.movie_path = movie_path
        self.schema_path = schema_path
        self.raw_df = pd.read_csv(movie_path)
        self.generate_schemas()

    def generate_schemas(self):
        self.generate_day_schema()
        # self.generate_director_schema()
        # self.generate_actor_schema()
        # self.generate_label_movie()

    def generate_day_schema(self):
        time_df = self.raw_df[['p_id', 'release_time']].copy()

        time_df.loc[:, 'release_time'] = pd.to_datetime(time_df['release_time'], errors='coerce')
        time_df = time_df[time_df['release_time'].notnull()]
        year, month, day = time_df['release_time'].dt.year, time_df['release_time'].dt.month, time_df['release_time'].dt.day

        day_schema = pd.concat([year, month, day, time_df['p_id']], axis=1)
        day_schema.columns = ['year', 'month', 'day', 'p_id']
        day_schema['season'] = 4
        day_schema.loc[day_schema['month'] <= 9, 'season'] = 3
        day_schema.loc[day_schema['month'] <= 6, 'season'] = 2
        day_schema.loc[day_schema['month'] <= 3, 'season'] = 1
        print(day_schema)
        day_cnt = day_schema[['year', 'month', 'day']].value_counts().reset_index()
        day_cnt.columns = ['year', 'month', 'day', 'day_count']

        month_cnt = day_schema[['year', 'month']].value_counts().reset_index()
        month_cnt.columns = ['year', 'month', 'month_count']

        season_cnt = day_schema[['year', 'season']].value_counts().reset_index()
        season_cnt.columns = ['year', 'season', 'season_count']

        year_cnt = day_schema[['year']].value_counts().reset_index()
        year_cnt.columns = ['year', 'year_count']

        day_schema = day_schema.merge(day_cnt, how='outer', on=['year', 'month', 'day'])
        day_schema = day_schema.merge(month_cnt, how='outer', on=['year', 'month'])
        day_schema = day_schema.merge(season_cnt, how='outer', on=['year', 'season'])
        day_schema = day_schema.merge(year_cnt, how='outer', on='year')

        day_schema_zipped = day_schema.drop_duplicates(subset=['year', 'month', 'day'])[['year', 'month', 'day', 'p_id']].copy()
        day_schema_zipped['day_id'] = range(day_schema_zipped.shape[0])
        print(day_schema_zipped)

        day_schema = day_schema.merge(day_schema_zipped[['year', 'month', 'day', 'day_id']], how='outer', on=['year', 'month', 'day'])

        print(day_schema)
        day_schema.to_csv(os.path.join(self.schema_path, 'day_schema'), index=False)

        # insert day_id into movie.csv

        movie_schema = pd.read_csv(os.path.join(self.schema_path, 'movie_schema'))[['product_id', 'score', 'emotion_score']]
        day_schema_zipped = day_schema_zipped.rename(columns={'p_id': 'product_id'})
        print(movie_schema)
        print(day_schema_zipped)
        day_schema_zipped.astype({'day_id': 'Int64'})
        movie_schema.astype({'score': 'Int64', 'emotion_score': 'Int64'})
        movie_schema = movie_schema.merge(day_schema_zipped[['product_id', 'day_id']], how='outer', on='product_id')
        # movie_schema.loc[movie_schema['score'].isnull(), 'score'] = -1
        # movie_schema.loc[movie_schema['emotion_score'].isnull(), 'emotion_score'] = -1
        # movie_schema.loc[movie_schema['day_id'].isnull(), 'day_id'] = -1
        # movie_schema['score'].astype(int)
        movie_schema[['score', 'emotion_score', 'day_id']] = movie_schema[['score', 'emotion_score', 'day_id']]
        print(movie_schema)

        movie_schema.to_csv(os.path.join(self.schema_path, 'movie_schema'), index=False)

    def generate_version_schema(self):
        version_df = self.raw_df['version']
        version_schema = pd.DataFrame(version_df[version_df.notnull()].drop_duplicates())
        version_schema['version_id'] = range(version_schema.shape[0])
        version_schema.to_csv(os.path.join(self.schema_path, 'version_schema'), index=False)

    def generate_director_schema(self):
        """
        generate director schema
        """
        director_df = self.raw_df[['director', 'p_id']]
        director_df = director_df[director_df['director'].notnull()]
        # next line should use regex and could be optimized
        director_df['director'] = director_df['director'].apply(lambda x: np.array(
            [item.strip() for item in
             re.split(',|;', x[1:-1].replace('\'', '').replace('\"', '').replace('*', '').replace('-', '').strip())
             if len(item.strip()) > 0]))
        director_schema = pd.DataFrame(director_df).explode('director')
        director_schema = director_schema[director_schema['director'].notnull()]

        director_count = director_schema['director'].value_counts().reset_index().rename(columns={'index': 'director', 'director': 'count'})
        director_schema = director_schema.merge(director_count, how='outer', on='director')
        print(director_schema)
        director_schema.to_csv(os.path.join(self.schema_path, 'director_schema'), index=False)

    def generate_actor_schema(self):
        self.raw_df = self.raw_df.rename(columns={'actor_list': 'actor'})
        actor_df = self.raw_df[['actor', 'p_id']]
        actor_df = actor_df[actor_df['actor'].notnull()]
        # next line should use regex and could be optimized
        actor_df['actor'] = actor_df['actor'].apply(lambda x: np.array(
            [item.strip() for item in
             re.split(',|;', x[1:-1].replace('\'', '').replace('\"', '').replace('*', '').replace('-', '').strip())
             if len(item.strip()) > 0]))
        actor_schema = pd.DataFrame(actor_df).explode('actor')
        actor_schema = actor_schema[actor_schema['actor'].notnull()]

        actor_count = actor_schema['actor'].value_counts().reset_index().rename(columns={'index': 'actor', 'actor': 'count'})
        actor_schema = actor_schema.merge(actor_count, how='outer', on='actor')
        print(actor_schema)
        actor_schema.to_csv(os.path.join(self.schema_path, 'actor_schema'), index=False)

    def generate_label_movie(self):
        self.raw_df = self.raw_df.rename(columns={'label_list': 'label'})
        label_df = self.raw_df[['label', 'p_id']]
        label_df = label_df[label_df['label'].notnull()]
        # next line should use regex and could be optimized
        label_df['label'] = label_df['label'].apply(lambda x: np.array(
            [item.strip() for item in
             re.split(',|;', x[1:-1].replace('\'', '').replace('\"', '').replace('*', '').replace('-', '').strip())
             if len(item.strip()) > 0]))
        label_schema = pd.DataFrame(label_df).explode('label')
        label_schema = label_schema[label_schema['label'].notnull()]

        label_count = label_schema['label'].value_counts().reset_index().rename(columns={'index': 'label', 'label': 'count'})
        label_schema = label_schema.merge(label_count, how='outer', on='label')
        print(label_schema)
        label_schema.to_csv(os.path.join(self.schema_path, 'label_schema'), index=False)
