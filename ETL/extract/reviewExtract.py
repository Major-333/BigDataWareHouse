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
        block_num = 0
        with open(self.raw_data_path, 'r', encoding='iso-8859-1', errors='replace') as file:
            while True:
                if block_num % 5000 == 0:
                    print('[block_num]:', block_num)
                try:
                    buffer = []
                    tmp = file.readline()
                    while len(buffer) < 8 and tmp:
                        if len(buffer) == 0 and 'product/productId:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 1 and 'review/userId:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 2 and 'review/profileName:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 3 and 'review/helpfulness:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 4 and 'review/score:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 5 and 'review/time:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 6 and 'review/summary:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        elif len(buffer) == 7 and 'review/text:' in tmp:
                            buffer.append(tmp.split(':')[1].strip())
                        tmp = file.readline()
                    block = buffer
                    up_vote, down_vote = block[3].split('/')
                    block[3] = up_vote
                    block.insert(4, down_vote)
                    block_num += 1
                    if sorted(self.uf_dict[block[0]])[0] == block[0]:
                        self.df.loc[self.df.size] = block
                    if block_num % 10000 == 0:
                        self.df.to_csv(self.target_path, mode='a', index=False, header=False)
                        self.init_df()
                    is_same_error = False
                except:
                    if not is_same_error:
                        is_same_error = True
                        print('in error')
                        print(buffer)
                        continue
                    else:
                        print('will break')
                        print(buffer)
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
