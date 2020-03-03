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
                      <el-button type="primary" @click="addDialogVisible=true">添加用户</el-button>
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
                         <el-button type="primary" @click="showEditDialog(scope.row.id)" icon="el-icon-edit" size="mini"></el-button>
                         <!--删除-->
                         <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeUserById(scope.row.id)"></el-button>
                         <!--分配角色-->
                         <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
                             <el-button type="warning" icon="el-icon-setting" size="mini" @click="setRole(scope.row)"></el-button>
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

        <!--添加用户对话框-->
        <el-dialog
                title="添加用户"
                :visible.sync="addDialogVisible"
                width="50%" @close="addDialogClosed">
            <!--内容主体区域-->
            <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
                <el-form-item label="用户名" prop="username">
                    <el-input v-model="addForm.username"></el-input>
                </el-form-item>
                <el-form-item label="密码" prop="password">
                    <el-input type="password" v-model="addForm.password"></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="addForm.email"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="addForm.mobile"></el-input>
                </el-form-item>
            </el-form>

            <!--底部区域-->
            <span slot="footer" class="dialog-footer">
                <el-button @click="addDialogVisible = false">取 消</el-button>
                <el-button type="primary" @click="addUser">确 定</el-button>
            </span>
        </el-dialog>

        <!--修改用户对话框-->
        <el-dialog
                title="修改" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
            <!--内容主体区域-->
            <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
                <el-form-item label="用户名">
                    <el-input v-model="editForm.username" disabled></el-input>
                </el-form-item>
                <el-form-item label="邮箱" prop="email">
                    <el-input v-model="editForm.email"></el-input>
                </el-form-item>
                <el-form-item label="手机" prop="mobile">
                    <el-input v-model="editForm.mobile"></el-input>
                </el-form-item>
            </el-form>
            <!--底部区域-->
            <span slot="footer" class="dialog-footer">
            <el-button @click="editDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="editUserInfo">确 定</el-button>
            </span>
        </el-dialog>

        <!--分配角色的对话框-->
        <el-dialog title="分配角色" :visible.sync="setRoleDialogVisible" width="50%" @close="setRoleDialogClosed">
            <div>
                <p>当前的用户:{{userInfo.username}}</p>
                <p>当前的角色:{{userInfo.role_name}}</p>
                <p>分配的新角色:
                    <el-select v-model="selectedRoleId" placeholder="请选择">
                        <el-option
                                v-for="item in rolesList"
                                :key="item.id"
                                :label="item.roleName"
                                :value="item.id">
                        </el-option>
                    </el-select>
                </p>
            </div>
            <span slot="footer" class="dialog-footer">
            <el-button @click="setRoleDialogVisible = false">取 消</el-button>
            <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
          </span>
        </el-dialog>

    </div>
</template>
0
<script>
    export default {
        data(){
            //验证邮箱的规则
            var checkEmail = (rule,value,cb) =>{
                //正则表达式
                const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/
                if(regEmail.test(value)){
                    //合法
                    return cb()
                }
                cb(new Error('请输入合法的邮箱'))

            }

            //验证手机号规则
            var checkMobile = (rule,value,cb) =>{
                //正则表达式
                const regMobile = /^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
                if(regMobile.test(value)){
                    //合法
                    return cb()
                }
                cb(new Error('请输入合法的手机'))
            }

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
                total:0,
                //添加用户对话框的显示与隐藏
                addDialogVisible:false,
                //修改用户对话框的显示与隐藏
                editDialogVisible:false,
                //查询到的用户信息对象
                editForm:{
                    username:'',
                    password:'',
                    email:'',
                    mobile:''
                },
                //添加用户信息表单数据
                addForm:{
                   username:'',
                    password:'',
                    email:'',
                    mobile:''
                },
                //添加用户表单信息的验证对象
                addFormRules:{
                    username:[
                        {required:true,message:'请输入用户名',trigger:'blur'},
                        {min:3,max:10,message:'用户名长度在3~10个字符之间',trigger:'blur'}
                    ],
                    password:[
                        {required:true,message:'请输入密码',trigger:'blur'},
                        {min:6,max:15,message:'密码长度在6~15个字符之间',trigger:'blur'}
                    ],
                    email:[
                        {required:true,message:'请输入邮箱',trigger:'blur'},
                        {validator:checkEmail,trigger:'blur'}
                    ],
                    mobile:[
                        {required:true,message:'请输入手机',trigger:'blur'},
                        {validator:checkMobile,trigger:'blur'}
                    ]
                },
                //修改用户表单信息的验证对象
                editFormRules:{
                    email:[
                        {required:true,message:'请输入邮箱',trigger:'blur'},
                        {validator:checkEmail,trigger:'blur'}
                    ],
                    mobile:[
                        {required:true,message:'请输入手机',trigger:'blur'},
                        {validator:checkMobile,trigger:'blur'}
                    ]
                },
                //分配角色对话框隐藏与显示
                setRoleDialogVisible:false,
                //需要被分配角色的用户信息
                userInfo:{},
                //角色列表
                rolesList:[],
                //已选中角色id值
                selectedRoleId:''
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
                        id:1,
                        username:'admin',
                        email:'123@qq.com',
                        mobile:'18808108305',
                        role_name:'超级管理员',
                        mg_state:true
                    },
                    {
                        id:2,
                        username:'123',
                        email:'321@qq.com',
                        mobile:'17854335116',
                        role_name:'测试123',
                        mg_state:false
                    },
                    {
                        id:3,
                        username:'admin123',
                        email:'123@qq.com',
                        mobile:'18808108305',
                        role_name:'管理员',
                        mg_state:false
                    },
                    {
                        id:4,
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
            },
            //监听用户添加对话框关闭
            addDialogClosed(){
                this.$refs.addFormRef.resetFields()
            },
            //监听用户修改对话框关闭
            editDialogClosed(){
                this.$refs.editFormRef.resetFields()
            },
            //添加用户预验证
            addUser(){
                //const _this = this
                this.$refs.addFormRef.validate(valid =>{
                     if(!valid)
                         return
                    //发起添加用户的请求
                     /*this.$http.post('/users',{params:this.addForm}).then(function (resp) {
                           if(resp.meta.status!==201){
                               _this.$message.error('添加用户错误')
                           }
                           //添加成功
                           _this.$message.success('添加用户成功')
                           //把对话框隐藏
                           this.addDialogVisible = false
                           //重新获取列表
                           this.getUserList()
                     })*/
                })
            },
            //显示用户修改对话框
            showEditDialog(id){
                const _this = this
                /*this.$http.get('users/'+id).then(function (resp) {
                    if(resp.meta.status!==200){
                        _this.$message.error('查询用户信息失败!')
                    }

                    this.editForm = resp.data
                })

                //显示对话框
                this.editDialogVisible = true*/
            },
            //修改用户预验证
            editUserInfo(){
                 const _this = this
                 this.$refs.editFormRef.validate(valid =>{
                      if(!valid)
                          return
                      //发起修改用户请求
                     this.$http.put('users/'+this.editForm.id,{
                         email:this.editForm.email,
                         mobile:this.editForm.mobile}).then(function (resp) {
                         if(resp.meta.status!==200){
                             _this.$message.error('更新用户信息失败!')
                         }
                         //隐藏
                         _this.editDialogVisible = false
                         //获取
                         _this.getUserList()
                         //提示
                         _this.$message.success('更新用户信息成功!')
                     })
                 })
            },
            //根据id删除用户信息
            async removeUserById(id){
                const confirmResult = await this.$confirm('此操作将永久删除该用户, 是否继续?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
            }
            ).catch(err => err)
                //如果用户确认删除 则返回值为字符串 confirm
                //如果用户取消了删除 则返回值为字符串 cancel
                if(confirmResult!=='confirm'){
                    return this.$message.info('已取消删除')
                }

                /*this.$http.delete('users/'+id).then(function (resp) {

                })*/
                const {data:res} = await this.$http.delete('users/'+id)
                if(res.meta.status!==200){
                    this.$message.error('删除用户信息失败!')
                }
                this.$message.success('删除用户信息成功!')
                this.getUserList()
            },
            //展示分配角色的对话框
            async setRole(userInfo){
               this.userInfo = userInfo

                //展示对话框前获取所有的角色列表
                /*const {data:res} = await this.$http.get('roles')
                if(res.meta.data!==200){
                    this.$message.error('获取角色列表失败!')
                }
                this.rolesList = this.data*/
                this.rolesList = [
                    {
                        id:30,
                        roleName:'主管',
                        roleDesc:'技术负责人'
                    }
                ]
                this.setRoleDialogVisible=true

            },
            //分配角色
            async saveRoleInfo(){
                 if(!this.selectedRoleId){
                     this.$message.error('请选择要分配的角色!')
                 }

                 /*const {data:res} = await this.$http.put('users/${this.userInfo.id}/role',{rids:this.selectedRoleId})
                 if(res.meta.status!==200){
                     this.$message.error('更新角色失败!')
                 }
                this.$message.success('更新角色成功!')
                this.getUserList()*/
                this.setRoleDialogVisible = false

            },
              //监听分配角色对话框的关闭事件
            setRoleDialogClosed(){
               this.selectedRoleId = ''
                this.userInfo = {}
            }
        }
    }
</script>

<style scoped>

</style>
