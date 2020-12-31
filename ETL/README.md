# how to use ETL

### 奶牛快传(extraData.zip)：
 https://cowtransfer.com/s/d9fbf97ee38c42

 ## Folder Structure

```
.
├── ETL                         # ETL脚本项目
│   ├── processedData           # 处理后的数据
│   │   └── mysqlData           # mysql入库需要的csv文件
│   ├── rawData                 # 存放原始数据集(movies.txt)
│   │   ├── extraData           # 额外的数据，从链接下载后获取
│   │   └── webPages            # 网页数据
│   │   └── movies.txt          # SNAP 原始数据
│   └── utils                   # ETL工具模块
│       ├── deduplicate         # 原始数据去重的模块
│       └── neo4jPreprocess     # 处理数据为neo4j所需的csv格式的模块
```

## how to start

```bash
$ python run.py --review-extract --movie-extract --review-transform --movie-transform
```

- 🎉 Extract should be done before Transform

