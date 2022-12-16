FROM java:8
MAINTAINER dydong<1935569700@qq.com>

ENV MTPATH /usr/local
WORKDIR $MYPATH

RUN yum -y install vim
RUN yum -y install net-tools

COPY *.jar /user/local/app.jar

CMD ["--server.port=8080"]
CMD echo $MYPATH
CMD echo "----end----"
CMD /bin/bash

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app.jar"]