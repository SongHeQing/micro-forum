# 正在编写实习展示项目,基于SpringBoot和React的微社区。  
> 正在完成文章列表展示功能、用户登录、发布文章，  
准备完成的功能文章图片展示和发布、文章详情,  
预加入功能,文章点赞收藏评论;  

- 用户登录功能、后端准备使用jwt令牌技术，登录成功时响应令牌给前端，  
，在Axios请求拦截器中，每次请求携带令牌给后端，后端在拦截器中校验  
令牌，并存储在ThreadLocal中方便获取用户数据  
- 发布文章功能、普通文章使用textarea接收，图文混排考虑使用ReactQuill富文本编辑器。


  | 任务    | 后端接口和业务完成状态 | 数据库结构完成状态  | 前端静态页面完成状态 | 前端接口和业务完成状态 |
  | :----: |     :----:     |      :----:     |    :----:       |       :----:     |
  | 获取主页文章列表 |      ✅       |       ✅        |       ✅        |     ✅          |
   | 获取主页文章列表的封面 |      ✅       |       ✅        |       ✅        |     ✅          |
  | 文章发布 |      ✅       |       ✅        |       ✅        |     ✅          |
  | 用户登录 |      ✅       |       ✅        |       ✅        |     ✅          |
  | 令牌技术配合前后端拦截器检测用户状态 |      ✅       |       🈚        |       🈚        |     ✅      |
  | 用户主页 |      ⏳       |       ⏳        |       ⏳        |     ⏳          |
  | 评论 |      ✅       |       ✅        |       ✅        |     ✅          |
  | 二级评论 |      ✅       |       ✅        |       ⏳        |     ✅          |

>   状态： ✅完成   🚧开发中  ⏳未开始  🎨 设计或测量UI中