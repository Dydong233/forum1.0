/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 8.0.28 : Database - forum
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`forum` /*!40100 DEFAULT CHARACTER SET utf8 */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `forum`;

/*Table structure for table `article` */

CREATE TABLE `article` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '文章唯一id',
  `author` varchar(255) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;

/*Data for the table `article` */

insert  into `article`(`id`,`author`,`title`,`content`) values (14,'admin','凸包','## 凸包\r\n---\r\n1.将点按x为第一关键字，y为第二关键字排序\r\n2.从左至右维护下半部分，从右至左维护上半部分\r\n3..维护一个队列保证如果前一个向量在当前向量的左侧则直接推入，在右侧一直迭代至左侧为止\r\n\r\n```cpp\r\n#include<iostream>\r\n#include<cstring>\r\n#include<cmath>\r\n#include<algorithm>\r\n#define x first\r\n#define y second\r\nusing namespace std;\r\ntypedef long long LL;\r\ntypedef pair<double,double> PDD;\r\nconst int N = 1e4+10;\r\nint n,stk[N];;\r\nPDD q[N];\r\nbool used[N];\r\nPDD operator - (PDD a,PDD b)    {\r\n    return {a.x-b.x,a.y-b.y};\r\n}\r\ndouble cross(PDD a,PDD b)\r\n{\r\n    return a.x*b.y-b.x*a.y;\r\n}\r\ndouble area(PDD a,PDD b,PDD c)\r\n{\r\n    return cross(b-a,c-a);\r\n}\r\ndouble get_dist(PDD a,PDD b)\r\n{\r\n    double dx=a.x-b.x;\r\n    double dy=a.y-b.y;\r\n    return sqrt(dx*dx+dy*dy);\r\n}\r\ndouble andrew()\r\n{\r\n    sort(q,q+n);\r\n    int top=0;\r\n    for(int i=0;i<n;i++)\r\n    {\r\n        while(top>=2&&area(q[stk[top-1]],q[stk[top]],q[i])<=0)\r\n        {\r\n            if(area(q[stk[top-1]],q[stk[top]],q[i])<0)\r\n                used[stk[top--]]=false;\r\n            else    top--;\r\n        }\r\n        stk[++top]=i;\r\n        used[i]=true;\r\n    }\r\n    used[0]=false;\r\n    for(int i=n-1;i>=0;i--)\r\n    {\r\n        if(used[i]) continue;\r\n        while(top>=2&&area(q[stk[top-1]],q[stk[top]],q[i])<=0)\r\n            used[stk[top--]]=false;\r\n        stk[++top]=i;\r\n        used[i]=true;\r\n    }\r\n    double res=0;\r\n    for(int i=2;i<=top;i++)\r\n        res+=get_dist(q[stk[i-1]],q[stk[i]]);\r\n    return res;\r\n}\r\nint main()\r\n{\r\n    scanf(\"%d\",&n);\r\n    for(int i=0;i<n;i++)    scanf(\"%lf%lf\",&q[i].x,&q[i].y);\r\n    double res=andrew();\r\n    printf(\"%.2lf\\n\",res);\r\n    return 0;\r\n}\r\n```'),(15,'admin','滑动窗口','#### 滑动窗口\r\n\r\n> 长度n，k窗口滑动。\r\n\r\n---\r\n```cpp\r\n#include<bits/stdc++.h>\r\nusing namespace std;\r\nconst int N=1e6 + 7;\r\nint n,k;\r\nstruct node{\r\n	int pos,dig;\r\n	node():pos(0),dig(0){}\r\n}a[N];\r\ndeque<node> maxn,minn;\r\nvector<int> v1,v2;\r\nint main()\r\n{\r\n	cin>>n>>k;\r\n	for(int i=1;i<=n;i++)\r\n	{\r\n		cin>>a[i].dig;	a[i].pos=i;\r\n		while(!minn.empty()&&i-minn.front().pos>=k)	minn.pop_front();\r\n		while(!maxn.empty()&&i-maxn.front().pos>=k)	maxn.pop_front();\r\n		if(a[i].dig<minn.front().dig){\r\n			minn.clear();\r\n			minn.push_front(a[i]);\r\n		}\r\n		else{\r\n			while(!minn.empty()&&a[i].dig<minn.back().dig)	minn.pop_back();\r\n			minn.push_back(a[i]);\r\n		}\r\n		if(a[i].dig>maxn.front().dig){\r\n			maxn.clear();\r\n			maxn.push_front(a[i]);\r\n		}\r\n		else{\r\n			while(!maxn.empty()&&a[i].dig>maxn.back().dig)	maxn.pop_back();\r\n			maxn.push_back(a[i]);\r\n		}\r\n		if(i>=k){\r\n			v1.push_back(minn.front().dig);\r\n			v2.push_back(maxn.front().dig);\r\n		}\r\n	}\r\n	for(int i=0;i<v1.size();i++)	cout<<v1[i]<<\" \";\r\n	cout<<\'\\n\';\r\n	for(int i=0;i<v2.size();i++)	cout<<v2[i]<<\" \"; \r\n	return 0;\r\n}\r\n```'),(16,'user1','Dijkstra','```cpp\r\n    while(!que.empty())\r\n    {\r\n        Node tmp=que.top(); que.pop();\r\n        if(tmp.w>dist[tmp.p])  continue;\r\n        int u=tmp.p;\r\n        dist[u]=tmp.w;\r\n        for(int i=head[u];~i;i=edge[i].next)\r\n        {\r\n            int v=edge[i].to;\r\n            if(tmp.w+edge[i].f<dist[v])\r\n            {\r\n                dist[v]=tmp.w+edge[i].f;\r\n                que.push({v,dist[v]});\r\n            }\r\n        }\r\n    }\r\n```\r\n\r\n一些单源的思考：\r\n    1.和枚举的结合，先做预处理再枚举每个点解决问题\r\n    2.寻找第k+1条最小的路，进行二分把所有大于mid的路径进行1赋值，小于0赋值，如果满足就左移\r\n    3.构造虚拟源点进行单元最短路\r\n    4.与状压DP进行结合（ 如拯救大兵瑞恩），利用最短路进行dp\r\n    5.最短路计数的问题，这类问题会满足一种拓扑序，再dijkstra会有一种天然的拓扑序可以用\r\n    6.寻找次短路的问题（如观光），也是利用dp的思想去构造\r\n    7.记录某一点到s点的路径，只要用vecotr<int> pre[N]存就行');

/*Table structure for table `comment` */

CREATE TABLE `comment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `author` varchar(255) NOT NULL,
  `aid` int NOT NULL,
  `content` varchar(2048) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;

/*Data for the table `comment` */

/*Table structure for table `user` */

CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT COMMENT '用户唯一id',
  `username` varchar(255) NOT NULL COMMENT '用户名称',
  `password` varbinary(255) NOT NULL COMMENT '用户密码',
  `invitation` varchar(255) DEFAULT NULL COMMENT '邀请码',
  `introduction` varchar(1024) DEFAULT NULL COMMENT '用户简介',
  `pic` varbinary(255) DEFAULT NULL COMMENT '用户头像',
  PRIMARY KEY (`id`,`username`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;

/*Data for the table `user` */

insert  into `user`(`id`,`username`,`password`,`invitation`,`introduction`,`pic`) values (7,'admin','123456','root','这个人很懒，没有留下什么备注......','/img/person_1.jpg'),(8,'user1','123456','user','大家好这个是测试号1号~','/upload/icon/pg-5dcb1f0501f14ee09da9d548d5420d83.jpg');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
