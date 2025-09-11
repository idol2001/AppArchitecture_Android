# AppArchitecture_Android

一个基于MVVM架构的Android应用框架示例，展示了现代Android应用开发的最佳实践，包括组件化架构、网络请求封装和页面路由管理，实现了一个简单的天气预报功能，可以查询城市的当前天气，使用[和风天气](https://dev.qweather.com)提供API和天气数据。


## 架构设计

采用**MVVM架构**，核心组件包括：
- **ViewModel**：管理UI相关数据，与数据层交互，处理业务逻辑
- **LiveData**：持有可观察的数据，自动响应数据变化并通知UI
- **Fragment**：负责UI展示和用户交互，观察ViewModel中的数据变化

## 网络请求

基于[OkHttp3](https://github.com/square/okhttp)和[Retrofit](https://github.com/square/retrofit)实现网络请求封装：
- 使用Retrofit进行API接口定义和请求管理
- 集成OkHttp3拦截器实现：
    - 网络请求/响应日志打印（`LoggingInterceptor`）
    - 身份验证处理（`AuthInterceptor`）
- 依赖版本：
    - OkHttp 5.1.0
    - Retrofit 3.0.0

## 页面路由管理

采用[airbnb DeepLinkDispatch](https://github.com/airbnb/DeepLinkDispatch)库实现路由功能：

### 路由定义
- 在`Router`类中定义路由路径常量
- 通过`@DeepLink`注解为Activity配置路由映射
- 通过链式调用优雅跳转页面，注意with方法参数顺序要和deeplink地址中参数顺序一致

```java
// 路由路径定义
public enum PageEnum {
    MAIN_PAGE(PageEnum.MAIN_PAGE_DEEPLINK),
    CITY_PAGE(PageEnum.CITY_PAGE_DEEPLINK);

    // 将 URL 定义为 public static final String 常量
    public static final String MAIN_PAGE_DEEPLINK = "customscheme://example.com/main_page/{city_id}";
    public static final String CITY_PAGE_DEEPLINK = "customscheme://example.com/city_page/{city_name}";
  ...
}

// Activity路由配置
@DeepLink(PageEnum.MAIN_PAGE_DEEPLINK)
public class MainActivity extends AppCompatActivity { ... }
```

### 页面跳转
- 普通跳转：
- with方法参数顺序要和deeplink地址中参数顺序一致
  ```java
  Router.page(PageEnum.CITY_PAGE).with("乌鲁木齐").to(getActivity());
  ```

- 需要返回结果的跳转：
  ```java
  Router.page(PageEnum.MAIN_PAGE).to(getActivity(), resultLauncher);
  ```

## 依赖库

- 架构组件：AndroidX Lifecycle (LiveData, ViewModel)
- 网络：[OkHttp3](https://github.com/square/okhttp), [Retrofit](https://github.com/square/retrofit), [Gson](https://github.com/google/gson)转换器
- 路由：[DeepLinkDispatch](https://github.com/airbnb/DeepLinkDispatch) 6.2.2
- 其他：Lombok（简化实体类代码）

## 项目结构

```
AppArchitecture_Android
├── common
│   ├── router  // 路由相关类
│   ├── utils   // 其他工具类
│   └── webservice  // 网络请求相关封装
└── ui  
    ├── city         // 包含Activity和Fragment等UI组件
    │   ├── viewmodel // 包含ViewModel、Adpter等类，处理业务逻辑
    │   └── model    // 数据模型类
    └── main
        ├── viewmodel
        └── model
```

## 功能特点

- 清晰的MVVM架构分层
- 灵活的路由管理系统
- 可扩展的网络请求框架
- 响应式UI更新机制

该框架可作为Android应用开发的基础模板，方便快速构建功能完善、架构清晰的应用程序。

## 和风天气

使用和风天气API，需要在项目中增加`res/values/qweather_auth.xml`文件，文件内容如下：

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="qweather_api_host">Your API Host</string>
    <string name="qweather_private_key">Your Private Key</string>
    <string name="qweather_key_id">Your Key ID</string>
    <string name="qweather_project_id">Your Project ID</string>
</resources>
```

