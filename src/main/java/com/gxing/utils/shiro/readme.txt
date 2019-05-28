shiro：Apache shiro框架，提供加密，认证，授权等权限控制功能
相关jar包：
	shiro-core 核心包
	shiro-web  web项目包
	shiro-spring spring整合包
	shiro-aspectj 
依赖jar包：
	slf4j-api
				

<properties>
        <slf4j.version>1.6.6</slf4j.version>
        <shiro.version>1.2.3</shiro.version>   
  </properties>

	<!-- shiro -->
	<!-- apache shiro dependencies -->
        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-core</artifactId>
            <version>${shiro.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-web</artifactId>
            <version>${shiro.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-spring</artifactId>
            <version>${shiro.version}</version>
        </dependency>

        <dependency>
            <groupId>org.apache.shiro</groupId>
            <artifactId>shiro-aspectj</artifactId>
            <version>${shiro.version}</version>
        </dependency>