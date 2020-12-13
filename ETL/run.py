import argparse
from utils.neo4jPreprocess.csvGenerate import CsvGenerate
from extract.movieExtract import MovieExtract
from extract.reviewExtract import ReviewExtract
import pandas as pd

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
    parser.add_argument('-e', '--extract', nargs='?', help='extract review data from movie.txt')

    return parser.parse_args()


if __name__ == '__main__':
    args = get_args()

    print(args.page_dir_path)
    print(args.raw_data_path)
    print(args.uf_path)

    if args.extract:
        print('will extract review data from ' + args.raw_data_path)
        test = ReviewExtract(args.raw_data_path, args.uf_path)
        test.run()
    try:
        df = pd.read_csv('./processedData/reviews.csv')
        print('[review rows num]:', df.size)
    except _:
        print('review data needs to be extracted first')
