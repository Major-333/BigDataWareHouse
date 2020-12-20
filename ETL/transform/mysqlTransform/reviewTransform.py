import pandas as pd
import numpy as np
from datetime import datetime
import os
import numexpr as ne
import re


class ReviewTransform:

    def __init__(self, review_path='./processedData/reviews.csv', schema_path='./processedData/mysqlData/'):
        self.review_path = review_path
        self.schema_path = schema_path
        self.raw_df = pd.read_csv(review_path)
        self.generate_schemas()

    def generate_schemas(self):
        self.generate_user_schema()
        self.generate_review_schema()

    def generate_user_schema(self):
        user_schema = pd.DataFrame(self.raw_df['profile_name'])
        user_schema['user_id'] = self.raw_df['user_id']
        print(user_schema)
        user_schema.to_csv(os.path.join(self.schema_path, 'user_schema'), index=False)

    def generate_review_schema(self):
        """
        generate review_schema, score_schema, emotion_score_schema
        and insert score and emotion_score into movie schema
        """
        # generate review_schema
        review_schema = self.raw_df.drop(['profile_name', 'text', 'summary'], axis=1)
        print(review_schema)
        score = np.array(review_schema['score'])
        score = (score - np.min(score))/(np.max(score)-np.min(score))
        review_schema['emotion_score'] = score
        review_schema.to_csv(os.path.join(self.schema_path, 'review_schema'), index=False)

        # init movie_schema
        movie_schema = review_schema.groupby('product_id')[['score', 'emotion_score']].mean().round(2).reset_index()
        movie_schema['score'] = (movie_schema['score'] * 100).astype(int)
        movie_schema['emotion_score'] = (movie_schema['emotion_score'] * 100).astype(int)
        print(movie_schema)
        movie_schema.to_csv(os.path.join(self.schema_path, 'movie_schema'), index=False)

        # generate score_schema
        score_schema = pd.DataFrame(columns=['score'])
        score_schema['score'] = np.linspace(0, 500, 501)

        score_df = movie_schema['score'].value_counts().reset_index().rename(columns={'score': 'count', 'index': 'score'})

        score_schema = score_schema.merge(score_df, how='outer', on='score')
        score_schema['count'][score_schema['count'].isnull()] = 0
        score_schema = score_schema.astype(int)
        score_schema.to_csv(os.path.join(self.schema_path, 'score_schema'), index=False)

        # generate emotion_score_schema
        emotion_score_schema = pd.DataFrame(columns=['emotion_score'])
        emotion_score_schema['emotion_score'] = np.linspace(0, 100, 101)

        emotion_score_df = movie_schema['emotion_score'].value_counts().reset_index().rename(columns={'emotion_score': 'count', 'index': 'emotion_score'})

        emotion_score_schema = emotion_score_schema.merge(emotion_score_df, how='outer', on='emotion_score')
        emotion_score_schema['count'][emotion_score_schema['count'].isnull()] = 0
        emotion_score_schema = emotion_score_schema.astype(int)
        emotion_score_schema.to_csv(os.path.join(self.schema_path, 'emotion_score_schema'), index=False)
