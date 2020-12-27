import argparse
from extract.movieExtract import MovieExtract
from extract.reviewExtract import ReviewExtract
from transform.mysqlTransform.movieTransform import MovieTransform
from transform.mysqlTransform.reviewTransform import ReviewTransform
import pandas as pd
import pickle


def get_args():

    parser = argparse.ArgumentParser(description='Extract movie and review information from source data and web pages, '
                                                 'Transform data into the files needed for each database entry, '
                                                 'Load the processed data. ',
                                     formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument('--web_path', type=str, default='./rawData/webPages/',
                        help='path of web pages dir', dest='page_dir_path')
    parser.add_argument('--movie_path', type=str, default='./rawData/movies.txt',
                        help='path of movies.txt', dest='raw_data_path')
    parser.add_argument('--uf_path', type=str, default='./processedData/component_mapping.pickle',
                        help='path of union find data', dest='uf_path')
    parser.add_argument('--label_path', type=str, default='./processedData/TODO',  # TODO
                        help='path of movie label', dest='label_path')
    parser.add_argument('--review_extract', nargs='?', help='extract review data from movie.txt')
    parser.add_argument('--movie_extract', nargs='?', help='extract movie data from web pages')
    return parser.parse_args()


if __name__ == '__main__':
    args = get_args()

    # print(args.page_dir_path)
    # print(args.raw_data_path)
    # print(args.uf_path)

    if args.review_extract:
        print('will extract review data from ' + args.raw_data_path)
        review_extract = ReviewExtract(args.raw_data_path, args.uf_path)
        review_extract.run()
    if args.movie_extract:
        print('will extract movie data from ' + args.page_dir_path)
        movie_extract = MovieExtract(args.page_dir_path, args.uf_path)
        movie_extract.run()

    try:
        df = pd.read_csv('./processedData/reviews.csv')
        print('[review rows num]:', df.shape[0])
    except _:
        print('review data needs to be extracted first')

    # junk code begins

    uf_path = './processedData/component_mapping.pickle'

    with open(uf_path, 'rb') as uf_file:
        uf_dict = pickle.load(uf_file)

    print('number of product is: ', len(uf_dict.keys()))
    movie_id_list = [item for item in uf_dict.keys() if item == sorted(uf_dict[item])[0]]
    print('number of movie is: ', len(movie_id_list))

    print('will check review.csv')
    df = pd.read_csv('./processedData/reviews.csv')
    df = df['product_id']
    print('number of review \'s row', df.shape[0])
    df = df.drop_duplicates()
    print('number of review \'s row', df.shape[0])

    print('will transform for mysql')

    # review_transform = ReviewTransform()
    # movie_transform = MovieTransform()

    review = pd.read_csv('./processedData/reviews.csv')
    movie = pd.read_csv('./processedData/movies.csv')
    review = review.rename(columns={'product_id': 'p_id'})

    print(review.shape[0])
    print(movie.shape[0])
    print(movie[movie['p_id'].isin(review['p_id'])]['p_id'].shape[0])
    swq = movie[~movie['p_id'].isin(review['p_id'])]['p_id']

    movie_schema_df = pd.read_csv('./processedData/mysqlData/movie_schema')
    print(movie_schema_df.shape[0])
    print(swq.shape[0])
