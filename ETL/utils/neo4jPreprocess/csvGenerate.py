import pickle
from utils.deduplicate.unionFind import UnionFind
from utils.neo4jPreprocess.model.movieNode import MovieNode
from utils.neo4jPreprocess.model.userNode import UserNode


class CsvGenerate:
    def __init__(self):
        print('in csv generate')
        self.raw_file_path = 'rawData/movies.txt'
        self.uf_file_path = 'processedData/component_mapping.pickle'
        self.uf_dict = {}

    def init_uf_dict(self):
        with open(self.uf_file_path, 'rb') as uf_file:
            self.uf_dict = pickle.load(uf_file)

    def process(self):
        self.init_uf_dict()
        node_num = 0
        is_first_row = True
        with open(self.raw_file_path, 'r', encoding='utf-8', errors='replace') as file:
            while is_first_row or file.readline():
                is_first_row = False
                block = [file.readline().split(':')[1].strip() for i in range(8)]
                if block[0] in self.uf_dict.keys():
                    a, b = block[3].split('/')
                    a, b = float(a), float(b)
                    block[3] = round(a/b if b != 0 else 0, 2)
                    movie_node = MovieNode(block[0])
                    user_node = UserNode(block[1], block[2])

                    node_num += 1
                    print(block)
                if node_num > 10:
                    break

        print(node_num)
    
    def greeting(self):
        print('hello my friend')
        print('Your warehouse is under construction.') 
