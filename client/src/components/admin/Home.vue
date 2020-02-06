<template>
        <el-container class="home-container">
            <!--头部区域-->
            <el-header>
                <div>
                    <img src="../../assets/logo.png">
                    <span>周氏科技后台管理系统</span>
                </div>
                <el-button type="info" @click="logout">退出</el-button>
            </el-header>
            <!--页面主体-->
            <el-container>
                    <!--侧边栏-->
                      <el-aside :width="isCollapse?'64px':'200px'">
                          <div class="toggle-button" @click="toggleCollapse">|||</div>
                          <!--侧边栏菜单区域-->
                          <el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" :unique-opened="true"
                                   :collapse="isCollapse" :collapse-transition="false" :router="true" :default-active="activePath">
                              <!--一级菜单-->
                              <el-submenu :index="item.id+''" v-for="item in menulist" :key="item.id">
                                  <!--一级菜单的模板区域-->
                                  <template slot="title">
                                      <!--图标-->
                                      <i :class="iconObj[item.id]"></i>
                                      <!--文本-->
                                      <span>{{item.name}}</span>
                                  </template>
                                  <!--二级菜单-->
                                  <el-menu-item :index="'/'+item2.path" v-for="item2 in item.children" :key="item2.id" @click="saveNavState('/'+item2.path)">
                                      <template slot="title">
                                          <!--图标-->
                                          <i class="el-icon-menu"></i>
                                          <!--文本-->
                                          <span>{{item2.name}}</span>
                                      </template>
                                  </el-menu-item>
                              </el-submenu>
                          </el-menu>

                      </el-aside>
                    <!--右侧内容主体-->
                      <el-main>
                            <router-view></router-view>
                      </el-main>
            </el-container>
        </el-container>
</template>

<script>
    export default {
        created(){
          this.getMenuList()
          this.activePath = window.sessionStorage.getItem('activePath')
        },
        data(){
            return{
               //左侧菜单数据
               menulist: [
                   {
                       id:101,
                       name:'用户管理',
                       children:[
                           {
                               id:102,
                               name:'用户列表',
                               path:'users'
                           }
                       ]
                   },
                   {
                       id:103,
                       name:'权限管理',
                       children:[
                           {
                               id:104,
                               name:'角色列表',
                               path:'roles'
                           },
                           {
                               id:105,
                               name:'权限列表',
                               path:'rights'
                           }
                       ]
                   },
                   {
                       id:106,
                       name:'商品管理',
                       children:[
                           {
                               id:107,
                               name:'商品列表',
                               path:'goods'
                           },
                           {
                               id:108,
                               name:'分类参数',
                               path:'sortargs'
                           },
                           {
                               id:109,
                               name:'商品分类',
                               path:'sorts'
                           }
                       ]
                   },
                   {
                       id: 110,
                       name:'订单管理',
                       children:[
                           {
                               id:111,
                               name:'订单列表',
                               path:'orders'
                           }
                       ]
                   }
                   ],
                iconObj:{
                    '101':'iconfont icon-user',
                    '103':'iconfont icon-tijikongjian',
                    '106':'iconfont icon-shangpin',
                    '110':'iconfont icon-danju'
                },
                //是否折叠
                isCollapse:false,
                //被激活链接地址
                activePath:''
            }
        },
        methods:{
            logout(){
                 window.sessionStorage.clear()
                this.$message.success("退出成功!")
                 this.$router.push("/login")
            },
            //获取菜单列表
            getMenuList(){

            },
            //菜单折叠与展开
            toggleCollapse(){
                this.isCollapse = !this.isCollapse
            },
            //保存激活状态
            saveNavState(activePath){
                window.sessionStorage.setItem('activePath',activePath)
                this.activePath=activePath
            }
        }
    }
</script>

<style lang="less" scoped>
      .home-container{
          height: 100%;
      }
      .el-header{
          background-color: #373d41;
          display: flex;
          //左右贴边对齐
          justify-content: space-between;
          padding-left: 0;
          //居中
          align-items: center;
          color:#fff;
          font-size: 18px;
          > div{
              display: flex;
              align-items: center;
              span{
                  margin-left: 15px;
              }
          }
      }
      .el-aside{
          background-color: #333744;
          .el-menu{
              border-right: none;
          }
      }
      .el-main{
          background-color: #eaedf1;
      }
      .iconfont{
          margin-right: 10px;
      }
      .toggle-button{
          background-color: #4A5064;
          font-size: 10px;
          line-height: 24px;
          color:#fff;
          text-align: center;
          letter-spacing: 0.2em;
          cursor: pointer;
      }
</style>
