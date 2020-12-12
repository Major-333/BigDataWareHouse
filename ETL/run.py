import argparse
from utils.neo4jPreprocess.csvGenerate import CsvGenerate
from extract.movieExtract import MovieExtract


def get_args():

    parser = argparse.ArgumentParser(description='Extract movie and review information from source data and web pages, '
                                                 'Transform data into the files needed for each database entry, '
                                                 'Load the processed data. ',
                                     formatter_class=argparse.ArgumentDefaultsHelpFormatter)
    parser.add_argument('--web-path', type=str, default='./rawData/webPages/',
                        help='path of web pages dir', dest='page_dir_path')
    parser.add_argument('--movie-path', type=str, default='./rawData/movies.txt',
                        help='path of movies.txt', dest='raw_data_path')
    parser.add_argument('--uf-path', type=str, default='./processedData/component_mapping.pickle',
                        help='path of movies.txt', dest='uf_path')
    return parser.parse_args()


if __name__ == '__main__':
    args = get_args()

    print(args.page_dir_path)
    print(args.raw_data_path)
    print(args.uf_path)

    a = CsvGenerate()
    a.greeting()

    # movie_extract = MovieExtract
    # movie_extract.run()

