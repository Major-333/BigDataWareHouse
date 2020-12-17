import os
import pickle
from lxml import etree
import time
import calendar
from datetime import datetime
import pandas as pd


class MovieExtract:

    def __init__(
            self,
            page_dir_path,
            uf_path,
            labels_path='./rawData/extraData/labels.csv',
            title_path='./rawData/extraData/titles_list.csv',
            target_path='./processedData/movies.csv',
    ):
        """
        初始化
        """
        self.page_dir_path = page_dir_path                          # 网页数据的文件夹路径
        self.uf_path = uf_path                                      # 并查集的文件路径
        self.labels_path = labels_path                              # 电影风格文件路径
        self.title_path = title_path                                # 电影名称文件路径
        self.target_path = target_path                              # 电影 big table 的存储路径

        self.uf_dict = {}                                           # 并查集数据结构
        self.movie_dict = {}
        self.movie_df = pd.DataFrame()                              # 电影信息
        self.label_df = pd.read_csv(self.labels_path, header=0)     # 导入 label data frame
        self.title_df = pd.read_csv(self.title_path, header=0)      # 导入 title data frame

        self.init_uf_dict()                                         # 导入并查集数据
        self.init_movie_df()                                        # 初始化 movie data frame
        self.movie_df.to_csv(self.target_path, index=False)

    def run(self):
        """
        从webPages文件下遍历网页文件，将id list中的网页进行信息抽取
        """

        for root_path, _, file_names in os.walk(self.page_dir_path):
            for index, file_name in enumerate(file_names):
                p_id = file_name.split('.')[0][-10:]

                if p_id in self.uf_dict.keys():
                    self.movie_dict['p_id'] = p_id
                    self.get_labels(p_id)
                    html = etree.parse(os.path.join(root_path, file_name), etree.HTMLParser())
                    self.get_title(p_id, html)
                    try:

                        node_list = html.xpath('//div[@id="detailBullets_feature_div"]')[1].xpath('ul/li')
                        for node in node_list:
                            key = node.xpath('span/span[1]/text()')[0].split(':')[0].strip()
                            if 'Actors' in key:
                                self.get_actors(node)
                            elif 'Release date' in key:
                                self.get_release_time(node)
                            elif 'Director' in key:
                                self.get_director(node)
                    except :
                        # print('[index]:', index, ' except')
                        # TODO: 可能是第二种页面类型 换解析方法
                        pass
                    finally:
                        self.add_movie()
                if index % 1000 == 0:
                    print('[index]:', index)
                if index % 10000 == 0:
                    self.movie_df.to_csv(self.target_path, mode='a', index=False, header=False)
                    self.init_movie_df()
            self.movie_df.to_csv(self.target_path, mode='a', index=False, header=False)
            break

    def get_actors(self, node, page_type=1):
        """
        从一个网页中抽取所有演员信息
        """
        if page_type == 1:
            self.movie_dict['actor_list'] = [actor.strip() for actor in node.xpath('span/span[2]/text()')[0].split(',')]
        elif page_type == 2:
            pass

    def get_release_time(self, node, page_type=1):
        """
        从一个网页中抽取电影上映时间
        """
        if page_type == 1:
            release_time = node.xpath('span/span[2]/text()')[0]
            year = int(release_time.split(',')[1].strip())
            month_str = release_time.split(',')[0].strip().split(' ')[0].strip()
            month = int(list(calendar.month_name).index(month_str))
            day = int(release_time.split(',')[0].strip().split(' ')[1].strip())
            dt = datetime(year, month, day)
            self.movie_dict['release_time'] = dt.strftime('%Y-%m-%d')
        elif page_type == 2:
            pass

    def get_director(self, node, page_type=1):
        """
         从一个网页中抽取导演信息
        """
        if page_type == 1:
            self.movie_dict['director'] = node.xpath('span/span[2]/text()')[0].strip()
        elif page_type == 2:
            pass

    def get_labels(self, p_id):
        """
        获取电影风格
        """
        data = self.label_df.loc[self.label_df['p_id'] == p_id]
        self.movie_dict['label_list'] = eval(data.iloc[0]['labels'])

    def get_title(self, p_id, html):
        """
        获取电影名称
        """
        # self.movie_dict['title'] = html.xpath('/span[@id="productTitle"]')
        data = html.xpath('//span[@id="productTitle"]/text()')
        if len(data) == 0:
            data = html.xpath('//h1[@class="_1GTSsh _2Q73m9"]/text()')
            self.movie_dict['title'] = '' if len(data) == 0 else data[0].split('[')[0].split('(')[0].strip()
        else:
            self.movie_dict['title'] = data[0].split('[')[0].split('(')[0].strip()

    def get_starring(self):
        """
        从一个网页中抽取电影的所有主演
        """
        pass

    def get_version(self):
        """
        从一个网页中抽取电影的版本
        """
        pass

    def init_uf_dict(self):
        with open(self.uf_path, 'rb') as uf_file:
            self.uf_dict = pickle.load(uf_file)

    def init_movie_df(self):
        self.movie_df = pd.DataFrame(
            columns=[
                'p_id',
                'title',
                'label_list',
                'director',
                'actor_list',
                'release_time',
                'version',
                'starring_list',
            ],
            index=[]
        )

    def add_movie(self):
        """
        将保存单个电影信息的 dict 插入到 data frame 中
        """
        self.movie_df.loc[self.movie_df.size] = self.movie_dict
        self.movie_dict = {}
