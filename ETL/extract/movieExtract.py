import os


class MovieExtract:

    def __init__(self, page_dir_path, uf_path, labels_path):
        """
        初始化
        """
        self.page_dir_path = page_dir_path  # 网页数据的文件夹路径
        self.uf_path = uf_path  # 并查集的文件路径
        self.labels_path = labels_path  # 电影风格文件路径

    def run(self):
        """
        从webPages文件下遍历网页文件，将id list中的网页进行信息抽取
        :return:
        """
        # file_id_list =
        for i, j, k in os.walk(self.page_dir_path):
            print(i, j, k)

    def get_actors(self):
        """
        从一个网页中抽取所有演员信息
        :return:
        """
        pass

    def get_release_time(self):
        """
        从一个网页中抽取电影上映时间
        :return:
        """
        pass

    def get_labels(self):
        """
        从一个网页中抽取电影的电影风格
        :return:
        """
        pass

    def get_starring(self):
        """
        从一个网页中抽取电影的所有主演
        :return:
        """
        pass

    def get_version(self):
        """
        从一个网页中抽取电影的版本
        :return:
        """
        pass