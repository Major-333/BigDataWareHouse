import pickle
import pandas as pd


class ReviewExtract:

    def __init__(self, raw_data_path, uf_path, target_path='./processedData/reviews.csv'):
        """
        初始化
        """
        self.raw_data_path = raw_data_path  # movies.txt 路径文件
        self.uf_path = uf_path  # 并查集文件路径
        self.uf_dict = {}  # 并查集数据结构
        self.df = pd.DataFrame()
        self.init_df()
        self.target_path = target_path  # big table的存储路径
        self.df.to_csv(self.target_path, index=False)

    def run(self):
        """
        处理 movies.txt
        默认保存到./
        """
        self.init_uf_dict()
        is_first_row = True  # 第一行不需要额外读取空行
        block_num = 0
        with open(self.raw_data_path, 'r', encoding='utf-8', errors='replace') as file:
            while is_first_row or file.readline():
                is_first_row = False
                try:
                    block = [file.readline().split(':')[1].strip() for i in range(8)]
                    up_vote, down_vote = block[3].split('/')
                    block[3] = up_vote
                    block.insert(4, down_vote)
                    block_num += 1
                    if block[0] in self.uf_dict.keys():
                        self.df.loc[self.df.size] = block
                    if block_num % 1000 == 0:
                        print('[block_num]:', block_num)
                    if block_num % 10000 == 0:
                        self.df.to_csv(self.target_path, mode='a', index=False, header=False)
                        self.init_df()
                except _:
                    break
        self.df.to_csv(self.target_path, mode='a', index=False, header=False)

    def init_uf_dict(self):
        with open(self.uf_path, 'rb') as uf_file:
            self.uf_dict = pickle.load(uf_file)

    def init_df(self):
        self.df = pd.DataFrame(
            columns=[
                'product_id',
                'user_id',
                'profile_name',
                'up_vote',
                'down_vote',
                'score',
                'time',
                'summary',
                'text'
            ],
            index=[]
        )
