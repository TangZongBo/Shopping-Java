<template>
    <div>
        <!--面包屑导航区域-->
        <el-breadcrumb separator-class="el-icon-arrow-right">
            <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
            <el-breadcrumb-item>用户管理</el-breadcrumb-item>
            <el-breadcrumb-item>用户列表</el-breadcrumb-item>
        </el-breadcrumb>

        <!--卡片视图区域-->
        <el-card class="box-card">
            <el-row :gutter="20">
                <el-col :span="8">
                    <!--搜索添加区域-->
                    <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
                        <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
                    </el-input>
                </el-col>
                <el-col :span="4">
                      <el-button type="primary">添加用户</el-button>
                </el-col>
            </el-row>

            <!--用户列表区域-->
             <el-table :data="userlist.slice((queryInfo.pagenum-1)*queryInfo.pagesize,queryInfo.pagenum*queryInfo.pagesize)" border stripe>
                 <el-table-column label="#" type="index"></el-table-column>
                 <el-table-column label="姓名" prop="username"></el-table-column>
                 <el-table-column label="邮箱" prop="email"></el-table-column>
                 <el-table-column label="电话" prop="mobile"></el-table-column>
                 <el-table-column label="角色" prop="role_name"></el-table-column>
                 <el-table-column label="状态">
                     <template slot-scope="scope">
                         <el-switch v-model="scope.row.mg_state" @change="userStateChanged(scope.row)">
                         </el-switch>
                     </template>
                 </el-table-column>
                 <el-table-column label="操作" width="180px">
                     <template slot-scope="scope">
                         <!--修改-->
                         <el-button type="primary" icon="el-icon-edit" size="mini"></el-button>
                         <!--删除-->
                         <el-button type="danger" icon="el-icon-delete" size="mini"></el-button>
                         <!--分配角色-->
                         <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
                             <el-button type="warning" icon="el-icon-setting" size="mini"></el-button>
                         </el-tooltip>

                     </template>
                 </el-table-column>
             </el-table>

            <!--分页区域-->
            <el-pagination
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange"
                    :current-page="queryInfo.pagenum"
                    :page-sizes="[1,2,5,10]"
                    :page-size="queryInfo.pagesize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total">
            </el-pagination>
        </el-card>


    </div>
</template>

<script>
    export default {
        data(){
            return{
                //获取用户列表参数对象
                queryInfo:{
                    query:'',
                    //当前的页数
                    pagenum:1,
                    //当前每页显示多少条数据
                    pagesize:2
                },
                userlist:[],
                total:0
            }
        },
        created() {
            this.getUserList()
        },
        methods:{
           /*async*/ getUserList(){

                //alert(this.queryInfo.query) 搜索功能没有实现
                this.userlist = [
                    {
                        username:'admin',
                        email:'123@qq.com',
                        mobile:'18808108305',
                        role_name:'超级管理员',
                        mg_state:true
                    },
                    {
                        username:'123',
                        email:'321@qq.com',
                        mobile:'17854335116',
                        role_name:'测试123',
                        mg_state:false
                    },
                    {
                        username:'admin123',
                        email:'123@qq.com',
                        mobile:'18808108305',
                        role_name:'管理员',
                        mg_state:false
                    },
                    {
                        username:'123789',
                        email:'321@qq.com',
                        mobile:'17854335116',
                        role_name:'测试123',
                        mg_state:false
                    }
                ]
               this.total = this.userlist.length
               /*this.$http.get('users',{params:this.queryInfo}).then(function (resp) {
                   if(resp.meta.status !==200) return _this.$message.error("获取用户列表失败!")

                   //赋值
                   this.userlist = resp.data.users
                   this.total = resp.data.total

               });*/
            },
            //监听 pageSize 改变的事件
            handleSizeChange(newSize){
               //console.log('每页'+ newSize+'条')
               this.queryInfo.pagesize = newSize
                this.getUserList()
            },
            //监听 页码值 改变的事件
            handleCurrentChange(newSize){
                //console.log('当前第'+newSize+'页')
                this.queryInfo.pagenum = newSize
                this.getUserList()
            },
            userStateChanged(userinfo){
               //console.log(userinfo)
               /*const _this = this
               this.$http.put('users/${userinfo.id}/state/${userinfo.mg_state}').then(function (resp) {
                    if(resp.meta.status!==200){
                        userinfo.mg_state = !userinfo.mg_state
                        _this.$message.error("用户状态更新失败!")
                        return
                    }
                   _this.$message.success("用户状态更新成功!")
               })*/
                this.$message.success("用户状态更新成功!")
            }
        }
    }
</script>

<style scoped>

</style>
